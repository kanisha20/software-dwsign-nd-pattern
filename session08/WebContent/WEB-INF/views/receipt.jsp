<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!--
  TASK 3: receipt.jsp
  Shows confirmation receipt on successful verification,
  or 'Payment could not be verified' on signature failure / tampering.
-->
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>SocietyPay - Transaction Receipt</title>
    <style>
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: #f0f4f8; max-width: 520px; margin: 40px auto; padding: 20px; }
        .card { background: white; padding: 30px; border-radius: 10px; box-shadow: 0 4px 15px rgba(0,0,0,0.08); }
        .success-box { border-top: 4px solid #16a34a; }
        .error-box { border-top: 4px solid #dc2626; }
        h2 { margin-top: 0; }
        .success-title { color: #16a34a; }
        .error-title { color: #dc2626; }
        .receipt-details { background: #f8fafc; border: 1px solid #e2e8f0; border-radius: 6px; padding: 16px; margin: 20px 0; }
        .receipt-row { display: flex; justify-content: space-between; padding: 8px 0; border-bottom: 1px solid #f1f5f9; font-size: 14px; }
        .receipt-row:last-child { border-bottom: none; font-weight: bold; font-size: 16px; }
        .btn-home { display: block; text-align: center; background: #1e3a8a; color: white; text-decoration: none; padding: 12px; border-radius: 6px; font-weight: bold; margin-top: 20px; }
        .alert-failed { background: #fee2e2; color: #991b1b; padding: 15px; border-radius: 6px; margin: 20px 0; font-weight: bold; text-align: center; }
    </style>
</head>
<body>

    <%
        Boolean isSuccess = (Boolean) request.getAttribute("paymentSuccess");
        if (Boolean.TRUE.equals(isSuccess)) {
    %>
        <div class="card success-box">
            <h2 class="success-title">✅ Payment Successful!</h2>
            <p style="color: #64748b; font-size: 14px;">Your housing maintenance payment has been verified and recorded.</p>

            <div class="receipt-details">
                <div class="receipt-row">
                    <span>Flat Number:</span>
                    <strong><%= request.getAttribute("flatNo") %></strong>
                </div>
                <div class="receipt-row">
                    <span>Razorpay Order ID:</span>
                    <code><%= request.getAttribute("orderId") %></code>
                </div>
                <div class="receipt-row">
                    <span>Payment ID:</span>
                    <code><%= request.getAttribute("paymentId") %></code>
                </div>
                <div class="receipt-row">
                    <span>Status:</span>
                    <strong style="color: #16a34a;">PAID</strong>
                </div>
                <div class="receipt-row">
                    <span>Amount Paid:</span>
                    <span>₹ <%= request.getAttribute("amount") %></span>
                </div>
            </div>

            <a class="btn-home" href="${pageContext.request.contextPath}/pay/start?flatNo=A-101">Back to SocietyPay</a>
        </div>

    <% } else { %>

        <div class="card error-box">
            <h2 class="error-title">❌ Transaction Failed</h2>
            <!-- Constraint check: exact message 'Payment could not be verified' -->
            <div class="alert-failed">
                ⚠️ Payment could not be verified
            </div>
            <p style="color: #64748b; font-size: 14px; text-align: center;">
                HMAC-SHA256 signature verification failed. This transaction was either forged or tampered in transit.
            </p>
            <a class="btn-home" href="${pageContext.request.contextPath}/pay/start?flatNo=A-101">Try Again</a>
        </div>

    <% } %>

</body>
</html>
