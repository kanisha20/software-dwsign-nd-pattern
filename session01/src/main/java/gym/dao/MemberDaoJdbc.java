package gym.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import gym.db.ConnectionPool;

public class MemberDaoJdbc implements MemberDao {

    public void addMember(Member m) {
        Connection con = ConnectionPool.getInstance().borrowConnection();

        if (con == null) {
            throw new RuntimeException("No connection available");
        }

        try {
            String sql = "INSERT INTO members (member_name, city, plan_months, fee) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, m.getMemberName());
            ps.setString(2, m.getCity());
            ps.setInt(3, m.getPlanMonths());
            ps.setDouble(4, m.getFee());

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionPool.getInstance().returnConnection(con);
        }
    }

    public List<Member> findAll() {
        return findByCity(null);
    }

    public List<Member> findByCity(String city) {
        List<Member> list = new ArrayList<Member>();
        Connection con = ConnectionPool.getInstance().borrowConnection();

        if (con == null) {
            throw new RuntimeException("No connection available");
        }

        try {
            String sql;
            if (city == null) {
                sql = "SELECT * FROM members";
            } else {
                sql = "SELECT * FROM members WHERE city = ?";
            }

            PreparedStatement ps = con.prepareStatement(sql);
            if (city != null) {
                ps.setString(1, city);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Member m = new Member(
                    rs.getInt("member_id"),
                    rs.getString("member_name"),
                    rs.getString("city"),
                    rs.getInt("plan_months"),
                    rs.getDouble("fee")
                );
                list.add(m);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionPool.getInstance().returnConnection(con);
        }

        return list;
    }
}
