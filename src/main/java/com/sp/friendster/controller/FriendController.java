package com.sp.friendster.controller;

import com.sp.friendster.exception.RequestInvalidException;
import com.sp.friendster.model.*;
import com.sp.friendster.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friends")
public class FriendController {


    @Autowired
    private UserService userService;


    @ApiOperation(value = "Add friend connection between two email addresses", position = 1)
    @ApiResponses({
            @ApiResponse(code=200,response = CommonResponse.class, message = "Success"),
            @ApiResponse(code=400,response = ErrorResponse.class, message = "Request payload invalid")
    })
    @PostMapping(path = "/connect")
    public CommonResponse connect(@RequestBody FriendListRequest request){
        validateFriendsRequest(request);
        userService.addFriendConnection(request.getFriends()[0], request.getFriends()[1]);
        CommonResponse commonResponse = new CommonResponse(true);
        return commonResponse;
    }



    @PostMapping
    @ApiOperation(value = "Retrieve the friends list for an email address", position = 2)
    @ApiResponses({
            @ApiResponse(code=200,response = FriendListResponse.class, message = "Success"),
            @ApiResponse(code=400,response = ErrorResponse.class, message = "Request payload invalid"),
            @ApiResponse(code=404,response = ErrorResponse.class, message = "Email not exists")
    })
    public FriendListResponse listFriendsByEmail(@RequestBody EmailRequest request){
        if(request.getEmail()==null){
            throw new RequestInvalidException("friends must not null");
        }
        List<String> friends = userService.listFriendsByEmail(request.getEmail());
        FriendListResponse friendListResponse = new FriendListResponse(true,friends);
        return friendListResponse;
    }




    @ApiOperation(value = "Retrieve the common friends list between two email addresses", position = 3)
    @ApiResponses({
            @ApiResponse(code=200,response = FriendListResponse.class, message = "Success"),
            @ApiResponse(code=400,response = ErrorResponse.class, message = "Request payload invalid"),
            @ApiResponse(code=404,response = ErrorResponse.class, message = "Email not exists")
    })
    @PostMapping(path = "/common")
    public FriendListResponse listCommonFriends(@RequestBody FriendListRequest request){
        validateFriendsRequest(request);
        List<String> commonFriends = userService.listCommonFriends(request.getFriends()[0], request.getFriends()[1]);
        FriendListResponse friendListResponse = new FriendListResponse(true, commonFriends);
        return friendListResponse;
    }



    @ApiOperation(value = "To subscribe to updates from an email address", position = 4)
    @ApiResponses({
            @ApiResponse(code=200,response = CommonResponse.class, message = "Success"),
            @ApiResponse(code=400,response = ErrorResponse.class, message = "Request payload invalid"),
            @ApiResponse(code=404,response = ErrorResponse.class, message = "Email not exists")
    })
    @PostMapping(path = "/subscribe")
    public CommonResponse subscribe(@RequestBody SubscribeBlockRequest request){
        validateOnSubscribeOrBlock(request);
        userService.subscribe(request.getRequestor(), request.getTarget());
        CommonResponse commonResponse = new CommonResponse(true);
        return commonResponse;
    }



    @ApiOperation(value = "To block updates from an email address", position = 5)
    @ApiResponses({
            @ApiResponse(code=200,response = CommonResponse.class, message = "Success"),
            @ApiResponse(code=400,response = ErrorResponse.class, message = "Request payload invalid"),
            @ApiResponse(code=404,response = ErrorResponse.class, message = "Email not exists")
    })
    @PostMapping(path = "/block")
    public CommonResponse block(@RequestBody SubscribeBlockRequest request){
        validateOnSubscribeOrBlock(request);
        userService.block(request.getRequestor(), request.getTarget());
        CommonResponse commonResponse = new CommonResponse(true);
        return commonResponse;
    }




    @ApiOperation(value = "To retrieve all email addresses that can receive updates from an email address", position = 6)
    @ApiResponses({
            @ApiResponse(code=200,response = NotifyResponse.class, message = "Success"),
            @ApiResponse(code=400,response = ErrorResponse.class, message = "Request payload invalid"),
            @ApiResponse(code=404,response = ErrorResponse.class, message = "Email not exists")
    })
    @PostMapping(path = "/notify")
    public CommonResponse postMessage(@RequestBody NotifyRequest request){
        if(request.getSender()==null){
            throw new RequestInvalidException("sender must not null");
        }

        if(request.getText()==null){
            throw new RequestInvalidException("text must not null");
        }
        List<String> recipientList = userService.notifyFriend(request.getSender(), request.getText());
        NotifyResponse notifyResponse = new NotifyResponse(true, recipientList);
        return notifyResponse;
    }

    // #PAYLOAD VALIDATION#

    private void validateFriendsRequest(FriendListRequest request){
        if(request.getFriends()==null)
            throw new RequestInvalidException("friends must not null");

        if(request.getFriends().length != 2){
            throw new RequestInvalidException("friends size " + request.getFriends().length + ", expected 2");
        }
    }

    private void validateOnSubscribeOrBlock(SubscribeBlockRequest request){
        if(request.getRequestor()==null){
            throw new RequestInvalidException("requestor must not null");
        }

        if(request.getTarget()==null){
            throw new RequestInvalidException("target must not null");
        }
    }
}
