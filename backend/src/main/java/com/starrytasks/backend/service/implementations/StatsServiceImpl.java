package com.starrytasks.backend.service.implementations;
import com.starrytasks.backend.api.external.StatsDTO;
import com.starrytasks.backend.api.internal.TaskSchedule;
import com.starrytasks.backend.api.internal.User;
import com.starrytasks.backend.api.internal.UserTask;
import com.starrytasks.backend.repository.TaskScheduleRepository;
import com.starrytasks.backend.repository.UserRepository;
import com.starrytasks.backend.repository.UserTaskRepository;
import com.starrytasks.backend.service.StatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final UserRepository userRepository;
    private final UserTaskRepository userTaskRepository;
    private final TaskScheduleRepository taskScheduleRepository;

    @Override
    public StatsDTO getStatsForParent(Long parentId) {
        List<User> children = userRepository.findByParentId(parentId);
        Map<Long, List<UserTask>> tasksByChild = new HashMap<>();

        for (User child : children) {
            List<UserTask> userTasks = userTaskRepository.findByUserId(child.getId());
            tasksByChild.put(child.getId(), userTasks);
        }

        return calculateStats(tasksByChild, children);
    }

    private StatsDTO calculateStats(Map<Long, List<UserTask>> tasksByChild, List<User> children) {
        int numberOfWeeks = 4;

        List<Map<String, Integer>> weeklyStars = IntStream.range(0, numberOfWeeks)
                .mapToObj(i -> new HashMap<String, Integer>())
                .collect(Collectors.toList());
        List<Map<String, Integer>> weeklyIncompleteTasks = IntStream.range(0, numberOfWeeks)
                .mapToObj(i -> new HashMap<String, Integer>())
                .collect(Collectors.toList());

        LocalDate now = LocalDate.now();
        for (int i = numberOfWeeks; i > 0; i--) {
            LocalDate weekStart = now.minusWeeks(i).with(WeekFields.of(Locale.getDefault()).getFirstDayOfWeek());
            LocalDate weekEnd = weekStart.plusDays(6);


            System.out.println(weekStart);
            System.out.println(weekEnd);
            for (User child : children) {
                Long childId = child.getId();
                String childName = child.getUserProfile().getName();
                List<UserTask> userTasks = tasksByChild.get(childId);

                int stars = 0;
                int incompleteTasks = 0;

                for (UserTask userTask : userTasks) {
                    TaskSchedule taskSchedule = taskScheduleRepository.findTaskScheduleByTask(userTask.getTask());
                    LocalDate taskDate = taskSchedule.getScheduledDate();
                    System.out.println("TaskDate???" + taskDate);
                    System.out.println(userTask.getTask().getCustomName());


                    if (!taskDate.isBefore(weekStart) && !taskDate.isAfter(weekEnd) && !taskDate.isAfter(LocalDate.now())) {
                        if (userTask.getStatus().getName().equals("Completed")) {
                            stars += userTask.getTask().getAssignedStars();
                        } else {
                            incompleteTasks++;
                        }
                    }
                }

                int weekIndex = numberOfWeeks - i;
                weeklyStars.get(weekIndex).put(childName, stars);
                weeklyIncompleteTasks.get(weekIndex).put(childName, incompleteTasks);
            }
        }

        return new StatsDTO(weeklyStars, weeklyIncompleteTasks);
    }
}
