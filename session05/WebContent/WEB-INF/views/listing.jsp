<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="pg.model.PgListing" %>
<!--
  TASK 2 + 3: listing.jsp?id=2
  Shows PG details and renders BOTH images side by side:
  1) From Disk (/pg/photo?id=2)
  2) From Database MEDIUMBLOB (/pg/photoDb?id=2)
-->
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>PgFinder - Listing Details</title>
    <style>
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: #f8f9fa; max-width: 850px; margin: 30px auto; padding: 20px; }
        .card { background: white; padding: 30px; border-radius: 8px; box-shadow: 0 4px 12px rgba(0,0,0,0.06); }
        h1 { color: #0056b3; margin-top: 0; }
        .details-grid { display: grid; grid-template-columns: repeat(3, 1fr); gap: 15px; margin-bottom: 25px; background: #e9ecef; padding: 15px; border-radius: 6px; }
        .details-item span { display: block; font-size: 13px; color: #6c757d; }
        .details-item strong { font-size: 17px; color: #212529; }
        .image-container { display: flex; gap: 20px; justify-content: center; margin-top: 25px; flex-wrap: wrap; }
        .image-box { flex: 1; min-width: 280px; max-width: 380px; border: 1px solid #dee2e6; border-radius: 6px; padding: 12px; background: #fff; text-align: center; box-shadow: 0 2px 5px rgba(0,0,0,0.05); }
        .image-box h4 { margin-top: 5px; color: #495057; font-size: 15px; }
        .image-box img { width: 100%; height: 230px; object-fit: cover; border-radius: 4px; border: 1px solid #eee; }
        .tag { display: inline-block; padding: 3px 8px; border-radius: 4px; font-size: 12px; font-weight: bold; margin-bottom: 8px; }
        .tag-disk { background: #cce5ff; color: #004085; }
        .tag-blob { background: #d4edda; color: #155724; }
        .nav-link { display: inline-block; margin-top: 25px; color: #0056b3; text-decoration: none; font-weight: 500; }
    </style>
</head>
<body>

    <div class="card">
        <%
            PgListing listing = (PgListing) request.getAttribute("listing");
            if (listing == null) {
        %>
            <h2>Listing not found!</h2>
            <a href="${pageContext.request.contextPath}/pg/upload" class="nav-link">← Go to Upload Page</a>
        <% } else { %>
            <h1>🏡 <%= listing.getPgName() %></h1>

            <div class="details-grid">
                <div class="details-item">
                    <span>Listing ID</span>
                    <strong>#<%= listing.getListingId() %></strong>
                </div>
                <div class="details-item">
                    <span>City</span>
                    <strong><%= listing.getCity() %></strong>
                </div>
                <div class="details-item">
                    <span>Monthly Rent</span>
                    <strong>₹ <%= listing.getRent() %></strong>
                </div>
            </div>

            <h3>📸 Room Photos Comparison (Side-by-Side)</h3>
            <p style="color: #6c757d; font-size: 14px;">Comparing disk-served file vs database MEDIUMBLOB stream:</p>

            <div class="image-container">
                <!-- Source 1: Disk Stream via PhotoDownloadServlet (Task 2) -->
                <div class="image-box">
                    <span class="tag tag-disk">Folder Storage</span>
                    <h4>Served from Disk Path</h4>
                    <img src="${pageContext.request.contextPath}/pg/photo?id=<%= listing.getListingId() %>" 
                         alt="Room Photo from Disk"
                         onerror="this.onerror=null; this.src='https://via.placeholder.com/350x230?text=No+Disk+Photo';">
                    <p style="font-size: 12px; color: #888; margin-top: 8px;">Endpoint: <code>/pg/photo?id=<%= listing.getListingId() %></code></p>
                </div>

                <!-- Source 2: Database MEDIUMBLOB Stream via BlobPhotoServlet (Task 3) -->
                <div class="image-box">
                    <span class="tag tag-blob">Database MEDIUMBLOB</span>
                    <h4>Served from DB Blob</h4>
                    <img src="${pageContext.request.contextPath}/pg/photoDb?id=<%= listing.getListingId() %>" 
                         alt="Room Photo from DB Blob"
                         onerror="this.onerror=null; this.src='https://via.placeholder.com/350x230?text=No+BLOB+Photo';">
                    <p style="font-size: 12px; color: #888; margin-top: 8px;">Endpoint: <code>/pg/photoDb?id=<%= listing.getListingId() %></code></p>
                </div>
            </div>

            <div style="text-align: center;">
                <a href="${pageContext.request.contextPath}/pg/upload" class="nav-link">📤 Upload Another Photo</a>
            </div>
        <% } %>
    </div>

</body>
</html>
