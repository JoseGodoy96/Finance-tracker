package com.chema.db.backend.service;

import com.chema.db.backend.model.Category;
import com.chema.db.backend.model.User;
import com.chema.db.backend.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAllForUser(User user){
        List<Category> categories = new ArrayList<>();
        return categories;
    }

    public createForUser(Category category, User user){
        category.setUser(user);
        return categoryRepository.save(category);
    }

    public void deleteForUser(Long id, User user){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", id));
        if (category.getUser() == null || !category.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You cannot delete this category");
        }
        categoryRepository.delete(category);
    }
}
