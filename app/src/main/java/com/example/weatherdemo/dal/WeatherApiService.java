package com.example.weatherdemo.dal;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherdemo.R;
import com.example.weatherdemo.model.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherApiService {
    private String url = "https://api.openweathermap.org/data/3.0/onecall";
    private String apiKey = "b48cdc34e15483b4288dee21260e8b8c";
    private Context context;
    private int[] imgs = {R.drawable.sun_rain, R.drawable.moon_wind, R.drawable.night_rain, R.drawable.tornado, R.drawable.sunny};

    public WeatherApiService(Context context) {
        this.context = context;
    }

    public WeatherApiService() {
    }

    private int getImgWeather(String weather){
        switch (weather){
            case "Thunderstorm":
                return imgs[3];
            case "Clear":
                return imgs[4];
            case "Rain":
                return imgs[0];
            case "Clouds":
                return imgs[4];
            default:
                return imgs[4];
        }
    }

    public Item getWeatherDetail(double lat, double lon, final VolleyCallback callback) {
        Item item = new Item();
        String tempUrl = "https://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"&units=metric&appid=b48cdc34e15483b4288dee21260e8b8c";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, tempUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("weather response", response);
                try {
                    JSONObject jsonResponse = new JSONObject(response);

                    JSONArray jsonArrayWeather = jsonResponse.getJSONArray("weather");
                    JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                    String desc = jsonObjectWeather.getString("description");
                    String nameCity = jsonResponse.getString("name");
                    String status = jsonObjectWeather.getString("main");

                    item.setWeather(desc);
                    item.setCity(nameCity);
                    item.setImage(getImgWeather(status));

                    JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                    double temp = jsonObjectMain.getDouble("temp");
                    double highTemp = jsonObjectMain.getDouble("temp_max");
                    double lowTemp = jsonObjectMain.getDouble("temp_min");
                    Log.d("weather response high: ", desc + " " + temp + " " + highTemp + " " + lowTemp);
                    item.setTemperature(temp);
                    item.setHigh(highTemp);
                    item.setLow(lowTemp);
                    callback.onSuccess(item);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.err.println("weather error" + error);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        requestQueue.add(stringRequest);
        return item;
    }

    public interface VolleyCallback {
        void onSuccess(Item result);
    }
}

