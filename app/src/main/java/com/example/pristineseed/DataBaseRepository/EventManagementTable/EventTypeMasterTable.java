package com.example.pristineseed.DataBaseRepository.EventManagementTable;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.pristineseed.model.event_managment_model.EventTypeMasterModel;


@Entity(tableName = "event_type_master_table")
public class EventTypeMasterTable {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    int android_id;

    @ColumnInfo(name = "id")
    private String id;

    public int getAndroid_id() {
        return android_id;
    }

    public void setAndroid_id(int android_id) {
        this.android_id = android_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEvent_type() {
        return event_type;
    }


    public void setEvent_type(String event_type) {
        this.event_type = event_type;
    }



    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public String getNo_of_attendee() {
        return no_of_attendee;
    }

    public void setNo_of_attendee(String no_of_attendee) {
        this.no_of_attendee = no_of_attendee;
    }

    @ColumnInfo(name = "event_type")
   private String event_type;

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    @ColumnInfo(name = "rate")
   private String rate;
    @ColumnInfo(name = "parent_id")
   private int parent_id;
    @ColumnInfo(name = "no_of_attendee")
   private String no_of_attendee;

    @ColumnInfo(name = "qty")
    private String qty;

    public static EventTypeMasterTable getEventTypeData(EventTypeMasterModel eventTypeMasterModel){
        EventTypeMasterTable eventTypeMasterTable=new EventTypeMasterTable();
        eventTypeMasterTable. id=eventTypeMasterModel.id;
        eventTypeMasterTable. event_type=eventTypeMasterModel.event_type;
        eventTypeMasterTable. rate=eventTypeMasterModel.rate;
        eventTypeMasterTable.  parent_id=eventTypeMasterModel.parent_id;
        eventTypeMasterTable.qty=eventTypeMasterModel.qty;
        eventTypeMasterTable.  no_of_attendee=eventTypeMasterModel.no_of_attendee;
        return eventTypeMasterTable;

    }
}
