package com.example.pristineseed.sql_lite_process.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.pristineseed.sql_lite_process.model.User;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    Long insert(User u);
    @Insert
    void insert(List<User> u);

  /*  @Query("SELECT * FROM user_mst ORDER BY id DESC")
    List<User> getAllUsers();

    @Query("SELECT * FROM user_mst WHERE id =:id")
    User getUser(int id);*/

    @Update
    void update(User u);

    @Delete
    void delete(User u);
}