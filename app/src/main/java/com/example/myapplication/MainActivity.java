package com.example.myapplication;

import android.os.Bundle;

import com.example.myapplication.Adapter.ViewPagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    ViewPagerAdapter pagerAdapter;
    @BindView(R.id.fab)
    FloatingActionButton floatingActionButton;
    @BindView(R.id.tablayout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        tabLayout.addTab(tabLayout.newTab().setText("Products"));
        tabLayout.addTab(tabLayout.newTab().setText("Providers"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        pagerAdapter = new ViewPagerAdapter(this,getSupportFragmentManager(),getLifecycle(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
                super.onPageSelected(position);
            }
        });


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
    @OnClick(R.id.fab)
    public void Click(){
        int pos = tabLayout.getSelectedTabPosition();
        if (pos == 0){
            //produtc
        }
        else{

        }
    }

}