package com.chema.db.backend.service;

import com.chema.db.backend.model.Category;
import com.chema.db.backend.model.User;
import com.chema.db.backend.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import com.chema.db.backend.exception.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAllForUser(User user) {
        return Stream.concat(
                categoryRepository.findByUserIsNull().stream(),
                categoryRepository.findByUser(user).stream()
        ).toList();
    }

    public Category createForUser(Category category, User user) {
        category.setUser(user);
        return categoryRepository.save(category);
    }

    public void deleteForUser(Long id, User user) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", id));

        if (category.getUser() == null || !category.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You cannot delete this category");
        }

        categoryRepository.delete(category);
    }
}
