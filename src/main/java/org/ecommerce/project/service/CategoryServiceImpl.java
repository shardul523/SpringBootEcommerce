package org.ecommerce.project.service;

import org.ecommerce.project.dto.CategoriesDTO;
import org.ecommerce.project.dto.CategoryDTO;
import org.ecommerce.project.exceptions.APIException;
import org.ecommerce.project.exceptions.ResourceNotFoundException;
import org.ecommerce.project.model.Category;
import org.ecommerce.project.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoriesDTO getAllCategories() {
        List<CategoryDTO> content = categoryRepository.findAll()
                .stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();

        if (content.isEmpty()) {
            throw new APIException("No categories found");
        }

        return new CategoriesDTO(content);
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category selectedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if (selectedCategory != null) {
            throw new APIException("Category with the same name already exists");
        }
        Category savedCategory = categoryRepository.save(category);

        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO deleteCategory(Long id) {
        Category selectedCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", id));

//        Category deletedCategory = selectedCategory.get();
        categoryRepository.delete(selectedCategory);
        return modelMapper.map(selectedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO) {
        Optional<Category> selectedCategory = categoryRepository.findById(categoryId);

        if (selectedCategory.isEmpty()) {
            throw new ResourceNotFoundException("category", "id", categoryId);
        }

        Category categoryToUpdate = selectedCategory.get();

        categoryToUpdate.setCategoryName(categoryDTO.getCategoryName());

        Category updatedCategory = categoryRepository.save(categoryToUpdate);

        return modelMapper.map(updatedCategory, CategoryDTO.class);
    }
}
