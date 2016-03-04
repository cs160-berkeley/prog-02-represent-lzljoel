package com.example.joel.represent;

import android.widget.ImageView;

/**
 * Created by joel on 3/1/16.
 */
public class representatives {
    private int id;
    private String name;
    private String party;
    private int image;

    public representatives(int id, String name, String party, int image) {
        this.id = id;
        this.name = name;
        this.party = party;
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

    public int getImage() {
        return image;
    }
}
