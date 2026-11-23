-- ============================================================
--  TIFFIN BOX — Database Setup (Same as Q2)
-- ============================================================
CREATE DATABASE IF NOT EXISTS tiffindb;
USE tiffindb;

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
