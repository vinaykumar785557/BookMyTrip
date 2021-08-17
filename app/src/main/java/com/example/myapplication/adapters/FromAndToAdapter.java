package com.example.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.models.FromAndToLocations;

import java.util.ArrayList;

public class FromAndToAdapter extends BaseAdapter {
    Context context;
    ArrayList<FromAndToLocations> allLocations;
    LayoutInflater inflter;

    public FromAndToAdapter(Context applicationContext, ArrayList<FromAndToLocations> allLocations) {
        this.context = applicationContext;
        this.allLocations = allLocations;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return allLocations.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.from_and_to_location_item, null);
        TextView names = (TextView) view.findViewById(R.id.txt_place);
        names.setText(allLocations.get(i).getLocationName());
        return view;
    }
}
