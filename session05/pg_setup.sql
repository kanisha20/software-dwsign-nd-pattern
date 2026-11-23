-- ============================================================
--  PgFinder — Database Setup (Q5)
-- ============================================================

CREATE DATABASE IF NOT EXISTS pgdb;
USE pgdb;

CREATE TABLE IF NOT EXISTS pg_listings (
    listing_id   INT PRIMARY KEY AUTO_INCREMENT,
    pg_name      VARCHAR(100) NOT NULL,
    city         VARCHAR(50)  NOT NULL,
    rent         DOUBLE       NOT NULL,
    photo_path   VARCHAR(255),
    photo_blob   MEDIUMBLOB
);

-- 4 fictional PG listings
INSERT INTO pg_listings (pg_name, city, rent) VALUES
('Sunrise Boys Hostel',    'Ahmedabad', 6500.00),
('Shiv Kripa PG & Rooms',   'Surat',     5500.00),
('Maruti PG for Students',  'Rajkot',    4800.00),
('Galaxy Girls Living',    'Ahmedabad', 7000.00);
