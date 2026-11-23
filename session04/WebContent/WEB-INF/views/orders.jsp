<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Orders Management - TiffinBox</title>
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
                    <h2 class="h3 fw-bold text-dark">Customer Orders</h2>
                </div>

                <div class="card shadow-sm border-0">
                    <div class="card-body p-0">
                        <div class="table-responsive">
                            <table class="table table-hover align-middle mb-0">
                                <thead class="table-light">
                                    <tr>
                                        <th>Order ID</th>
                                        <th>Customer Name</th>
                                        <th>Plan Name</th>
                                        <th>Qty</th>
                                        <th>Amount</th>
                                        <th>Status</th>
                                        <th>Order Date</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="ord" items="${orders}">
                                        <tr>
                                            <td class="fw-bold">#${ord.orderId}</td>
                                            <td>${ord.customerName}</td>
                                            <td><span class="badge bg-secondary">${ord.planName}</span></td>
                                            <td>${ord.quantity}</td>
                                            <td class="fw-bold">
                                                ₹ <fmt:formatNumber value="${ord.amount}" type="number" minFractionDigits="2" maxFractionDigits="2"/>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${ord.status == 'DELIVERED'}">
                                                        <span class="badge badge-delivered px-2 py-1">DELIVERED</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badge badge-pending px-2 py-1">PENDING</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>${ord.orderDate}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </main>
        </div>
    </div>

    <jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>
