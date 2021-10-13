package com.example.myapplication.Adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myapplication.Fragments.ProviderlistFragment;
import com.example.myapplication.Fragments.productlistfragment;


public class ViewPagerAdapter extends FragmentStateAdapter
{
    private Context context;
    int tabcount;

    public ViewPagerAdapter(Context context,@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle,int tabcount)
    {
        super(fragmentManager, lifecycle);
        this.context=context;
        this.tabcount=tabcount;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position)
    {
        if(position==0)
            return new productlistfragment();
        if(position==1)
            return new ProviderlistFragment();
        return null;
    }

    @Override
    public int getItemCount()
    {
        return tabcount;
    }
}
