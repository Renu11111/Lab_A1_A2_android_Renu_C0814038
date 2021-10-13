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
import com.example.myapplication.Pojo.Providerbean;
import com.example.myapplication.R;
import com.example.myapplication.ui.main.ProviderAddUpdate;

import java.util.List;


public abstract class ProviderlistAdapter extends RecyclerView.Adapter<ProviderlistAdapter.ViewHolder>{

     Context context;
    public List<Providerbean> list;

    public ProviderlistAdapter(Context context, List<Providerbean> listprovider)
    {
        this.context=context;
        this.list=listprovider;

    }

    @NonNull
    @Override
    public ProviderlistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.provideritem, viewGroup, false);
        return new ViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(ViewHolder viewHolder,  int position)
    {

        viewHolder.name.setText(list.get(position).getProvider_name());
        viewHolder.email.setText(list.get(position).getProvider_email());
        viewHolder.products.setText("Products Count :" + Database.getInstance(context).getProductDao().getProductsCount(list.get(position).getProvider_id()));
        viewHolder.delete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                deleteProvider(position);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView name,email,price,products;
        ImageView delete;
        public ViewHolder(View view) {
            super(view);
            email=(TextView)itemView.findViewById(R.id.email);
            name=(TextView)itemView.findViewById(R.id.name);
            products=(TextView)itemView.findViewById(R.id.products);
            delete=(ImageView) itemView.findViewById(R.id.delete);
            // Define click listener for the ViewHolder's View
            view.setOnClickListener(this);
        }



        @Override
        public void onClick(View v) {
            Intent i=new Intent(context, ProviderAddUpdate.class);
            i.putExtra("from","update");
            i.putExtra("provider_id", list.get(getAdapterPosition()).getProvider_id());
            context.startActivity(i);
        }
    }
    public abstract void deleteProvider(int pos);
}
