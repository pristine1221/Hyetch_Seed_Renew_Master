package com.example.pristineseed.DataBaseRepository.GeographicalRepo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.pristineseed.model.item.RoleMasterModel;

@Entity(tableName = "role_maaster_table")
public class RoleMasterTable {
   // @PrimaryKey(autoGenerate = true)
    private int android_id;

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "no")
    private String no;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "name_2")
    private String name_2;
    @ColumnInfo(name = "contact")
    private String contact;
    @ColumnInfo(name = "phone_No")
    private String phone_No;
    @ColumnInfo(name = "telex_No")
    private String telex_No;
    @ColumnInfo(name = "fax_No")
    private String fax_No;
    @ColumnInfo(name = "address")
    private String address;
    @ColumnInfo(name = "address_2")
    private String address_2;
    @ColumnInfo(name = "post_Code")
    private String post_Code;
    @ColumnInfo(name = "city")
    private String city;
    @ColumnInfo(name = "region")
    private String region;
    @ColumnInfo(name = "zone")
    private String zone;
    @ColumnInfo(name = "state_Code")
    private String state_Code;
    @ColumnInfo(name = "area")
    private String area;

    public int getAndroid_id() {
        return android_id;
    }

    public void setAndroid_id(int android_id) {
        this.android_id = android_id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_2() {
        return name_2;
    }

    public void setName_2(String name_2) {
        this.name_2 = name_2;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhone_No() {
        return phone_No;
    }

    public void setPhone_No(String phone_No) {
        this.phone_No = phone_No;
    }

    public String getTelex_No() {
        return telex_No;
    }

    public void setTelex_No(String telex_No) {
        this.telex_No = telex_No;
    }

    public String getFax_No() {
        return fax_No;
    }

    public void setFax_No(String fax_No) {
        this.fax_No = fax_No;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress_2() {
        return address_2;
    }

    public void setAddress_2(String address_2) {
        this.address_2 = address_2;
    }

    public String getPost_Code() {
        return post_Code;
    }

    public void setPost_Code(String post_Code) {
        this.post_Code = post_Code;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getState_Code() {
        return state_Code;
    }

    public void setState_Code(String state_Code) {
        this.state_Code = state_Code;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getTerritory() {
        return territory;
    }

    public void setTerritory(String territory) {
        this.territory = territory;
    }

    public String getBussiness_Type() {
        return bussiness_Type;
    }

    public void setBussiness_Type(String bussiness_Type) {
        this.bussiness_Type = bussiness_Type;
    }

    public String getCustomer_Type() {
        return customer_Type;
    }

    public void setCustomer_Type(String customer_Type) {
        this.customer_Type = customer_Type;
    }

    public String getGsT_Registration_No() {
        return gsT_Registration_No;
    }

    public void setGsT_Registration_No(String gsT_Registration_No) {
        this.gsT_Registration_No = gsT_Registration_No;
    }

    public String getGsT_Registration_Type() {
        return gsT_Registration_Type;
    }

    public void setGsT_Registration_Type(String gsT_Registration_Type) {
        this.gsT_Registration_Type = gsT_Registration_Type;
    }

    public String getGsT_Customer_Type() {
        return gsT_Customer_Type;
    }

    public void setGsT_Customer_Type(String gsT_Customer_Type) {
        this.gsT_Customer_Type = gsT_Customer_Type;
    }

    @ColumnInfo(name = "territory")
    private String territory;
    @ColumnInfo(name = "bussiness_Type")
    private String bussiness_Type;
    @ColumnInfo(name = "customer_Type")
    private String customer_Type;
    @ColumnInfo(name = "gsT_Registration_No")
    private String gsT_Registration_No;
    @ColumnInfo(name = "gsT_Registration_Type")
    private String gsT_Registration_Type;
    @ColumnInfo(name = "gsT_Customer_Type")
    private String gsT_Customer_Type;

    @ColumnInfo(name = "region_Name")
  private String region_Name;
    @ColumnInfo(name = "zone_Name")
  private String zone_Name;

    public String getRegion_Name() {
        return region_Name;
    }

    public void setRegion_Name(String region_Name) {
        this.region_Name = region_Name;
    }

    public String getZone_Name() {
        return zone_Name;
    }

    public void setZone_Name(String zone_Name) {
        this.zone_Name = zone_Name;
    }

    public String getState_Name() {
        return state_Name;
    }

    public void setState_Name(String state_Name) {
        this.state_Name = state_Name;
    }

    public String getArea_Name() {
        return area_Name;
    }

    public void setArea_Name(String area_Name) {
        this.area_Name = area_Name;
    }

    public String getDistrict_Name() {
        return district_Name;
    }

    public void setDistrict_Name(String district_Name) {
        this.district_Name = district_Name;
    }

    public String getTerritory_Name() {
        return territory_Name;
    }

    public void setTerritory_Name(String territory_Name) {
        this.territory_Name = territory_Name;
    }

    @ColumnInfo(name = "state_Name")
  private String state_Name;
    @ColumnInfo(name = "area_Name")
  private String area_Name;
    @ColumnInfo(name = "district_Name")
  private String district_Name;
    @ColumnInfo(name = "territory_Name")
  private String territory_Name;


    public static RoleMasterTable insertRoleMasterData(RoleMasterModel.Data roleMasterModel) {
        RoleMasterTable roleMasterTable = new RoleMasterTable();

        roleMasterTable.no = roleMasterModel.no;
        roleMasterTable.name = roleMasterModel.name;
        roleMasterTable.name_2 = roleMasterModel.name_2;
        roleMasterTable.contact = roleMasterModel.contact;
        roleMasterTable.phone_No = roleMasterModel.phone_No;
        roleMasterTable.telex_No = roleMasterModel.telex_No;
        roleMasterTable.fax_No = roleMasterModel.fax_No;
        roleMasterTable.address = roleMasterModel.address;
        roleMasterTable.address_2 = roleMasterModel.address_2;
        roleMasterTable.post_Code = roleMasterModel.post_Code;
        roleMasterTable.city = roleMasterModel.city;
        roleMasterTable.region = roleMasterModel.region;
        roleMasterTable.zone = roleMasterModel.zone;
        roleMasterTable.state_Code = roleMasterModel.state_Code;
        roleMasterTable.area = roleMasterModel.area;
        roleMasterTable.territory = roleMasterModel.territory;
        roleMasterTable.bussiness_Type = roleMasterModel.bussiness_Type;
        roleMasterTable.customer_Type = roleMasterModel.customer_Type;
        roleMasterTable.gsT_Registration_No = roleMasterModel.gsT_Registration_No;
        roleMasterTable.gsT_Registration_Type = roleMasterModel.gsT_Registration_Type;
        roleMasterTable.gsT_Customer_Type = roleMasterModel.gsT_Customer_Type;
        roleMasterTable.region_Name   =roleMasterModel.region_Name;
        roleMasterTable.zone_Name   =roleMasterModel.zone_Name;
        roleMasterTable.state_Name   =roleMasterModel.state_Name;
        roleMasterTable.area_Name   =roleMasterModel.area_Name;
        roleMasterTable.district_Name   =roleMasterModel.district_Name;
        roleMasterTable.territory_Name   =roleMasterModel.territory_Name;

        return roleMasterTable;

    }


}
