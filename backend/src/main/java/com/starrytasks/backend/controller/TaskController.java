package com.starrytasks.backend.controller;

import com.starrytasks.backend.api.external.StatusResponseDTO;
import com.starrytasks.backend.api.external.TaskDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {


    @GetMapping
    public List<TaskDTO> getAllTasks() {
        Set<DayOfWeek> scheduledDays = Set.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY);

        return List.of(new TaskDTO()
                .setTaskId(1L)
                .setName("Water plants")
                .setCategoryId(1L)
                .setStars(4)
                .setDescription("")
                .setUserId(1L)
                .setScheduledDays(scheduledDays)
        );
    }

    @PostMapping
    public ResponseEntity<Object> createTask(@RequestBody TaskDTO taskDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(new StatusResponseDTO(201));    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(new StatusResponseDTO(200));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTask(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new StatusResponseDTO(200));
    }
}
