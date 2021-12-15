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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailcountry);
        Intent i = getIntent();
        String URL = "https://corona.lmao.ninja/v2/countries/" + i.getStringExtra("CountryCode");
        TextView textView=findViewById(R.id.countryname);
        textView.setText(URL);
    }

    public class MyAsyncTask extends AsyncTask<String, Void, CountryCorona> {
        @Override
        protected CountryCorona doInBackground(String... params) {
            return null;
        }

        @Override
        protected void onPostExecute(CountryCorona result) {
        }
    }
}
