package com.example.pristineseed.DataBaseRepository.Planting;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.pristineseed.DataBaseRepository.Scheduler.SchedulerInspectionLineTable;

import java.util.List;

@Dao
public interface PlantingLineDao {
    @Insert
    Long insert(PlantingLineTable plantingLineTable);

    @Insert
    void insertList(List<PlantingLineTable> plantingLineTableList);

    @Query("SELECT * FROM planting_line_table ORDER BY code DESC")
    List<PlantingLineTable> getAllData();


    @Query("SELECT * FROM planting_line_table WHERE code=:code")
    List<PlantingLineTable> getAllDatabyPlantingCode(String code);

    @Update
    void update(PlantingLineTable scheduleInspectionLineTable);

    @Query("delete from planting_line_table")
    int deleteAllRecord();

    @Query("SELECT COUNT(code) FROM planting_line_table WHERE code=:code")
    int isDataExist(String code); //change arrival_plan_no to schedule_no


    @Query("SELECT COUNT(code) FROM planting_line_table WHERE line_no=:line_no and code=:code")
    int isDataExist_on_line(String line_no,String code); //change arrival_plan_no to schedule_no

    @Query("delete from planting_line_table WHERE code=:code and line_no=:line_no")
    int deletePlantingLine(String code,String line_no);


    @Query("delete from planting_line_table WHERE line_no=:line_no")
    int deletePlantingLineNo(String line_no);



}
