package tiffin.dao;

import tiffin.model.DashboardStats;
import tiffin.model.Order;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================
 *  Q4 TASK 2+3 — ORDER DAO IMPLEMENTATION (JDBC)
 * ============================================================
 *  findAll()           → saare orders laao (Task 2)
 *  getDashboardStats() → total, pending, revenue (Task 3)
 * ============================================================
 */
public class OrderDaoImpl implements OrderDao {

    private String jdbcUrl, dbUser, dbPassword;

    public OrderDaoImpl(String jdbcUrl, String dbUser, String dbPassword) {
        this.jdbcUrl = jdbcUrl; this.dbUser = dbUser; this.dbPassword = dbPassword;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
    }

    // ================================================================
    //  findAll() — Saare orders laao [Task 2]
    // ================================================================
    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders ORDER BY order_date DESC";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Order o = new Order(
                    rs.getInt("order_id"),
                    rs.getString("customer_name"),
                    rs.getString("plan_name"),
                    rs.getInt("quantity"),
                    rs.getDouble("amount"),
                    rs.getString("status"),
                    rs.getString("order_date")
                );
                orders.add(o);
            }
        } catch (SQLException e) {
            System.out.println("Error in OrderDao.findAll: " + e.getMessage());
        }
        return orders;
    }

    // ================================================================
    //  getDashboardStats() — Dashboard ke 3 cards ke liye [Task 3]
    // ================================================================
    @Override
    public DashboardStats getDashboardStats() {
        int totalOrders = 0;
        int pendingOrders = 0;
        double totalRevenue = 0;

        // ---- Total orders + total revenue ----
        String sql1 = "SELECT COUNT(*) AS total, COALESCE(SUM(amount),0) AS revenue FROM orders";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql1);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                totalOrders = rs.getInt("total");
                totalRevenue = rs.getDouble("revenue");
            }
        } catch (SQLException e) {
            System.out.println("Error getting total stats: " + e.getMessage());
        }

        // ---- Pending orders count ----
        String sql2 = "SELECT COUNT(*) AS pending FROM orders WHERE status = 'PENDING'";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql2);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                pendingOrders = rs.getInt("pending");
            }
        } catch (SQLException e) {
            System.out.println("Error getting pending count: " + e.getMessage());
        }

        return new DashboardStats(totalOrders, pendingOrders, totalRevenue);
    }
}
