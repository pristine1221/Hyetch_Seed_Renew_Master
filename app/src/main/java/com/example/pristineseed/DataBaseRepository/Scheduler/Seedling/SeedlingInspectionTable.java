package com.example.pristineseed.DataBaseRepository.Scheduler.Seedling;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.pristineseed.model.scheduler_inspection.GerminationInspectionHeaderModel;
import com.example.pristineseed.model.scheduler_inspection.SeedLing_InspectionLineModel;

@Entity(tableName = "seedling_inpection_table")
public class SeedlingInspectionTable {
    public int getAndroid_id() {
        return android_id;
    }

    public void setAndroid_id(int android_id) {
        this.android_id = android_id;
    }

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

    public String getPest_infestation_level(){
        return pest_infestation_level;
    }
    public void setPest_infestation_level(String pest_infestation_level){
        this.pest_infestation_level=pest_infestation_level;
    }

    public String getDisease_infestation_level(){
        return disease_infestation_level;
    }
    public void setDisease_infestation_level(String disease_infestation_level){
        this.disease_infestation_level=disease_infestation_level;
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
    @ColumnInfo(name = "pest_infestation_level ")
    private String pest_infestation_level ;
    @ColumnInfo(name = "disease_infestation_level  ")
    private String disease_infestation_level  ;
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
    @ColumnInfo(name="standing_acres")
    private String standing_acres;
    @ColumnInfo(name="pld_acres")
    private String pld_acres;
    @ColumnInfo(name = "net_acres")
    private String net_acres;
    @ColumnInfo(name="pld_reason")
    private String pld_reason;

    public int getSync_with_api_ins2() {
        return sync_with_api_ins2;
    }

    public void setSync_with_api_ins2(int sync_with_api_ins2) {
        this.sync_with_api_ins2 = sync_with_api_ins2;
    }

    @ColumnInfo(name = "sync_with_api_ins2")
    private int sync_with_api_ins2;

    @ColumnInfo(name = "seed_setting")
   private String seed_setting;

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

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    @ColumnInfo(name = "seed_setting_prcnt")
   private String seed_setting_prcnt;
    @ColumnInfo(name = "attachment")
   private String attachment;

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

    public String getStanding_acres(){
        return standing_acres;
    }
    public void setStanding_acres(String standing_acres){
        this.standing_acres=standing_acres;
    }

    public String getPld_acres(){
        return pld_acres;
    }
    public void setPld_acres(String pld_acres){
        this.pld_acres=pld_acres;
    }

    public String getNet_acres(){
        return net_acres;
    }
    public void setNet_acres(String net_acres){
        this.net_acres=net_acres;
    }

    public String getPld_reason(){
        return pld_reason;
    }
    public void setPld_reason(String pld_reason){
        this.pld_reason=pld_reason;
    }

    @ColumnInfo(name = "female_reciept_no")
    private String female_reciept_no;
    @ColumnInfo(name = "other_reciept_no")
    private String other_reciept_no;
    @ColumnInfo(name = "grain_remarks")
    private String grain_remarks;

    public  static SeedlingInspectionTable insertSeedlingInspection(SeedLing_InspectionLineModel seedLing_inspectionLineModel){

        SeedlingInspectionTable seedlingInspectionTable=new SeedlingInspectionTable();

     seedlingInspectionTable.scheduler_no=seedLing_inspectionLineModel.scheduler_no;
     seedlingInspectionTable.arrival_plan_no=seedLing_inspectionLineModel.arrival_plan_no;
     seedlingInspectionTable.production_lot_no=seedLing_inspectionLineModel.production_lot_no;
     seedlingInspectionTable.crop_condition=seedLing_inspectionLineModel.crop_condition;
     seedlingInspectionTable.crop_stage=seedLing_inspectionLineModel.crop_stage;
     seedlingInspectionTable.date_of_inspection=seedLing_inspectionLineModel.date_of_inspection;
     seedlingInspectionTable.vigor=seedLing_inspectionLineModel.vigor;
     seedlingInspectionTable.pest=seedLing_inspectionLineModel.pest;
     seedlingInspectionTable.diseases=seedLing_inspectionLineModel.diseases;
     seedlingInspectionTable.pest_infestation_level=seedLing_inspectionLineModel.pest_infestation_level;
     seedlingInspectionTable.disease_infestation_level=seedLing_inspectionLineModel.disease_infestation_level;
     seedlingInspectionTable.pest_remarks=seedLing_inspectionLineModel.pest_remarks;
     seedlingInspectionTable.diseases_remarks=seedLing_inspectionLineModel.diseases_remarks;
     seedlingInspectionTable.recommended_date=seedLing_inspectionLineModel.recommended_date;
     seedlingInspectionTable.actual_date=seedLing_inspectionLineModel.actual_date;
     seedlingInspectionTable.isolation=seedLing_inspectionLineModel.isolation;
     seedlingInspectionTable.isolation_distance=seedLing_inspectionLineModel.isolation_distance;
     seedlingInspectionTable.isolation_time=seedLing_inspectionLineModel.isolation_time;
     seedlingInspectionTable.created_on=seedLing_inspectionLineModel.created_on;
     seedlingInspectionTable.sync_with_api_ins2=seedLing_inspectionLineModel.sync_with_api_ins2;
     seedlingInspectionTable.seed_setting=seedLing_inspectionLineModel.seed_setting;
     seedlingInspectionTable.seed_setting_prcnt=seedLing_inspectionLineModel.seed_setting_prcnt;
     seedlingInspectionTable.attachment=seedLing_inspectionLineModel.attachment;
        seedlingInspectionTable.male_reciept_no = seedLing_inspectionLineModel.male_reciept_no;
        seedlingInspectionTable.female_reciept_no = seedLing_inspectionLineModel.female_reciept_no;
        seedlingInspectionTable.other_reciept_no = seedLing_inspectionLineModel.other_reciept_no;
        seedlingInspectionTable.grain_remarks = seedLing_inspectionLineModel.grain_remarks;
        seedlingInspectionTable.standing_acres=seedLing_inspectionLineModel.standing_acres;
        seedlingInspectionTable.pld_acres=seedLing_inspectionLineModel.pld_acre;
        seedlingInspectionTable.net_acres=seedLing_inspectionLineModel.net_acre;
        seedlingInspectionTable.pld_reason=seedLing_inspectionLineModel.pld_reason;

     return seedlingInspectionTable;

    }

}
