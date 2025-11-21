package com.jnnieto.invetryx.product.catalog.service.mappers;

import com.jnnieto.invetryx.product.catalog.service.dto.ProductRequest;
import com.jnnieto.invetryx.product.catalog.service.dto.ProductResponse;
import com.jnnieto.invetryx.product.catalog.service.models.Product;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(
        componentModel = "spring",
        builder = @Builder(disableBuilder = true),
        uses = { CategoryMapper.class }
)
public interface ProductMapper {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Product toEntity(ProductRequest request);

    ProductResponse toResponse(Product product);

    List<ProductResponse> toResponseList(List<Product> products);

}
