package com.chema.db.backend.config;

import com.chema.db.backend.model.Category;
import com.chema.db.backend.model.TransactionType;
import com.chema.db.backend.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    public DataInitializer(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) {
        if (categoryRepository.findByUserIsNull().isEmpty()) {
            seedDefaultCategories();
        }
    }

    private void seedDefaultCategories() {
        List<Category> defaults = List.of(
                // Ingresos
                createDefault("Salary", TransactionType.INCOME),
                createDefault("Freelance", TransactionType.INCOME),
                createDefault("Investments", TransactionType.INCOME),
                createDefault("Other income", TransactionType.INCOME),
                // Gastos
                createDefault("Food", TransactionType.EXPENSE),
                createDefault("Transport", TransactionType.EXPENSE),
                createDefault("Housing", TransactionType.EXPENSE),
                createDefault("Entertainment", TransactionType.EXPENSE),
                createDefault("Health", TransactionType.EXPENSE),
                createDefault("Other expense", TransactionType.EXPENSE)
        );
        categoryRepository.saveAll(defaults);
    }

    private Category createDefault(String name, TransactionType type) {
        Category category = new Category();
        category.setName(name);
        category.setType(type);
        return category;
    }
}
