package com.sp.friendster.exception;

public class RequestInvalidException extends RuntimeException {

    public RequestInvalidException(String message){
        super(message);
    }
}
