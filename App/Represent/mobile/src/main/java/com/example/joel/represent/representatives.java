package com.example.joel.represent;

import android.widget.ImageView;

/**
 * Created by joel on 3/1/16.
 */
public class representatives {
    private String id;
    private String name;
    private String party;
    private String term;
    private String website;
    private String email;
    private String tweet;
//    private int image;

    public representatives(String id, String name, String party,String term, String website, String email, String tweet) {
        this.id = id;
        this.name = name;
        this.party = party;
        this.term = term;
        this.website = website;
        this.email = email;
        this.tweet = tweet;
//        this.image = image;
    }
    public String getTerm(){
        return term;
    }
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getParty() {
        return party;
    }

    public String getWebsite() {
        return website;
    }

    public String getEmail() {
        return email;
    }

    public String getTweet() {
        return tweet;
    }

//    public int getImage() {
//        return image;
//    }
}
