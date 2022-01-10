package com.example.pristineseed.DataBaseRepository.GeographicalRepo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "geographic_setup_table")
public class GeoghraphicalTable {
    @NonNull
    @PrimaryKey()
    @ColumnInfo(name = "id")
    private long id;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getTaluka() {
        return taluka;
    }

    public void setTaluka(String taluka) {
        this.taluka = taluka;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getUpdated_on() {
        return updated_on;
    }

    public void setUpdated_on(String updated_on) {
        this.updated_on = updated_on;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    @ColumnInfo(name="zone")
    private String zone;
    @ColumnInfo(name="state")
    private String state;

    @ColumnInfo(name="region")
    private String region;

    @ColumnInfo(name="district")
    private String district;

    @ColumnInfo(name="taluka")
    private String taluka;

    @ColumnInfo(name = "managers")
    private String manager;

    @ColumnInfo(name = "updated_on")
    private String updated_on;

    @ColumnInfo(name = "active")
    private int active;

}
