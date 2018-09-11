package com.sp.friendster.controller;

import com.sp.friendster.exception.RequestInvalidException;
import com.sp.friendster.model.CommonResponse;
import com.sp.friendster.model.CommonRequest;
import com.sp.friendster.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friends")
public class FriendController {


    @Autowired
    private UserService userService;

    @PostMapping
    public CommonResponse listFriendsByEmail(@RequestBody CommonRequest request){
        if(request.getEmail()==null){
            throw new RequestInvalidException("request.friends must not null");
        }
        List<String> friends = userService.listFriendsByEmail(request.getEmail());
        CommonResponse commonResponse = new CommonResponse(true,friends);
        return commonResponse;
    }

    @PostMapping(path = "/connect")
    public CommonResponse connect(@RequestBody CommonRequest request){
        validateFriendsRequest(request);
        userService.addFriendConnection(request.getFriends()[0], request.getFriends()[1]);
        CommonResponse commonResponse = new CommonResponse(true);
        return commonResponse;
    }

    @PostMapping(path = "/common")
    public CommonResponse listCommonFriends(@RequestBody CommonRequest request){
        validateFriendsRequest(request);
        List<String> commonFriends = userService.listCommonFriends(request.getFriends()[0], request.getFriends()[1]);
        CommonResponse commonResponse = new CommonResponse(true, commonFriends);
        return commonResponse;
    }

    private void validateFriendsRequest(CommonRequest request){
        if(request.getFriends()==null)
            throw new RequestInvalidException("request.friends must not null");

        if(request.getFriends().length != 2){
            throw new RequestInvalidException("request.friends size " + request.getFriends().length + ", expected 2");
        }
    }
}
