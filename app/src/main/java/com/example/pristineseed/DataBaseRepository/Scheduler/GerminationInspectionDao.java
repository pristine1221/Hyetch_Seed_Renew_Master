package com.example.pristineseed.DataBaseRepository.Scheduler;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface GerminationInspectionDao {

    @Insert
    Long insert(GerminationInspection1_Table germinationInspection1_table);

    @Insert
    void insertList(List<GerminationInspection1_Table> germinationInspection1TableList);

    @Query("SELECT * FROM germination_inspection_table ORDER BY production_lot_no DESC")
    List<GerminationInspection1_Table> getAllData();

    @Query("SELECT * FROM germination_inspection_table WHERE production_lot_no =:production_lot_no")
    List<GerminationInspection1_Table> getInpection1DataByLotNo(String production_lot_no);

    @Update
    void update(GerminationInspection1_Table germinationInspection1_table);

    @Query("delete from germination_inspection_table")
    int deleteAllRecord();

    @Query("SELECT COUNT(production_lot_no) FROM germination_inspection_table")
    int getRowCount();

    @Query("SELECT COUNT(production_lot_no) FROM germination_inspection_table WHERE production_lot_no = :production_lot_no")
    int isDataExist(String production_lot_no);

    @Query("SELECT * FROM germination_inspection_table WHERE production_lot_no=:production_lot_no")
    GerminationInspection1_Table getGerminationCreatedOn(String production_lot_no);

    @Query("SELECT * FROM germination_inspection_table WHERE  sync_with_api=0")
    List<GerminationInspection1_Table> getSavedLineIntoLocal();


    @Query("SELECT * FROM germination_inspection_table WHERE production_lot_no = :production_lot_no and sync_with_api=1")
    int getApiSyncwithServerLineInsertedInOne(String production_lot_no);


    @Query("SELECT * from scheduler_line_table sl INNER JOIN germination_inspection_table gi on sl.production_lot_no=gi.production_lot_no and gi.sync_with_api=0 and sl.inspection_1=0")
    List<GerminationInspection1_Table> getUnpostedLine();


}
