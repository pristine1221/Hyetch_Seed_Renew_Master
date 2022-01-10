package com.example.pristineseed.DataSyncingBackgraundProcess;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SyncMasterDao {

    @Insert
    Long insert(SyncMasterTable syncMasterTable);

    @Insert
    void insert(List<SyncMasterTable> syncMasterTablesList);

    @Query("SELECT * FROM syncMatserDataTable ORDER BY all_table_name ")
    List<SyncMasterTable> fetechAllTableRecord();

    @Update
    void update(SyncMasterTable syncMasterTable);

    @Query("delete from syncMatserDataTable")
    int deleteAllRecord();

    @Query("SELECT COUNT(all_table_name) FROM syncMatserDataTable")
    int getRowCount();

    @Query("SELECT * FROM syncMatserDataTable WHERE all_table_name=:all_table_name")
    boolean isDataExist(String all_table_name);

    @Query("update syncMatserDataTable SET inReady=:inReady AND initLastDateTime=:inLastDateTime where All_table_name=:All_table_name")
    int updateComplete(String All_table_name, int inReady, String inLastDateTime);
}
