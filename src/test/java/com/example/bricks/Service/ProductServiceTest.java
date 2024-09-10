package com.example.bricks.Service;

import com.example.bricks.api.dto.Category;
import com.example.bricks.api.exception.ResourceNotFoundException;
import com.example.bricks.model.Product;
import com.example.bricks.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProductsWithCategory() {
        // Datos de prueba
        Product product = new Product(1, "Product 1", 100.0, 10, 1);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> productPage = new PageImpl<>(Collections.singletonList(product));

        when(productRepository.findAllByNameContainingAndPriceBetweenAndStockBetweenAndCategory(
                anyString(), anyDouble(), anyDouble(), anyInt(), anyInt(), anyInt(), any(Pageable.class)))
                .thenReturn(productPage);

        Page<Product> result = productService.getAllProducts("Product", 50.0, 150.0, 5, 20, 1, pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals("Product 1", result.getContent().get(0).getName());
    }

    @Test
    void testGetAllProductsWithoutCategory() {
        Product product = new Product(1, "Product 1", 100.0, 10, 1);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> productPage = new PageImpl<>(Collections.singletonList(product));

        when(productRepository.findAllByNameContainingAndPriceBetweenAndStockBetween(
                anyString(), anyDouble(), anyDouble(), anyInt(), anyInt(), any(Pageable.class)))
                .thenReturn(productPage);

        Page<Product> result = productService.getAllProducts("Product", 50.0, 150.0, 5, 20, null, pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals("Product 1", result.getContent().get(0).getName());
    }

    @Test
    void testGetProduct() {
        Product product = new Product(1, "Product 1", 100.0, 10, 1);

        when(productRepository.findById(1L)).thenReturn(java.util.Optional.of(product));

        Product result = productService.getProduct(1L);

        assertEquals(1, result.getId());
        assertEquals("Product 1", result.getName());
    }

    @Test
    void testGetProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            productService.getProduct(1L);
        });
    }

    @Test
    void testCreateProductWithValidCategory() {
        Product product = new Product(1, "New Product", 200.0, 15, 1);
        Product savedProduct = new Product(1, "New Product", 200.0, 15, 1);
        Category category = new Category();
        category.setId(1);
        List<Category> categories = Collections.singletonList(category);

        when(categoryService.getCategories()).thenReturn(categories);
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        Product result = productService.createProduct(product);

        assertEquals(1, result.getId());
        assertEquals("New Product", result.getName());
    }

    @Test
    void testCreateProductWithInvalidCategory() {
        Product product = new Product(1, "New Product", 200.0, 15, 99);

        when(categoryService.getCategories()).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> {
            productService.createProduct(product);
        });
    }

    @Test
    void testUpdateProduct() {
        // Datos de prueba
        Product existingProduct = new Product(100, "Existing Product", 100.0, 10, 1);
        Product updatedProduct = new Product(100, "Updated Product", 150.0, 20, 1);
        Category category = new Category();
        category.setId(1);
        List<Category> categories = Collections.singletonList(category);

        when(productRepository.findById(1L)).thenReturn(java.util.Optional.of(existingProduct));
        when(categoryService.getCategories()).thenReturn(categories);
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        Product result = productService.updateProduct(1L, updatedProduct);

        assertEquals("Updated Product", result.getName());
        assertEquals(150.0, result.getPrice());
    }

    @Test
    void testDeleteProduct() {
        productService.deleteProduct(1L);

        verify(productRepository).deleteById(1L);
    }
}