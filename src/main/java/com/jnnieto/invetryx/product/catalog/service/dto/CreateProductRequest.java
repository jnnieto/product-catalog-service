package com.jnnieto.invetryx.product.catalog.service.dto;

import lombok.Data;

@Data
public class CreateProductRequest {

    private String name;

    private String description;

    private double price;

    private String category;

}
