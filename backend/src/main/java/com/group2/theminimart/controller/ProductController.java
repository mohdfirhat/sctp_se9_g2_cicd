package com.group2.theminimart.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.group2.theminimart.dto.ProductDto;
import com.group2.theminimart.dto.ProductRequestDto;
import com.group2.theminimart.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/products")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Create a new product")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("")
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductRequestDto productRequestDto) {
        logger.info("Request to create a new product: {}", productRequestDto.getTitle());
        ProductDto newProductDto = productService.createProduct(productRequestDto);
        logger.info("Created a new product|Id: {}", newProductDto.getId());
        return new ResponseEntity<>(newProductDto, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all products")
    @GetMapping("")
    public ResponseEntity<List<ProductDto>> getAllallProducts() {
        logger.info("Request to get all products");
        List<ProductDto> allProductsDto = productService.getAllProducts();
        logger.info("Returned all products size: {}", allProductsDto.size());
        return new ResponseEntity<>(allProductsDto, HttpStatus.OK);
    }

    @Operation(summary = "Get a product by id")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id) {
        logger.info("Request to get product by id: {}", id);
        ProductDto foundProductDto = productService.getProduct(id);
        logger.info("Returned product|Id: {}", foundProductDto.getId());
        return new ResponseEntity<>(foundProductDto, HttpStatus.OK);

    }

    @Operation(summary = "Update a product by id")
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id,
            @Valid @RequestBody ProductRequestDto productRequestDto) {
        logger.info("Request to update product by id: {}", id);
        ProductDto updatedProductDto = productService.updateProduct(id, productRequestDto);
        logger.info("Updated product|Id: {}", updatedProductDto.getId());
        return new ResponseEntity<>(updatedProductDto, HttpStatus.OK);
    }

    @Operation(summary = "Delete a product by id")
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable Long id) {
        logger.info("Request to delete product by id: {}", id);
        productService.deleteProduct(id);
        logger.info("Deleted product|Id: {}", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Search products by title, description, and category")
    @GetMapping("/search")
    public ResponseEntity<List<ProductDto>> searchProducts(@RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String category) {

        // Return empty list if all parameters are null or empty.
        if ((title == null || title.isEmpty()) &&
                (description == null || description.isEmpty()) &&
                (category == null || category.isEmpty())) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }

        title = title == null ? "" : title;
        description = description == null ? "" : description;
        category = category == null ? "" : category;
        logger.info("Request to search products by title: {}, description: {}, category: {}", title, description,
                category);
        List<ProductDto> foundProductsDto = productService.searchProducts(title, description, category);
        logger.info("Returned products size: {}", foundProductsDto.size());
        return new ResponseEntity<>(foundProductsDto, HttpStatus.OK);
    }
}
