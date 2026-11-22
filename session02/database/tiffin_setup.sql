CREATE DATABASE IF NOT EXISTS tiffindb;
USE tiffindb;

CREATE TABLE IF NOT EXISTS tiffin_plans (
    plan_id INT AUTO_INCREMENT PRIMARY KEY,
    plan_name VARCHAR(100) NOT NULL,
    price_per_day DECIMAL(8,2) NOT NULL,
    city VARCHAR(50) NOT NULL
);

DELETE FROM tiffin_plans;

INSERT INTO tiffin_plans (plan_name, price_per_day, city) VALUES
('Basic Lunch', 60, 'Ahmedabad'),
('Healthy Lunch', 90, 'Ahmedabad'),
('Premium Lunch', 120, 'Ahmedabad'),
('Student Dinner', 70, 'Surat'),
('Family Dinner', 150, 'Surat'),
('Gujarati Thali', 100, 'Surat'),
('Office Lunch', 80, 'Rajkot'),
('Monthly Special', 130, 'Rajkot');
