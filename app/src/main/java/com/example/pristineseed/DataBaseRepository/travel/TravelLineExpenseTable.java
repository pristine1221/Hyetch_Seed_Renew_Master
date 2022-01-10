package com.example.pristineseed.DataBaseRepository.travel;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


import com.example.pristineseed.model.travel.ta_da_model.SyncTravelDetailModel;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "travel_line_expance_table")
public class TravelLineExpenseTable {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int android_travelcode;
    @NonNull
    @ColumnInfo(name = "travelcode")
    private String travelcode;


    @ColumnInfo(name = "line_no")
    private String line_no;

    @ColumnInfo(name = "create_on")
    private String createOn;

    public String getCreateOn() {
        return createOn;
    }

    public void setCreateOn(String createOn) {
        this.createOn = createOn;
    }



    public int getAndroid_travelcode() {
        return android_travelcode;
    }

    public void setAndroid_travelcode(int android_travelcode) {
        this.android_travelcode = android_travelcode;
    }

    public String getTravelcode() {
        return travelcode;
    }

    public void setTravelcode( String travelcode) {
        this.travelcode = travelcode;
    }

    public String getLine_no() {
        return line_no;
    }

    public void setLine_no( String line_no) {
        this.line_no = line_no;
    }


    public String getDate() {
        return date;
    }

    public void setDate( String date) {
        this.date = date;
    }

    public String getFrom_loc() {
        return from_loc;
    }

    public void setFrom_loc( String from_loc) {
        this.from_loc = from_loc;
    }


    public String getTo_loc() {
        return to_loc;
    }

    public void setTo_loc( String to_loc) {
        this.to_loc = to_loc;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture( String departure) {
        this.departure = departure;
    }


    public String getArrival() {
        return arrival;
    }

    public void setArrival( String arrival) {
        this.arrival = arrival;
    }


    public String getFare() {
        return fare;
    }

    public void setFare( String fare) {
        this.fare = fare;
    }


    public String getMode_of_travel() {
        return mode_of_travel;
    }

    public void setMode_of_travel(String mode_of_travel) {
        this.mode_of_travel = mode_of_travel;
    }

    @NonNull
    public String getLoading_in_any() {
        return loading_in_any;
    }

    public void setLoading_in_any( String loading_in_any) {
        this.loading_in_any = loading_in_any;
    }


    public String getDistance_km() {
        return distance_km;
    }

    public void setDistance_km( String distance_km) {
        this.distance_km = distance_km;
    }


    public String getFuel_vehicle_expance() {
        return fuel_vehicle_expance;
    }

    public void setFuel_vehicle_expance( String fuel_vehicle_expance) {
        this.fuel_vehicle_expance = fuel_vehicle_expance;
    }


    public String getDaily_express() {
        return daily_express;
    }

    public void setDaily_express( String daily_express) {
        this.daily_express = daily_express;
    }


    public String getVehicle_repairing() {
        return vehicle_repairing;
    }

    public void setVehicle_repairing( String vehicle_repairing) {
        this.vehicle_repairing = vehicle_repairing;
    }


    public String getLocal_convance() {
        return local_convance;
    }

    public void setLocal_convance( String local_convance) {
        this.local_convance = local_convance;
    }


    public String getOther_expenses() {
        return other_expenses;
    }

    public void setOther_expenses( String other_expenses) {
        this.other_expenses = other_expenses;
    }


    public String getTotal_amount_calulated() {
        return total_amount_calulated;
    }

    public void setTotal_amount_calulated( String total_amount_calulated) {
        this.total_amount_calulated = total_amount_calulated;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public String getLineSend() {
        return lineSend;
    }

    public void setLineSend(String lineSend) {
        this.lineSend = lineSend;
    }

    @NonNull
    @ColumnInfo(name = "date")
    private String date;

    @NonNull
    @ColumnInfo(name = "from_loc")
    private String from_loc;

    @NonNull
    @ColumnInfo(name = "to_loc")
    private String to_loc;
    @NonNull
    @ColumnInfo(name = "departure")
    private String departure;
    @NonNull
    @ColumnInfo(name = "arrival")
    private String arrival;
    @NonNull
    @ColumnInfo(name = "fare")
    private String fare;
    @NonNull
    @ColumnInfo(name = "mode_of_travel")
    private String mode_of_travel;
    @NonNull
    @ColumnInfo(name = "loading_in_any")
    private String loading_in_any;
    @NonNull
    @ColumnInfo(name = "distance_km")
    private String distance_km;
    @NonNull
    @ColumnInfo(name = "fuel_vehicle_expance")
    private String fuel_vehicle_expance;
    @NonNull
    @ColumnInfo(name = "daily_express")
    private String daily_express;
    @NonNull
    @ColumnInfo(name = "vehicle_repairing")
    private String vehicle_repairing;
    @NonNull
    @ColumnInfo(name = "local_convance")
    private String local_convance;
    @NonNull
    @ColumnInfo(name = "other_expenses")
    private String other_expenses;
    @NonNull
    @ColumnInfo(name = "total_amount_calulated")
    private String total_amount_calulated;

    @ColumnInfo(name = "created_on")
    private String created_on;

    @ColumnInfo(name = "lineSend")
    private String lineSend;
    @ColumnInfo(name = "from_loc_name")
    private String from_loc_name;

    public String getFrom_loc_name() {
        return from_loc_name;
    }

    public void setFrom_loc_name(String from_loc_name) {
        this.from_loc_name = from_loc_name;
    }

    public String getTo_loc_name() {
        return to_loc_name;
    }

    public void setTo_loc_name(String to_loc_name) {
        this.to_loc_name = to_loc_name;
    }

    @ColumnInfo(name = "to_loc_name")
    private String to_loc_name;

    @ColumnInfo(name = "mod_city")
    private String mod_city;
    @ColumnInfo(name = "mod_lodging")
    private String mod_lodging;
    @ColumnInfo(name = "mod_da_half")
    private String mod_da_half;

    public String getMod_city() {
        return mod_city;
    }

    public void setMod_city(String mod_city) {
        this.mod_city = mod_city;
    }

    public String getMod_lodging() {
        return mod_lodging;
    }

    public void setMod_lodging(String mod_lodging) {
        this.mod_lodging = mod_lodging;
    }

    public String getMod_da_half() {
        return mod_da_half;
    }

    public void setMod_da_half(String mod_da_half) {
        this.mod_da_half = mod_da_half;
    }

    public String getMode_da_full() {
        return mode_da_full;
    }

    public void setMode_da_full(String mode_da_full) {
        this.mode_da_full = mode_da_full;
    }

    public String getMod_ope_max() {
        return mod_ope_max;
    }

    public void setMod_ope_max(String mod_ope_max) {
        this.mod_ope_max = mod_ope_max;
    }

    public String getUser_grade() {
        return user_grade;
    }

    public void setUser_grade(String user_grade) {
        this.user_grade = user_grade;
    }

    @ColumnInfo(name = "mode_da_full")
    private String mode_da_full;
    @ColumnInfo(name = "mod_ope_max")
    private String mod_ope_max;
    @ColumnInfo(name = "user_grade")
    private String user_grade;

    @ColumnInfo(name = "expence_type")
    private String expence_type;
    public String getExpence_type() { return expence_type; }
    public void setExpence_type(String expence_type) { this.expence_type = expence_type; }

    @ColumnInfo(name = "from_km")
    private String from_km;
    public String getFrom_km() { return from_km;}
    public void setFrom_km(String from_km) { this.from_km = from_km; }

    @ColumnInfo(name = "to_km")
    private String to_km;
    public String getTo_km() { return to_km; }
    public void setTo_km(String to_km) { this.to_km = to_km; }

    @ColumnInfo(name = "total_km")
    private String total_km;
    public String getTotal_km() { return total_km; }
    public void setTotal_km(String total_km) { this.total_km = total_km; }

    @ColumnInfo(name = "total_km_amt")
    private String total_km_amt;
    public String getTotal_km_amt() { return total_km_amt; }
    public void setTotal_km_amt(String total_km_amt) { this.total_km_amt = total_km_amt; }

    @ColumnInfo(name = "rupees_per_km")
    private String rupees_per_km;
    public String getRupees_per_km() { return rupees_per_km; }
    public void setRupees_per_km(String rupees_per_km) { this.rupees_per_km = rupees_per_km; }

    @ColumnInfo(name = "remarks")
    private String remarks;
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    @ColumnInfo(name = "attachment")
    private String attachment;
    public String getAttachment() { return attachment; }
    public void setAttachment(String attachment) { this.attachment = attachment; }


    @Ignore
    @ColumnInfo(name = "adavanced_tour_plan ")
    private String adavanced_tour_plan;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Ignore
    @ColumnInfo(name = "type ")
    private String type;

    public String getAdavanced_tour_plan() {
        return adavanced_tour_plan;
    }

    public void setAdavanced_tour_plan(String adavanced_tour_plan) {
        this.adavanced_tour_plan = adavanced_tour_plan;
    }

    @ColumnInfo(name = "food_expenses")
    private String food_expenses;

    @ColumnInfo(name = "printing_and_stationory ")
    private String printing_and_stationory;

    public String getFood_expenses() {
        return food_expenses;
    }

    public void setFood_expenses(String food_expenses) {
        this.food_expenses = food_expenses;
    }

    public String getPrinting_and_stationory() {
        return printing_and_stationory;
    }

    public void setPrinting_and_stationory(String printing_and_stationory) {
        this.printing_and_stationory = printing_and_stationory;
    }

    public String getTelephone_expenses() {
        return telephone_expenses;
    }

    public void setTelephone_expenses(String telephone_expenses) {
        this.telephone_expenses = telephone_expenses;
    }

    @ColumnInfo(name = "telephone_expenses")
    private String telephone_expenses;




}
