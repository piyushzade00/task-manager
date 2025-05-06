package com.webdevchallenge.backend.service.impl;

import com.webdevchallenge.backend.dto.task.TaskRequestDTO;
import com.webdevchallenge.backend.dto.task.TaskResponseDTO;
import com.webdevchallenge.backend.entity.TaskEntity;
import com.webdevchallenge.backend.entity.UserEntity;
import com.webdevchallenge.backend.exception.ResourceNotFoundException;
import com.webdevchallenge.backend.mapper.TaskMapper;
import com.webdevchallenge.backend.repository.TaskRepository;
import com.webdevchallenge.backend.repository.UserRepository;
import com.webdevchallenge.backend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO) {
        if(taskRequestDTO == null)
            throw new IllegalArgumentException("Task data cannot be empty");

        String description = taskRequestDTO.getDescription().trim();
        if(description.isEmpty())
            throw new ResourceNotFoundException("Description cannot be empty");
        else if(description.length() < 4)
            throw new ResourceNotFoundException("Description cannot be less than 4 characters");
        else if(description.length() > 50)
            throw new ResourceNotFoundException("Description cannot be longer than 50 characters");
        else if(!(taskRequestDTO.getDeadline() == null))
        {
            LocalDate deadline = LocalDate.from(taskRequestDTO.getDeadline());

            if(deadline.isBefore(LocalDateTime.now().toLocalDate()))
                return null;
        }

        UserEntity userEntity = userRepository.findById(taskRequestDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        TaskEntity taskEntity = taskMapper.toEntity(taskRequestDTO,new TaskEntity(),userEntity);
        TaskEntity savedTaskEntity = taskRepository.save(taskEntity);

        return taskMapper.toTaskResponseDTO(savedTaskEntity);
    }

    @Override
    public TaskResponseDTO updateTask(Long taskId, TaskRequestDTO taskRequestDTO) {
       String description = taskRequestDTO.getDescription().trim();
       LocalDate deadline = LocalDate.from(taskRequestDTO.getDeadline());

        if(description.isEmpty())
            throw new ResourceNotFoundException("Description cannot be empty");
        else if(description.length() < 4)
            throw new ResourceNotFoundException("Description cannot be less than 4 characters");
        else if(description.length() > 50)
            throw new ResourceNotFoundException("Description cannot be longer than 50 characters");
        else if(deadline.isBefore(LocalDateTime.now().toLocalDate()))
            throw new IllegalArgumentException("Deadline cannot be before current time.");

        TaskEntity existingTaskEntity = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        UserEntity userEntity = userRepository.findById(taskRequestDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        TaskEntity taskEntity = taskMapper.toEntity(taskRequestDTO,existingTaskEntity,userEntity);
        TaskEntity savedTaskEntity = taskRepository.save(taskEntity);

        return taskMapper.toTaskResponseDTO(savedTaskEntity);
    }

    @Override
    public TaskResponseDTO getTaskById(Long taskId) {
        if(taskId == null)
            return null;

        TaskEntity taskEntity = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        return taskMapper.toTaskResponseDTO(taskEntity);
    }

    @Override
    public TaskResponseDTO getTaskByIdForUser(Long taskId, Long userId) {
        if(taskId == null)
            throw new ResourceNotFoundException("Task not found");
        else if(userId == null)
            throw new ResourceNotFoundException("User not found");

        TaskEntity taskEntity = taskRepository.findByTaskIdAndCreatedByUserId(taskId,userId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        return taskMapper.toTaskResponseDTO(taskEntity);
    }

    @Override
    public List<TaskResponseDTO> getAllTasksForUser(Long userId) {
        if(userId == null)
            return null;

        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<TaskEntity> taskEntityDTOS = taskRepository.findByCreatedByUserId(userId);

        List<TaskResponseDTO> taskResponseDTOS = new ArrayList<>();
        for(TaskEntity taskEntity : taskEntityDTOS)
            taskResponseDTOS.add(taskMapper.toTaskResponseDTO(taskEntity));

        return taskResponseDTOS;
    }

    @Override
    public List<TaskResponseDTO> getActiveTasksForUser(Long userId) {
        if(userId == null)
            return null;

        List<TaskEntity> taskEntityList = taskRepository.findByCreatedByUserIdAndIsDeletedFalse(userId);

        List<TaskResponseDTO> taskResponseDTOS = new ArrayList<>();

        for (TaskEntity taskEntity : taskEntityList) {
            taskResponseDTOS.add(taskMapper.toTaskResponseDTO(taskEntity));
        }

        return taskResponseDTOS;
    }

    @Override
    public List<TaskResponseDTO> getTasksDueToday(Long userId) {
        if(userId == null)
            return null;

        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<TaskEntity> taskEntityDTOS = taskRepository.findByCreatedByUserId(userId);

        List<TaskResponseDTO> taskResponseDTOS = new ArrayList<>();
        for(TaskEntity taskEntity : taskEntityDTOS)
        {
            if(taskEntity.getDeadline().getDayOfYear() == LocalDateTime.now().getDayOfYear())
                taskResponseDTOS.add(taskMapper.toTaskResponseDTO(taskEntity));
        }

        return taskResponseDTOS;
    }

    @Override
    public boolean softDeleteTask(Long taskId) {
        if(taskId == null)
            throw new ResourceNotFoundException("Task not found");

        TaskEntity taskEntity = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        taskEntity.setDeleted(true);

        taskRepository.save(taskEntity);

        return true;
    }
}
