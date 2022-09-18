package com.songwriter.backend.exceptions;

public class ImageNotFoundException extends RuntimeException {
    public ImageNotFoundException(String ex) {
        super(ex);
    }
}
