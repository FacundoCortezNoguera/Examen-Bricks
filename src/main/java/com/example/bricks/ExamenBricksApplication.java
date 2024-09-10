package com.example.bricks;

import com.example.bricks.model.Product;
import com.example.bricks.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ExamenBricksApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExamenBricksApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(ProductRepository productRepository) {
		return args -> {
			productRepository.save(new Product(1, "Product 1", 100.0, 10, 1));
			productRepository.save(new Product(2, "Product 2", 200.0, 5, 1));
			productRepository.save(new Product(3, "Product 3", 300.0, 20, 1));
			System.out.println("Products loaded into the database.");
		};
	}

}
