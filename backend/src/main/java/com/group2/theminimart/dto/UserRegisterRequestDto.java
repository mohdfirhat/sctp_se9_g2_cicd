package com.group2.theminimart.dto;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserRegisterRequestDto {
  @NotBlank(message = "Username must not be blank")
  @Length(min = 3, message = "Username has to be at least 3 characters")
  private String username;
  @NotBlank(message = "Password must not be blank")
  @Length(min = 8, message = "Password has to be at least 8 characters")
  private String password;
  private List<String> roles;
}
