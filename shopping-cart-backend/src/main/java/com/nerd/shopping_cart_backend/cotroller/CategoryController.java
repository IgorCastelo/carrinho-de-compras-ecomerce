package com.nerd.shopping_cart_backend.cotroller;

import com.nerd.shopping_cart_backend.exceptions.AlreadyExistsException;
import com.nerd.shopping_cart_backend.exceptions.ResourceNotFoundException;
import com.nerd.shopping_cart_backend.model.Category;
import com.nerd.shopping_cart_backend.response.ApiResponse;
import com.nerd.shopping_cart_backend.service.category.ICategoreService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PostUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;


@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final ICategoreService categoreService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategory() {
        try {
            List<Category> allCategories = categoreService.getAllCategories();
            return ResponseEntity.ok().body(new ApiResponse("Found!", allCategories));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error: ", INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategory(@PathVariable Category name) {
        try {
            Category newCategory = categoreService.addCategory(name);
            return ResponseEntity.ok().body(new ApiResponse("Category adicionada", newCategory));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("category/{id}/category")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long categoryId) {
        try {
            Category category = categoreService.getCategoryById(categoryId);
            return ResponseEntity.ok().body(new ApiResponse("Found! ", category));

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("category/{name}/category")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String categoryName) {
        try {
            Category category = categoreService.getCategoryByName(categoryName);
            return ResponseEntity.ok().body(new ApiResponse("Found!  ", category));

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
        @DeleteMapping("category/{id}/delete")
        public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id) {
            try {
                categoreService.deleteCategoryById(id);
                return ResponseEntity.ok().body(new ApiResponse("Sucess", null));

            } catch (ResourceNotFoundException e) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
            }
    }
    @PutMapping("category/{id}/update")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id, Category categoryName) {
        try {
            Category category = categoreService.updateCategory(categoryName, id);
            return ResponseEntity.ok().body(new ApiResponse("Found!  ", category));

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}

