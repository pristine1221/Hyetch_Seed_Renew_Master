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

    @ColumnInfo(name = "pest_infestation_level ")
    private String pest_infestation_level ;

    @ColumnInfo(name = "disease_infestation_level  ")
    private String disease_infestation_level  ;

    @ColumnInfo(name = "target_date_of_detasseling")
    private String target_date_of_detasseling;

    @ColumnInfo(name = "actual_date_of_detasseling")
    private String actual_date_of_detasseling;

    @ColumnInfo(name = "net_deviation_days")
    private String net_deviation_days;

    @ColumnInfo(name = "date_1st_pass")
    private String date_1st_pass;

    @ColumnInfo(name = "prcnt_of_silk_1st_pass")
    private String prcnt_of_silk_1st_pass;
    @ColumnInfo(name = "prcnt_of_male_shedding_1st_pass")
    private String prcnt_of_male_shedding_1st_pass;
    @ColumnInfo(name = "date_2nd_pass")
    private String date_2nd_pass;
    @ColumnInfo(name = "prcnt_of_silk_2nd_pass")
    private String prcnt_of_silk_2nd_pass;
    @ColumnInfo(name = "prcnt_of_male_shedding_2nd_pass")
    private String prcnt_of_male_shedding_2nd_pass;
    @ColumnInfo(name = "Date_final_pass")
    private String Date_final_pass;
    @ColumnInfo(name = "prcnt_of_Silk_final_pass")
    private String prcnt_of_Silk_final_pass;
    @ColumnInfo(name = "prcnt_of_Male_Shedding_final_pass")
    private String prcnt_of_Male_Shedding_final_pass;
    @ColumnInfo(name = "Date_1st_Roughing")
    private String Date_1st_Roughing;
    @ColumnInfo(name = "Type_of_Offtype_Roughing_1")
    private String Type_of_Offtype_Roughing_1;
    @ColumnInfo(name = "In_Male_or_Female_Roughing_1")
    private String In_Male_or_Female_Roughing_1;
    @ColumnInfo(name = "no_of_Off_types_Roughing_1")
    private String no_of_Off_types_Roughing_1;
    @ColumnInfo(name = "Type_of_Offtype_Roughing_2")
    private String Type_of_Offtype_Roughing_2;
    @ColumnInfo(name = "In_Male_or_Female_Roughing_2")
    private String In_Male_or_Female_Roughing_2;
    @ColumnInfo(name = "no_of_Off_types_Roughing_2")
    private String no_of_Off_types_Roughing_2;
    @ColumnInfo(name = "Date_Roughing_2")
    private String Date_Roughing_2;
    @ColumnInfo(name = "Date_Roughing_3")
    private String Date_Roughing_3;
    @ColumnInfo(name = "Type_of_Offtype_Roughing_3")
    private String Type_of_Offtype_Roughing_3;
    @ColumnInfo(name = "In_Male_or_Female_Roughing_3")
    private String In_Male_or_Female_Roughing_3;
    @ColumnInfo(name = "no_of_Off_types_Roughing_3")
    private String no_of_Off_types_Roughing_3;

    @ColumnInfo(name="standing_acres ")
    private String standing_acres;
    @ColumnInfo(name="pld_acre ")
    private String pld_acre;
    @ColumnInfo(name = "net_acre")
    private String net_acre;
    @ColumnInfo(name="pld_reason")
    private String pld_reason;

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

    public String getStanding_acres(){
        return standing_acres;
    }
    public void setStanding_acres(String standing_acres){
        this.standing_acres=standing_acres;
    }

    public String getPld_acre(){
        return pld_acre;
    }
    public void setPld_acre(String pld_acre){
        this.pld_acre=pld_acre;
    }

    public String getNet_acre(){
        return net_acre;
    }
    public void setNet_acre(String net_acre){
        this.net_acre=net_acre;
    }

    public String getPld_reason(){
        return pld_reason;
    }
    public void setPld_reason(String pld_reason){
        this.pld_reason=pld_reason;
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

    public String getTarget_date_of_detasseling(){
        return target_date_of_detasseling;
    }
    public void setTarget_date_of_detasseling(String target_date_of_detasseling){
        this.target_date_of_detasseling=target_date_of_detasseling;
    }

    public String getActual_date_of_detasseling(){
        return actual_date_of_detasseling;
    }
    public void setActual_date_of_detasseling(String actual_date_of_detasseling){
        this.actual_date_of_detasseling=actual_date_of_detasseling;
    }

    public String getNet_deviation_days(){
        return net_deviation_days;
    }
    public void setNet_deviation_days(String net_deviation_days){
        this.net_deviation_days=net_deviation_days;
    }

    public String getDate_1st_pass(){
        return date_1st_pass;
    }
    public void setDate_1st_pass(String date_1st_pass){
        this.date_1st_pass=date_1st_pass;
    }

    public String getPrcnt_of_silk_1st_pass(){
        return prcnt_of_silk_1st_pass;
    }
    public void setPrcnt_of_silk_1st_pass(String prcnt_of_silk_1st_pass){
        this.prcnt_of_silk_1st_pass=prcnt_of_silk_1st_pass;
    }

    public String getPrcnt_of_male_shedding_1st_pass(){
        return prcnt_of_male_shedding_1st_pass;
    }
    public void setPrcnt_of_male_shedding_1st_pass(String prcnt_of_male_shedding_1st_pass){
        this.prcnt_of_male_shedding_1st_pass=prcnt_of_male_shedding_1st_pass;
    }

    public String getDate_2nd_pass(){
        return date_2nd_pass;
    }
    public void setDate_2nd_pass(String date_2nd_pass){
        this.date_2nd_pass=date_2nd_pass;
    }

    public String getPrcnt_of_silk_2nd_pass(){
        return prcnt_of_silk_2nd_pass;
    }
    public void setPrcnt_of_silk_2nd_pass(String prcnt_of_silk_2nd_pass){
        this.prcnt_of_silk_2nd_pass=prcnt_of_silk_2nd_pass;
    }

    public String getPrcnt_of_male_shedding_2nd_pass(){
        return prcnt_of_male_shedding_2nd_pass;
    }
    public void setPrcnt_of_male_shedding_2nd_pass(String prcnt_of_male_shedding_2nd_pass){
        this.prcnt_of_male_shedding_2nd_pass=prcnt_of_male_shedding_2nd_pass;
    }

    public String getDate_final_pass(){
        return Date_final_pass;
    }
    public void setDate_final_pass(String Date_final_pass){
        this.Date_final_pass=Date_final_pass;
    }

    public String getPrcnt_of_Silk_final_pass(){
        return prcnt_of_Silk_final_pass;
    }
    public void setPrcnt_of_Silk_final_pass(String prcnt_of_Silk_final_pass){
        this.prcnt_of_Silk_final_pass=prcnt_of_Silk_final_pass;
    }

    public String getPrcnt_of_Male_Shedding_final_pass(){
        return prcnt_of_Male_Shedding_final_pass;
    }
    public void setPrcnt_of_Male_Shedding_final_pass(String prcnt_of_male_shedding_final_pass){
        this.prcnt_of_Male_Shedding_final_pass=prcnt_of_male_shedding_final_pass;
    }

    public String getDate_1st_Roughing(){
        return Date_1st_Roughing;
    }
    public void setDate_1st_Roughing(String date_1st_Roughing){
        this.Date_1st_Roughing=date_1st_Roughing;
    }

    public String getType_of_Offtype_Roughing_1(){
        return Type_of_Offtype_Roughing_1;
    }
    public void setType_of_Offtype_Roughing_1(String type_of_Offtype_Roughing_1){
        this.Type_of_Offtype_Roughing_1=type_of_Offtype_Roughing_1;
    }

    public String getIn_Male_or_Female_Roughing_1(){
        return In_Male_or_Female_Roughing_1;
    }
    public void setIn_Male_or_Female_Roughing_1(String in_Male_or_Female_Roughing_1){
        this.In_Male_or_Female_Roughing_1=in_Male_or_Female_Roughing_1;
    }

    public String getNo_of_Off_types_Roughing_1(){
        return no_of_Off_types_Roughing_1;
    }
    public void setNo_of_Off_types_Roughing_1(String no_of_Off_types_Roughing_1){
        this.no_of_Off_types_Roughing_1=no_of_Off_types_Roughing_1;
    }

    public String getDate_Roughing_2(){
        return Date_Roughing_2;
    }
    public void setDate_Roughing_2(String date_Roughing_2){
        this.Date_Roughing_2=date_Roughing_2;
    }

    public String getType_of_Offtype_Roughing_2(){
        return Type_of_Offtype_Roughing_2;
    }
    public void setType_of_Offtype_Roughing_2(String type_of_Offtype_Roughing_2){
        this.Type_of_Offtype_Roughing_2=type_of_Offtype_Roughing_2;
    }

    public String getIn_Male_or_Female_Roughing_2(){
        return In_Male_or_Female_Roughing_2;
    }
    public void setIn_Male_or_Female_Roughing_2(String in_Male_or_Female_Roughing_2){
        this.In_Male_or_Female_Roughing_2=in_Male_or_Female_Roughing_2;
    }

    public String getNo_of_Off_types_Roughing_2(){
        return no_of_Off_types_Roughing_2;
    }
    public void setNo_of_Off_types_Roughing_2(String no_of_Off_types_Roughing_2){
        this.no_of_Off_types_Roughing_2=no_of_Off_types_Roughing_2;
    }

    public String getDate_Roughing_3(){
        return Date_Roughing_3;
    }
    public void setDate_Roughing_3(String dateRoughing3){
        this.Date_Roughing_3=dateRoughing3;
    }

    public String getType_of_Offtype_Roughing_3(){
        return Type_of_Offtype_Roughing_3;
    }
    public void setType_of_Offtype_Roughing_3(String type_of_Offtype_Roughing_3){
        this.Type_of_Offtype_Roughing_3=type_of_Offtype_Roughing_3;
    }

    public String getIn_Male_or_Female_Roughing_3(){
        return In_Male_or_Female_Roughing_3;
    }
    public void setIn_Male_or_Female_Roughing_3(String in_Male_or_Female_Roughing_3){
        this.In_Male_or_Female_Roughing_3=in_Male_or_Female_Roughing_3;
    }

    public String getNo_of_Off_types_Roughing_3(){
        return no_of_Off_types_Roughing_3;
    }
    public void setNo_of_Off_types_Roughing_3(String no_of_Off_types_Roughing_3){
        this.no_of_Off_types_Roughing_3=no_of_Off_types_Roughing_3;
    }

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


  floweringInspectionTable.pest_infestation_level=floweringInspectionModel.pest_infestation_level;
  floweringInspectionTable.disease_infestation_level=floweringInspectionModel.disease_infestation_level;
  floweringInspectionTable.target_date_of_detasseling=floweringInspectionModel.target_date_of_detasseling;
  floweringInspectionTable.actual_date_of_detasseling=floweringInspectionModel.actual_date_of_detasseling;
  floweringInspectionTable.net_deviation_days=floweringInspectionModel.net_deviation_days;
  floweringInspectionTable.date_1st_pass=floweringInspectionModel.date_1st_pass;
  floweringInspectionTable.prcnt_of_silk_1st_pass=floweringInspectionModel.prcnt_of_silk_1st_pass;
  floweringInspectionTable.prcnt_of_male_shedding_1st_pass=floweringInspectionModel.prcnt_of_male_shedding_1st_pass;
  floweringInspectionTable.date_2nd_pass=floweringInspectionModel.date_2nd_pass;
  floweringInspectionTable.prcnt_of_silk_2nd_pass=floweringInspectionModel.prcnt_of_silk_2nd_pass;
  floweringInspectionTable.prcnt_of_male_shedding_2nd_pass=floweringInspectionModel.prcnt_of_male_shedding_2nd_pass;
  floweringInspectionTable.Date_final_pass=floweringInspectionModel.Date_final_pass;
  floweringInspectionTable.prcnt_of_Silk_final_pass=floweringInspectionModel.prcnt_of_Silk_final_pass;
  floweringInspectionTable.prcnt_of_Male_Shedding_final_pass=floweringInspectionModel.prcnt_of_Male_Shedding_final_pass;
  floweringInspectionTable.Date_1st_Roughing=floweringInspectionModel.Date_1st_Roughing;
  floweringInspectionTable.Type_of_Offtype_Roughing_1=floweringInspectionModel.Type_of_Offtype_Roughing_1;
  floweringInspectionTable.In_Male_or_Female_Roughing_1=floweringInspectionModel.In_Male_or_Female_Roughing_1;
  floweringInspectionTable.no_of_Off_types_Roughing_1=floweringInspectionModel.no_of_Off_types_Roughing_1;
  floweringInspectionTable.Type_of_Offtype_Roughing_2=floweringInspectionModel.Type_of_Offtype_Roughing_2;
  floweringInspectionTable.In_Male_or_Female_Roughing_2=floweringInspectionModel.In_Male_or_Female_Roughing_2;
  floweringInspectionTable.no_of_Off_types_Roughing_2=floweringInspectionModel.no_of_Off_types_Roughing_2;
  floweringInspectionTable.Date_Roughing_3=floweringInspectionModel.Date_Roughing_3;
  floweringInspectionTable.Date_Roughing_2=floweringInspectionModel.Date_Roughing_2;
  floweringInspectionTable.Type_of_Offtype_Roughing_3=floweringInspectionModel.Type_of_Offtype_Roughing_3;
  floweringInspectionTable.In_Male_or_Female_Roughing_3=floweringInspectionModel.In_Male_or_Female_Roughing_3;
  floweringInspectionTable.no_of_Off_types_Roughing_3=floweringInspectionModel.no_of_Off_types_Roughing_3;

  floweringInspectionTable.net_acre=floweringInspectionModel.net_acre;
  floweringInspectionTable.standing_acres=floweringInspectionModel.standing_acres;
  floweringInspectionTable.pld_acre=floweringInspectionModel.pld_acre;
  floweringInspectionTable.pld_reason=floweringInspectionModel.pld_reason;

  return floweringInspectionTable;

    }
}
