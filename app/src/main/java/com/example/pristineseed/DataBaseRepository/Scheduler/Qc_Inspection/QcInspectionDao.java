package com.example.pristineseed.DataBaseRepository.Scheduler.Qc_Inspection;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.pristineseed.DataBaseRepository.Scheduler.FloweringInspectionTable.FloweringInspectionTable;
import com.example.pristineseed.DataBaseRepository.Scheduler.HarvestingInspection.HarvestingInspectionTable;

import java.util.List;

@Dao
public interface QcInspectionDao {

    @Insert
    Long insert(QcInspectionTable qcInspectionTable);

    @Insert
    void insertList(List<QcInspectionTable> qcInspectionTableList);

    @Query("SELECT * FROM qc_inspection_table ORDER BY production_lot_no DESC")
    List<QcInspectionTable> getAllData();

    @Query("SELECT * FROM qc_inspection_table WHERE production_lot_no =:production_lot_no")
    List<QcInspectionTable> getInpection1DataByLotNo(String production_lot_no);

    @Update
    void update(QcInspectionTable qcInspectionTable);

    @Query("delete from qc_inspection_table")
    int deleteAllRecord();

    @Query("SELECT COUNT(production_lot_no) FROM qc_inspection_table")
    int getRowCount();

    @Query("SELECT COUNT(production_lot_no) FROM qc_inspection_table WHERE production_lot_no = :production_lot_no")
    int isDataExist(String production_lot_no);

    @Query("SELECT * FROM qc_inspection_table WHERE production_lot_no=:production_lot_no")
    QcInspectionTable getQcInspectionCreatedOn(String production_lot_no);

    @Query("SELECT * FROM qc_inspection_table WHERE  syncwithQc=0")
    List<QcInspectionTable> getSavedLineIntoLocal();

}
