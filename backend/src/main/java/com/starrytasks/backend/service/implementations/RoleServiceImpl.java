package com.starrytasks.backend.service.implementations;

import com.starrytasks.backend.api.external.AuthenticationResponse;
import com.starrytasks.backend.api.external.RoleDTO;
import com.starrytasks.backend.api.external.UserDetailsDTO;
import com.starrytasks.backend.api.internal.Role;
import com.starrytasks.backend.api.internal.User;
import com.starrytasks.backend.mapper.RoleMapper;
import com.starrytasks.backend.repository.RoleRepository;
import com.starrytasks.backend.repository.UserRepository;
import com.starrytasks.backend.service.JwtService;
import com.starrytasks.backend.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleMapper roleMapper;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public List<RoleDTO> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream()
                .map(roleMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public String assignRoleAndGenerateToken(String roleName, User user) {
        Role role = roleRepository.findRoleByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        user.setRole(role);
        userRepository.save(user);


        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", user.getRole().getName());
        extraClaims.put("hasParent", user.getParent() != null);

        return jwtService.generateToken(extraClaims, user);
    }

    @Override
    public UserDetailsDTO getAdditionalUserDetails(User user) {
        return roleMapper.map(user);
    }

}
