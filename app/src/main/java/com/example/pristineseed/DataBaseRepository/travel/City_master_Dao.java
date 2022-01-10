package com.example.pristineseed.DataBaseRepository.travel;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;

@Dao
public interface City_master_Dao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(CityMasterTable cityMasterTable);
    @Insert
    void insert(List<CityMasterTable> cityMasterTables);

    @Query("SELECT * FROM city_master_table WHERE active=0")
    List<CityMasterTable> getAllData();

    @Update
    void update(CityMasterTable cityMasterTable);


    @Query("delete from city_master_table")
    int deleteAllRecord();

    @Query("SELECT COUNT(code) FROM city_master_table")
    int getRowCount();

    @Query("SELECT * from city_master_table")//WHERE code=:code"
    List<CityMasterTable> fetechCityByName();

    @Query("SELECT * FROM city_master_table WHERE code=:code")
    int isDataExist(String code);

}
