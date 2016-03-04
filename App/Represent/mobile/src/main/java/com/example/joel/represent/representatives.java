package com.example.joel.represent;

import android.widget.ImageView;

/**
 * Created by joel on 3/1/16.
 */
public class representatives {
    private int id;
    private String name;
    private String party;
    private String website;
    private String email;
    private String tweet;
    private int image;

    public representatives(int id, String name, String party, String website, String email, String tweet, int image) {
        this.id = id;
        this.name = name;
        this.party = party;
        this.website = website;
        this.email = email;
        this.tweet = tweet;
        this.image = image;
    }

    public int getId() {
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

    public int getImage() {
        return image;
    }
}
