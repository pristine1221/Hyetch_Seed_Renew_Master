package com.example.pristineseed.model.travel.travel_view_header;

import com.example.pristineseed.model.travel.ta_da_model.SyncTravelDetailModel;

import java.util.ArrayList;
import java.util.List;

public class TravelHeaderModel {
    public int android_travelcode;
    public String travelcode;
    public String from_loc;
    public String to_loc;
    public String start_date;
    public String end_date;
    public String travel_reson;
    public String expense_budget;
    public String approve_budget;
    public String created_on;
    public String created_by;
    public String status;
    public String STATUS;
    public String approver_id;
    public String approve_on;
    public String reason;
    public String user_type;
    public String type;
    public String adavanced_tour_plan;

    public String from_loc_name;
    public String to_loc_name;
    public List<SyncTravelDetailModel.Travel_line_Expense> travel_line_expense;



    public class Travel_Line_Attachments {
        public String id;
        public String attachment;

    }
}