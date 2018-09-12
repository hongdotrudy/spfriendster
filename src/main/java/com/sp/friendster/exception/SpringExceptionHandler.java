package com.sp.friendster.exception;


import com.sp.friendster.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Component
public class SpringExceptionHandler {

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorResponse> httpMessageNotReadable(){
        return new ResponseEntity<ErrorResponse>(new ErrorResponse(false, "Request body invalid"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({RequestInvalidException.class})
    public ResponseEntity<ErrorResponse> requestNotValid(Exception ex){
        return new ResponseEntity<ErrorResponse>(new ErrorResponse(false, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UserEmailNotFoundException.class})
    public ResponseEntity<ErrorResponse> userEmailNotFound(Exception ex){
        return new ResponseEntity<ErrorResponse>(new ErrorResponse(false, ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({BlockFriendException.class})
    public ResponseEntity<ErrorResponse> blockFriendException(Exception ex){
        return new ResponseEntity<ErrorResponse>(new ErrorResponse(false, ex.getMessage()), HttpStatus.CONFLICT);
    }
}
