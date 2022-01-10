package com.example.pristineseed.DataBaseRepository.EventManagementTable;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;

@Dao
public interface EventTypeMasterDao {
    @Insert()
    Long insert(EventTypeMasterTable eventTypeMasterTable);
    @Insert
    void insert(List<EventTypeMasterTable> eventTypeMasterTableList );


    @Query("SELECT * FROM event_type_master_table ORDER BY id DESC")
    List<EventTypeMasterTable> fetchAllData();


    @Update
    void update(EventTypeMasterTable eventTypeMasterTable);


    @Query("delete from event_type_master_table")
    int deleteAllRecord();

    @Query("SELECT * FROM event_type_master_table WHERE id=:id")
    boolean isDataExist(String id);

    @Query("SELECT * FROM event_type_master_table WHERE parent_id='0'")
    List<EventTypeMasterTable> fetchParent();

    @Query("SELECT * FROM event_type_master_table WHERE parent_id=:parent_id")
    List<EventTypeMasterTable> fetchChield(int parent_id);

    @Query("SELECT *FROM event_type_master_table WHERE event_type=:event_type")
    int getParentId(String event_type);

    @Query("SELECT * FROM event_type_master_table WHERE id=:id")
    List<EventTypeMasterTable>  fetchNameById(String id);

  //  @Query("SELECT ()")
}
