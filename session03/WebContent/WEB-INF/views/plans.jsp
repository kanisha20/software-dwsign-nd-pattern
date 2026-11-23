<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="tiffin.model.TiffinPlan" %>
<!--
  PLANS.JSP — Updated for Q3
  NEW: "Add to Cart" links + "Set City" cookie links
-->
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>TiffinBox - Plans</title>
    <style>
        body { font-family: 'Segoe UI', Arial, sans-serif; max-width: 900px; margin: 30px auto; background: #f5f5f5; padding: 20px; }
        h1 { color: #e65100; }
        table { width: 100%; border-collapse: collapse; background: white; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }
        th { background: #e65100; color: white; padding: 12px; text-align: left; }
        td { padding: 10px 12px; border-bottom: 1px solid #ddd; }
        tr:hover { background: #fff3e0; }
        .btn { display: inline-block; padding: 8px 16px; background: #e65100; color: white; text-decoration: none; border-radius: 5px; margin: 5px 3px; font-size: 13px; }
        .btn:hover { background: #bf360c; }
        .btn-green { background: #2e7d32; }
        .btn-green:hover { background: #1b5e20; }
        .btn-blue { background: #1565c0; }
        .btn-blue:hover { background: #0d47a1; }
        .filter-box { background: white; padding: 15px; margin: 15px 0; border-radius: 5px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }
        select, button { padding: 8px 15px; font-size: 14px; }
        .no-data { color: #c62828; font-weight: bold; padding: 20px; }
        .nav { background: #333; padding: 10px 15px; border-radius: 5px; margin-bottom: 15px; }
        .nav a { color: white; margin-right: 15px; text-decoration: none; }
        .nav a:hover { text-decoration: underline; }
    </style>
</head>
<body>

    <!-- ======== Navigation Bar ======== -->
    <div class="nav">
        <a href="plans">🏠 Plans</a>
        <a href="cart">🛒 Cart</a>
        <a href="checkout?step=1">📦 Checkout</a>
        <a href="staff/login">🔐 Staff Login</a>
    </div>

    <h1>🍱 TiffinBox — Ghar Ka Swaad!</h1>

    <!-- ======== City Filter (dropdown) ======== -->
    <div class="filter-box">
        <form method="get" action="plans">
            <label><b>Filter by City:</b></label>
            <select name="city">
                <option value="">-- All Cities --</option>
                <%
                    String selectedCity = (String) request.getAttribute("selectedCity");
                    if (selectedCity == null) selectedCity = "";
                    String[] cities = {"Ahmedabad", "Surat", "Rajkot"};
                    for (String c : cities) {
                        String selected = c.equals(selectedCity) ? "selected" : "";
                %>
                    <option value="<%= c %>" <%= selected %>><%= c %></option>
                <% } %>
            </select>
            <button type="submit">🔍 Filter</button>
        </form>

        <!-- ======== Q3 TASK 2: Set City Cookie ======== -->
        <p style="margin-top:10px;">
            <b>Remember my city (Cookie):</b>
            <% for (String c : cities) { %>
                <a class="btn-blue btn" href="setCity?city=<%= c %>"><%= c %></a>
            <% } %>
        </p>
    </div>

    <!-- ======== Buttons ======== -->
    <a class="btn" href="plans?action=add">➕ Add Plan</a>
    <a class="btn" href="plans">📋 All Plans</a>

    <!-- ======== Plans Table ======== -->
    <%
        List<TiffinPlan> plans = (List<TiffinPlan>) request.getAttribute("plans");
    %>
    <% if (plans == null || plans.isEmpty()) { %>
        <p class="no-data">No plans in this city.</p>
    <% } else { %>
        <h2><%= (selectedCity != null && !selectedCity.isEmpty()) ? "Plans in " + selectedCity : "All Tiffin Plans" %> (<%= plans.size() %>)</h2>
        <table>
            <tr><th>#</th><th>Plan Name</th><th>Price/Day</th><th>City</th><th>Action</th></tr>
            <% for (TiffinPlan p : plans) { %>
            <tr>
                <td><%= p.getPlanId() %></td>
                <td><%= p.getPlanName() %></td>
                <td><b>&#8377;<%= p.getPricePerDay() %></b></td>
                <td><%= p.getCity() %></td>
                <td>
                    <!-- Q3 TASK 1: Add to Cart link -->
                    <a class="btn-green btn" href="cart?action=add&planId=<%= p.getPlanId() %>">🛒 Add to Cart</a>
                </td>
            </tr>
            <% } %>
        </table>
    <% } %>

</body>
</html>
