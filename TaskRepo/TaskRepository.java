package EmployeeTaskTrackerSystem.TaskRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import EmployeeTaskTrackerSystem.ExceptionsHandle.TaskNotFoundException;

public class TaskRepository<T> {
    private List<T> taskList = new ArrayList<>();

    public void addTask(T task) {
        taskList.add(task);
    }

    public void removeTask(T task) throws TaskNotFoundException {
        if (!taskList.remove(task)) {
            throw new TaskNotFoundException("Task not found for removal: " + task);
        }
    }

    public T findTask(Predicate<T> condition) throws TaskNotFoundException {
        return taskList.stream().filter(condition).findFirst().orElseThrow(() -> new TaskNotFoundException("Task not found with given condition"));
    }

    public List<T> getAllTasks() {
        return new ArrayList<>(taskList);
    }
}
