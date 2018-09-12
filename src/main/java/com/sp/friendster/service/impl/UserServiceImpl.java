package com.sp.friendster.service.impl;

import com.sp.friendster.domain.User;
import com.sp.friendster.exception.BlockFriendException;
import com.sp.friendster.exception.UserEmailNotFoundException;
import com.sp.friendster.repository.UserRepository;
import com.sp.friendster.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void addFriendConnection(String email1, String email2) {
        addFriendByEmail(email1, email2);
        addFriendByEmail(email2, email1);
    }

    @Override
    public List<String> listFriendsByEmail(String email) {
        User user =  findUserByEmail(email);
        return user.getFriends();
    }

    @Override
    public List<String> listCommonFriends(String email1, String email2) {
        List<String> commonFriends = new ArrayList<>();
        User user1 = findUserByEmail(email1);
        User user2 = findUserByEmail(email2);
        for(String friend : user1.getFriends()){
            if(user2.getFriends().contains(friend)){
                commonFriends.add(friend);
            }
        }
        return commonFriends;
    }

    @Override
    public void subscribe(String requestor, String target) {
        User userRequestor = findUserByEmail(requestor);
        User userTarget = findUserByEmail(target);
        if(!userRequestor.getSubscribe().contains(target)){
            userRequestor.subscribeTo(userTarget.getEmail());
        }
        userRepository.save(userRequestor);
    }

    @Override
    public void block(String requestor, String target) {
        User userRequestor = findUserByEmail(requestor);
        User userTarget = findUserByEmail(target);
        if(!userRequestor.getBlocks().contains(target)){
            userRequestor.blockFrom(userTarget.getEmail());
        }
        userRepository.save(userRequestor);
    }

    @Override
    public List<String> notifyFriend(String email, String message) {
        List<String> friendList = new ArrayList<>();
        User sender = findUserByEmail(email);
        List<User> users =  userRepository.findUsersToNotify(sender.getEmail(), message);
        for(User user : users){
            friendList.add(user.getEmail());
        }
        return friendList;
    }


    /**
     * Add new friend if it not exists in current friend list.
     * if email1 is not existing user, then create new document
     */
    private void addFriendByEmail(String email1, String email2){
        User user = userRepository.findByEmail(email1);
        if(user==null){
            user = new User(email1);
            user.addFriend(email2);
        } else {
            if(user.getBlocks().contains(email2)){
                throw new BlockFriendException(email2 + " is blocked by " + email1);
            }

            if(!user.getFriends().contains(email2)){
                user.addFriend(email2);
            }
        }
        userRepository.save(user);
    }

    /**
     * Find user using email address, if not found return UserEmailNotFoundException
     * @param email
     * @return
     */
    private User findUserByEmail(String email){
        User user = userRepository.findByEmail(email);
        if(user==null)
            throw new UserEmailNotFoundException("Email " + email + "not found");

        return user;
    }
}
