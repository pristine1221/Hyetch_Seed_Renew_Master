package com.example.pristineseed.DataBaseRepository.Scheduler;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.pristineseed.model.scheduler_inspection.Germination_InspectionLineModel;

@Entity(tableName = "germination_inspection_table")
public class GerminationInspection1_Table {
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

    public String getGermination_per() {
        return germination_per;
    }

    public void setGermination_per(String germination_per) {
        this.germination_per = germination_per;
    }

    public String getRecommended_dose_of_fertilizer() {
        return recommended_dose_of_fertilizer;
    }

    public void setRecommended_dose_of_fertilizer(String recommended_dose_of_fertilizer) {
        this.recommended_dose_of_fertilizer = recommended_dose_of_fertilizer;
    }

    public String getRecommended_dose_of_fertilizer_in_bags() {
        return recommended_dose_of_fertilizer_in_bags;
    }

    public void setRecommended_dose_of_fertilizer_in_bags(String recommended_dose_of_fertilizer_in_bags) {
        this.recommended_dose_of_fertilizer_in_bags = recommended_dose_of_fertilizer_in_bags;
    }

    public String getBasal_dose() {
        return basal_dose;
    }

    public void setBasal_dose(String basal_dose) {
        this.basal_dose = basal_dose;
    }

    public String getBasal_dose_bags() {
        return basal_dose_bags;
    }

    public void setBasal_dose_bags(String basal_dose_bags) {
        this.basal_dose_bags = basal_dose_bags;
    }

    public String getRemark_for_fertilizer() {
        return remark_for_fertilizer;
    }

    public void setRemark_for_fertilizer(String remark_for_fertilizer) {
        this.remark_for_fertilizer = remark_for_fertilizer;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public String getDate_of_inspection() {
        return date_of_inspection;
    }

    public void setDate_of_inspection(String date_of_inspection) {
        this.date_of_inspection = date_of_inspection;
    }

    @ColumnInfo(name = "crop_condition")
    private String crop_condition;
    @ColumnInfo(name = "crop_stage")
    private String crop_stage;
    @ColumnInfo(name = "germination_per")
    private String germination_per;
    @ColumnInfo(name = "recommended_dose_of_fertilizer")
    private String recommended_dose_of_fertilizer;
    @ColumnInfo(name = "recommended_dose_of_fertilizer_in_bags")
    private String recommended_dose_of_fertilizer_in_bags;
    @ColumnInfo(name = "basal_dose")
    private String basal_dose;
    @ColumnInfo(name = "basal_dose_bags")
    private String basal_dose_bags;
    @ColumnInfo(name = "remark_for_fertilizer")
    private String remark_for_fertilizer;
    @ColumnInfo(name = "created_by")
    private String created_by;
    @ColumnInfo(name = "created_on")
    private String created_on;
    @ColumnInfo(name = "date_of_inspection")
    private String date_of_inspection;


    public int getSync_with_api() {
        return sync_with_api;
    }

    public void setSync_with_api(int sync_with_api) {
        this.sync_with_api = sync_with_api;
    }

    @ColumnInfo(name = "sync_with_api")
    private int sync_with_api;

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    @ColumnInfo(name = "attachment")
    private String attachment;

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
    private String seed_setting;
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

    public String getRecommended_date() {
        return recommended_date;
    }

    public void setRecommended_date(String recommended_date) {
        this.recommended_date = recommended_date;
    }

    @ColumnInfo(name = "recommended_date")
    private String recommended_date;

    public String getActual_date() {
        return actual_date;
    }

    public void setActual_date(String actual_date) {
        this.actual_date = actual_date;
    }

    @ColumnInfo(name = "actual_date")
    private String actual_date;

    public String getStanding_acres() {
        return standing_acres;
    }

    public void setStanding_acres(String standing_acres) {
        this.standing_acres = standing_acres;
    }

    @ColumnInfo(name = "standing_acres")
    private String standing_acres;

    public String getSown_acres(){
        return sown_acres;
    }
    public void setSown_acres(String sown_acres){
        this.sown_acres=sown_acres;
    }
    @ColumnInfo(name="sown_acres")
    private String sown_acres;

    public static GerminationInspection1_Table insertGerminationInspection(Germination_InspectionLineModel germination_inspectionLineModel) {
        GerminationInspection1_Table germinationInspection1_table = new GerminationInspection1_Table();
        germinationInspection1_table.scheduler_no = germination_inspectionLineModel.scheduler_no;
        germinationInspection1_table.arrival_plan_no = germination_inspectionLineModel.arrival_plan_no;
        germinationInspection1_table.production_lot_no = germination_inspectionLineModel.production_lot_no;
        germinationInspection1_table.crop_condition = germination_inspectionLineModel.crop_condition;
        germinationInspection1_table.crop_stage = germination_inspectionLineModel.crop_stage;
        germinationInspection1_table.germination_per = germination_inspectionLineModel.germination_per;
        germinationInspection1_table.recommended_dose_of_fertilizer = germination_inspectionLineModel.recommended_dose_of_fertilizer;
        germinationInspection1_table.recommended_dose_of_fertilizer_in_bags = germination_inspectionLineModel.recommended_dose_of_fertilizer_in_bags;
        germinationInspection1_table.basal_dose = germination_inspectionLineModel.basal_dose;
        germinationInspection1_table.basal_dose_bags = germination_inspectionLineModel.basal_dose_bags;
        germinationInspection1_table.remark_for_fertilizer = germination_inspectionLineModel.remark_for_fertilizer;
        germinationInspection1_table.created_by = germination_inspectionLineModel.created_by;
        germinationInspection1_table.created_on = germination_inspectionLineModel.created_on;
        germinationInspection1_table.date_of_inspection = germination_inspectionLineModel.date_of_inspection;
        germinationInspection1_table.sync_with_api = germination_inspectionLineModel.sync_with_api;
        germinationInspection1_table.seed_setting = germination_inspectionLineModel.seed_setting;
        germinationInspection1_table.seed_setting_prcnt = germination_inspectionLineModel.seed_setting_prcnt;
        germinationInspection1_table.attachment = germination_inspectionLineModel.attachment;
        germinationInspection1_table.male_reciept_no = germination_inspectionLineModel.male_reciept_no;
        germinationInspection1_table.female_reciept_no = germination_inspectionLineModel.female_reciept_no;
        germinationInspection1_table.other_reciept_no = germination_inspectionLineModel.other_reciept_no;
        germinationInspection1_table.recommended_date  =germination_inspectionLineModel.recommended_date;
        germinationInspection1_table.actual_date=germination_inspectionLineModel.actual_date;
        germinationInspection1_table.standing_acres=germination_inspectionLineModel.standing_acres;
        germinationInspection1_table.sown_acres=germination_inspectionLineModel.sown_acres;

        return germinationInspection1_table;
    }

}
