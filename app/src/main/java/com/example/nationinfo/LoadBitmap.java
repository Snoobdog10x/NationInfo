package com.example.nationinfo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class LoadBitmap extends AsyncTask<Void, Void, Bitmap> {

    private CountryAdapter.ViewHolder holder;
    private Country c;
    private Context context;
    public LoadBitmap(CountryAdapter.ViewHolder holder,Country c,Context context) {
        this.holder=holder;
        this.c=c;
        this.context=context;
    }

    @Override
    protected Bitmap doInBackground(Void... para) {
        Bitmap b = null;
        try {
            b = BitmapFactory.decodeStream((InputStream) new URL(c.getFlag()).getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return b;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        holder.imageView.setImageBitmap(bitmap);
        holder.countryname.setText(context.getString(R.string.countryname) + " " + c.getName());
        holder.countrycode.setText(context.getString(R.string.countrycode) + " " + c.getCountryCode());
        holder.population.setText(context.getString(R.string.population) + " " + c.getPopulation() + context.getString(R.string.popunit));
        holder.areaInSqKm.setText(context.getString(R.string.areaInSqKm) + " " + c.getAreaInSqKm() + context.getString(R.string.areaunit));
    }
}
