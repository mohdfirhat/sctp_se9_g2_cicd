package com.group2.theminimart.exception;

public class RatingAlreadyExistException extends RuntimeException {

  public RatingAlreadyExistException(String username, Long productId) {
    super("Rating for username '" + username + "' and productId '" + productId + "' already exist.");
  }

}
