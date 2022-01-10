package com.example.pristineseed.DataBaseRepository.travel;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TravelViewHeaderDao {

    @Insert
    Long insert(TravelHeaderTable travelHeaderTable);
    @Insert
    void insert(List<TravelHeaderTable> travelHeaderTable);


    @Update
    void update(TravelHeaderTable travelHeaderTable);

    @Query("SELECT * FROM travel_header_table WHERE travelcode = :travel_code and created_on=:createOn and travelcode is not null")
    boolean isDataExist(String travel_code,String createOn);

    @Query("update travel_header_table SET travelcode=:travelcode,created_on=:created_on where android_travelcode=:android_event_code")
    int updateSync( String travelcode,String created_on,int android_event_code);

    @Query("delete from travel_header_table")
    int deleteAllRecord();


    @Query("SELECT COUNT(travelcode) FROM travel_header_table")
    int getRowCount();


    @Query("update travel_header_table SET status=:status,approve_on=:approve_on where travelcode=:travelcode")
    int updateStatus(String travelcode,String status,String approve_on);

   /* @Query("SELECT * FROM travel_header_table WHERE travelcode is null")
    List<TravelHeaderTable>fetechUnsendData();*/

    @Query("SELECT * FROM travel_header_table")
    List<TravelHeaderTable> fetechTravelHeaderData();


    @Query("update travel_header_table SET status=:status,reason=:reason,approve_budget=:approve_budget,approve_on=:approve_on  where travelcode=:travelcode")
    int update_travelStatus(String travelcode,String status,String reason,String approve_budget,String approve_on);


    @Query("SELECT * from travel_header_table WHERE travelcode is null")
    List<TravelHeaderTable> fetechUnsend();

}
