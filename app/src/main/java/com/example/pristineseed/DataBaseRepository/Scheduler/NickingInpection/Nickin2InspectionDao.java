package com.example.pristineseed.DataBaseRepository.Scheduler.NickingInpection;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.pristineseed.DataBaseRepository.Scheduler.GerminationInspection1_Table;

import java.util.List;

@Dao
public interface Nickin2InspectionDao {

    @Insert
    Long insert(Nicking2InspectionTable nicking2InspectionTable);

    @Insert
    void insertList(List<Nicking2InspectionTable> nicking2InspectionTableList);

    @Query("SELECT * FROM nicking2_inspection_table ORDER BY production_lot_no DESC")
    List<Nicking2InspectionTable> getAllData();

    @Query("SELECT * FROM nicking2_inspection_table WHERE production_lot_no =:production_lot_no")
    List<Nicking2InspectionTable> getInpection1DataByLotNo(String production_lot_no);

    @Update
    void update(Nicking2InspectionTable nicking2InspectionTable);

    @Query("delete from nicking2_inspection_table")
    int deleteAllRecord();

    @Query("SELECT COUNT(production_lot_no) FROM nicking2_inspection_table")
    int getRowCount();

    @Query("SELECT COUNT(production_lot_no) FROM nicking2_inspection_table WHERE production_lot_no = :production_lot_no")
    int isDataExist(String production_lot_no);

    @Query("SELECT * FROM nicking2_inspection_table WHERE production_lot_no=:production_lot_no")
    Nicking2InspectionTable getNicking2CreatedOn(String production_lot_no);


    @Query("SELECT * FROM nicking2_inspection_table WHERE  syncWithApi5=0")
    List<Nicking2InspectionTable> getSavedLineIntoLocal();

}
