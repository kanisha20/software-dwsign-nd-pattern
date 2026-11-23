<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Staff Dashboard - TiffinBox</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/tiffin.css">
</head>
<body class="d-flex flex-column min-vh-100">

    <jsp:include page="/WEB-INF/views/common/header.jsp" />

    <div class="container-fluid flex-grow-1">
        <div class="row">
            <jsp:include page="/WEB-INF/views/common/sidebar.jsp" />

            <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4 py-4">
                <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-2 pb-2 mb-4 border-bottom">
                    <h2 class="h3 fw-bold text-dark">Staff Overview Dashboard</h2>
                </div>

                <!-- 3 Bootstrap Cards for Stats -->
                <div class="row g-4 mb-4">
                    <!-- Total Orders Card -->
                    <div class="col-md-4">
                        <div class="card card-stat bg-primary text-white p-3">
                            <div class="card-body">
                                <h6 class="text-uppercase tracking-wider opacity-75">Total Orders</h6>
                                <h2 class="display-6 fw-bold mt-2">${stats.totalOrders}</h2>
                            </div>
                        </div>
                    </div>

                    <!-- Pending Orders Card (Turns Red via c:if if > 3) -->
                    <div class="col-md-4">
                        <c:choose>
                            <c:when test="${stats.pendingOrders > 3}">
                                <div class="card card-stat card-pending-alert p-3">
                                    <div class="card-body">
                                        <h6 class="text-uppercase tracking-wider opacity-75">Pending Orders (Attention!)</h6>
                                        <h2 class="display-6 fw-bold mt-2">${stats.pendingOrders}</h2>
                                    </div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="card card-stat bg-warning text-dark p-3">
                                    <div class="card-body">
                                        <h6 class="text-uppercase tracking-wider opacity-75">Pending Orders</h6>
                                        <h2 class="display-6 fw-bold mt-2">${stats.pendingOrders}</h2>
                                    </div>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <!-- Total Revenue Card -->
                    <div class="col-md-4">
                        <div class="card card-stat bg-success text-white p-3">
                            <div class="card-body">
                                <h6 class="text-uppercase tracking-wider opacity-75">Total Revenue</h6>
                                <h2 class="display-6 fw-bold mt-2">
                                    ₹ <fmt:formatNumber value="${stats.totalRevenue}" type="number" minFractionDigits="2" maxFractionDigits="2"/>
                                </h2>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="alert alert-info">
                    <strong>Quick Links:</strong> Check out the latest customer orders in <a href="${pageContext.request.contextPath}/staff/orders" class="alert-link">Orders List</a>.
                </div>
            </main>
        </div>
    </div>

    <jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>
