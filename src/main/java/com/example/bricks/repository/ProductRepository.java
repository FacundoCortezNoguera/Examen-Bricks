package com.example.bricks.repository;

import com.example.bricks.model.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Método con filtro por categoría
    Page<Product> findAllByNameContainingAndPriceBetweenAndStockBetweenAndCategory(
            String name, Double minPrice, Double maxPrice, Integer minStock, Integer maxStock, Integer category, Pageable pageable);

    // Método sin filtro por categoría
    Page<Product> findAllByNameContainingAndPriceBetweenAndStockBetween(
            String name, Double minPrice, Double maxPrice, Integer minStock, Integer maxStock, Pageable pageable);
}
