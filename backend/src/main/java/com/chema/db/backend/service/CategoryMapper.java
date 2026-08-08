package com.chema.db.backend.service;

import com.chema.db.backend.dto.CategoryRequest;
import com.chema.db.backend.dto.CategoryResponse;
import com.chema.db.backend.model.Category;

public class CategoryMapper {

    public static Category toEntity(CategoryRequest request) {
        Category category = new Category();
        category.setName(request.getName());
        category.setType(request.getType());
        return category;
    }

    public static CategoryResponse toResponse(Category category) {
        CategoryResponse response = new CategoryResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        response.setType(category.getType());
        response.setSystem(category.getUser() == null);
        return response;
    }
}