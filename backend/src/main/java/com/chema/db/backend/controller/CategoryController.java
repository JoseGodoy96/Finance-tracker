package com.chema.db.backend.controller;

import com.chema.db.backend.model.Category;
import com.chema.db.backend.model.User;
import com.chema.db.backend.service.CategoryService;
import com.chema.db.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final UserService userService;

    public CategoryController(CategoryService categoryService, UserService userService) {
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @GetMapping
    public List<Category> findAll(@RequestParam String username) {
        User user = userService.findByUsername(username);
        return categoryService.findAllForUser(user);
    }

    @PostMapping
    public Category create(@Valid @RequestBody Category category, @RequestParam String username) {
        User user = userService.findByUsername(username);
        return categoryService.createForUser(category, user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, @RequestParam String username) {
        User user = userService.findByUsername(username);
        categoryService.deleteForUser(id, user);
    }
}
