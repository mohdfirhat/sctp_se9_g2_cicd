package com.group2.theminimart.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group2.theminimart.dto.RatingResponseDto;
import com.group2.theminimart.entity.Rating;
import com.group2.theminimart.service.RatingService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/ratings")
public class RatingController {

  private RatingService ratingService;

  public RatingController(RatingService ratingService) {
    this.ratingService = ratingService;
  }

  // Create (only user able to create)

  // Read
  // admin feature
  @Operation(summary = "Get All Ratings")
  @GetMapping
  public ResponseEntity<List<RatingResponseDto>> getRatings() {
    return new ResponseEntity<>(ratingService.getRatings(), HttpStatus.OK);
  }

  // Update
  // admin feature
  @Operation(summary = "Update Rating by Id")
  @PutMapping("/{id}")
  public ResponseEntity<RatingResponseDto> updateRating(@PathVariable Long id,
      @Valid @RequestBody Rating rating) {
    return new ResponseEntity<>(ratingService.updateRating(id, rating),
        HttpStatus.OK);
  }

  // Delete
  // admin feature
  @Operation(summary = "Delete Rating by Id")
  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> deleteRating(@PathVariable Long id) {
    ratingService.deleteRating(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
