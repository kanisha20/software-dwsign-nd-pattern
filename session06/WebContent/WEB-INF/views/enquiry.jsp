<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="pg.model.PgListing" %>
<!--
  TASK 3: enquiry.jsp
  Fields: studentName, studentEmail, listingId
  Posts to /pg/enquiry
-->
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>PgFinder - Student Enquiry & Mailer</title>
    <style>
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: #f0f2f5; max-width: 650px; margin: 40px auto; padding: 20px; }
        .card { background: white; padding: 30px; border-radius: 8px; box-shadow: 0 4px 12px rgba(0,0,0,0.08); }
        h2 { color: #0056b3; margin-top: 0; }
        label { display: block; margin-top: 15px; font-weight: bold; color: #333; }
        input[type="text"], input[type="email"], input[type="number"] { width: 100%; padding: 10px; margin-top: 6px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }
        button { background: #0056b3; color: white; border: none; padding: 12px 20px; border-radius: 5px; cursor: pointer; font-size: 16px; margin-top: 20px; width: 100%; font-weight: bold; }
        button:hover { background: #004085; }
        .alert { padding: 12px; border-radius: 5px; margin-bottom: 20px; font-size: 14px; }
        .alert-success { background: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
        .alert-danger { background: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
        .hint { font-size: 12px; color: #666; margin-top: 4px; }
        .table-info { width: 100%; border-collapse: collapse; margin-top: 15px; }
        .table-info th, .table-info td { border: 1px solid #dee2e6; padding: 8px; font-size: 13px; text-align: left; }
        .table-info th { background: #e9ecef; }
    </style>
</head>
<body>

    <div class="card">
        <h2>📩 PgFinder — Student Enquiry Form</h2>
        <p style="color: #666; font-size: 14px;">Fill this form to receive booking details and the 10 Mandatory House Rules via email.</p>

        <% if (request.getAttribute("success") != null) { %>
            <div class="alert alert-success">
                ✅ <strong><%= request.getAttribute("success") %></strong>
                <%
                    PgListing listing = (PgListing) request.getAttribute("listing");
                    if (listing != null) {
                %>
                    <div style="margin-top: 8px;">
                        <strong>PG Booked:</strong> <%= listing.getPgName() %> (<%= listing.getCity() %>) at ₹<%= listing.getRent() %>/month.
                    </div>
                <% } %>
            </div>
        <% } %>

        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-danger">
                ❌ <strong><%= request.getAttribute("error") %></strong>
            </div>
        <% } %>

        <form method="post" action="${pageContext.request.contextPath}/pg/enquiry">
            <label for="studentName">Student Full Name:</label>
            <input type="text" id="studentName" name="studentName" placeholder="e.g. Rahul Sharma"
                   value="<%= request.getParameter("studentName") != null ? request.getParameter("studentName") : "" %>" required>

            <label for="studentEmail">Student Email Address:</label>
            <input type="text" id="studentEmail" name="studentEmail" placeholder="e.g. rahul@example.com"
                   value="<%= request.getParameter("studentEmail") != null ? request.getParameter("studentEmail") : "" %>" required>
            <div class="hint">Test constraint: Must contain '@' symbol.</div>

            <label for="listingId">PG Listing ID:</label>
            <input type="number" id="listingId" name="listingId" placeholder="e.g. 2"
                   value="<%= request.getParameter("listingId") != null ? request.getParameter("listingId") : "2" %>" required>
            <div class="hint">Valid IDs in DB: 1 (Sunrise), 2 (Shiv Kripa), 3 (Maruti), 4 (Galaxy). Try ID 99 to test unknown error!</div>

            <button type="submit">🚀 Submit Enquiry & Send Mail</button>
        </form>

        <h4 style="margin-top: 30px; margin-bottom: 8px; color: #495057;">Available Listings in System:</h4>
        <table class="table-info">
            <thead>
                <tr>
                    <th>Listing ID</th>
                    <th>PG Name</th>
                    <th>City</th>
                    <th>Rent</th>
                </tr>
            </thead>
            <tbody>
                <tr><td>1</td><td>Sunrise Boys Hostel</td><td>Ahmedabad</td><td>₹ 6,500</td></tr>
                <tr><td>2</td><td>Shiv Kripa PG & Rooms</td><td>Surat</td><td>₹ 5,500</td></tr>
                <tr><td>3</td><td>Maruti PG for Students</td><td>Rajkot</td><td>₹ 4,800</td></tr>
                <tr><td>4</td><td>Galaxy Girls Living</td><td>Ahmedabad</td><td>₹ 7,000</td></tr>
            </tbody>
        </table>
    </div>

</body>
</html>
