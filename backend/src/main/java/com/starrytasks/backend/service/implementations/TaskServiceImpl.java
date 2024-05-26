package com.starrytasks.backend.service.implementations;

import com.starrytasks.backend.api.external.*;
import com.starrytasks.backend.api.internal.*;
import com.starrytasks.backend.mapper.TaskMapper;
import com.starrytasks.backend.rabbitmq.NotificationProducer;
import com.starrytasks.backend.repository.*;
import com.starrytasks.backend.service.TaskService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UserTaskRepository userTaskRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final TaskScheduleRepository taskScheduleRepository;
    private final UserStarsRepository userStarsRepository;
    private final NotificationProducer notificationProducer;

    @Transactional
    @Override
    public void saveTask(AddTaskDTO taskDTO, Long parentId) {
        User parent = userRepository.findById(parentId).orElseThrow(() -> new RuntimeException("User not found"));
        User child = userRepository.findUserById(taskDTO.getChildId());
        Category category = categoryRepository.findByName(taskDTO.getCategoryName());
        Status status = new Status().setStatusId(2L).setName("Processed");

        for (DayOfWeek day : taskDTO.getScheduledDays()) {
            List<LocalDate> dates = calculateDates(day, taskDTO.getStartDate(), taskDTO.getEndDate());
            for (LocalDate date : dates) {
                Task task = taskMapper.mapToTask(taskDTO, parent, category);
                taskRepository.save(task);

                TaskSchedule schedule = taskMapper.mapToTaskSchedule(task, date);
                taskScheduleRepository.save(schedule);

                UserTask userTask = taskMapper.mapToUserTask(task, child, status);
                userTaskRepository.save(userTask);
            }
        }
    }


    @Override
    public void updateTask(Long userTaskId, TasksDTO userTaskDTO) {
        UserTask userTask = userTaskRepository.findById(userTaskId)
                .orElseThrow(() -> new RuntimeException("UserTask not found for the given ID: " + userTaskId));
        Task task = userTask.getTask();
        TaskSchedule taskSchedule = taskScheduleRepository.findTaskScheduleByTask(task);
        Category category = categoryRepository.findByName(userTaskDTO.getCategoryName());
        taskMapper.updateTaskFromDTO(task, userTaskDTO, category);
        taskMapper.updateTaskScheduleFromDTO(taskSchedule, userTaskDTO);

        taskRepository.save(task);
        userTaskRepository.save(userTask);
        taskScheduleRepository.save(taskSchedule);
    }


    @Override
    public List<TaskDetailsDTO> getTasksByChildId(Long childId) {
        List<UserTask> userTasks = userTaskRepository.findByUserId(childId);
        return userTasks.stream()
                .map(taskMapper::toTaskDetailsDTO)
                .collect(Collectors.toList());
    }


    @Transactional
    @Override
    public void deleteTask(Long taskId) {
        Task task = taskRepository.findByTaskId(taskId);
        if (task != null) {
            LocalDate today = LocalDate.now();
            if (task.getSchedules().stream().anyMatch(schedule -> schedule.getScheduledDate().isBefore(today)) || userTaskRepository.findByTask_TaskId(taskId).getStatus().getName().equals("Completed")) {
                throw new RuntimeException("Cannot delete past or completed tasks");
            }

            userTaskRepository.deleteUserTaskByTask(task);
            taskScheduleRepository.deleteAll(task.getSchedules());
            taskRepository.deleteById(taskId);
        } else {
            System.out.println("Task not found for the given ID: " + taskId);
        }
    }


    private List<LocalDate> calculateDates(DayOfWeek dayOfWeek, LocalDate startDate, LocalDate endDate) {
        System.out.println("Calculating dates for: " + dayOfWeek + " from " + startDate + " to " + endDate);
        List<LocalDate> dates = new ArrayList<>();
        LocalDate date = startDate.with(TemporalAdjusters.nextOrSame(dayOfWeek));
        while (!date.isAfter(endDate)) {
            dates.add(date);
            System.out.println("Adding date: " + date);
            date = date.plusWeeks(1);
        }
        return dates;
    }


    @Override
    public List<TasksDTO> findTasksScheduledBetweenAndChildId(LocalDate startDate, LocalDate endDate, Long childId) {
        List<TasksDTO> allTasks = new ArrayList<>();
        LocalDate date = startDate;
        while (!date.isAfter(endDate)) {
            List<TasksDTO> dailyTasks = findTasksForChildByDate(childId, date);
            allTasks.addAll(dailyTasks);
            date = date.plusDays(1);
        }
        return allTasks;
    }

    @Override
    public void toggleTaskCompletion(Long taskId) {
        UserTask userTask = userTaskRepository.findByTask_TaskId(taskId);
        UserStars userStars = userStarsRepository.findByUserId(userTask.getUser().getId());
        if (userStars != null) {
            boolean isCompleted = userTask.getStatus().getName().equals("Completed");
            userTask.setStatus(new Status().setStatusId(isCompleted ? 2L : 1L).setName(isCompleted ? "Processed" : "Completed"));
            if(userTask.getStatus().getStatusId() == 2L) {
                int subtract = userStars.getTotalStars() - userTask.getTask().getAssignedStars();
                if(subtract < 0) {
                    subtract = 0;
                }
                userStars.setTotalStars(subtract);
            } else {
                userStars.setTotalStars(userStars.getTotalStars() + userTask.getTask().getAssignedStars());

                User parent = userTask.getUser().getParent();
                String parentEmail = parent.getEmail();
                String taskName = userTask.getTask().getCustomName();
                String childName = userTask.getUser().getUserProfile().getName();
                String message = String.format("Task: %s, Child: %s, ParentEmail: %s", taskName, childName, parentEmail);

                notificationProducer.sendNotification(message);
            }
            userStarsRepository.save(userStars);
            userTaskRepository.save(userTask);

        } else {
            throw new RuntimeException("Task not found");
        }
    }
    @Override
    public TasksDTO getTaskById(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found for the given ID: " + taskId));

        return taskMapper.toTasksDTO(task);
    }
    @Override
    public List<TasksDTO> findTasksForChildByDateAndCategory(Long childId, LocalDate date, String categoryName) {
        return findTasks(childId, date, categoryName);
    }

    @Override
    public List<TasksDTO> findTasksForChildByDate(Long childId, LocalDate date) {
        return findTasks(childId, date, null);
    }

    private List<TasksDTO> findTasks(Long childId, LocalDate date, String categoryName) {
        if (categoryName == null) {
            return userTaskRepository.findTasksForChildByScheduledDate(childId, date);
        } else {
            return userTaskRepository.findTasksForChildByScheduledDateAndCategory(childId, date, categoryName);
        }
    }


}

