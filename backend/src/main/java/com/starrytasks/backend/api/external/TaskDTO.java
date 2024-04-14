package com.starrytasks.backend.api.external;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.DayOfWeek;
import java.util.Set;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class TaskDTO {
    private Long taskId;
    private String name;
    private String description;
    private Integer stars;
    private Long categoryId;
    private Long userId;
    private Set<DayOfWeek> scheduledDays;

}
