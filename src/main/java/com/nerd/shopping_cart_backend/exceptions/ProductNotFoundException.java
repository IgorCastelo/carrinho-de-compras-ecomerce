package com.nerd.shopping_cart_backend.exceptions;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String  message ) {
        super(message);
    }
}
