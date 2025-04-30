package com.group2.theminimart.service;

import java.util.List;

import com.group2.theminimart.dto.CartDto;
import com.group2.theminimart.entity.CartContent;

public interface CartContentService {
    // add business logic
    public CartContent createCartContent(CartContent cart);

    public CartDto createCartContent(Long userId, CartDto cartDto);
    
    public CartDto createCartContent(String username, CartDto cartDto);

    public List<CartDto> getCartContents(Long userId);

    public List<CartDto> getCartContents(String username);

    public CartDto getCartContent(Long userId);

    public List<CartDto> updateCart(Long userId, List<CartDto> cartDtoList);

    public List<CartDto> updateCart(String username, List<CartDto> cartDtoList);

    public CartDto updateCartContent(Long userId, Long productId, CartDto cartDto);
    
    public CartDto updateCartContent(String username, Long productId, CartDto cartDto);

    public void deleteCart(Long userId);
    
    public void deleteCart(String username);

    public void deleteCartContent(Long userId, Long productId);
    
    public void deleteCartContent(String username, Long productId);
}
