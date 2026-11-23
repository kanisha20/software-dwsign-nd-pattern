package society.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * ============================================================
 *  TASK 3: DuesApiServlet
 * ============================================================
 *  URL: /api/dues?flatNo=A-101
 *  - Looks up all monthly rows for the given flatNo from `maintenance_dues`.
 *  - Returns a hand-built JSON array:
 *      [{"month":"September 2026","amount":1800.0},{"month":"October 2026","amount":1800.0}, ...]
 *  - If flat not found or 0 rows, returns empty JSON array: []
 * ============================================================
 */
@WebServlet("/api/dues")
public class DuesApiServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        PrintWriter out = response.getWriter();

        String flatNo = request.getParameter("flatNo");
        if (flatNo == null || flatNo.trim().isEmpty()) {
            out.print("[]");
            return;
        }
        flatNo = flatNo.trim().toUpperCase();

        String jdbcUrl = getServletContext().getInitParameter("jdbcUrl");
        String dbUser  = getServletContext().getInitParameter("dbUser");
        String dbPass  = getServletContext().getInitParameter("dbPassword");

        StringBuilder jsonArray = new StringBuilder("[");
        boolean first = true;

        try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPass);
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT month, amount FROM maintenance_dues WHERE UPPER(flat_no) = ? ORDER BY id ASC")) {

            ps.setString(1, flatNo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    if (!first) {
                        jsonArray.append(",");
                    }
                    String month = rs.getString("month");
                    double amount = rs.getDouble("amount");

                    jsonArray.append("{")
                             .append("\"month\":\"").append(escapeJson(month)).append("\",")
                             .append("\"amount\":").append(amount)
                             .append("}");
                    first = false;
                }
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("[]");
            return;
        }

        jsonArray.append("]");
        out.print(jsonArray.toString());
    }

    private String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\"", "\\\"");
    }
}
