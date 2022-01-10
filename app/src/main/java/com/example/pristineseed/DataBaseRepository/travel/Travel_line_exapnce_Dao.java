package com.example.pristineseed.DataBaseRepository.travel;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface Travel_line_exapnce_Dao {

    @Insert
    Long insert(TravelLineExpenseTable travelLineExpenseTable);

    @Insert
    void insert(List<TravelLineExpenseTable> travelLineExpenseTableList);

    @Query("SELECT * FROM travel_line_expance_table WHERE travelcode=0")
    List<TravelLineExpenseTable> getAllData();


    @Update
    void update(TravelLineExpenseTable travelLineExpenseTable);

    @Query("delete from travel_line_expance_table")
    int deleteAllRecord();

    @Query("SELECT COUNT(travelcode) FROM travel_line_expance_table")
    int getRowCount();

    @Query("SELECT * FROM travel_line_expance_table WHERE travelcode=:travelcode")
    List<TravelLineExpenseTable> fetch(String travelcode);

  @Query("SELECT * FROM travel_line_expance_table WHERE lineSend='LOCAL'")
  List<TravelLineExpenseTable> fetechUnsendLine();

  @Query("update travel_line_expance_table set created_on=:created_on and lineSend=:lineSend where travelcode=:travel_code and line_no=:line_no")
  int updateAllExpenseLines(String created_on, String lineSend, String travel_code , String line_no) ;


  @Query("delete from travel_line_expance_table where travelcode=:travelcode AND android_travelcode=:android_travelcode AND line_no=:line_no")
  int delete(String travelcode, int android_travelcode, String line_no);

    @Query("SELECT * FROM travel_line_expance_table WHERE travelcode=:travelcode AND android_travelcode=:android_travelcode")
    List<TravelLineExpenseTable> fetchAfterLineDelete(String travelcode , int android_travelcode);
}
