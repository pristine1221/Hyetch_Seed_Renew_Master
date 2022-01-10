package com.example.pristineseed.DataBaseRepository.seed_dispatch_note;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.pristineseed.model.item.OrganizerModel;

@Entity(tableName = "organizer_master_table")
public class Organizer_master_Table {

    @NonNull
    public String getNo() {
        return No;
    }

    public String getVendor_Classification() {
        return Vendor_Classification;
    }

    public void setVendor_Classification(String vendor_Classification) {
        Vendor_Classification = vendor_Classification;
    }

    public void setNo(@NonNull String no) {
        No = no;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "code")
    public String No;
    @ColumnInfo(name = "Name")
    public String Name;
    @ColumnInfo(name = "Vendor_Classification")
    public String Vendor_Classification;

public static Organizer_master_Table insertOrganiserData(OrganizerModel.Data organiser_data) {
    Organizer_master_Table organizer_master_table = new Organizer_master_Table();

   organizer_master_table. No=organiser_data.No;
   organizer_master_table. Name=organiser_data.Name;
   organizer_master_table. Vendor_Classification=organiser_data.Vendor_Classification;

    return organizer_master_table;
}

}
