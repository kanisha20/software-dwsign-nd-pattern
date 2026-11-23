<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!--
  Q3 TASK 3 — CHECKOUT STEP 2: Delivery slot choose karo
  customerName HIDDEN FIELD mein hai (cookies blocked pe bhi kaam karega)
  encodeURL() se URL rewriting
-->
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>TiffinBox - Checkout Step 2</title>
    <style>
        body { font-family: 'Segoe UI', Arial, sans-serif; max-width: 500px; margin: 50px auto; background: #f5f5f5; padding: 20px; }
        h1 { color: #e65100; }
        .box { background: white; padding: 25px; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }
        label { display: block; margin-top: 15px; font-weight: bold; }
        .radio-option { display: block; padding: 15px; margin: 8px 0; background: #fff3e0; border-radius: 5px; cursor: pointer; font-size: 16px; }
        .radio-option:hover { background: #ffe0b2; }
        .radio-option input { margin-right: 10px; }
        .btn { display: inline-block; padding: 12px 25px; margin-top: 20px; background: #2e7d32; color: white; border: none; border-radius: 5px; cursor: pointer; font-size: 16px; }
        .btn:hover { background: #1b5e20; }
        .error { background: #ffcdd2; color: #c62828; padding: 12px; border-radius: 5px; margin-bottom: 15px; font-weight: bold; }
        .step { color: #777; font-size: 14px; }
        .name-display { background: #e3f2fd; padding: 10px 15px; border-radius: 5px; margin-bottom: 15px; }
    </style>
</head>
<body>

    <h1>📦 Checkout</h1>
    <p class="step">Step 2 of 2 — Choose delivery slot</p>

    <%
        String error = (String) request.getAttribute("error");
        if (error != null) {
    %>
        <div class="error">⚠️ <%= error %></div>
    <% } %>

    <%
        // Controller ne customerName set kiya hai
        String customerName = (String) request.getAttribute("customerName");
    %>

    <div class="name-display">
        👤 <b>Name:</b> <%= customerName %>
    </div>

    <div class="box">
        <!-- encodeURL() se wrap karo (URL Rewriting) -->
        <form method="post" action="<%= response.encodeURL("checkout") %>">

            <!-- Hidden fields: step + customerName -->
            <input type="hidden" name="step" value="3">

            <!--
              HIDDEN FIELD — customerName yahan chhupa hai
              Browser mein inspect karke edit ho sakta hai,
              isliye server pe RE-VALIDATE karna zaroori hai! (Constraint)
            -->
            <input type="hidden" name="customerName" value="<%= customerName %>">

            <label>Choose Delivery Slot:</label>

            <label class="radio-option">
                <input type="radio" name="slot" value="Lunch 12-1" required>
                🕐 Lunch 12:00 PM - 1:00 PM
            </label>

            <label class="radio-option">
                <input type="radio" name="slot" value="Dinner 7-8">
                🌙 Dinner 7:00 PM - 8:00 PM
            </label>

            <button type="submit" class="btn">✅ Place Order</button>
        </form>
    </div>

    <br>
    <a href="<%= response.encodeURL("checkout?step=1") %>">← Back to Step 1</a>

</body>
</html>
