package com.jnnieto.invetryx.product.catalog.service.controllers;

import com.jnnieto.invetryx.product.catalog.service.dto.CreateProductRequest;
import com.jnnieto.invetryx.product.catalog.service.models.Product;
import com.jnnieto.invetryx.product.catalog.service.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable String id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest request) {
        return new ResponseEntity<>(productService.save(request),  HttpStatus.CREATED);
    }

}
