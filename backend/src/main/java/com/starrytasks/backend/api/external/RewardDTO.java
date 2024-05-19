package com.starrytasks.backend.api.external;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class RewardDTO {
    private Long id;
    private String name;
    private byte[] imageUrl;
    private Integer costInStars;
    private Boolean isDefault;
    private Boolean isGranted;
    private Long userRewardId;
    private LocalDate redemptionDate;
}
