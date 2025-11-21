package com.jnnieto.invetryx.product.catalog.service.dto;

import java.math.BigDecimal;

public record ProductResponse(
        String id,
        String name,
        String description,
        BigDecimal price,
        CategoryResponse category
) {}
