package net.myjdbc.jdbc.connection;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class ConnectionPool {

    private static final String SQL_VERIFY_CONN = "select 1";
    private static ConnectionPool instance = null;
    private final String URL = "jdbc:mysql://127.0.0.1:3306";
    private final String USERNAME = "root";
    private final String PASSWORD = "password";
    Stack<Connection> freePool = new Stack<Connection>();
    Set<Connection> occupiedPool = new HashSet<Connection>();

    private ConnectionPool() {

    }

    public static ConnectionPool getInstance() {
        if (instance == null)
            instance = new ConnectionPool();
        return instance;
    }

    public synchronized Connection getConnection() throws SQLException {
        Connection conn = null;
        conn = getConnectionFromPool();
        if (conn == null) {
            conn = createNewConnectionForPool();
        }
        conn = makeAvailable(conn);
        return conn;
    }


    private Connection createNewConnectionForPool() throws SQLException {
        Connection conn = createNewConnection();
        occupiedPool.add(conn);
        return conn;
    }

    private Connection createNewConnection() throws SQLException {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        return conn;
    }

    private Connection getConnectionFromPool() {
        Connection conn = null;
        if (freePool.size() > 0) {
            conn = freePool.pop();
            occupiedPool.add(conn);
        }
        return conn;
    }

    private Connection makeAvailable(Connection conn) throws SQLException {
        if (isConnectionAvailable(conn)) {
            return conn;
        }
        occupiedPool.remove(conn);
        conn.close();

        conn = createNewConnection();
        occupiedPool.add(conn);
        return conn;
    }

    private boolean isConnectionAvailable(Connection conn) {
        try {
            Statement statement = conn.createStatement();
            statement.execute(SQL_VERIFY_CONN);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

}
