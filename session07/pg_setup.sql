-- ============================================================
--  Q7 Database Setup: pgdb + owners table
-- ============================================================

CREATE DATABASE IF NOT EXISTS pgdb;
USE pgdb;

-- 1. Owners table for OTP Authentication (Task 2)
CREATE TABLE IF NOT EXISTS owners (
    owner_id    INT PRIMARY KEY AUTO_INCREMENT,
    owner_name  VARCHAR(100) NOT NULL,
    email       VARCHAR(100) NOT NULL UNIQUE,
    phone       VARCHAR(20)  NOT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 3 fictional owners (one with customizable personal email)
INSERT INTO owners (owner_name, email, phone) VALUES
('Dipak Patel (Self)',     'dipak.owner@gmail.com', '9876543210'),
('Rameshwar Properties',   'rameshwar.pg@gmail.com', '9123456780'),
('Kavya Accommodations',   'kavya.hostels@gmail.com', '9898989898')
ON DUPLICATE KEY UPDATE owner_name = VALUES(owner_name);

-- 2. PG Listings table
CREATE TABLE IF NOT EXISTS pg_listings (
    listing_id   INT PRIMARY KEY AUTO_INCREMENT,
    pg_name      VARCHAR(100) NOT NULL,
    city         VARCHAR(50)  NOT NULL,
    rent         INT          NOT NULL
);
