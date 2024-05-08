package com.starrytasks.backend.mapper;


import com.starrytasks.backend.api.external.TaskDTO;
import com.starrytasks.backend.api.internal.Category;
import com.starrytasks.backend.api.internal.TaskSchedule;

import com.starrytasks.backend.api.internal.Task;
import com.starrytasks.backend.api.internal.User;
import com.starrytasks.backend.repository.CategoryRepository;
import com.starrytasks.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.util.Set;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Component
public class TaskMapper {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;


//    public TaskDTO map(Task task) {
//        Set<DayOfWeek> scheduledDays = task.getSchedules().stream()
//                .map(TaskSchedule::getDay)
//                .collect(Collectors.toSet());
//
//        return new TaskDTO()
//                .setTaskId(task.getTaskId())
//                .setName(task.getCustomName())
//                .setCategoryId(task.getCategory() != null ? task.getCategory().getCategoryId() : null)
//                .setStars(task.getAssignedStars())
//                .setUserId(task.getUser() != null ? task.getUser().getId() : null)
//                .setScheduledDays(scheduledDays);
//    }
//
//
//    public Task map(TaskDTO taskDTO) {
//        Category category = categoryRepository.findById(taskDTO.getCategoryId()).orElse(null);
//        User user = userRepository.findById(taskDTO.getUserId()).orElse(null);
//        Task task =  new Task()
//                .setTaskId(taskDTO.getTaskId())
//                .setCustomName(taskDTO.getName())
//                .setCategory(category)
//                .setAssignedStars(taskDTO.getStars())
//                .setUser(user);
//
//        if (taskDTO.getScheduledDays() != null) {
//            Set<TaskSchedule> schedules = taskDTO.getScheduledDays().stream()
//                    .map(day -> new TaskSchedule().setTask(task).setDay(day))
//                    .collect(Collectors.toSet());
//            task.setSchedules(schedules);
//        }
//        return task;
//    }
}
