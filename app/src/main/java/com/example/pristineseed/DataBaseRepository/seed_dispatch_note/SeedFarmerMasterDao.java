package com.example.pristineseed.DataBaseRepository.seed_dispatch_note;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SeedFarmerMasterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Seed_Farmer_master_Table seedFarmer_master_table);
    @Insert
    void insertList(List<Seed_Farmer_master_Table> seedFarmer_master_tableList);

    @Query("SELECT * FROM seed_farmer_master_table ORDER BY code DESC")
    List<Seed_Farmer_master_Table> getAllData();


    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Seed_Farmer_master_Table seedFarmer_master_table);

    @Query("delete from seed_farmer_master_table")
    int deleteAllRecord();

    @Query("SELECT COUNT(1) FROM seed_farmer_master_table")
    int getRowCount();

    @Query("SELECT COUNT(code)FROM seed_farmer_master_table WHERE lower(code)= :code")
    int isDataExist(String code);
}
