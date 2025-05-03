package com.webdevchallenge.backend.mapper;

import com.webdevchallenge.backend.dto.task.TaskRequestDTO;
import com.webdevchallenge.backend.dto.task.TaskResponseDTO;
import com.webdevchallenge.backend.entity.TaskEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public TaskEntity toEntity(TaskRequestDTO taskRequestDTO){
        if(taskRequestDTO == null)
            return null;

        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setDescription(taskRequestDTO.getDescription());
        taskEntity.setStatus(taskRequestDTO.getStatus());
        taskEntity.setPriority(taskRequestDTO.getPriority());
        taskEntity.setDeadline(taskRequestDTO.getDeadline());

        return taskEntity;
    }

    public TaskResponseDTO toTaskResponseDTO(TaskEntity taskEntity){
        if (taskEntity == null)
            return null;

        return new TaskResponseDTO(
                taskEntity.getDescription(),
                taskEntity.getStatus(),
                taskEntity.getPriority(),
                taskEntity.getDeadline(),
                taskEntity.getUser().getUserId(),
                taskEntity.getCreatedAt(),
                taskEntity.getUpdatedAt(),
                taskEntity.isDeleted()
        );
    }
}
