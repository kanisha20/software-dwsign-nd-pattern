package gym.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConnectionPool {

    private static final ConnectionPool instance = new ConnectionPool();

    private final ArrayList<Connection> pool = new ArrayList<Connection>();

    private ConnectionPool() {
        try {
            String url = "jdbc:mysql://localhost:3306/gymdb";
            String user = "root";
            String password = "your_password"; // Change this password

            for (int i = 0; i < 3; i++) {
                Connection con = DriverManager.getConnection(url, user, password);
                pool.add(con);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Could not connect to MySQL. Check MySQL and password.", e);
        }
    }

    public static ConnectionPool getInstance() {
        return instance;
    }

    public synchronized Connection borrowConnection() {
        if (pool.size() > 0) {
            return pool.remove(0);
        }
        return null;
    }

    public synchronized void returnConnection(Connection con) {
        if (con != null) {
            pool.add(con);
        }
    }
}
