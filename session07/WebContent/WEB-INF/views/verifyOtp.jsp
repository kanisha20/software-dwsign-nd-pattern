<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!--
  TASK 2 & 3: verifyOtp.jsp (Step 2)
  Form inputs 6-digit OTP, shows countdown info, and handles failed attempt feedback.
-->
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>PgFinder - Verify OTP (Step 2)</title>
    <style>
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: #eef2f7; max-width: 480px; margin: 60px auto; padding: 20px; }
        .card { background: white; padding: 35px; border-radius: 10px; box-shadow: 0 4px 15px rgba(0,0,0,0.08); }
        h2 { color: #0056b3; margin-top: 0; text-align: center; }
        .subtitle { text-align: center; color: #666; font-size: 14px; margin-bottom: 25px; }
        label { display: block; margin-top: 15px; font-weight: bold; color: #333; text-align: center; }
        input[type="text"] { width: 100%; padding: 12px; margin-top: 8px; border: 2px solid #0056b3; border-radius: 6px; box-sizing: border-box; font-size: 24px; text-align: center; letter-spacing: 8px; font-weight: bold; }
        button { background: #28a745; color: white; border: none; padding: 12px 20px; border-radius: 6px; cursor: pointer; font-size: 16px; margin-top: 25px; width: 100%; font-weight: bold; }
        button:hover { background: #218838; }
        .alert-info { background: #d1ecf1; color: #0c5460; border: 1px solid #bee5eb; padding: 12px; border-radius: 6px; margin-bottom: 20px; font-size: 14px; }
        .alert-danger { background: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; padding: 12px; border-radius: 6px; margin-bottom: 20px; font-size: 14px; }
        .cancel-link { display: block; text-align: center; margin-top: 20px; color: #6c757d; text-decoration: none; font-size: 14px; }
        .cancel-link:hover { text-decoration: underline; }
        .simulation-box { margin-top: 20px; background: #fff3cd; border: 1px solid #ffeeba; border-radius: 6px; padding: 10px; font-size: 12px; color: #856404; text-align: center; }
    </style>
</head>
<body>

    <div class="card">
        <h2>🔑 Verify OTP</h2>
        <p class="subtitle">Step 2: Enter the 6-digit verification code</p>

        <% if (request.getAttribute("infoMessage") != null) { %>
            <div class="alert-info">
                ℹ️ <%= request.getAttribute("infoMessage") %>
            </div>
        <% } %>

        <% if (request.getAttribute("error") != null) { %>
            <div class="alert-danger">
                ❌ <strong><%= request.getAttribute("error") %></strong>
            </div>
        <% } %>

        <form method="post" action="${pageContext.request.contextPath}/owner/verifyOtp">
            <label for="otp">Enter 6-Digit OTP:</label>
            <input type="text" id="otp" name="otp" maxlength="6" pattern="\\d{6}" placeholder="------" autocomplete="off" autofocus required>

            <button type="submit">✅ Verify & Login</button>
        </form>

        <%
            // Developer assist: if in development / test mode without Gmail credentials
            String generatedOtp = (String) session.getAttribute("generatedOtp");
            if (generatedOtp != null) {
        %>
            <div class="simulation-box">
                🛠️ <strong>Console Simulation / Demo Mode:</strong><br>
                Generated OTP: <strong style="font-size: 16px; color: #d63384;"><%= generatedOtp %></strong>
            </div>
        <% } %>

        <a class="cancel-link" href="${pageContext.request.contextPath}/owner/sendOtp">← Re-enter different email</a>
    </div>

</body>
</html>
