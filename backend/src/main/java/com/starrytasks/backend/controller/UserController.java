package com.starrytasks.backend.controller;

import com.starrytasks.backend.api.external.UserDTO;
import com.starrytasks.backend.api.external.StatusResponseDTO;
import com.starrytasks.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {


    private UserService userService;


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

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(new StatusResponseDTO(200));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new StatusResponseDTO(200));
    }
}
