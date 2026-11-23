# Q6 — Mail Integration (JavaMail API) with PgFinder

## 📁 Project Overview
Is project mein JavaMail API ka integration kiya gaya hai PG enquiry notifications aur attachment ke saath confirmation email bhejne ke liye.

### Implemented Tasks & Features

1. **Task 1: HTML Enquiry Mail (`PgMailer.java`)**:
   - Method: `sendEnquiryMail(toEmail, pgName, city, rent)`
   - Subject: `'Your enquiry for <pgName>'`
   - Body: Clean HTML formatted table (PG name, city, rent in ₹)
   - Protocol: SMTP over `smtp.gmail.com`, Port `587`, STARTTLS enabled.
   - Credentials: Reads `MAIL_USER` and `MAIL_APP_PASSWORD` from Environment Variables.
   - Constraint: `message.writeTo(System.out)` hamesha print karta hai message stream ko.
   - Hint: `message.saveChanges()` call kiya gaya hai stream output se pehle. Missing env vars pe network send skip karta hai bina crash huye (Print-only safe mode).

2. **Task 2: House Rules Attachment (`sendHouseRulesMail`)**:
   - Method: `sendHouseRulesMail(toEmail, pgName)`
   - Attachment: `pg_house_rules.txt` (10 realistic PG rules defined).
   - Multipart Structure: `MimeMultipart` with one `MimeBodyPart` for HTML text and another `MimeBodyPart` created via `attachFile()`.
   - Security Constraint: Password code ya chat mein hardcode nahi hai.

3. **Task 3: Wiring into PgFinder (`EnquiryServlet` + `enquiry.jsp`)**:
   - Endpoint: `/pg/enquiry`
   - Fields: `studentName`, `studentEmail`, `listingId`.
   - Database: Saves record into `enquiries` table in database `pgdb`.
   - Validations:
     - `studentEmail` must contain `@` (e.g. `user` without `@` is rejected).
     - `listingId` must exist in `pg_listings` (unknown id like 99 produces error).
   - Output log: Raw MIME output saved in `mail_log.txt`.

---

## 🗄️ Database Setup
MySQL mein `pg_setup.sql` run karein:
```sql
SOURCE C:/Users/dipak/Desktop/project 2/Q6_Mail_Integration/PgFinder/pg_setup.sql;
```

---

## 🧪 Testing

### Standalone Test (Command Line):
```bash
cd "C:\Users\dipak\Desktop\project 2\Q6_Mail_Integration\PgFinder\src"
javac -cp ".;path/to/javax.mail.jar" pg/mail/*.java pg/model/*.java
java -cp ".;path/to/javax.mail.jar" pg.mail.MailTestDemo
```

### Web App Testing in Browser:
1. Open: `http://localhost:8080/PgFinder/pg/enquiry`
2. **Test 1 (Valid Input)**: Name = `Kavya`, Email = `your_email@gmail.com`, Listing ID = `2`.
   - Result: Saved in DB + Console prints RFC 822 MIME stream + Success alert.
3. **Test 2 (Unknown Listing ID)**: Enter Listing ID = `99`.
   - Result: Rejected with message `"Listing ID #99 does not exist in our database."`
4. **Test 3 (Invalid Email)**: Enter Email = `kavyapatel`.
   - Result: Rejected with message `"Invalid email address! Email must contain '@' and follow a valid format."`
