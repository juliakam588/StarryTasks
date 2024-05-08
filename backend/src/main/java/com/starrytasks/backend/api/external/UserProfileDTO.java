package com.starrytasks.backend.api.external;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class UserProfileDTO {
    private Long id;
    private String name;
    private byte[] profilePicture;
    private Integer currentStars;
}
