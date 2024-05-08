package com.starrytasks.backend.api.external;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class AddTaskDTO {
    private String name;
    private String categoryName;
    private Integer stars;
    private Set<DayOfWeek> scheduledDays;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long childId;

}
