package com.example.weathernow;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.weathernow.adapter.CityAdapter;
import com.example.weathernow.model.City;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    public CityAdapter cityAdapter;
    public static ArrayList<City> arrCity;
    public static SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
//        if(!sharedPreferences.getString("id","").equals("")){
//            Intent intent = new Intent(MainActivity.this,WeatherDisplay.class);
//            startActivity(intent);
//        }
        mapping();
        createListCity();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        eventListViewJSON();
        eventListViewGSON();
    }

    private void eventListViewGSON() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,WeatherDisplay.class);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });
    }

//    private void eventListViewJSON() {
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
//                String url = "http://api.openweathermap.org/data/2.5/weather?id="+arrCity.get(position).getId()+"&appid=044c41605d89bfffcc59a2a62e878c60";
//                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//                StringRequest stringRequest = new StringRequest(StringRequest.Method.GET, url, new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            SharedPreferences.Editor editor = sharedPreferences.edit();
//                            JSONObject jsonObject = new JSONObject(response);
//                            String day = jsonObject.getString("dt");
//                            JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
//                            String temp = jsonObjectMain.getString("temp");
//                            JSONArray jsonArraytWeather = jsonObject.getJSONArray("weather");
//                            JSONObject jsonObjectStausWeather = jsonArraytWeather.getJSONObject(0);
//                            String status = jsonObjectStausWeather.getString("main");
//                            arrCity.get(position).setStatus(status);
//                            arrCity.get(position).setTemp(temp);
//                            editor.putString("day",day);
//                            editor.putString("id",arrCity.get(position).getId());
//                            editor.putString("city",arrCity.get(position).getName());
//                            editor.putString("temp",temp);
//                            editor.putString("status",status);
//                            editor.apply();
//                            Intent intent = new Intent(MainActivity.this,WeatherDisplay.class);
//                            startActivity(intent);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                });
//                requestQueue.add(stringRequest);
//            }
//        });
//    }

    private void createListCity() {
        arrCity = new ArrayList<>();
        arrCity.add(new City("1566083","Thanh pho Ho Chi Minh"));
        arrCity.add(new City("6252001","United States"));
        arrCity.add(new City("6254975","Blairgowrie"));
        arrCity.add(new City("6267363","Puerto Motilones"));
        arrCity.add(new City("6268219","Kobrino"));
        cityAdapter = new CityAdapter(arrCity,getApplicationContext(),R.layout.line_city);
        listView.setAdapter(cityAdapter);
    }

    private void mapping() {
        listView = findViewById(R.id.listView_City);
    }
}
