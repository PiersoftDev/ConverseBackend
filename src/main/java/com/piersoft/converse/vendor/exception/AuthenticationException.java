package com.piersoft.converse.vendor.exception;

public class AuthenticationException extends Exception{

    private String message;

    public AuthenticationException(){
        super();
    }

    public AuthenticationException(String message){
        super(message);
        this.message = message;
    }
}
