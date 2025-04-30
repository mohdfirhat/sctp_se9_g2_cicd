package com.group2.theminimart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SampleCartDto {
  private Long id;
  private String title;
  private double price;
  private String description;
  private String image;
  private int quantity;
  private double total;
}
