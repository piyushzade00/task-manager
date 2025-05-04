package com.webdevchallenge.backend.entity;

import com.webdevchallenge.backend.enums.TaskPriority;
import com.webdevchallenge.backend.enums.TaskStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Where(clause = "is_deleted = false")
@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
}
