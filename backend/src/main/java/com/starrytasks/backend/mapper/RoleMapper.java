package com.starrytasks.backend.mapper;

import com.starrytasks.backend.api.external.RoleDTO;
import com.starrytasks.backend.api.internal.Role;
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
}
