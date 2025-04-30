package com.group2.theminimart.dto;

import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatingRequestDto {
  @NotNull
  @Range(min = 1, max = 5, message = "Rating should be between 1 to 5")
  private Double rate;
}
