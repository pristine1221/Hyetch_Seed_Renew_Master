package com.example.pristineseed.DataBaseRepository.Scheduler.Qc_Inspection;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.pristineseed.model.scheduler_inspection.Qc_Inspection_Model;

@Entity(tableName = "qc_inspection_table")
public class QcInspectionTable {
    private int android_id;

    @NonNull
    @ColumnInfo(name = "scheduler_no")
    public String scheduler_no;

    @NonNull
    @ColumnInfo(name = "arrival_plan_no")
    public String arrival_plan_no;

    public int getAndroid_id() {
        return android_id;
    }

    public void setAndroid_id(int android_id) {
        this.android_id = android_id;
    }

    @NonNull
    public String getScheduler_no() {
        return scheduler_no;
    }

    public void setScheduler_no(@NonNull String scheduler_no) {
        this.scheduler_no = scheduler_no;
    }

    public String getArrival_plan_no() {
        return arrival_plan_no;
    }

    public void setArrival_plan_no(String arrival_plan_no) {
        this.arrival_plan_no = arrival_plan_no;
    }

    @NonNull
    public String getProduction_lot_no() {
        return production_lot_no;
    }

    public void setProduction_lot_no(@NonNull String production_lot_no) {
        this.production_lot_no = production_lot_no;
    }

    public String getDate_of_inspection() {
        return date_of_inspection;
    }

    public void setDate_of_inspection(String date_of_inspection) {
        this.date_of_inspection = date_of_inspection;
    }

    public String getDate_of_actual_date(){
        return actual_date;
    }

    public void setDate_of_actual_date(String dateOfActualDate){
        this.actual_date = dateOfActualDate;
    }

    public String getCrop() {
        return crop;
    }

    public void setCrop(String crop) {
        this.crop = crop;
    }

    public String getOff_type_per_male_parent() {
        return off_type_per_male_parent;
    }

    public void setOff_type_per_male_parent(String off_type_per_male_parent) {
        this.off_type_per_male_parent = off_type_per_male_parent;
    }

    public String getOff_type_per_female_parent() {
        return off_type_per_female_parent;
    }

    public void setOff_type_per_female_parent(String off_type_per_female_parent) {
        this.off_type_per_female_parent = off_type_per_female_parent;
    }

    public String getPollen_shedders_or_shedding_tassel() {
        return pollen_shedders_or_shedding_tassel;
    }

    public void setPollen_shedders_or_shedding_tassel(String pollen_shedders_or_shedding_tassel) {
        this.pollen_shedders_or_shedding_tassel = pollen_shedders_or_shedding_tassel;
    }

    public String getFlag_status() {
        return flag_status;
    }

    public void setFlag_status(String flag_status) {
        this.flag_status = flag_status;
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

    public String getNick_per() {
        return nick_per;
    }

    public void setNick_per(String nick_per) {
        this.nick_per = nick_per;
    }

    public String getBorder_rows() {
        return border_rows;
    }

    public void setBorder_rows(String border_rows) {
        this.border_rows = border_rows;
    }

    public String getWind_direction() {
        return wind_direction;
    }

    public void setWind_direction(String wind_direction) {
        this.wind_direction = wind_direction;
    }

    public String getMale_stand() {
        return male_stand;
    }

    public void setMale_stand(String male_stand) {
        this.male_stand = male_stand;
    }

    public int getSyncwithQc() {
        return syncwithQc;
    }

    public void setSyncwithQc(int syncwithQc) {
        this.syncwithQc = syncwithQc;
    }

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "production_lot_no")
    public String production_lot_no;

    @ColumnInfo(name = "date_of_inspection")
    public String date_of_inspection;

    @ColumnInfo(name = "actual_date")
    public String actual_date;

    @ColumnInfo(name = "crop")
    public String crop;

    @ColumnInfo(name = "off_type_per_male_parent")
    public String off_type_per_male_parent;

    @ColumnInfo(name = "off_type_per_female_parent")
    public String off_type_per_female_parent;

    @ColumnInfo(name = "pollen_shedders_or_shedding_tassel")
    public String pollen_shedders_or_shedding_tassel;


    @ColumnInfo(name = "flag_status")
    public String flag_status;


    @ColumnInfo(name = "isolation_distance")
    public String isolation_distance;

    @ColumnInfo(name = "isolation_time")
    public String isolation_time;

    @ColumnInfo(name = "nick_per")
    public String nick_per;

    @ColumnInfo(name = "border_rows")
    public String border_rows ;

    @ColumnInfo(name = "wind_direction")
    public String wind_direction ;

    @ColumnInfo(name = "male_stand")
    public String male_stand;

    @ColumnInfo(name = "syncwithQc")
    public int syncwithQc;

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    @ColumnInfo(name = "created_on")
    private String created_on;



    @ColumnInfo(name = "attachment")
    public String attachment;

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
    public String seed_setting ;
    @ColumnInfo(name = "seed_setting_prcnt")
    public String seed_setting_prcnt;

    @ColumnInfo(name = "male_reciept_no")
    private String male_reciept_no;
    @ColumnInfo(name = "female_reciept_no")
    private String female_reciept_no;
    @ColumnInfo(name = "other_reciept_no")
    private String other_reciept_no;


    @ColumnInfo(name = "crop_condition")
    public String crop_condition;

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

    public String getDate_of_field_updated() {
        return date_of_field_updated;
    }

    public void setDate_of_field_updated(String date_of_field_updated) {
        this.date_of_field_updated = date_of_field_updated;
    }

    public String getLot_recommend() {
        return lot_recommend;
    }

    public void setLot_recommend(String lot_recommend) {
        this.lot_recommend = lot_recommend;
    }

    @ColumnInfo(name = "crop_stage")
    public String crop_stage;
    @ColumnInfo(name = "date_of_field_updated")
    public String date_of_field_updated;
    @ColumnInfo(name = "lot_recommend")
    public String lot_recommend;

    @ColumnInfo(name = "grain_remarks")
    private String grain_remarks;

    public String getGrain_remarks() {
        return grain_remarks;
    }

    public void setGrain_remarks(String grain_remarks) {
        this.grain_remarks = grain_remarks;
    }


    public static QcInspectionTable insertOcDatafromIntoLocal(Qc_Inspection_Model qc_inspection_model){

      QcInspectionTable qcInspectionTable=new QcInspectionTable();

      qcInspectionTable.scheduler_no=qc_inspection_model.scheduler_no;
      qcInspectionTable.arrival_plan_no=qc_inspection_model.arrival_plan_no;
      qcInspectionTable.production_lot_no=qc_inspection_model.production_lot_no;
      qcInspectionTable.date_of_inspection=qc_inspection_model.date_of_inspection;

      qcInspectionTable.actual_date = qc_inspection_model.actual_date;

      qcInspectionTable.crop=qc_inspection_model.crop;
      qcInspectionTable.off_type_per_male_parent=qc_inspection_model.off_type_per_male_parent;
      qcInspectionTable.off_type_per_female_parent=qc_inspection_model.off_type_per_female_parent;
      qcInspectionTable.pollen_shedders_or_shedding_tassel=qc_inspection_model.pollen_shedders_or_shedding_tassel;
      qcInspectionTable.flag_status=qc_inspection_model.flag_status;
      qcInspectionTable.isolation_distance=qc_inspection_model.isolation_distance;
      qcInspectionTable.isolation_time=qc_inspection_model.isolation_time;
      qcInspectionTable.nick_per=qc_inspection_model.nick_per;
      qcInspectionTable.border_rows =qc_inspection_model.border_rows;
      qcInspectionTable.wind_direction =qc_inspection_model.wind_direction;
      qcInspectionTable.male_stand=qc_inspection_model.male_stand;
      qcInspectionTable.created_on=qc_inspection_model.created_on;
      qcInspectionTable.syncwithQc=qc_inspection_model.syncwithQc;
      qcInspectionTable.seed_setting=qc_inspection_model.seed_setting;
      qcInspectionTable.seed_setting_prcnt=qc_inspection_model.seed_setting_prcnt;
      qcInspectionTable.attachment=qc_inspection_model.attachment;

        qcInspectionTable.male_reciept_no=qc_inspection_model.male_reciept_no;
        qcInspectionTable.female_reciept_no=qc_inspection_model.female_reciept_no;
        qcInspectionTable.other_reciept_no=qc_inspection_model.other_reciept_no;

        qcInspectionTable.crop_condition=qc_inspection_model.crop_condition;
        qcInspectionTable.crop_stage=qc_inspection_model.crop_stage;
        qcInspectionTable.date_of_field_updated=qc_inspection_model.date_of_field_updated;
        qcInspectionTable.lot_recommend=qc_inspection_model.lot_recommend;
        qcInspectionTable.grain_remarks=qc_inspection_model.grain_remarks;

      return qcInspectionTable;
    }

}

