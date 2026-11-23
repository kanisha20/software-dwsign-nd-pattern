-- ============================================================
--  Q9 Database Setup: societypaydb + residents + maintenance_dues
-- ============================================================

CREATE DATABASE IF NOT EXISTS societypaydb;
USE societypaydb;

-- 1. Residents table (Task 1: holding 2 taken flats)
CREATE TABLE IF NOT EXISTS residents (
    flat_no        VARCHAR(20) PRIMARY KEY,
    resident_name  VARCHAR(100) NOT NULL,
    email          VARCHAR(100) NOT NULL
);

-- Seed with 2 already taken flats
INSERT INTO residents (flat_no, resident_name, email) VALUES
('A-101', 'Rajesh Sharma', 'rajesh.sharma@example.com'),
('B-201', 'Vikramaditya Roy', 'vikram.roy@example.com')
ON DUPLICATE KEY UPDATE resident_name = VALUES(resident_name);

-- 2. Maintenance Dues table (Task 3: A-101 has 3 monthly rows)
CREATE TABLE IF NOT EXISTS maintenance_dues (
    id             INT PRIMARY KEY AUTO_INCREMENT,
    flat_no        VARCHAR(20)  NOT NULL,
    resident_name  VARCHAR(100) NOT NULL,
    month          VARCHAR(30)  NOT NULL,
    amount         DOUBLE       NOT NULL
);

-- Clean and insert fresh dues for demo
TRUNCATE TABLE maintenance_dues;

INSERT INTO maintenance_dues (flat_no, resident_name, month, amount) VALUES
('A-101', 'Rajesh Sharma', 'September 2026', 1800.00),
('A-101', 'Rajesh Sharma', 'October 2026',   1800.00),
('A-101', 'Rajesh Sharma', 'November 2026',  1800.00),
('B-201', 'Vikramaditya Roy', 'November 2026', 2200.00),
('C-301', 'Ananya Gupta',  'November 2026',  1950.00);
