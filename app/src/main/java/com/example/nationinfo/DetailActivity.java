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
        setContentView(R.layout.detailcountry_fix);
        Intent i = getIntent();
        String URL = "https://corona.lmao.ninja/v2/countries/" + i.getStringExtra("CountryCode");
        MyAsyncTask asyncTask=new MyAsyncTask();
        asyncTask.execute(URL);
        //đoạn này được dùng để set từng thông tin vào text view tương ứng
        //tạo một đối tượng tên textView đối đượng này được xác định bằng findViewById
        //VD Bảo tạo một textView có id hinhquocgia
        //và ta muốn set hình quốc kì vào text view này
        //nên ta sẽ tạo một đối tượng ImageView có tên imageView để set cho textview này
        //ImageView imageView=findViewById(R.id.hinhquocgia); hinhquocgia ở đây chính là id
        //của textview
        //Picasso.get().load(CountryCorona.getFlag()).into(imageView); dùng để load ảnh
        try {
           CountryCorona countryCorona= asyncTask.get();
           //TextView
           TextView textView_name_of_country=findViewById(R.id.countryname);//Điền id của textView name vào đây
           TextView textView_deaths = findViewById(R.id.deaths);//điền ID của textview ca chết vào đây
           TextView textView_cases = findViewById(R.id.cases);//điền ID ca bệnh vào đây
           TextView textView_recovered = findViewById(R.id.recovered);//điền ID số ca hồi phục vào đây
            //ImageView
           ImageView imageView_Flag_of_country=findViewById(R.id.flagimg);//điền id của text view flag vào đây
           //Set vàp TextView
           textView_name_of_country.setText(countryCorona.getName_of_country());
           textView_deaths.setText(countryCorona.getNumber_of_death_of_country());
           textView_cases.setText(countryCorona.getNumber_of_infected_of_country());
           textView_recovered.setText(countryCorona.getNumber_of_recovered_of_country());
           //Set vào image View
           Picasso.get().load(countryCorona.getFlag_of_country()).into(imageView_Flag_of_country);
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
        //đây là hàm CountryCorona, nó hoạt động như sau:
        //nó lấy từng data trong file json để gán cho từng chuỗi.
        //những chuỗi này bao gồm: tên quốc gia, cờ, số ca nhiễm, +2 không nhớ
        //ví dụ JSONObject jsonObj = new JSONObject(in); tạo một đối tượng json
        //String countryname=jsonObj.getString("country"); lấy tên quốc gia
        //return new CountryCorona(,,,,);
        //trả về một đối tượng quốc gia gồm 5 chuỗi tên quốc gia, nhiễm bệnh, tử vong,
        //bình phục, quốc kì
        private CountryCorona readJSONStream(String in) throws JSONException {
            JSONObject jsonObj = new JSONObject(in);
            String Name = jsonObj.getString("country");
            JSONObject CountryInfo = jsonObj.getJSONObject("countryInfo");
            String flag = CountryInfo.getString("flag");
            int number_of_death = jsonObj.getInt("deaths");
            int number_of_recovered = jsonObj.getInt("recovered");
            int number_of_infected = jsonObj.getInt("cases");
            return new CountryCorona(Name,flag,number_of_death,number_of_recovered,number_of_infected);
        }

        @Override
        protected void onPostExecute(CountryCorona result) {
        }
    }
}
