package org.ecommerce.project.service;

import org.ecommerce.project.exceptions.APIException;
import org.ecommerce.project.exceptions.ResourceNotFoundException;
import org.ecommerce.project.model.Category;
import org.ecommerce.project.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> allCategories = categoryRepository.findAll();

        if (allCategories.isEmpty()) {
            throw new APIException("No categories found");
        }

        return allCategories;
    }

    @Override
    public String createCategory(Category category) {
        Category selectedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if (selectedCategory != null) {
            throw new APIException("Category with the same name already exists");
        }
        categoryRepository.save(category);
        return "Category created successfully";
    }

    @Override
    public String deleteCategory(Long id) {
        Optional<Category> selectedCategory = categoryRepository.findById(id);
        if (selectedCategory.isEmpty()) {
            throw new ResourceNotFoundException("Category", "categoryId", id);
        }
        categoryRepository.delete(selectedCategory.get());
        return "Category deleted successfully";
    }

    @Override
    public void updateCategory(Long categoryId, Category category) {
        Optional<Category> selectedCategory = categoryRepository.findById(categoryId);

        if (selectedCategory.isEmpty()) {
            throw new ResourceNotFoundException("category", "id", categoryId);
        }

        Category categoryToUpdate = selectedCategory.get();

        categoryToUpdate.setCategoryName(category.getCategoryName());

        categoryRepository.save(categoryToUpdate);
    }
}
