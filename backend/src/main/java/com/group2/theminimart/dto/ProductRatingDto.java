package com.group2.theminimart.dto;

//import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRatingDto {
    private Double rate;
    private Integer count;

}
