package com.example.pristineseed.sql_lite_process.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.pristineseed.sql_lite_process.model.ExceptionTableModel;

import java.util.List;

@Dao
public interface ExceptionDao {
    @Insert
    long insert(ExceptionTableModel data);

    @Query("SELECT * FROM exception_mst ORDER BY id DESC")
    List<ExceptionTableModel> getAllException();

    @Query("delete from exception_mst")
    int deleteAllRecord();
}
