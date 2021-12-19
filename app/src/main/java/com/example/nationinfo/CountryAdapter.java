package com.example.nationinfo;


import android.app.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.ImageView;


import androidx.annotation.NonNull;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class CountryAdapter extends ArrayAdapter<Country> {
    private List<Country> countries;
    private Activity context;
    private int loadedpotition=0;
    public CountryAdapter(@NonNull Activity context, List<Country> countries) {
        super(context, R.layout.mainlist, countries);
        this.countries = countries;
        this.context = context;
    }
    @Override
    public View getView(int position, View rowView, ViewGroup parent) {
        if (rowView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            rowView = layoutInflater.inflate(R.layout.mainlist, null, false);
        }
        return rowView;
    }

    public void updateload() {
        this.loadedpotition = loadedpotition;
    }
}
