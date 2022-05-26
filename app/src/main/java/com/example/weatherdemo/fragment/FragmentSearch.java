package com.example.weatherdemo.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.weatherdemo.R;
import com.example.weatherdemo.adapter.RecycleViewCityAdapter;
import com.example.weatherdemo.dal.CityApiService;
import com.example.weatherdemo.dal.SQLiteHelper;
import com.example.weatherdemo.model.City;

import java.util.ArrayList;
import java.util.List;

public class FragmentSearch extends Fragment implements RecycleViewCityAdapter.ItemListener, SearchView.OnQueryTextListener{
    private RecyclerView recyclerView;
    private SearchView searchCity;
    private SQLiteHelper db;
    private RecycleViewCityAdapter adapter;
    private CityApiService cityApiService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cityApiService = new CityApiService(getContext());

        recyclerView = view.findViewById(R.id.cityRecycleView);
        searchCity = view.findViewById(R.id.searchCity);
        db = new SQLiteHelper(getContext());
        adapter = new RecycleViewCityAdapter();

        List<City> list = new ArrayList<>();
        adapter.setList(list);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        searchCity.setOnQueryTextListener(this);

        adapter.setItemListener(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        List<City> cityList = adapter.getList();
        City c = cityList.get(position);
        Log.d("add", c.getName());
        db.addItem(c);
        searchCity.setQuery("", false);
        searchCity.clearFocus();
        adapter.setList(new ArrayList<>());
        Toast.makeText(this.getContext(), "City Added", Toast.LENGTH_SHORT).show();
        ViewPager viewPager = getActivity().findViewById(R.id.viewPager);
        viewPager.setCurrentItem(2);


    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        cityApiService.getCities(s, new CityApiService.VolleyCallback() {
            @Override
            public void onSuccess(List<City> result) {
                adapter.setList(result);
            }
        });
        return false;
    }
}
