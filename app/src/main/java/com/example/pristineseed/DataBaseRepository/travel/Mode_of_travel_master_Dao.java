package com.example.pristineseed.DataBaseRepository.travel;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface Mode_of_travel_master_Dao {

    @Insert
    Long insert(ModeOfTravelMasterTable modeOfTravelMasterTable);
    @Insert
    void insert(List<ModeOfTravelMasterTable> modeOfTravelMasterTables);

  /*  @Query("SELECT * FROM travel_header_table WHERE   " )
    List<TravelHeaderTable> getAllData();
*/

    @Update
    void update(ModeOfTravelMasterTable modeOfTravelMasterTables);

    @Query("SELECT * FROM mode_of_travel_master_table WHERE id =:id and  grade = :grade and cities=:cities")
    boolean isDataExist(int id , String grade, String cities);

    @Query("delete from mode_of_travel_master_table")
    int deleteAllRecord();

    @Query("SELECT COUNT(travelcode) FROM travel_header_table")
    int getRowCount();

    @Query("SELECT  DISTINCT  mot.travel_mode  FROM mode_of_travel_master_table mot WHERE mot.grade=:userGrade OR mot.id>(SELECT mot2.id FROM mode_of_travel_master_table mot2 WHERE mot2.grade=:userGrade LIMIT 1)")
    List<String> getModeOfTravelDropDown(String userGrade);



    @Query("SELECT * FROM mode_of_travel_master_table WHERE grade=:grade AND cities=(SELECT dm.class_of_city FROM distric_master_table dm WHERE code=:cityCode) LIMIT 1")
    ModeOfTravelMasterTable fetchModeOfTravel(String cityCode, String grade);



    @Query("SELECT * FROM mode_of_travel_master_table WHERE grade=:grade AND cities=:cities LIMIT 1")
    ModeOfTravelMasterTable fetchModeOfTravelNew(String cities, String grade);

}
