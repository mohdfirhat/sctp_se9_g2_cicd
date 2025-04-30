package com.group2.theminimart.service;

import java.util.List;

import com.group2.theminimart.dto.RatingRequestDto;
import com.group2.theminimart.dto.RatingResponseDto;
import com.group2.theminimart.dto.UserLoginRequestDto;
import com.group2.theminimart.dto.UserRegisterRequestDto;
import com.group2.theminimart.dto.UserResponseDto;
import com.group2.theminimart.dto.UserUpdateRequestDto;
import com.group2.theminimart.dto.UserWithTokenResponseDto;

public interface UserService {
    // register
    public UserResponseDto registerUser(UserRegisterRequestDto userRegisterDto);

    public UserResponseDto registerAdminUser(UserRegisterRequestDto userRegisterDto);

    // login
    public UserWithTokenResponseDto loginUser(UserLoginRequestDto userLoginRequestDto);

    public List<UserResponseDto> getUsers();

    public UserResponseDto getUser(Long id);

    public UserResponseDto updateUserPassword(String username, UserUpdateRequestDto userUpdateRequestDto);

    public void deleteUser(String username);

    public List<RatingResponseDto> getUserRatings(String username);

    public RatingResponseDto getUserRatingByProductId(String username, Long productId);

    public RatingResponseDto addProductRating(String username, Long productId, RatingRequestDto ratingDto);

    public RatingResponseDto updateProductRating(String username, Long productId, RatingRequestDto ratingDto);

    public void deleteProductRating(String username, Long productId);
}
