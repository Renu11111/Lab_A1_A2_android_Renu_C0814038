package com.example.myapplication.Database.DatabaseInterface;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.Pojo.Providerbean;

import java.util.List;

@Dao
public interface ProviderDao {
    @Query("SELECT provider_name FROM providers")
    List<String> getAllNames();

    @Query("SELECT * FROM providers")
    List<Providerbean> getAllProviders();

    @Query("SELECT * FROM providers WHERE provider_id = :provider_id")
    Providerbean getSpecficProviderById(int provider_id);

    @Query("SELECT * FROM providers WHERE provider_name = :name")
    Providerbean getProviderByName(String name);

    @Insert
    void insertAllProviders(List<Providerbean> providers);

    @Update
    void updateProvider(Providerbean providers);

    @Delete
    void deleteProvider(Providerbean provider);
}
