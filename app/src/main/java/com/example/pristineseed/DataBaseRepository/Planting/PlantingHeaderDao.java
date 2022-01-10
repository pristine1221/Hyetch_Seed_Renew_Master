package com.example.pristineseed.DataBaseRepository.Planting;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.pristineseed.DataBaseRepository.Scheduler.Scheduler_Header_Table;

import java.util.List;

@Dao
public interface PlantingHeaderDao {
    @Insert
    Long insert(PlantingDetailHeaderTable plantingDetailHeaderTable);
    @Insert
    void insertList(List<PlantingDetailHeaderTable> plantingDetailHeaderTableList);

    @Query("SELECT * FROM Planting_header_table ORDER BY code DESC")
    List<PlantingDetailHeaderTable> getAllData();

    @Query("SELECT * FROM Planting_header_table WHERE code =:code")
    List<PlantingDetailHeaderTable> getHeaderByPlantingno(String code);

    @Update
    void update(PlantingDetailHeaderTable scheduler_header_table);

    @Query("delete from Planting_header_table")
    int deleteAllRecord();

    @Query("SELECT COUNT(code) FROM Planting_header_table")
    int getRowCount();

    @Query("SELECT 1 FROM Planting_header_table WHERE code = :code")
    boolean isDataExist(String code);

    @Query("delete from Planting_header_table WHERE code=:code ")
     int deleteRecordHedaer(String code);


    @Query("update Planting_header_table  set status=:status and complete_planting_local_status=:complete_planting_local_status where code=:planting_code")
    int updateCompletePlantingStatus(int status, int complete_planting_local_status,String planting_code);
}
