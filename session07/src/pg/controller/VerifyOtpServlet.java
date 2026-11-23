package pg.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * ============================================================
 *  TASK 3: VerifyOtpServlet
 * ============================================================
 *  URL: /owner/verifyOtp
 *  - Compares entered OTP with session OTP.
 *  - Checks expiry: System.currentTimeMillis() > otpExpiryTime.
 *  - Blocks login after 3 wrong attempts!
 *  - On SUCCESS:
 *      1. request.changeSessionId()
 *      2. Stores `ownerEmail` in session.
 *      3. Clears temporary OTP session attributes.
 *      4. Redirects to /ownerHome.jsp.
 * ============================================================
 */
@WebServlet("/owner/verifyOtp")
public class VerifyOtpServlet extends HttpServlet {

    private static final int MAX_ATTEMPTS = 3;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/owner/sendOtp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("generatedOtp") == null) {
            request.setAttribute("error", "No active OTP session found. Please enter your email again.");
            request.getRequestDispatcher("/WEB-INF/views/ownerLogin.jsp").forward(request, response);
            return;
        }

        // Check if already blocked
        Boolean isBlocked = (Boolean) session.getAttribute("loginBlocked");
        if (Boolean.TRUE.equals(isBlocked)) {
            request.setAttribute("error", "Account access locked due to 3 consecutive failed OTP attempts. Please restart login.");
            request.getRequestDispatcher("/WEB-INF/views/ownerLogin.jsp").forward(request, response);
            return;
        }

        String enteredOtp = request.getParameter("otp");
        String storedOtp = (String) session.getAttribute("generatedOtp");
        Long expiryTime = (Long) session.getAttribute("otpExpiryTime");
        Integer attempts = (Integer) session.getAttribute("otpFailedAttempts");
        if (attempts == null) attempts = 0;

        String ownerEmail = (String) session.getAttribute("pendingOwnerEmail");
        String ownerName = (String) session.getAttribute("pendingOwnerName");

        // 1. Expiry Check
        if (expiryTime != null && System.currentTimeMillis() > expiryTime) {
            // Clean expired OTP
            session.removeAttribute("generatedOtp");
            session.removeAttribute("otpExpiryTime");
            request.setAttribute("error", "OTP has expired! Please request a new one.");
            request.getRequestDispatcher("/WEB-INF/views/ownerLogin.jsp").forward(request, response);
            return;
        }

        // 2. Comparison Check
        if (enteredOtp != null && enteredOtp.trim().equals(storedOtp)) {
            // ================== SUCCESS ==================
            // Constraint: request.changeSessionId()
            request.changeSessionId();

            // Store logged in owner session
            session.setAttribute("ownerEmail", ownerEmail);
            session.setAttribute("ownerName", ownerName);

            // Constraint: remove the OTP attributes
            session.removeAttribute("generatedOtp");
            session.removeAttribute("otpExpiryTime");
            session.removeAttribute("otpFailedAttempts");
            session.removeAttribute("pendingOwnerEmail");
            session.removeAttribute("pendingOwnerName");
            session.removeAttribute("loginBlocked");

            // Redirect to owner home
            response.sendRedirect(request.getContextPath() + "/ownerHome.jsp");

        } else {
            // ================== WRONG OTP ==================
            attempts++;
            session.setAttribute("otpFailedAttempts", attempts);

            if (attempts >= MAX_ATTEMPTS) {
                // Constraint: Block login after 3 wrong attempts
                session.setAttribute("loginBlocked", true);
                session.removeAttribute("generatedOtp");
                session.removeAttribute("otpExpiryTime");

                request.setAttribute("error", "Maximum limit exceeded! Your login is blocked after 3 wrong attempts.");
                request.getRequestDispatcher("/WEB-INF/views/ownerLogin.jsp").forward(request, response);
            } else {
                int remaining = MAX_ATTEMPTS - attempts;
                request.setAttribute("error", "Invalid OTP entered! Attempts remaining: " + remaining);
                request.setAttribute("email", ownerEmail);
                request.getRequestDispatcher("/WEB-INF/views/verifyOtp.jsp").forward(request, response);
            }
        }
    }
}
