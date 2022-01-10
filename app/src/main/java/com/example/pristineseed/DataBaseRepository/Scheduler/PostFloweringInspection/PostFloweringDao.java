package com.example.pristineseed.DataBaseRepository.Scheduler.PostFloweringInspection;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.pristineseed.DataBaseRepository.Scheduler.FloweringInspectionTable.FloweringInspectionTable;

import java.util.List;

@Dao
public interface PostFloweringDao {

    @Insert
    Long insert(PostfloweringInspectionTable postfloweringInspectionTable);

    @Insert
    void insertList(List<PostfloweringInspectionTable> postfloweringInspectionTableList);

    @Query("SELECT * FROM post_flowering_inspection_table ORDER BY production_lot_no DESC")
    List<PostfloweringInspectionTable> getAllData();

    @Query("SELECT * FROM post_flowering_inspection_table WHERE production_lot_no =:production_lot_no")
    List<PostfloweringInspectionTable> getInpection1DataByLotNo(String production_lot_no);

    @Update
    void update(PostfloweringInspectionTable floweringInspectionTable);

    @Query("delete from post_flowering_inspection_table")
    int deleteAllRecord();

    @Query("SELECT COUNT(production_lot_no) FROM post_flowering_inspection_table")
    int getRowCount();

    @Query("SELECT COUNT(production_lot_no) FROM post_flowering_inspection_table WHERE production_lot_no = :production_lot_no")
    int isDataExist(String production_lot_no);

    @Query("SELECT * FROM post_flowering_inspection_table WHERE production_lot_no=:production_lot_no")
    PostfloweringInspectionTable getPostFloweringInspectionCreatedOn(String production_lot_no);


    @Query("SELECT * FROM post_flowering_inspection_table WHERE  synWithApi7=0")
    List<PostfloweringInspectionTable> getSavedLineIntoLocal();
}
