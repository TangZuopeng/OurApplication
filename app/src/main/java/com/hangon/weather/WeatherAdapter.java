package com.hangon.weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fd.ourapplication.R;

import java.util.List;

/**
 * Created by Chuan on 2016/5/5.
 */
public class WeatherAdapter extends ArrayAdapter<Weather> {
    private int resourceId;

    public WeatherAdapter(Context context, int textViewResourceId, List<Weather> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Weather iweather = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        TextView date = (TextView) view.findViewById(R.id.weather_forecast_date);
        TextView weather = (TextView) view.findViewById(R.id.weather_forecast_weather);
        TextView temperature = (TextView) view.findViewById(R.id.weather_forecast_temperature);
        ImageView icon = (ImageView) view.findViewById(R.id.weather_forecast_img);
        String weatherStr = iweather.getWeather();
        if (weatherStr.contains("晴")){
            icon.setImageResource(R.drawable.tq_11);
        }else if (weatherStr.contains("多云")){
            icon.setImageResource(R.drawable.tq_15);
        }else if (weatherStr.contains("阵雨")){
            icon.setImageResource(R.drawable.tq_19);
        }else if (weatherStr.contains("中雨")){
            icon.setImageResource(R.drawable.tq_23);
        }else if (weatherStr.contains("大雨")){
            icon.setImageResource(R.drawable.tq_25);
        }else if (weatherStr.contains("雷")){
            icon.setImageResource(R.drawable.tq_28);
        }else if (weatherStr.contains("阴")){
            icon.setImageResource(R.drawable.tq_30);
        }else {
            icon.setImageResource(R.drawable.tq_15);
        }
        String dateStr = iweather.getDate();
        if (dateStr.contains("一")){
            dateStr = "周一";
        }else if (dateStr.contains("二")){
            dateStr = "周二";
        }else if (dateStr.contains("三")){
            dateStr = "周三";
        }else if (dateStr.contains("四")){
            dateStr = "周四";
        }else if (dateStr.contains("五")){
            dateStr = "周五";
        }else if (dateStr.contains("六")){
            dateStr = "周六";
        }else if (dateStr.contains("日")){
            dateStr = "周日";
        }
        date.setText(dateStr);
        weather.setText(weatherStr);
        temperature.setText(iweather.getTemperature());
        return view;
    }
}
