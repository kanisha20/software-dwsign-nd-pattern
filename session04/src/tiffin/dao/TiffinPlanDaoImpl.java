package tiffin.dao;

import tiffin.model.TiffinPlan;
import java.sql.*; import java.util.ArrayList; import java.util.List;

/** TiffinPlanDaoImpl — same as Q3 */
public class TiffinPlanDaoImpl implements TiffinPlanDao {
    private String jdbcUrl, dbUser, dbPassword;
    public TiffinPlanDaoImpl(String u, String user, String pass) { jdbcUrl=u; dbUser=user; dbPassword=pass; }
    private Connection getConnection() throws SQLException { return DriverManager.getConnection(jdbcUrl, dbUser, dbPassword); }

    @Override public List<TiffinPlan> findAll() {
        List<TiffinPlan> list = new ArrayList<>();
        try (Connection c=getConnection(); PreparedStatement ps=c.prepareStatement("SELECT * FROM tiffin_plans ORDER BY city,price_per_day"); ResultSet rs=ps.executeQuery()) {
            while(rs.next()) list.add(new TiffinPlan(rs.getInt("plan_id"),rs.getString("plan_name"),rs.getDouble("price_per_day"),rs.getString("city")));
        } catch(SQLException e){ System.out.println("Error: "+e.getMessage()); }
        return list;
    }
    @Override public List<TiffinPlan> findByCity(String city) {
        List<TiffinPlan> list = new ArrayList<>();
        try (Connection c=getConnection(); PreparedStatement ps=c.prepareStatement("SELECT * FROM tiffin_plans WHERE city=? ORDER BY price_per_day")) {
            ps.setString(1,city); ResultSet rs=ps.executeQuery();
            while(rs.next()) list.add(new TiffinPlan(rs.getInt("plan_id"),rs.getString("plan_name"),rs.getDouble("price_per_day"),rs.getString("city")));
        } catch(SQLException e){ System.out.println("Error: "+e.getMessage()); }
        return list;
    }
    @Override public void addPlan(TiffinPlan p) {
        try (Connection c=getConnection(); PreparedStatement ps=c.prepareStatement("INSERT INTO tiffin_plans(plan_name,price_per_day,city) VALUES(?,?,?)")) {
            ps.setString(1,p.getPlanName()); ps.setDouble(2,p.getPricePerDay()); ps.setString(3,p.getCity()); ps.executeUpdate();
        } catch(SQLException e){ System.out.println("Error: "+e.getMessage()); }
    }
    @Override public TiffinPlan findById(int id) {
        try (Connection c=getConnection(); PreparedStatement ps=c.prepareStatement("SELECT * FROM tiffin_plans WHERE plan_id=?")) {
            ps.setInt(1,id); ResultSet rs=ps.executeQuery();
            if(rs.next()) return new TiffinPlan(rs.getInt("plan_id"),rs.getString("plan_name"),rs.getDouble("price_per_day"),rs.getString("city"));
        } catch(SQLException e){ System.out.println("Error: "+e.getMessage()); }
        return null;
    }
}
