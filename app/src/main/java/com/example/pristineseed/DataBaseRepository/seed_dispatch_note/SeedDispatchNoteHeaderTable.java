package com.example.pristineseed.DataBaseRepository.seed_dispatch_note;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.pristineseed.DataBaseRepository.Planting.PlantingDetailHeaderTable;
import com.example.pristineseed.model.PlantingModel.PlantingHeaderModel;
import com.example.pristineseed.model.seed_dispatch_note.SeedDispatchHeaderModel;

import java.util.ArrayList;

@Entity(tableName = "seed_dispatch_note_header_table")
public class SeedDispatchNoteHeaderTable {
    public int getAndroid_id() {
        return android_id;
    }

    public void setAndroid_id(int android_id) {
        this.android_id = android_id;
    }
/*
    @NonNull
    @PrimaryKey(autoGenerate = true)*/
    private int android_id;


    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "dispatch_no")
    public String dispatch_no;
    @ColumnInfo(name = "date")
    public String date;

    @NonNull
    public String getDispatch_no() {
        return dispatch_no;
    }

    public void setDispatch_no(@NonNull String dispatch_no) {
        this.dispatch_no = dispatch_no;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFrom_location() {
        return from_location;
    }

    public void setFrom_location(String from_location) {
        this.from_location = from_location;
    }

    public String getTo_location() {
        return to_location;
    }

    public void setTo_location(String to_location) {
        this.to_location = to_location;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public String getTransporter() {
        return transporter;
    }

    public void setTransporter(String transporter) {
        this.transporter = transporter;
    }

    public String getOrganizer_name() {
        return organizer_name;
    }

    public void setOrganizer_name(String organizer_name) {
        this.organizer_name = organizer_name;
    }

    public String getOrganizer_code() {
        return organizer_code;
    }

    public void setOrganizer_code(String organizer_code) {
        this.organizer_code = organizer_code;
    }

    public String getTruck_number() {
        return truck_number;
    }

    public void setTruck_number(String truck_number) {
        this.truck_number = truck_number;
    }

    public String getSeason_code() {
        return season_code;
    }

    public void setSeason_code(String season_code) {
        this.season_code = season_code;
    }

    public String getCamp_at() {
        return camp_at;
    }

    public void setCamp_at(String camp_at) {
        this.camp_at = camp_at;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    @ColumnInfo(name = "from_location")
    public String from_location;
    @ColumnInfo(name = "to_location")
    public String to_location;
    @ColumnInfo(name = "supervisor")
    public String supervisor;
    @ColumnInfo(name = "transporter")
    public String transporter;
    @ColumnInfo(name = "organizer_name ")
    public String organizer_name;
    @ColumnInfo(name = "organizer_code")
    public String organizer_code;
    @ColumnInfo(name = "truck_number")
    public String truck_number;
    @ColumnInfo(name = "season_code")
    public String season_code;
    @ColumnInfo(name = "camp_at")
    public String camp_at;
    @ColumnInfo(name = "remarks")
    public String remarks;
    @ColumnInfo(name = "created_on")
    public String created_on;
    @ColumnInfo(name = "created_by")
    public String created_by;
    @ColumnInfo(name = "status")
    public int status;
    @ColumnInfo(name = "nav_sync")
    public String nav_sync;
    @ColumnInfo(name = "nav_message")
    public String nav_message;

    public String getDelete_line_header() {
        return delete_line_header;
    }

    public void setDelete_line_header(String delete_line_header) {
        this.delete_line_header = delete_line_header;
    }

    @ColumnInfo(name = "delete_line_header")
    public String delete_line_header;


    public int getComplete_dispatch_header_local_status() {
        return complete_dispatch_header_local_status;
    }

    public void setComplete_dispatch_header_local_status(int complete_dispatch_header_local_status) {
        this.complete_dispatch_header_local_status = complete_dispatch_header_local_status;
    }

    @ColumnInfo(name = "complete_dispatch_header_local_status")
    private int complete_dispatch_header_local_status;


    public String getDocument_type() {
        return document_type;
    }

    public void setDocument_type(String document_type) {
        this.document_type = document_type;
    }

    @ColumnInfo(name = "document_type")
    private String document_type;

    public String getRefrence_no() {
        return refrence_no;
    }

    public void setRefrence_no(String refrence_no) {
        this.refrence_no = refrence_no;
    }

    @ColumnInfo(name = "refrence_no")
    private String refrence_no;


    public static SeedDispatchNoteHeaderTable getDispatchHeaderData(SeedDispatchHeaderModel seedDispatchHeaderModel){
        SeedDispatchNoteHeaderTable seedDispatchNoteHeaderTable=new SeedDispatchNoteHeaderTable();
      seedDispatchNoteHeaderTable.dispatch_no=seedDispatchHeaderModel.dispatch_no;
      seedDispatchNoteHeaderTable.date=seedDispatchHeaderModel.date;
      seedDispatchNoteHeaderTable.from_location=seedDispatchHeaderModel.from_location;
      seedDispatchNoteHeaderTable.to_location=seedDispatchHeaderModel.to_location;
      seedDispatchNoteHeaderTable.supervisor=seedDispatchHeaderModel.supervisor;
      seedDispatchNoteHeaderTable.transporter=seedDispatchHeaderModel.transporter;
      seedDispatchNoteHeaderTable.organizer_name=seedDispatchHeaderModel.organizer_name;
      seedDispatchNoteHeaderTable.organizer_code=seedDispatchHeaderModel.organizer_code;
      seedDispatchNoteHeaderTable.truck_number=seedDispatchHeaderModel.truck_number;
      seedDispatchNoteHeaderTable.season_code=seedDispatchHeaderModel.season_code;
      seedDispatchNoteHeaderTable.camp_at=seedDispatchHeaderModel.camp_at;
      seedDispatchNoteHeaderTable.remarks=seedDispatchHeaderModel.remarks;
      seedDispatchNoteHeaderTable.created_on=seedDispatchHeaderModel.created_on;
      seedDispatchNoteHeaderTable.created_by =seedDispatchHeaderModel.created_by;
      seedDispatchNoteHeaderTable.status=seedDispatchHeaderModel.status;
      seedDispatchNoteHeaderTable.nav_sync=seedDispatchHeaderModel.nav_sync;
      seedDispatchNoteHeaderTable.nav_message=seedDispatchHeaderModel.nav_message;
      seedDispatchNoteHeaderTable.document_type=seedDispatchHeaderModel.document_type;
      seedDispatchNoteHeaderTable.refrence_no=seedDispatchHeaderModel.refrence_no;

      return seedDispatchNoteHeaderTable;

    }

    public static SeedDispatchHeaderModel getDispatchServerFormat(SeedDispatchNoteHeaderTable passData) {
        SeedDispatchHeaderModel seedDispatchHeaderModel = new SeedDispatchHeaderModel();
        seedDispatchHeaderModel.dispatch_no= passData.getDispatch_no();
        seedDispatchHeaderModel.date= passData.getDate();
        seedDispatchHeaderModel.from_location=passData.getFrom_location();
        seedDispatchHeaderModel.to_location = passData.getTo_location();
        seedDispatchHeaderModel.supervisor = passData.getSupervisor();
        seedDispatchHeaderModel.transporter= passData.getTransporter();
        seedDispatchHeaderModel.organizer_name=passData.getOrganizer_name();
        seedDispatchHeaderModel.organizer_code=passData.getOrganizer_code();
        seedDispatchHeaderModel.truck_number=passData.getTruck_number();
        seedDispatchHeaderModel.season_code=passData.getSeason_code();
        seedDispatchHeaderModel.camp_at=passData.getCamp_at();
        seedDispatchHeaderModel.remarks=passData.getRemarks();
        seedDispatchHeaderModel.created_on=passData.getCreated_on();
        seedDispatchHeaderModel.created_by=passData.getCreated_by();
        seedDispatchHeaderModel.status=passData.getStatus();
        seedDispatchHeaderModel.nav_sync=passData.getNav_sync();
        seedDispatchHeaderModel.nav_message=passData.getNav_message();
        seedDispatchHeaderModel.document_type=passData.document_type;
        seedDispatchHeaderModel.refrence_no=passData.refrence_no;
        seedDispatchHeaderModel.dispatch_line=new ArrayList<>();
        return seedDispatchHeaderModel;
    }

}
