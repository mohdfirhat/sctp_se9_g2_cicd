package com.group2.theminimart.exception;

public class RatingNotFoundException extends RuntimeException {

  public RatingNotFoundException() {
    super("Could not find rating with that username or id.");
  }

  public RatingNotFoundException(Long userId) {
    super("Could not find rating for user id: " + userId);
  }

  public RatingNotFoundException(Long userId, Long productId) {
    super("Could not find rating for user id " + userId + " and product id: " + productId);
  }

}
