-- V1__Initial_Schema.sql
-- Initial database schema for PPS Project

-- Drop existing tables if they exist
DROP TABLE IF EXISTS Transaction;
DROP TABLE IF EXISTS Follow;
DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS PPS;

-- Create Users table with improved schema
CREATE TABLE Users (
    emailid VARCHAR(50) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,  -- Increased length for password hash
    password2 VARCHAR(255) NOT NULL, -- Increased length for password hash
    fName VARCHAR(50) NOT NULL,      -- Increased length and made required
    lname VARCHAR(50) NOT NULL,      -- Increased length and made required
    address VARCHAR(255) NOT NULL,   -- Increased length and made required
    birthday DATE NOT NULL,          -- Made required
    PPSbalance BIGINT DEFAULT 0,     -- Added default value
    Dollarbal BIGINT DEFAULT 0,      -- Added default value
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT chk_positive_pps CHECK (PPSbalance >= 0),
    CONSTRAINT chk_positive_dollar CHECK (Dollarbal >= 0)
);

-- Create Follow table with improved schema
CREATE TABLE Follow (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- Added primary key
    emailid VARCHAR(50) NOT NULL,
    followerid VARCHAR(50) NOT NULL,       -- Changed to match emailid length
    followdate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (emailid) REFERENCES Users(emailid) ON DELETE CASCADE,
    FOREIGN KEY (followerid) REFERENCES Users(emailid) ON DELETE CASCADE,
    UNIQUE KEY unique_follow (emailid, followerid)  -- Prevent duplicate follows
);

-- Create PPS table for PPS price tracking
CREATE TABLE PPS (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ppsprice INTEGER NOT NULL,
    effective_from TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_positive_price CHECK (ppsprice > 0)
);

-- Create Transaction table with improved schema
CREATE TABLE Transaction (
    transid BIGINT AUTO_INCREMENT PRIMARY KEY,
    transname VARCHAR(50) NOT NULL,
    dollaramt BIGINT NOT NULL DEFAULT 0,
    PPSamt BIGINT NOT NULL DEFAULT 0,
    PPSbal BIGINT NOT NULL DEFAULT 0,
    fromuser VARCHAR(50) NOT NULL,
    touser VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) DEFAULT 'COMPLETED',
    FOREIGN KEY (fromuser) REFERENCES Users(emailid) ON DELETE RESTRICT,
    FOREIGN KEY (touser) REFERENCES Users(emailid) ON DELETE RESTRICT,
    CONSTRAINT chk_valid_transaction CHECK (
        (dollaramt >= 0 AND PPSamt >= 0) AND
        (dollaramt > 0 OR PPSamt > 0)
    )
);

-- Create indexes for better query performance
CREATE INDEX idx_transaction_fromuser ON Transaction(fromuser);
CREATE INDEX idx_transaction_touser ON Transaction(touser);
CREATE INDEX idx_transaction_created_at ON Transaction(created_at);
CREATE INDEX idx_follow_emailid ON Follow(emailid);
CREATE INDEX idx_follow_followerid ON Follow(followerid);
CREATE INDEX idx_follow_date ON Follow(followdate);

-- Insert root user
INSERT INTO Users (
    emailid, 
    password, 
    password2, 
    fName, 
    lname, 
    address, 
    birthday, 
    PPSbalance, 
    Dollarbal
) VALUES (
    'root',
    -- Default password: pass1234 (should be changed in production)
    '$2a$10$8K1p/a0dL1LXMIgoEDFrwOkqrQ1gIO7Pq5PXQxVqboFBGtKw2T0Se',
    '$2a$10$8K1p/a0dL1LXMIgoEDFrwOkqrQ1gIO7Pq5PXQxVqboFBGtKw2T0Se',
    'Root',
    'Admin',
    'System',
    '2000-01-01',
    1000000000000,
    1000000
);

-- Insert initial PPS price
INSERT INTO PPS (ppsprice) VALUES (1);

-- Create views for common queries
CREATE VIEW user_balance_view AS
SELECT 
    emailid,
    fName,
    lname,
    PPSbalance,
    Dollarbal
FROM Users;

CREATE VIEW transaction_summary_view AS
SELECT 
    fromuser,
    touser,
    transname,
    SUM(dollaramt) as total_dollar_amount,
    SUM(PPSamt) as total_pps_amount,
    COUNT(*) as transaction_count
FROM Transaction
GROUP BY fromuser, touser, transname;

CREATE VIEW user_followers_count_view AS
SELECT 
    emailid,
    COUNT(followerid) as follower_count
FROM Users u
LEFT JOIN Follow f ON u.emailid = f.emailid
GROUP BY emailid;

-- Create stored procedures for common operations
DELIMITER //

CREATE PROCEDURE transfer_pps(
    IN p_from_user VARCHAR(50),
    IN p_to_user VARCHAR(50),
    IN p_amount BIGINT,
    OUT p_success BOOLEAN
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SET p_success = FALSE;
    END;

    START TRANSACTION;
    
    -- Check sufficient balance
    IF (SELECT PPSbalance FROM Users WHERE emailid = p_from_user) >= p_amount THEN
        -- Update balances
        UPDATE Users SET PPSbalance = PPSbalance - p_amount WHERE emailid = p_from_user;
        UPDATE Users SET PPSbalance = PPSbalance + p_amount WHERE emailid = p_to_user;
        
        -- Record transaction
        INSERT INTO Transaction (transname, PPSamt, fromuser, touser)
        VALUES ('Transfer', p_amount, p_from_user, p_to_user);
        
        COMMIT;
        SET p_success = TRUE;
    ELSE
        ROLLBACK;
        SET p_success = FALSE;
    END IF;
END //

DELIMITER ;
