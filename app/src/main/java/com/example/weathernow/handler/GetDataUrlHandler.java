package com.example.weathernow.handler;

import android.icu.text.DecimalFormat;
import android.icu.text.NumberFormat;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import com.example.weathernow.WeatherDisplay;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

public class GetDataUrlHandler extends Handler {
    private WeakReference<WeatherDisplay> weakReference;

    public GetDataUrlHandler(WeatherDisplay weakReference) {
        this.weakReference = new WeakReference<>(weakReference);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        if (weakReference.get()!=null){
            if(msg.what == WeatherDisplay.COMPLETE_GET_DATA ){
                weakReference.get().textViewStatus.setText(weakReference.get().weatherResponse.getWeather().get(0).getMain());
                double tempConvert = Double.parseDouble(weakReference.get().weatherResponse.getMain().getTemp());
                tempConvert = tempConvert - 273.15;
                String day = weakReference.get().weatherResponse.getDt();
                long longDay = Long.parseLong(day) * 1000;
                Date date = new Date(longDay);
                weakReference.get().textViewDay.setText(SimpleDateFormat.getTimeInstance().format(date));
                NumberFormat numberFormat = new DecimalFormat("#0.00");
                String stringTemp = numberFormat.format(tempConvert)+" oC";
                weakReference.get().textViewTemp.setText(stringTemp);
                new WeatherDisplay.GetWeatherData(weakReference.get()).execute(weakReference.get().weatherResponse.getWeather().get(0).getIcon());
            }
        }
    }
}
