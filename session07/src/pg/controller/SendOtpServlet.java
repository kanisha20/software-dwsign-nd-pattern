package pg.controller;

import pg.mail.PgMailer;
import pg.security.OtpGenerator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * ============================================================
 *  TASK 2: SendOtpServlet
 * ============================================================
 *  URL: /owner/sendOtp
 *  - Verifies if owner email exists in `owners` table.
 *  - If not found -> shows 'Owner not found'.
 *  - If found:
 *      1. Generates 6-digit OTP using OtpGenerator.
 *      2. Reads `otpValiditySeconds` from web.xml (default 300).
 *      3. Saves OTP, expiry timestamp, and attempts count in HttpSession.
 *      4. Sends OTP via PgMailer.sendOtpMail().
 *      5. Forwards to verifyOtp.jsp.
 * ============================================================
 */
@WebServlet("/owner/sendOtp")
public class SendOtpServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/ownerLogin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        if (email == null || email.trim().isEmpty()) {
            request.setAttribute("error", "Please enter your registered email address.");
            request.getRequestDispatcher("/WEB-INF/views/ownerLogin.jsp").forward(request, response);
            return;
        }
        email = email.trim();

        String jdbcUrl = getServletContext().getInitParameter("jdbcUrl");
        String dbUser  = getServletContext().getInitParameter("dbUser");
        String dbPass  = getServletContext().getInitParameter("dbPassword");

        boolean ownerExists = false;
        String ownerName = "";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPass);
             PreparedStatement ps = conn.prepareStatement("SELECT owner_name FROM owners WHERE email = ?")) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ownerExists = true;
                    ownerName = rs.getString("owner_name");
                }
            }
        } catch (Exception e) {
            request.setAttribute("error", "Database connection error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/ownerLogin.jsp").forward(request, response);
            return;
        }

        // Test constraint: Unknown email must show 'Owner not found'
        if (!ownerExists) {
            request.setAttribute("error", "Owner not found");
            request.getRequestDispatcher("/WEB-INF/views/ownerLogin.jsp").forward(request, response);
            return;
        }

        // 1. Generate OTP
        String otp = OtpGenerator.generateOtp();

        // 2. Read validity in seconds from context parameter (default: 300 seconds)
        String validityStr = getServletContext().getInitParameter("otpValiditySeconds");
        int validitySeconds = 300;
        if (validityStr != null) {
            try {
                validitySeconds = Integer.parseInt(validityStr.trim());
            } catch (NumberFormatException ignored) {}
        }

        long expiryTime = System.currentTimeMillis() + (validitySeconds * 1000L);

        // 3. Store in HttpSession
        HttpSession session = request.getSession(true);
        session.setAttribute("pendingOwnerEmail", email);
        session.setAttribute("pendingOwnerName", ownerName);
        session.setAttribute("generatedOtp", otp);
        session.setAttribute("otpExpiryTime", expiryTime);
        session.setAttribute("otpFailedAttempts", 0); // initialize failed attempts to 0
        session.removeAttribute("loginBlocked");

        // 4. Send email
        PgMailer.sendOtpMail(email, otp);

        // Also pass for display in simulation/demo
        request.setAttribute("email", email);
        request.setAttribute("infoMessage", "A 6-digit verification code has been sent to " + email);
        request.setAttribute("validitySeconds", validitySeconds);

        request.getRequestDispatcher("/WEB-INF/views/verifyOtp.jsp").forward(request, response);
    }
}
