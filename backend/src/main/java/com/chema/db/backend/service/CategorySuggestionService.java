package com.chema.db.backend.service;

import com.chema.db.backend.dto.CategoryResponse;
import com.chema.db.backend.exception.ResourceNotFoundException;
import com.chema.db.backend.model.Category;
import com.chema.db.backend.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategorySuggestionService {

    private final CategoryRepository categoryRepository;
    private final AnthropicClient anthropicClient;

    public CategorySuggestionService(CategoryRepository categoryRepository, AnthropicClient anthropicClient) {
        this.categoryRepository = categoryRepository;
        this.anthropicClient = anthropicClient;
    }

    public CategoryResponse suggest(String description) {
        String targetCategoryName;

        try {
            targetCategoryName = askAnthropic(description);
        } catch (Exception e) {
            // Fallback: si Anthropic falla, tiramos de reglas keyword
            targetCategoryName = fallbackKeywords(description);
        }

        Category category = categoryRepository.findByUserIsNull().stream()
                .filter(c -> c.getName().equalsIgnoreCase(targetCategoryName))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Category", targetCategoryName));

        return CategoryMapper.toResponse(category);
    }

    private String askAnthropic(String description) {
        String prompt = "Eres un clasificador de transacciones financieras. " +
                "Categorías disponibles: Salary, Freelance, Investments, Other income, " +
                "Food, Transport, Housing, Entertainment, Health, Other expense. " +
                "Clasifica esta transacción: '" + description + "'. " +
                "Responde SOLO con el nombre exacto de una categoría, sin explicaciones ni comillas.";

        return anthropicClient.ask(prompt).trim();
    }

    private String fallbackKeywords(String description) {
        String desc = description.toLowerCase();

        if (desc.contains("café") || desc.contains("coffee") || desc.contains("starbucks")) {
            return "Food";
        } else if (desc.contains("uber") || desc.contains("taxi") || desc.contains("gasolina") || desc.contains("metro")) {
            return "Transport";
        } else if (desc.contains("nómina") || desc.contains("salary") || desc.contains("sueldo")) {
            return "Salary";
        } else if (desc.contains("alquiler") || desc.contains("rent") || desc.contains("hipoteca")) {
            return "Housing";
        } else if (desc.contains("médico") || desc.contains("farmacia") || desc.contains("hospital")) {
            return "Health";
        } else if (desc.contains("netflix") || desc.contains("cine") || desc.contains("spotify")) {
            return "Entertainment";
        } else {
            return "Other expense";
        }
    }
}
