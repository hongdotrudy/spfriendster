package com.sp.friendster.service;

import java.util.List;

public interface UserService {

    void addFriendConnection(String email1, String email2);
    List<String> listFriendsByEmail(String email);
    List<String> listCommonFriends(String email1, String email2);
}
