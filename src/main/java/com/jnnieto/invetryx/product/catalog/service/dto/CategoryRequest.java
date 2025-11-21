package com.jnnieto.invetryx.product.catalog.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequest(
        @NotBlank(message = "Name is required")
        @Size(max = 50, message = "Name can not exceed 50 characters")
        String name
) { }
