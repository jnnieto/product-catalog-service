package com.jnnieto.invetryx.product.catalog.service.services.impl;

import com.jnnieto.invetryx.product.catalog.service.common.exceptions.ResourceNotFoundException;
import com.jnnieto.invetryx.product.catalog.service.dto.CategoryRequest;
import com.jnnieto.invetryx.product.catalog.service.models.Category;
import com.jnnieto.invetryx.product.catalog.service.repositories.CategoryRepository;
import com.jnnieto.invetryx.product.catalog.service.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category createCategory(CategoryRequest category) {
        Category categoryDb = Category.builder()
                .name(category.getName())
                .build();
        return this.categoryRepository.save(categoryDb);
    }

    @Override
    public List<Category> getCategories() {
        return this.categoryRepository.findAll();
    }

    @Override
    public Category getCategory(String id) {
        return this.categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

}
