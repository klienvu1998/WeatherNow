package com.example.weathernow.handler;

import android.os.Handler;
import android.os.Message;
import com.example.weathernow.WeatherDisplay;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;

public class GetDataUrlHandler extends Handler {
    private WeakReference<WeatherDisplay> weakReference;

    public GetDataUrlHandler(WeakReference<WeatherDisplay> weakReference) {
        this.weakReference = weakReference;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        WeatherDisplay weatherDisplay = weakReference.get();
        if (weatherDisplay!=null){
            if(msg.what == WeatherDisplay.COMPLETE_GET_DATA ){
                weatherDisplay.textViewStatus.setText(weatherDisplay.weatherResponse.getWeather().get(0).getMain());
                double tempConvert = Double.parseDouble(weatherDisplay.weatherResponse.getMain().getTemp());
                tempConvert = tempConvert - 273.15;
                String day = weatherDisplay.weatherResponse.getDt();
                long longDay = Long.parseLong(day) * 1000;
                Date date = new Date(longDay);
                weatherDisplay.textViewDay.setText(SimpleDateFormat.getTimeInstance().format(date));
                weatherDisplay.textViewTemp.setText(String.valueOf(tempConvert));
                new WeatherDisplay.GetWeatherData(weakReference).execute(weatherDisplay.weatherResponse.getWeather().get(0).getIcon());
            }
        }
    }
}
