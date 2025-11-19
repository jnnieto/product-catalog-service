package com.jnnieto.invetryx.product.catalog.service.services;

import com.jnnieto.invetryx.product.catalog.service.dto.CreateProductRequest;
import com.jnnieto.invetryx.product.catalog.service.models.Product;

import java.util.List;

public interface ProductService {

    List<Product> findAll();

    Product findById(String id);

    Product save(CreateProductRequest product);

}
