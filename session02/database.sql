CREATE DATABASE IF NOT EXISTS tiffindb;
USE tiffindb;

CREATE TABLE IF NOT EXISTS tiffin_plans (
    planId INT AUTO_INCREMENT PRIMARY KEY,
    planName VARCHAR(100) NOT NULL,
    pricePerDay DOUBLE NOT NULL,
    city VARCHAR(50) NOT NULL
);

INSERT INTO tiffin_plans (planName, pricePerDay, city) VALUES
('Basic Veg', 60, 'Ahmedabad'),
('Standard Veg', 80, 'Ahmedabad'),
('Premium Veg', 100, 'Surat'),
('Gujarati Thali', 120, 'Surat'),
('Special Thali', 150, 'Rajkot'),
('Mini Thali', 70, 'Rajkot'),
('Healthy Meal', 90, 'Ahmedabad'),
('Family Meal', 140, 'Surat');
