package com.starrytasks.backend.api.external;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ChildStatsDTO {
    private Long childId;
    private List<Integer> starsByWeek;
    private List<Integer> incompleteTasksByWeek;
    private String childName;


}
