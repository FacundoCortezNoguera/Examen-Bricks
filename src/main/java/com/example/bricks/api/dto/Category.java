package com.example.bricks.api.dto;

import lombok.Data;

@Data
public class Category {
    private Integer id;
    private String code;
    private String name;
    private int position;
    private String icon;
    private String description;
}