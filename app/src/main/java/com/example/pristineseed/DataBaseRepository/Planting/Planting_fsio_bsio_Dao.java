package com.example.pristineseed.DataBaseRepository.Planting;

import android.view.textclassifier.SelectionEvent;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface Planting_fsio_bsio_Dao {

    @Insert
    Long insert(PlantingFSIO_BSIO_Table plantingFSIO_bsio_table);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertList(List<PlantingFSIO_BSIO_Table> plantingFSIO_bsio_tableList);

    @Query("SELECT * FROM planting_fsio_bsio_table ORDER BY code DESC")
    List<PlantingFSIO_BSIO_Table> getAllData();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(PlantingFSIO_BSIO_Table plantingFSIO_bsio_table);

    @Query("delete from planting_fsio_bsio_table")
    int deleteAllRecord();

    @Query("SELECT COUNT(1) FROM planting_fsio_bsio_table")
    int getRowCount();

    @Query("SELECT 1 FROM planting_fsio_bsio_table WHERE code = :no")
    boolean isDataExist(String no);


    @Query("SELECT * FROM planting_fsio_bsio_table WHERE Document_SubType=:Document_SubType and organizer_code=:organizer_code")
    List<PlantingFSIO_BSIO_Table> getPlantingBsioFsioList(String Document_SubType,String organizer_code);

    @Query("SELECT * FROM planting_fsio_bsio_table WHERE Document_SubType=:doc_sub_type")
    List<PlantingFSIO_BSIO_Table> getDocNo(String doc_sub_type);

}
