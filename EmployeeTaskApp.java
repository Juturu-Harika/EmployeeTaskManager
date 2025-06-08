package EmployeeTaskTrackerSystem;

import EmployeeTaskTrackerSystem.ExceptionsHandle.TaskNotFoundException;
import EmployeeTaskTrackerSystem.Model.Employee;
import EmployeeTaskTrackerSystem.Model.TaskStatus;
import EmployeeTaskTrackerSystem.Model.Task;
import EmployeeTaskTrackerSystem.TaskRepo.TaskRepository;
import EmployeeTaskTrackerSystem.Service.TaskManager;
import EmployeeTaskTrackerSystem.Service.TaskMonitor;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class EmployeeTaskApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaskRepository<Task> taskRepo = new TaskRepository<>();
        TaskManager manager = new TaskManager(taskRepo);
        TaskMonitor monitor = new TaskMonitor(taskRepo);
        monitor.setDaemon(true);
        monitor.start();

        while (true) {
            System.out.println("Employee Task Tracker Menu");
            System.out.println("1. Add Employee");
            System.out.println("2. Add Task to Employee");
            System.out.println("3. View Tasks for Employee");
            System.out.println("4. View Tasks Due Tomorrow");
            System.out.println("5. View Employees with > 3 Pending Tasks");
            System.out.println("6. Sort Tasks by Priority");
            System.out.println("7. Sort Tasks by Due Date");
            System.out.println("8. Search Tasks by Keyword");
            System.out.println("9. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter Employee ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Employee Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Department: ");
                    String dept = scanner.nextLine();
                    manager.addEmployee(new Employee(id, name, dept));
                }
                case 2 -> {
                    System.out.print("Enter Employee ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    Employee emp = manager.findEmployeeById(id);
                    if (emp != null) {
                        System.out.print("Enter Task ID: ");
                        int tid = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter Description: ");
                        String desc = scanner.nextLine();
                        System.out.print("Enter Priority (higher number = higher priority): ");
                        int prio = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter Due Date (YYYY-MM-DD): ");
                        LocalDate dueDate = LocalDate.parse(scanner.nextLine());
                        Task task = new Task(tid, desc, dueDate, prio, TaskStatus.Pending, null);
                        task.setAssignedTo(emp);
                        manager.assignTask(emp, task);
                    } else {
                        System.out.println("Employee not found.");
                    }
                }
                case 3 -> {
                    System.out.print("Enter Employee ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    Employee emp = manager.findEmployeeById(id);
                    if (emp != null) {
                        List<Task> tasks = manager.getTasksForEmployee(emp);
                        tasks.forEach(System.out::println);
                    } else {
                        System.out.println("Employee not found.");
                    }
                }
                case 4 -> manager.getTasksDueTomorrow().forEach(System.out::println);
                case 5 -> manager.getEmployeesWithMoreThan3PendingTasks()
                        .forEach(System.out::println);
                case 6 -> {
                    System.out.print("Enter Employee ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    Employee emp = manager.findEmployeeById(id);
                    if (emp != null) {
                        manager.getTasksSortedByPriority(emp)
                                .forEach(System.out::println);
                    } else {
                        System.out.println("Employee not found.");
                    }
                }
                case 7 -> {
                    System.out.print("Enter Employee ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    Employee emp = manager.findEmployeeById(id);
                    if (emp != null) {
                        manager.getTasksSortedByDueDate(emp)
                                .forEach(System.out::println);
                    } else {
                        System.out.println("Employee not found.");
                    }
                }
                case 8 -> {
                    System.out.print("Enter keyword to search: ");
                    String keyword = scanner.nextLine();
                    manager.searchTasksByKeyword(keyword)
                            .forEach(System.out::println);
                }
                case 9 -> {
                    System.out.println("Exiting application. Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }
}
