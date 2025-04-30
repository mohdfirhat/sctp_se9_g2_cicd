package com.group2.theminimart.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long id;
    private String title;
    private Double price;
    private String description;
    private String category;
    private String image;
    private ProductRatingDto rating;

}