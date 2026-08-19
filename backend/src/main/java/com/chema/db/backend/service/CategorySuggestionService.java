package com.chema.db.backend.service;

import com.chema.db.backend.dto.CategoryResponse;
import com.chema.db.backend.exception.ResourceNotFoundException;
import com.chema.db.backend.model.Category;
import com.chema.db.backend.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategorySuggestionService {

    private final CategoryRepository categoryRepository;

    public CategorySuggestionService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    public CategoryResponse suggest(String description) {
        String desc = description.toLowerCase();

        String targetCategoryName;

        if (desc.contains("café") || desc.contains("coffee") || desc.contains("starbucks")) {
            targetCategoryName = "Food";
        } else if (desc.contains("uber") || desc.contains("taxi") || desc.contains("gasolina") || desc.contains("metro")) {
            targetCategoryName = "Transport";
        } else if (desc.contains("nómina") || desc.contains("salary") || desc.contains("sueldo")) {
            targetCategoryName = "Salary";
        } else if (desc.contains("alquiler") || desc.contains("rent") || desc.contains("hipoteca")) {
            targetCategoryName = "Housing";
        } else if (desc.contains("médico") || desc.contains("farmacia") || desc.contains("hospital")) {
            targetCategoryName = "Health";
        } else if (desc.contains("netflix") || desc.contains("cine") || desc.contains("spotify")) {
            targetCategoryName = "Entertainment";
        } else {
            targetCategoryName = "Other expense";
        }

        Category category = categoryRepository.findByUserIsNull().stream()
                .filter(c -> c.getName().equals(targetCategoryName))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Category", targetCategoryName));

        return CategoryMapper.toResponse(category);
    }
}
