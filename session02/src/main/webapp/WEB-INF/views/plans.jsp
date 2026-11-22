<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="tiffin.model.TiffinPlan" %>
<!DOCTYPE html>
<html>
<head>
    <title>Tiffin Plans</title>
</head>
<body>
    <h2>Welcome to TiffinBox</h2>

    <h3>Search Plans By City</h3>
    <form action="${pageContext.request.contextPath}/plans" method="get">
        <select name="city">
            <option value="" <%= request.getAttribute("selectedCity") == null ||
                "".equals(request.getAttribute("selectedCity")) ? "selected" : "" %>>All Cities</option>
            <option value="Ahmedabad" <%= "Ahmedabad".equals(request.getAttribute("selectedCity")) ? "selected" : "" %>>Ahmedabad</option>
            <option value="Surat" <%= "Surat".equals(request.getAttribute("selectedCity")) ? "selected" : "" %>>Surat</option>
            <option value="Rajkot" <%= "Rajkot".equals(request.getAttribute("selectedCity")) ? "selected" : "" %>>Rajkot</option>
        </select>
        <button type="submit">Search</button>
    </form>

    <p><a href="${pageContext.request.contextPath}/addPlan">Add New Plan</a></p>

    <h3>Available Tiffin Plans</h3>
    <table border="1" cellpadding="10">
        <tr>
            <th>ID</th>
            <th>Plan Name</th>
            <th>Price Per Day</th>
            <th>City</th>
        </tr>
        <%
            List<TiffinPlan> plans =
                (List<TiffinPlan>) request.getAttribute("plans");

            if (plans != null && !plans.isEmpty()) {
                for (TiffinPlan p : plans) {
        %>
        <tr>
            <td><%= p.getPlanId() %></td>
            <td><%= p.getPlanName() %></td>
            <td>₹<%= p.getPricePerDay() %></td>
            <td><%= p.getCity() %></td>
        </tr>
        <%
                }
            } else {
        %>
        <tr>
            <td colspan="4">No plans in this city</td>
        </tr>
        <%
            }
        %>
    </table>
</body>
</html>
