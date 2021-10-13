package com.example.myapplication.Pojo;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.security.Provider;

@Entity(tableName = "products", foreignKeys = {@ForeignKey(entity = Providerbean.class, parentColumns = "provider_id", childColumns = "provider_fk", onDelete = ForeignKey.CASCADE)})
public class Productbean implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int product_id;
    private String product_name;
    private String product_desc;
    private int product_price;
    @NonNull
    private int provider_fk;

    public Productbean(String product_name, String product_desc, int product_price, int provider_fk) {
        this.product_name = product_name;
        this.product_desc = product_desc;
        this.product_price = product_price;
        this.provider_fk = provider_fk;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_desc() {
        return product_desc;
    }

    public void setProduct_desc(String product_desc) {
        this.product_desc = product_desc;
    }

    public int getProduct_price() {
        return product_price;
    }

    public void setProduct_price(int product_price) {
        this.product_price = product_price;
    }

    public int getProvider_fk() {
        return provider_fk;
    }

    public void setProvider_fk(int provider_fk) {
        this.provider_fk = provider_fk;
    }
}