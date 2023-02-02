package com.company.chatterbook;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private List<ChatterPost> chatterPosts;

    public User (String name) {
        this.name = name;
        this.chatterPosts = new ArrayList<>();
    }

    public List<ChatterPost> getChatterPosts() {
        return chatterPosts;
    }

    public void setChatterPosts(List<ChatterPost> chatterPosts) {
        this.chatterPosts = chatterPosts;
    }


    public Object getUsername() {
        return this.name;
    }
}