package com.starrytasks.backend.api.external;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class UserStarsDTO {
    private Long userId;
    private Integer totalStars;
    private Integer starsSpent;
    private Integer currentStars;
}
