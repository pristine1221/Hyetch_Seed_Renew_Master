package com.example.pristineseed.DataBaseRepository.tfa;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface CreateTFADao {

   @Insert Long insert(TFA_Header_Table tfa_header_table);

   @Insert
    void insert(List<TFA_Header_Table> tfa_header_tableList);

    @Query("SELECT * FROM tfa_header_table ORDER BY android_id DESC")
       List<TFA_Header_Table> getAllData();

   @Update
    void update(TFA_Header_Table tfa_header_table);

    @Query("SELECT * FROM tfa_header_table WHERE tfa_code = :tfa_code and created_on=:createOn and tfa_code is not null")
    boolean isDataExist(String tfa_code,String createOn);

    @Query("update tfa_header_table SET created_on=:created_on where tfa_code=:tfa_code")
    int update_TfaStatus(String tfa_code,String created_on);

    @Query("SELECT * FROM tfa_header_table")
    List<TFA_Header_Table> fetchTfaHeaderData();

    @Query("SELECT * FROM tfa_header_table WHERE tfa_code=:tfa_code")
    List<TFA_Header_Table> fetch(String tfa_code);
}
