package com.webdevchallenge.backend.controller;

import com.webdevchallenge.backend.dto.task.TaskRequestDTO;
import com.webdevchallenge.backend.dto.task.TaskResponseDTO;
import com.webdevchallenge.backend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/create-task")
    public ResponseEntity<TaskResponseDTO> createTask(@RequestBody TaskRequestDTO taskRequestDTO) {
        TaskResponseDTO responseDTO = taskService.createTask(taskRequestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/update-task/{taskId}")
    public ResponseEntity<TaskResponseDTO> updateTask(@PathVariable Long taskId, @RequestBody TaskRequestDTO taskRequestDTO) {
        TaskResponseDTO updateResponseDTO = taskService.updateTask(taskId, taskRequestDTO);
        return ResponseEntity.ok(updateResponseDTO);
    }

    @GetMapping("/get-all-tasks-for-user/{userId}")
    public ResponseEntity<List<TaskResponseDTO>> getAllTasksForUser(@PathVariable Long userId){
        List<TaskResponseDTO> responseDTOs = taskService.getAllTasksForUser(userId);
        return ResponseEntity.ok(responseDTOs);
    }

    @GetMapping("/get-task-by-id/{taskId}/for-user-id/{userId}")
    public ResponseEntity<TaskResponseDTO> getTaskByIdForUser(@PathVariable Long taskId, @PathVariable Long userId){
        TaskResponseDTO responseDTO = taskService.getTaskByIdForUser(taskId, userId);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/get-task-by-id/{taskId}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable Long taskId){
        TaskResponseDTO responseDTO = taskService.getTaskById(taskId);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/get-active-tasks-for-user-id/{userId}")
    public ResponseEntity<List<TaskResponseDTO>> getActiveTasksForUser(@PathVariable Long userId){
        List<TaskResponseDTO> responseDTOs = taskService.getActiveTasksForUser(userId);
        return ResponseEntity.ok(responseDTOs);
    }

    @GetMapping("/get-tasks-due-today-for-user-id/{userId}")
    public ResponseEntity<List<TaskResponseDTO>> getTasksDueToday(@PathVariable Long userId){
        List<TaskResponseDTO> responseDTOs = taskService.getTasksDueToday(userId);
        return ResponseEntity.ok(responseDTOs);
    }

    @DeleteMapping("soft-delete-task/{taskId}")
    public ResponseEntity<Boolean> softDeleteTask(@PathVariable Long taskId) {
        Boolean isSoftDeleted = taskService.softDeleteTask(taskId);
        return ResponseEntity.ok(isSoftDeleted);
    }
}
