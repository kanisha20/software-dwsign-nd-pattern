# Q8 — Online Payment Integration (Razorpay)

## 📁 Project Overview
Is project ('SocietyPay') mein Housing Society Maintenance dues collect karne ke liye **Razorpay Payment Gateway** integrate kiya gaya hai.

### Implemented Tasks & Features

1. **Task 1: Order Creation via HttpClient (`RazorpayOrderService.java`)**:
   - Database table: `maintenance_dues` in `societypaydb` with 6 flats (A-101, A-102, B-201, B-202, C-301, C-302) owing ₹1,500 to ₹2,500.
   - Endpoint: `/pay/createOrder?flatNo=A-101` (`CreateOrderServlet.java`).
   - Method: `RazorpayOrderService.createOrder(amountPaise, receipt)` executes an HTTP POST to `https://api.razorpay.com/v1/orders` using modern `java.net.http.HttpClient`.
   - Sends `Authorization: Basic <Base64(keyId:keySecret)>`.
   - Parses the returned Order ID using `indexOf("\"id\":\"")`.

2. **Task 2: Pay Now Frontend (`payNow.jsp` + `PayNowServlet.java`)**:
   - Endpoint: `/pay/start?flatNo=A-101`
   - Integrates Razorpay JavaScript Checkout SDK: `https://checkout.razorpay.com/v1/checkout.js`.
   - Initializes `new Razorpay(options)`.
   - `handler` callback silently sends `razorpay_payment_id`, `razorpay_order_id`, and `razorpay_signature` to `/pay/verify`.
   - Constraint: **Razorpay Key Secret is NEVER exposed to the frontend JSP.**

3. **Task 3: Server-side HMAC-SHA256 Signature Verification (`VerifyPaymentServlet.java`)**:
   - Endpoint: `/pay/verify`
   - Recomputes HMAC-SHA256 of `orderId + "|" + paymentId` using `javax.crypto.Mac` and the secret key.
   - Hex encoding: `String.format("%02x", b)`.
   - Safe timing comparison: `MessageDigest.isEqual()`.
   - On SUCCESS: Saves record into `payments` table (status `PAID`) and renders receipt in `receipt.jsp`.
   - On TAMPERING / FAILURE: Shows `'Payment could not be verified'`.
   - Test class: `TamperedPayloadTest.java` verifies 1 legitimate payment and 3 tampered payloads.

---

## 🗄️ Database Setup
MySQL mein `society_setup.sql` run karein:
```sql
SOURCE C:/Users/dipak/Desktop/project 2/Q8_Payment_Integration/SocietyPay/society_setup.sql;
```

---

## 🧪 Testing Checklist
1. **Signature Verification Unit Test**:
   ```bash
   cd "C:\Users\dipak\Desktop\project 2\Q8_Payment_Integration\SocietyPay\src"
   javac society/service/*.java society/test/*.java
   java society.test.TamperedPayloadTest
   ```
2. **Order Creation Check**:
   - Open: `http://localhost:8080/SocietyPay/pay/createOrder?flatNo=A-101`
   - Output: Shows Flat No, Due Amount, and created Razorpay Order ID.
3. **Pay Now Page**:
   - Open: `http://localhost:8080/SocietyPay/pay/start?flatNo=A-101`
   - Click "Pay Now" -> Razorpay Modal opens.
   - Pay with test UPI: `success@razorpay`.
4. **Tampered Payload Verification**:
   - If an invalid signature is received at `/pay/verify`, the response displays:
     **`Payment could not be verified`**.
