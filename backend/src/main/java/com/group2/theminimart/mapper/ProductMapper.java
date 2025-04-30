package com.group2.theminimart.mapper;

import com.group2.theminimart.dto.ProductRequestDto;
import com.group2.theminimart.entity.Product;

public class ProductMapper {

    public static Product productDtoToProduct(ProductRequestDto productRequestDto) {

        return Product.builder().title(productRequestDto.getTitle()).price(productRequestDto.getPrice())
                .description(productRequestDto.getDescription()).category(productRequestDto.getCategory())
                .image(productRequestDto.getImage()).build();

    }

}
