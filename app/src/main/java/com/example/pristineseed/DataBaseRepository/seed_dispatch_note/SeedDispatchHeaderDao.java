package com.example.pristineseed.DataBaseRepository.seed_dispatch_note;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.pristineseed.DataBaseRepository.Planting.PlantingDetailHeaderTable;

import java.util.List;

@Dao
public interface SeedDispatchHeaderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(SeedDispatchNoteHeaderTable seedDispatchNoteHeaderTable);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertList(List<SeedDispatchNoteHeaderTable> seedDispatchNoteHeaderTables);

    @Query("SELECT * FROM seed_dispatch_note_header_table ORDER BY dispatch_no DESC")
    List<SeedDispatchNoteHeaderTable> getAllData();

    @Query("SELECT * FROM seed_dispatch_note_header_table WHERE dispatch_no =:code")
    List<SeedDispatchNoteHeaderTable> getHeaderDispactchNo(String code);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(SeedDispatchNoteHeaderTable seedDispatchNoteHeaderTable);

    @Query("delete from seed_dispatch_note_header_table")
    int deleteAllRecord();

    @Query("SELECT COUNT(dispatch_no) FROM seed_dispatch_note_header_table")
    int getRowCount();

    @Query("SELECT 1 FROM seed_dispatch_note_header_table WHERE dispatch_no = :code")
    boolean isDataExist(String code);

    @Query("delete from seed_dispatch_note_header_table WHERE dispatch_no=:dispatch_no")
    int deleteRecordHedaer(String dispatch_no);

    @Query("delete from seed_dispatch_note_header_table WHERE android_id=:android_id")
    int deleteRecordHedaerOffline(int  android_id);

    @Query("update seed_dispatch_note_header_table  set delete_line_header=:delete_header_update_no")
    int updateAfterDelete(int delete_header_update_no);


    @Query("update seed_dispatch_note_header_table  set status=:status and complete_dispatch_header_local_status=:complete_dispatch_header_local_status where dispatch_no=:dispatch_no")
    int updateCompleteDispatchHeaderStatus(int status, int complete_dispatch_header_local_status,String dispatch_no);


}
