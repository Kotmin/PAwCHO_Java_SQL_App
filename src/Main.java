import java.sql.*;
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
