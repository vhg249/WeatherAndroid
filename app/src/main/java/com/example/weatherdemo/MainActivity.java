package com.example.weatherdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.weatherdemo.adapter.ViewPagerAdapter;
import com.example.weatherdemo.dal.SQLiteHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView navigationView;
    private ViewPager viewPager;
    private SQLiteHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onInit();
        navigationView.setSelectedItemId(R.id.mLocation);

    }

    private void onInit() {
        navigationView = findViewById(R.id.bottom_nav);
        viewPager = findViewById(R.id.viewPager);
        db = new SQLiteHelper(this);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        navigationView.getMenu().findItem(R.id.mSearch).setChecked(true);
                        break;
                    case 1:
                        navigationView.getMenu().findItem(R.id.mLocation).setChecked(true);
                        break;
                    case 2:
                        navigationView.getMenu().findItem(R.id.mList).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.mSearch:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.mLocation:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.mList:
                        viewPager.setCurrentItem(2);
                        break;
                }
                return true;
            }
        });

    }


}