<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!-- Same addPlan.jsp as Q2 -->
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>TiffinBox - Add New Plan</title>
    <style>
        body { font-family: 'Segoe UI', Arial, sans-serif; max-width: 500px; margin: 30px auto; background: #f5f5f5; padding: 20px; }
        h1 { color: #e65100; }
        .box { background: white; padding: 25px; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }
        label { display: block; margin-top: 15px; font-weight: bold; }
        input, select { width: 100%; padding: 10px; margin-top: 5px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; font-size: 14px; }
        .btn { padding: 12px 25px; margin-top: 20px; background: #e65100; color: white; border: none; border-radius: 5px; cursor: pointer; font-size: 16px; }
        .error { background: #ffcdd2; color: #c62828; padding: 12px; border-radius: 5px; margin-bottom: 15px; font-weight: bold; }
    </style>
</head>
<body>
    <h1>🍱 Add New Plan</h1>
    <% String error = (String) request.getAttribute("error"); if (error != null) { %>
        <div class="error">⚠️ <%= error %></div>
    <% } %>
    <div class="box">
        <form method="post" action="plans">
            <label>Plan Name:</label>
            <input type="text" name="planName" placeholder="e.g. Deluxe Thali">
            <label>Price Per Day (Rs.):</label>
            <input type="number" name="pricePerDay" placeholder="e.g. 100" step="0.01">
            <label>City:</label>
            <select name="city">
                <option value="">-- Select --</option>
                <option value="Ahmedabad">Ahmedabad</option>
                <option value="Surat">Surat</option>
                <option value="Rajkot">Rajkot</option>
            </select>
            <button type="submit" class="btn">➕ Add Plan</button>
        </form>
    </div>
    <a href="plans">← Back</a>
</body>
</html>
