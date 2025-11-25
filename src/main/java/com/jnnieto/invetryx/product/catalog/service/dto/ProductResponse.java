package com.jnnieto.invetryx.product.catalog.service.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductResponse(
        String id,
        String name,
        String description,
        BigDecimal price,
        CategoryResponse category
) {}
