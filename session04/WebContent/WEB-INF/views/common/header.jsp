<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- HEADER.JSP: Top Navbar & Asset Links -->
<nav class="navbar navbar-expand-lg top-navbar px-4 py-2 sticky-top">
    <div class="container-fluid">
        <a class="navbar-brand d-flex align-items-center" href="${pageContext.request.contextPath}/staff/home">
            <img src="${pageContext.request.contextPath}/assets/img/logo.svg" alt="TiffinBox Logo" height="30" class="me-2">
            <span class="fw-bold text-dark d-none d-sm-inline">Admin Portal</span>
        </a>
        <div class="d-flex align-items-center ms-auto">
            <span class="me-3 text-muted">
                👤 Welcome, <strong class="text-dark">${sessionScope.staff}</strong>
            </span>
            <a href="${pageContext.request.contextPath}/staff/logout" class="btn btn-outline-danger btn-sm">
                🚪 Logout
            </a>
        </div>
    </div>
</nav>
