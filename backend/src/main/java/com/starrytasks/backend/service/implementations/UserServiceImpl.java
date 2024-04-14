package com.starrytasks.backend.service.implementations;

import com.starrytasks.backend.api.external.UserDTO;
import com.starrytasks.backend.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    @Override
    public List<UserDTO> findChildrenByParentId(Long parentId) {
        return List.of();
    }
}
