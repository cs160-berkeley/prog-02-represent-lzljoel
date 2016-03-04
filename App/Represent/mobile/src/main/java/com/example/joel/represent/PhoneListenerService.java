package com.example.joel.represent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.nio.charset.StandardCharsets;

/**
 * Created by joel on 2/29/16.
 */
public class PhoneListenerService extends WearableListenerService {

    //   WearableListenerServices don't need an iBinder or an onStartCommand: they just need an onMessageReceieved.
    private static final String ZIP_CODE = "/zipCode" ;
    private static final String NEW_LOC = "/newLoc";
    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("T", "in WatchListenerService, got: " + messageEvent.getPath());
        if(messageEvent.getPath() .equalsIgnoreCase( ZIP_CODE ) ){
            Intent goList = new Intent(getApplicationContext(),FromWatchDetailed.class);
            Bundle watchZip = new Bundle();
            String zipCode = new String(messageEvent.getData());
            watchZip.putString("zipCode", zipCode);
            goList.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            goList.putExtras(watchZip);
            Log.d("T", "about to start Detailed View with Zip Code");
            startActivity(goList);
        }
        if(messageEvent.getPath() .equalsIgnoreCase( NEW_LOC) ){
            String newLoc = new String(messageEvent.getData());
            Toast.makeText(getApplicationContext(), newLoc, Toast.LENGTH_SHORT).show();
        }
         else {
            super.onMessageReceived( messageEvent );
        }

    }
}
