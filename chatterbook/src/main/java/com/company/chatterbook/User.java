package com.company.chatterbook;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private ArrayList<ChatterPost> chatterPosts;

    public User(String name) {
        this.name = name;
        this.chatterPosts = new ArrayList<ChatterPost>();
    }
    public void setChatterPosts(List<ChatterPost> myCatIsSoCute) {
    }
}