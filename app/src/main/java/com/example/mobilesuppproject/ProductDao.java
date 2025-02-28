package com.example.mobilesuppproject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProductDao {

    @Query("SELECT * FROM products")
    LiveData<List<Product>> getAllProducts();

    @Query("SELECT * FROM products WHERE category = :category")
    LiveData<List<Product>> getProductsByCategory(String category);

    @Insert
    void insert(Product product);

    @Query("SELECT DISTINCT category FROM products")
    LiveData<List<String>> getAllCategories();

    @Query("SELECT COUNT(*) FROM products WHERE name = :productName")
    int getCountByName(String productName);

    @Query("SELECT * FROM products WHERE name LIKE '%' || :query || '%'")
    LiveData<List<Product>> searchProductsByName(String query);

}
