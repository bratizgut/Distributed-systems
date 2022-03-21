package task2.dao;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.xml.datatype.XMLGregorianCalendar;
import task2.models.generated.Node;
import task2.models.generated.Tag;

import task2.dbManager.DbManager;
import task2.models.TagDB;

public class NodeDao implements Dao<Node> {

    private final PreparedStatement insertPreparedStatement;
    private final PreparedStatement batchInsertPreparedStatement;

    private final TagDao tagDao;
    private int curBatchSize;

    public NodeDao() throws SQLException {
        insertPreparedStatement = DbManager.getConnection().prepareCall("INSERT INTO nodes \n"
                + " (id, lat, lon, _user, uid, visible, version, changeset, _timestamp) \n"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        batchInsertPreparedStatement = DbManager.getConnection().prepareCall("INSERT INTO nodes \n"
                + " (id, lat, lon, _user, uid, visible, version, changeset, _timestamp) \n"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

        tagDao = new TagDao();
        curBatchSize = 0;
    }

    private void prepareStatement(Node t, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1, t.getId().longValue());
        preparedStatement.setDouble(2, t.getLat());
        preparedStatement.setDouble(3, t.getLon());
        preparedStatement.setString(4, t.getUser());
        preparedStatement.setLong(5, t.getUid().longValue());
        if (t.isVisible() != null) {
            preparedStatement.setBoolean(6, t.isVisible());
        } else {
            preparedStatement.setNull(6, Types.NULL);
        }
        preparedStatement.setLong(7, t.getVersion().longValue());
        preparedStatement.setLong(8, t.getChangeset().longValue());
        Timestamp timestamp = new Timestamp(t.getTimestamp().toGregorianCalendar().getTimeInMillis());
        preparedStatement.setTimestamp(9, timestamp);
    }

    private Node parseNode(ResultSet rs) throws SQLException {
        Node node = new Node();
        node.setId(BigInteger.valueOf(rs.getLong("id")));
        node.setLat(rs.getDouble("lat"));
        node.setLon(rs.getDouble("lon"));
        node.setUser(rs.getString("_user"));
        node.setUid(BigInteger.valueOf(rs.getLong("uid")));
//        node.setVisible();
        node.setVersion(BigInteger.valueOf(rs.getLong("version")));
        node.setChangeset(BigInteger.valueOf(rs.getLong("changeset")));
//        node.setTimestamp();
        return node;
    }

    public int flushBatch() throws SQLException {
        int batchSize = curBatchSize;
        batchInsertPreparedStatement.executeBatch();
        curBatchSize = 0;
        batchInsertPreparedStatement.clearBatch();
        tagDao.flushBatch();
        return batchSize;
    }

    @Override
    public void saveExecute(Node t) throws SQLException {
        String sql = String.format("INSERT INTO nodes \n"
                + " (id, lat, lon, _user, uid, visible, version, changeset, _timestamp) \n"
                + "VALUES (%s, %s, %s, '%s', %s, %s, %s, %s, '%s')",
                t.getId(), t.getLat(), t.getLon(), t.getUser(), t.getUid(), t.isVisible(), t.getVersion(), t.getChangeset(), t.getTimestamp());
        DbManager.getConnection().createStatement().execute(sql);
        for (Tag tag : t.getTag()) {
            TagDB tagDB = new TagDB(tag);
            tagDB.setNodeId(t.getId().longValue());
            tagDao.saveExecute(tagDB);
        }
    }

    @Override
    public void savePrep(Node t) throws SQLException {
        prepareStatement(t, insertPreparedStatement);
        insertPreparedStatement.execute();
        for (Tag tag : t.getTag()) {
            TagDB tagDB = new TagDB(tag);
            tagDB.setNodeId(t.getId().longValue());
            tagDao.savePrep(tagDB);
        }
    }

    @Override
    public void saveBatch(Node t) throws SQLException {
        prepareStatement(t, batchInsertPreparedStatement);
        batchInsertPreparedStatement.addBatch();

        curBatchSize++;
        if (curBatchSize >= 1000) {
            flushBatch();
        }

        for (Tag tag : t.getTag()) {
            TagDB tagDB = new TagDB(tag);
            tagDB.setNodeId(t.getId().longValue());
            tagDao.saveBatch(tagDB);
        }
    }

    @Override
    public Optional<Node> getById(long id) throws SQLException {
        String sql = "SELECT * FROM nodes \n"
                + "WHERE id = " + id;
        try ( ResultSet rs = DbManager.getConnection().createStatement().executeQuery(sql)) {
            if (rs.next()) {
                return Optional.of(parseNode(rs));
            }
            return Optional.empty();
        }
    }

    @Override
    public List<Node> getAll() throws SQLException {
        String sql = "SELECT * FROM nodes \n";
        try ( ResultSet rs = DbManager.getConnection().createStatement().executeQuery(sql)) {
            List<Node> resulList = new ArrayList<>();
            while (rs.next()) {
                resulList.add(parseNode(rs));
            }
            return resulList;
        }
    }

    @Override
    public int delete(Node t) throws SQLException {
        String sql = "DELETE FROM nodes \n"
                + "WHERE id = " + t.getId();
        return DbManager.getConnection().createStatement().executeUpdate(sql);
    }

    @Override
    public int update(Node t) throws SQLException {
        String sql = String.format("UPDATE nodes SET \n"
                + "lat = %s, lon = %s, "
                + "_user = &s, uid = %s, "
                + "visible = %s, version = %s, "
                + "changeset = %s, _timestamp = %s \n"
                + "WHERE id = %s",
                t.getLat(),
                t.getLon(),
                t.getUser(),
                t.getUid(),
                t.isVisible(),
                t.getVersion(),
                t.getChangeset(),
                t.getTimestamp(),
                t.getId());
        return DbManager.getConnection().createStatement().executeUpdate(sql);
    }

}
