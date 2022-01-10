package com.example.pristineseed.DataBaseRepository.EventManagementTable;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;

@Dao
public interface EventManagmentDao {

    @Insert()
    Long insert(EventManagemantTable eventManagemantTable);
    @Insert
    void insert(List<EventManagemantTable> eventManagemantTableList );


    @Query("SELECT * FROM event_management_table ORDER BY event_code DESC")
    List<EventManagemantTable> fetchAllData();


    @Update
    void update(EventManagemantTable eventManagemantTable);


    @Query("delete from event_management_table")
    int deleteAllRecord();

    @Query("SELECT * FROM event_management_table WHERE event_code=:id")
    boolean isDataExist(String id);


    @Query("update event_management_table set status=:status WHERE event_code=:event_code")
    int update_EventStatus(String event_code, String status);


    @Query("update event_management_table set status=:status,reject_reason=:reject_reason,approve_on=:approve_on WHERE event_code=:event_code")
    int update_EventStatusOnViewDetail(String event_code, String status, String reject_reason, String approve_on);


    @Query("SELECT * FROM event_expence_line_table WHERE event_code='0'")
    List<EventManagemantTable> fetchUnsendData();


    @Query("update event_management_table set event_code=:event_code AND created_on=:created_on where android_event_code=:android_event_code ")
   int  updateSyncData(int android_event_code, String event_code, String created_on);


    @Query("update event_management_table set actual_farmers=:actual_farmers ,actual_dealers=:actual_dealers,actual_distributers=:actual_distributers WHERE android_event_code=:android_event_code and event_code=:event_code")
    int updateFarmaerDealerDistributers(int android_event_code, String event_code, String actual_farmers,String actual_distributers,String actual_dealers);
}
