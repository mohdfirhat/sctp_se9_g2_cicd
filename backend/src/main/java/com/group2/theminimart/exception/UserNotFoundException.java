package com.group2.theminimart.exception;

public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException() {
    super("Could not find user with that username.");
  }

  public UserNotFoundException(String username) {
    super("Could not find user with username: " + username);
  }

  public UserNotFoundException(Long id) {
    super("Could not find user with id: " + id);
  }

}
