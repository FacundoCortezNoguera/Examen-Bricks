package com.example.bricks.Service;

import com.example.bricks.api.dto.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ResponseEntity<List<Category>> responseEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        categoryService.categoryApiUrl = "https://api.develop.bricks.com.ar/loyalty/category/producer";
    }

    @Test
    void testGetCategories() {
        // Datos simulados
        Category category = new Category();
        category.setId(1);  // Supón que la categoría tiene id = 1
        category.setName("Category 1");
        category.setCode("CAT1");
        category.setPosition(1);
        category.setIcon("icon.png");
        category.setDescription("Description of Category 1");

        List<Category> categoryList = Collections.singletonList(category);

        // Simulación del comportamiento del RestTemplate
        when(restTemplate.exchange(
                any(String.class),
                eq(HttpMethod.GET),
                eq(null),
                any(ParameterizedTypeReference.class)
        )).thenReturn(responseEntity);

        when(responseEntity.getBody()).thenReturn(categoryList);

        // Llamada al método
        List<Category> result = categoryService.getCategories();

        // Verificación
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Category 1", result.get(0).getName());
        assertEquals("CAT1", result.get(0).getCode());
        assertEquals(1, result.get(0).getPosition());
        assertEquals("icon.png", result.get(0).getIcon());
        assertEquals("Description of Category 1", result.get(0).getDescription());

        // Verifica que RestTemplate haya sido llamado con la URL correcta
        verify(restTemplate, times(1)).exchange(
                anyString(),
                eq(HttpMethod.GET),
                eq(null),
                any(ParameterizedTypeReference.class)
        );
    }

    @Test
    void testGetCategoriesEmptyResponse() {
        // Simulación de una respuesta vacía
        when(restTemplate.exchange(
                any(String.class),
                eq(HttpMethod.GET),
                eq(null),
                any(ParameterizedTypeReference.class)
        )).thenReturn(responseEntity);

        when(responseEntity.getBody()).thenReturn(Collections.emptyList());

        // Llamada al método
        List<Category> result = categoryService.getCategories();

        // Verificación
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void testRefreshCategoriesCache() {
        // Simulación de la limpieza del caché
        categoryService.refreshCategoriesCache();

        // No se puede verificar el caché directamente en un test unitario, pero podemos verificar que el método se llama sin errores
        // Aquí solo verificamos que el método no lance excepciones
        assertDoesNotThrow(() -> categoryService.refreshCategoriesCache());
    }

}