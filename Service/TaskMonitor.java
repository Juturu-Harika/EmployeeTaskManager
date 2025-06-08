package EmployeeTaskTrackerSystem.Service;

import EmployeeTaskTrackerSystem.Model.Employee;
import EmployeeTaskTrackerSystem.Model.Task;
import EmployeeTaskTrackerSystem.Model.TaskStatus;
import EmployeeTaskTrackerSystem.TaskRepo.TaskRepository;
import java.time.LocalDate;
import java.util.List;

public class TaskMonitor extends Thread {

    private final TaskRepository<Task> taskRepository;

    public TaskMonitor(TaskRepository<Task> taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("[TaskMonitor] Checking for overdue tasks at " + LocalDate.now() + "...");

            List<Task> allTasks = taskRepository.getAllTasks();

            allTasks.stream()
                    .filter(task -> task.getDueDate().isBefore(LocalDate.now())
                            && task.getStatus() != TaskStatus.Completed)
                    .forEach(task -> {
                        Employee employee = task.getAssignedTo();
                        String empName = (employee != null) ? employee.getName() : "Unassigned";
                        System.out.println("[OVERDUE] " + task + " | Assigned to: " + empName);
                    });

            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                System.out.println("[TaskMonitor] Interrupted. Exiting...");
                break;
            }
        }
    }
}
