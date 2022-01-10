package com.example.pristineseed.DataBaseRepository.Scheduler.MaturityInspection;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.pristineseed.DataBaseRepository.Scheduler.Vegitative.VegitativeInspectionTable;
import com.example.pristineseed.model.scheduler_inspection.MaturityInspectionModel;
import com.example.pristineseed.model.scheduler_inspection.Vegitative_InspectionModel;

@Entity(tableName = "maturity_inspection_table")
public class MaturityInspectionTable {
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

  public String getYield_estimation_in_kgs() {
    return yield_estimation_in_kgs;
  }

  public void setYield_estimation_in_kgs(String yield_estimation_in_kgs) {
    this.yield_estimation_in_kgs = yield_estimation_in_kgs;
  }

  public String getAbiotic_stress() {
    return abiotic_stress;
  }

  public void setAbiotic_stress(String abiotic_stress) {
    this.abiotic_stress = abiotic_stress;
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

  public String getRemarks() {
    return remarks;
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }

  public String getCreated_on() {
    return created_on;
  }

  public void setCreated_on(String created_on) {
    this.created_on = created_on;
  }

  public int getSyncWithApi8() {
    return syncWithApi8;
  }

  public void setSyncWithApi8(int syncWithApi8) {
    this.syncWithApi8 = syncWithApi8;
  }

  @ColumnInfo(name = "date_of_inspection")
  private String date_of_inspection;
  @ColumnInfo(name = "crop_condition")
  private String crop_condition;
  @ColumnInfo(name = "crop_stage")
  private String crop_stage;
  @ColumnInfo(name = "yield_estimation_in_kgs")
  private String yield_estimation_in_kgs;
  @ColumnInfo(name = "abiotic_stress")
  private String abiotic_stress;
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
  @ColumnInfo(name = "remarks")
  private String remarks;
  @ColumnInfo(name = "created_on")
  private String created_on;
  @ColumnInfo(name = "syncWithApi8")
  private int syncWithApi8;

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


    public static MaturityInspectionTable insertMatuirtyInspectionDataIntoLocal(MaturityInspectionModel maturity_inspectionModel){
    MaturityInspectionTable maturityInspectionTable=new MaturityInspectionTable();

  maturityInspectionTable.scheduler_no=maturity_inspectionModel.scheduler_no;
  maturityInspectionTable.arrival_plan_no=maturity_inspectionModel.arrival_plan_no;
  maturityInspectionTable.production_lot_no=maturity_inspectionModel.production_lot_no;
  maturityInspectionTable.date_of_inspection=maturity_inspectionModel.date_of_inspection;
  maturityInspectionTable.crop_condition=maturity_inspectionModel.crop_condition;
  maturityInspectionTable.crop_stage=maturity_inspectionModel.crop_stage;
  maturityInspectionTable.yield_estimation_in_kgs=maturity_inspectionModel.yield_estimation_in_kgs;
  maturityInspectionTable.abiotic_stress=maturity_inspectionModel.abiotic_stress;
  maturityInspectionTable.pest=maturity_inspectionModel.pest;
  maturityInspectionTable.diseases=maturity_inspectionModel.diseases;
  maturityInspectionTable.pest_remarks=maturity_inspectionModel.pest_remarks;
  maturityInspectionTable.diseases_remarks=maturity_inspectionModel.diseases_remarks;
  maturityInspectionTable.recommended_date=maturity_inspectionModel.recommended_date;
  maturityInspectionTable.actual_date=maturity_inspectionModel.actual_date;
  maturityInspectionTable.remarks=maturity_inspectionModel.remarks;
  maturityInspectionTable.created_on=maturity_inspectionModel.created_on;
  maturityInspectionTable.syncWithApi8=maturity_inspectionModel.syncWithApi8;
  maturityInspectionTable.seed_setting=maturity_inspectionModel.seed_setting;
  maturityInspectionTable.seed_setting_prcnt=maturity_inspectionModel.seed_setting_prcnt;
  maturityInspectionTable.attachment=maturity_inspectionModel.attachment;

        maturityInspectionTable.male_reciept_no=maturity_inspectionModel.male_reciept_no;
        maturityInspectionTable.female_reciept_no=maturity_inspectionModel.female_reciept_no;
        maturityInspectionTable.other_reciept_no=maturity_inspectionModel.other_reciept_no;



        return  maturityInspectionTable;

  }

}
