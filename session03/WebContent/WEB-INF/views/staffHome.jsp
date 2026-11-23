<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!--
  Q3 TASK 4 — STAFF HOME (Protected Page)
  Sirf logged-in staff access kar sakta hai.
  Logout link hai jo session.invalidate() karta hai.
-->
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>TiffinBox - Staff Home</title>
    <style>
        body { font-family: 'Segoe UI', Arial, sans-serif; max-width: 600px; margin: 50px auto; background: #f5f5f5; padding: 20px; }
        h1 { color: #2e7d32; }
        .box { background: white; padding: 25px; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }
        .info { padding: 10px 0; border-bottom: 1px solid #eee; }
        .btn { display: inline-block; padding: 10px 20px; background: #c62828; color: white; text-decoration: none; border-radius: 5px; margin-top: 15px; }
        .btn:hover { background: #b71c1c; }
        .btn-blue { background: #1565c0; }
        .btn-blue:hover { background: #0d47a1; }
    </style>
</head>
<body>

    <%
        String staffName = (String) request.getAttribute("staffName");
    %>

    <h1>🏠 Staff Dashboard</h1>

    <div class="box">
        <div class="info">
            <h2>Welcome, <%= staffName %>! 👋</h2>
            <p>You are logged in as staff.</p>
        </div>

        <div class="info">
            <p><b>Session ID:</b> <%= session.getId() %></p>
            <p><b>Session Timeout:</b> <%= session.getMaxInactiveInterval() / 60 %> minutes</p>
        </div>

        <a class="btn-blue btn" href="<%= request.getContextPath() %>/plans">📋 Manage Plans</a>
        <a class="btn" href="<%= request.getContextPath() %>/staff/logout">🚪 Logout</a>
    </div>

</body>
</html>
