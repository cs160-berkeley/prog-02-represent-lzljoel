package com.example.joel.represent;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.wearable.activity.WearableActivity;


import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.support.wearable.view.DotsPageIndicator;
import android.support.wearable.view.GridViewPager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by joel on 3/2/16.
 */
public class ListView extends WearableActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mAcc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        final Resources res = getResources();
        final GridViewPager pager = (GridViewPager) findViewById(R.id.pager);
        pager.setAdapter(new RepGridPagerAdapter(this, getFragmentManager()));
        DotsPageIndicator dotsPageIndicator = (DotsPageIndicator) findViewById(R.id.page_indicator);
        dotsPageIndicator.setPager(pager);


//        Intent fromPhone = getIntent();
//        Bundle phoneBundle = fromPhone.getExtras();
//        String zip = phoneBundle.getString("zipCode");
//        Toast.makeText(getApplicationContext(), zip, Toast.LENGTH_SHORT).show();

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAcc = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


    }
    public void voteView(View view) {
        Intent goVote = new Intent(this, VoteView.class);
//        Bundle loc = new Bundle();
//        Intent fromPhone = getIntent();
//        Bundle phoneBundle = fromPhone.getExtras();
//        String zip = phoneBundle.getString("zipCode");
//        loc.putString("zipCode",zip);
//        goVote.putExtras(loc);
        startActivity(goVote);
    }
    public void goPhone(View view){
        Intent goPhone = new Intent(this, WatchToPhoneService.class);
//        Bundle b = new Bundle();
//        b.putString("zipCode", "zip");
//        goPhone.putExtras(b);
        startService(goPhone);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        float mCurrent = (float) Math.sqrt(x*x+y*y+z*z);
        if (mCurrent > 100) {
            Intent sensor = new Intent(this, WatchToPhoneService.class);
            Bundle sensorBundle = new Bundle();
            sensorBundle.putString("newLoc", String.valueOf((Math.random() * 10000 + 1)));
            sensor.putExtras(sensorBundle);
            startService(sensor);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAcc, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
}
