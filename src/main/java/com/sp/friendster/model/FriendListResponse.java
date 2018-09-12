package com.sp.friendster.model;

import java.util.List;

public class FriendListResponse extends CommonResponse {

    private List<String> friends;
    private Integer count;

    public FriendListResponse(boolean success) {
        super(success);
    }

    public FriendListResponse(boolean success, List<String> friends) {
        super(success);
        this.friends = friends;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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

}
