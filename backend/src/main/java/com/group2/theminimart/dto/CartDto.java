package com.group2.theminimart.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    // private Long id;
    // private Long userId;
    // private Long productId;
    // private double price;
    // @Positive(message = "Count must be a positive number")
    // private int count;
    // @Positive(message = "Total must be a positive number")
    // private double total;

    @Positive
    private Long id;
    private String title;
    @PositiveOrZero
    private double price;
    private String description;
    private String image;
    @PositiveOrZero
    private int quantity;
    @PositiveOrZero
    private double total;
}
