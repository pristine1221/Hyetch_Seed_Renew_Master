package com.example.pristineseed.DataBaseRepository.GeographicalRepo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "user_location_master_table",primaryKeys = {"email_id","location_code"})
public class LocationMasterTable {

            @NonNull
            @ColumnInfo(name = "email_id")
            private String email_id;

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public String getLocation_code() {
        return location_code;
    }

    public void setLocation_code(String location_code) {
        this.location_code = location_code;
    }

    @ColumnInfo(name = "location_name")
    private String location_name;
    @NonNull
    @ColumnInfo(name = "location_code")
    private String location_code;
}
