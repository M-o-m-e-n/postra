package com.devtiro.blog.services.impl;

import com.devtiro.blog.domain.entities.Category;
import com.devtiro.blog.repositories.CategoryRepository;
import com.devtiro.blog.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> listCategories() {
        return categoryRepository.findAllWithPostCount();
    }

    @Override
    public Category getCategoryById(UUID id) {
        return null;
    }

    @Override
    public void deleteCategory(UUID id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            if (!category.get().getPosts().isEmpty()){
                throw new IllegalStateException("Category has posts associated to it.");
            }
        }
        categoryRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Category createCategory(Category category) {
        boolean existCategory = categoryRepository.existsByNameIgnoreCase(category.getName());
        String categoryName = category.getName();
        if (existCategory) {
            throw new IllegalArgumentException("Category already exists with name: " + categoryName + ".");
        }
        return categoryRepository.save(category);
    }
}
