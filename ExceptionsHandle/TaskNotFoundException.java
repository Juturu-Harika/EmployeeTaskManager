package EmployeeTaskTrackerSystem.ExceptionsHandle;

public class TaskNotFoundException extends Exception {
    public TaskNotFoundException(String message) {
        super(message);
    }
}
