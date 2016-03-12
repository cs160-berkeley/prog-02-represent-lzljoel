package com.example.joel.represent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static android.widget.AdapterView.*;

/**
 * Created by joel on 2/29/16.
 */
public class congressionalView extends Activity {
    private ListView lvRep;
    private RepAdapter adapter;
    protected static List<representatives> mRepList;
    protected ArrayList<JSONObject> jList = new ArrayList<JSONObject>();
    private String sunLight;
    JSONArray jArray;
    private String zipUrl = "http://congress.api.sunlightfoundation.com/legislators/locate?zip=";
    private String keyUrl = "&apikey=0f2d93fd52fa4f9c88fba788c27486e5";
    private String latUrl = "http://congress.api.sunlightfoundation.com/legislators/locate?latitude=";
    private String longUrl = "&longitude=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rep_list);

        Intent intent  = getIntent();
        Bundle extras  = intent.getExtras();
        String zip = extras.getString("zipCode");
        String mlat = extras.getString("lat");
        String mLong = extras.getString("long");
        //Toast.makeText(getApplicationContext(), zip, Toast.LENGTH_SHORT).show();
        if(zip != null){
            sunLight = zipUrl + zip + keyUrl;
            Toast.makeText(getApplicationContext(), zip, Toast.LENGTH_SHORT).show();
        }
        else{
            sunLight = latUrl + mlat + longUrl + mLong + keyUrl;
            Toast.makeText(getApplicationContext(), mlat + "," + mLong, Toast.LENGTH_SHORT).show();
        }
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            new RetrieveFeedTask().execute(sunLight);
        }



//        lvRep = (ListView) findViewById(R.id.listview_reps);
//        mRepList = new ArrayList<>();
//        mRepList.add(new representatives(1, "Barbara Boxer", "Democratic",
//                "Website: http://www.boxer.senate.gov", "Email: senator@boxer.senate.gov",
//                "Last Tweet: “Putting the country first means Obama nominating a Justice and the Senate doing its constitutional duty by voting on the nominee.",
//                R.drawable.barbara_boxer));
//        mRepList.add(new representatives(2, "Dianne Feistein", "Democratic",
//                "Website: http://www.boxer.senate.gov", "Email: senator@boxer.senate.gov",
//                "Last Tweet: “Putting the country first means Obama nominating a Justice and the Senate doing its constitutional duty by voting on the nominee.",
//                R.drawable.dianne_feinstein));
//        mRepList.add(new representatives(3, "Barbara Lee", "Democratic",
//                "Website: http://www.boxer.senate.gov",
//                "Email: senator@boxer.senate.gov",
//                "Last Tweet: “Putting the country first means Obama nominating a Justice and the Senate doing its constitutional duty by voting on the nominee.",
//                R.drawable.barbara_lee));

//        adapter = new RepAdapter(getApplicationContext(), jList);
//        lvRep.setAdapter(adapter);
//
//        lvRep.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent detail = new Intent(getApplicationContext(), DetailedView.class);
//                Bundle extras = new Bundle();
//                extras.putString("personId", mRepList.get(position).getName());
//                extras.putString("personPhoto", String.valueOf(mRepList.get(position).getImage()));
//                detail.putExtras(extras);
//                startActivity(detail);
//            }
//        });


    }
    class RetrieveFeedTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... url) {
//            URL apiurlcc;
            try {
                URL apiurlcc = new URL(url[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) apiurlcc.openConnection();
                urlConnection.connect();
                try {
                    BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( urlConnection.getInputStream()));
                    String line;
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    Log.d("representatives", stringBuilder.toString());
                    return stringBuilder.toString();

                }
                finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
            }catch (MalformedURLException e) {
                e.printStackTrace();
                return ("URL is not responding============================");
            } catch (IOException e) {
                e.printStackTrace();
                return ("URL is invalid============================");
            }catch (Exception e){
                Log.e("ERROR", e.getMessage(),e);
                return null;
            }
        }

        protected void onPostExecute(String response) {
            System.out.print(response);
            JSONObject jObject;
            if(response != null){
                try{
                    jObject = (JSONObject) new JSONTokener(response).nextValue();
                    jArray = jObject.getJSONArray("results");
                    System.out.print(jArray.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
//            if(jArray != null){
//                for(int i = 0; i < jArray.length(); i ++){
//                    try {
//                        JSONObject temp = (JSONObject) jArray.get(i);
//                        //                        // Pulling items from the array
////                        String id = temp.getString("bioguide_id");
////
////                        String firstName = temp.getString("first_name");
////                        String lastName = temp.getString("last_name");
////                        String party = temp.getString("party");
////                        String website = temp.getString("website");
////                        String email = temp.getString("oc_email");
////                        String tweet = temp.getString("twitter_id");
////                        jList.add(new representatives(1,firstName + " " + lastName, party, website, email,tweet, R.drawable.barbara_boxer));
//                        jList.add(temp);
//                        System.out.print(jList.toString());
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
            lvRep = (ListView) findViewById(R.id.listview_reps);
            adapter = new RepAdapter(getApplicationContext(), jArray);
            mRepList = adapter.getList();


            lvRep.setAdapter(adapter);
            lvRep.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent detail = new Intent(getApplicationContext(), DetailedView.class);
                    Bundle extras = new Bundle();
                    extras.putString("personName", mRepList.get(position).getName());
                    extras.putString("personId", mRepList.get(position).getId());
                    extras.putString("personParty", mRepList.get(position).getParty());
                    extras.putString("personTerm",mRepList.get(position).getTerm());
//                    extras.putString("personPhoto", String.valueOf(mRepList.get(position).getImage()));
                    detail.putExtras(extras);
                    startActivity(detail);
                }
            });


//            try {
//                JSONArray jArray = feed.getJSONArray("results");
//                for(int i = 0; i < jArray.length(); i ++){
//                    try {
//                        JSONObject oneObject = jArray.getJSONObject(i);
//                        // Pulling items from the array
//                        int id = Integer.parseInt(oneObject.getString("bioguide_id"));
//                        String firstName = oneObject.getString("first_name");
//                        String lastName = oneObject.getString("last_name");
//                        String party = oneObject.getString("party");
//                        String website = oneObject.getString("website");
//                        String email = oneObject.getString("oc_email");
//                        String tweet = oneObject.getString("twitter_id");
//                        jList.add(new representatives(id,firstName + " " + lastName, party, website, email,tweet, R.drawable.barbara_boxer));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
        }
    }
    private String readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while(i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo.toString();
        } catch (IOException e) {
            return "";
        }
    }


}