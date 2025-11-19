package com.jnnieto.invetryx.product.catalog.service.services.impl;

import com.jnnieto.invetryx.product.catalog.service.common.exceptions.ResourceNotFoundException;
import com.jnnieto.invetryx.product.catalog.service.dto.CreateProductRequest;
import com.jnnieto.invetryx.product.catalog.service.models.Category;
import com.jnnieto.invetryx.product.catalog.service.models.Product;
import com.jnnieto.invetryx.product.catalog.service.repositories.ProductRepository;
import com.jnnieto.invetryx.product.catalog.service.services.CategoryService;
import com.jnnieto.invetryx.product.catalog.service.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    @Override
    public List<Product> findAll() {
        return this.productRepository.findAll();
    }

    @Override
    public Product findById(String id) {
        return this.productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    @Override
    public Product save(CreateProductRequest product) {
        Category category = this.categoryService.getCategory(product.getCategory());

        Product newProduct = Product.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .category(category)
                .build();

        return this.productRepository.save(newProduct);
    }
}
