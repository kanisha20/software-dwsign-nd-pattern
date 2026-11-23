<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="society.model.MaintenanceDue" %>
<!--
  TASK 2: payNow.jsp
  - Opens Razorpay Checkout via JavaScript SDK (new Razorpay(options))
  - Key ID, amount, order_id passed from servlet
  - Posts razorpay_payment_id, razorpay_order_id, razorpay_signature to /pay/verify
  - Constraint: KEY SECRET IS NEVER INCLUDED IN JSP!
-->
<%
    MaintenanceDue due = (MaintenanceDue) request.getAttribute("due");
    String keyId = (String) request.getAttribute("keyId");
    String orderId = (String) request.getAttribute("orderId");
    Integer amountPaise = (Integer) request.getAttribute("amountPaise");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>SocietyPay - Pay Maintenance Due</title>
    <!-- Razorpay Checkout JavaScript SDK -->
    <script src="https://checkout.razorpay.com/v1/checkout.js"></script>
    <style>
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: #f0f4f8; max-width: 520px; margin: 40px auto; padding: 20px; }
        .card { background: white; padding: 30px; border-radius: 10px; box-shadow: 0 4px 15px rgba(0,0,0,0.08); }
        h2 { color: #1e3a8a; margin-top: 0; text-align: center; }
        .due-details { background: #eff6ff; border: 1px solid #bfdbfe; border-radius: 8px; padding: 18px; margin: 20px 0; }
        .row-item { display: flex; justify-content: space-between; padding: 6px 0; font-size: 14px; border-bottom: 1px dashed #dbeafe; }
        .row-item:last-child { border-bottom: none; }
        .total-amount { font-size: 20px; font-weight: bold; color: #1e3a8a; }
        .btn-pay { background: #2563eb; color: white; border: none; padding: 14px 20px; border-radius: 6px; cursor: pointer; font-size: 16px; font-weight: bold; width: 100%; transition: background 0.2s; }
        .btn-pay:hover { background: #1d4ed8; }
        .badge-test { display: inline-block; background: #fef08a; color: #854d0e; padding: 4px 8px; border-radius: 4px; font-size: 12px; font-weight: bold; margin-bottom: 15px; }
        .info-note { font-size: 12px; color: #64748b; margin-top: 15px; text-align: center; }
    </style>
</head>
<body>

    <div class="card">
        <span class="badge-test">TEST MODE ONLY</span>
        <h2>🏢 SocietyPay Maintenance</h2>

        <div class="due-details">
            <div class="row-item">
                <span>Flat Number:</span>
                <strong><%= due.getFlatNo() %></strong>
            </div>
            <div class="row-item">
                <span>Resident Name:</span>
                <strong><%= due.getResidentName() %></strong>
            </div>
            <div class="row-item">
                <span>Billing Period:</span>
                <strong><%= due.getMonth() %></strong>
            </div>
            <div class="row-item" style="margin-top: 8px;">
                <span>Total Amount Due:</span>
                <span class="total-amount">₹ <%= due.getAmount() %></span>
            </div>
        </div>

        <!-- Pay Now Trigger -->
        <button id="rzp-button" class="btn-pay">💳 Pay Now (₹ <%= due.getAmount() %>)</button>

        <p class="info-note">
            Test Mode: Use UPI ID <code>success@razorpay</code> or mock payment. Never enter personal credit/debit card numbers.
        </p>
    </div>

    <!-- Hidden Form to POST verification data back to VerifyPaymentServlet -->
    <form id="verifyForm" method="post" action="${pageContext.request.contextPath}/pay/verify" style="display:none;">
        <input type="hidden" name="razorpay_payment_id" id="razorpay_payment_id">
        <input type="hidden" name="razorpay_order_id" id="razorpay_order_id">
        <input type="hidden" name="razorpay_signature" id="razorpay_signature">
        <input type="hidden" name="flatNo" value="<%= due.getFlatNo() %>">
        <input type="hidden" name="amount" value="<%= due.getAmount() %>">
    </form>

    <script>
        var options = {
            "key": "<%= keyId %>",
            "amount": "<%= amountPaise %>",
            "currency": "INR",
            "name": "SocietyPay Housing",
            "description": "Maintenance Dues - <%= due.getFlatNo() %>",
            "order_id": "<%= orderId %>",
            "handler": function (response) {
                // Post response to VerifyPaymentServlet
                document.getElementById('razorpay_payment_id').value = response.razorpay_payment_id;
                document.getElementById('razorpay_order_id').value = response.razorpay_order_id;
                document.getElementById('razorpay_signature').value = response.razorpay_signature;
                document.getElementById('verifyForm').submit();
            },
            "prefill": {
                "name": "<%= due.getResidentName() %>",
                "email": "resident.<%= due.getFlatNo().toLowerCase() %>@society.com",
                "contact": "9876543210"
            },
            "theme": {
                "color": "#2563eb"
            }
        };

        var rzp = new Razorpay(options);

        document.getElementById('rzp-button').onclick = function (e) {
            rzp.open();
            e.preventDefault();
        };

        // Fallback simulation button for offline/headless browsers
        rzp.on('payment.failed', function (response) {
            alert("Payment failed: " + response.error.description);
        });
    </script>

</body>
</html>
