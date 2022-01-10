package com.example.pristineseed.DataBaseRepository.Scheduler.HarvestingInspection;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.pristineseed.DataBaseRepository.Scheduler.MaturityInspection.MaturityInspectionTable;

import java.util.List;

@Dao
public interface HarvestingInspectionDao {

    @Insert
    Long insert(HarvestingInspectionTable harvestingInspectionTable);

    @Insert
    void insertList(List<HarvestingInspectionTable> harvestingInspectionTableList);

    @Query("SELECT * FROM harvesting_inspection_table ORDER BY production_lot_no DESC")
    List<HarvestingInspectionTable> getAllData();

    @Query("SELECT * FROM harvesting_inspection_table WHERE production_lot_no =:production_lot_no")
    List<HarvestingInspectionTable> getInpection1DataByLotNo(String production_lot_no);

    @Update
    void update(HarvestingInspectionTable harvestingInspectionTable);

    @Query("delete from harvesting_inspection_table")
    int deleteAllRecord();

    @Query("SELECT COUNT(production_lot_no) FROM vegitative_inpsection_table")
    int getRowCount();

    @Query("SELECT COUNT(production_lot_no) FROM harvesting_inspection_table WHERE production_lot_no = :production_lot_no")
    int isDataExist(String production_lot_no);

    @Query("SELECT * FROM harvesting_inspection_table WHERE production_lot_no=:production_lot_no")
    HarvestingInspectionTable getCreatedOn(String production_lot_no);

    @Query("SELECT * FROM harvesting_inspection_table WHERE  synWithApi9=0")
    List<HarvestingInspectionTable> getSavedLineIntoLocal();



}
