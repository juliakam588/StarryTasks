package com.starrytasks.backend.service;

import com.starrytasks.backend.api.external.AuthenticationResponse;
import com.starrytasks.backend.api.external.RoleDTO;
import com.starrytasks.backend.api.external.UserDetailsDTO;
import com.starrytasks.backend.api.internal.Role;
import com.starrytasks.backend.api.internal.User;

import java.util.List;

public interface RoleService {
    List<RoleDTO> getAllRoles();

    String assignRoleAndGenerateToken(String roleName, User user);

    UserDetailsDTO getAdditionalUserDetails(User user);
}
