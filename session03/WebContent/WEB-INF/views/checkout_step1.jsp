<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!--
  Q3 TASK 3 — CHECKOUT STEP 1: Customer name lo
  Form POST hoga /checkout pe, step=2 ke saath
  encodeURL() se URL rewriting hogi (cookies blocked pe bhi kaam karega)
-->
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>TiffinBox - Checkout Step 1</title>
    <style>
        body { font-family: 'Segoe UI', Arial, sans-serif; max-width: 500px; margin: 50px auto; background: #f5f5f5; padding: 20px; }
        h1 { color: #e65100; }
        .box { background: white; padding: 25px; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }
        label { display: block; margin-top: 15px; font-weight: bold; }
        input { width: 100%; padding: 10px; margin-top: 5px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; font-size: 14px; }
        .btn { display: inline-block; padding: 12px 25px; margin-top: 20px; background: #e65100; color: white; border: none; border-radius: 5px; cursor: pointer; font-size: 16px; }
        .btn:hover { background: #bf360c; }
        .error { background: #ffcdd2; color: #c62828; padding: 12px; border-radius: 5px; margin-bottom: 15px; font-weight: bold; }
        .step { color: #777; font-size: 14px; }
    </style>
</head>
<body>

    <h1>📦 Checkout</h1>
    <p class="step">Step 1 of 2 — Enter your name</p>

    <!-- Error message (agar validation fail ho) -->
    <%
        String error = (String) request.getAttribute("error");
        if (error != null) {
    %>
        <div class="error">⚠️ <%= error %></div>
    <% } %>

    <div class="box">
        <!--
          CONSTRAINT: response.encodeURL() se wrap karo
          Cookies blocked hone pe URL mein ;jsessionid= add hoga
        -->
        <form method="post" action="<%= response.encodeURL("checkout") %>">
            <!-- Hidden field: next step = 2 -->
            <input type="hidden" name="step" value="2">

            <label>Your Name:</label>
            <input type="text" name="customerName" placeholder="e.g. Kavya Patel"
                   maxlength="40" required>
            <p style="color:#777; font-size:12px;">Max 40 characters</p>

            <button type="submit" class="btn">Next → Delivery Slot</button>
        </form>
    </div>

    <br>
    <a href="<%= response.encodeURL("cart") %>">← Back to Cart</a>

</body>
</html>
