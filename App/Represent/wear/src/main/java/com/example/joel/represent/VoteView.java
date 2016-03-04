package com.example.joel.represent;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by joel on 3/2/16.
 */
public class VoteView extends WearableActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vote_2012);
        Intent fromPhone = getIntent();
        Bundle phoneBundle = fromPhone.getExtras();
        String zip = phoneBundle.getString("zipCode");
        TextView voteLoc = (TextView) findViewById(R.id.voteArea);
        voteLoc.setText(zip);
        Toast.makeText(getApplicationContext(), zip, Toast.LENGTH_SHORT).show();
    }
}
