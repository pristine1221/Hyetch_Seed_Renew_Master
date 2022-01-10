package com.example.pristineseed.DataBaseRepository.GeographicalRepo;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ZoneMaterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(ZoneMasterTable zoneMasterTable);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<ZoneMasterTable> zoneMasterTables);

    @Query("SELECT * FROM zone_master_table ORDER BY code DESC")
    List<ZoneMasterTable> getAllData();

    @Query("SELECT * FROM zone_master_table WHERE code =:code")
    ZoneMasterTable getZoneNameBycode(String code);

    @Update
    void update(ZoneMasterTable zoneMasterTable);

    @Query("delete from zone_master_table")
    int deleteAllRecord();

    @Query("SELECT COUNT(id) FROM zone_master_table")
    int getRowCount();



}
