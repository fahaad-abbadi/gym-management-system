package com.example.advanced_mappings.exceptions;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message)
    {
        super(message);
    }
}
