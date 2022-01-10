package com.example.pristineseed.DataBaseRepository.GeographicalRepo;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.pristineseed.model.RoleMasterModelNew;

import java.util.List;

@Dao
public interface RoleMasterDao {

    @Insert
    Long insert(RoleMasterTable roleMasterTable);
    @Insert
    void insert(List<RoleMasterTable> roleMasterTableList);


    @Query("SELECT * FROM role_maaster_table")
    List<RoleMasterTable> fetchAllData();

    @Query("SELECT DISTINCT  customer_Type FROM role_maaster_table WHERE  customer_Type not null AND customer_Type not in ('',' ')")
    List<CustomerTypeModel> fetchAllDataByCustType();

    @Update
    void update(RoleMasterTable roleMasterTable);

    @Query("delete from role_maaster_table")
    int deleteAllRecord();

    @Query("SELECT COUNT(1) FROM role_maaster_table")
    int getRowCount();

    @Query("SELECT * from role_maaster_table WHERE customer_Type=:role_type and customer_Type not null")
    List<RoleMasterTable> fetechDataByRole(String role_type);

    @Query("SELECT * from role_maaster_table WHERE `no`=:role_code and customer_Type not null")
    RoleMasterTable getRoleNameByCode(String role_code);

    @Query("SELECT * from role_maaster_table WHERE customer_Type=:role_type OR customer_Type=:role_type2 and customer_Type not null")
    List<RoleMasterTable> fetechDataByBothRole(String role_type,String role_type2);

    @Query("SELECT * from role_maaster_table WHERE `no`=:role_code")
    RoleMasterTable getRoleBycode(String role_code);

    @Query("SELECT `no`,name from role_maaster_table WHERE `no`=:role_code")
    RoleMasterModelNew getRoleBycodeNew(String role_code);

    @Query("SELECT COUNT(1) FROM role_maaster_table where customer_Type=:customer_type and customer_Type not null and customer_Type not in('',' ')")
    int getRowCountRoleType(String customer_type);
}
