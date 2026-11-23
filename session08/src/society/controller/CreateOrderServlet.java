package society.controller;

import society.service.RazorpayOrderService;

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
 *  TASK 1: CreateOrderServlet
 * ============================================================
 *  URL: /pay/createOrder?flatNo=A-101
 *  Looks up the maintenance due for flatNo and prints the created order id.
 * ============================================================
 */
@WebServlet("/pay/createOrder")
public class CreateOrderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String flatNo = request.getParameter("flatNo");
        if (flatNo == null || flatNo.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("Error: Missing required query parameter 'flatNo'");
            return;
        }

        String jdbcUrl = getServletContext().getInitParameter("jdbcUrl");
        String dbUser  = getServletContext().getInitParameter("dbUser");
        String dbPass  = getServletContext().getInitParameter("dbPassword");

        double amountDue = 0;
        boolean found = false;

        try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPass);
             PreparedStatement ps = conn.prepareStatement("SELECT amount FROM maintenance_dues WHERE flat_no = ?")) {
            ps.setString(1, flatNo.trim());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    amountDue = rs.getDouble("amount");
                    found = true;
                }
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("Database Error: " + e.getMessage());
            return;
        }

        if (!found) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            out.println("Error: No maintenance dues found for Flat No: " + flatNo);
            return;
        }

        // Convert Rupees to Paise
        int amountPaise = (int) Math.round(amountDue * 100);
        String receipt = "rcpt_" + flatNo.replace("-", "_") + "_" + System.currentTimeMillis();

        try {
            String orderId = RazorpayOrderService.createOrder(amountPaise, receipt);
            out.println("Flat No: " + flatNo);
            out.println("Due Amount: Rs. " + amountDue);
            out.println("Created Razorpay Order ID: " + orderId);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("Razorpay Error: " + e.getMessage());
        }
    }
}
