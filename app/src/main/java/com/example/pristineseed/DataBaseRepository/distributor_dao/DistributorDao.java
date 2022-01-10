package com.example.pristineseed.DataBaseRepository.distributor_dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.pristineseed.DataBaseRepository.distribution_master_table.Distribution_master_table;

import java.util.List;

@Dao
public interface DistributorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Distribution_master_table distribution_master_table);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Distribution_master_table> distribution_master_tableList);

    @Query("SELECT * FROM distribution_master_table ORDER BY distributor_code DESC")
    List<Distribution_master_table> getAllData();

    @Query("SELECT * FROM distribution_master_table WHERE android_id =:android_id")
    Distribution_master_table getAllCollectionData(int  android_id);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Distribution_master_table districMasterTable);

    @Query("delete from distribution_master_table")
    int deleteAllRecord();

    @Query("SELECT COUNT(android_id) FROM distribution_master_table")
    int getRowCount();

    @Query("SELECT COUNT(distributor_code) FROM distribution_master_table WHERE distributor_code=:distributor_code")
    int count(String distributor_code);

    @Query("SELECT * FROM distribution_master_table WHERE distributor_code=:distributor_code")
    boolean isDataExist(String distributor_code);

    @Query("delete from distribution_master_table WHERE distributor_code=:distributor_code")
    int deleteFromLocal(String distributor_code);
}
