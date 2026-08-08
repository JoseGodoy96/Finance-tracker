package com.chema.db.backend.controller;

import com.chema.db.backend.dto.CategoryRequest;
import com.chema.db.backend.dto.CategoryResponse;
import com.chema.db.backend.model.Category;
import com.chema.db.backend.model.User;
import com.chema.db.backend.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CategoryResponse> findAll(@AuthenticationPrincipal User user) {
        return categoryService.findAllForUser(user);
    }

    @PostMapping
    public CategoryResponse create(@Valid @RequestBody CategoryRequest request, @AuthenticationPrincipal User user) {
        return categoryService.createForUser(request, user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, @AuthenticationPrincipal User user) {
        categoryService.deleteForUser(id, user);
    }
}
