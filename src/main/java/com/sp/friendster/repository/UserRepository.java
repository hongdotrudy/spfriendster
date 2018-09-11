package com.sp.friendster.repository;

import com.sp.friendster.domain.User;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, ObjectId> {

    User findByEmail (String email);
}
