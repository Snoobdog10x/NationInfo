package com.example.nationinfo;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.List;

public class MainListview extends ArrayAdapter<Country> {
    private Activity context;
    private List<Country> data;
    public MainListview(Activity context,List<Country> data) {
        super(context,R.layout.mainlist,data);
        this.context=context;
        this.data=data;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.mainlist, null,true);
        TextView titleText = (TextView) rowView.findViewById(R.id.listtitle);
        TextView countrycode = (TextView) rowView.findViewById(R.id.countrycode);
        Country c=data.get(position);
        titleText.setText(context.getString(R.string.countryname)+" "+c.getName());
        countrycode.setText(context.getString(R.string.countrycode)+" "+c.getCountryCode());
        return rowView;
    }
}
