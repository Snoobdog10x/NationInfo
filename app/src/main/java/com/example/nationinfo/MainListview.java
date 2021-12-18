package com.example.nationinfo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
        ImageView im=(ImageView)rowView.findViewById(R.id.ListImg);
        TextView titleText = (TextView) rowView.findViewById(R.id.listtitle);
        TextView countrycode = (TextView) rowView.findViewById(R.id.countrycode);
        Country c=data.get(position);
        new DownloadImageTask(im,titleText,countrycode,c).execute();
        return rowView;
    }
    private class DownloadImageTask extends AsyncTask<Void, Void, Bitmap> {
        ImageView bmImage;
        TextView countrycode;
        TextView titleText;
        Country c;
        public DownloadImageTask(ImageView bmImage,TextView titleText,TextView countrycode,Country c) {
            this.bmImage = bmImage;
            this.titleText=titleText;
            this.countrycode=countrycode;
            this.c=c;
        }

        protected Bitmap doInBackground(Void... urls) {
            String urldisplay = c.getFlag();
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
            titleText.setText(context.getString(R.string.countryname)+" "+c.getName());
            countrycode.setText(context.getString(R.string.countrycode)+" "+c.getCountryCode());
        }
    }
}
