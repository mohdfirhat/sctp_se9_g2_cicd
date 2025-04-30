package com.group2.theminimart.exception;

public class WrongUserException extends RuntimeException {

  public WrongUserException(Long id) {
    super("Username does not match with user with id: " + id);
  }

}
