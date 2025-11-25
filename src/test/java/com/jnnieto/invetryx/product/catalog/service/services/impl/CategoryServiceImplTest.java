package com.jnnieto.invetryx.product.catalog.service.services.impl;

import com.jnnieto.invetryx.product.catalog.service.common.exceptions.ResourceConflictException;
import com.jnnieto.invetryx.product.catalog.service.common.exceptions.ResourceNotFoundException;
import com.jnnieto.invetryx.product.catalog.service.dto.CategoryRequest;
import com.jnnieto.invetryx.product.catalog.service.models.Category;
import com.jnnieto.invetryx.product.catalog.service.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category category;

    private List<Category> categories;

    private String randomUuid;

    @BeforeEach
    void setUp() {
        randomUuid = UUID.randomUUID().toString();
        category = Category.builder()
                .name("Category 1")
                .id(randomUuid)
                .build();
        categories = List.of(category);
    }

    @Test
    void getCategories_ShouldReturnAllCategories() {
        when(categoryRepository.findAll()).thenReturn(categories);
        List<Category> categories = categoryService.getCategories();
        assertEquals(1, categories.size());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void getCategoryById_ShouldReturnCategory_WhenExists() {
        when(categoryRepository.findById(anyString())).thenReturn(Optional.of(category));
        Category result = categoryService.getCategory(anyString());
        assertNotNull(result);
        assertEquals(randomUuid, result.getId());
        assertEquals(category.getName(), result.getName());
        verify(categoryRepository, times(1)).findById(anyString());
    }

    @Test
    void getCategoryById_ShouldReturnCategory_WhenDoesNotExist() {
        when(categoryRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> categoryService.getCategory(anyString()));
        verify(categoryRepository, times(1)).findById(anyString());
    }

    @Test
    void createCategory_ShouldReturnCategory_WhenNameDoesNotExist() {
        CategoryRequest categoryRequest = new CategoryRequest("Category 2");
        Category category2 = Category.builder()
                .name(categoryRequest.name())
                .id(randomUuid)
                .build();

        when(categoryRepository.existsByName(categoryRequest.name())).thenReturn(false);
        when(categoryRepository.save(any())).thenReturn(category2);

        Category category = categoryService.createCategory(categoryRequest);
        assertNotNull(category);
        assertEquals(categoryRequest.name(), category.getName());
        verify(categoryRepository, times(1)).existsByName(anyString());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }
    @Test
    void createCategory_ShouldReturnCategory_WhenNameExist() {
        CategoryRequest categoryRequest = new CategoryRequest("Category 2");

        when(categoryRepository.existsByName(categoryRequest.name())).thenReturn(true);

        assertThrows(ResourceConflictException.class, () -> categoryService.createCategory(categoryRequest));
        verify(categoryRepository, times(1)).existsByName(anyString());
        verify(categoryRepository, never()).save(any(Category.class));
    }

}