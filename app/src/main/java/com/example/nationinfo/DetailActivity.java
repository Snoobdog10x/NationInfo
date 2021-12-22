package com.example.nationinfo;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
        MyAsyncTask asyncTask=new MyAsyncTask();
        asyncTask.execute(URL);
        try {
           CountryCorona countryCorona= asyncTask.get();
           TextView textView=findViewById(R.id.URL);
           ImageView imageView=findViewById(R.id.hinhquocgia);
           textView.setText(countryCorona.getName());
            Picasso.get().load(CountryCorona.getFlag()).into(imageView);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public class MyAsyncTask extends AsyncTask<String, Void, CountryCorona> {
        @Override
        protected CountryCorona doInBackground(String... params) {
            CountryCorona data = null;
            HttpURLConnection httpUrlConnection = null;
            try {
                httpUrlConnection = (HttpURLConnection) new URL(params[0]).openConnection();
                InputStream in = new BufferedInputStream(httpUrlConnection.getInputStream());
                data = readJSONStream(readStream(in));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return data;
        }

        private String readStream(InputStream in) {
            BufferedReader reader = null;
            StringBuilder data = new StringBuilder();
            try {
                reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null) {
                    data.append(line);
                }
            } catch (IOException e) {
                Log.e(TAG, "IOException");
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Log.e(TAG, "IOException");
                    }
                }
            }
            return data.toString();
        }

        private CountryCorona readJSONStream(String in) throws JSONException {
            JSONObject jsonObj = new JSONObject(in);
            String countryname=jsonObj.getString("country");
            JSONObject CountryInfo =jsonObj.getJSONObject("countryInfo");
            String flag=CountryInfo.getString("flag");
            return new CountryCorona(,,,,);
        }

        @Override
        protected void onPostExecute(CountryCorona result) {
        }
    }
}
