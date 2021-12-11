package com.example.nationinfo;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.PrecomputedText;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<Country> list= null;
        try {
            list = new background().execute().get();
            setContentView(R.layout.activity_main);
            TextView textView = findViewById(R.id.maintext);
            MainListview Adapter = new MainListview(this, list);
            ListView listView = (ListView) findViewById(R.id.list);
            listView.setAdapter(Adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Country country = (Country) parent.getItemAtPosition(position);
                    sendMessage(country);
                }
            });
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    private void sendMessage(Country country) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("country", country);
        startActivity(intent);
    }
    public class background extends AsyncTask<Void, Void, List<Country>> {
        private static final String URL = "http://api.geonames.org/countryInfoJSON?username=duythanh1565";

        @Override
        protected List<Country> doInBackground(Void... params) {
            //String data = null;
            List<Country> data = null;
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
        protected void onPostExecute(List<Country> countries) {
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

        private List<Country> readJSONStream(String in) throws JSONException {
            List<Country> data = new ArrayList<>();

            JSONObject jsonObj = new JSONObject(in);
            JSONArray countries = jsonObj.getJSONArray("geonames");
            for (int i = 0; i < countries.length(); i++) {
                JSONObject country = countries.getJSONObject(i);
                String countryName = country.getString("countryName");
                int population = country.getInt("population");
                double areaInSqKm = country.getDouble("areaInSqKm");
                String countryCode = country.getString("countryCode");
                Country c = new Country(countryName, population, areaInSqKm, countryCode);
                data.add(c);
            }
            return data;
        }
    }
}