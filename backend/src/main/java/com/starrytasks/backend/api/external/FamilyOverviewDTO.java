package com.starrytasks.backend.api.external;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class FamilyOverviewDTO {
    private String parentName;
    private List<UserProfileDTO> children;
}
