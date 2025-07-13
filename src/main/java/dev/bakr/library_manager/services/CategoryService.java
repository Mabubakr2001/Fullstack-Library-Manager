package dev.bakr.library_manager.services;

import dev.bakr.library_manager.entities.Category;
import dev.bakr.library_manager.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category findOrCreateCategory(String categoryName) {
        return categoryRepository.findByName(categoryName)
                .orElseGet(() -> {
                    Category category = new Category();
                    category.setName(categoryName);
                    category.setDescription(fetchDescription(categoryName));
                    return categoryRepository.save(category);
                });
    }

    private String fetchDescription(String categoryName) {
        return "Hello it's me, I was wondering if after all these years you'd like to meet.";
    }
}
