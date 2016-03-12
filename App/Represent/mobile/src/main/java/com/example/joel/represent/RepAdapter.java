package com.example.joel.represent;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joel on 3/1/16.
 */
public class RepAdapter extends BaseAdapter {
    private Context mContext;
    private List<representatives> mRepList = new ArrayList<>();
//    private ArrayList<JSONObject> jList;
    private JSONArray ja;
    private String id = null;
    public RepAdapter(Context mContext, JSONArray jArray) {
        this.mContext = mContext;
        this.ja = jArray;
        for(int i = 0; i < jArray.length(); i++) {
            try {
                JSONObject jb = jArray.getJSONObject(i);
                id = jb.getString("bioguide_id").toString();
                String firstName = jb.getString("first_name").toString();
                String lastName = jb.getString("last_name").toString();
                String party = jb.getString("party").toString();
                String term = jb.getString("term_end").toString();
                String website = jb.getString("website").toString();
                String email = jb.getString("oc_email").toString();
                String tweet = jb.getString("twitter_id").toString();
//                System.out.println("string to get " + jb.toString());
                System.out.println("test1 " + firstName);
                System.out.println("test1 " + lastName);
                System.out.println("test1 " + party);
                System.out.println("test1 " + website);
                System.out.println("test1 " + email);
                System.out.println("test1 " + tweet);
                mRepList.add(new representatives(id, tweet, party, term, website, email, tweet));
//                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//                View repView = inflater.inflate(R.layout.rep, parent, false);
//
//                ImageView iv2 = (ImageView) repView.findViewById(R.id.rep_image);
//                Picasso.with(mContext).load("https://theunitedstates.io/images/congress/225x275/" +
//                        id + ".jpg").into(iv2);
            }catch (JSONException e){
                e.printStackTrace();
            }
        }

//        this.jArray = jArray;
    }
    public List<representatives> getList(){
        return mRepList;
    }
    @Override
    public int getCount() {
        return mRepList.size();
    }

    @Override
    public Object getItem(int position) {
        return mRepList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        View repView = inflater.inflate(R.layout.rep, parent, false);
        TextView nameView = (TextView) repView.findViewById(R.id.rep_name);
        TextView partyView = (TextView) repView.findViewById(R.id.party);
        TextView webView = (TextView) repView.findViewById(R.id.website);
        TextView emailView = (TextView) repView.findViewById(R.id.email);
        TextView tweetView = (TextView) repView.findViewById(R.id.tweet);
        ImageView imageView = (ImageView) repView.findViewById(R.id.rep_image);

        ImageView iv2 = (ImageView) repView.findViewById(R.id.rep_image);
//        Picasso.with(mContext).load("https://theunitedstates.io/images/congress/225x275/" +
//                id + ".jpg").into(iv2);
//        ImageView view = (ImageView) convertView;
//        if (view == null) {
//            view = new ImageView(mContext);
//        }
        String temp = mRepList.get(position).getId();
        Picasso.with(mContext).load("https://theunitedstates.io/images/congress/225x275/" +
                temp + ".jpg").into(iv2);

        nameView.setText(mRepList.get(position).getName());
        partyView.setText(mRepList.get(position).getParty());
        webView.setText(mRepList.get(position).getWebsite());
        emailView.setText(mRepList.get(position).getEmail());
        tweetView.setText(mRepList.get(position).getTweet());
//        imageView.setImageResource(mRepList.get(position).getImage());

        return repView;
    }




}
