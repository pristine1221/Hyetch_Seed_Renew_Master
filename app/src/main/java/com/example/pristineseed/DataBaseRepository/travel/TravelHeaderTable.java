package com.example.pristineseed.DataBaseRepository.travel;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.pristineseed.model.travel.ta_da_model.SyncTravelDetailModel;
import com.example.pristineseed.model.travel.travel_view_header.TravelHeaderModel;

@Entity(tableName = "travel_header_table")
public class TravelHeaderTable {

    public int getAndroid_travelcode() { return android_travelcode; }
    public void setAndroid_travelcode(int android_travelcode) { this.android_travelcode = android_travelcode; }

    @PrimaryKey(autoGenerate = true)
    private int android_travelcode;


    @ColumnInfo(name = "email")
    private String email;
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @ColumnInfo(name = "from_loc")
    private String from_loc;
    public String getFrom_loc() { return from_loc; }
    public void setFrom_loc(String from_loc) { this.from_loc = from_loc; }

    @ColumnInfo(name = "to_loc")
    private String to_loc;
    public String getTo_loc() { return to_loc; }
    public void setTo_loc(String to_loc) { this.to_loc = to_loc; }

    @ColumnInfo(name = "start_date")
    private String start_date;
    public String getStart_date() { return start_date; }
    public void setStart_date(String start_date) { this.start_date = start_date; }

    @ColumnInfo(name = "end_date")
    private String end_date;
    public String getEnd_date() { return end_date; }
    public void setEnd_date(String end_date) { this.end_date = end_date; }

    @ColumnInfo(name = "travel_reson")
    private String travel_reson;
    public String getTravel_reson() { return travel_reson; }
    public void setTravel_reson(String travel_reson) { this.travel_reson = travel_reson; }

    @ColumnInfo(name = "expense_budget")
    private String expense_budget;
    public String getExpense_budget() { return expense_budget; }
    public void setExpense_budget(String expense_budget) { this.expense_budget = expense_budget; }

    @ColumnInfo(name = "approver_id")
    private String approver_id;
    public String getApprover_id() { return approver_id; }
    public void setApprover_id(String approver_id) { this.approver_id = approver_id; }

    @ColumnInfo(name = "user_type")
    private String user_type;
    public String getUser_type() { return user_type; }
    public void setUser_type(String user_type) { this.user_type = user_type; }

    @ColumnInfo(name = "travelcode")
    private String travelcode;
    public String getTravelcode() { return travelcode; }
    public void setTravelcode(String travelcode) { this.travelcode = travelcode; }

    @ColumnInfo(name = "status")
    private String status;
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @ColumnInfo(name = "created_on")
    private String created_on;

    public String getCreated_on() { return created_on; }
    public void setCreated_on(String created_on) { this.created_on = created_on; }

    @ColumnInfo(name = "created_by")
    private String created_by;
    public String getCreated_by() { return created_by; }
    public void setCreated_by(String created_by) { this.created_by = created_by; }


    public String from_loc_name;
    public String to_loc_name;

    @ColumnInfo(name = "approve_on")
    private String approve_on;


    public String getApprove_on() {
        return approve_on;
    }

    public void setApprove_on(String approve_on) {
        this.approve_on = approve_on;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @ColumnInfo(name = "reason")
    private String reason;

    @ColumnInfo(name = "approve_budget")
    public String approve_budget;

    public String getApprove_budget() {
        return approve_budget;
    }

    public void setApprove_budget(String approve_budget) {
        this.approve_budget = approve_budget;
    }

    public static  TravelHeaderTable getTravelHederData(TravelHeaderModel travelHeaderModel){
        TravelHeaderTable travelHeaderTable=new TravelHeaderTable();
        travelHeaderTable.travelcode=travelHeaderModel.travelcode;
        travelHeaderTable.from_loc=travelHeaderModel.from_loc;
        travelHeaderTable.to_loc=travelHeaderModel.to_loc;
        travelHeaderTable.start_date=travelHeaderModel.start_date;
        travelHeaderTable.end_date=travelHeaderModel.end_date;
        travelHeaderTable.travel_reson=travelHeaderModel.travel_reson;
        travelHeaderTable.expense_budget=travelHeaderModel.expense_budget;
        travelHeaderTable.approve_budget=travelHeaderModel.approve_budget;
        travelHeaderTable.created_on=travelHeaderModel.created_on;
        travelHeaderTable.created_by=travelHeaderModel.created_by;
        travelHeaderTable.status=travelHeaderModel.status;
        travelHeaderTable.approver_id=travelHeaderModel.approver_id;
        travelHeaderTable.approve_on=travelHeaderModel.approve_on;
        travelHeaderTable.reason=travelHeaderModel.reason;
   /*   travelHeaderTable.from_loc_name=travelHeaderModel.from_loc_name;
      travelHeaderTable.to_loc_name=travelHeaderModel.to_loc_name;*/
        return travelHeaderTable;
    }
    public static  TravelHeaderTable getTravelHederSyncData( SyncTravelDetailModel travelHeaderModel){
        TravelHeaderTable travelHeaderTable=new TravelHeaderTable();
        travelHeaderTable.travelcode=travelHeaderModel.travelcode;
        travelHeaderTable.from_loc=travelHeaderModel.from_loc;
        travelHeaderTable.to_loc=travelHeaderModel.to_loc;
        travelHeaderTable.start_date=travelHeaderModel.start_date;
        travelHeaderTable.end_date=travelHeaderModel.end_date;
        travelHeaderTable.travel_reson=travelHeaderModel.travel_reson;
        travelHeaderTable.expense_budget=travelHeaderModel.expense_budget;
        travelHeaderTable.approve_budget=travelHeaderModel.approve_budget;
        travelHeaderTable.created_on=travelHeaderModel.created_on;
        travelHeaderTable.created_by=travelHeaderModel.created_by;
        travelHeaderTable.status=travelHeaderModel.STATUS;
        travelHeaderTable.approver_id=travelHeaderModel.approver_id;
        travelHeaderTable.approve_on=travelHeaderModel.approve_on;
        travelHeaderTable.reason=travelHeaderModel.reason;
      /*  travelHeaderTable.from_loc_name=travelHeaderModel.from_loc_name;
        travelHeaderTable.to_loc_name=travelHeaderModel.to_loc_name;*/
        return travelHeaderTable;
    }


}
