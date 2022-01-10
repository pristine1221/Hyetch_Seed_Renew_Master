package com.example.pristineseed.DataBaseRepository.EventManagementTable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.pristineseed.model.event_managment_model.EventManagemantModel;
import com.example.pristineseed.model.event_managment_model.SyncEventDetailModel;

@Entity(tableName = "event_management_table")
public class EventManagemantTable {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int android_event_code;

    @ColumnInfo(name = "event_code")
    private String event_code;
    @ColumnInfo(name = "event_desc")
    private String event_desc;
    @ColumnInfo(name = "event_date")
    private String event_date;
    @ColumnInfo(name = "event_type")
    private String event_type;
    @ColumnInfo(name = "event_budget")
    private String event_budget;
    @ColumnInfo(name = "crop")
    private String crop;
    @ColumnInfo(name = "variety")
    private String variety;
    @ColumnInfo(name = "state")
    private String state;
    @ColumnInfo(name = "district")
    private String district;
    @ColumnInfo(name = "village")
    private String village;
    @ColumnInfo(name = "taluka")
    private String taluka;
    @ColumnInfo(name = "farmer_name")
    private String farmer_name;
    @ColumnInfo(name = "farmer_mobile_no")
    private String farmer_mobile_no;
    @ColumnInfo(name = "expected_farmers")
    private String expected_farmers;
    @ColumnInfo(name = "expected_dealers")
    private String expected_dealers;
    @ColumnInfo(name = "expected_distributer")
    private String expected_distributer;
    @ColumnInfo(name = "event_cover_villages")
    private String event_cover_villages;
    @ColumnInfo(name = "created_on")
    private String created_on;
    @ColumnInfo(name = "created_by")
    private String created_by;
    @ColumnInfo(name = "approver_email")
    private String approver_email;
    @ColumnInfo(name = "status")
    private String status;
    @ColumnInfo(name = "reject_reason")
    private String reject_reason;

    public int getAndroid_event_code() {
        return android_event_code;
    }

    public void setAndroid_event_code(int android_event_code) {
        this.android_event_code = android_event_code;
    }

    public String getEvent_code() {
        return event_code;
    }

    public void setEvent_code(String event_code) {
        this.event_code = event_code;
    }

    public String getEvent_desc() {
        return event_desc;
    }

    public void setEvent_desc(String event_desc) {
        this.event_desc = event_desc;
    }

    public String getEvent_date() {
        return event_date;
    }

    public void setEvent_date(String event_date) {
        this.event_date = event_date;
    }

    public String getEvent_type() {
        return event_type;
    }

    public void setEvent_type(String event_type) {
        this.event_type = event_type;
    }

    public String getEvent_budget() {
        return event_budget;
    }

    public void setEvent_budget(String event_budget) {
        this.event_budget = event_budget;
    }

    public String getCrop() {
        return crop;
    }

    public void setCrop(String crop) {
        this.crop = crop;
    }

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getTaluka() {
        return taluka;
    }

    public void setTaluka(String taluka) {
        this.taluka = taluka;
    }

    public String getFarmer_name() {
        return farmer_name;
    }

    public void setFarmer_name(String farmer_name) {
        this.farmer_name = farmer_name;
    }

    public String getFarmer_mobile_no() {
        return farmer_mobile_no;
    }

    public void setFarmer_mobile_no(String farmer_mobile_no) {
        this.farmer_mobile_no = farmer_mobile_no;
    }

    public String getExpected_farmers() {
        return expected_farmers;
    }

    public void setExpected_farmers(String expected_farmers) {
        this.expected_farmers = expected_farmers;
    }

    public String getExpected_dealers() {
        return expected_dealers;
    }

    public void setExpected_dealers(String expected_dealers) {
        this.expected_dealers = expected_dealers;
    }

    public String getExpected_distributer() {
        return expected_distributer;
    }

    public void setExpected_distributer(String expected_distributer) {
        this.expected_distributer = expected_distributer;
    }

    public String getEvent_cover_villages() {
        return event_cover_villages;
    }

    public void setEvent_cover_villages(String event_cover_villages) {
        this.event_cover_villages = event_cover_villages;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getApprover_email() {
        return approver_email;
    }

    public void setApprover_email(String approver_email) {
        this.approver_email = approver_email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReject_reason() {
        return reject_reason;
    }

    public void setReject_reason(String reject_reason) {
        this.reject_reason = reject_reason;
    }

    public String getApprove_on() {
        return approve_on;
    }

    public void setApprove_on(String approve_on) {
        this.approve_on = approve_on;
    }

    public String getCrop_name() {
        return crop_name;
    }

    public void setCrop_name(String crop_name) {
        this.crop_name = crop_name;
    }

    public String getVariety_name() {
        return variety_name;
    }

    public void setVariety_name(String variety_name) {
        this.variety_name = variety_name;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }

    public String getTaluka_name() {
        return taluka_name;
    }

    public void setTaluka_name(String taluka_name) {
        this.taluka_name = taluka_name;
    }

    @ColumnInfo(name = "approve_on")
    private String approve_on;
    @ColumnInfo(name = "crop_name")
    private String crop_name;
    @ColumnInfo(name = "variety_name")
    private String variety_name;
    @ColumnInfo(name = "state_name")
    private String state_name;
    @ColumnInfo(name = "district_name")
    private String district_name;
    @ColumnInfo(name = "taluka_name")
    private String taluka_name;

    @ColumnInfo(name = "actual_farmers")
    private String actual_farmers;
    @ColumnInfo(name = "actual_distributers")
    private String actual_distributers;

    public String getActual_farmers() {
        return actual_farmers;
    }

    public void setActual_farmers(String actual_farmers) {
        this.actual_farmers = actual_farmers;
    }

    public String getActual_distributers() {
        return actual_distributers;
    }

    public void setActual_distributers(String actual_distributers) {
        this.actual_distributers = actual_distributers;
    }

    public String getActual_dealers() {
        return actual_dealers;
    }

    public void setActual_dealers(String actual_dealers) {
        this.actual_dealers = actual_dealers;
    }

    @ColumnInfo(name = "actual_dealers")
    private String actual_dealers;

    public static EventManagemantTable getEventManagmentData(EventManagemantModel eventManagemantModel ){
        EventManagemantTable eventManagemantTable=new EventManagemantTable();

        eventManagemantTable. event_code=eventManagemantModel.event_code;
        eventManagemantTable. event_desc=eventManagemantModel.event_desc;
        eventManagemantTable. event_date=eventManagemantModel.event_date;
        eventManagemantTable. event_type=eventManagemantModel.event_type;
        eventManagemantTable. event_budget=eventManagemantModel.event_budget;
        eventManagemantTable. crop=eventManagemantModel.crop;
        eventManagemantTable. variety=eventManagemantModel.variety;
        eventManagemantTable. state=eventManagemantModel.state;
        eventManagemantTable. district=eventManagemantModel.district;
        eventManagemantTable. village=eventManagemantModel.village;
        eventManagemantTable. taluka=eventManagemantModel.taluka;
        eventManagemantTable. farmer_name=eventManagemantModel.farmer_name;
        eventManagemantTable. farmer_mobile_no=eventManagemantModel.farmer_mobile_no;
        eventManagemantTable. expected_farmers=eventManagemantModel.expected_farmers;
        eventManagemantTable. expected_dealers=eventManagemantModel.expected_dealers;
        eventManagemantTable. expected_distributer=eventManagemantModel.expected_distributer;
        eventManagemantTable. event_cover_villages=eventManagemantModel.event_cover_villages;
        eventManagemantTable. created_on=eventManagemantModel.created_on;
        eventManagemantTable. created_by=eventManagemantModel.created_by;
        eventManagemantTable. approver_email=eventManagemantModel.approver_email;
        eventManagemantTable. status=eventManagemantModel.status;
        eventManagemantTable. reject_reason=eventManagemantModel.reject_reason;
        eventManagemantTable. approve_on=eventManagemantModel.approve_on;

        eventManagemantTable. crop_name=eventManagemantModel.crop_name;
        eventManagemantTable. variety_name=eventManagemantModel.variety_name;
        eventManagemantTable. state_name=eventManagemantModel.state_name;
        eventManagemantTable. district_name=eventManagemantModel.district_name;
        eventManagemantTable. taluka_name=eventManagemantModel.taluka_name;
        eventManagemantTable.actual_farmers=eventManagemantModel.actual_farmers;
        eventManagemantTable.actual_dealers=eventManagemantModel.actual_dealers;
        eventManagemantTable.actual_distributers=eventManagemantModel.actual_distributers;
        return eventManagemantTable;

    }



    public static EventManagemantTable getEventManagmentDataOnDetailPage(SyncEventDetailModel eventDetail_sync_model ){
        EventManagemantTable eventManagemantTable=new EventManagemantTable();

        eventManagemantTable. event_code=eventDetail_sync_model.event_code;
        eventManagemantTable. event_desc=eventDetail_sync_model.event_desc;
        eventManagemantTable. event_date=eventDetail_sync_model.event_date;
        eventManagemantTable. event_type=eventDetail_sync_model.event_type;
        eventManagemantTable. event_budget=eventDetail_sync_model.event_budget;
        eventManagemantTable. crop=eventDetail_sync_model.crop;
        eventManagemantTable. variety=eventDetail_sync_model.variety;
        eventManagemantTable. state=eventDetail_sync_model.state;
        eventManagemantTable. district=eventDetail_sync_model.district;
        eventManagemantTable. village=eventDetail_sync_model.village;
        eventManagemantTable. taluka=eventDetail_sync_model.taluka;
        eventManagemantTable. farmer_name=eventDetail_sync_model.farmer_name;
        eventManagemantTable. farmer_mobile_no=eventDetail_sync_model.farmer_mobile_no;
        eventManagemantTable. expected_farmers=eventDetail_sync_model.expected_farmers;
        eventManagemantTable. expected_dealers=eventDetail_sync_model.expected_dealers;
        eventManagemantTable. expected_distributer=eventDetail_sync_model.expected_distributer;
        eventManagemantTable. event_cover_villages=eventDetail_sync_model.event_cover_villages;
        eventManagemantTable. created_on=eventDetail_sync_model.created_on;
        eventManagemantTable. created_by=eventDetail_sync_model.created_by;
        eventManagemantTable. approver_email=eventDetail_sync_model.approver_email;
        eventManagemantTable. status=eventDetail_sync_model.status;
        eventManagemantTable. reject_reason=eventDetail_sync_model.reject_reason;
        eventManagemantTable. approve_on=eventDetail_sync_model.approve_on;

        eventManagemantTable. crop_name=eventDetail_sync_model.crop_name;
        eventManagemantTable. variety_name=eventDetail_sync_model.crop_hybrid;
        eventManagemantTable. state_name=eventDetail_sync_model.state_name;
        eventManagemantTable. district_name=eventDetail_sync_model.district_name;
        eventManagemantTable. taluka_name=eventDetail_sync_model.taluka_name;
        eventManagemantTable.actual_farmers=eventDetail_sync_model.actual_farmers;
        eventManagemantTable.actual_dealers=eventDetail_sync_model.actual_dealers;
        eventManagemantTable.actual_distributers=eventDetail_sync_model.actual_distributers;
        return eventManagemantTable;

    }
}
