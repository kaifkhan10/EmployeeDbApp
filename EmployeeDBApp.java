import java.sql.*;
import java.util.Scanner;

public class EmployeeDBApp {

    static final String JDBC_URL = "jdbc:mysql://localhost:3306/employee_db";
    static final String DB_USER = "root";
    static final String DB_PASSWORD = "kaifsql"; // MySQL password

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("✅ Connected to database!");

            while (true) {
                showMenu();
                int choice = getIntInput(scanner, "Enter your choice: ");

                switch (choice) {
                    case 1: addEmployee(conn, scanner); break;
                    case 2: viewEmployees(conn); break;
                    case 3: updateEmployee(conn, scanner); break;
                    case 4: deleteEmployee(conn, scanner); break;
                    case 5: searchEmployee(conn, scanner); break;
                    case 6:
                        System.out.print("Are you sure you want to exit? (y/n): ");
                        String confirm = scanner.next().toLowerCase();
                        if (confirm.equals("y") || confirm.equals("yes")) {
                            System.out.println("👋 Exiting...");
                            return;
                        }
                        break;
                    default: System.out.println(" Invalid choice! Please try again.");
                }
            }

        } catch (SQLException e) {
            System.out.println(" Database connection error: " + e.getMessage());
        }
    }

    static void showMenu() {
        System.out.println("\n=== Employee DB Menu ===");
        System.out.println("1. Add Employee");
        System.out.println("2. View All Employees");
        System.out.println("3. Update Employee");
        System.out.println("4. Delete Employee");
        System.out.println("5. Search Employee by Name");
        System.out.println("6. Exit");
    }

    static int getIntInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(" Invalid input. Please enter a number.");
            }
        }
    }

    static void addEmployee(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter position: ");
        String position = scanner.nextLine();
        double salary = getDoubleInput(scanner,"Enter salary: ");

        String sql = "INSERT INTO employees (name, position, salary) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, position);
            stmt.setDouble(3, salary);
            stmt.executeUpdate();
            System.out.println("✅ Employee added successfully!");
        }
    }

    static void viewEmployees(Connection conn) throws SQLException {
        String sql = "SELECT * FROM employees";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n--- Employee List ---");
            while (rs.next()) {
                System.out.printf("ID: %d | Name: %s | Position: %s | Salary: %.2f%n",
                        rs.getInt("id"), rs.getString("name"),
                        rs.getString("position"), rs.getDouble("salary"));
            }
        }
    }

    static void updateEmployee(Connection conn, Scanner scanner) throws SQLException {
        int id = getIntInput(scanner, "Enter employee ID to update: ");
        System.out.print("Enter new name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new position: ");
        String position = scanner.nextLine();
        double salary = getDoubleInput(scanner, "Enter new salary: ");

        String sql = "UPDATE employees SET name=?, position=?, salary=? WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, position);
            stmt.setDouble(3, salary);
            stmt.setInt(4, id);
            int rows = stmt.executeUpdate();
            System.out.println(rows > 0 ? "✅ Employee updated!" : " Employee ID not found.");
        }
    }

    static void deleteEmployee(Connection conn, Scanner scanner) throws SQLException {
        int id = getIntInput(scanner, "Enter employee ID to delete: ");
        String sql = "DELETE FROM employees WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            System.out.println(rows > 0 ? "✅ Employee deleted!" : " Employee ID not found.");
        }
    }

    static void searchEmployee(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter employee name to search: ");
        String name = scanner.nextLine();
        String sql = "SELECT * FROM employees WHERE name LIKE ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + name + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                System.out.println("\n--- Search Results ---");
                boolean found = false;
                while (rs.next()) {
                    System.out.printf("ID: %d | Name: %s | Position: %s | Salary: %.2f%n",
                            rs.getInt("id"), rs.getString("name"),
                            rs.getString("position"), rs.getDouble("salary"));
                    found = true;
                }
                if (!found) System.out.println(" No employee found with that name.");
            }
        }
    }

    static double getDoubleInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(" Invalid input. Please enter a number.");
            }
        }
    }
}
