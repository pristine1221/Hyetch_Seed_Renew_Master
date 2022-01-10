package com.example.pristineseed.DataBaseRepository.EventManagementTable;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EventManagmentExpanceLineDao {

    @Insert()
    Long insert(EventManagementExpenseLineTable eventManagementExpenseLineTable);
    @Insert
    void insert(List<EventManagementExpenseLineTable> eventManagementExpenseLineTableList );

    @Query("SELECT * FROM event_expence_line_table ORDER BY android_event_code DESC")
    List<EventManagementExpenseLineTable> fetchAllData();

    @Update
    void update(EventManagementExpenseLineTable eventManagementExpenseLineTable);

    @Query("delete from event_expence_line_table")
    int deleteAllRecord();

    @Query("SELECT * FROM event_expence_line_table WHERE event_code=:event_code")
    boolean isDataExist(String event_code);

    @Query("SELECT * FROM event_expence_line_table WHERE event_code=:event_code")
    List<EventManagementExpenseLineTable> fetch(String event_code);
}
