package com.group2.theminimart.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {
  private String message;
  @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
  private LocalDateTime timestamp;
}
