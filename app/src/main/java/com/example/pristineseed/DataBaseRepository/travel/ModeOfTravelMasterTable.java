package com.example.pristineseed.DataBaseRepository.travel;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

import com.example.pristineseed.model.travel.ta_da_model.ModeOfTravelModel;


@Entity(tableName = "mode_of_travel_master_table",primaryKeys = {"id","grade", "cities"})//,primaryKeys = {"grade", "cities"}
public class ModeOfTravelMasterTable {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @ColumnInfo(name = "grade")
    private String grade;
    @NonNull
    @ColumnInfo(name = "cities")
    private String cities;

    @ColumnInfo(name = "travel_mode")
    private String travel_mode;


    @NonNull
    public String getGrade() {
        return grade;
    }

    public void setGrade(@NonNull String grade) {
        this.grade = grade;
    }

    @NonNull
    public String getCities() {
        return cities;
    }

    public void setCities(@NonNull String cities) {
        this.cities = cities;
    }

    public String getTravel_mode() {
        return travel_mode;
    }

    public void setTravel_mode(String travel_mode) {
        this.travel_mode = travel_mode;
    }

    public String getLodging() {
        return lodging;
    }

    public void setLodging(String lodging) {
        this.lodging = lodging;
    }

    public float getDa_half() {
        return da_half;
    }

    public void setDa_half(float da_half) {
        this.da_half = da_half;
    }

    public float getDa_full() {
        return da_full;
    }

    public void setDa_full(float da_full) {
        this.da_full = da_full;
    }

    public float getOpe_max() {
        return ope_max;
    }

    public void setOpe_max(float ope_max) {
        this.ope_max = ope_max;
    }

    @ColumnInfo(name = "lodging")
    private String lodging;

    @ColumnInfo(name = "da_half")
    private float da_half;

    @ColumnInfo(name = "da_full")
    private float da_full;

    @ColumnInfo(name = "ope_max")
    private float ope_max;

  public static ModeOfTravelMasterTable getModeOfTravelData(ModeOfTravelModel modeOfTravelModel){
     ModeOfTravelMasterTable modeOfTravelMasterTable    =new ModeOfTravelMasterTable();

      modeOfTravelMasterTable.id=modeOfTravelModel.id;
      modeOfTravelMasterTable. grade=modeOfTravelModel.grade;
      modeOfTravelMasterTable. cities=modeOfTravelModel.cities;
      modeOfTravelMasterTable. travel_mode=modeOfTravelModel.travel_mode;
      modeOfTravelMasterTable.lodging=modeOfTravelModel.lodging;
      modeOfTravelMasterTable.da_half=modeOfTravelModel.da_half;
      modeOfTravelMasterTable.da_full=modeOfTravelModel.da_full;
      modeOfTravelMasterTable. ope_max=modeOfTravelModel.ope_max;

      return  modeOfTravelMasterTable;
  }

}
