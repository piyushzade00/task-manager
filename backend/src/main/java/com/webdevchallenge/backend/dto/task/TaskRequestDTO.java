package com.webdevchallenge.backend.dto.task;

import com.webdevchallenge.backend.enums.TaskPriority;
import com.webdevchallenge.backend.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class TaskRequestDTO {

    @NotBlank(message = "Description cannot be blank.")
    @Size(min = 4, max = 50, message = "Description must be at least 4 and less than 50 characters.")
    private String description;

    private TaskStatus status;

    private TaskPriority priority;

    private LocalDateTime deadline;

    private Long userId;

    public TaskRequestDTO(String description, TaskStatus status, TaskPriority priority, LocalDateTime deadline, Long userId) {
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.deadline = deadline;
        this.userId = userId;
    }

    public @NotBlank(message = "Description cannot be blank.") @Size(min = 4, max = 50, message = "Description must be at least 4 and less than 50 characters.") String getDescription() {
        return description;
    }

    public void setDescription(@NotBlank(message = "Description cannot be blank.") @Size(min = 4, max = 50, message = "Description must be at least 4 and less than 50 characters.") String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
