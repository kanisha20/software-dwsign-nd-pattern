<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="tiffin.model.TiffinPlan" %>
<!--
  Q3 TASK 1 — CART.JSP
  Cart ke items dikhao: plan name, quantity, line total, grand total
-->
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>TiffinBox - Your Cart</title>
    <style>
        body { font-family: 'Segoe UI', Arial, sans-serif; max-width: 750px; margin: 30px auto; background: #f5f5f5; padding: 20px; }
        h1 { color: #e65100; }
        table { width: 100%; border-collapse: collapse; background: white; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }
        th { background: #2e7d32; color: white; padding: 12px; text-align: left; }
        td { padding: 10px 12px; border-bottom: 1px solid #ddd; }
        .total-row { background: #e8f5e9; font-weight: bold; font-size: 18px; }
        .btn { display: inline-block; padding: 10px 20px; background: #e65100; color: white; text-decoration: none; border-radius: 5px; margin: 10px 5px; }
        .btn:hover { background: #bf360c; }
        .btn-green { background: #2e7d32; }
        .empty { text-align: center; padding: 40px; color: #777; font-size: 18px; }
        .nav { background: #333; padding: 10px 15px; border-radius: 5px; margin-bottom: 15px; }
        .nav a { color: white; margin-right: 15px; text-decoration: none; }
    </style>
</head>
<body>

    <div class="nav">
        <a href="plans">🏠 Plans</a>
        <a href="cart">🛒 Cart</a>
        <a href="checkout?step=1">📦 Checkout</a>
    </div>

    <h1>🛒 Your Cart</h1>

    <%
        // CartServlet ne yeh set kiya hai
        List<Object[]> cartItems = (List<Object[]>) request.getAttribute("cartItems");
        Double grandTotal = (Double) request.getAttribute("grandTotal");
    %>

    <% if (cartItems == null || cartItems.isEmpty()) { %>
        <div class="empty">
            <p>Cart khaali hai! 😔</p>
            <a class="btn" href="plans">← Browse Plans</a>
        </div>
    <% } else { %>
        <table>
            <tr>
                <th>Plan Name</th>
                <th>City</th>
                <th>Price/Day</th>
                <th>Quantity</th>
                <th>Line Total</th>
            </tr>
            <%
                // Har cart item = Object[] { TiffinPlan, qty(int), lineTotal(double) }
                for (Object[] item : cartItems) {
                    TiffinPlan plan = (TiffinPlan) item[0];
                    int qty         = (Integer) item[1];
                    double lineTotal = (Double) item[2];
            %>
            <tr>
                <td><%= plan.getPlanName() %></td>
                <td><%= plan.getCity() %></td>
                <td>&#8377;<%= plan.getPricePerDay() %></td>
                <td><%= qty %> day(s)</td>
                <td><b>&#8377;<%= lineTotal %></b></td>
            </tr>
            <% } %>
            <!-- ======== Grand Total Row ======== -->
            <tr class="total-row">
                <td colspan="4" style="text-align:right;">GRAND TOTAL:</td>
                <td>&#8377;<%= grandTotal %></td>
            </tr>
        </table>

        <br>
        <a class="btn" href="plans">← Add More</a>
        <a class="btn btn-green" href="checkout?step=1">📦 Proceed to Checkout</a>
    <% } %>

</body>
</html>
