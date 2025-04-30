package com.group2.theminimart.service;

import java.util.List;

import com.group2.theminimart.dto.ProductDto;
import com.group2.theminimart.dto.ProductRequestDto;

public interface ProductService {

    ProductDto createProduct(ProductRequestDto productRequestDto);

    ProductDto getProduct(Long id);

    List<ProductDto> getAllProducts();

    ProductDto updateProduct(Long id, ProductRequestDto productRequestDto);

    void deleteProduct(Long id);

    List<ProductDto> searchProducts(String title, String description, String category);
}
