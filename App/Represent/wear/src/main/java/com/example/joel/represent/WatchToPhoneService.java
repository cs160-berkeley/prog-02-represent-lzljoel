package com.example.joel.represent;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.CapabilityApi;
import com.google.android.gms.wearable.CapabilityInfo;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by joel on 3/2/16.
 */
public class WatchToPhoneService extends Service {
    private GoogleApiClient mWatchApiClient;
    private List<Node> nodes = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        //initialize the googleAPIClient for message passing
        mWatchApiClient = new GoogleApiClient.Builder( this )
                .addApi( Wearable.API )
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle connectionHint) {
                    }

                    @Override
                    public void onConnectionSuspended(int cause) {
                    }
                })
                .build();
        //and actually connect it
//        mWatchApiClient.connect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWatchApiClient.disconnect();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Which cat do we want to feed? Grab this info from INTENT
        // which was passed over when we called startService
        Bundle extras = intent.getExtras();
        final String zipCode = extras.getString("zipCode");
        final String newLoc = extras.getString("newLoc");
        if(zipCode != null) {
            // Send the message with the zip code
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //first, connect to the apiclient
                    mWatchApiClient.connect();
                    //now that you're connected, send a massage with the zip code
                    sendMessage("/zipCode", zipCode);
                }
            }).start();
        }
        if(newLoc != null){
            // Send the message with the zip code
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //first, connect to the apiclient
                    mWatchApiClient.connect();
                    //now that you're connected, send a massage with the zip code
                    sendMessage("/newLoc", newLoc);
                }
            }).start();
        }
        return START_STICKY;
    }

//    @Override
//    public void onConnected(Bundle bundle) {
//        Log.d("T", "in onconnected");
//        Wearable.NodeApi.getConnectedNodes(mWatchApiClient)
//                .setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>() {
//                    @Override
//                    public void onResult(NodeApi.GetConnectedNodesResult getConnectedNodesResult) {
//                        nodes = getConnectedNodesResult.getNodes();
//                        Log.d("T", "found nodes");
//                        //when we find a connected node, we populate the list declared above
//                        //finally, we can send a message
//                        sendMessage("/send_toast", "Good job!");
//                        Log.d("T", "sent");
//                    }
//                });
//    }

    private void sendMessage( final String path, final String text ) {
        //one way to send message: start a new thread and call .await()
        //see watchtophoneservice for another way to send a message
        new Thread( new Runnable() {
            @Override
            public void run() {
                NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes( mWatchApiClient ).await();
                for(Node node : nodes.getNodes()) {
                    //we find 'nodes', which are nearby bluetooth devices (aka emulators)
                    //send a message for each of these nodes (just one, for an emulator)
                    MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(
                            mWatchApiClient, node.getId(), path, text.getBytes() ).await();
                    //4 arguments: api client, the node ID, the path (for the listener to parse),
                    //and the message itself (you need to convert it to bytes.)
                }
            }
        }).start();
    }
//    @Override
//    public void onConnectionSuspended(int i) {
//
//    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
