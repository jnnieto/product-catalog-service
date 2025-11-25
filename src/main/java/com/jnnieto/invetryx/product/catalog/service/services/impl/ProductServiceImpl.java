    package com.jnnieto.invetryx.product.catalog.service.services.impl;

    import com.jnnieto.invetryx.product.catalog.service.common.exceptions.ResourceConflictException;
    import com.jnnieto.invetryx.product.catalog.service.common.exceptions.ResourceNotFoundException;
    import com.jnnieto.invetryx.product.catalog.service.dto.ProductRequest;
    import com.jnnieto.invetryx.product.catalog.service.dto.ProductResponse;
    import com.jnnieto.invetryx.product.catalog.service.mappers.ProductMapper;
    import com.jnnieto.invetryx.product.catalog.service.models.Category;
    import com.jnnieto.invetryx.product.catalog.service.models.Product;
    import com.jnnieto.invetryx.product.catalog.service.repositories.ProductRepository;
    import com.jnnieto.invetryx.product.catalog.service.services.CategoryService;
    import com.jnnieto.invetryx.product.catalog.service.services.ProductService;
    import lombok.RequiredArgsConstructor;
    import org.springframework.stereotype.Service;

    import java.util.List;

    @Service
    @RequiredArgsConstructor
    public class ProductServiceImpl implements ProductService {

        private final ProductRepository productRepository;
        private final CategoryService categoryService;
        private final ProductMapper productMapper;

        @Override
        public List<ProductResponse> findAll() {
            return productMapper.toResponseList(productRepository.findAll());
        }

        @Override
        public ProductResponse findById(String id) {
            return productMapper.toResponse(productRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found")));
        }

        @Override
        public ProductResponse save(ProductRequest request) {
            Category category = categoryService.getCategory(request.categoryId());

            if (productRepository.existsByName(request.name())) {
                throw new ResourceConflictException("A product with that name already exists.");
            }

            Product product = productMapper.toEntity(request);
            product.setCategory(category);

            return productMapper.toResponse(productRepository.save(product));
        }
    }
