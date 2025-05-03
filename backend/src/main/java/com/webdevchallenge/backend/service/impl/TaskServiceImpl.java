package com.webdevchallenge.backend.service.impl;

import com.webdevchallenge.backend.dto.task.TaskRequestDTO;
import com.webdevchallenge.backend.dto.task.TaskResponseDTO;
import com.webdevchallenge.backend.mapper.TaskMapper;
import com.webdevchallenge.backend.repository.TaskRepository;
import com.webdevchallenge.backend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO) {
        return null;
    }

    @Override
    public TaskResponseDTO updateTask(Long taskId, TaskRequestDTO taskRequestDTO) {
        return null;
    }

    @Override
    public TaskResponseDTO getTaskById(Long taskId) {
        return null;
    }

    @Override
    public List<TaskResponseDTO> getTaskByIdForUser(Long taskId, Long userId) {
        return List.of();
    }

    @Override
    public List<TaskResponseDTO> getAllTasksForUser(Long userId) {
        return List.of();
    }

    @Override
    public List<TaskResponseDTO> getActiveTasksForUser(Long userId) {
        return List.of();
    }

    @Override
    public List<TaskResponseDTO> getTasksDueToday(Long userId) {
        return List.of();
    }

    @Override
    public void softDeleteTask(Long taskId) {

    }
}
