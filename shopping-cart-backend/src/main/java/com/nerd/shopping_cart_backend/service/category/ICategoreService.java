package com.nerd.shopping_cart_backend.service.category;

import com.nerd.shopping_cart_backend.model.Category;

import java.util.List;

public interface ICategoreService {
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
    Category addCategory(Category category);
    Category updateCategory(Category category, Long id);

    void deleteCategoryById(Long id);


}
