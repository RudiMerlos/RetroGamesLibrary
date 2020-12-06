CREATE TABLE IF NOT EXISTS user (
    email VARCHAR(100) PRIMARY KEY,
    passw VARCHAR(16) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(200),
    birthdate DATE
);