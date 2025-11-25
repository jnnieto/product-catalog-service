package com.jnnieto.invetryx.product.catalog.service.services.impl;

import com.jnnieto.invetryx.product.catalog.service.common.exceptions.ResourceConflictException;
import com.jnnieto.invetryx.product.catalog.service.common.exceptions.ResourceNotFoundException;
import com.jnnieto.invetryx.product.catalog.service.dto.CategoryResponse;
import com.jnnieto.invetryx.product.catalog.service.dto.ProductRequest;
import com.jnnieto.invetryx.product.catalog.service.dto.ProductResponse;
import com.jnnieto.invetryx.product.catalog.service.mappers.ProductMapper;
import com.jnnieto.invetryx.product.catalog.service.models.Category;
import com.jnnieto.invetryx.product.catalog.service.models.Product;
import com.jnnieto.invetryx.product.catalog.service.repositories.ProductRepository;
import com.jnnieto.invetryx.product.catalog.service.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product productEntity;
    private ProductRequest productRequest;

    private Category category;

    private ProductResponse productResponse;


    private String randomUuid;

    private static final String TEST = "TEST";

    @BeforeEach
    void setUp() {
        randomUuid = UUID.randomUUID().toString();

        category = Category.builder()
                .id(randomUuid)
                .name(TEST)
                .build();

        productEntity = Product.builder()
                .id(randomUuid)
                .name(TEST)
                .description(TEST)
                .price(new BigDecimal("100000"))
                .category(category)
                .build();

        productRequest = ProductRequest.builder()
                .price(new BigDecimal("100000"))
                .description(TEST)
                .categoryId(category.getId())
                .name(TEST)
                .build();

        // IMPORTANTE: Mockear el mapper ANTES de usarlo
        productResponse = ProductResponse.builder()
                .id(randomUuid)
                .name(TEST)
                .description(TEST)
                .price(new BigDecimal("100000"))
                .category(new CategoryResponse(randomUuid, TEST))
                .build();
    }

    @Test
    void getProducts_ShouldReturnAllProducts() {
        when(productRepository.findAll()).thenReturn(Collections.singletonList(productEntity));
        when(productMapper.toResponseList(Collections.singletonList(productEntity)))
                .thenReturn(Collections.singletonList(productResponse));

        List<ProductResponse> products = productService.findAll();

        assertThat(products).hasSize(1);
        assertThat(products.get(0).name()).isEqualTo(TEST);

        verify(productRepository, times(1)).findAll();
    }

    @Test
    void findById_ShouldReturnProductResponse_WhenExists() {
        when(productRepository.findById(randomUuid)).thenReturn(Optional.of(productEntity));
        when(productMapper.toResponse(productEntity)).thenReturn(productResponse);

        ProductResponse result = productService.findById(randomUuid);

        assertThat(result.name()).isEqualTo(TEST);
        verify(productRepository, times(1)).findById(randomUuid);
    }

    @Test
    void findById_ShouldThrowNotFound_WhenDoesNotExist() {
        when(productRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.findById(anyString()));
    }

    @Test
    void save_ShouldCreateProduct_WhenDataIsValid() {
        when(categoryService.getCategory(randomUuid)).thenReturn(category);
        when(productRepository.existsByName(TEST)).thenReturn(false);
        when(productMapper.toEntity(productRequest)).thenReturn(productEntity);
        when(productRepository.save(productEntity)).thenReturn(productEntity);
        when(productMapper.toResponse(productEntity)).thenReturn(productResponse);

        ProductResponse result = productService.save(productRequest);

        assertThat(result.name()).isEqualTo(TEST);

        verify(categoryService, times(1)).getCategory(randomUuid);
        verify(productRepository, times(1)).existsByName(TEST);
        verify(productRepository, times(1)).save(productEntity);
    }

    @Test
    void save_ShouldThrowConflict_WhenProductNameExists() {
        when(categoryService.getCategory(randomUuid)).thenReturn(category);
        when(productRepository.existsByName(TEST)).thenReturn(true);

        assertThrows(ResourceConflictException.class, () -> productService.save(productRequest));

        verify(productRepository, never()).save(any());
    }

    @Test
    void save_ShouldThrowNotFound_WhenCategoryDoesNotExist() {
        when(categoryService.getCategory(randomUuid))
                .thenThrow(new ResourceNotFoundException("Category not found"));

        assertThrows(ResourceNotFoundException.class, () -> productService.save(productRequest));

        verify(productRepository, never()).save(any());
    }

}