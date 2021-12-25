package com.example.nationinfo;

import static android.content.ContentValues.TAG;

import static java.lang.Thread.sleep;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class MainActivity extends AppCompatActivity {
    JSONArray data;
    List<Country> countries;
    private ProgressBar loadingPB;
    private RecyclerView rvCountries;
    private CountryAdapter adapter;
    private int index = 0;
    private int count =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        background background = new background();
        background.execute();
        try {
            loadingPB = findViewById(R.id.idPBLoading);
            rvCountries = (RecyclerView) findViewById(R.id.rvCountry);
            countries = new ArrayList<>();
            data = background.get();
            adapter = new CountryAdapter(countries, MainActivity.this);
            rvCountries.setAdapter(adapter);
            rvCountries.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            rvCountries.setItemAnimator(new SlideInUpAnimator());
            getCountry();
            rvCountries.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (!recyclerView.canScrollVertically(1)) {
                        getCountry();

                    }
                }
            });
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void getCountry() {

        for (; index < data.length(); index++) {
            if (count==5) {
                break;
            }
            JSONObject country = null;
            try {
                country = data.getJSONObject(index);
                Country c = new Country(country.getString("countryName"), country.getInt("population")
                        , country.getDouble("areaInSqKm"), country.getString("countryCode"));
                countries.add(c);
                adapter.notifyDataSetChanged();
                count++;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        count=0;
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public class background extends AsyncTask<Void, Void, JSONArray> {
        private static final String URL = "http://api.geonames.org/countryInfoJSON?username=duythanh1565";

        @Override
        protected JSONArray doInBackground(Void... params) {
            //String data = null;
            JSONArray data = null;
            HttpURLConnection httpUrlConnection = null;

            try {
                // 1. Get connection. 2. Prepare request (URI)
                httpUrlConnection = (HttpURLConnection) new URL(URL).openConnection();

                // 3. This app does not use a request body
                // 4. Read the response
                InputStream in = new BufferedInputStream(httpUrlConnection.getInputStream());

                data = readJSONStream(readStream(in));

            } catch (MalformedURLException exception) {
                Log.e(TAG, "MalformedURLException");
            } catch (IOException | JSONException exception) {
                Log.e(TAG, "IOException");
            } finally {
                if (null != httpUrlConnection) {
                    // 5. Disconnect
                    httpUrlConnection.disconnect();
                }
            }
            return data;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(JSONArray countries) {
            super.onPostExecute(countries);
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

        private JSONArray readJSONStream(String in) throws JSONException {
            JSONObject jsonObj = new JSONObject(in);
            JSONArray data = jsonObj.getJSONArray("geonames");
            return data;
        }
    }
}