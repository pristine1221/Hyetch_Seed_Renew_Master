package com.example.pristineseed.DataBaseRepository.Scheduler.PostFloweringInspection;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.pristineseed.DataBaseRepository.Scheduler.FloweringInspectionTable.FloweringInspectionTable;
import com.example.pristineseed.model.scheduler_inspection.FloweringInspectionModel;
import com.example.pristineseed.model.scheduler_inspection.PostFloweringInspectionModel;

@Entity(tableName = "Post_flowering_inspection_table")
public class PostfloweringInspectionTable {
    private int android_id;

    @NonNull
    @ColumnInfo(name = "scheduler_no")
   private String scheduler_no;

    @NonNull
    @ColumnInfo(name = "arrival_plan_no")
   private String arrival_plan_no;

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

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public int getSynWithApi7() {
        return synWithApi7;
    }

    public void setSynWithApi7(int synWithApi7) {
        this.synWithApi7 = synWithApi7;
    }


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
    @ColumnInfo(name = "created_on")
   private String created_on;
    @ColumnInfo(name = "synWithApi7")
   private int synWithApi7;



    @ColumnInfo(name = "attachment")
   private String attachment;

    //todo new fields..................
    @ColumnInfo(name = "pest_infestation_level")
    private String pest_infestation_level ;

    @ColumnInfo(name = "disease_infestation_level")
    private String disease_infestation_level;

    @ColumnInfo(name = "standing_acres")
    private String standing_acres;

    @ColumnInfo(name = "pld_acre")
    private String pld_acre;

    @ColumnInfo(name = "pld_reason")//
    private String pld_reason;

    @ColumnInfo(name = "yield_estimation")
    private String yield_estimation;


    public String getPest_infestation_level() {
        return pest_infestation_level;
    }
    public void setPest_infestation_level(String pest_infestation_level) {
        this.pest_infestation_level = pest_infestation_level;
    }

    public String getDisease_infestation_level() {
        return disease_infestation_level;
    }
    public void setDisease_infestation_level(String disease_infestation_level) {
        this.disease_infestation_level = disease_infestation_level;
    }

    public String getStanding_acres() {
        return standing_acres;
    }
    public void setStanding_acres(String standing_acres) {
        this.standing_acres = standing_acres;
    }

    public String getPld_acre() {
        return pld_acre;
    }
    public void setPld_acre(String pld_acre) {
        this.pld_acre = pld_acre;
    }

    public String getPld_reason() {
        return pld_reason;
    }
    public void setPld_reason(String pld_reason) {
        this.pld_reason = pld_reason;
    }

    public String getYield_estimation() {
        return yield_estimation;
    }
    public void setYield_estimation(String yield_estimation) {
        this.yield_estimation = yield_estimation;
    }



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

    public static PostfloweringInspectionTable inertPostFloweringInspection(PostFloweringInspectionModel postfloweringInspectionModel){

        PostfloweringInspectionTable postfloweringInspectionTable=new PostfloweringInspectionTable();

      postfloweringInspectionTable.scheduler_no=postfloweringInspectionModel.scheduler_no;
      postfloweringInspectionTable.arrival_plan_no=postfloweringInspectionModel.arrival_plan_no;
      postfloweringInspectionTable.production_lot_no=postfloweringInspectionModel.production_lot_no;
      postfloweringInspectionTable.crop_condition=postfloweringInspectionModel.crop_condition;
      postfloweringInspectionTable.crop_stage=postfloweringInspectionModel.crop_stage;
      postfloweringInspectionTable.date_of_inspection=postfloweringInspectionModel.date_of_inspection;
      postfloweringInspectionTable.seed_setting=postfloweringInspectionModel.seed_setting;
      postfloweringInspectionTable.other_types=postfloweringInspectionModel.other_types;
      postfloweringInspectionTable.seed_setting_prcnt=postfloweringInspectionModel.seed_setting_prcnt;
      postfloweringInspectionTable.pest=postfloweringInspectionModel.pest;
      postfloweringInspectionTable.diseases=postfloweringInspectionModel.diseases;
      postfloweringInspectionTable.pest_remarks=postfloweringInspectionModel.pest_remarks;
      postfloweringInspectionTable.diseases_remarks=postfloweringInspectionModel.diseases_remarks;
      postfloweringInspectionTable.recommended_date=postfloweringInspectionModel.recommended_date;
      postfloweringInspectionTable.actual_date=postfloweringInspectionModel.actual_date;
      postfloweringInspectionTable.created_on=postfloweringInspectionModel.created_on;
      postfloweringInspectionTable.attachment=postfloweringInspectionModel.attachment;
      postfloweringInspectionTable.synWithApi7=postfloweringInspectionModel.synWithApi7;
      postfloweringInspectionTable.male_reciept_no=postfloweringInspectionModel.male_reciept_no;
      postfloweringInspectionTable.female_reciept_no=postfloweringInspectionModel.female_reciept_no;
      postfloweringInspectionTable.other_reciept_no=postfloweringInspectionModel.other_reciept_no;

      //todo new fields....................
        postfloweringInspectionTable.pest_infestation_level=postfloweringInspectionModel.pest_infestation_level;
        postfloweringInspectionTable.disease_infestation_level=postfloweringInspectionModel.disease_infestation_level;
        postfloweringInspectionTable.standing_acres=postfloweringInspectionModel.standing_acres;
        postfloweringInspectionTable.pld_acre=postfloweringInspectionModel.pld_acre;
        postfloweringInspectionTable.pld_reason=postfloweringInspectionModel.pld_reason;
        postfloweringInspectionTable.yield_estimation=postfloweringInspectionModel.yield_estimation;

        return postfloweringInspectionTable;
    }



}
