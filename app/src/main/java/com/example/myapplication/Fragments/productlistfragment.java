package com.example.myapplication.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.myapplication.Adapter.ProductlistAdapter;
import com.example.myapplication.Adapter.ProviderlistAdapter;
import com.example.myapplication.Database.DatabaseInterface.Database;
import com.example.myapplication.Pojo.Productbean;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class productlistfragment extends Fragment {

    @BindView(R.id.product_recycler)
    RecyclerView product_recycler;

    @BindView(R.id.search_txt)
    EditText search_txt;
    Database database;
    List<Productbean> listproduct;

    ProductlistAdapter adapter;

    public productlistfragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_productlistfragment, container, false);
        ButterKnife.bind(this,view);
        database = Database.getInstance(getActivity());
        listproduct = database.getProductDao().getAllProducts();
        search_txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
        if(listproduct.size()==0){
            product_recycler.setVisibility(View.GONE);
        }else {
            product_recycler.setVisibility(View.VISIBLE);
        }
        adapter= new ProductlistAdapter(getContext(), listproduct) {
            @Override
            public void deleteproduct(int pos) {
                final AlertDialog.Builder mainDialog = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialogView = inflater.inflate(R.layout.alert_dialog, null);
                mainDialog.setView(dialogView);

                final Button cancel = (Button) dialogView.findViewById(R.id.cancel);
                final Button save = (Button) dialogView.findViewById(R.id.save);
                final ImageView cross=(ImageView) dialogView.findViewById(R.id.cross);
                final AlertDialog alertDialog = mainDialog.create();
                alertDialog.show();

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        alertDialog.dismiss();

                    }
                });

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        database.getProductDao().deleteProduct(listproduct.get(pos));
                        listproduct.remove(pos);
                        adapter.list=listproduct;
                        product_recycler.getAdapter().notifyDataSetChanged();
                        alertDialog.dismiss();
                    }
                });
                cross.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
            }
        };
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        product_recycler.setLayoutManager(mLayoutManager);
        product_recycler.setAdapter(adapter);

        return view;

    }
    private void filter(String text) {
        listproduct = database.getProductDao().getAllProducts();
        List<Productbean> temp = new ArrayList();
        for (Productbean n :listproduct) {
            if(n.getProduct_name().toLowerCase().contains(text.toLowerCase()) || n.getProduct_desc().toLowerCase().contains(text.toLowerCase())){
                temp.add(n);
            }
        }
        adapter.list = temp;
        product_recycler.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.list = database.getProductDao().getAllProducts();
        product_recycler.getAdapter().notifyDataSetChanged();

    }
}