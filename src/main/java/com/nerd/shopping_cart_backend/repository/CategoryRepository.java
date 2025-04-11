package com.nerd.shopping_cart_backend.repository;

import com.nerd.shopping_cart_backend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);

    boolean existingByName(String name);
}
