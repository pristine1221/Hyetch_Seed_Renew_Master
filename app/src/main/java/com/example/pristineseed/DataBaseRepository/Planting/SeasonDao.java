package com.example.pristineseed.DataBaseRepository.Planting;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SeasonDao {

    @Insert
    Long insert(SeasonMasterTable plantingDetailHeaderTable);
    @Insert
    void insertList(List<SeasonMasterTable> plantingDetailHeaderTableList);

    @Query("SELECT * FROM season_master_table ORDER BY Season_Code DESC")
    List<SeasonMasterTable> getAllData();

    @Query("SELECT * FROM season_master_table WHERE Season_Code =:code")
    SeasonMasterTable getSeasonCode(String code);

    @Update
    void update(SeasonMasterTable scheduler_header_table);

    @Query("delete from season_master_table")
    int deleteAllRecord();

    @Query("SELECT COUNT(Season_Code) FROM season_master_table")
    int getRowCount();

    @Query("SELECT COUNT(Season_Code) FROM season_master_table WHERE lower(Season_Code) =:code")
    int isDataExist(String code);
}
