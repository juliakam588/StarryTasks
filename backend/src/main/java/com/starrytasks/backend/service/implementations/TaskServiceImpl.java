package com.starrytasks.backend.service.implementations;

import com.starrytasks.backend.api.external.*;
import com.starrytasks.backend.api.internal.*;
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
    private final UserTaskRepository userTaskRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final TaskScheduleRepository taskScheduleRepository;
    private final UserStarsRepository userStarsRepository;

    @Transactional
    @Override
    public void saveTask(AddTaskDTO taskDTO, Long parentId) {
        User parent = userRepository.findById(parentId).orElseThrow(() -> new RuntimeException("User not found"));

        User child = userRepository.findUserById(taskDTO.getChildId());

        for (DayOfWeek day : taskDTO.getScheduledDays()) {
            List<LocalDate> dates = calculateDates(day, taskDTO.getStartDate(), taskDTO.getEndDate());
            for (LocalDate date : dates) {
                Task task = new Task();
                task.setCustomName(taskDTO.getName());
                task.setAssignedStars(taskDTO.getStars());
                task.setCategory(categoryRepository.findByName(taskDTO.getCategoryName()));
                task.setUser(parent);
                taskRepository.save(task);

                TaskSchedule schedule = new TaskSchedule();
                schedule.setTask(task);
                schedule.setScheduledDate(date);
                taskScheduleRepository.save(schedule);

                UserTask userTask = new UserTask();
                userTask.setTask(task);
                userTask.setUser(child);
                userTask.setAssignedDate(LocalDate.now());
                userTask.setStatus(new Status().setStatusId(2L).setName("Processed"));
                userTaskRepository.save(userTask);
            }
        }
    }


    @Override
    public void updateTask(Long taskId, TaskDetailsDTO taskDTO) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            task.setCustomName(taskDTO.getTaskName());
            task.setAssignedStars(taskDTO.getAssignedStars());
            task.setCategory(categoryRepository.findByName(taskDTO.getCategoryName()));

            taskScheduleRepository.deleteAll(task.getSchedules());
            Set<TaskSchedule> schedules = new HashSet<>();
            for (DayOfWeek day : taskDTO.getScheduledDays()) {
                List<LocalDate> dates = calculateDates(day, taskDTO.getStartDate(), taskDTO.getEndDate());
                for (LocalDate date : dates) {
                    TaskSchedule schedule = new TaskSchedule();
                    schedule.setTask(task);
                    schedule.setScheduledDate(date);
                    schedules.add(schedule);
                }
            }
            task.setSchedules(schedules);

            taskRepository.save(task);
        } else {
            System.out.println("Task not found for the given ID: " + taskId);
        }
    }


    @Override
    public List<TaskDetailsDTO> getTasksByChildId(Long childId) {
        List<UserTask> userTasks = userTaskRepository.findByUserId(childId);
        return userTasks.stream().map(ut -> {
            Task task = ut.getTask();
            TaskDetailsDTO dto = new TaskDetailsDTO();
            dto.setTaskId(task.getTaskId());
            dto.setTaskName(task.getCustomName());
            dto.setAssignedStars(task.getAssignedStars());
            dto.setScheduledDays(task.getSchedules().stream().map(TaskSchedule::getScheduledDate).map(LocalDate::getDayOfWeek).collect(Collectors.toSet()));
            dto.setCompleted(ut.getStatus().getName().equals("Completed"));
            dto.setCategoryName(task.getCategory() != null ? task.getCategory().getName() : null);
            dto.setChildId(childId);
            dto.setStartDate(task.getSchedules().stream().min(Comparator.comparing(TaskSchedule::getScheduledDate)).map(TaskSchedule::getScheduledDate).orElse(null));
            dto.setEndDate(task.getSchedules().stream().max(Comparator.comparing(TaskSchedule::getScheduledDate)).map(TaskSchedule::getScheduledDate).orElse(null));
            return dto;
        }).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteTask(Long taskId) {
        Task task = taskRepository.findByTaskId(taskId);
        if (task != null) {
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

    public List<TasksDTO> findTasksForChildByDate(Long childId, LocalDate date) {
        List<TasksDTO> tasksDTOList = userTaskRepository.findTasksForChildByScheduledDate(childId, date).stream()
                .map(schedule -> {
                    Task task = taskRepository.findByTaskId(schedule.getTaskId());
                    UserTask userTask = userTaskRepository.findByTask_TaskIdAndUserId(task.getTaskId(), childId);
                    System.out.println("Task ID: " + task.getTaskId());
                    System.out.println("Scheduled Date: " + schedule.getScheduledDate());
                    if (userTask != null) {
                        System.out.println("User Task Status: " + userTask.getStatus().getName());
                    } else {
                        System.out.println("User Task is null for task ID: " + task.getTaskId());
                    }
                    return new TasksDTO(
                            task.getTaskId(),
                            task.getCustomName(),
                            task.getAssignedStars(),
                            schedule.getScheduledDate(),
                            userTask != null && userTask.getStatus().getName().equals("Completed"),
                            task.getCategory().getName()
                    );
                })
                .collect(Collectors.toList());
        System.out.println("Tasks DTO List: " + tasksDTOList);
        return tasksDTOList;
    }


    @Override
    public List<TasksDTO> findTasksScheduledBetweenAndChildId(LocalDate startDate, LocalDate endDate, Long childId) {
        return taskScheduleRepository.findAllByDateRangeAndChildId(startDate, endDate, childId);
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
            }
            userStarsRepository.save(userStars);
            userTaskRepository.save(userTask);

        } else {
            throw new RuntimeException("Task not found");
        }
    }

}
