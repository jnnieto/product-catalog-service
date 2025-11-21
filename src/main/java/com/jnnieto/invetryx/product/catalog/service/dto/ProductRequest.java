package com.jnnieto.invetryx.product.catalog.service.dto;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank(message = "Name is required")
        @Size(max = 50, message = "Name can not exceed 50 characters")
        String name,
        @Size(max = 255, message = "Description too long")
        String description,
        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.01", message = "Price must be greater than 0")
        BigDecimal price,
        @NotBlank(message = "Category ID is required")
        String categoryId
) {}
