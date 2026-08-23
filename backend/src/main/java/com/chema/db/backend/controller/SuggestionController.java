package com.chema.db.backend.controller;

import com.chema.db.backend.dto.CategoryResponse;
import com.chema.db.backend.dto.SuggestionRequest;
import com.chema.db.backend.service.CategorySuggestionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
public class SuggestionController {

    private final CategorySuggestionService categorySuggestionService;

    public SuggestionController(CategorySuggestionService categorySuggestionService) {
        this.categorySuggestionService = categorySuggestionService;
    }

    @PostMapping("/suggest-category")
    public CategoryResponse suggest(@Valid @RequestBody SuggestionRequest request) {
        return categorySuggestionService.suggest(request.getDescription());
    }
}
