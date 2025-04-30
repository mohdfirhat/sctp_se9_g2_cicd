package com.group2.theminimart.mapper;

import com.group2.theminimart.dto.CartDto;
import com.group2.theminimart.entity.CartContent;
import com.group2.theminimart.entity.Product;
import com.group2.theminimart.entity.User;

public class CartMapper {
    // public static CartDto toDto(CartContent cartContent) {
    //     return new CartDto(
    //         cartContent.getId(),
    //         cartContent.getUser().getId(),
    //         cartContent.getProduct().getId(),
    //         cartContent.getProduct().getPrice(),
    //         cartContent.getCount(),
    //         cartContent.getTotal()
    //     );
    // } 
    
    public static CartDto toDto(CartContent cartContent) {
        return new CartDto(
            cartContent.getProduct().getId(),
            cartContent.getProduct().getTitle(),
            cartContent.getProduct().getPrice(),
            cartContent.getProduct().getDescription(),
            cartContent.getProduct().getImage(),
            cartContent.getCount(),
            cartContent.getTotal()
        );
    }

    public static CartContent fromDto(CartDto cartDto, User user, Product product) {
        return CartContent.builder()
                .user(user)
                .product(product) 
                .count(cartDto.getQuantity())
                .total(cartDto.getTotal())
                .build();
    }
    
}
