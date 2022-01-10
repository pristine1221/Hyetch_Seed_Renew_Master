package com.example.pristineseed.DataBaseRepository.Planting;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.pristineseed.DataBaseRepository.Scheduler.Scheduler_Header_Table;
import com.example.pristineseed.model.PlantingModel.PlantingHeaderModel;
import com.example.pristineseed.model.scheduler_inspection.SchedulerModel;

import java.util.ArrayList;

@Entity(tableName = "Planting_header_table")
public class PlantingDetailHeaderTable {
    public int getAndroid_id() {
        return android_id;
    }

    public void setAndroid_id(int android_id) {
        this.android_id = android_id;
    }

    private int android_id;


        @NonNull
        @PrimaryKey
         @ColumnInfo(name = "code")
         private String code;

         @ColumnInfo(name = "production_centre_loc")
         private String production_centre_loc;

    public String getCode() {
        return code;
    }

    public  void setCode(String code) {
        this.code = code;
    }

    public String getProduction_centre_loc() {
        return production_centre_loc;
    }

    public void setProduction_centre_loc(String production_centre_loc) {
        this.production_centre_loc = production_centre_loc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate_of_harvest() {
        return date_of_harvest;
    }

    public void setDate_of_harvest(String date_of_harvest) {
        this.date_of_harvest = date_of_harvest;
    }

    public String getSeason_code() {
        return season_code;
    }

    public void setSeason_code(String season_code) {
        this.season_code = season_code;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNav_sync() {
        return nav_sync;
    }

    public void setNav_sync(String nav_sync) {
        this.nav_sync = nav_sync;
    }

    public String getNav_message() {
        return nav_message;
    }

    public void setNav_message(String nav_message) {
        this.nav_message = nav_message;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStage_code() {
        return stage_code;
    }

    public void setStage_code(String stage_code) {
        this.stage_code = stage_code;
    }

    public String getTotal_sowing_area_in_acres() {
        return total_sowing_area_in_acres;
    }

    public void setTotal_sowing_area_in_acres(String total_sowing_area_in_acres) {
        this.total_sowing_area_in_acres = total_sowing_area_in_acres;
    }

    public String getTotal_land_in_acres() {
        return total_land_in_acres;
    }

    public void setTotal_land_in_acres(String total_land_in_acres) {
        this.total_land_in_acres = total_land_in_acres;
    }

    public String getParent_type() {
        return parent_type;
    }

    public void setParent_type(String parent_type) {
        this.parent_type = parent_type;
    }

    public String getDocument_SubType() {
        return Document_SubType;
    }

    public void setDocument_SubType(String document_SubType) {
        Document_SubType = document_SubType;
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

    public String getCompleted_on() {
        return completed_on;
    }

    public void setCompleted_on(String completed_on) {
        this.completed_on = completed_on;
    }

         @ColumnInfo(name = "date")
         private String date;
         @ColumnInfo(name = "date_of_harvest")
         private String date_of_harvest;
         @ColumnInfo(name = "season_code")
         private String season_code;
         @ColumnInfo(name = "status")
         private int status;
         @ColumnInfo(name = "nav_sync")
         private String nav_sync;
         @ColumnInfo(name = "nav_message")
         private String nav_message;

         @Ignore
         @ColumnInfo(name = "type")
         private String type;
         @ColumnInfo(name = "stage_code")
         private String stage_code;
         @ColumnInfo(name = "total_sowing_area_in_acres")
         private String total_sowing_area_in_acres;
         @ColumnInfo(name = "total_land_in_acres")
         private String total_land_in_acres;

         @ColumnInfo(name = "parent_type")
         private String parent_type;

         @ColumnInfo(name = "Document_SubType")
         private String Document_SubType;
         @ColumnInfo(name = "created_by")
         private String created_by;

         @ColumnInfo(name = "created_on")
         private String created_on;

         @ColumnInfo(name = "completed_on")
         private String completed_on;

         public String getHybrid() {
        return Hybrid;
    }

        public void setHybrid(String hybrid) {
        Hybrid = hybrid;
    }

        @ColumnInfo(name = "Hybrid")
        private String Hybrid;

    public int getDelete_line_header() {
        return delete_line_header;
    }
    public void setDelete_line_header(int delete_line_header) {
        this.delete_line_header = delete_line_header;
    }

    @ColumnInfo(name = "delete_line_header")
     private int delete_line_header;

    public int getComplete_planting_local_status() {
        return complete_planting_local_status;
    }

    public void setComplete_planting_local_status(int complete_planting_local_status) {
        this.complete_planting_local_status = complete_planting_local_status;
    }

    @ColumnInfo(name = "complete_planting_local_status")
    private int complete_planting_local_status;

    public String getOrganizer_code() {
        return organizer_code;
    }

    public void setOrganizer_code(String organizer_code) {
        this.organizer_code = organizer_code;
    }

    public String getOrganizer_name() {
        return organizer_name;
    }

    public void setOrganizer_name(String organizer_name) {
        this.organizer_name = organizer_name;
    }

    @ColumnInfo(name = "organizer_code")
   private String organizer_code;
    @ColumnInfo(name = "organizer_name")
   private String organizer_name;

         public static  PlantingDetailHeaderTable getPlantingHeaderData(PlantingHeaderModel plantingHeaderModel){
         PlantingDetailHeaderTable plantingDetailHeaderTable=new PlantingDetailHeaderTable();
           plantingDetailHeaderTable.code=plantingHeaderModel.code;
           plantingDetailHeaderTable.production_centre_loc=plantingHeaderModel.production_centre_loc;
           plantingDetailHeaderTable.date=plantingHeaderModel.date;
           plantingDetailHeaderTable.date_of_harvest=plantingHeaderModel.date_of_harvest;
           plantingDetailHeaderTable.season_code=plantingHeaderModel.season_code;
           plantingDetailHeaderTable.status=plantingHeaderModel.status;
           plantingDetailHeaderTable.nav_sync=plantingHeaderModel.nav_sync;
           plantingDetailHeaderTable.nav_message=plantingHeaderModel.nav_message;
          // plantingDetailHeaderTable.type=plantingHeaderModel.type;
           plantingDetailHeaderTable.stage_code=plantingHeaderModel.stage_code;
           plantingDetailHeaderTable.total_sowing_area_in_acres=plantingHeaderModel.total_sowing_area_in_acres;
           plantingDetailHeaderTable.total_land_in_acres=plantingHeaderModel.total_land_in_acres;
           plantingDetailHeaderTable.parent_type=plantingHeaderModel.parent_type;
           plantingDetailHeaderTable.Document_SubType=plantingHeaderModel.Document_SubType;
           plantingDetailHeaderTable.created_by =plantingHeaderModel.created_by;
           plantingDetailHeaderTable.created_on=plantingHeaderModel.created_on;
           plantingDetailHeaderTable.completed_on=plantingHeaderModel.completed_on;
           plantingDetailHeaderTable.organizer_code=plantingHeaderModel.organizer_code;
           plantingDetailHeaderTable.organizer_name=plantingHeaderModel.organizer_name;
           return plantingDetailHeaderTable;
         }

    public static PlantingHeaderModel getServerFormat(PlantingDetailHeaderTable passData) {
        PlantingHeaderModel plantingHeaderModel = new PlantingHeaderModel();
        plantingHeaderModel.code= passData.getCode();
        plantingHeaderModel.production_centre_loc= passData.getProduction_centre_loc();
        plantingHeaderModel.date=passData.date;
        plantingHeaderModel.date_of_harvest = passData.getDate_of_harvest();
        plantingHeaderModel.season_code = passData.getSeason_code();
        plantingHeaderModel.status= passData.getStatus();
        plantingHeaderModel.nav_sync=passData.getNav_sync();
        plantingHeaderModel.nav_message=passData.getNav_message();
       // plantingHeaderModel.type=passData.getType();
        plantingHeaderModel.stage_code=passData.getStage_code();
        plantingHeaderModel.total_sowing_area_in_acres=passData.getTotal_sowing_area_in_acres();
        plantingHeaderModel.total_land_in_acres=passData.getTotal_land_in_acres();
        plantingHeaderModel.Document_SubType=passData.getDocument_SubType();
        plantingHeaderModel.parent_type=passData.getParent_type();
        plantingHeaderModel.created_by=passData.getCreated_by();
        plantingHeaderModel.created_on=passData.getCreated_on();
        plantingHeaderModel.organizer_code=passData.getOrganizer_code();
        plantingHeaderModel.organizer_name=passData.getOrganizer_name();
        plantingHeaderModel.pl=new ArrayList<>();
        return plantingHeaderModel;
    }
}
