package com.example.weathernow.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.weathernow.R;
import com.example.weathernow.model.City;

import java.util.ArrayList;

public class CityAdapter extends BaseAdapter {

    private ArrayList<City> arrayList;
    private Context context;
    private int layout;

    public CityAdapter(ArrayList<City> arrayList, Context context, int layout) {
        this.arrayList = arrayList;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            convertView = inflater.inflate(layout,null);
            viewHolder = new ViewHolder();
            viewHolder.textViewCityName = convertView.findViewById(R.id.textView_CityName);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.textViewCityName.setText(arrayList.get(position).getName());
        return convertView;
    }

    private static class ViewHolder{
        TextView textViewCityName;
    }
}
