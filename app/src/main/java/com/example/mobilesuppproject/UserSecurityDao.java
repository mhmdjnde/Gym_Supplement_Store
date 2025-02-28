package com.example.mobilesuppproject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserSecurityDao {

    @Query("SELECT * FROM userinfo")
    LiveData<List<UserSecurity>> getAllAccounts();

    @Insert
    void    addUser(UserSecurity newU);
    @Delete
    void    delUser(UserSecurity delU);

    @Update
    void    updateUser(UserSecurity upU);

    @Query("SELECT COUNT(*) FROM userinfo WHERE email = :accountEmail AND password = :accountPassword")
    int checkAccountEmail(String accountEmail, String accountPassword);
    @Query("SELECT * FROM userinfo WHERE email = :accountEmail LIMIT 1")
    UserSecurity getUserByEmail(String accountEmail);


}
