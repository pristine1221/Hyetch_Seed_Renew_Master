package com.example.pristineseed.DataBaseRepository.Planting;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.pristineseed.model.PlantingModel.PlantingFsio_bsio_model;

import retrofit2.http.Query;

@Entity(tableName = "planting_fsio_bsio_table")
public class PlantingFSIO_BSIO_Table {
   /* @NonNull
    @PrimaryKey(autoGenerate = true)*/
   private int android_id;

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "code")
    private String No;

    public int getAndroid_id() {
        return android_id;
    }

    public void setAndroid_id(int android_id) {
        this.android_id = android_id;
    }

    @NonNull
    public String getNo() {
        return No;
    }

    public void setNo( @NonNull String no) {
        No = no;
    }

    public String getDocument_SubType() {
        return Document_SubType;
    }

    public void setDocument_SubType(String document_SubType) {
        Document_SubType = document_SubType;
    }

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

    @ColumnInfo(name = "Document_SubType")
    private String Document_SubType;

    @ColumnInfo(name = "organizer_code")
    private String organizer_code;

    @ColumnInfo(name = "organizer_name")
    private String organizer_name;

    @ColumnInfo(name = "Child_Seed_Type" )
    private String Child_Seed_Type;

    @ColumnInfo(name = "Child_Seed")
    private String Child_Seed;

    @ColumnInfo(name = "Child_Seed_Name")
    private String Child_Seed_Name;

    @ColumnInfo(name = "Crop_Code")
    private String Crop_Code;

    public String getChild_Seed_Type() {
        return Child_Seed_Type;
    }

    public void setChild_Seed_Type(String child_Seed_Type) {
        Child_Seed_Type = child_Seed_Type;
    }

    public String getChild_Seed() {
        return Child_Seed;
    }

    public void setChild_Seed(String child_Seed) {
        Child_Seed = child_Seed;
    }

    public String getChild_Seed_Name() {
        return Child_Seed_Name;
    }

    public void setChild_Seed_Name(String child_Seed_Name) {
        Child_Seed_Name = child_Seed_Name;
    }

    public String getCrop_Code() {
        return Crop_Code;
    }

    public void setCrop_Code(String crop_Code) {
        Crop_Code = crop_Code;
    }

    public static PlantingFSIO_BSIO_Table getPlanting_fsio_bsio_data(PlantingFsio_bsio_model plantingFsio_bsio_model){
        PlantingFSIO_BSIO_Table plantingFSIO_bsio_table=new PlantingFSIO_BSIO_Table();
        plantingFSIO_bsio_table.No=plantingFsio_bsio_model.No;
        plantingFSIO_bsio_table.Document_SubType=plantingFsio_bsio_model.Document_SubType;
        plantingFSIO_bsio_table.organizer_code=plantingFsio_bsio_model.organizer_code;
        plantingFSIO_bsio_table.organizer_name=plantingFsio_bsio_model.organizer_name;
        plantingFSIO_bsio_table.Child_Seed_Type=plantingFsio_bsio_model.Child_Seed_Type;
        plantingFSIO_bsio_table.Child_Seed=plantingFsio_bsio_model.Child_Seed;
        plantingFSIO_bsio_table.Child_Seed_Name=plantingFsio_bsio_model.Child_Seed_Name;
        plantingFSIO_bsio_table.Crop_Code=plantingFsio_bsio_model.Crop_Code;
        return plantingFSIO_bsio_table;
    }

}
