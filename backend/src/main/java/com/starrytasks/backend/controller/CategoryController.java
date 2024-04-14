package com.starrytasks.backend.controller;

import com.starrytasks.backend.api.external.CategoryDTO;
import com.starrytasks.backend.api.external.StatusResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @GetMapping("/{categoryId}")
    public CategoryDTO getCategory(@PathVariable Long categoryId) {
        return new CategoryDTO()
                .setId(categoryId)
                .setCategoryName("Homework");
    }


    @GetMapping
    public List<CategoryDTO> getAllCategories() {
        return List.of(new CategoryDTO()
                .setId(1L)
                .setCategoryName("Cleaning")
        );
    }

    @PostMapping
    public ResponseEntity<Object> addCategory(@RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new StatusResponseDTO(201));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> editCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(new StatusResponseDTO(200));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCategory(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new StatusResponseDTO(200));
    }
}
