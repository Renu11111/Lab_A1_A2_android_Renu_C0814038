package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.R;

import java.util.List;


public class ProductlistAdapter extends RecyclerView.Adapter<ProductlistAdapter.ViewHolder>{

    private Context context;
    private View.OnClickListener onClickListener;

    public ProductlistAdapter(Context context, List data) {

    }

    @NonNull
    @Override
    public ProductlistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.product_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, final int position) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {


        }
    }


}

