package com.starrytasks.backend.api.external;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
public class TaskDetailsDTO {
    private Long taskId;
    private String taskName;
    private Integer assignedStars;
    private Set<DayOfWeek> scheduledDays;
    private boolean isCompleted;
    private String categoryName;
    private String statusName;
    private Long childId;
    private LocalDate startDate;
    private LocalDate endDate;


}