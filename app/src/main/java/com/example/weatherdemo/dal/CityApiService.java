package com.example.weatherdemo.dal;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherdemo.model.City;
import com.example.weatherdemo.model.Item;
import com.google.gson.Gson;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;

public class CityApiService {
    private Gson gson = new Gson();
    private Context context;

    public CityApiService(Context context) {
        this.context = context;
    }

    public CityApiService() {
    }


    public List<City> getCities(String name, final CityApiService.VolleyCallback callback) {
        List<City> list = new ArrayList<>();
        String URL = "http://api.openweathermap.org/geo/1.0/direct?q=" + name + "&lang=vi&limit=100&appid=b48cdc34e15483b4288dee21260e8b8c";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("city response", response);
                try {
                    JSONArray jsonResponse = new JSONArray(response);
                    for (int i=0;i<jsonResponse.length();i++){
                        JSONObject jsonObjectCity = jsonResponse.getJSONObject(i);
                        Log.d("country data: ",  jsonObjectCity.getString("name"));
                        String name = jsonObjectCity.getString("name");
                        String country = jsonObjectCity.getString("country");
                        double lat = jsonObjectCity.getDouble("lat");
                        double lon = jsonObjectCity.getDouble("lon");
                        list.add(new City(name, country, lat, lon));
                    }
                    callback.onSuccess(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.err.println("city error" + error);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        requestQueue.add(stringRequest);
        return list;
    }

    public interface VolleyCallback {
        void onSuccess(List<City> result);
    }

    public void searchCity(String key) {
        (new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String where = URLEncoder.encode("{" +
                            "    \"name\": {" +
                            "        \"$regex\": \"" + key + "\"" +
                            "    }" +
                            "}", "utf-8");
                    URL url = new URL("https://parseapi.back4app.com/classes/City?keys=name,country,countryCode,cityId&where=" + where);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestProperty("X-Parse-Application-Id", "kysTM8sxjGAzL9kRFu5SbI3zRSZgRvzj6Feb9CaI"); // This is the fake app's application id
                    urlConnection.setRequestProperty("X-Parse-Master-Key", "saeclF3NoaHo0ETX9uN88H85xT2KAY4QCHNp4n1F"); // This is the fake app's readonly master key
                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        StringBuilder stringBuilder = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            stringBuilder.append(line);
                        }
                        JSONObject data = new JSONObject(stringBuilder.toString()); // Here you have the data that you need
                        City res = gson.fromJson(String.valueOf(data), City.class);
                        System.out.println("hello" + res);
                        Log.d("MainActivity", data.toString(2));
                    } finally {
                        urlConnection.disconnect();
                    }
                } catch (Exception e) {
                    Log.e("MainActivity", e.toString());
                }
            }
        })).start();
    }
}
