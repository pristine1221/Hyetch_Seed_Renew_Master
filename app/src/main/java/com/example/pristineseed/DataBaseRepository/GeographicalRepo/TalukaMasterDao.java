package com.example.pristineseed.DataBaseRepository.GeographicalRepo;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TalukaMasterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(TalukaMasterTable talukaMasterTable);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<TalukaMasterTable> talukaMasterTables);

    @Query("SELECT * FROM taluka_master_table ORDER BY code DESC")
    List<TalukaMasterTable> getAllData();

    @Query("SELECT * FROM taluka_master_table WHERE code =:code")
    TalukaMasterTable getGeoId(String code);

    @Update
    void update(TalukaMasterTable talukaMasterTable);

    @Query("delete from taluka_master_table")
    int deleteAllRecord();

    @Query("SELECT COUNT(id) FROM taluka_master_table")
    int getRowCount();

    @Query("SELECT * FROM taluka_master_table tm WHERE tm.code IN (SELECT gs.taluka FROM geographic_setup_table gs WHERE gs.district=:district_no) ")
    List<TalukaMasterTable> fetchBydistrictNo(String district_no);

    @Query("SELECT *FROM taluka_master_table WHERE code=:code")
    List<TalukaMasterTable>getTalukaName(String code);

}
