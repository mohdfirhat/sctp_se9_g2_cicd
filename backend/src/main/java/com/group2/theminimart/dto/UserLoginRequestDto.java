package com.group2.theminimart.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRequestDto {
  @NotBlank(message = "Username must not be blank")
  @Length(min = 3, message = "Username has to be at least 3 characters")
  private String username;
  @NotBlank(message = "Password must not be blank")
  @Length(min = 8, message = "Password has to be at least 8 characters")
  private String password;
}
