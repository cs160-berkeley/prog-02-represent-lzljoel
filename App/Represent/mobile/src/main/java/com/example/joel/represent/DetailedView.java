package com.example.joel.represent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by joel on 2/29/16.
 */
public class DetailedView extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed);
        Intent repDetail = getIntent();
        Bundle extras = repDetail.getExtras();
        String repId = extras.getString("personId");
        int img = Integer.parseInt(extras.getString("personPhoto"));
        ImageView photo = (ImageView) findViewById(R.id.photo);
        photo.setImageResource(img);
        TextView detailName = (TextView)findViewById(R.id.detailName);
        detailName.append(" " + repId);
    }
}
