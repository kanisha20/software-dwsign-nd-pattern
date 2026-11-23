# Q4 — Template Integration (Bootstrap + JSTL + EL)

## 📁 Project Overview
Is project mein Admin Dashboard ko Bootstrap 5 aur modular JSP includes (`header.jsp`, `sidebar.jsp`, `footer.jsp`) ke saath design kiya gaya hai.

### Features
1. **Modular JSP Layout**:
   - `header.jsp`: Navbar, Logo (`logo.svg`), logged in staff name aur logout button.
   - `sidebar.jsp`: Sidebar links (`Dashboard`, `Orders List`, `Public Plans`) with active link highlighting using `${activePage}`.
   - `footer.jsp`: Bootstrap JS aur custom JS load karta hai.
2. **Orders Table with JSTL & EL**:
   - `<c:forEach>` loop se list render hoti hai.
   - Amounts `<fmt:formatNumber>` aur `₹` sign ke saath formatted hain.
   - Status: Green badge for `DELIVERED`, Orange for `PENDING` using `<c:choose>`.
3. **Dashboard Stats**:
   - 3 Cards: Total Orders, Pending Orders, Total Revenue.
   - **Condition**: Agar Pending Orders > 3 ho, card **Red** ho jata hai using `<c:choose>` / `<c:if>`.

## 🗄️ Database Setup
`tiffin_setup.sql` run karein MySQL mein:
```sql
CREATE DATABASE IF NOT EXISTS tiffindb;
USE tiffindb;
-- tiffin_plans + orders tables create aur populate ho jayenge
```
Test ke baad 5 pending orders ko delivered mark karne ke liye:
```sql
UPDATE orders SET status = 'DELIVERED' WHERE order_id IN (3, 5, 6, 7);
```
