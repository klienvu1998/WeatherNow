package com.example.weathernow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.weathernow.adapter.CityAdapter;
import com.example.weathernow.model.City;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    public CityAdapter cityAdapter;
    public static ArrayList<City> arrCity;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        if(!sharedPreferences.getString("id","").equals("")){
            Intent intent = new Intent(MainActivity.this,WeatherDisplay.class);
            startActivity(intent);
        }
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
        eventListViewGSON();
    }

    private void eventListViewGSON() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,WeatherDisplay.class);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("id",arrCity.get(position).getId());
                editor.putString("city",arrCity.get(position).getName());
                editor.apply();
                startActivity(intent);
            }
        });
    }


    private void createListCity() {
        arrCity = new ArrayList<>();
        BufferedReader reader;
        String jsonCity = "";
        try {
            reader = new BufferedReader(new InputStreamReader(getAssets().open("jsonCity.txt")));
            jsonCity = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        arrCity = gson.fromJson(jsonCity, new TypeToken<ArrayList<City>>(){}.getType());
//        arrCity.add(new City("1566083","Thanh pho Ho Chi Minh"));
//        arrCity.add(new City("6252001","United States"));
//        arrCity.add(new City("6254975","Blairgowrie"));
//        arrCity.add(new City("6267363","Puerto Motilones"));
//        arrCity.add(new City("6268219","Kobrino"));
//        String jsonArrayCity = new Gson().toJson(arrCity);
//        Log.d("MainActivity",jsonArrayCity);
//            File path = getApplicationContext().getFilesDir();
//            File file = new File(path,"jsonCity.txt");
//        FileOutputStream stream = null;
//        try {
//            stream = new FileOutputStream(file);
//            stream.write(jsonArrayCity.getBytes());
//            stream.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        cityAdapter = new CityAdapter(arrCity,getApplicationContext(),R.layout.line_city);
        listView.setAdapter(cityAdapter);
    }

    private void mapping() {
        listView = findViewById(R.id.listView_City);
    }
}
