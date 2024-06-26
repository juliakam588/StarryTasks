package com.starrytasks.backend.api.external;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;
@Data
@Accessors(chain = true)
@AllArgsConstructor
@RequiredArgsConstructor
public class TasksDTO {
    private Long taskId;
    private String taskName;
    private Integer assignedStars;
    private LocalDate scheduledDate;
    private boolean isCompleted;
    private String categoryName;
}
