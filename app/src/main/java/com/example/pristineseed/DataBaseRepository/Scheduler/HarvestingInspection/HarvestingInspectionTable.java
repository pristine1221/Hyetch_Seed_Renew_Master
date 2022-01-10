package com.example.pristineseed.DataBaseRepository.Scheduler.HarvestingInspection;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.pristineseed.model.scheduler_inspection.HarvestingInspectionModel;

@Entity(tableName = "Harvesting_inspection_table")
public class HarvestingInspectionTable {

    private int android_id;

    @NonNull
 @ColumnInfo(name = "scheduler_no")
  private String scheduler_no ;

    @NonNull
    @ColumnInfo(name = "arrival_plan_no")
  private String arrival_plan_no;

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "production_lot_no")
  private String production_lot_no;

    @ColumnInfo(name = "date_of_inspection")
  private String date_of_inspection ;

    @ColumnInfo(name = "crop_condition")
  private String crop_condition;

    @ColumnInfo(name = "crop_stage")
  private String crop_stage;

    @ColumnInfo(name = "moisture_per")
  private String moisture_per;

    @ColumnInfo(name = "sorting_grading")
  private String sorting_grading;

    @ColumnInfo(name = "overall_agronomy")
  private String overall_agronomy ;

    @ColumnInfo(name = "recommended_date")
  private String recommended_date ;

    @ColumnInfo(name = "actual_date")
  private String actual_date ;

    @ColumnInfo(name = "pest")
  private String pest ;


    @ColumnInfo(name = "diseases")
  private String diseases ;


    @ColumnInfo(name = "pest_remarks")
  private String pest_remarks;

    @ColumnInfo(name = "diseases_remarks")
  private String diseases_remarks ;

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

    public String getDate_of_inspection() {
        return date_of_inspection;
    }

    public void setDate_of_inspection(String date_of_inspection) {
        this.date_of_inspection = date_of_inspection;
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

    public String getMoisture_per() {
        return moisture_per;
    }

    public void setMoisture_per(String moisture_per) {
        this.moisture_per = moisture_per;
    }

    public String getSorting_grading() {
        return sorting_grading;
    }

    public void setSorting_grading(String sorting_grading) {
        this.sorting_grading = sorting_grading;
    }

    public String getOverall_agronomy() {
        return overall_agronomy;
    }

    public void setOverall_agronomy(String overall_agronomy) {
        this.overall_agronomy = overall_agronomy;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @ColumnInfo(name = "remarks")
  public String remarks ;

    public int getSynWithApi9() {
        return synWithApi9;
    }

    public void setSynWithApi9(int synWithApi9) {
        this.synWithApi9 = synWithApi9;
    }

    @ColumnInfo(name = "synWithApi9")
    public int synWithApi9;

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    @ColumnInfo(name = "created_on")
    public String created_on;


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

    @ColumnInfo(name = "female_reciept_no")
    private String female_reciept_no;
    @ColumnInfo(name = "other_reciept_no")
    private String other_reciept_no;


    public static HarvestingInspectionTable insertHarvestingDataIntoLocal(HarvestingInspectionModel harvestingInspectionModel){

     HarvestingInspectionTable harvestingInspectionTable=new HarvestingInspectionTable();

      harvestingInspectionTable.scheduler_no=harvestingInspectionModel.scheduler_no;
      harvestingInspectionTable.arrival_plan_no =harvestingInspectionModel.arrival_plan_no;
      harvestingInspectionTable.production_lot_no=harvestingInspectionModel.production_lot_no;
      harvestingInspectionTable.date_of_inspection =harvestingInspectionModel.date_of_inspection;
      harvestingInspectionTable.crop_condition =harvestingInspectionModel.crop_condition;
      harvestingInspectionTable.crop_stage =harvestingInspectionModel.crop_stage;
      harvestingInspectionTable.moisture_per =harvestingInspectionModel.moisture_per;
      harvestingInspectionTable.sorting_grading =harvestingInspectionModel.sorting_grading;
      harvestingInspectionTable.overall_agronomy =harvestingInspectionModel.overall_agronomy;
      harvestingInspectionTable.recommended_date =harvestingInspectionModel.recommended_date;
      harvestingInspectionTable.actual_date=harvestingInspectionModel.actual_date;
      //harvestingInspectionTable.pest =harvestingInspectionModel.pest;
      harvestingInspectionTable.diseases =harvestingInspectionModel.diseases;
      harvestingInspectionTable.pest_remarks =harvestingInspectionModel.pest_remarks;
      harvestingInspectionTable.diseases_remarks =harvestingInspectionModel.diseases_remarks;
      harvestingInspectionTable.remarks =harvestingInspectionModel.remarks;
      harvestingInspectionTable.created_on=harvestingInspectionModel.created_on;
      harvestingInspectionTable.synWithApi9=harvestingInspectionModel.synWithApi9;
      harvestingInspectionTable.seed_setting=harvestingInspectionModel.seed_setting;
      harvestingInspectionTable.seed_setting_prcnt=harvestingInspectionModel.seed_setting_prcnt;
      harvestingInspectionTable.attachment=harvestingInspectionModel.attachment;
        harvestingInspectionTable.male_reciept_no=harvestingInspectionModel.male_reciept_no;
        harvestingInspectionTable.female_reciept_no=harvestingInspectionModel.female_reciept_no;
        harvestingInspectionTable.other_reciept_no=harvestingInspectionModel.other_reciept_no;


        return harvestingInspectionTable ;
    }

}
