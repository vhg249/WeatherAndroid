package com.example.weatherdemo.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.weatherdemo.R;
import com.example.weatherdemo.adapter.RecycleViewAdapter;
import com.example.weatherdemo.dal.SQLiteHelper;
import com.example.weatherdemo.dal.WeatherApiService;
import com.example.weatherdemo.model.City;
import com.example.weatherdemo.model.Item;

import java.util.ArrayList;
import java.util.List;

public class FragmentList extends Fragment implements RecycleViewAdapter.ItemListener{
    private RecycleViewAdapter adapter;
    private RecyclerView recyclerView;
    private SQLiteHelper db;
    private WeatherApiService weatherApiService;
    List<Item> listWeather = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        db = new SQLiteHelper(context);
        weatherApiService = new WeatherApiService(context);
        super.onAttach(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycleView);

        adapter = new RecycleViewAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
        getWeatherList();
        adapter.setItemListener(this);
    }

    public List<City> getCities() {
        try {
            List<City> list = db.getAll();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void getWeatherList() {
        try {
            List<City> cities = getCities();
            List<Item> weatherList = new ArrayList<>();

            for (int i = 0; i < cities.size(); i++) {
                City c = cities.get(i);
                Log.d("list city sss", c.getName());
                weatherApiService.getWeatherDetail(c.getLat(), c.getLon(), result -> {
                        listWeather.add(result);
                        result.setId(c.getId());
                        adapter.setList(listWeather);
                        Log.d("ggg", listWeather.size() + "" + result.getImage());
                    }
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onItemClick(View view, int position) {
        Item item = listWeather.get(position);
        Bundle result = new Bundle();
        Log.d("zzz", item.toString());
        result.putString("SelectedCityName", item.getCity());
        result.putString("SelectedCityWeather", item.getWeather());
        result.putDouble("SelectedCityTemp", item.getTemperature());
        result.putDouble("SelectedCityHighTemp", item.getHigh());
        result.putDouble("SelectedCityLowTemp", item.getLow());
        getParentFragmentManager().setFragmentResult("selectedCity", result);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

        ViewPager viewPager = getActivity().findViewById(R.id.viewPager);
        viewPager.setCurrentItem(1);
    }
}
