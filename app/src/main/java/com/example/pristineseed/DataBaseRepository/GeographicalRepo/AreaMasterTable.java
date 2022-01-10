package com.example.pristineseed.DataBaseRepository.GeographicalRepo;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "area_master_table")
public class AreaMasterTable {
   /* @NonNull
    @PrimaryKey(autoGenerate = true)*/
    private int android_id;

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "area_code")
   private String area_code;

    public int getAndroid_id() {
        return android_id;
    }

    public void setAndroid_id(int android_id) {
        this.android_id = android_id;
    }

    public String getArea_code() {
        return area_code;
    }

    public void setArea_code(String area_code) {
        this.area_code = area_code;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String getUpdated_on() {
        return updated_on;
    }

    public void setUpdated_on(String updated_on) {
        this.updated_on = updated_on;
    }

    @ColumnInfo(name = "area_name")
   private String area_name;
    @ColumnInfo(name = "updated_on")
   private String updated_on;

}
