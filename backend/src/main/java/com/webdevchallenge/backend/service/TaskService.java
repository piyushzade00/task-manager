package com.webdevchallenge.backend.service;

import com.webdevchallenge.backend.dto.task.TaskRequestDTO;
import com.webdevchallenge.backend.dto.task.TaskResponseDTO;

import java.util.List;

public interface TaskService {

    TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO);

    TaskResponseDTO updateTask(Long taskId, TaskRequestDTO taskRequestDTO);

    List<TaskResponseDTO> getAllTasksForUser(Long userId);

    TaskResponseDTO getTaskByIdForUser(Long taskId, Long userId);

    TaskResponseDTO getTaskById(Long taskId);

    List<TaskResponseDTO> getActiveTasksForUser(Long userId);

    List<TaskResponseDTO> getTasksDueToday(Long userId);

    boolean softDeleteTask(Long taskId);
}
