package com.example.joel.represent;


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.inputmethodservice.Keyboard;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.FragmentGridPagerAdapter;
import android.support.wearable.view.GridPagerAdapter;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joel on 3/2/16.
 */
public class RepGridPagerAdapter extends FragmentGridPagerAdapter{
    private final Context mContext;
    private List<Row> mRows;

    public RepGridPagerAdapter(Context ctx, FragmentManager fm) {
        super(fm);
        mContext = ctx;
        mRows = new ArrayList<RepGridPagerAdapter.Row>();
        mRows.add(new Row(new watchList()));
        mRows.add(new Row(new watchList()));
        mRows.add(new Row(new watchList()));
        mRows.add(new Row(new VoteCard()));
    }

//    private Fragment cardFragment(int titleRes, int textRes) {
//        Resources res = mContext.getResources();
//        CardFragment fragment =
//                CardFragment.create(res.getText(titleRes), res.getText(textRes));
//
//        // Add some extra bottom margin to leave room for the page indicator
//        //fragment.setCardMarginBottom(
//        //      res.getDimensionPixelSize(R.dimen.card_margin_bottom));
//        return fragment;
//    }

    private class Row {
        final List<Fragment> columns = new ArrayList<Fragment>();

        public Row(Fragment... fragments) {
            for (Fragment f : fragments) {
                add(f);
            }
        }

        public void add(Fragment f) {
            columns.add(f);
        }

        Fragment getColumn(int i) {
            return columns.get(i);
        }

        public int getColumnCount() {
            return columns.size();
        }
    }
    @Override
    public Fragment getFragment(int row, int col) {
        Row adapterRow = mRows.get(row);
        return adapterRow.getColumn(col);
    }

    @Override
    public int getRowCount() {
        return mRows.size();
    }

    @Override
    public int getColumnCount(int i) {
        return mRows.get(i).getColumnCount();
    }
}
