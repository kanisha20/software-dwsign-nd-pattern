package society.controller;

import society.service.SignatureVerificationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * ============================================================
 *  TASK 2 & 3: VerifyPaymentServlet
 * ============================================================
 *  URL: /pay/verify
 *  - Receives:
 *      razorpay_payment_id
 *      razorpay_order_id
 *      razorpay_signature
 *      flatNo
 *      amount
 *  - Verifies HMAC-SHA256 signature using key secret on server.
 *  - If VALID:
 *      Saves row in `payments` table (status 'PAID').
 *      Shows clean receipt.
 *  - If INVALID / TAMPERED:
 *      Rejects with 'Payment could not be verified'.
 * ============================================================
 */
@WebServlet("/pay/verify")
public class VerifyPaymentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String paymentId = request.getParameter("razorpay_payment_id");
        String orderId   = request.getParameter("razorpay_order_id");
        String signature = request.getParameter("razorpay_signature");
        String flatNo    = request.getParameter("flatNo");
        String amountStr = request.getParameter("amount");

        double amount = 0;
        if (amountStr != null) {
            try { amount = Double.parseDouble(amountStr); } catch (Exception ignored) {}
        }

        System.out.println(">>> Received Payment Verification Request <<<");
        System.out.println("Payment ID : " + paymentId);
        System.out.println("Order ID   : " + orderId);
        System.out.println("Signature  : " + signature);
        System.out.println("Flat No    : " + flatNo);

        String keySecret = System.getenv("RAZORPAY_KEY_SECRET");
        if (keySecret == null || keySecret.trim().isEmpty()) {
            keySecret = "sample_test_secret_key";
        }

        // Server-Side Verification (Task 3)
        boolean isValid = SignatureVerificationService.verifySignature(orderId, paymentId, signature, keySecret);

        if (isValid) {
            // Save to database
            String jdbcUrl = getServletContext().getInitParameter("jdbcUrl");
            String dbUser  = getServletContext().getInitParameter("dbUser");
            String dbPass  = getServletContext().getInitParameter("dbPassword");

            try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPass);
                 PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO payments (flat_no, order_id, payment_id, amount, status) VALUES (?, ?, ?, ?, 'PAID')")) {

                ps.setString(1, flatNo != null ? flatNo : "A-101");
                ps.setString(2, orderId);
                ps.setString(3, paymentId);
                ps.setDouble(4, amount);
                ps.executeUpdate();

            } catch (Exception e) {
                System.err.println("[VerifyPaymentServlet DB Error]: " + e.getMessage());
            }

            request.setAttribute("paymentSuccess", true);
            request.setAttribute("flatNo", flatNo);
            request.setAttribute("orderId", orderId);
            request.setAttribute("paymentId", paymentId);
            request.setAttribute("amount", amount);

        } else {
            // Rejection condition: Task 3
            request.setAttribute("paymentSuccess", false);
            request.setAttribute("errorMessage", "Payment could not be verified");
        }

        request.getRequestDispatcher("/WEB-INF/views/receipt.jsp").forward(request, response);
    }
}
