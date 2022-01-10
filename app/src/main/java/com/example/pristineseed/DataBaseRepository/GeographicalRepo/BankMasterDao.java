package com.example.pristineseed.DataBaseRepository.GeographicalRepo;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BankMasterDao {
    @Insert
    Long insert(BankMasterTable bankMasterTable);
    @Insert
    void insert(List<BankMasterTable> bankMasterTablesList);

    @Query("SELECT * FROM bank_master_table WHERE Name not null")
    List<BankMasterTable> fetchAllData();

    @Update
    void update(BankMasterTable bankMasterTable);

    @Query("delete from bank_master_table")
    int deleteAllRecord();

    @Query("SELECT COUNT(1) FROM bank_master_table")
    int getRowCount();

    @Query("SELECT * FROM bank_master_table WHERE Customer_No=:no")
    boolean isDataExist(String no);


}
