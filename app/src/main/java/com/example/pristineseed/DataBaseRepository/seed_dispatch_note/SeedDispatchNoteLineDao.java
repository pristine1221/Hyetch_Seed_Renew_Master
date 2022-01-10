package com.example.pristineseed.DataBaseRepository.seed_dispatch_note;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.pristineseed.DataBaseRepository.Planting.PlantingLineTable;

import java.util.List;

@Dao
public interface SeedDispatchNoteLineDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(SeedDispatchNoteLineTable seedDispatchNoteLineTable);

    @Insert
    void insertList(List<SeedDispatchNoteLineTable> seedDispatchNoteLineTableList);

    @Query("SELECT * FROM seed_dispatch_note_line_table ORDER BY dispatch_no DESC")
    List<SeedDispatchNoteLineTable> getAllData();


    @Query("SELECT * FROM seed_dispatch_note_line_table WHERE dispatch_no=:code ")
    List<SeedDispatchNoteLineTable> getAllDatabyDispatchCode(String code);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int update(SeedDispatchNoteLineTable seedDispatchNoteLineTable);

    @Query("delete from seed_dispatch_note_line_table")
    int deleteAllRecord();

    @Query("SELECT COUNT(dispatch_no) FROM seed_dispatch_note_line_table WHERE dispatch_no=:code")
    boolean isDataExist(String code); //change arrival_plan_no to schedule_no


    @Query("SELECT COUNT(dispatch_no) FROM seed_dispatch_note_line_table WHERE line_no=:line_no and dispatch_no=:code")
    int isDataExist_on_line(String line_no,String code); //change arrival_plan_no to schedule_no


    @Query("delete FROM seed_dispatch_note_line_table WHERE  dispatch_no=:dispatch_no and line_no=:line_no")
    int deleteSeedDispatchNoteLine(String dispatch_no,String line_no);

    @Query("SELECT * FROM seed_dispatch_note_line_table WHERE android_id=:android_id and line_no=:line_no")
    int deleteLocalSeedDispatchNoteLine(int android_id,String line_no);

}
