package com.company.chatterbook;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


public class ChatterPost {
    private String text;

    public ChatterPost(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }
}
