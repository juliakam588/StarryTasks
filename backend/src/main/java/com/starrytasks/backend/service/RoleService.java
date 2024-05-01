package com.starrytasks.backend.service;

import com.starrytasks.backend.api.external.RoleDTO;
import com.starrytasks.backend.api.internal.Role;

import java.util.List;

public interface RoleService {
    public List<RoleDTO> getAllRoles();
}
