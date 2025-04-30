package com.group2.theminimart.mapper;

import com.group2.theminimart.dto.UserRegisterRequestDto;
import com.group2.theminimart.dto.UserResponseDto;
import com.group2.theminimart.dto.UserWithTokenResponseDto;
import com.group2.theminimart.entity.User;

public class UserMapper {
  public static UserResponseDto usertoUserResponseDto(User user) {
    return new UserResponseDto(
        user.getId(),
        user.getUsername());
    // user.getRatings(),
    // user.getCart());
  }

  public static UserWithTokenResponseDto userToUserWithTokenDto(User user) {
    return UserWithTokenResponseDto.builder().id(user.getId()).username(user.getUsername()).build();
  }

  public static User userRegisterDtoToUser(UserRegisterRequestDto userRegisterDto) {
    return User.builder().username(userRegisterDto.getUsername()).password(userRegisterDto.getPassword()).build();
  }
}
