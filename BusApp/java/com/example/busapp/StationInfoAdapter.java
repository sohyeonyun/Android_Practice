package com.example.busapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class StationInfoAdapter extends BaseAdapter {
    Context con;
    ArrayList<StationInfo> total;

    public StationInfoAdapter(Context c, ArrayList<StationInfo> t) {
        con = c;
        total = t;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView station_name = new TextView(con);
        TextView station_number = new TextView(con);
        StationInfo temp = total.get(i);
        station_name.setText(temp.station_name);
        station_number.setText(temp.station_number);
        LinearLayout layout = new LinearLayout(con);
        layout.addView(station_name);
        layout.addView(station_number);
        return layout;
    }

    @Override
    public int getCount() {
        return total.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
}
