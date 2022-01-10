package com.example.pristineseed.DataBaseRepository.GeographicalRepo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "taluka_master_table")
public class TalukaMasterTable {
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "code")
    private String  code;
    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "updated_on")
    private String updated_on;
    @ColumnInfo(name = "active")
    private String active;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
