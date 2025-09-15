# Employee Management System (Java + Swing + MySQL)

## 📌 Project Overview
This is a simple **Employee Management System** built with:
- **Java (Swing for GUI)**
- **MySQL (JDBC for database connection)**
- **Eclipse IDE**

It includes modules for:
- User login authentication
- Employee insertion and listing
- Attendance tracking
- Salary calculation
- Department management
- Leave management
- Bank, Payroll, and Tax record insertion

---

## ⚙️ Prerequisites
Before running this project, make sure you have:
- **Java JDK 17+**
- **Eclipse IDE** (or any Java IDE)
- **MySQL Server** installed and running
- **MySQL Connector JAR** added to your project’s `Referenced Libraries`

---


## How to Run

- Clone or download this repository.
- Open the project in Eclipse.
- Update your MySQL username & password in:
     static final String URL = "jdbc:mysql://localhost:3306/student";
     static final String USER = "root";
     static final String PWD = "abc123";

Run the project:

- **Start with LoginUI.java or Dashboard.java.
- **Use the default credentials:

     Username: admin
   Password: admin123



## 🗄️ Database Setup
1. Open MySQL and create the database:
   ```sql
   CREATE DATABASE student;
   USE student;
