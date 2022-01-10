package com.example.pristineseed.DataBaseRepository.GeographicalRepo;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserLocationMasterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(LocationMasterTable userLocationTable);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<LocationMasterTable> userLocationTableList);

    @Query("SELECT * FROM user_location_master_table ORDER BY location_code DESC")
    List<LocationMasterTable> getAllData();

    @Update
    void update(LocationMasterTable userLocationTable);

    @Query("delete from user_location_master_table")
    int deleteAllRecord();

    @Query("SELECT COUNT(location_code) FROM user_location_master_table")
    int getRowCount();

    @Query("SELECT * FROM user_location_master_table WHERE location_code=:location_code and email_id=:email_id")
    int isDataExist(String location_code,String email_id);


    @Query("SELECT * FROM user_location_master_table where location_code =:location_code")
    LocationMasterTable getLocationByCode(String location_code);

}
