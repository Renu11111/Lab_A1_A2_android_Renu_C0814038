package com.example.myapplication.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.Database.DatabaseInterface.Database;
import com.example.myapplication.Pojo.Productbean;
import com.example.myapplication.R;
import com.example.myapplication.ui.main.ProductAddUpdate;

import java.util.List;


public abstract class ProductlistAdapter extends RecyclerView.Adapter<ProductlistAdapter.ViewHolder>{

     Context context;
    public List<Productbean> list;


    public ProductlistAdapter(Context context, List<Productbean> listproduct) {
        this.context=context;
        this.list=listproduct;
    }

    @NonNull
    @Override
    public ProductlistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.product_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder re, @SuppressLint("RecyclerView") int position) {

        re.name.setText("Name: "+toCapitalize(list.get(position).getProduct_name()));

        re.description.setText("Desc: "+toCapitalize(list.get(position).getProduct_desc()));

        re.price.setText("Price: $"+list.get(position).getProduct_price());

        String providerStr = Database.getInstance(context).getProviderDao().getSpecficProviderById(list.get(position).getProvider_fk()).getProvider_name();

        re.provider.setText("Provider: "+toCapitalize(providerStr));

        re.delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                deleteproduct(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name,description,price,provider;
        ImageView delete;
        public ViewHolder(View view) {
            super(view);
            description= (TextView)itemView.findViewById(R.id.description);
            name= (TextView)itemView.findViewById(R.id.name);
            price= (TextView)itemView.findViewById(R.id.price);
            provider= (TextView)itemView.findViewById(R.id.provider);
            delete= (ImageView) itemView.findViewById(R.id.delete);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            Intent i=new Intent(context, ProductAddUpdate.class);
            i.putExtra("from","update");
            i.putExtra("product_id", list.get(getAdapterPosition()).getProduct_id());
            context.startActivity(i);
        }
    }
    public abstract void deleteproduct(int pos);

    public String toCapitalize(String s){
        return s.substring(0,1).toUpperCase() + s.substring(1).toLowerCase();
    }

}

