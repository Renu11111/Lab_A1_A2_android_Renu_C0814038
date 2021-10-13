package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.Adapter.ViewPagerAdapter;
import com.example.myapplication.Database.DatabaseInterface.Database;
import com.example.myapplication.Pojo.Productbean;
import com.example.myapplication.Pojo.Providerbean;
import com.example.myapplication.ui.main.ProductAddUpdate;
import com.example.myapplication.ui.main.ProviderAddUpdate;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

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
        if (Database.getInstance(getApplicationContext()).getProductDao().getAllProducts().size() == 0 && Database.getInstance(getApplicationContext()).getProviderDao().getAllProviders().size() == 0){
            addDummyData();
        }
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
    public void addDummyData(){
        Providerbean p = new Providerbean("Apple","apple@gmail.com","654446456465",445,454);
        Providerbean p2 = new Providerbean("Mi","mi@gmail.com","8989789789",445,454);
        List<Providerbean> providers = new ArrayList<>();
        providers.add(p);
        providers.add(p2);
        Database.getInstance(getApplicationContext()).getProviderDao().insertAllProviders(providers);
        Providerbean p11 = Database.getInstance(getApplicationContext()).getProviderDao().getProviderByName("Apple");
        Providerbean p12 = Database.getInstance(getApplicationContext()).getProviderDao().getProviderByName("Mi");
        Productbean pro = new Productbean("macbook","laptop",1200,p11.getProvider_id());
        Productbean pro2 = new Productbean("iTab","tab",800,p11.getProvider_id());
        Productbean pro3 = new Productbean("iPhone","phone",1200,p11.getProvider_id());
        Productbean pro4 = new Productbean("thinkpad","laptop",1000,p12.getProvider_id());
        Productbean pro5 = new Productbean("tablet","tab",300,p12.getProvider_id());
        Productbean pro6 = new Productbean("mi redmi","phone",100,p12.getProvider_id());
        List<Productbean> products = new ArrayList<>();;
        products.add(pro);
        products.add(pro2);
        products.add(pro3);
        products.add(pro4);
        products.add(pro5);
        products.add(pro6);
        Database.getInstance(getApplicationContext()).getProductDao().insertAllProducts(products);
    }
    @OnClick(R.id.fab)
    public void Click(){
        int pos = tabLayout.getSelectedTabPosition();
        if (pos == 0){
            Intent i=new Intent(getApplicationContext(), ProductAddUpdate.class);
            i.putExtra("from","new");
            startActivity(i);
        }
        else{
            Intent i=new Intent(getApplicationContext(), ProviderAddUpdate.class);
            i.putExtra("from","new");
            startActivity(i);
        }
    }


}