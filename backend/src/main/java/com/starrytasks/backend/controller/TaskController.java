package com.starrytasks.backend.controller;
import com.starrytasks.backend.api.internal.User;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.starrytasks.backend.api.external.*;
import com.starrytasks.backend.service.TaskService;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<StatusResponseDTO> createTask(@RequestBody AddTaskDTO taskDTO, Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        Long parentId = currentUser.getId();
        taskService.saveTask(taskDTO, parentId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new StatusResponseDTO(201));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StatusResponseDTO> updateTask(@PathVariable Long id, @RequestBody TasksDTO taskDTO) {
        taskService.updateTask(id, taskDTO);
        return ResponseEntity.ok(new StatusResponseDTO(200));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StatusResponseDTO> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok(new StatusResponseDTO(200));
    }

    @GetMapping("/child/{childId}")
    public ResponseEntity<List<TaskDetailsDTO>> getTasksByChildId(@PathVariable Long childId) {
        List<TaskDetailsDTO> tasks = taskService.getTasksByChildId(childId);
        if (tasks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tasks);
    }
    @GetMapping("/scheduled")
    public List<TasksDTO> getTasksScheduledBetween(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam("childId") Long childId) {

        return taskService.findTasksScheduledBetweenAndChildId(startDate, endDate, childId);
    }
    @GetMapping("/child/{childId}/date")
    public ResponseEntity<List<TasksDTO>> getTasksForChildByDate(
            @PathVariable Long childId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) String categoryName) {

        List<TasksDTO> tasks;
        if (categoryName == null) {
            tasks = taskService.findTasksForChildByDate(childId, date);
        } else {
            tasks = taskService.findTasksForChildByDateAndCategory(childId, date, categoryName);
        }

        if (tasks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tasks);
    }


    @GetMapping("/mytasks")
    public ResponseEntity<?> getMyTasks(Authentication authentication, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        if (authentication == null || authentication.getPrincipal() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User currentUser = (User) authentication.getPrincipal();

        List<TasksDTO> tasks = taskService.findTasksForChildByDate(currentUser.getId(), date);
        if (tasks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/{id}/toggle-completion")
    public ResponseEntity<StatusResponseDTO> toggleTaskCompletion(@PathVariable Long id) {
        taskService.toggleTaskCompletion(id);
        return ResponseEntity.ok(new StatusResponseDTO(200));
    }
    @GetMapping("/{taskId}/edit")
    public ResponseEntity<TasksDTO> getTaskDetailsForEdit(@PathVariable Long taskId) {
        TasksDTO task = taskService.getTaskById(taskId);
        return ResponseEntity.ok(task);
    }

}

