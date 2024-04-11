package com.starrytasks.backend.mapper;

import com.starrytasks.backend.api.external.UserDTO;
import com.starrytasks.backend.api.internal.User;

public class UserMapper {
    public UserDTO map(User user) {
        return new UserDTO()
                .setId(user.getId())
                .setEmail(user.getEmail());
    }

    public User map(UserDTO user) {
        return new User()
                .setId(user.getId())
                .setEmail(user.getEmail());
    }
    
}
