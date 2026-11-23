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
 *  TASK 2: ResidentRegisterServlet
 * ============================================================
 *  URL: /resident/register (POST)
 *  - Takes: `flatNo`, `residentName`, `email`
 *  - Validations:
 *      1. residentName not empty
 *      2. email contains '@'
 *      3. flat not already taken
 *  - Inserts row into `residents` table.
 *  - Returns hand-built JSON:
 *      {"ok":true}
 *      or
 *      {"ok":false,"message":"..."}
 * ============================================================
 */
@WebServlet("/resident/register")
public class ResidentRegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String flatNo = request.getParameter("flatNo");
        String residentName = request.getParameter("residentName");
        String email = request.getParameter("email");

        // 1. Validation: Name
        if (residentName == null || residentName.trim().isEmpty()) {
            out.print("{\"ok\":false,\"message\":\"Resident name cannot be empty.\"}");
            return;
        }

        // 2. Validation: Email contains '@'
        if (email == null || !email.contains("@") || email.indexOf('@') == 0 || email.endsWith("@")) {
            out.print("{\"ok\":false,\"message\":\"Invalid email address! Must contain '@'.\"}");
            return;
        }

        // 3. Validation: Flat number
        if (flatNo == null || flatNo.trim().isEmpty()) {
            out.print("{\"ok\":false,\"message\":\"Flat number cannot be empty.\"}");
            return;
        }

        flatNo = flatNo.trim().toUpperCase();
        residentName = residentName.trim();
        email = email.trim();

        String jdbcUrl = getServletContext().getInitParameter("jdbcUrl");
        String dbUser  = getServletContext().getInitParameter("dbUser");
        String dbPass  = getServletContext().getInitParameter("dbPassword");

        try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPass)) {

            // Check if flat already taken
            try (PreparedStatement checkPs = conn.prepareStatement("SELECT 1 FROM residents WHERE UPPER(flat_no) = ?")) {
                checkPs.setString(1, flatNo);
                try (ResultSet rs = checkPs.executeQuery()) {
                    if (rs.next()) {
                        out.print("{\"ok\":false,\"message\":\"Flat " + flatNo + " is already registered by another resident!\"}");
                        return;
                    }
                }
            }

            // Insert new resident
            try (PreparedStatement insertPs = conn.prepareStatement(
                    "INSERT INTO residents (flat_no, resident_name, email) VALUES (?, ?, ?)")) {
                insertPs.setString(1, flatNo);
                insertPs.setString(2, residentName);
                insertPs.setString(3, email);
                insertPs.executeUpdate();
            }

            // Hand-crafted JSON success (Constraint: No JSON Library)
            out.print("{\"ok\":true}");

        } catch (Exception e) {
            out.print("{\"ok\":false,\"message\":\"Database error: " + escapeJson(e.getMessage()) + "\"}");
        }
    }

    private String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\"", "\\\"");
    }
}
