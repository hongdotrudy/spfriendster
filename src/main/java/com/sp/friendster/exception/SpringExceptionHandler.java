package com.sp.friendster.exception;


import com.sp.friendster.model.CommonResponse;
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
    public ResponseEntity<CommonResponse> httpMessageNotReadable(){
        return new ResponseEntity<CommonResponse>(new CommonResponse(false, "Request body invalid"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({RequestInvalidException.class})
    public ResponseEntity<CommonResponse> requestNotValid(Exception ex){
        return new ResponseEntity<CommonResponse>(new CommonResponse(false, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UserEmailNotFoundException.class})
    public ResponseEntity<CommonResponse> userEmailNotFound(Exception ex){
        return new ResponseEntity<CommonResponse>(new CommonResponse(false, ex.getMessage()), HttpStatus.NOT_FOUND);
    }
}
