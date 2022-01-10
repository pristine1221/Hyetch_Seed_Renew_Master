package com.example.pristineseed.model.travel.ta_da_model;

import java.util.ArrayList;
import java.util.List;

public class SyncTravelDetailModel {
    public boolean condition;
    public String message;
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
    public String STATUS;
    public String approver_id;
    public String approve_on;
    public String reason;
    public String from_loc_name;
    public String type;
    public String adavanced_tour_plan;
    public String to_loc_name;
    public List<Travel_line_Expense> travel_line_expense;

    public class Travel_line_Expense {
        public boolean condition;
        public String message;
        public String travelcode;
        public String line_no;
        public String date;
        public String from_loc;
        public String to_loc;
        public String departure;
        public String arrival;
        public String fare;
        public String mode_of_travel;
        public String loading_in_any;
        public String distance_km;
        public String fuel_vehicle_expance;
        public String daily_express;
        public String vehicle_repairing;
        public String local_convance;
        public String other_expenses;
        public String total_amount_calulated;
        public String created_on;
        public String expence_type;
        public String from_km;
        public String to_km;
        public String total_km;
        public String total_km_amt;
        public String rupees_per_km;
        public String remarks;

          public String mod_city;
          public String mod_lodging;
          public String mod_da_half;
          public String mode_da_full;
          public String mod_ope_max;
          public String user_grade;
          public String food_expenses;
          public String printing_and_stationory;
          public String telephone_expenses;
          public String toll_tax_expense;
          public String courier;
        public List<Travel_Line_Attachments>travel_line_attachments=new ArrayList<>();

    }


    public class Travel_Line_Attachments {
        public String id;
        public String attachment;

    }
}
