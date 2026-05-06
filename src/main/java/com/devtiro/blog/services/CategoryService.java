package com.devtiro.blog.services;

import com.devtiro.blog.domain.entities.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    List<Category> listCategories();
    Category getCategoryById(UUID id);
    Category createCategory(Category category);
    Category updateCategory(UUID id, Category category);
    void deleteCategory(UUID id);
}
