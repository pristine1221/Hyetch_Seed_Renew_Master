package com.example.pristineseed.DataBaseRepository.GeographicalRepo;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "crop_hytech_master_table")
public class CropHytechMasterTable {


    @PrimaryKey(autoGenerate = true)
    private int android_id;

    public int getAndroid_id() {
        return android_id;
    }

    public void setAndroid_id(int android_id) {
        this.android_id = android_id;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @ColumnInfo(name = "Code")
    public String Code;
    @ColumnInfo(name = "Description")
    public String Description;


}
