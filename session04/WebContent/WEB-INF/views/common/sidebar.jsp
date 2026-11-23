<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- SIDEBAR.JSP: Navigation Menu with Active Highlighting -->
<div class="col-md-3 col-lg-2 p-0 sidebar">
    <div class="p-3 text-white border-bottom border-secondary text-center">
        <h5 class="m-0 fw-bold">🍱 TiffinBox Staff</h5>
    </div>
    <ul class="nav flex-column mt-3">
        <li class="nav-item">
            <a class="nav-link ${activePage == 'home' ? 'active' : ''}" 
               href="${pageContext.request.contextPath}/staff/home">
                📊 Dashboard
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${activePage == 'orders' ? 'active' : ''}" 
               href="${pageContext.request.contextPath}/staff/orders">
                📦 Orders List
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${activePage == 'plans' ? 'active' : ''}" 
               href="${pageContext.request.contextPath}/plans">
                🍱 Public Plans
            </a>
        </li>
    </ul>
</div>
