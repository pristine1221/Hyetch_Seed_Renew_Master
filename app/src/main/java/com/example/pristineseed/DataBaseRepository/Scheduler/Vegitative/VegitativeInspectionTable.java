package com.example.pristineseed.DataBaseRepository.Scheduler.Vegitative;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.pristineseed.model.scheduler_inspection.Vegitative_InspectionModel;

@Entity(tableName = "vegitative_inpsection_table")
public class VegitativeInspectionTable {
    private  int android_id;


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

    public String getVigor() {
        return vigor;
    }

    public void setVigor(String vigor) {
        this.vigor = vigor;
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

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

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
    @ColumnInfo(name = "vigor")
private String vigor;
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
    @ColumnInfo(name = "created_on")
private String created_on;


    @ColumnInfo(name = "other_types")
   private String other_types;

    public String getOther_types() {
        return other_types;
    }

    public void setOther_types(String other_types) {
        this.other_types = other_types;
    }

    public String getFirst_top_dressing() {
        return first_top_dressing;
    }

    public void setFirst_top_dressing(String first_top_dressing) {
        this.first_top_dressing = first_top_dressing;
    }

    public String getFirst_top_dressing_bags() {
        return first_top_dressing_bags;
    }

    public void setFirst_top_dressing_bags(String first_top_dressing_bags) {
        this.first_top_dressing_bags = first_top_dressing_bags;
    }

    @ColumnInfo(name = "first_top_dressing")
   private String first_top_dressing;
    @ColumnInfo(name = "first_top_dressing_bags")
   private String first_top_dressing_bags;

    public int getSyncWith_Api() {
        return syncWith_Api;
    }

    public void setSyncWith_Api(int syncWith_Api) {
        this.syncWith_Api = syncWith_Api;
    }

    @ColumnInfo(name = "syncWith_Api")
    private int syncWith_Api;

    public String getNav_error() {
        return nav_error;
    }

    public void setNav_error(String msg) {
        this.nav_error = msg;
    }

    @ColumnInfo(name = "nav_error")
    private String nav_error;

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    @ColumnInfo(name = "attachment")
    private String attachment;

    @ColumnInfo(name = "seed_setting")
    private String seed_setting ;

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

    @ColumnInfo(name = "seed_setting_prcnt")
    private String seed_setting_prcnt;

    @ColumnInfo(name = "grain_remarks")
    private String grain_remarks;

    public String getGrain_remarks() {
        return grain_remarks;
    }

    public void setGrain_remarks(String grain_remarks) {
        this.grain_remarks = grain_remarks;
    }

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

    @ColumnInfo(name = "male_reciept_no")
    private String male_reciept_no;
    @ColumnInfo(name = "female_reciept_no")
    private String female_reciept_no;
    @ColumnInfo(name = "other_reciept_no")
    private String other_reciept_no;


    public static  VegitativeInspectionTable insertVegitativeDataIntoLocal(Vegitative_InspectionModel vegitative_inspectionModel){

        VegitativeInspectionTable vegitativeInspectionTable=new VegitativeInspectionTable();

      vegitativeInspectionTable.scheduler_no=vegitative_inspectionModel.scheduler_no;
      vegitativeInspectionTable.arrival_plan_no=vegitative_inspectionModel.arrival_plan_no;
      vegitativeInspectionTable.production_lot_no=vegitative_inspectionModel.production_lot_no;
      vegitativeInspectionTable.crop_condition=vegitative_inspectionModel.crop_condition;
      vegitativeInspectionTable.crop_stage=vegitative_inspectionModel.crop_stage;
      vegitativeInspectionTable.date_of_inspection=vegitative_inspectionModel.date_of_inspection;
      vegitativeInspectionTable.vigor=vegitative_inspectionModel.vigor;
      vegitativeInspectionTable.pest=vegitative_inspectionModel.pest;
      vegitativeInspectionTable.diseases=vegitative_inspectionModel.diseases;
      vegitativeInspectionTable.pest_remarks=vegitative_inspectionModel.pest_remarks;
      vegitativeInspectionTable.diseases_remarks=vegitative_inspectionModel.diseases_remarks;
      vegitativeInspectionTable.recommended_date=vegitative_inspectionModel.recommended_date;
      vegitativeInspectionTable.actual_date=vegitative_inspectionModel.actual_date;
      vegitativeInspectionTable.isolation=vegitative_inspectionModel.isolation;
      vegitativeInspectionTable.isolation_distance=vegitative_inspectionModel.isolation_distance;
      vegitativeInspectionTable.isolation_time=vegitative_inspectionModel.isolation_time;
      vegitativeInspectionTable.created_on=vegitative_inspectionModel.created_on;
      vegitativeInspectionTable.other_types=vegitative_inspectionModel.other_types;
      vegitativeInspectionTable.first_top_dressing=vegitative_inspectionModel.first_top_dressing;
      vegitativeInspectionTable.first_top_dressing_bags=vegitative_inspectionModel.first_top_dressing_bags;
      vegitativeInspectionTable.date_of_inspection=vegitative_inspectionModel.date_of_inspection;
      vegitativeInspectionTable.syncWith_Api=vegitative_inspectionModel.syncWithApi;
      vegitativeInspectionTable.nav_error=vegitative_inspectionModel.nav_error;
      vegitativeInspectionTable.attachment=vegitative_inspectionModel.attachment;
      vegitativeInspectionTable.seed_setting=vegitative_inspectionModel.seed_setting;
      vegitativeInspectionTable.seed_setting_prcnt=vegitative_inspectionModel.seed_setting_prcnt;
        vegitativeInspectionTable.male_reciept_no = vegitative_inspectionModel.male_reciept_no;
        vegitativeInspectionTable.female_reciept_no = vegitative_inspectionModel.female_reciept_no;
        vegitativeInspectionTable.other_reciept_no = vegitative_inspectionModel.other_reciept_no;
        vegitativeInspectionTable.grain_remarks = vegitative_inspectionModel.grain_remarks;



      return vegitativeInspectionTable;

    }

}
