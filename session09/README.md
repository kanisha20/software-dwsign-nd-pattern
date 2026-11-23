# Q9 — AJAX & API Integration (SocietyPay)

## 📁 Project Overview
Is project mein **Asynchronous JavaScript (AJAX) and Custom REST API integration** implement ki gayi hai bina kisi page reload ke.

### Implemented Tasks & Constraints

1. **Task 1: Real-time Live Flat Availability Check (`CheckFlatServlet.java` + `register.jsp`)**:
   - Database table: `residents` in `societypaydb` holding 2 taken flats (`A-101`, `B-201`).
   - Endpoint: `/checkFlat?flatNo=B-204`
   - Technology: Pure **`XMLHttpRequest`** triggered on flat number field `blur`.
   - Output: Hand-built JSON: `{"available":true}` or `{"available":false}`.
   - UI: Green message for available, Red message for taken without page reload.
   - Constraint: **No JSON library & No jQuery.**

2. **Task 2: Asynchronous Form Submission without Reload (`ResidentRegisterServlet.java`)**:
   - Endpoint: `/resident/register` (POST)
   - Technology: **`fetch()` API** with a `URLSearchParams` body and `event.preventDefault()`.
   - Validations:
     - Name not empty
     - Email contains `@`
     - Flat not already taken
   - Response: Hand-crafted JSON `{"ok":true}` or `{"ok":false,"message":"..."}`.

3. **Task 3: Custom Dues API & Dynamic Table Rendering (`DuesApiServlet.java` + `dues.jsp`)**:
   - Endpoint: `/api/dues?flatNo=A-101`
   - Database: `A-101` contains 3 monthly records (September, October, November 2026).
   - Hand-crafted JSON array response: `[{"month":"...","amount":...}, ...]`.
   - Frontend: `dues.jsp` calls endpoint via `fetch()` and `response.json()`.
   - Table generation: Built dynamically using **`document.createElement`**.
   - Handles missing flats by displaying **`No dues found`**.

---

## 🗄️ Database Setup
MySQL mein `society_ajax_setup.sql` run karein:
```sql
SOURCE C:/Users/dipak/Desktop/project 2/Q9_AJAX_Integration/SocietyPay/society_ajax_setup.sql;
```

---

## 🧪 Testing Checklist
1. **Live Check (Task 1)**:
   - Go to `http://localhost:8080/SocietyPay/register.jsp`
   - Enter `A-101` and click outside -> Shows **`✖ Flat A-101 is already occupied!`** in red.
   - Enter `B-204` and click outside -> Shows **`✔ Flat B-204 is available!`** in green.
2. **Submit Test (Task 2)**:
   - Enter `B-204`, Name `Sunil Dave`, Email `sunil@example.com` -> Click Register -> Success message without page reload!
   - Try registering `A-101` -> Shows error message.
   - Try bad email without `@` -> Shows email validation error.
3. **Dues Viewer API (Task 3)**:
   - Go to `http://localhost:8080/SocietyPay/dues.jsp`
   - Enter `A-101` -> Renders 3 months table dynamically.
   - Enter `B-201` -> Renders 1 month row.
   - Enter `Z-999` -> Shows **`No dues found`**.
