package com.starrytasks.backend.service.implementations;

import com.starrytasks.backend.api.external.RoleDTO;
import com.starrytasks.backend.api.internal.Role;
import com.starrytasks.backend.mapper.RoleMapper;
import com.starrytasks.backend.repository.RoleRepository;
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

    @Override
    public List<RoleDTO> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream()
                .map(role -> roleMapper.map(role))
                .collect(Collectors.toList());
    }
}
