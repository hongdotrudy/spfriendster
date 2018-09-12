package com.sp.friendster.model;


import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse {

    private boolean success;
    private String message;
    private List<String> friends;
    private Integer count;
    private List<String> recipients;

    public CommonResponse(boolean success) {
        this.success = success;
    }

    public CommonResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public CommonResponse(boolean success, List<String> friends) {
        this.success = success;
        this.friends = friends;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public Integer getCount() {
        return this.friends!=null?this.friends.size():null;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<String> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<String> recipients) {
        this.recipients = recipients;
    }
}
