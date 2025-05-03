package com.webdevchallenge.backend.repository;

import com.webdevchallenge.backend.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    List<TaskEntity> findByCreatedByUserId(Long userId); // All tasks for a user

    Optional<TaskEntity> findByTaskIdAndCreatedByUserId(Long taskId, Long userId); // Specific task for a user

    List<TaskEntity> findByCreatedByUserIdAndIsDeletedFalse(Long userId); // Only active tasks

    boolean existsByTaskIdAndCreatedByUserId(Long taskId, Long userId); // for validation
}
