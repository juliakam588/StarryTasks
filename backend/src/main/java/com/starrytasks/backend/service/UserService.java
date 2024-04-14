package com.starrytasks.backend.service;

import com.starrytasks.backend.api.external.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> findChildrenByParentId(Long parentId);
}
