package org.ecommerce.project.service;

import org.ecommerce.project.model.Category;

import java.util.List;

public interface CategoryService {
    public List<Category> getAllCategories();
    public String createCategory(Category category);
    public String deleteCategory(Long id);
    public void updateCategory(Long categoryId, Category category);
}
