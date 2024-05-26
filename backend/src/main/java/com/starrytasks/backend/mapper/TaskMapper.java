package com.starrytasks.backend.mapper;


import com.starrytasks.backend.api.external.AddTaskDTO;
import com.starrytasks.backend.api.external.TaskDetailsDTO;
import com.starrytasks.backend.api.external.TasksDTO;
import com.starrytasks.backend.api.internal.*;

import com.starrytasks.backend.repository.CategoryRepository;
import com.starrytasks.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Component
public class TaskMapper {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;


    public Task mapToTask(AddTaskDTO taskDTO, User parent, Category category) {
        Task task = new Task();
        task.setCustomName(taskDTO.getName());
        task.setAssignedStars(taskDTO.getStars());
        task.setCategory(category);
        task.setUser(parent);
        return task;
    }

    public TaskSchedule mapToTaskSchedule(Task task, LocalDate date) {
        TaskSchedule schedule = new TaskSchedule();
        schedule.setTask(task);
        schedule.setScheduledDate(date);
        return schedule;
    }

    public UserTask mapToUserTask(Task task, User child, Status status) {
        UserTask userTask = new UserTask();
        userTask.setTask(task);
        userTask.setUser(child);
        userTask.setAssignedDate(LocalDate.now());
        userTask.setStatus(status);
        return userTask;
    }

    public void updateTaskFromDTO(Task task, TasksDTO userTaskDTO, Category category) {
        task.setCustomName(userTaskDTO.getTaskName());
        task.setAssignedStars(userTaskDTO.getAssignedStars());
        task.setCategory(category);
    }

    public void updateTaskScheduleFromDTO(TaskSchedule taskSchedule, TasksDTO userTaskDTO) {
        taskSchedule.setScheduledDate(userTaskDTO.getScheduledDate());
    }
    public TaskDetailsDTO toTaskDetailsDTO(UserTask userTask) {
        Task task = userTask.getTask();
        TaskDetailsDTO dto = new TaskDetailsDTO();

        dto.setTaskId(task.getTaskId());
        dto.setTaskName(task.getCustomName());
        dto.setAssignedStars(task.getAssignedStars());
        dto.setScheduledDays(task.getSchedules().stream()
                .map(TaskSchedule::getScheduledDate)
                .map(LocalDate::getDayOfWeek)
                .collect(Collectors.toSet()));
        dto.setCompleted(userTask.getStatus().getName().equals("Completed"));
        dto.setCategoryName(task.getCategory() != null ? task.getCategory().getName() : null);
        dto.setChildId(userTask.getUser().getId());
        dto.setStartDate(task.getSchedules().stream()
                .min(Comparator.comparing(TaskSchedule::getScheduledDate))
                .map(TaskSchedule::getScheduledDate)
                .orElse(null));
        dto.setEndDate(task.getSchedules().stream()
                .max(Comparator.comparing(TaskSchedule::getScheduledDate))
                .map(TaskSchedule::getScheduledDate)
                .orElse(null));

        return dto;
    }
    public TasksDTO toTasksDTO(Task task) {
        TasksDTO dto = new TasksDTO();
        dto.setTaskId(task.getTaskId());
        dto.setTaskName(task.getCustomName());
        dto.setAssignedStars(task.getAssignedStars());
        dto.setCategoryName(task.getCategory() != null ? task.getCategory().getName() : null);
        dto.setScheduledDate(task.getSchedules().stream()
                .map(TaskSchedule::getScheduledDate)
                .findFirst()
                .orElse(null));

        return dto;
    }


    public TasksDTO toTasksDTO(Task task, TaskSchedule schedule, UserTask userTask) {
        TasksDTO dto = new TasksDTO();
        dto.setTaskId(task.getTaskId());
        dto.setTaskName(task.getCustomName());
        dto.setAssignedStars(task.getAssignedStars());
        dto.setScheduledDate(schedule.getScheduledDate());
        dto.setCompleted(userTask.getStatus().getName().equals("Completed"));
        dto.setCategoryName(task.getCategory() != null ? task.getCategory().getName() : null);
        return dto;
    }
}
