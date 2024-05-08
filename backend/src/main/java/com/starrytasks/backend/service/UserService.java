package com.starrytasks.backend.service;

import com.starrytasks.backend.api.external.UserDTO;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    List<UserDTO> findChildrenByParentId(Long parentId);

    void uploadBookCoverPicture(MultipartFile file, Authentication connectedUser);
}
