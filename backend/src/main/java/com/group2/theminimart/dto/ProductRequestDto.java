package com.group2.theminimart.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequestDto {

    @NotBlank(message = "Product title is required")
    private String title;

    @NotNull(message = "Product price is required")
    @Positive(message = "Product price must be greater than 0")
    private Double price;

    private String description;
    private String category;
    private String image;

}