package task2.dbManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbManager {

    private static Connection connection;

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/OSM";
    private static final String DB_USER = "OSM";
    private static final String DB_PASSWORD = "OSM";

    public static synchronized Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        }
        return connection;
    }

    public static void closeConnection() throws SQLException {
        getConnection().close();
    }

    public static void initDB() throws SQLException {
        Connection conn = getConnection();

        conn.createStatement().execute("DROP TABLE IF EXISTS tags CASCADE");
        conn.createStatement().execute("DROP TABLE IF EXISTS nodes CASCADE");

        conn.createStatement().execute("CREATE TABLE nodes (\n"
                + "    id BIGINT PRIMARY KEY,\n"
                + "    lat DECIMAL,\n"
                + "    lon DECIMAL,\n"
                + "    _user VARCHAR(2000),\n"
                + "    uid BIGINT,\n"
                + "    visible BOOLEAN,\n"
                + "    version BIGINT,\n"
                + "    changeset BIGINT,\n"
                + "    _timestamp TIMESTAMP\n"
                + ");");

        conn.createStatement().execute("CREATE TABLE tags(\n"
                + "    node_id BIGINT NOT NULL REFERENCES nodes(id),\n"
                + "    key VARCHAR(256) NOT NULL,\n"
                + "    value VARCHAR(256) NOT NULL,\n"
                + "    PRIMARY KEY (node_id, key)\n"
                + ");");
    }

}
