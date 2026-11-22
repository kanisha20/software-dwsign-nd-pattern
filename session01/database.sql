CREATE DATABASE IF NOT EXISTS gymdb;
USE gymdb;

CREATE TABLE IF NOT EXISTS members (
    member_id INT AUTO_INCREMENT PRIMARY KEY,
    member_name VARCHAR(50),
    city VARCHAR(50),
    plan_months INT,
    fee DOUBLE
);

INSERT INTO members (member_name, city, plan_months, fee)
VALUES
('Amit', 'Ahmedabad', 6, 4500),
('Ravi', 'Ahmedabad', 3, 2500),
('Neha', 'Surat', 6, 4500),
('Priya', 'Surat', 12, 8000),
('Raj', 'Rajkot', 3, 2500),
('Kiran', 'Rajkot', 12, 8000);
