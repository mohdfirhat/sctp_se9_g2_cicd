package com.group2.theminimart.service;

import java.util.List;

import com.group2.theminimart.dto.RatingResponseDto;
import com.group2.theminimart.entity.Rating;

public interface RatingService {
  public List<RatingResponseDto> getRatings();

  public RatingResponseDto getRating(Long id);

  public RatingResponseDto updateRating(Long id, Rating rating);

  public void deleteRating(Long id);
}
