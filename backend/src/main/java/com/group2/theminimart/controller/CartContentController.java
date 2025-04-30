package com.group2.theminimart.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group2.theminimart.dto.CartDto;
import com.group2.theminimart.entity.CartContent;
import com.group2.theminimart.service.CartContentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/cart")
@SecurityRequirement(name = "bearerAuth")
public class CartContentController {
    private CartContentService cartContentService;

    public CartContentController(CartContentService cartContentService) {
        this.cartContentService = cartContentService;
    };

    // create
    @Operation(summary = "Create Cart Content")
    @PostMapping
    public ResponseEntity<CartContent> createCartContent(@Valid @RequestBody CartContent cartContent) {
        return new ResponseEntity<>(cartContentService.createCartContent(cartContent), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Get Cart Content by User Id")
    @GetMapping("/{id}")
    public ResponseEntity<CartDto> getCartContent(@PathVariable Long id) {
        return new ResponseEntity<>(cartContentService.getCartContent(id), HttpStatus.OK);
    }

    // update
    @Operation(summary = "Update Cart Content by Id")
    @PutMapping("/{id}")
    public ResponseEntity<CartDto> updateCartContent(@PathVariable Long id, @Valid @RequestBody CartDto cartDto) {
        return new ResponseEntity<>(cartContentService.updateCartContent(id, 1L, cartDto), HttpStatus.ACCEPTED);
    }

    // delete
    @Operation(summary = "Delete Cart by Id")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCart(@PathVariable Long id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
