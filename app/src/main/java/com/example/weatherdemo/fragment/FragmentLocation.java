package com.example.weatherdemo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.example.weatherdemo.R;
import com.example.weatherdemo.dal.WeatherApiService;
import com.example.weatherdemo.model.Item;

public class FragmentLocation extends Fragment {
    private WeatherApiService weatherApiService;
    private TextView tvCity, tvTemperature, tvStatus, tvHighTemp, tvLowTemp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_location, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvCity = view.findViewById(R.id.city);
        tvTemperature = view.findViewById(R.id.temperature);
        tvStatus = view.findViewById(R.id.status);
        tvHighTemp = view.findViewById(R.id.highTemp);
        tvLowTemp = view.findViewById(R.id.lowTemp);

        weatherApiService = new WeatherApiService(getContext());
        getDefautlWeather();
        getFragmentData();
    }

    private void getFragmentData(){
        getParentFragmentManager().setFragmentResultListener("selectedCity", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                tvCity.setText(result.getString("SelectedCityName"));
                tvTemperature.setText(result.getDouble("SelectedCityTemp")+"°");
                tvHighTemp.setText("H: " +result.getDouble("SelectedCityHighTemp")+"°");
                tvLowTemp.setText("L: " +result.getDouble("SelectedCityLowTemp")+"°");
                tvStatus.setText(result.getString("SelectedCityWeather"));
            }
        });
    }

    private void getDefautlWeather(){
        Item item = weatherApiService.getWeatherDetail(21.0245, 105.84117, new WeatherApiService.VolleyCallback() {
            @Override
            public void onSuccess(Item result) {
                System.out.println("haha "+ result.getWeather());
                tvCity.setText(result.getCity());
                tvTemperature.setText(result.getTemperature()+"°");
                tvStatus.setText(result.getWeather());
                tvHighTemp.setText("H: " +result.getHigh()+"°");
                tvLowTemp.setText("L: " +result.getLow()+"°");
            }
        });
    }
}
