package EmployeeTaskTrackerSystem.Model;

import java.time.LocalDate;
import java.util.Objects;

public class Task implements Comparable<Task>{
    private long id;
    private String description;
    private LocalDate dueDate;
    private int priority;
    private TaskStatus status;
    private Employee assignedTo;

    public Task(long id, String description, LocalDate dueDate, int priority, TaskStatus status, Employee assignedTo){
        this.id=id;
        this.description=description;
        this.dueDate=dueDate;
        this.priority=priority;
        this.status=status;
        this.assignedTo=null;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }


    @Override
    public int compareTo(Task other) {
        return Integer.compare(other.priority, this.priority);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", desc='" + description + '\'' +
                ", status=" + status +
                ", due=" + dueDate +
                ", priority=" + priority +
                '}';
    }

    public Employee getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(Employee assignedTo) {
        this.assignedTo = assignedTo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}
