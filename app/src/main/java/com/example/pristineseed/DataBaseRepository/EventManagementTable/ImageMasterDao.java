package com.example.pristineseed.DataBaseRepository.EventManagementTable;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;

@Dao
public interface ImageMasterDao {
    @Insert()
    Long insert(ImageMasterTable imageMasterTable);
    @Insert
    void insert(List<ImageMasterTable> imageMasterTableList );


    @Query("SELECT * FROM event_image_master_tabe ORDER BY id DESC")
    List<ImageMasterTable> fetchAllData();


    @Update
    void update(ImageMasterTable imageMasterTable);


    @Query("delete from event_image_master_tabe")
    int deleteAllRecord();

    @Query("SELECT * FROM event_image_master_tabe WHERE id=:id")
    boolean isDataExist(String id);

    @Query("SELECT * FROM event_image_master_tabe WHERE code=:code")
    List<ImageMasterTable> fetechImagUrl(String code);



    @Query("delete FROM event_image_master_tabe WHERE code=:code")
     int deleteEventCode(String code);

}
