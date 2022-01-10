package com.example.pristineseed.DataBaseRepository.GeographicalRepo;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface StateMasterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(StateMasterTable stateMasterTable);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<StateMasterTable> stateMasterTables);

    @Query("SELECT * FROM state_master_table ORDER BY code DESC")
    List<StateMasterTable> getAllData();

    @Query("SELECT * FROM state_master_table WHERE code =:id")
    StateMasterTable getGeoId(int id);

    @Update
    void update(StateMasterTable stateMasterTable);


    @Query("delete from state_master_table")
    int deleteAllRecord();

    @Query("SELECT COUNT(code) FROM state_master_table")
    int getRowCount();

    @Query("SELECT * FROM state_master_table WHERE code=:code")
    boolean isDataExist(String code);

    @Query("SELECT * FROM state_master_table WHERE code=:code")
    List<StateMasterTable> getStateName(String code);



}
