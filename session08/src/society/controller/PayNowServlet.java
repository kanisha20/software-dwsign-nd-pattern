package society.controller;

import society.model.MaintenanceDue;
import society.service.RazorpayOrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * ============================================================
 *  TASK 2: PayNowServlet
 * ============================================================
 *  URL: /pay/start?flatNo=A-101
 *  - Looks up due for flatNo.
 *  - Creates Razorpay order.
 *  - Forwards to payNow.jsp with keyId, amountPaise, orderId, and flat details.
 *  Constraint: Key Secret is NEVER passed to the JSP!
 * ============================================================
 */
@WebServlet("/pay/start")
public class PayNowServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String flatNo = request.getParameter("flatNo");
        if (flatNo == null || flatNo.trim().isEmpty()) {
            flatNo = "A-101"; // default demo flat
        }
        flatNo = flatNo.trim();

        String jdbcUrl = getServletContext().getInitParameter("jdbcUrl");
        String dbUser  = getServletContext().getInitParameter("dbUser");
        String dbPass  = getServletContext().getInitParameter("dbPassword");

        MaintenanceDue due = null;

        try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPass);
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM maintenance_dues WHERE flat_no = ?")) {
            ps.setString(1, flatNo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    due = new MaintenanceDue(
                        rs.getString("flat_no"),
                        rs.getString("resident_name"),
                        rs.getString("month"),
                        rs.getDouble("amount")
                    );
                }
            }
        } catch (Exception e) {
            request.setAttribute("error", "Database Error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/receipt.jsp").forward(request, response);
            return;
        }

        if (due == null) {
            request.setAttribute("error", "No dues found for Flat: " + flatNo);
            request.getRequestDispatcher("/WEB-INF/views/receipt.jsp").forward(request, response);
            return;
        }

        // Amount in Paise
        int amountPaise = (int) Math.round(due.getAmount() * 100);
        String receipt = "rcpt_" + due.getFlatNo() + "_" + System.currentTimeMillis();

        String orderId;
        try {
            orderId = RazorpayOrderService.createOrder(amountPaise, receipt);
        } catch (Exception e) {
            orderId = "order_test_" + System.currentTimeMillis();
        }

        // Read public key ID (Safe for frontend JSP, Secret stays on server only)
        String keyId = System.getenv("RAZORPAY_KEY_ID");
        if (keyId == null || keyId.trim().isEmpty()) {
            keyId = "rzp_test_1DP5mmOlF5G5ag"; // Razorpay public test key demo
        }

        request.setAttribute("due", due);
        request.setAttribute("keyId", keyId);
        request.setAttribute("orderId", orderId);
        request.setAttribute("amountPaise", amountPaise);

        request.getRequestDispatcher("/WEB-INF/views/payNow.jsp").forward(request, response);
    }
}
