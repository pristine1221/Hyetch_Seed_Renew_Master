package com.example.pristineseed.DataBaseRepository.travel;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.pristineseed.model.travel.ta_da_model.CityMasterModel;

@Entity(tableName = "city_master_table")
public class CityMasterTable {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int android_id;

    @ColumnInfo(name = "code")
    private String code;

    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "country_region_code")
    private String country_region_code;

    public int getAndroid_id() {
        return android_id;
    }

    public void setAndroid_id(int android_id) {
        this.android_id = android_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry_region_code() {
        return country_region_code;
    }

    public void setCountry_region_code(String country_region_code) {
        this.country_region_code = country_region_code;
    }

    public String getClass_of_city() {
        return class_of_city;
    }

    public void setClass_of_city(String class_of_city) {
        this.class_of_city = class_of_city;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    @ColumnInfo(name = "class_of_city")
    private String class_of_city;
    @ColumnInfo(name = "active")
    private int active;



    public static CityMasterTable getCityMastertTableData(CityMasterModel cityMasterModel){

        CityMasterTable cityMasterTable=new CityMasterTable();
        cityMasterTable.code=cityMasterModel.code;
        cityMasterTable.name=cityMasterModel.name;
        cityMasterTable.country_region_code=cityMasterModel.country_region_code;
        cityMasterTable.class_of_city=cityMasterModel.class_of_city;
        cityMasterTable.active=cityMasterModel.active;

               return  cityMasterTable;

    }
}
