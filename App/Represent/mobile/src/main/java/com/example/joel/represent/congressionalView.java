package com.example.joel.represent;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.widget.AdapterView.*;

/**
 * Created by joel on 2/29/16.
 */
public class congressionalView extends Activity {
    private ListView lvRep;
    private RepAdapter adapter;
    protected static List<representatives> mRepList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rep_list);
        lvRep = (ListView) findViewById(R.id.listview_reps);
        mRepList = new ArrayList<>();
        mRepList.add(new representatives(1, "Barbara Boxer", "Democratic",
                "Website: http://www.boxer.senate.gov", "Email: senator@boxer.senate.gov",
                "Last Tweet: “Putting the country first means Obama nominating a Justice and the Senate doing its constitutional duty by voting on the nominee.",
                R.drawable.barbara_boxer));
        mRepList.add(new representatives(2, "Dianne Feistein", "Democratic",
                "Website: http://www.boxer.senate.gov", "Email: senator@boxer.senate.gov",
                "Last Tweet: “Putting the country first means Obama nominating a Justice and the Senate doing its constitutional duty by voting on the nominee.",
                R.drawable.dianne_feinstein));
        mRepList.add(new representatives(3, "Barbara Lee", "Democratic",
                "Website: http://www.boxer.senate.gov",
                "Email: senator@boxer.senate.gov",
                "Last Tweet: “Putting the country first means Obama nominating a Justice and the Senate doing its constitutional duty by voting on the nominee.",
                R.drawable.barbara_lee));

        adapter = new RepAdapter(getApplicationContext(), mRepList);
        lvRep.setAdapter(adapter);

        Intent intent  = getIntent();
        Bundle extras  = intent.getExtras();
        String zip = extras.getString("zipCode");
        Toast.makeText(getApplicationContext(), zip, Toast.LENGTH_SHORT).show();

        lvRep.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detail = new Intent(getApplicationContext(), DetailedView.class);
                Bundle extras = new Bundle();
                extras.putString("personId", mRepList.get(position).getName());
                extras.putString("personPhoto", String.valueOf(mRepList.get(position).getImage()));
                detail.putExtras(extras);
                startActivity(detail);
            }
        });


    }
    /*
    public void onDetailed(View v) {
        Intent detail = new Intent(this, DetailedView.class);
        detail.putExtra("personId", v.getTag().toString());
        startActivity(detail);
    }
    */

}