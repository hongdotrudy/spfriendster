package com.sp.friendster.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
public class User {

    @Id
    private String id;
    private String email;

    private List<String> friends = new ArrayList<>();
    private List<String> subscribe = new ArrayList<>();
    private List<String> blocks = new ArrayList<>();

    public User(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public void addFriend(String email){
        this.friends.add(email);
    }

    public List<String> getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(List<String> subscribe) {
        this.subscribe = subscribe;
    }

    public void subscribeTo(String email){
        this.subscribe.add(email);
    }

    public List<String> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<String> blocks) {
        this.blocks = blocks;
    }

    public void blockFrom(String email){
        this.blocks.add(email);
    }
}
