package com.expenses_06.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryDto {
    private Long id;
    private String name;
    private String color;
}
