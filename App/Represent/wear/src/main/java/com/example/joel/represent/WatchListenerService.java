package com.example.joel.represent;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.nio.charset.StandardCharsets;
/**
 * Created by joel on 3/2/16.
 */
public class WatchListenerService extends WearableListenerService {
    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("T", "in WatchListenerService, got: " + messageEvent.getPath());
        if(messageEvent.getPath() != null){
            Intent goList = new Intent(getApplicationContext(),ListView.class);
            Bundle watchZip = new Bundle();
            String zipCode = new String(messageEvent.getData());
            watchZip.putString("zipCode", zipCode);
            goList.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            goList.putExtras(watchZip);
            Log.d("T", "about to start watch List View with Zip Code");
            startActivity(goList);
        }
        else{
            super.onMessageReceived(messageEvent);
        }
    }
}
