package com.group2.theminimart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RatingResponseDto {
  private Long id;

  private Double rate;

  private Long userId;

  private Long productId;
}
