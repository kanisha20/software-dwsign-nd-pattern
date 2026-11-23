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
 *  TASK 1: CheckFlatServlet
 * ============================================================
 *  URL: /checkFlat?flatNo=B-204
 *  - Checks if flatNo already exists in `residents` table.
 *  - Returns hand-built JSON: {"available":true} or {"available":false}
 *  - CONSTRAINT: NO JSON library used! Handcrafted JSON string.
 * ============================================================
 */
@WebServlet("/checkFlat")
public class CheckFlatServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        PrintWriter out = response.getWriter();

        String flatNo = request.getParameter("flatNo");
        if (flatNo == null || flatNo.trim().isEmpty()) {
            out.print("{\"available\":false,\"message\":\"Flat number cannot be blank\"}");
            return;
        }
        flatNo = flatNo.trim().toUpperCase();

        String jdbcUrl = getServletContext().getInitParameter("jdbcUrl");
        String dbUser  = getServletContext().getInitParameter("dbUser");
        String dbPass  = getServletContext().getInitParameter("dbPassword");

        boolean exists = false;
        try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPass);
             PreparedStatement ps = conn.prepareStatement("SELECT 1 FROM residents WHERE UPPER(flat_no) = ?")) {

            ps.setString(1, flatNo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    exists = true;
                }
            }
        } catch (Exception e) {
            out.print("{\"available\":false,\"error\":\"" + escapeJson(e.getMessage()) + "\"}");
            return;
        }

        // Hand-built JSON response (Constraint: No JSON Library)
        if (exists) {
            out.print("{\"available\":false}");
        } else {
            out.print("{\"available\":true}");
        }
    }

    private String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\"", "\\\"");
    }
}
