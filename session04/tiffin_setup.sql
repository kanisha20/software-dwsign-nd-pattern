-- ============================================================
--  TIFFINBOX — Q4 Database Setup
--  tiffindb + tiffin_plans + orders table (8 fictional orders)
-- ============================================================

CREATE DATABASE IF NOT EXISTS tiffindb;
USE tiffindb;

-- Plans table (same as before)
CREATE TABLE IF NOT EXISTS tiffin_plans (
    plan_id        INT PRIMARY KEY AUTO_INCREMENT,
    plan_name      VARCHAR(100) NOT NULL,
    price_per_day  DOUBLE       NOT NULL,
    city           VARCHAR(50)  NOT NULL
);

INSERT INTO tiffin_plans (plan_name, price_per_day, city) VALUES
('Basic Thali',       60.00,  'Ahmedabad'),
('Standard Thali',    85.00,  'Ahmedabad'),
('Premium Thali',    120.00,  'Ahmedabad'),
('Light Lunch',       70.00,  'Surat'),
('Full Meal Box',    100.00,  'Surat'),
('Deluxe Dabba',     150.00,  'Surat'),
('Mini Tiffin',       65.00,  'Rajkot'),
('Family Pack',      130.00,  'Rajkot');

-- ============================================================
--  NEW: Orders table (Q4 Task 2)
-- ============================================================
CREATE TABLE IF NOT EXISTS orders (
    order_id       INT PRIMARY KEY AUTO_INCREMENT,
    customer_name  VARCHAR(100) NOT NULL,
    plan_name      VARCHAR(100) NOT NULL,
    quantity       INT          NOT NULL,
    amount         DOUBLE       NOT NULL,
    status         VARCHAR(20)  NOT NULL,   -- 'DELIVERED' or 'PENDING'
    order_date     DATE         NOT NULL
);

-- 8 fictional orders (mix of DELIVERED and PENDING)
INSERT INTO orders (customer_name, plan_name, quantity, amount, status, order_date) VALUES
('Aarav Patel',    'Basic Thali',    5,   300.00, 'DELIVERED', '2026-11-15'),
('Meera Shah',     'Premium Thali',  3,   360.00, 'DELIVERED', '2026-11-16'),
('Rohan Desai',    'Full Meal Box',  7,   700.00, 'PENDING',   '2026-11-18'),
('Priya Joshi',    'Deluxe Dabba',  10,  1500.00, 'DELIVERED', '2026-11-19'),
('Karan Trivedi',  'Family Pack',    2,   260.00, 'PENDING',   '2026-11-20'),
('Nisha Parmar',   'Light Lunch',    4,   280.00, 'PENDING',   '2026-11-21'),
('Kavya Mehta',    'Standard Thali', 6,   510.00, 'PENDING',   '2026-11-22'),
('Raj Sharma',     'Mini Tiffin',    3,   195.00, 'DELIVERED', '2026-11-23');

-- ============================================================
--  Task 3 Test: Mark order 5 as DELIVERED (run after testing)
-- ============================================================
-- UPDATE orders SET status = 'DELIVERED' WHERE order_id = 5;
