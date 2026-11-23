-- ============================================================
--  Q6 Database Setup: pgdb + pg_listings + enquiries table
-- ============================================================

CREATE DATABASE IF NOT EXISTS pgdb;
USE pgdb;

-- 1. PG Listings table
CREATE TABLE IF NOT EXISTS pg_listings (
    listing_id   INT PRIMARY KEY AUTO_INCREMENT,
    pg_name      VARCHAR(100) NOT NULL,
    city         VARCHAR(50)  NOT NULL,
    rent         INT          NOT NULL,
    photo_path   VARCHAR(255),
    photo_blob   MEDIUMBLOB
);

-- Seed listings if empty
INSERT INTO pg_listings (listing_id, pg_name, city, rent) VALUES
(1, 'Sunrise Boys Hostel',   'Ahmedabad', 6500),
(2, 'Shiv Kripa PG & Rooms',  'Surat',     5500),
(3, 'Maruti PG for Students', 'Rajkot',    4800),
(4, 'Galaxy Girls Living',   'Ahmedabad', 7000)
ON DUPLICATE KEY UPDATE pg_name = VALUES(pg_name);

-- 2. Enquiries table (Task 3)
CREATE TABLE IF NOT EXISTS enquiries (
    enquiry_id    INT PRIMARY KEY AUTO_INCREMENT,
    student_name  VARCHAR(100) NOT NULL,
    student_email VARCHAR(100) NOT NULL,
    listing_id    INT          NOT NULL,
    enquiry_date  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (listing_id) REFERENCES pg_listings(listing_id)
);
