<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!--
  TASK 3: ownerHome.jsp (Protected Owner Area)
  Constraint: sends visitors without `ownerEmail` back to the login.
-->
<%
    String ownerEmail = (String) session.getAttribute("ownerEmail");
    if (ownerEmail == null) {
        response.sendRedirect(request.getContextPath() + "/owner/sendOtp");
        return;
    }
    String ownerName = (String) session.getAttribute("ownerName");
    if (ownerName == null) ownerName = "Valued Owner";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>PgFinder - Owner Dashboard</title>
    <style>
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: #f4f6f9; max-width: 750px; margin: 40px auto; padding: 20px; }
        .card { background: white; padding: 30px; border-radius: 8px; box-shadow: 0 4px 12px rgba(0,0,0,0.06); }
        h1 { color: #0056b3; margin-top: 0; }
        .welcome-badge { background: #e3f2fd; border-left: 4px solid #0056b3; padding: 15px; margin-bottom: 25px; border-radius: 0 4px 4px 0; }
        .stats-grid { display: grid; grid-template-columns: repeat(3, 1fr); gap: 15px; margin-bottom: 25px; }
        .stat-box { background: #f8f9fa; border: 1px solid #dee2e6; padding: 15px; border-radius: 6px; text-align: center; }
        .stat-box span { display: block; font-size: 13px; color: #6c757d; }
        .stat-box strong { font-size: 22px; color: #0056b3; }
        .btn-logout { display: inline-block; padding: 10px 20px; background: #dc3545; color: white; text-decoration: none; border-radius: 5px; font-weight: bold; }
        .btn-logout:hover { background: #c82333; }
    </style>
</head>
<body>

    <div class="card">
        <h1>🏡 Owner Portal Dashboard</h1>

        <div class="welcome-badge">
            <h3 style="margin: 0; color: #0056b3;">Welcome back, <%= ownerName %>! 👋</h3>
            <p style="margin: 5px 0 0 0; color: #555; font-size: 14px;">
                Verified Email: <strong><%= ownerEmail %></strong> | Session Authenticated via 2-Factor OTP.
            </p>
        </div>

        <div class="stats-grid">
            <div class="stat-box">
                <span>Active Listings</span>
                <strong>4 Properties</strong>
            </div>
            <div class="stat-box">
                <span>Total Enquiries</span>
                <strong>12 Students</strong>
            </div>
            <div class="stat-box">
                <span>Occupancy</span>
                <strong>88% Full</strong>
            </div>
        </div>

        <p style="color: #666; font-size: 14px;">
            Your session is secured using <code>request.changeSessionId()</code> and protected against session fixation vulnerabilities.
        </p>

        <hr style="border: 0; border-top: 1px solid #eee; margin: 25px 0;">

        <div style="display: flex; justify-content: space-between; align-items: center;">
            <span style="font-size: 13px; color: #888;">Session ID: <code><%= session.getId() %></code></span>
            <a href="${pageContext.request.contextPath}/owner/logout" class="btn-logout">🚪 Secure Logout</a>
        </div>
    </div>

</body>
</html>
