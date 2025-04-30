package com.group2.theminimart.exception;

public class CartContentNotFoundException extends RuntimeException {
    public CartContentNotFoundException(Long cartContentId) {
        super("Could not find cartContent with id: " + cartContentId);
    }

    public CartContentNotFoundException(Long userId, Long productId) {
        super("The cart item with product id: " + productId + " and for user id: " + userId + " cannot be found");
    }
}
