package com.starrytasks.backend.api.external;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class UserRewardDTO {
    private Long id;
    private Long userId;
    private Long rewardId;
    private LocalDate redemptionDate;
}
