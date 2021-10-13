package com.example.myapplication.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

public class ProductAddUpdate extends AppCompatActivity {

    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.description)
    EditText description;
    @BindView(R.id.price)
    EditText price;
    @BindView(R.id.provider_name)
    TextView provider_name;
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.add)
    Button add;
    List<Providerbean> providerlist;
    private Productbean productbean;
    private Providerbean providerbean;
    Database database;
    int provider_id;
    String from="";
    Providerbean selectedProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_add);
        ButterKnife.bind(this);
        database = Database.getInstance(this.getApplicationContext());
        providerlist=database.getProviderDao().getAllProviders();
        Intent i=getIntent();
        from=i.getStringExtra("from");
        int id = i.getIntExtra("product_id", -1);
        if(from.equalsIgnoreCase("new")){
            provider_name.setVisibility(View.GONE);
            add.setText("Add");

        }else {
            provider_name.setVisibility(View.VISIBLE);
            add.setText("Update");
            if (id != -1) {

                productbean = database.getProductDao().getProductById(id);
                providerbean = database.getProviderDao().getSpecficProviderById(productbean.getProvider_fk());
                name.setText(productbean.getProduct_name());
                description.setText(productbean.getProduct_desc());
                price.setText("" + productbean.getProduct_price());
                provider_name.setText("Provider: "+providerbean.getProvider_name());
                for (int j = 0; j < providerlist.size(); j++) {
                    if (providerlist.get(j).getProvider_id() == providerbean.getProvider_id())
                        spinner.setSelection(j);

                }
            }

        }
        if(providerlist.size()==0){

        }else {
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Database.getInstance(getApplicationContext()).getProviderDao().getAllNames());
            spinner.setAdapter(arrayAdapter);

        }


    }

    public boolean CheckValidation(){
        if(providerlist.size()==0){
            Toast.makeText(this, "Add Provider first", Toast.LENGTH_LONG).show();
                return false;
        }else {
            String providerS = (String) spinner.getSelectedItem();
            selectedProvider = Database.getInstance(getApplicationContext()).getProviderDao().getProviderByName(providerS);
            if (selectedProvider == null) {
                Toast.makeText(this, "Select a Provider for the product", Toast.LENGTH_LONG).show();
                return false;
            }
        }



        if (name.getText().toString().trim().length() == 0) {
            name.setError("Please enter name");
            name.requestFocus();
            return false;

        }else if(description.getText().toString().trim().length() == 0) {

            description.setError("Please enter description");
            description.requestFocus();
            return false;
        }else if(price.getText().toString().trim().length() == 0) {

            price.setError("Please enter price");
            price.requestFocus();
            return false;
        }
        return true;


    }
    @OnClick(R.id.add)
    public void AddProduct() {
        if (CheckValidation()) {
            String providerS = (String) spinner.getSelectedItem();
            selectedProvider = Database.getInstance(getApplicationContext()).getProviderDao().getProviderByName(providerS);
            provider_id = selectedProvider.getProvider_id();
            if(from.equalsIgnoreCase("new")) {
                List<Productbean> list = new ArrayList<>();
                list.add(new Productbean(name.getText().toString(), description.getText().toString(), Integer.parseInt(price.getText().toString()), provider_id));
                database.getProductDao().insertAllProducts(list);
                Toast.makeText(this, "Product added successfully", Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }else {
                if (productbean != null) {
                    productbean.setProduct_name(name.getText().toString());
                    productbean.setProduct_desc(description.getText().toString());
                    productbean.setProduct_price(Integer.parseInt(price.getText().toString()));
                    productbean.setProvider_fk(provider_id);
                    database.getProductDao().updateProduct(productbean);
                    Toast.makeText(this, "Product updated successfully", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                }
            }
        }
    }
}