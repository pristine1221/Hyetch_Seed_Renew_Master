package com.example.pristineseed.DataBaseRepository.GeographicalRepo;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RegionMasterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(RegionMasterTable regionMasterTable);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<RegionMasterTable> regionMasterTables);

    @Query("SELECT * FROM region_master_table ORDER BY code DESC")
    List<RegionMasterTable> getAllData();

    @Query("SELECT * FROM region_master_table WHERE code =:code")
    RegionMasterTable getGeoId(String code);

    @Update
    void update(RegionMasterTable regionMasterTable);

    @Query("delete from region_master_table")
    int deleteAllRecord();

    @Query("SELECT COUNT(id) FROM region_master_table")
    int getRowCount();


}
