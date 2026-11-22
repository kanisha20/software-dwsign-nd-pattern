package tiffin.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import tiffin.model.TiffinPlan;

public class TiffinPlanDaoImpl implements TiffinPlanDao {

    private String jdbcUrl;
    private String dbUser;
    private String dbPassword;

    public TiffinPlanDaoImpl(String jdbcUrl, String dbUser,
                             String dbPassword) {
        this.jdbcUrl = jdbcUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    private Connection getConnection() throws Exception {
        return DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
    }

    public List<TiffinPlan> findAll() throws Exception {
        List<TiffinPlan> list = new ArrayList<TiffinPlan>();
        String sql = "SELECT * FROM tiffin_plans";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                TiffinPlan plan = new TiffinPlan(
                    rs.getInt("planId"),
                    rs.getString("planName"),
                    rs.getDouble("pricePerDay"),
                    rs.getString("city")
                );
                list.add(plan);
            }
        }
        return list;
    }

    public List<TiffinPlan> findByCity(String city) throws Exception {
        List<TiffinPlan> list = new ArrayList<TiffinPlan>();
        String sql = "SELECT * FROM tiffin_plans WHERE city = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, city);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    TiffinPlan plan = new TiffinPlan(
                        rs.getInt("planId"),
                        rs.getString("planName"),
                        rs.getDouble("pricePerDay"),
                        rs.getString("city")
                    );
                    list.add(plan);
                }
            }
        }
        return list;
    }

    public void addPlan(TiffinPlan plan) throws Exception {
        String sql = "INSERT INTO tiffin_plans "
                   + "(planName, pricePerDay, city) VALUES (?, ?, ?)";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, plan.getPlanName());
            ps.setDouble(2, plan.getPricePerDay());
            ps.setString(3, plan.getCity());
            ps.executeUpdate();
        }
    }
}
