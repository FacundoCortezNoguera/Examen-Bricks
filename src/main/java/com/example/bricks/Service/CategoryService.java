package com.example.bricks.Service;

import com.example.bricks.api.dto.Category;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CategoryService {

    @Value("${bricks.category.url}")
    String categoryApiUrl;

    @Cacheable("categories")
    public List<Category> getCategories() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Category>> response = restTemplate.exchange(
                categoryApiUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Category>>() {});
        return response.getBody();
    }

    @Scheduled(fixedRate = 86400000)  // Refresca el caché una vez al día
    @CacheEvict(value = "categories", allEntries = true)
    public void refreshCategoriesCache() {
        // Método para limpiar caché de categorías diario
    }
}
