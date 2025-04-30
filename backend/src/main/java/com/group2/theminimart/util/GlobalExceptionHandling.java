package com.group2.theminimart.util;

import java.time.LocalDateTime;
import java.util.List;

import com.group2.theminimart.dto.ErrorResponse;
import com.group2.theminimart.exception.CartContentAlreadyExistException;
import com.group2.theminimart.exception.CartContentNotFoundException;
import com.group2.theminimart.exception.CartNotFoundException;
import com.group2.theminimart.exception.ProductNotFoundException;
import com.group2.theminimart.exception.ProductSearchNotFoundException;
import com.group2.theminimart.exception.RatingAlreadyExistException;
import com.group2.theminimart.exception.RatingNotFoundException;
import com.group2.theminimart.exception.UserAlreadyExistException;
import com.group2.theminimart.exception.UserNotFoundException;
import com.group2.theminimart.exception.UserWrongLoginDetailsException;
import com.group2.theminimart.exception.WrongUserException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandling {

  private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandling.class);

  // 400 Bad Request
  @ExceptionHandler({
    RatingAlreadyExistException.class,
    UserWrongLoginDetailsException.class,
    WrongUserException.class,
    UserWrongLoginDetailsException.class 
  })
  public ResponseEntity<ErrorResponse> handleBadRequestException(Exception e) {
    logger.error("🔴 " + HttpStatus.BAD_REQUEST + " " + e);
    ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  // 400 Validation Exception
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleNotValidException(MethodArgumentNotValidException e) {
    // Get a list of all validation errors from the exception object
    List<ObjectError> validationErrors = e.getBindingResult().getAllErrors();
    // Create a StringBuilder to store all error messages
    StringBuilder sb = new StringBuilder();
    // Loop through all the errors and append the error messages
    for (ObjectError error : validationErrors) {
      sb.append(error.getDefaultMessage() + ". ");
    }
    logger.error("🔴 " + HttpStatus.BAD_REQUEST + " " + sb);
    ErrorResponse errorResponse = new ErrorResponse(sb.toString(), LocalDateTime.now());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  // 404 Resource not found
  @ExceptionHandler({
    CartNotFoundException.class, 
    CartContentNotFoundException.class,
    NoHandlerFoundException.class, 
    ProductNotFoundException.class, 
    ProductSearchNotFoundException.class, 
    RatingNotFoundException.class,
    UserNotFoundException.class
  })
  public ResponseEntity<ErrorResponse> handleNotFoundException(Exception e) {
    logger.error("🔴 " + HttpStatus.NOT_FOUND + " " + e);
    ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now());
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  } 

  // 409 Conflict
  @ExceptionHandler({
    CartContentAlreadyExistException.class, 
    UserAlreadyExistException.class
  })
  public ResponseEntity<ErrorResponse> handleConflitError(Exception e) {
    logger.error("🔴 " + HttpStatus.CONFLICT + " " + e.getMessage());
    ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now());
    return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
  }

  // Handle General Exceptions
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleException(Exception e) {
    // log exception, logger.error..
    logger.error("🔴" + HttpStatus.INTERNAL_SERVER_ERROR + " " + e);
    ErrorResponse errorResponse = new ErrorResponse("Something went wrong. Contact support for help.",
        LocalDateTime.now());
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
