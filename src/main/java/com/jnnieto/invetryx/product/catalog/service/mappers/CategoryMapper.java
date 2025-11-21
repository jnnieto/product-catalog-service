package com.jnnieto.invetryx.product.catalog.service.mappers;

import com.jnnieto.invetryx.product.catalog.service.dto.CategoryResponse;
import com.jnnieto.invetryx.product.catalog.service.models.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponse toResponse(Category category);
}
