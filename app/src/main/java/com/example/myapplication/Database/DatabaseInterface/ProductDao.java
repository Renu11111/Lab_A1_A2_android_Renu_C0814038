package com.example.myapplication.Database.DatabaseInterface;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.Pojo.Productbean;

import java.util.List;

@Dao
public interface ProductDao {
    @Query("SELECT COUNT(*) FROM products WHERE provider_fk = :id")
    int getProductsCount(int id);

    @Query("SELECT * FROM products")
    List<Productbean> getAllProducts();

    @Query("SELECT * FROM products WHERE provider_fk = :id")
    List<Productbean> getAllProductsByProvider(int id);

    @Query("SELECT * FROM products WHERE product_id = :product_id")
    Productbean getProductById(int product_id);


    @Insert
    void insertAllProducts(List<Productbean> products);

    @Update
    void updateProduct(Productbean product);

    @Delete
    void deleteProduct(Productbean product);
}
