package com.example.bricks.Service;

import com.example.bricks.api.exception.ResourceNotFoundException;
import com.example.bricks.model.Product;
import com.example.bricks.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryService categoryService;

    public Page<Product> getAllProducts(String name, Double minPrice, Double maxPrice, Integer minStock, Integer maxStock, Integer  category, Pageable pageable) {
        String productName = (name != null) ? name : "";
        Double minProductPrice = (minPrice != null) ? minPrice : 0.0;
        Double maxProductPrice = (maxPrice != null) ? maxPrice : Double.MAX_VALUE;
        Integer minProductStock = (minStock != null) ? minStock : 0;
        Integer maxProductStock = (maxStock != null) ? maxStock : Integer.MAX_VALUE;
        if (category == null) {
            return productRepository.findAllByNameContainingAndPriceBetweenAndStockBetween(
                    productName, minProductPrice, maxProductPrice, minProductStock, maxProductStock, pageable);
        } else {
            // Si category no es null, aplicamos el filtro
            return productRepository.findAllByNameContainingAndPriceBetweenAndStockBetweenAndCategory(
                    productName, minProductPrice, maxProductPrice, minProductStock, maxProductStock, category, pageable);
        }
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    public Product createProduct(Product product) {

        boolean categoryExists = categoryService.getCategories()
                .stream()
                .anyMatch(category -> Objects.equals(category.getId(), product.getCategory()));

        if (!categoryExists) {
            throw new ResourceNotFoundException("Category not found for id: " + product.getCategory());
        }
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        Product product = getProduct(id);

        boolean categoryExists = categoryService.getCategories()
                .stream()
                .anyMatch(category -> Objects.equals(category.getId(), updatedProduct.getCategory()));

        if (!categoryExists) {
            throw new ResourceNotFoundException("Category not found for id: " + updatedProduct.getCategory());
        }

        product.setName(updatedProduct.getName());
        product.setPrice(updatedProduct.getPrice());
        product.setStock(updatedProduct.getStock());
        product.setCategory(updatedProduct.getCategory());
        return productRepository.save(product);
    }
}
