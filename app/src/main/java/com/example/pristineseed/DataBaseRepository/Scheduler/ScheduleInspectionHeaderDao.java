package com.example.pristineseed.DataBaseRepository.Scheduler;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ScheduleInspectionHeaderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Scheduler_Header_Table scheduelInspectionHeaderTables);

    @Insert
    void insertList(List<Scheduler_Header_Table> scheduelInspectionHeaderTables);

    @Query("SELECT * FROM scheduler_header_table  WHERE upper(user_id)=:user_id  and  user_type=:user_type ORDER BY schedule_no DESC")
    List<Scheduler_Header_Table> user_typegetAllData(String user_id,String user_type);

    @Query("SELECT * FROM scheduler_header_table WHERE schedule_no =:schedule_no")
    Scheduler_Header_Table getHeaderByScheduler_no(String schedule_no);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Scheduler_Header_Table scheduelInspectionHeaderTable);

    @Query("delete from scheduler_header_table")
    int deleteAllRecord();

    @Query("SELECT COUNT(schedule_no) FROM scheduler_header_table")
    int getRowCount();

    @Query("SELECT COUNT(schedule_no) FROM scheduler_header_table WHERE schedule_no = :schedule_no")
    int isDataExist(String schedule_no);

    @Query("update scheduler_header_table set season=:season,season_name=:season_name,production_centre=:production_center,production_centre_name=:production_center_name,date=:date,status=:status,user_type=:user_type where schedule_no=:scheduler_no ")
    int updateSchedulerHeader(String season,String season_name,String production_center,String production_center_name,String date,String status,String scheduler_no,String user_type);


}
