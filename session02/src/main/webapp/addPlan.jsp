<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Tiffin Plan</title>
</head>
<body>
    <h2>Add New Tiffin Plan</h2>

    <p style="color:red">${error}</p>

    <form action="${pageContext.request.contextPath}/plans" method="post">
        <p>
            Plan Name:
            <input type="text" name="planName">
        </p>

        <p>
            Price Per Day (Rs.30 to Rs.300):
            <input type="number" name="pricePerDay" min="30" max="300" step="0.01">
        </p>

        <p>
            City:
            <select name="city">
                <option value="">Select City</option>
                <option value="Ahmedabad">Ahmedabad</option>
                <option value="Surat">Surat</option>
                <option value="Rajkot">Rajkot</option>
            </select>
        </p>

        <input type="submit" value="Save Plan">
    </form>

    <p><a href="${pageContext.request.contextPath}/plans">Back to Plans</a></p>
</body>
</html>
