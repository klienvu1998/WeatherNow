package com.example.weathernow;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.weathernow.handler.GetDataUrlHandler;
import com.example.weathernow.model.WeatherResponse;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class WeatherDisplay extends AppCompatActivity {
    public final static int COMPLETE_GET_DATA = 100;

    public TextView textViewCity,textViewStatus,textViewTemp,textViewDay;
    private ImageView imgIcon;
    private Button btnMenu;
    public int position;
    public WeatherResponse weatherResponse;

    private GetDataUrlHandler mHanler;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_display);
        mapping();
        position = MainActivity.sharedPreferences.getInt("position",-1);
        textViewCity.setText(MainActivity.sharedPreferences.getString("city",""));
        mHanler = new GetDataUrlHandler(new WeakReference<>(this));
        getDataFromUrl();
    }

    private void getDataFromUrl() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                URL urlJSON ;
                String result;
                HttpURLConnection urlConnection;
                try {
                    urlJSON = new URL("https://api.openweathermap.org/data/2.5/weather?id="+ MainActivity.arrCity.get(position).getId()+"&appid=044c41605d89bfffcc59a2a62e878c60");
                    urlConnection = (HttpURLConnection) urlJSON.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();
                    BufferedReader inputStream = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    urlConnection.disconnect();
                    result = inputStream.readLine();
                    Gson gson = new Gson();
                    weatherResponse = gson.fromJson(result,WeatherResponse.class);
                    if(weatherResponse.getWeather() != null){
                        Message message = new Message();
                        message.what = COMPLETE_GET_DATA;
                        mHanler.sendMessage(message);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        eventMenuButton();
    }

    private void eventMenuButton() {
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = MainActivity.sharedPreferences.edit();
                editor.putString("screen","0");
                editor.apply();
                Intent intent = new Intent(WeatherDisplay.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void mapping() {
        textViewCity = findViewById(R.id.textView_Display_CityName);
        textViewStatus = findViewById(R.id.textView_Display_Status);
        textViewTemp = findViewById(R.id.textView_Display_Temp);
        btnMenu = findViewById(R.id.btn_Display_menu);
        textViewDay = findViewById(R.id.textView_Display_Day);
        imgIcon = findViewById(R.id.imgView_Display_icon);
    }

    public static class GetWeatherData extends AsyncTask<String,Void,Bitmap>{

        private WeakReference<WeatherDisplay> mWeakActivity;

        public GetWeatherData(WeakReference<WeatherDisplay> mWeakActivity) {
            this.mWeakActivity = mWeakActivity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(String... url) {
            Bitmap bmp = null;
            try {
                String img = "https://openweathermap.org/img/wn/"+url[0]+"@2x.png";
                URL url1 = new URL(img);
                InputStream in = url1.openStream();
                bmp = BitmapFactory.decodeStream(in);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bmp;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            WeatherDisplay weatherDisplay = mWeakActivity.get();
            if(weatherDisplay != null)
                weatherDisplay.imgIcon.setImageBitmap(result);
        }
    }
}
