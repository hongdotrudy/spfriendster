package com.sp.friendster.model;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;

@Api
@ApiModel
public class ErrorResponse extends CommonResponse{

    private String message;

    public ErrorResponse(boolean success, String message) {
        super(success);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
