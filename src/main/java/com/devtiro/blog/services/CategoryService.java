package com.devtiro.blog.services;

import com.devtiro.blog.domain.entities.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    List<Category> listCategories();
    Category getCategoryById(UUID id);
    void deleteCategory(UUID id);
    Category createCategory(Category category);


}
