package com.example.pristineseed.DataBaseRepository.Scheduler.FloweringInspectionTable;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.pristineseed.model.scheduler_inspection.FloweringInspectionModel;

@Entity(tableName = "flowering_inspection_table")
public class FloweringInspectionTable {
    private int android_id;

    @NonNull
    @ColumnInfo(name = "scheduler_no")
    private String scheduler_no;
    @NonNull
    @ColumnInfo(name = "arrival_plan_no")
    private String arrival_plan_no;

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "production_lot_no")
    private String production_lot_no;
    @ColumnInfo(name = "crop_condition")
    private String crop_condition;
    @ColumnInfo(name = "crop_stage")
    private String crop_stage;
    @ColumnInfo(name = "date_of_inspection")
    private String date_of_inspection;
    @ColumnInfo(name = "pollen_shedders")
    private String pollen_shedders;
    @ColumnInfo(name = "other_types")
    private String other_types;
    @ColumnInfo(name = "pollen_shedding_plants")
    private String pollen_shedding_plants;
    @ColumnInfo(name = "pollen_shedding_plants_per")
    private String pollen_shedding_plants_per;
    @ColumnInfo(name = "pest")
    private String pest;
    @ColumnInfo(name = "diseases")
    private String diseases;
    @ColumnInfo(name = "pest_remarks")
    private String pest_remarks;
    @ColumnInfo(name = "diseases_remarks")
    private String diseases_remarks;
    @ColumnInfo(name = "recommended_date")
    private String recommended_date;
    @ColumnInfo(name = "actual_date")
    private String actual_date;
    @ColumnInfo(name = "isolation")
    private String isolation;
    @ColumnInfo(name = "isolation_distance")
    private String isolation_distance;
    @ColumnInfo(name = "isolation_time")
    private String isolation_time;
    @ColumnInfo(name = "second_top_dressing")
    private String second_top_dressing;
    @ColumnInfo(name = "second_top_dressing_bags")
    private String second_top_dressing_bags;
    @ColumnInfo(name = "created_on")
    private String created_on;

    public int getAndroid_id() {
        return android_id;
    }

    public void setAndroid_id(int android_id) {
        this.android_id = android_id;
    }

    public String getScheduler_no() {
        return scheduler_no;
    }

    public void setScheduler_no(String scheduler_no) {
        this.scheduler_no = scheduler_no;
    }

    public String getArrival_plan_no() {
        return arrival_plan_no;
    }

    public void setArrival_plan_no(String arrival_plan_no) {
        this.arrival_plan_no = arrival_plan_no;
    }

    public String getProduction_lot_no() {
        return production_lot_no;
    }

    public void setProduction_lot_no(String production_lot_no) {
        this.production_lot_no = production_lot_no;
    }

    public String getCrop_condition() {
        return crop_condition;
    }

    public void setCrop_condition(String crop_condition) {
        this.crop_condition = crop_condition;
    }

    public String getCrop_stage() {
        return crop_stage;
    }

    public void setCrop_stage(String crop_stage) {
        this.crop_stage = crop_stage;
    }

    public String getDate_of_inspection() {
        return date_of_inspection;
    }

    public void setDate_of_inspection(String date_of_inspection) {
        this.date_of_inspection = date_of_inspection;
    }

    public String getPollen_shedders() {
        return pollen_shedders;
    }

    public void setPollen_shedders(String pollen_shedders) {
        this.pollen_shedders = pollen_shedders;
    }

    public String getOther_types() {
        return other_types;
    }

    public void setOther_types(String other_types) {
        this.other_types = other_types;
    }

    public String getPollen_shedding_plants() {
        return pollen_shedding_plants;
    }

    public void setPollen_shedding_plants(String pollen_shedding_plants) {
        this.pollen_shedding_plants = pollen_shedding_plants;
    }

    public String getPollen_shedding_plants_per() {
        return pollen_shedding_plants_per;
    }

    public void setPollen_shedding_plants_per(String pollen_shedding_plants_per) {
        this.pollen_shedding_plants_per = pollen_shedding_plants_per;
    }

    public String getPest() {
        return pest;
    }

    public void setPest(String pest) {
        this.pest = pest;
    }

    public String getDiseases() {
        return diseases;
    }

    public void setDiseases(String diseases) {
        this.diseases = diseases;
    }

    public String getPest_remarks() {
        return pest_remarks;
    }

    public void setPest_remarks(String pest_remarks) {
        this.pest_remarks = pest_remarks;
    }

    public String getDiseases_remarks() {
        return diseases_remarks;
    }

    public void setDiseases_remarks(String diseases_remarks) {
        this.diseases_remarks = diseases_remarks;
    }

    public String getRecommended_date() {
        return recommended_date;
    }

    public void setRecommended_date(String recommended_date) {
        this.recommended_date = recommended_date;
    }

    public String getActual_date() {
        return actual_date;
    }

    public void setActual_date(String actual_date) {
        this.actual_date = actual_date;
    }

    public String getIsolation() {
        return isolation;
    }

    public void setIsolation(String isolation) {
        this.isolation = isolation;
    }

    public String getIsolation_distance() {
        return isolation_distance;
    }

    public void setIsolation_distance(String isolation_distance) {
        this.isolation_distance = isolation_distance;
    }

    public String getIsolation_time() {
        return isolation_time;
    }

    public void setIsolation_time(String isolation_time) {
        this.isolation_time = isolation_time;
    }

    public String getSecond_top_dressing() {
        return second_top_dressing;
    }

    public void setSecond_top_dressing(String second_top_dressing) {
        this.second_top_dressing = second_top_dressing;
    }

    public String getSecond_top_dressing_bags() {
        return second_top_dressing_bags;
    }

    public void setSecond_top_dressing_bags(String second_top_dressing_bags) {
        this.second_top_dressing_bags = second_top_dressing_bags;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public int getSyncwith_api6() {
        return syncwith_api6;
    }

    public void setSyncwith_api6(int syncwith_api6) {
        this.syncwith_api6 = syncwith_api6;
    }

    @ColumnInfo(name = "syncwith_api6")
    private int syncwith_api6;


    @ColumnInfo(name = "attachment")
    private String attachment;

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getSeed_setting() {
        return seed_setting;
    }

    public void setSeed_setting(String seed_setting) {
        this.seed_setting = seed_setting;
    }

    public String getSeed_setting_prcnt() {
        return seed_setting_prcnt;
    }

    public void setSeed_setting_prcnt(String seed_setting_prcnt) {
        this.seed_setting_prcnt = seed_setting_prcnt;
    }

    @ColumnInfo(name = "seed_setting")
    private String seed_setting ;
    @ColumnInfo(name = "seed_setting_prcnt")
    private String seed_setting_prcnt;
    @ColumnInfo(name = "male_reciept_no")
    private String male_reciept_no;

    public String getMale_reciept_no() {
        return male_reciept_no;
    }

    public void setMale_reciept_no(String male_reciept_no) {
        this.male_reciept_no = male_reciept_no;
    }

    public String getFemale_reciept_no() {
        return female_reciept_no;
    }

    public void setFemale_reciept_no(String female_reciept_no) {
        this.female_reciept_no = female_reciept_no;
    }

    public String getOther_reciept_no() {
        return other_reciept_no;
    }

    public void setOther_reciept_no(String other_reciept_no) {
        this.other_reciept_no = other_reciept_no;
    }

    public String getGrain_remarks() {
        return grain_remarks;
    }

    public void setGrain_remarks(String grain_remarks) {
        this.grain_remarks = grain_remarks;
    }

    @ColumnInfo(name = "female_reciept_no")
    private String female_reciept_no;
    @ColumnInfo(name = "other_reciept_no")
    private String other_reciept_no;
    @ColumnInfo(name = "grain_remarks")
    private String grain_remarks;


    public static FloweringInspectionTable inertFloweringInspection(FloweringInspectionModel floweringInspectionModel){

        FloweringInspectionTable floweringInspectionTable=new FloweringInspectionTable();

  floweringInspectionTable.scheduler_no=floweringInspectionModel.scheduler_no;
  floweringInspectionTable.arrival_plan_no=floweringInspectionModel.arrival_plan_no;
  floweringInspectionTable.production_lot_no=floweringInspectionModel.production_lot_no;
  floweringInspectionTable.crop_condition=floweringInspectionModel.crop_condition;
  floweringInspectionTable.crop_stage=floweringInspectionModel.crop_stage;
  floweringInspectionTable.date_of_inspection=floweringInspectionModel.date_of_inspection;
  floweringInspectionTable.pollen_shedders=floweringInspectionModel.pollen_shedders;
  floweringInspectionTable.other_types=floweringInspectionModel.other_types;
  floweringInspectionTable.pollen_shedding_plants=floweringInspectionModel.pollen_shedding_plants;
  floweringInspectionTable.pollen_shedding_plants_per=floweringInspectionModel.pollen_shedding_plants_per;
  floweringInspectionTable.pest=floweringInspectionModel.pest;
  floweringInspectionTable.diseases=floweringInspectionModel.diseases;
  floweringInspectionTable.pest_remarks=floweringInspectionModel.pest_remarks;
  floweringInspectionTable.diseases_remarks=floweringInspectionModel.diseases_remarks;
  floweringInspectionTable.recommended_date=floweringInspectionModel.recommended_date;
  floweringInspectionTable.actual_date=floweringInspectionModel.actual_date;
  floweringInspectionTable.isolation=floweringInspectionModel.isolation;
  floweringInspectionTable.isolation_distance=floweringInspectionModel.isolation_distance;
  floweringInspectionTable.isolation_time=floweringInspectionModel.isolation_time;
  floweringInspectionTable.second_top_dressing=floweringInspectionModel.second_top_dressing;
  floweringInspectionTable.second_top_dressing_bags=floweringInspectionModel.second_top_dressing_bags;
  floweringInspectionTable.created_on=floweringInspectionModel.created_on;
  floweringInspectionTable.syncwith_api6=floweringInspectionModel.syncwith_api6;
  floweringInspectionTable.seed_setting=floweringInspectionModel.seed_setting;
  floweringInspectionTable.seed_setting_prcnt=floweringInspectionModel.seed_setting_prcnt;
  floweringInspectionTable.attachment=floweringInspectionModel.attachment;
        floweringInspectionTable.male_reciept_no=floweringInspectionModel.male_reciept_no;
        floweringInspectionTable.female_reciept_no=floweringInspectionModel.female_reciept_no;
        floweringInspectionTable.other_reciept_no=floweringInspectionModel.other_reciept_no;
        floweringInspectionTable.grain_remarks=floweringInspectionModel.grain_remarks;

  return floweringInspectionTable;

    }
}
