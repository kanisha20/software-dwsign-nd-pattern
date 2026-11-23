<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!--
  TASK 1: uploadPhoto.jsp
  Multipart form with fields: listingId, roomPhoto
-->
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>PgFinder - Upload Room Photo</title>
    <style>
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: #f4f6f9; max-width: 600px; margin: 40px auto; padding: 20px; }
        .card { background: white; padding: 30px; border-radius: 8px; box-shadow: 0 4px 10px rgba(0,0,0,0.08); }
        h2 { color: #0056b3; margin-top: 0; }
        label { display: block; margin-top: 15px; font-weight: bold; color: #333; }
        input[type="number"], input[type="file"], select { width: 100%; padding: 10px; margin-top: 6px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }
        button { background: #0056b3; color: white; border: none; padding: 12px 20px; border-radius: 5px; cursor: pointer; font-size: 16px; margin-top: 20px; width: 100%; }
        button:hover { background: #004085; }
        .alert { padding: 12px; border-radius: 5px; margin-bottom: 20px; }
        .alert-success { background: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
        .alert-danger { background: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
        .info { font-size: 13px; color: #666; margin-top: 4px; }
        .view-link { display: inline-block; margin-top: 15px; color: #0056b3; text-decoration: none; font-weight: bold; }
    </style>
</head>
<body>

    <div class="card">
        <h2>🏡 PgFinder: Upload Room Photo</h2>

        <% if (request.getAttribute("message") != null) { %>
            <div class="alert alert-success">
                ✅ <strong><%= request.getAttribute("message") %></strong><br>
                Saved path: <code><%= request.getAttribute("savedPath") %></code><br>
                <a class="view-link" href="${pageContext.request.contextPath}/listing?id=<%= request.getAttribute("listingId") %>">
                    👉 View PG Listing #<%= request.getAttribute("listingId") %> with Photos
                </a>
            </div>
        <% } %>

        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-danger">
                ❌ <strong><%= request.getAttribute("error") %></strong>
            </div>
        <% } %>

        <!-- Multipart Form: POST to /pg/upload -->
        <form method="post" action="${pageContext.request.contextPath}/pg/upload" enctype="multipart/form-data">
            <label for="listingId">Select PG Listing ID:</label>
            <input type="number" id="listingId" name="listingId" min="1" max="4" value="2" required>
            <div class="info">Options: 1 (Sunrise Boys), 2 (Shiv Kripa), 3 (Maruti PG), 4 (Galaxy Girls)</div>

            <label for="roomPhoto">Choose Room Photo:</label>
            <input type="file" id="roomPhoto" name="roomPhoto" accept=".jpg,.jpeg,.png" required>
            <div class="info">Constraints: Only <strong>.jpg, .jpeg, .png</strong> accepted. Max size: <strong>2 MB</strong>.</div>

            <button type="submit">📤 Upload Photo</button>
        </form>

        <hr style="margin-top: 30px; border: 0; border-top: 1px solid #eee;">
        <p style="text-align: center; margin-bottom: 0;">
            <a href="${pageContext.request.contextPath}/listing?id=2" class="view-link">View Listing ID #2 Directly</a>
        </p>
    </div>

</body>
</html>
