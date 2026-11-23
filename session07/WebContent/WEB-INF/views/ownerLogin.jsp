<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!--
  TASK 2: ownerLogin.jsp (Step 1)
  Form asks for registered owner email to send OTP.
-->
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>PgFinder - Owner Login (Step 1)</title>
    <style>
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: #eef2f7; max-width: 480px; margin: 60px auto; padding: 20px; }
        .card { background: white; padding: 35px; border-radius: 10px; box-shadow: 0 4px 15px rgba(0,0,0,0.08); }
        h2 { color: #0056b3; margin-top: 0; text-align: center; }
        .subtitle { text-align: center; color: #666; font-size: 14px; margin-bottom: 25px; }
        label { display: block; margin-top: 15px; font-weight: bold; color: #333; }
        input[type="email"] { width: 100%; padding: 12px; margin-top: 6px; border: 1px solid #ccc; border-radius: 6px; box-sizing: border-box; font-size: 15px; }
        button { background: #0056b3; color: white; border: none; padding: 12px 20px; border-radius: 6px; cursor: pointer; font-size: 16px; margin-top: 25px; width: 100%; font-weight: bold; transition: background 0.2s; }
        button:hover { background: #004085; }
        .alert-danger { background: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; padding: 12px; border-radius: 6px; margin-bottom: 20px; font-size: 14px; }
        .test-box { background: #f8f9fa; border: 1px solid #dee2e6; border-radius: 6px; padding: 12px; margin-top: 25px; font-size: 12px; color: #495057; }
        .test-box code { color: #d63384; font-weight: bold; }
    </style>
</head>
<body>

    <div class="card">
        <h2>🔐 Owner Portal Login</h2>
        <p class="subtitle">Step 1: Enter your registered email to receive a 6-digit OTP</p>

        <% if (request.getAttribute("error") != null) { %>
            <div class="alert-danger">
                ⚠️ <strong><%= request.getAttribute("error") %></strong>
            </div>
        <% } %>

        <form method="post" action="${pageContext.request.contextPath}/owner/sendOtp">
            <label for="email">Owner Registered Email:</label>
            <input type="email" id="email" name="email" placeholder="e.g. dipak.owner@gmail.com" required>

            <button type="submit">📨 Send 6-Digit OTP</button>
        </form>

        <div class="test-box">
            <strong>Registered Test Emails in DB:</strong><br>
            • <code>dipak.owner@gmail.com</code><br>
            • <code>rameshwar.pg@gmail.com</code><br>
            • <code>kavya.hostels@gmail.com</code><br>
            <em>Try entering <code>random@xyz.com</code> to verify 'Owner not found' constraint.</em>
        </div>
    </div>

</body>
</html>
