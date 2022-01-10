package com.example.pristineseed.DataBaseRepository.GeographicalRepo;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.pristineseed.DataBaseRepository.GeographicalRepo.AreaMasterTable;

import java.util.List;

@Dao
public interface AreaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(AreaMasterTable areaMasterTable);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<AreaMasterTable> areaMasterTable);

    @Query("SELECT * FROM area_master_table ORDER BY area_code DESC")
    List<AreaMasterTable> getAllData();

    @Update
    void update(AreaMasterTable areaMasterTable);

    @Query("delete from area_master_table")
    int deleteAllRecord();

    @Query("SELECT COUNT(android_id) FROM area_master_table")
    int getRowCount();

    @Query("SELECT * FROM area_master_table WHERE area_code=:code")
    boolean isDataExist(String code);

    @Query("SELECT * FROM area_master_table dm WHERE dm.area_code IN (SELECT gs.district FROM geographic_setup_table gs WHERE gs.state=:stateCode)")
    List<AreaMasterTable>fetch_byGeograficalStateCode(String stateCode);


    @Query("SELECT * FROM area_master_table where area_code=:areacode")
    List<AreaMasterTable> fetchDistrictName(String areacode);



}
