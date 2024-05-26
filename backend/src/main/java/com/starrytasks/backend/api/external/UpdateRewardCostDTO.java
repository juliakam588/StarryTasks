package com.starrytasks.backend.api.external;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UpdateRewardCostDTO {
    private Integer customCostInStars;
}
