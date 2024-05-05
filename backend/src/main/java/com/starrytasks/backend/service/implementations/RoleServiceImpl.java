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

import java.util.List;
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
                .map(role -> roleMapper.map(role))
                .collect(Collectors.toList());
    }

    @Override
    public String assignRoleAndGenerateToken(String roleName, User user) {
        Role role = roleRepository.findRoleByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        user.setRole(role);
        userRepository.save(user);

        return jwtService.generateToken(user);
    }

    @Override
    public UserDetailsDTO getAdditionalUserDetails(User user) {
        UserDetailsDTO userDetails = new UserDetailsDTO();
        userDetails.setRole(user.getRole());
        userDetails.setHasParent(user.getParent() != null);
        return userDetails;
    }


}
