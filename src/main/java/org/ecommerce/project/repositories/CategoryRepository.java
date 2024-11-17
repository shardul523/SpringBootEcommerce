package org.ecommerce.project.repositories;

import org.ecommerce.project.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    public Category findByCategoryName(String categoryName);
}
