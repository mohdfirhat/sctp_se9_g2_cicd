package com.group2.theminimart.exception;

public class CartContentAlreadyExistException extends RuntimeException{
    public CartContentAlreadyExistException(Long userId, Long productId) {
        super("Trying to create a cart item with an already exisiting product id: " + productId + " for user id: " + userId);
    }
}
