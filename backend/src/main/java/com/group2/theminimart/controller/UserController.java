package com.group2.theminimart.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group2.theminimart.dto.CartDto;
import com.group2.theminimart.dto.RatingRequestDto;
import com.group2.theminimart.dto.RatingResponseDto;
import com.group2.theminimart.dto.UserResponseDto;
import com.group2.theminimart.dto.UserUpdateRequestDto;
import com.group2.theminimart.service.CartContentService;
import com.group2.theminimart.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/users")
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    private UserService userService;
    private CartContentService cartContentService;

    public UserController(UserService userService, CartContentService cartContentService) {
        this.userService = userService;
        this.cartContentService = cartContentService;
    }

    // -------------- USERS CRUD OPERATIONS --------------
    // USER CREATE
    // Created at AuthController register

    // USER READ
    // admin feature
    @Operation(summary = "Get all users in the system")
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getUsers() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    // admin feature
    @Operation(summary = "Get a user by id")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }

    // USER UPDATE
    @Operation(summary = "Update user password")
    @PutMapping
    public ResponseEntity<UserResponseDto> updateUser(@RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(userService.updateUserPassword(username, userUpdateRequestDto), HttpStatus.OK);
    }

    // USER DELETE
    @Operation(summary = "Delete user account")
    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.deleteUser(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // -------------- RATINGS CRUD OPERATIONS --------------

    // RATINGS CREATE
    @Operation(summary = "Create a product rating by a user")
    @PostMapping("/ratings/products/{productId}")
    public ResponseEntity<RatingResponseDto> createProductRating(@PathVariable Long productId,
            @Valid @RequestBody RatingRequestDto ratingDto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(userService.addProductRating(username, productId,
                ratingDto), HttpStatus.CREATED);
    }

    // RATINGS READ
    @Operation(summary = "Get all ratings by a user")
    @GetMapping("/ratings")
    public ResponseEntity<List<RatingResponseDto>> getUserRatings() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(userService.getUserRatings(username), HttpStatus.OK);
    }

    @Operation(summary = "Get a product rating by a user")
    @GetMapping("/ratings/products/{productId}")
    public ResponseEntity<RatingResponseDto> getUserRating(@PathVariable Long productId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(userService.getUserRatingByProductId(username, productId), HttpStatus.OK);
    }

    // RATINGS UPDATE
    @Operation(summary = "Update a product rating by a user")
    @PutMapping("/ratings/products/{productId}")
    public ResponseEntity<RatingResponseDto> updateProductRating(@PathVariable Long productId,
            @Valid @RequestBody RatingRequestDto ratingDto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(userService.updateProductRating(username, productId, ratingDto), HttpStatus.OK);
    }

    // RATINGS DELETE
    @Operation(summary = "Delete a product rating by a user")
    @DeleteMapping("/ratings/products/{productId}")
    public ResponseEntity<HttpStatus> deleteProductRating(@PathVariable Long productId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.deleteProductRating(username, productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // -------------- CART CRUD OPERATIONS --------------

    // CART CREATE
    @Operation(summary = "Create a cart content by a user")
    @PostMapping("/cart")
    public ResponseEntity<CartDto> createCartContent(@Valid @RequestBody CartDto cartDto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(cartContentService.createCartContent(username, cartDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Get all cart contents by a user")
    @GetMapping("/cart")
    public ResponseEntity<List<CartDto>> getUserCart() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(cartContentService.getCartContents(username), HttpStatus.OK);
    }

    // CART UPDATE
    @Operation(summary = "Update the whole cart contents by a user")
    @PutMapping("/cart")
    public ResponseEntity<List<CartDto>> updateCart(@Valid @RequestBody List<CartDto> cartDtoList) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(cartContentService.updateCart(username, cartDtoList), HttpStatus.OK);
    }

    @Operation(summary = "Update a cart product content by a user")
    @PutMapping("/cart/{productId}")
    public ResponseEntity<CartDto> updateCartContent(@Valid @PathVariable Long productId,
            @Valid @RequestBody CartDto cartDto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(cartContentService.updateCartContent(username, productId, cartDto), HttpStatus.OK);
    }

    // CART DELETE
    @Operation(summary = "Delete the whole cart by a user")
    @DeleteMapping("cart")
    public ResponseEntity<HttpStatus> deleteCart() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        cartContentService.deleteCart(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Delete a cart product content by a user")
    @DeleteMapping("/cart/{productId}")
    public ResponseEntity<HttpStatus> deleteCartContent(@Valid @PathVariable Long productId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        cartContentService.deleteCartContent(username, productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
