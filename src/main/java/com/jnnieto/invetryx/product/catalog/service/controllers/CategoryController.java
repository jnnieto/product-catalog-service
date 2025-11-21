package com.jnnieto.invetryx.product.catalog.service.controllers;

import com.jnnieto.invetryx.product.catalog.service.dto.CategoryRequest;
import com.jnnieto.invetryx.product.catalog.service.models.Category;
import com.jnnieto.invetryx.product.catalog.service.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> getCategories() {
        return ResponseEntity.ok(categoryService.getCategories());
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@Valid @RequestBody CategoryRequest request) {
        return new ResponseEntity<>(categoryService.createCategory(request),  HttpStatus.CREATED);
    }
}
