package com.example.pristineseed.DataBaseRepository.Scheduler.FloweringInspectionTable;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.pristineseed.DataBaseRepository.Scheduler.NickingInpection.Nicking2InspectionTable;
import com.example.pristineseed.DataBaseRepository.Scheduler.NickingInpection.NickingInspectionTable;

import java.util.List;

@Dao
public interface FloweringInspectionDao {

    @Insert
    Long insert(FloweringInspectionTable floweringInspectionTable);

    @Insert
    void insertList(List<FloweringInspectionTable> floweringInspectionTableList);

    @Query("SELECT * FROM flowering_inspection_table ORDER BY production_lot_no DESC")
    List<FloweringInspectionTable> getAllData();

    @Query("SELECT * FROM flowering_inspection_table WHERE production_lot_no =:production_lot_no")
    List<FloweringInspectionTable> getInpection1DataByLotNo(String production_lot_no);

    @Update
    void update(FloweringInspectionTable floweringInspectionTable);

    @Query("delete from flowering_inspection_table")
    int deleteAllRecord();

    @Query("SELECT COUNT(production_lot_no) FROM flowering_inspection_table")
    int getRowCount();

    @Query("SELECT COUNT(production_lot_no) FROM flowering_inspection_table WHERE production_lot_no = :production_lot_no")
    int isDataExist(String production_lot_no);

    @Query("SELECT * FROM flowering_inspection_table WHERE production_lot_no=:production_lot_no")
    FloweringInspectionTable getFloweringInspectionCreatedOn(String production_lot_no);

    @Query("SELECT * FROM flowering_inspection_table WHERE  syncwith_api6=0")
    List<FloweringInspectionTable> getSavedLineIntoLocal();


}
