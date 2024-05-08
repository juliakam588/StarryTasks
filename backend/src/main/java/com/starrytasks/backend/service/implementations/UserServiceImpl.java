package com.starrytasks.backend.service.implementations;

import com.starrytasks.backend.api.external.UserDTO;
import com.starrytasks.backend.api.internal.User;
import com.starrytasks.backend.api.internal.UserProfile;
import com.starrytasks.backend.repository.UserProfileRepository;
import com.starrytasks.backend.repository.UserRepository;
import com.starrytasks.backend.service.FileStorageService;
import com.starrytasks.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final FileStorageService fileStorageService;
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

    @Override
    public List<UserDTO> findChildrenByParentId(Long parentId) {
        return List.of();
    }

    @Override
    public void uploadBookCoverPicture(MultipartFile file, Authentication connectedUser) {
        var user =  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var profilePicture = fileStorageService.saveFile(file, user.getId());
        UserProfile userProfile = userProfileRepository.findByUserId(user.getId());
        userProfile.setProfilePicturePath(profilePicture);
        userProfileRepository.save(userProfile);

    }
}
