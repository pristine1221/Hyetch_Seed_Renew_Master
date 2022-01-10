package com.example.pristineseed.DataBaseRepository.GeographicalRepo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "distric_master_table")
public class DistricMasterTable {

    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @PrimaryKey()
    @ColumnInfo(name = "code")
    private String code;

    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "updated_on")
    private String updated_on;
    @ColumnInfo(name = "active")
    private String active;

    public String getClass_of_city() {
        return class_of_city;
    }

    public void setClass_of_city(String class_of_city) {
        this.class_of_city = class_of_city;
    }

    @ColumnInfo(name = "class_of_city")
    private String class_of_city;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode( String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpdated_on() {
        return updated_on;
    }

    public void setUpdated_on(String updated_on) {
        this.updated_on = updated_on;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

}
