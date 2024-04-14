package com.starrytasks.backend.api.external;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class InvitationDTO {
    private String invitationCode;
    private UserDTO generatedByUser;
    private LocalDate expirationDate;
    private boolean isActive;


}
