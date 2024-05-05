package com.starrytasks.backend.api.external;
import com.starrytasks.backend.api.internal.Role;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class UserDetailsDTO {
    private Role role;
    private boolean hasParent;
}
