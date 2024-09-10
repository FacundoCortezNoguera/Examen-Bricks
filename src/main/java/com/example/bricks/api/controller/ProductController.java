package com.example.bricks.api.controller;

import com.example.bricks.Service.CategoryService;
import com.example.bricks.Service.ProductService;
import com.example.bricks.api.dto.ProductResponse;
import com.example.bricks.api.dto.Category;
import com.example.bricks.model.Product;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public Page<Product> getProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Integer minStock,
            @RequestParam(required = false) Integer maxStock,
            @RequestParam(required = false) Integer category,
            Pageable pageable) {

        return productService.getAllProducts(name, minPrice, maxPrice, minStock, maxStock, category, pageable);
    }

    @GetMapping("/{id}")
    public ProductResponse getProduct(@PathVariable Long id) {

        Product product = productService.getProduct(id);
        String categoryName = categoryService.getCategories()
                .stream()
                .filter(category -> category.getId() == product.getCategory())
                .map(Category::getName)
                .findFirst()
                .orElse("Unknown");
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getStock(), categoryName);
    }

    @PostMapping
    public Product createProduct(@Valid @RequestBody Product product) {
        return productService.createProduct(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @Valid @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }
}
