package com.example.pristineseed.DataBaseRepository.Scheduler.NickingInpection;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.pristineseed.model.scheduler_inspection.Nicking_InspectionLineModel;

@Entity(tableName = "nicking_inspection_insert_table")
public class NickingInspectionTable {
    private int android_id;
    public int getAndroid_id()
    { return android_id;
    }
    public void setAndroid_id(int android_id) {
        this.android_id = android_id;
    }
    @ColumnInfo(name = "scheduler_no")
    private String scheduler_no;
    public String getScheduler_no() { return scheduler_no; }
    public void setScheduler_no(String scheduler_no) { this.scheduler_no = scheduler_no; }


    @NonNull
    @ColumnInfo(name = "arrival_plan_no")
    private String arrival_plan_no;
    public String getArrival_plan_no() { return arrival_plan_no; }
    public void setArrival_plan_no(String arrival_plan_no) { this.arrival_plan_no = arrival_plan_no; }

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "production_lot_no")
    private String production_lot_no;
    public String getProduction_lot_no() { return production_lot_no; }
    public void setProduction_lot_no(String production_lot_no) { this.production_lot_no = production_lot_no; }

    @ColumnInfo(name = "date_of_inspection")
    private String date_of_inspection;
    public String getDate_of_inspection() { return date_of_inspection; }
    public void setDate_of_inspection(String date_of_inspection) { this.date_of_inspection = date_of_inspection; }

    @ColumnInfo(name = "crop_condition")
    private String crop_condition;
    public String getCrop_condition() { return crop_condition; }
    public void setCrop_condition(String crop_condition) { this.crop_condition = crop_condition; }

    @ColumnInfo(name = "crop_stage")
    private String crop_stage;
    public String getCrop_stage() { return crop_stage; }
    public void setCrop_stage(String crop_stage) { this.crop_stage = crop_stage; }

    @ColumnInfo(name = "status_of_female")
    private String status_of_female;
    public String getStatus_of_female() { return status_of_female; }
    public void setStatus_of_female(String status_of_female) { this.status_of_female = status_of_female; }

    @ColumnInfo(name = "status_of_male")
    private String status_of_male;
    public String getStatus_of_male() { return status_of_male; }
    public void setStatus_of_male(String status_of_male) { this.status_of_male = status_of_male; }

    @ColumnInfo(name = "next_plan_of_action")
    private String next_plan_of_action;
    public String getNext_plan_of_action() { return next_plan_of_action; }
    public void setNext_plan_of_action(String next_plan_of_action) { this.next_plan_of_action = next_plan_of_action; }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @ColumnInfo(name = "remarks")
    private String remarks;

    @ColumnInfo(name = "insert_messge")
    private String insert_messge;
    public String getInsert_messge() { return insert_messge; }
    public void setInsert_messge(String insert_messge) { this.insert_messge = insert_messge; }

    @ColumnInfo(name = "status")
    private String status;
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @ColumnInfo(name = "sync_with_api_insp4")
    private int sync_with_api_insp4;
    public int getSync_with_api_insp4() { return sync_with_api_insp4; }
    public void setSync_with_api_insp4(int sync_with_api_insp4) { this.sync_with_api_insp4 = sync_with_api_insp4; }

    @ColumnInfo(name = "created_on")
    private String created_on;
    public String getCreated_on() { return created_on; }
    public void setCreated_on(String created_on) { this.created_on = created_on;
    }

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

    public static NickingInspectionTable insertNickingDataFromServer(Nicking_InspectionLineModel nickingInspectionHeaderModel){

        NickingInspectionTable nickingInspInsertTable = new NickingInspectionTable();
        nickingInspInsertTable.scheduler_no=nickingInspectionHeaderModel.scheduler_no;
        nickingInspInsertTable.arrival_plan_no=nickingInspectionHeaderModel.arrival_plan_no;
        nickingInspInsertTable.production_lot_no=nickingInspectionHeaderModel.production_lot_no;
        nickingInspInsertTable.date_of_inspection=nickingInspectionHeaderModel.date_of_inspection;
        nickingInspInsertTable.crop_condition=nickingInspectionHeaderModel.crop_condition;
        nickingInspInsertTable.crop_stage=nickingInspectionHeaderModel.crop_stage;
        nickingInspInsertTable.status_of_female=nickingInspectionHeaderModel.status_of_female;
        nickingInspInsertTable.status_of_male=nickingInspectionHeaderModel.status_of_male;
        nickingInspInsertTable.next_plan_of_action=nickingInspectionHeaderModel.next_plan_of_action;
        nickingInspInsertTable.remarks=nickingInspectionHeaderModel.remarks;
        nickingInspInsertTable.sync_with_api_insp4=nickingInspectionHeaderModel.sync_with_api_insp4;
        nickingInspInsertTable.seed_setting=nickingInspectionHeaderModel.seed_setting;
        nickingInspInsertTable.seed_setting_prcnt=nickingInspectionHeaderModel.seed_setting_prcnt;
        nickingInspInsertTable.attachment=nickingInspectionHeaderModel.attachment;
        nickingInspInsertTable.male_reciept_no=nickingInspectionHeaderModel.male_reciept_no;
        nickingInspInsertTable.female_reciept_no=nickingInspectionHeaderModel.female_reciept_no;
        nickingInspInsertTable.other_reciept_no=nickingInspectionHeaderModel.other_reciept_no;
        nickingInspInsertTable.recommended_date=nickingInspectionHeaderModel.recommended_date;

        nickingInspInsertTable.actual_date=nickingInspectionHeaderModel.actual_date;

        return nickingInspInsertTable;
    }

}
