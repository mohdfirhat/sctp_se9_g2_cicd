package com.group2.theminimart.exception;

import java.util.List;

public class ProductNotFoundException extends RuntimeException {
  public ProductNotFoundException(Long id) {
    super("Could not find product with id: " + id);
  }
  public ProductNotFoundException(List<Long> id) {
    super("Could not find products with ids: " + id);
  }
}
