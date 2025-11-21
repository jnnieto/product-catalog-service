package com.jnnieto.invetryx.product.catalog.service.services;

import com.jnnieto.invetryx.product.catalog.service.dto.ProductRequest;
import com.jnnieto.invetryx.product.catalog.service.dto.ProductResponse;

import java.util.List;

public interface ProductService {

    List<ProductResponse> findAll();

    ProductResponse findById(String id);

    ProductResponse save(ProductRequest product);

}
