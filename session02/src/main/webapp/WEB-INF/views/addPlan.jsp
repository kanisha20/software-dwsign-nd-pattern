<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Tiffin Plan</title>
</head>
<body>
    <h2>Add New Tiffin Plan</h2>

    <%
        String error = (String) request.getAttribute("error");
        if (error != null) {
    %>
        <p style="color:red;"><%= error %></p>
    <%
        }
        String planName = (String) request.getAttribute("planName");
        String price = (String) request.getAttribute("pricePerDay");
        String city = (String) request.getAttribute("selectedCity");
    %>

    <form action="${pageContext.request.contextPath}/plans" method="post">
        Plan Name:
        <input type="text" name="planName"
               value="<%= planName == null ? "" : planName %>">
        <br><br>

        Price Per Day:
        <input type="number" name="pricePerDay" min="30" max="300"
               step="0.01" value="<%= price == null ? "" : price %>">
        <br><br>

        City:
        <select name="city">
            <option value="Ahmedabad" <%= "Ahmedabad".equals(city) ? "selected" : "" %>>Ahmedabad</option>
            <option value="Surat" <%= "Surat".equals(city) ? "selected" : "" %>>Surat</option>
            <option value="Rajkot" <%= "Rajkot".equals(city) ? "selected" : "" %>>Rajkot</option>
        </select>
        <br><br>

        <button type="submit">Add Plan</button>
    </form>

    <p><a href="${pageContext.request.contextPath}/plans">Back to Plans</a></p>
</body>
</html>
