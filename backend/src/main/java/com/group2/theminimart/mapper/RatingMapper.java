package com.group2.theminimart.mapper;

import com.group2.theminimart.dto.RatingResponseDto;
import com.group2.theminimart.entity.Rating;

public class RatingMapper {
  public static RatingResponseDto RatingtoDto(Rating rating) {
    return new RatingResponseDto(rating.getId(), rating.getRate(), rating.getUser().getId(),
        rating.getProduct().getId());

  }
}
