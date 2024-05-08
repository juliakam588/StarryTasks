package com.starrytasks.backend.controller;

import com.starrytasks.backend.api.external.UserDTO;
import com.starrytasks.backend.api.external.StatusResponseDTO;
import com.starrytasks.backend.api.external.UserProfileDTO;
import com.starrytasks.backend.api.internal.User;
import com.starrytasks.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {


    private final UserService userService;


    @GetMapping("/parent/{parentId}/children")
    public ResponseEntity<List<UserDTO>> getChildrenOfParent(@PathVariable Long parentId) {
        List<UserDTO> children = userService.findChildrenByParentId(parentId);
        if (children.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(children);
        }
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        return new UserDTO()
                .setId(id)
                .setEmail("example@example.com");
    }

    @PostMapping(value = "/avatar", consumes = "multipart/form-data")
    public ResponseEntity<?> uploadBookCoverPicture(
            @RequestPart("file") MultipartFile file,
            Authentication connectedUser
    ) {
        userService.uploadBookCoverPicture(file, connectedUser);
        return ResponseEntity.accepted().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(new StatusResponseDTO(200));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new StatusResponseDTO(200));
    }
}
