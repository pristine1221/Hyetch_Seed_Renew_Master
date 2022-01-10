package com.example.pristineseed.DataBaseRepository.Scheduler.MaturityInspection;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.pristineseed.DataBaseRepository.Scheduler.Vegitative.VegitativeInspectionTable;

import java.util.List;


@Dao
public interface MaturityInspectionDao {
    @Insert
    Long insert(MaturityInspectionTable maturityInspectionTable);

    @Insert
    void insertList(List<MaturityInspectionTable> maturityInspectionTableList);

    @Query("SELECT * FROM maturity_inspection_table ORDER BY production_lot_no DESC")
    List<MaturityInspectionTable> getAllData();

    @Query("SELECT * FROM maturity_inspection_table WHERE production_lot_no =:production_lot_no")
    List<MaturityInspectionTable> getInpection1DataByLotNo(String production_lot_no);

    @Update
    void update(MaturityInspectionTable maturityInspectionTable);

    @Query("delete from maturity_inspection_table")
    int deleteAllRecord();

    @Query("SELECT COUNT(production_lot_no) FROM maturity_inspection_table")
    int getRowCount();

    @Query("SELECT COUNT(production_lot_no) FROM maturity_inspection_table WHERE production_lot_no = :production_lot_no")
    int isDataExist(String production_lot_no);

    @Query("SELECT * FROM maturity_inspection_table WHERE production_lot_no=:production_lot_no")
    MaturityInspectionTable getCreatedOn(String production_lot_no);

    @Query("SELECT * FROM maturity_inspection_table WHERE  syncWithApi8=0")
    List<MaturityInspectionTable> getSavedLineIntoLocal();



}
