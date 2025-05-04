package com.webdevchallenge.backend.mapper;

import com.webdevchallenge.backend.dto.task.TaskRequestDTO;
import com.webdevchallenge.backend.dto.task.TaskResponseDTO;
import com.webdevchallenge.backend.entity.TaskEntity;
import com.webdevchallenge.backend.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public TaskEntity toEntity(TaskRequestDTO taskRequestDTO, TaskEntity taskEntity, UserEntity userEntity) {
        if(taskRequestDTO == null || userEntity == null)
            return null;

        taskEntity.setDescription(taskRequestDTO.getDescription());
        taskEntity.setStatus(taskRequestDTO.getStatus());
        taskEntity.setPriority(taskRequestDTO.getPriority());
        taskEntity.setDeadline(taskRequestDTO.getDeadline());
        taskEntity.setCreatedBy(userEntity);

        return taskEntity;
    }

    public TaskResponseDTO toTaskResponseDTO(TaskEntity taskEntity){
        if (taskEntity == null)
            return null;

        return new TaskResponseDTO(
                taskEntity.getTaskId(),
                taskEntity.getDescription(),
                taskEntity.getStatus(),
                taskEntity.getPriority(),
                taskEntity.getDeadline(),
                taskEntity.getCreatedBy().getUserId(),
                taskEntity.getCreatedAt(),
                taskEntity.getUpdatedAt(),
                taskEntity.isDeleted()
        );
    }
}
