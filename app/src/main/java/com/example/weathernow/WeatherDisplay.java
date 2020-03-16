package com.example.weathernow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherDisplay extends AppCompatActivity {

    private TextView textViewCity,textViewStatus,textViewTemp,textViewDay;
    private Button btnMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_display);
        mapping();
        double tempConvert = Double.parseDouble(MainActivity.sharedPreferences.getString("temp",""));
        tempConvert = tempConvert - 273.15;
        String day = MainActivity.sharedPreferences.getString("day","");
        long longDay = Long.parseLong(day) * 1000;
        Date date = new Date(longDay);
        String city = "City: " + MainActivity.sharedPreferences.getString("city","");
        String temp = "Temp: " + tempConvert;
        String status = "Status: " + MainActivity.sharedPreferences.getString("status","");
        textViewCity.setText(city);
        textViewTemp.setText(temp);
        textViewStatus.setText(status);
        textViewDay.setText(SimpleDateFormat.getDateInstance().format(date));
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
                editor.putString("id","");
                editor.putString("city","");
                editor.putString("temp","");
                editor.putString("status","");
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
    }
}
