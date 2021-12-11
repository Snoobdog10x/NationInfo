package com.example.nationinfo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class DetailActivity extends AppCompatActivity {
    private ImageView flagimg;
    private TextView countryname;
    private TextView population;
    private TextView areaInSqKm;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        Country country = (Country) i.getSerializableExtra("country");
        if(country!=null) {
            MyAsyncTask task = new MyAsyncTask();
            try {
                Bitmap flag = task.execute(country.getFlag()).get();
                setContentView(R.layout.detailcountry);
                flagimg = findViewById(R.id.flagimg);
                countryname = findViewById(R.id.countryname);
                population = findViewById(R.id.population);
                areaInSqKm = findViewById(R.id.areaInSqKm);
                flagimg.setImageBitmap(flag);
                countryname.setText(country.getName());
                population.setText(country.getPopulation()+"");
                areaInSqKm.setText(country.getAreaInSqKm()+"");
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public class MyAsyncTask extends AsyncTask<String, Void, Bitmap> {
        @Override protected Bitmap doInBackground(String... params) {
            Bitmap bitmap=null;
            for (String scr:params) {
                bitmap=getFlagBitmap(scr);
            }
            return bitmap;
        }
        @Override protected void onPostExecute(Bitmap result) {
        }
        public Bitmap getFlagBitmap(String src) {
            try {
                StrictMode.ThreadPolicy gfgPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(gfgPolicy);
                URL url = new URL(src);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (IOException e) {
                return null;
            }
        }
    }


}
