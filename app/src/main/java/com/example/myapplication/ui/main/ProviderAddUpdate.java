package com.example.myapplication.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.Adapter.ProductlistAdapter;
import com.example.myapplication.Database.DatabaseInterface.Database;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Pojo.Productbean;
import com.example.myapplication.Pojo.Providerbean;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProviderAddUpdate extends AppCompatActivity {

    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.lat)
    EditText latitude;
    @BindView(R.id.lng)
    EditText longitude;
    @BindView(R.id.callus)
    Button callus;
    @BindView(R.id.emailus)
    Button emailus;
    @BindView(R.id.add)
    Button add;
    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    ProductlistAdapter adapter;
    String from="";
    private List<Productbean> productlist;
    Database database;
    int provider_id;
    Providerbean selected_Provider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_add);
        ButterKnife.bind(this);
        Intent i=getIntent();
        from=i.getStringExtra("from");
        provider_id = i.getIntExtra("provider_id",-1);
        database = Database.getInstance(this.getApplicationContext());
        productlist = database.getProductDao().getAllProducts();
        setValues();
        if (productlist.size() == 0) {

        }

    }
    public void setValues(){
        if (from.equalsIgnoreCase("update")) {
            selected_Provider = Database.getInstance(getApplicationContext()).getProviderDao().getSpecficProviderById(provider_id);
            if (selected_Provider != null){
                add.setText("Update");
                name.setText(selected_Provider.getProvider_name());
                email.setText(selected_Provider.getProvider_email());
                phone.setText(selected_Provider.getProvider_phone());
                latitude.setText(""+selected_Provider.getProvider_lat());
                longitude.setText(""+selected_Provider.getProvider_long());
                callus.setVisibility(View.VISIBLE);
                emailus.setVisibility(View.VISIBLE);
                List<Productbean> listP= new ArrayList<>();
                listP = Database.getInstance(getApplicationContext()).getProductDao().getAllProductsByProvider(selected_Provider.getProvider_id());
                adapter = new ProductlistAdapter(getApplicationContext(),listP) {
                    @Override
                    public void deleteproduct(int pos) {
                        return;
                    }
                };
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(adapter);
            }
        }
        else{
            add.setText("Add");
            callus.setVisibility(View.GONE);
            emailus.setVisibility(View.GONE);
        }
    }

    public boolean CheckValidation(){
        if (name.getText().toString().trim().length() == 0) {
            name.setError("Please enter name");
            name.requestFocus();
            return false;

        }else if(email.getText().toString().trim().length() == 0) {

            email.setError("Please enter email");
            email.requestFocus();
            return false;
        }else if(phone.getText().toString().trim().length() == 0) {

            phone.setError("Please enter phone number");
            phone.requestFocus();
            return false;
        }else if(latitude.getText().toString().trim().length() == 0) {

            latitude.setError("Please enter latitude");
            latitude.requestFocus();
            return false;
        }else if(longitude.getText().toString().trim().length() == 0) {

            longitude.setError("Please enter longitude");
            longitude.requestFocus();
            return false;
        }
        return true;


    }
    @OnClick(R.id.callus)
    public void call(){
        Intent callAction = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + selected_Provider.getProvider_phone().trim()));
        startActivity(callAction);
    }
    @OnClick(R.id.emailus)
    public void email(){
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + email));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "body");
        startActivity(Intent.createChooser(emailIntent, "Chooser Title"));
    }
    @OnClick(R.id.add)
    public void AddUpdate(){
               if(CheckValidation()) {
                   if (from.equalsIgnoreCase("update")) {
                       selected_Provider.setProvider_email(email.getText().toString());
                       selected_Provider.setProvider_lat(Double.parseDouble(latitude.getText().toString()));
                       selected_Provider.setProvider_long(Double.parseDouble(longitude.getText().toString()));
                       selected_Provider.setProvider_name(name.getText().toString());
                       selected_Provider.setProvider_phone(phone.getText().toString());
                       database.getProviderDao().updateProvider(selected_Provider);
                       Toast.makeText(this, "Provider updated successfully", Toast.LENGTH_LONG).show();
                       Intent i = new Intent(getApplicationContext(), MainActivity.class);
                       startActivity(i);
                   }
                   else{
                       List<Providerbean> list = new ArrayList<>();
                       list.add(new Providerbean(name.getText().toString(), email.getText().toString(), phone.getText().toString(), Double.parseDouble(latitude.getText().toString()), Double.parseDouble(longitude.getText().toString())));
                       database.getProviderDao().insertAllProviders(list);
                       Toast.makeText(this, "Provider added successfully", Toast.LENGTH_LONG).show();
                       Intent i = new Intent(getApplicationContext(), MainActivity.class);
                       startActivity(i);

                   }
               }
    }
}