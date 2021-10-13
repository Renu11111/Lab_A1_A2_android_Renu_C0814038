package com.example.myapplication.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.myapplication.Adapters.ProviderlistAdapter;
import com.example.myapplication.Database.DatabaseInterface.Database;
import com.example.myapplication.Pojo.Providerbean;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ProviderlistFragment extends Fragment {

    @BindView(R.id.provider_recycler)
    RecyclerView provider_recycler;

    @BindView(R.id.search_txt)
    EditText search_txt;
    ProviderlistAdapter adapter;
    Database database;
    List<Providerbean> listprovider;



    public ProviderlistFragment() { }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_providerlist, container, false);
        ButterKnife.bind(this,view);
        database = Database.getInstance(getActivity());
        listprovider = database.getProviderDao().getAllProviders();
        if(listprovider.size()==0){
            provider_recycler.setVisibility(View.GONE);
        }else {
            provider_recycler.setVisibility(View.VISIBLE);
        }
        adapter= new ProviderlistAdapter(getContext(), listprovider) {
            @Override
            public void deleteProvider(int pos) {
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
                        database.getProviderDao().deleteProvider(listprovider.get(pos));
                        listprovider.remove(pos);
                        adapter.list=listprovider;
                        provider_recycler.getAdapter().notifyDataSetChanged();
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
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        provider_recycler.setLayoutManager(mLayoutManager);
        provider_recycler.setAdapter(adapter);

        return view;
    }
    private void filter(String text) {
        listprovider = database.getProviderDao().getAllProviders();
        List<Providerbean> temp = new ArrayList();
        for (Providerbean n :listprovider) {
            if(n.getProvider_name().toLowerCase().contains(text.toLowerCase()) || n.getProvider_email().toLowerCase().contains(text.toLowerCase())){
                temp.add(n);
            }
        }
        adapter.list = temp;
        provider_recycler.getAdapter().notifyDataSetChanged();
    }
}