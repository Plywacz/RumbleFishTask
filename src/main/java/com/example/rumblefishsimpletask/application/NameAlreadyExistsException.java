package com.example.rumblefishsimpletask.application;

public class NameAlreadyExistsException extends RuntimeException{
    public NameAlreadyExistsException(String message) {
        super(message);
    }
}
