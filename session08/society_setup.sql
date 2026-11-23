-- ============================================================
--  SocietyPay — Database Setup (Q8)
-- ============================================================

CREATE DATABASE IF NOT EXISTS societypaydb;
USE societypaydb;

-- 1. Maintenance Dues table (Task 1: 6 flats owing ₹1,500 to ₹2,500)
CREATE TABLE IF NOT EXISTS maintenance_dues (
    flat_no        VARCHAR(20) PRIMARY KEY,
    resident_name  VARCHAR(100) NOT NULL,
    month          VARCHAR(30)  NOT NULL,
    amount         DOUBLE       NOT NULL
);

INSERT INTO maintenance_dues (flat_no, resident_name, month, amount) VALUES
('A-101', 'Rajesh Sharma',    'November 2026', 1800.00),
('A-102', 'Bhavna Ben Patel', 'November 2026', 1500.00),
('B-201', 'Vikramaditya Roy', 'November 2026', 2200.00),
('B-202', 'Sanjay Mehta',     'November 2026', 2500.00),
('C-301', 'Ananya Gupta',     'November 2026', 1950.00),
('C-302', 'Farhan Sheikh',    'November 2026', 2100.00)
ON DUPLICATE KEY UPDATE amount = VALUES(amount);

-- 2. Payments table (Task 3: saves verified transaction)
CREATE TABLE IF NOT EXISTS payments (
    id             INT PRIMARY KEY AUTO_INCREMENT,
    flat_no        VARCHAR(20)  NOT NULL,
    order_id       VARCHAR(100) NOT NULL,
    payment_id     VARCHAR(100) NOT NULL,
    amount         DOUBLE       NOT NULL,
    status         VARCHAR(20)  NOT NULL DEFAULT 'PAID',
    payment_time   TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
