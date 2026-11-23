<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!--
  Q3 TASK 3 — CHECKOUT CONFIRMATION (Final step)
  Name, delivery slot, aur cart total dikhao
-->
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>TiffinBox - Order Confirmed!</title>
    <style>
        body { font-family: 'Segoe UI', Arial, sans-serif; max-width: 500px; margin: 50px auto; background: #f5f5f5; padding: 20px; }
        h1 { color: #2e7d32; }
        .box { background: white; padding: 25px; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }
        .detail { padding: 10px 0; border-bottom: 1px solid #eee; font-size: 16px; }
        .detail b { display: inline-block; width: 140px; }
        .total { background: #e8f5e9; padding: 15px; border-radius: 5px; margin-top: 15px; font-size: 20px; font-weight: bold; text-align: center; }
        .btn { display: inline-block; padding: 10px 20px; background: #e65100; color: white; text-decoration: none; border-radius: 5px; margin-top: 20px; }
    </style>
</head>
<body>

    <h1>✅ Order Confirmed!</h1>

    <%
        String customerName = (String) request.getAttribute("customerName");
        String slot         = (String) request.getAttribute("slot");
        Double cartTotal    = (Double) request.getAttribute("cartTotal");
    %>

    <div class="box">
        <div class="detail">
            <b>👤 Name:</b> <%= customerName %>
        </div>
        <div class="detail">
            <b>🕐 Delivery Slot:</b> <%= slot %>
        </div>
        <div class="total">
            🧾 Cart Total: &#8377;<%= cartTotal %>
        </div>
    </div>

    <p style="color: #777;">Thank you for ordering with TiffinBox! 🍱</p>

    <a class="btn" href="<%= response.encodeURL("plans") %>">← Back to Plans</a>

</body>
</html>
