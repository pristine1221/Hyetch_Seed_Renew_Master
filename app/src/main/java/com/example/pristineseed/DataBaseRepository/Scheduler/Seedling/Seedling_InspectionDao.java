package com.example.pristineseed.DataBaseRepository.Scheduler.Seedling;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.pristineseed.DataBaseRepository.Scheduler.GerminationInspection1_Table;

import java.util.List;

@Dao
public interface Seedling_InspectionDao {


    @Insert
    Long insert(SeedlingInspectionTable seedlingInspectionTable);

    @Insert
    void insertList(List<SeedlingInspectionTable> seedlingInspectionTableList);

    @Query("SELECT * FROM seedling_inpection_table ORDER BY production_lot_no DESC")
    List<SeedlingInspectionTable> getAllData();

    @Query("SELECT * FROM seedling_inpection_table WHERE production_lot_no =:production_lot_no")
    List<SeedlingInspectionTable> getInpection1DataByLotNo(String production_lot_no);

    @Update
    void update(SeedlingInspectionTable seedlingInspectionTable);

    @Query("delete from seedling_inpection_table")
    int deleteAllRecord();

    @Query("SELECT COUNT(production_lot_no) FROM seedling_inpection_table")
    int getRowCount();

    @Query("SELECT COUNT(production_lot_no) FROM seedling_inpection_table WHERE production_lot_no = :production_lot_no")
    int isDataExist(String production_lot_no);

    @Query(("SELECT * FROM seedling_inpection_table WHERE production_lot_no=:production_lot"))
    SeedlingInspectionTable getSeedlingCreatedOnByLot(String production_lot);


    @Query("SELECT * FROM seedling_inpection_table WHERE  sync_with_api_ins2=0")
    List<SeedlingInspectionTable> getSavedLineIntoLocal();
}
