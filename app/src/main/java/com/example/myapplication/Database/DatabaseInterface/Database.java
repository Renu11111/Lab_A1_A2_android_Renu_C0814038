package com.example.myapplication.Database.DatabaseInterface;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myapplication.Pojo.Productbean;
import com.example.myapplication.Pojo.Providerbean;

import java.security.Provider;


@androidx.room.Database(entities = { Providerbean.class, Productbean.class }, version = 1)
public abstract class Database extends RoomDatabase {
    public abstract ProductDao getProductDao();
    public abstract ProviderDao getProviderDao();
    private static Database database;
    public static Database getInstance(Context context) {
        if (null == database) {
            database = buildDatabaseInstance(context);
        }
        return database;
    }

    private static Database buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context,
                Database.class,
                "DB")
                .allowMainThreadQueries().build();
    }

    public void cleanUp(){
        database = null;
    }

}
