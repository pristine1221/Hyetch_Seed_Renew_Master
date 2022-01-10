package com.example.pristineseed.DataBaseRepository.GeographicalRepo;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.HashSet;
import java.util.List;

@Dao
public interface DistricMasterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(DistricMasterTable districMasterTable);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<DistricMasterTable> districMasterTables);

    @Query("SELECT * FROM distric_master_table ORDER BY code DESC")
    List<DistricMasterTable> getAllData();

    @Query("SELECT * FROM distric_master_table WHERE id =:id")
    DistricMasterTable getGeoId(int id);

    @Update
    void update(DistricMasterTable districMasterTable);

    @Query("delete from distric_master_table")
    int deleteAllRecord();

    @Query("SELECT COUNT(id) FROM distric_master_table")
    int getRowCount();

    @Query("SELECT * FROM distric_master_table WHERE code=:code")
    boolean isDataExist(String code);

    @Query("SELECT * FROM distric_master_table dm WHERE dm.code IN (SELECT gs.district FROM geographic_setup_table gs WHERE gs.state=:stateCode)")
    List<DistricMasterTable>fetch_byGeograficalStateCode(String stateCode);

    @Query("SELECT * FROM distric_master_table where code=:districtcode")
    List<DistricMasterTable> fetchDistrictName(String districtcode);

    @Query("SELECT * FROM distric_master_table where code=:districtcode ")
    DistricMasterTable getDistName(String districtcode);


}
