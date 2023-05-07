import java.sql.*;
import java.util.Scanner;

// task list

public class Main {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://my_db:3306/my_db";
        String username = "student";
        String password = "pass123";

        try {
            // Class.forName("com.mysql.cj.jdbc.Driver");
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
            
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("1. Add new task");
                System.out.println("2. Delete existing task");
                System.out.println("3. Modify existing task list");
                System.out.println("4. Display task list");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();
                int id;
                switch (choice) {
                    case 1:
                        System.out.print("Enter new task: ");
                        String name = scanner.nextLine();
                        PreparedStatement addStatement = connection.prepareStatement("INSERT INTO tasks(name) VALUES (?)");
                        addStatement.setString(1, name);
                        addStatement.executeUpdate();
                        System.out.println("Task added successfully.");
                        break;
                    case 2:
                        System.out.print("Enter the ID of the task to delete: ");
                        id = scanner.nextInt();
                        scanner.nextLine();
                        PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM tasks WHERE id = ?");
                        deleteStatement.setInt(1, id);
                        deleteStatement.executeUpdate();
                        System.out.println("Item deleted successfully.");
                        break;
                    case 3:
                        System.out.print("Enter the ID of the task to modify: ");
                        int itemId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter the new task name: ");
                        String newName = scanner.nextLine();
                        PreparedStatement modifyStatement = connection.prepareStatement("UPDATE tasks SET name = ? WHERE id = ?");
                        modifyStatement.setString(1, newName);
                        modifyStatement.setInt(2, itemId);
                        modifyStatement.executeUpdate();
                        System.out.println("Task modified successfully.");
                        break;
                    case 4:
                        Statement displayStatement = connection.createStatement();
                        ResultSet resultSet = displayStatement.executeQuery("SELECT * FROM tasks");
                        while (resultSet.next()) {
                            id = resultSet.getInt("id");
                            String itemName = resultSet.getString("name");
                            System.out.println("Task " + id + ": " + itemName);
                        }
                        break;
                    case 5:
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
