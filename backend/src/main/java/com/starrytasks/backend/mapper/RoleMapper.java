package com.starrytasks.backend.mapper;

import com.starrytasks.backend.api.external.RoleDTO;
import com.starrytasks.backend.api.external.UserDetailsDTO;
import com.starrytasks.backend.api.internal.Role;
import com.starrytasks.backend.api.internal.User;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {


    public RoleDTO map(Role role) {
        return new RoleDTO()
                .setId(role.getId())
                .setName(role.getName());
    }

    public Role map(RoleDTO roleDTO) {
        return new Role()
                .setId(roleDTO.getId())
                .setName(roleDTO.getName());
    }

    public UserDetailsDTO map(User user) {
        UserDetailsDTO userDetails = new UserDetailsDTO();
        userDetails.setRole(user.getRole());
        userDetails.setHasParent(user.getParent() != null);
        return userDetails;
    }
}
