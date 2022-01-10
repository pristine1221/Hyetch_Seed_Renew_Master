package com.example.pristineseed.DataBaseRepository.GeographicalRepo;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UOMDao {

    @Insert
    Long insert(UomTable uomTable);

    @Insert
    void insert(List<UomTable> uomTablesList);

    @Query("delete from uom_table")
    int deleteAllRecord();

    @Query("SELECT COUNT(1) FROM uom_table")
    int getRowCount();


    @Query("SELECT * FROM uom_table")
    List<UomTable> fetchAllData();
}
