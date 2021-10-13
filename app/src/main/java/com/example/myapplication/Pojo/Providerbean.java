package com.example.myapplication.Pojo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "providers")
public class Providerbean implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int provider_id;
    private String provider_name;
    private String provider_email;
    private String provider_phone;
    private double provider_lat;
    private double provider_long;


    public Providerbean(String provider_name, String provider_email, String provider_phone, double provider_lat, double provider_long) {
        this.provider_name = provider_name;
        this.provider_email = provider_email;
        this.provider_phone = provider_phone;
        this.provider_lat = provider_lat;
        this.provider_long = provider_long;
    }

    public int getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(int provider_id) {
        this.provider_id = provider_id;
    }

    public String getProvider_name() {
        return provider_name;
    }

    public void setProvider_name(String provider_name) {
        this.provider_name = provider_name;
    }

    public String getProvider_email() {
        return provider_email;
    }

    public void setProvider_email(String provider_email) {
        this.provider_email = provider_email;
    }

    public String getProvider_phone() {
        return provider_phone;
    }

    public void setProvider_phone(String provider_phone) {
        this.provider_phone = provider_phone;
    }

    public double getProvider_lat() {
        return provider_lat;
    }

    public void setProvider_lat(double provider_lat) {
        this.provider_lat = provider_lat;
    }

    public double getProvider_long() {
        return provider_long;
    }

    public void setProvider_long(double provider_long) {
        this.provider_long = provider_long;
    }

}