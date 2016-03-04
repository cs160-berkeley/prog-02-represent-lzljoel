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

import java.util.List;

/**
 * Created by joel on 3/1/16.
 */
public class RepAdapter extends BaseAdapter {
    private Context mContext;
    private List<representatives> mRepList;

    public RepAdapter(Context mContext, List<representatives> mRepList) {
        this.mContext = mContext;
        this.mRepList = mRepList;
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


        nameView.setText(mRepList.get(position).getName());
        partyView.setText(mRepList.get(position).getParty());
        webView.setText(mRepList.get(position).getWebsite());
        emailView.setText(mRepList.get(position).getEmail());
        tweetView.setText(mRepList.get(position).getTweet());
        imageView.setImageResource(mRepList.get(position).getImage());

        return repView;
    }




}
