package com.example.joel.represent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by joel on 2/29/16.
 */
public class DetailedView extends Activity {
    String lookup = "http://congress.api.sunlightfoundation.com/committees?member_ids=";
    String key = "&apikey=0f2d93fd52fa4f9c88fba788c27486e5";
    String forB = "http://congress.api.sunlightfoundation.com/bills?sponsor_id=";
    int toggle = 0;
    JSONArray representativesJSONArray;
    JSONArray billsJSONArray;
//    String s1 = "temp";
//    String p1 = "temp";
//    String e1 = "temp";
//    String w1 = "temp";
//    String t1 = "temp";
//    String end1 = "temp";
//    String bioguide = "temp";
    String b1 = "Bills: "; //bioguide
    String com1 = "Commitees: "; //bioguide
    int leng = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed);
        Intent repDetail = getIntent();
        Bundle extras = repDetail.getExtras();
        String repId = extras.getString("personName");
//        int img = Integer.parseInt(extras.getString("personPhoto"));
//        ImageView photo = (ImageView) findViewById(R.id.photo);
//        photo.setImageResource(img);
        TextView detailName = (TextView)findViewById(R.id.detailName);
        TextView partyView = (TextView) findViewById(R.id.party);
        TextView termView = (TextView) findViewById(R.id.term);
        TextView committeesView = (TextView) findViewById(R.id.committees);
        TextView billView = (TextView) findViewById(R.id.bills);

        String party = extras.getString("personParty");
        String term = extras.getString("personTerm");
        detailName.append(" " + repId);
        partyView.append(party);
        termView.append(term);
        ImageView iv2 = (ImageView) findViewById(R.id.photo);
        String temp = extras.getString("personId");
        Picasso.with(getApplicationContext()).load("https://theunitedstates.io/images/congress/225x275/" +
                temp + ".jpg").into(iv2);
        String web = lookup + temp + key;
        String mBill = forB + temp + key;
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            new DownloadWebpageTask().execute(web);
            new DownloadWebpageTask().execute(mBill);
        }

    }
    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            if (toggle == 1) {
                try {
                    URL url = new URL(urls[0]);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        StringBuilder stringBuilder = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuilder.append(line).append("\n");
                        }
                        bufferedReader.close();
                        toggle = 1;
                        return stringBuilder.toString();
                    }
                    finally{
                        urlConnection.disconnect();
                    }
                }
                catch(Exception e) {
                    Log.e("ERROR", e.getMessage(), e);
                    return null;
                }
            }
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    toggle = 1;
                    return stringBuilder.toString();
                }
                finally{
                    urlConnection.disconnect();
                }
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String response) {
            JSONObject JSONobj;
            if(response != null) {
                try {
                    JSONobj = (JSONObject) new JSONTokener(response).nextValue();
                    representativesJSONArray = JSONobj.getJSONArray("results");
                    if (toggle == 1) {
                        billsJSONArray = JSONobj.getJSONArray("results");
                    }
                    System.out.println(representativesJSONArray.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (billsJSONArray != null && toggle == 1) {
                for (int i = 0; i < billsJSONArray.length(); i++) {
                    try {
                        JSONObject temp = (JSONObject) billsJSONArray.get(i);

                        if (i < 3) {
                            b1 += temp.getString("introduced_on") + ", ";
                            if (temp.getString("short_title") != null) {
                                b1 += temp.getString("short_title");
                            } else {
                                b1 += temp.getString("official_title");
                            }
                        } else if (i == 3) {
                            b1 += temp.getString("introduced_on");
                            if (temp.getString("short_title") != null) {
                                b1 += temp.getString("short_title");
                            } else {
                                b1 += temp.getString("official_title");
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (representativesJSONArray != null) {
                for (int i=0; i< representativesJSONArray.length() ;i++){
                    try {
                        JSONObject temp = (JSONObject) representativesJSONArray.get(i) ;
                        if (i < 3) {
                            leng = representativesJSONArray.length();
                            com1 += temp.getString("name") + ", ";
                        } else if (i == 3) {
                            com1 += temp.getString("name");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println(com1);
            TextView bill = (TextView) findViewById(R.id.bills);
            bill.setText(com1);
            System.out.println(b1);
            TextView committee = (TextView) findViewById(R.id.committees);
            committee.setText(b1);
        }
    }
}

