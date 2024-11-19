package org.ecommerce.project.service;

import org.ecommerce.project.dto.CategoriesDTO;
import org.ecommerce.project.dto.CategoryDTO;
import org.ecommerce.project.model.Category;

import java.util.List;

public interface CategoryService {
    CategoriesDTO getAllCategories();
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    CategoryDTO deleteCategory(Long id);
    CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO);
}
