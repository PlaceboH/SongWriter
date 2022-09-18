package com.songwriter.backend.exceptions;

public class MusicWorkNotFoundException extends RuntimeException {
    public MusicWorkNotFoundException(String ex) {
        super(ex);
    }
}
