package com.fiseq.image_service.exception;

public class NoSuchImageExistException extends RuntimeException{
    private String message;

    public NoSuchImageExistException() {}

    public NoSuchImageExistException(String msg) {
        super(msg);
        this.message = msg;
    }
}
