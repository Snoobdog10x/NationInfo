package com.example.nationinfo;


import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {
    private List<Country> countries;
    private Activity context;

    // Pass in the contact array into the constructor
    public CountryAdapter(List<Country> countries, Activity context) {
        this.countries = countries;
        this.context = context;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public ImageView imageView;
        public TextView countryname;
        public TextView countrycode;
        public TextView population;
        public TextView areaInSqKm;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View rowView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(rowView);
            imageView = (ImageView) rowView.findViewById(R.id.ListImg);
            countryname = (TextView) rowView.findViewById(R.id.countryname);
            countrycode = (TextView) rowView.findViewById(R.id.countrycode);
            population = (TextView) rowView.findViewById(R.id.population);
            areaInSqKm = (TextView) rowView.findViewById(R.id.areaInSqKm);
            rowView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAbsoluteAdapterPosition(); // gets item position
            if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it\\
                System.out.println("hiih");
                sendMessage(countries.get(position).getCountryCode());
            }
        }
    }

    public void sendMessage(String code) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("CountryCode", code);
        context.startActivity(intent);
    }

    @Override
    public CountryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.mainlist, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(CountryAdapter.ViewHolder holder, int position) {
       // System.out.println(position);
        new LoadBitmap(holder, countries.get(position), context).execute();
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }
}
