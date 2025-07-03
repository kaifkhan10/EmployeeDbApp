**EmployeeDBApp**

A simple Java console-based application to manage employees in a MySQL database using JDBC.

🚀 **Features**
Add new employee records
View all employees
Update existing employee data
Delete employee records
Search employees by name
Console menu-driven interface

🛠 **Tools & Technologies Used**
Java (JDK 8 or higher)
MySQL Database
MySQL Connector/J (JDBC driver)
VS Code / Any Java IDE
Command Prompt / Terminal

🏗️ **Database Setup Instructions**
Step 1: Open MySQL Command Line or Workbench and run these commands:

CREATE DATABASE employee_db;
USE employee_db;

CREATE TABLE employees (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    position VARCHAR(100),
    salary DOUBLE
);

⚙️ **Project Setup Instructions**

Step 2: Download MySQL Connector/J
Download from: https://dev.mysql.com/downloads/connector/j/
Extract the zip file.
Use the .jar file (Example: mysql-connector-j-8.3.0.jar).

Step 3: Clone this Repository
git clone https://github.com/yourusername/EmployeeDBApp.git

Step 4: Go to the project directory
cd EmployeeDBApp

Step 5: Compile the Java file
javac -cp ".;mysql-connector-j-8.3.0.jar" EmployeeDBApp.java

Step 6: Run the Java program
java -cp ".;mysql-connector-j-8.3.0.jar" EmployeeDBApp

🔑 **Update your database connection**
In EmployeeDBApp.java, update your MySQL username and password:

static final String JDBC_URL = "jdbc:mysql://localhost:3306/employee_db";
static final String DB_USER = "root";
static final String DB_PASSWORD = "your_password";

📋 **Example Console Output**
=== Employee DB Menu ===
1. Add Employee
2. View All Employees
3. Update Employee
4. Delete Employee
5. Search Employee by Name
6. Exit

👨‍💻 **Author**
*Kaif Khan*

📄 **License**
This project is open-source for learning and personal use.
