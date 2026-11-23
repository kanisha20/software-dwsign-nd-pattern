package tiffin.dao;

import tiffin.model.TiffinPlan;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================
 *  DAO IMPLEMENTATION — Updated for Q3
 *  Naya method: findById(int planId)
 *  CONSTRAINT: Koi servlet class import NAHI hai!
 * ============================================================
 */
public class TiffinPlanDaoImpl implements TiffinPlanDao {

    private String jdbcUrl;
    private String dbUser;
    private String dbPassword;

    public TiffinPlanDaoImpl(String jdbcUrl, String dbUser, String dbPassword) {
        this.jdbcUrl    = jdbcUrl;
        this.dbUser     = dbUser;
        this.dbPassword = dbPassword;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
    }

    // -------- findAll --------
    @Override
    public List<TiffinPlan> findAll() {
        List<TiffinPlan> plans = new ArrayList<>();
        Connection conn = null; PreparedStatement ps = null; ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement("SELECT * FROM tiffin_plans ORDER BY city, price_per_day");
            rs = ps.executeQuery();
            while (rs.next()) {
                plans.add(new TiffinPlan(
                    rs.getInt("plan_id"), rs.getString("plan_name"),
                    rs.getDouble("price_per_day"), rs.getString("city")));
            }
        } catch (SQLException e) {
            System.out.println("Error in findAll: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); }   catch (SQLException ignored) {}
            try { if (ps != null) ps.close(); }   catch (SQLException ignored) {}
            try { if (conn != null) conn.close(); } catch (SQLException ignored) {}
        }
        return plans;
    }

    // -------- findByCity --------
    @Override
    public List<TiffinPlan> findByCity(String city) {
        List<TiffinPlan> plans = new ArrayList<>();
        Connection conn = null; PreparedStatement ps = null; ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement("SELECT * FROM tiffin_plans WHERE city = ? ORDER BY price_per_day");
            ps.setString(1, city);
            rs = ps.executeQuery();
            while (rs.next()) {
                plans.add(new TiffinPlan(
                    rs.getInt("plan_id"), rs.getString("plan_name"),
                    rs.getDouble("price_per_day"), rs.getString("city")));
            }
        } catch (SQLException e) {
            System.out.println("Error in findByCity: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); }   catch (SQLException ignored) {}
            try { if (ps != null) ps.close(); }   catch (SQLException ignored) {}
            try { if (conn != null) conn.close(); } catch (SQLException ignored) {}
        }
        return plans;
    }

    // -------- addPlan --------
    @Override
    public void addPlan(TiffinPlan plan) {
        Connection conn = null; PreparedStatement ps = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(
                "INSERT INTO tiffin_plans (plan_name, price_per_day, city) VALUES (?, ?, ?)");
            ps.setString(1, plan.getPlanName());
            ps.setDouble(2, plan.getPricePerDay());
            ps.setString(3, plan.getCity());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error in addPlan: " + e.getMessage());
        } finally {
            try { if (ps != null) ps.close(); }   catch (SQLException ignored) {}
            try { if (conn != null) conn.close(); } catch (SQLException ignored) {}
        }
    }

    // ================================================================
    //  NEW — findById(int planId) [Q3 Task 1]
    //  Cart mein planId stored hai, usse plan details chahiye
    // ================================================================
    @Override
    public TiffinPlan findById(int planId) {
        Connection conn = null; PreparedStatement ps = null; ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement("SELECT * FROM tiffin_plans WHERE plan_id = ?");
            ps.setInt(1, planId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return new TiffinPlan(
                    rs.getInt("plan_id"), rs.getString("plan_name"),
                    rs.getDouble("price_per_day"), rs.getString("city"));
            }
        } catch (SQLException e) {
            System.out.println("Error in findById: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); }   catch (SQLException ignored) {}
            try { if (ps != null) ps.close(); }   catch (SQLException ignored) {}
            try { if (conn != null) conn.close(); } catch (SQLException ignored) {}
        }
        return null;  // plan nahi mila
    }
}
