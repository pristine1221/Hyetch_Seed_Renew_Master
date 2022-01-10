package com.example.pristineseed.DataBaseRepository.Scheduler.NickingInpection;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.pristineseed.DataBaseRepository.Scheduler.SchedulerInspectionLineTable;

import java.util.List;

@Dao
public interface NickingInspInsertDao {
    @Insert
    Long insert(NickingInspectionTable nickingInspInsertTable);
    @Insert
    void insertList(List<NickingInspectionTable> nickingInspInsertTableList);

    @Query("SELECT * FROM nicking_inspection_insert_table ORDER BY production_lot_no DESC")
    List<NickingInspectionTable> getAllData();

    @Query("SELECT * FROM nicking_inspection_insert_table WHERE production_lot_no =:production_lot_no")
    List<NickingInspectionTable> getInpection1DataByLotNo(String production_lot_no);


    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(NickingInspectionTable nickingInspInsertTable);

    @Query("delete from nicking_inspection_insert_table")
    int deleteAllRecord();

    @Query("SELECT COUNT(production_lot_no) FROM nicking_inspection_insert_table")
    int getRowCount();

    @Query("SELECT COUNT(production_lot_no) FROM nicking_inspection_insert_table WHERE production_lot_no = :production_lot_no")
    int isDataExist(String production_lot_no);


    @Query("update nicking_inspection_insert_table set sync_with_api_insp4=:sync_with_api_insp4 and created_on=:created_on where android_id=:androi_id and status='Pending'")
    int updateNickingInspectionData(String sync_with_api_insp4,String created_on,int androi_id);


    @Query("SELECT * FROM nicking_inspection_insert_table WHERE  sync_with_api_insp4=0")
    List<NickingInspectionTable> getSavedLineIntoLocal();


    @Query("SELECT * FROM nicking_inspection_insert_table WHERE  production_lot_no=:production_lot_no")
    NickingInspectionTable nickingDataGet(String production_lot_no);




}
