# Q7 — OTP Verification via Mail Integration (PgFinder)

## 📁 Project Overview
Is project mein **2-Step Email OTP Authentication** implement ki gayi hai PG Owners ke liye:

### Implemented Tasks & Features

1. **Task 1: SecureRandom OTP Generator (`OtpGenerator.java`)**:
   - Method: `generateOtp()` using `java.security.SecureRandom`.
   - Generates 6-digit numbers in range `100000` to `999999`.
   - Test class: `OtpGeneratorCheck.java` calls the method 10,000 times, verifying 0 invalid lengths and tracking uniqueness.

2. **Task 2: Step 1 Owner Login (`SendOtpServlet.java` + `ownerLogin.jsp`)**:
   - Database table: `owners` in `pgdb` with 3 owners.
   - Endpoint: `/owner/sendOtp`
   - Verification: Checks owner email in database. If email is not registered, shows `'Owner not found'`.
   - Session Storage: Stores OTP and expiry timestamp based on context parameter `otpValiditySeconds` (300 seconds default).
   - Mail Dispatch: Calls `PgMailer.sendOtpMail(email, otp)` and forwards to `verifyOtp.jsp`.

3. **Task 3: Step 2 OTP Verification & Account Locking (`VerifyOtpServlet.java`)**:
   - Endpoint: `/owner/verifyOtp`
   - Expiry Handling: Rejects OTP if `System.currentTimeMillis() > otpExpiryTime` (e.g. test with `otpValiditySeconds = 20`).
   - Brute-Force Protection: Tracks failed attempts and **locks login after 3 wrong attempts**.
   - Session Fixation Protection: On successful validation, calls `request.changeSessionId()`, sets `ownerEmail`, cleans temporary OTP attributes, and redirects to `ownerHome.jsp`.
   - Authorization Check: `ownerHome.jsp` redirects visitors back to login if `ownerEmail` is absent from session.

---

## 🗄️ Database Setup
MySQL mein `pg_setup.sql` run karein:
```sql
SOURCE C:/Users/dipak/Desktop/project 2/Q7_OTP_Verification/PgFinder/pg_setup.sql;
```

---

## 🧪 Testing Checklist
1. **10,000 Loop Check**:
   ```bash
   cd "C:\Users\dipak\Desktop\project 2\Q7_OTP_Verification\PgFinder\src"
   javac pg/security/*.java
   java pg.security.OtpGeneratorCheck
   ```
2. **Unknown Email Test**:
   - Enter `notregistered@gmail.com` -> Result: shows `'Owner not found'`.
3. **Valid Email Test**:
   - Enter `dipak.owner@gmail.com` -> Result: OTP generated, emailed, forwarded to verification screen.
4. **3 Wrong Attempts Test**:
   - Enter wrong OTP `111111` 3 times -> Result: Account locked / blocked.
5. **Expiry Test**:
   - Change `otpValiditySeconds` to `20` in `web.xml`, wait 20s -> Result: shows `'OTP has expired!'`.
