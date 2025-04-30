package com.group2.theminimart.exception;

public class ProductSearchNotFoundException extends RuntimeException {
    public ProductSearchNotFoundException(String title, String description, String category) {
        super(String.format("No products found matching criteria - title: %s, description: %s, category: %s",
                title != null ? title : "",
                description != null ? description : "",
                category != null ? category : ""));
    }
}
