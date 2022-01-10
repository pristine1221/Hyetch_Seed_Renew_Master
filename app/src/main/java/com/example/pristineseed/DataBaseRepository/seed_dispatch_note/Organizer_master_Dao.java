package com.example.pristineseed.DataBaseRepository.seed_dispatch_note;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface Organizer_master_Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Organizer_master_Table organizer_master_table);
    @Insert
    void insertList(List<Organizer_master_Table> organizer_master_tableList);

    @Query("SELECT * FROM organizer_master_table ORDER BY code DESC")
    List<Organizer_master_Table> getAllData();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Organizer_master_Table organizer_master_table);

    @Query("delete from organizer_master_table")
    int deleteAllRecord();

    @Query("SELECT COUNT(1) FROM organizer_master_table")
    int getRowCount();

    @Query("SELECT COUNT(code) FROM organizer_master_table WHERE lower(code)= :code")
    int     isDataExist(String code);

    @Query("SELECT * FROM organizer_master_table WHERE Vendor_Classification= :Vendor_Classification OR  Vendor_Classification=:Vendor_Classfi")
    List<Organizer_master_Table> getVendorClasification(String Vendor_Classification,String Vendor_Classfi);

}
