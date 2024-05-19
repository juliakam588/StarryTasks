package com.starrytasks.backend.api.external;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ChildRewardsDTO {
    private Long childId;
    private String childName;
    private List<RewardDTO> rewards;
}
