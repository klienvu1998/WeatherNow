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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weathernow.model.City;
import com.example.weathernow.model.MainInfo;
import com.example.weathernow.model.WeatherResponse;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;


public class WeatherDisplay extends AppCompatActivity {

    private TextView textViewCity,textViewStatus,textViewTemp,textViewDay;
    private ImageView imgIcon;
    private Button btnMenu;
    public int position;
    private WeatherResponse weatherResponse;
    private Bitmap bmp;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_display);
        mapping();
        position = MainActivity.sharedPreferences.getInt("position",-1);
        textViewCity.setText(MainActivity.sharedPreferences.getString("city",""));
        new GetWeatherData().execute("http://api.openweathermap.org/data/2.5/weather?id="+MainActivity.arrCity.get(position).getId()+"&appid=044c41605d89bfffcc59a2a62e878c60");

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

    class GetWeatherData extends AsyncTask<String,Void,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... url) {
            return getWeatherData(url[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            textViewStatus.setText(weatherResponse.getWeather().get(0).getMain());
            double tempConvert = Double.parseDouble(weatherResponse.getMain().getTemp());
            tempConvert = tempConvert - 273.15;
            String day = weatherResponse.getDt();
            long longDay = Long.parseLong(day) * 1000;
            Date date = new Date(longDay);
            textViewDay.setText(SimpleDateFormat.getTimeInstance().format(date));
            textViewTemp.setText(String.valueOf(tempConvert));
            imgIcon.setImageBitmap(bmp);
        }
    }

    private String getWeatherData(String url){
        try {
            URL urlJSON = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) urlJSON.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            Gson gson = new Gson();
            weatherResponse = gson.fromJson(inputStream.readLine(),WeatherResponse.class);
            String img = "http://openweathermap.org/img/wn/"+weatherResponse.getWeather().get(0).getIcon()+"@2x.png";
            URL url1 = new URL(img);
            bmp = BitmapFactory.decodeStream(url1.openConnection().getInputStream());
            urlConnection.disconnect();
            return inputStream.readLine();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
