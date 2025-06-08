package EmployeeTaskTrackerSystem.Service;

import EmployeeTaskTrackerSystem.Model.Employee;
import EmployeeTaskTrackerSystem.Model.Task;
import EmployeeTaskTrackerSystem.Model.TaskStatus;
import EmployeeTaskTrackerSystem.TaskRepo.TaskRepository;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class TaskManager {

    private final TaskRepository<Employee> employeeRepo = new TaskRepository<>();
    private TaskRepository<Task> taskRepo = new TaskRepository<>();

    public TaskManager(TaskRepository<Task> taskRepo) {
        this.taskRepo=taskRepo;
    }

    public void addEmployee(Employee employee) {
        employeeRepo.addTask(employee);
    }

    public Employee findEmployeeById(int id) {
        try {
            return employeeRepo.findTask(emp -> emp.getId() == id);
        } catch (Exception e) {
            return null;
        }
    }

    public void assignTask(Employee employee, Task task) {
        task.setAssignedTo(employee);
        taskRepo.addTask(task);
    }

    public List<Task> getTasksForEmployee(Employee employee) {
        return taskRepo.getAllTasks().stream()
                .filter(task -> task.getAssignedTo().equals(employee))
                .collect(Collectors.toList());
    }

    public List<Task> getTasksSortedByPriority(Employee employee) {
        return getTasksForEmployee(employee).stream()
                .sorted(Comparator.comparingInt(Task::getPriority).reversed())
                .collect(Collectors.toList());
    }

    public List<Task> getTasksSortedByDueDate(Employee employee) {
        return getTasksForEmployee(employee).stream()
                .sorted(Comparator.comparing(Task::getDueDate))
                .collect(Collectors.toList());
    }

    public List<Task> searchTasksByKeyword(String keyword) {
        return taskRepo.getAllTasks().stream()
                .filter(task -> task.getDescription().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Task> getTasksDueTomorrow() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        return taskRepo.getAllTasks().stream()
                .filter(task -> task.getDueDate().equals(tomorrow))
                .collect(Collectors.toList());
    }

    public List<Employee> getEmployeesWithMoreThan3PendingTasks() {
        return employeeRepo.getAllTasks().stream()
                .filter(emp -> getTasksForEmployee(emp).stream()
                        .filter(task -> task.getStatus() == TaskStatus.Pending)
                        .count() > 3)
                .collect(Collectors.toList());
    }
}
