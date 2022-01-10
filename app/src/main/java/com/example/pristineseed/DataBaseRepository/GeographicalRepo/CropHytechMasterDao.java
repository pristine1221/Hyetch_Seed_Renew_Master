package com.example.pristineseed.DataBaseRepository.GeographicalRepo;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface CropHytechMasterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(CropHytechMasterTable cropHytechMasterTable);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<CropHytechMasterTable> cropMasterTableList);

    @Query("SELECT * FROM crop_hytech_master_table ORDER BY code DESC")
    List<CropHytechMasterTable> getAllData();


    @Update
    void update(CropHytechMasterTable cropHytechMasterTable);

    @Query("delete from crop_hytech_master_table")
    int deleteAllRecord();

    @Query("SELECT COUNT(android_id) FROM crop_hytech_master_table")
    int getRowCount();

    @Query("SELECT * FROM crop_hytech_master_table WHERE code=:code")
    boolean isDataExist(String code);

    @Query("SELECT * FROM crop_hytech_master_table dm WHERE dm.code IN (SELECT gs.district FROM geographic_setup_table gs WHERE gs.state=:stateCode)")
    List<CropHytechMasterTable>fetch_byGeograficalStateCode(String stateCode);

    @Query("SELECT * from crop_hytech_master_table where Code=:code")
    List<CropHytechMasterTable>  getCropName(String code);

    @Query("SELECT * from crop_hytech_master_table where Code=:code")
    CropHytechMasterTable  getCropByCodeName(String code);


}
