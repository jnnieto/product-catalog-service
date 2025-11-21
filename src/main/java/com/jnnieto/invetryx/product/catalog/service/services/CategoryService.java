package com.jnnieto.invetryx.product.catalog.service.services;

import com.jnnieto.invetryx.product.catalog.service.dto.CategoryRequest;
import com.jnnieto.invetryx.product.catalog.service.models.Category;

import java.util.List;

public interface CategoryService {

    Category createCategory(CategoryRequest category);

    List<Category> getCategories();

    Category getCategory(String id);

}
