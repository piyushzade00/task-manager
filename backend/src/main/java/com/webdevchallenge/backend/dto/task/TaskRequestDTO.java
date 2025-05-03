package com.webdevchallenge.backend.dto.task;

import com.webdevchallenge.backend.enums.TaskPriority;
import com.webdevchallenge.backend.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequestDTO {

    @NotBlank(message = "Description cannot be blank.")
    @Size(min = 4, max = 50, message = "Description must be at least 4 and less than 50 characters.")
    private String description;

    private TaskStatus status;

    private TaskPriority priority;

    private LocalDateTime deadline;

    private Long userId;
}
