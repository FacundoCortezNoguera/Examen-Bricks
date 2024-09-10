package com.example.bricks.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductResponse {
    private int id;
    private String name;
    private Double price;
    private Integer stock;
    private String categoryName;
}
