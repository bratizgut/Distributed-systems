package task2.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import task2.models.TagDB;

import task2.dbManager.DbManager;

public class TagDao implements Dao<TagDB> {

    private final PreparedStatement insertPreparedStatement;
    private final PreparedStatement batchInsertPreparedStatement;

    private int curBatchSize = 0;

    public TagDao() throws SQLException {
        this.insertPreparedStatement = DbManager.getConnection().prepareCall("INSERT INTO tags"
                + "(key, value, node_id)"
                + "VALUES (?, ?, ?)");
        this.batchInsertPreparedStatement = DbManager.getConnection().prepareCall("INSERT INTO tags"
                + "(key, value, node_id)"
                + "VALUES (?, ?, ?)");;
    }

    private void prepareStatement(TagDB tag, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, tag.getKey());
        preparedStatement.setString(2, tag.getValue());
        preparedStatement.setLong(3, tag.getNodeId());
    }

    private TagDB parseTag(ResultSet rs) throws SQLException {
        TagDB tag = new TagDB();
        tag.setKey(rs.getString("key"));
        tag.setValue(rs.getString("value"));
        tag.setNodeId(rs.getLong("node_id"));
        return tag;
    }

    public int flushBatch() throws SQLException {
        int batchSize = curBatchSize;
        batchInsertPreparedStatement.executeBatch();
        curBatchSize = 0;
        batchInsertPreparedStatement.clearBatch();
        return batchSize;
    }

    @Override
    public Optional<TagDB> getById(long id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TagDB> getAll() throws SQLException {
        String sql = "SELECT * FROM tags \n";
        try ( ResultSet rs = DbManager.getConnection().createStatement().executeQuery(sql)) {
            List<TagDB> resulList = new ArrayList<>();
            while (rs.next()) {
                resulList.add(parseTag(rs));
            }
            return resulList;
        }
    }

    @Override
    public int delete(TagDB t) throws SQLException {
        String sql = "DELETE FROM nodes \n"
                + "WHERE key = " + t.getKey()
                + " AND node_id = " + t.getNodeId();
        return DbManager.getConnection().createStatement().executeUpdate(sql);
    }

    @Override
    public int update(TagDB t) throws SQLException {
        String sql = String.format("UPDATE tags SET"
                + "value = %s"
                + "WHERE key = %s AND node_id = %s", 
                t.getValue(), t.getKey(), t.getNodeId());
        return DbManager.getConnection().createStatement().executeUpdate(sql);
    }

    @Override
    public void saveExecute(TagDB t) throws SQLException {
        String sql = String.format("INSERT INTO tags "
                + "(key, value, node_id) "
                + "VALUES ('%s', '%s', %s)", t.getKey(), t.getValue(), t.getNodeId());
        DbManager.getConnection().createStatement().execute(sql);
    }

    @Override
    public void savePrep(TagDB t) throws SQLException {
        prepareStatement(t, insertPreparedStatement);
        insertPreparedStatement.execute();
    }

    @Override
    public void saveBatch(TagDB t) throws SQLException {
        prepareStatement(t, batchInsertPreparedStatement);
        batchInsertPreparedStatement.addBatch();

        curBatchSize++;
        if (curBatchSize >= 1000) {
            flushBatch();
        }
    }

}
