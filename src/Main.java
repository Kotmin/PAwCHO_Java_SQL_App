// import java.sql.*;
// import java.util.Scanner;

// // task list

// public class Main {
//     public static void main(String[] args) {
//         String jdbcUrl = "jdbc:mysql://my_db:3306/my_db";
//         String username = "student";
//         String password = "pass123";

//         try {
//             Class.forName("com.mysql.cj.jdbc.Driver");
//             // Class.forName("com.mysql.jdbc.Driver");
//             Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
            
//             Scanner scanner = new Scanner(System.in);
//             while (true) {
//                 System.out.println("1. Add new task");
//                 System.out.println("2. Delete existing task");
//                 System.out.println("3. Modify existing task list");
//                 System.out.println("4. Display task list");
//                 System.out.println("5. Exit");
//                 System.out.print("Enter your choice: ");
//                 int choice = scanner.nextInt();
//                 scanner.nextLine();
//                 int id;
//                 switch (choice) {
//                     case 1:
//                         System.out.print("Enter new task: ");
//                         String name = scanner.nextLine();
//                         PreparedStatement addStatement = connection.prepareStatement("INSERT INTO tasks(name) VALUES (?)");
//                         addStatement.setString(1, name);
//                         addStatement.executeUpdate();
//                         System.out.println("Task added successfully.");
//                         break;
//                     case 2:
//                         System.out.print("Enter the ID of the task to delete: ");
//                         id = scanner.nextInt();
//                         scanner.nextLine();
//                         PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM tasks WHERE id = ?");
//                         deleteStatement.setInt(1, id);
//                         deleteStatement.executeUpdate();
//                         System.out.println("Item deleted successfully.");
//                         break;
//                     case 3:
//                         System.out.print("Enter the ID of the task to modify: ");
//                         int itemId = scanner.nextInt();
//                         scanner.nextLine();
//                         System.out.print("Enter the new task name: ");
//                         String newName = scanner.nextLine();
//                         PreparedStatement modifyStatement = connection.prepareStatement("UPDATE tasks SET name = ? WHERE id = ?");
//                         modifyStatement.setString(1, newName);
//                         modifyStatement.setInt(2, itemId);
//                         modifyStatement.executeUpdate();
//                         System.out.println("Task modified successfully.");
//                         break;
//                     case 4:
//                         Statement displayStatement = connection.createStatement();
//                         ResultSet resultSet = displayStatement.executeQuery("SELECT * FROM tasks");
//                         while (resultSet.next()) {
//                             id = resultSet.getInt("id");
//                             String itemName = resultSet.getString("name");
//                             System.out.println("Task " + id + ": " + itemName);
//                         }
//                         break;
//                     case 5:
//                         System.exit(0);
//                     default:
//                         System.out.println("Invalid choice. Please try again.");
//                 }
//             }
//         } catch (ClassNotFoundException | SQLException e) {
//             e.printStackTrace();
//         }
//     }
// }


// Second approach

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaskController taskController = new TaskController();

        while (true) {
            System.out.println("CRUD Application");
            System.out.println("----------------");
            System.out.println("1. Create task");
            System.out.println("2. Read task");
            System.out.println("3. Update task");
            System.out.println("4. Delete task");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter task name: ");
                    String taskName = scanner.nextLine();
                    System.out.print("Enter task income: ");
                    double taskIncome = scanner.nextDouble();
                    scanner.nextLine(); // Consume the newline character
                    taskController.createTask(taskName, taskIncome);
                    System.out.println("Task created successfully.");
                    break;
                case 2:
                    System.out.print("Enter task ID: ");
                    int taskId = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character
                    Task task = taskController.readTask(taskId);
                    if (task != null) {
                        System.out.println("Task ID: " + task.getId());
                        System.out.println("Task Name: " + task.getName());
                        System.out.println("Task Income: " + task.getIncome());
                    } else {
                        System.out.println("Task not found.");
                    }
                    break;
                case 3:
                    System.out.print("Enter task ID: ");
                    int updateTaskId = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character
                    System.out.print("Enter new task name: ");
                    String updatedTaskName = scanner.nextLine();
                    System.out.print("Enter new task income: ");
                    double updatedTaskIncome = scanner.nextDouble();
                    scanner.nextLine(); // Consume the newline character
                    if (taskController.updateTask(updateTaskId, updatedTaskName, updatedTaskIncome)) {
                        System.out.println("Task updated successfully.");
                    } else {
                        System.out.println("Task not found.");
                    }
                    break;
                case 4:
                    System.out.print("Enter task ID: ");
                    int deleteTaskId = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character
                    if (taskController.deleteTask(deleteTaskId)) {
                        System.out.println("Task deleted successfully.");
                    } else {
                        System.out.println("Task not found.");
                    }
                    break;
                case 5:
                    System.out.println("Exiting the application. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            System.out.println();
        }
    }
}

class Task {
    private int id;
    private String name;
    private double income;

    public Task(int id, String name, double income) {
        this.id = id;
        this.name = name;
        this.income = income;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getIncome() {
        return income;
    }
}

import java.sql.*;

class TaskController {
    private static final String DB_URL = "jdbc:mysql://my_db:3306/my_db";
    private static final String DB_USERNAME = "student";
    private static final String DB_PASSWORD = "pass123";

    public void createTask(String name, double income) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String query = "INSERT INTO tasks (name, income) VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, name);
            statement.setDouble(2, income);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Task readTask(int id) {
        Task task = null;
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String query = "SELECT * FROM tasks WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int taskId = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double income = resultSet.getDouble("income");
                task = new Task(taskId, name, income);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return task;
    }

    public boolean updateTask(int id, String name, double income) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String query = "UPDATE tasks SET name = ?, income = ? WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, name);
            statement.setDouble(2, income);
            statement.setInt(3, id);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteTask(int id) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String query = "DELETE FROM tasks WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
