<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!--
  Q3 TASK 4 — STAFF LOGIN PAGE
  Username/password enter karo → StaffLoginServlet check karega
-->
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>TiffinBox - Staff Login</title>
    <style>
        body { font-family: 'Segoe UI', Arial, sans-serif; max-width: 400px; margin: 80px auto; background: #f5f5f5; padding: 20px; }
        h1 { color: #1565c0; text-align: center; }
        .box { background: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        label { display: block; margin-top: 15px; font-weight: bold; }
        input[type="text"], input[type="password"] { width: 100%; padding: 10px; margin-top: 5px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; font-size: 14px; }
        .btn { display: block; width: 100%; padding: 12px; margin-top: 20px; background: #1565c0; color: white; border: none; border-radius: 5px; cursor: pointer; font-size: 16px; }
        .btn:hover { background: #0d47a1; }
        .error { background: #ffcdd2; color: #c62828; padding: 12px; border-radius: 5px; margin-bottom: 15px; font-weight: bold; text-align: center; }
        .back { display: block; text-align: center; margin-top: 15px; color: #e65100; }
    </style>
</head>
<body>

    <h1>🔐 Staff Login</h1>

    <%
        String error = (String) request.getAttribute("error");
        if (error != null) {
    %>
        <div class="error">⚠️ <%= error %></div>
    <% } %>

    <div class="box">
        <form method="post" action="<%= request.getContextPath() %>/staff/login">

            <label>Username:</label>
            <input type="text" name="username" placeholder="Enter username" required>

            <label>Password:</label>
            <input type="password" name="password" placeholder="Enter password" required>

            <button type="submit" class="btn">🔑 Login</button>
        </form>
    </div>

    <a class="back" href="<%= request.getContextPath() %>/plans">← Back to Plans</a>

</body>
</html>
