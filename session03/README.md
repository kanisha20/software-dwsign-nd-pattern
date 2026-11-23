# Q3 — Session Management in Java Web Applications (TiffinBox Extended)

## 📁 Folder Structure
```
Q3_Session_Management/TiffinBox/
├── tiffin_setup.sql                           ← SQL setup
├── README.md
│
├── src/tiffin/
│   ├── model/
│   │   └── TiffinPlan.java                    ← Bean (same)
│   ├── dao/
│   │   ├── TiffinPlanDao.java                 ← Interface + NEW findById()
│   │   └── TiffinPlanDaoImpl.java             ← JDBC + NEW findById()
│   └── controller/
│       ├── PlanController.java                ← Updated: Cookie filter (Task 2)
│       ├── CartServlet.java                   ← TASK 1: HttpSession cart
│       ├── CityServlet.java                   ← TASK 2: Cookie set
│       ├── CheckoutServlet.java               ← TASK 3: Hidden fields checkout
│       ├── StaffLoginServlet.java             ← TASK 4: Login + changeSessionId
│       ├── StaffHomeServlet.java              ← TASK 4: Protected page
│       └── StaffLogoutServlet.java            ← TASK 4: session.invalidate()
│
└── WebContent/
    ├── index.jsp
    └── WEB-INF/
        ├── web.xml                            ← + staffUser, staffPass params
        └── views/
            ├── plans.jsp                      ← Updated: Add to Cart + Set Cookie
            ├── addPlan.jsp                    ← Add plan form
            ├── cart.jsp                       ← TASK 1: Cart display
            ├── checkout_step1.jsp             ← TASK 3: Name form
            ├── checkout_step2.jsp             ← TASK 3: Slot form + hidden field
            ├── checkout_confirm.jsp           ← TASK 3: Order confirmation
            ├── staffLogin.jsp                 ← TASK 4: Login form
            └── staffHome.jsp                  ← TASK 4: Protected dashboard
```

## 🧪 Testing

### Task 1 — Cart (HttpSession)
1. Open `/plans` → click "Add to Cart" on Plan 3 (twice) and Plan 5 (once)
2. Open `/cart` → Plan 3 qty=2, Plan 5 qty=1, Grand Total shows

### Task 2 — Cookie
1. Click "Remember: Surat" → cookie set, redirects to /plans (Surat filtered)
2. Close tab, reopen `/plans` → still Surat (cookie persists!)
3. Delete cookie in browser → all plans return

### Task 3 — Checkout (Hidden Fields + URL Rewriting)
1. `/checkout?step=1` → enter name → Step 2 (slot selection)
2. Choose "Lunch 12-1" → confirmation with name + slot + cart total
3. Block cookies in browser → still works (URL rewriting via encodeURL)

### Task 4 — Staff Login/Logout
- Login: `admin` / `tiffin123`
- Wrong password → error message
- Direct `/staff/home` without login → redirected to login
- After logout → `/staff/home` → redirected to login

## 📝 Session Techniques Used
| Technique      | Where Used                        | Purpose                           |
|----------------|-----------------------------------|-----------------------------------|
| HttpSession    | CartServlet, StaffLogin           | Cart data + staff authentication  |
| Cookie         | CityServlet, PlanController       | Remember delivery city (7 days)   |
| Hidden Fields  | CheckoutServlet (step 2→3)        | Carry customerName across forms   |
| URL Rewriting  | encodeURL() in all checkout JSPs  | Work even when cookies blocked    |
| invalidate()   | StaffLogoutServlet                | Destroy session on logout         |
| changeSessionId| StaffLoginServlet                 | Prevent session fixation attack   |
