package com.sp.friendster.repository;

import com.sp.friendster.domain.User;

import java.util.List;

public interface UserCustomRepository {

    List<User> findUsersToNotify(String email, String message);

}
