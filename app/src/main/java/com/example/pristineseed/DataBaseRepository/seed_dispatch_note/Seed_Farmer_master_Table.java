package com.example.pristineseed.DataBaseRepository.seed_dispatch_note;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.pristineseed.model.GeoSetupModel.DispatchFarmerModel;

@Entity(tableName = "seed_farmer_master_table")
public class Seed_Farmer_master_Table {

    @NonNull
    public String getNo() {
        return No;
    }

    public void setNo(@NonNull String no) {
        No = no;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "code")
    private String No;

    @ColumnInfo(name = "name")
   private String Name;


    @ColumnInfo(name = "Name_2")
   private String Name_2;

    @ColumnInfo(name = "Address")
   private String Address;

    @ColumnInfo(name = "Address_2")
   private String Address_2;

    @ColumnInfo(name = "City")
   private String City;

    @ColumnInfo(name = "Region_Name")
   private String Region_Name;

    @ColumnInfo(name = "Zone_Name")
   private String Zone_Name;

    public String getName_2() {
        return Name_2;
    }

    public void setName_2(String name_2) {
        Name_2 = name_2;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getAddress_2() {
        return Address_2;
    }

    public void setAddress_2(String address_2) {
        Address_2 = address_2;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getRegion_Name() {
        return Region_Name;
    }

    public void setRegion_Name(String region_Name) {
        Region_Name = region_Name;
    }

    public String getZone_Name() {
        return Zone_Name;
    }

    public void setZone_Name(String zone_Name) {
        Zone_Name = zone_Name;
    }

    public String getState_Name() {
        return State_Name;
    }

    public void setState_Name(String state_Name) {
        State_Name = state_Name;
    }

    public String getTerritory_Name() {
        return Territory_Name;
    }

    public void setTerritory_Name(String territory_Name) {
        Territory_Name = territory_Name;
    }

    public String getDistrict_Name() {
        return District_Name;
    }

    public void setDistrict_Name(String district_Name) {
        District_Name = district_Name;
    }

    public String getState_Code() {
        return State_Code;
    }

    public void setState_Code(String state_Code) {
        State_Code = state_Code;
    }

    @ColumnInfo(name = "State_Name")
   private String State_Name;

    @ColumnInfo(name = "Territory_Name")
   private String Territory_Name;

    @ColumnInfo(name = "District_Name")
   private String District_Name;

    @ColumnInfo(name = "State_Code")
   private String State_Code;

    public static Seed_Farmer_master_Table insertFarmerMasterTable(DispatchFarmerModel.Data data){

Seed_Farmer_master_Table farmer_master_table=new Seed_Farmer_master_Table();
farmer_master_table. No=  data.No;
farmer_master_table. Name= data.Name;
farmer_master_table. Name_2= data.Name_2;
farmer_master_table. Address= data.Address;
farmer_master_table. Address_2=data.Address_2;
farmer_master_table. City= data.City;
farmer_master_table. Region_Name=data.Region_Name;
farmer_master_table. Zone_Name= data.Zone_Name;
farmer_master_table. State_Name= data.State_Name;
farmer_master_table. Territory_Name= data.Territory_Name;
farmer_master_table. District_Name= data.District_Name;

return farmer_master_table;
    }

}
