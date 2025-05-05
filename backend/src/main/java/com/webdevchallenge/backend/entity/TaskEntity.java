package com.webdevchallenge.backend.entity;

import com.webdevchallenge.backend.enums.TaskPriority;
import com.webdevchallenge.backend.enums.TaskStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Where(clause = "is_deleted = false")
@Entity
@Table(name = "tasks")
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long taskId;

    @NotNull(message = "Description cannot be null.")
    @Size(min = 4, max = 50, message = "Description must be between 4 and 50 characters.")
    @Column(name = "description", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Task status is required.")
    @Column(name = "status", nullable = false)
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Task priority is required.")
    @Column(name = "priority", nullable = false)
    private TaskPriority priority;

    @Column(name="task_deadline")
    private LocalDateTime deadline;

    @CreationTimestamp
    @Column(name="created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @Column(name="isDeleted")
    private boolean isDeleted = false;

    // Reference to User (One User can have multiple tasks)
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "User must be associated with the task.")
    private UserEntity createdBy;

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public TaskEntity(Long taskId, String description, TaskStatus status, TaskPriority priority, LocalDateTime deadline, LocalDateTime createdAt, LocalDateTime updatedAt, boolean isDeleted, UserEntity createdBy) {
        this.taskId = taskId;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.deadline = deadline;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isDeleted = isDeleted;
        this.createdBy = createdBy;
    }

    public TaskEntity(){}

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public @NotNull(message = "Description cannot be null.") @Size(min = 4, max = 50, message = "Description must be between 4 and 50 characters.") String getDescription() {
        return description;
    }

    public void setDescription(@NotNull(message = "Description cannot be null.") @Size(min = 4, max = 50, message = "Description must be between 4 and 50 characters.") String description) {
        this.description = description;
    }

    public @NotNull(message = "Task status is required.") TaskStatus getStatus() {
        return status;
    }

    public void setStatus(@NotNull(message = "Task status is required.") TaskStatus status) {
        this.status = status;
    }

    public @NotNull(message = "Task priority is required.") TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(@NotNull(message = "Task priority is required.") TaskPriority priority) {
        this.priority = priority;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public @NotNull(message = "User must be associated with the task.") UserEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(@NotNull(message = "User must be associated with the task.") UserEntity createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
