package com.example.pristineseed.DataBaseRepository.GeographicalRepo;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface GeographicDao {

    @Insert()
    Long insert(GeoghraphicalTable geoghraphicalTable);

    @Insert()
    void insert(List<GeoghraphicalTable> geoghraphicalTable);

    @Query("SELECT * FROM geographic_setup_table ORDER BY id DESC")
    List<GeoghraphicalTable> getAllData();

    @Query("SELECT * FROM geographic_setup_table WHERE id =:id")
    GeoghraphicalTable getGeoId(String id);

    @Update
    void update(GeoghraphicalTable geoghraphicalTable);


    @Query("delete from geographic_setup_table")
    int deleteAllRecord();

    @Query("SELECT COUNT(id) FROM geographic_setup_table")
    int getRowCount();


    @Query("SELECT * FROM geographic_setup_table WHERE id=:id")
    boolean isDataExist(String id);

}
