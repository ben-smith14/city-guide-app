package com.example.android.cityguide;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Use a ViewPager to allow horizontal swiping between fragments. Attach an instance of
        // our custom subclass of FragmentPageAdapter to this ViewPager
        ViewPager tabViewPager = findViewById(R.id.tab_viewpager);
        CategoryAdapter locationCategoryAdapter = new CategoryAdapter(getSupportFragmentManager(), this);
        tabViewPager.setAdapter(locationCategoryAdapter);

        // Give a scrollable TabLayout to the ViewPager to display the list of tabs below the
        // action bar
        TabLayout scrollableTabLayout = findViewById(R.id.sliding_tabs);
        scrollableTabLayout.setupWithViewPager(tabViewPager);
    }
}
