package com.example.pristineseed.DataBaseRepository.Planting;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.pristineseed.model.PlantingModel.SeasonMasterModel;

@Entity(tableName = "season_master_table")
public class SeasonMasterTable {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int android_id;


    public int getAndroid_id() {
        return android_id;
    }

    public void setAndroid_id(int android_id) {
        this.android_id = android_id;
    }

    public String getSeason_Code() {
        return Season_Code;
    }

    public void setSeason_Code(String season_Code) {
        Season_Code = season_Code;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }


    @ColumnInfo(name = "Season_Code")
    private String Season_Code;
    @ColumnInfo(name = "Description")
    private String Description;

    @ColumnInfo(name = "lot_Indicator")
    private String lot_Indicator;

    public String getLot_Indicator() {
        return lot_Indicator;
    }

    public void setLot_Indicator(String lot_Indicator) {
        this.lot_Indicator = lot_Indicator;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getYear_Lot_Indicator_FN() {
        return year_Lot_Indicator_FN;
    }

    public void setYear_Lot_Indicator_FN(String year_Lot_Indicator_FN) {
        this.year_Lot_Indicator_FN = year_Lot_Indicator_FN;
    }

    public String getYear_Lot_Indicator_FM() {
        return year_Lot_Indicator_FM;
    }

    public void setYear_Lot_Indicator_FM(String year_Lot_Indicator_FM) {
        this.year_Lot_Indicator_FM = year_Lot_Indicator_FM;
    }

    public String getSync_To_App() {
        return sync_To_App;
    }

    public void setSync_To_App(String sync_To_App) {
        this.sync_To_App = sync_To_App;
    }

    @ColumnInfo(name = "year")
    private String year;
    @ColumnInfo(name = "year_Lot_Indicator_FN")
    private String year_Lot_Indicator_FN;
    @ColumnInfo(name = "year_Lot_Indicator_FM")
    private String year_Lot_Indicator_FM;
    @ColumnInfo(name = "sync_To_App")
    private String sync_To_App;



    public static SeasonMasterTable getSeasonMasterTable(SeasonMasterModel seasonMasterModel){
        SeasonMasterTable seasonMasterTable=new SeasonMasterTable();

        seasonMasterTable.Description=seasonMasterModel.description;
        seasonMasterTable.Season_Code=seasonMasterModel.season_Code;

        seasonMasterTable. lot_Indicator=seasonMasterModel.lot_Indicator;
        seasonMasterTable. year=seasonMasterModel.year;
        seasonMasterTable. year_Lot_Indicator_FN=seasonMasterModel.year_Lot_Indicator_FN;
        seasonMasterTable. year_Lot_Indicator_FM=seasonMasterModel.year_Lot_Indicator_FM;
        seasonMasterTable.sync_To_App=seasonMasterModel.sync_To_App;

        return seasonMasterTable;
    }
}
