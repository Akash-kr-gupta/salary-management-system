CREATE DATABASE student;
USE student;

CREATE TABLE login (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL
);

INSERT INTO login (username, password) VALUES ('admin', 'admin123');
