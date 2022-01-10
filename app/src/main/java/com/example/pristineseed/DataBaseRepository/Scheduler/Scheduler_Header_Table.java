package com.example.pristineseed.DataBaseRepository.Scheduler;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.pristineseed.model.scheduler_inspection.SchedulerModel;

import java.util.ArrayList;

@Entity(tableName = "scheduler_header_table")
public class Scheduler_Header_Table {

   /* public int getAndroid_id() {
        return android_id;
    }

    public void setAndroid_id(int android_id) {
        this.android_id = android_id;
    }*/

/*    @NonNull
@PrimaryKey(autoGenerate = true)
 private int android_id;*/


    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "schedule_no")
    private String schedule_no;
    @ColumnInfo(name = "user_id")
   private String user_id;
    @ColumnInfo(name = "season")
   private String season;
    @ColumnInfo(name = "season_name")
   private String season_name;
    @ColumnInfo(name = "production_centre")
   private String production_centre;

    @NonNull
    public String getSchedule_no() {
        return schedule_no;
    }

    public void setSchedule_no(String schedule_no) {
        this.schedule_no = schedule_no;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getSeason_name() {
        return season_name;
    }

    public void setSeason_name(String season_name) {
        this.season_name = season_name;
    }

    public String getProduction_centre() {
        return production_centre;
    }

    public void setProduction_centre(String production_centre) {
        this.production_centre = production_centre;
    }

    public String getProduction_centre_name() {
        return production_centre_name;
    }

    public void setProduction_centre_name(String production_centre_name) {
        this.production_centre_name = production_centre_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @ColumnInfo(name = "production_centre_name")
    private String production_centre_name;
    @ColumnInfo(name = "date")
    private String date;
    @ColumnInfo(name = "status")
    private String status;

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    @ColumnInfo(name = "user_type")
    private String user_type;


    public static Scheduler_Header_Table getThisTypeOfData(SchedulerModel passmodel) {
        Scheduler_Header_Table scheduelInspectionHeaderTable = new Scheduler_Header_Table();
        scheduelInspectionHeaderTable.setSchedule_no(passmodel.schedule_no);
        scheduelInspectionHeaderTable.setUser_id(passmodel.user_id);
        scheduelInspectionHeaderTable.setSeason(passmodel.season);
        scheduelInspectionHeaderTable.setSeason_name(passmodel.season_name);
        scheduelInspectionHeaderTable.setProduction_centre(passmodel.production_centre);
        scheduelInspectionHeaderTable.setProduction_centre_name(passmodel.production_centre_name);
        scheduelInspectionHeaderTable.setDate(passmodel.date);
        scheduelInspectionHeaderTable.setUser_type(passmodel.user_type);
        scheduelInspectionHeaderTable.setStatus(passmodel.status);

        return scheduelInspectionHeaderTable;
    }

    public static SchedulerModel getServerFormat(Scheduler_Header_Table passData) {
        SchedulerModel scheduleInspectionModel = new SchedulerModel();
        scheduleInspectionModel.schedule_no = passData.getSchedule_no();
        scheduleInspectionModel.user_id = passData.getUser_id();
        scheduleInspectionModel.season = passData.getSeason();
        scheduleInspectionModel.season_name = passData.getSeason_name();
        scheduleInspectionModel.production_centre = passData.getProduction_centre();
        scheduleInspectionModel.production_centre_name = passData.getProduction_centre_name();
        scheduleInspectionModel.date = passData.getDate();
        scheduleInspectionModel.status = passData.getStatus();
        scheduleInspectionModel.scheduler_lines=new ArrayList<>();
        return scheduleInspectionModel;
    }

}
