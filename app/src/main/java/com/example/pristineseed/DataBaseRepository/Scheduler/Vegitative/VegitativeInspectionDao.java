package com.example.pristineseed.DataBaseRepository.Scheduler.Vegitative;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.pristineseed.DataBaseRepository.Scheduler.Seedling.SeedlingInspectionTable;

import java.util.List;

@Dao
public interface VegitativeInspectionDao {

    @Insert
    Long insert(VegitativeInspectionTable vegitativeInspectionTable);

    @Insert
    void insertList(List<VegitativeInspectionTable> vegitativeInspectionTableList);

    @Query("SELECT * FROM vegitative_inpsection_table ORDER BY production_lot_no DESC")
    List<VegitativeInspectionTable> getAllData();

    @Query("SELECT * FROM vegitative_inpsection_table WHERE production_lot_no =:production_lot_no")
    List<VegitativeInspectionTable> getInpection1DataByLotNo(String production_lot_no);

    @Update
    void update(VegitativeInspectionTable vegitativeInspectionTable);

    @Query("delete from vegitative_inpsection_table")
    int deleteAllRecord();

    @Query("SELECT COUNT(production_lot_no) FROM vegitative_inpsection_table")
    int getRowCount();

    @Query("SELECT COUNT(production_lot_no) FROM vegitative_inpsection_table WHERE production_lot_no = :production_lot_no")
    int isDataExist(String production_lot_no);

    @Query("SELECT * FROM vegitative_inpsection_table WHERE production_lot_no=:production_lot_no")
    VegitativeInspectionTable getCreatedOn(String production_lot_no);

    @Query("SELECT * FROM vegitative_inpsection_table WHERE  syncWith_Api=0")
    List<VegitativeInspectionTable> getSavedLineIntoLocal();



}
