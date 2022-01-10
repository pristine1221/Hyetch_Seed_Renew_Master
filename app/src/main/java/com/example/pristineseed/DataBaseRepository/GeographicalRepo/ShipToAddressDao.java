package com.example.pristineseed.DataBaseRepository.GeographicalRepo;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ShipToAddressDao {
    @Insert
    Long insert(ShipToAddressDataTable shipToAddressDataTable);
    @Insert
    void insert(List<ShipToAddressDataTable> shipToAddressDataTableList);

    @Query("SELECT * FROM ship_to_address_table ORDER BY  Customer_No DESC")
    List<ShipToAddressDataTable> fetchAllData();

    @Update
    void update(ShipToAddressDataTable shipToAddressDataTable);

    @Query("delete from ship_to_address_table")
    int deleteAllRecord();

    @Query("SELECT COUNT(1) FROM ship_to_address_table")
    int getRowCount();

    @Query("SELECT * from ship_to_address_table WHERE Customer_No=:customer_no")
    List<ShipToAddressDataTable> fetechDataByRoleNo(String customer_no);


}
