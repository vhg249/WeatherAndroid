package com.example.weatherdemo.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.weatherdemo.fragment.FragmentList;
import com.example.weatherdemo.fragment.FragmentLocation;
import com.example.weatherdemo.fragment.FragmentSearch;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new FragmentSearch();
            case 1: return new FragmentLocation();
            case 2: return new FragmentList();
            default: return new FragmentLocation();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
