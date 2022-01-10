package com.example.pristineseed.DataBaseRepository.seed_dispatch_note;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.pristineseed.DataBaseRepository.Planting.PlantingLineTable;
import com.example.pristineseed.DataBaseRepository.Scheduler.Seedling.SeedlingInspectionTable;
import com.example.pristineseed.model.PlantingModel.PlantingHeaderModel;
import com.example.pristineseed.model.seed_dispatch_note.SeedDispatchHeaderModel;

@Entity(tableName = "seed_dispatch_note_line_table")//,primaryKeys = {"line_no","dispatch_no"}
public class SeedDispatchNoteLineTable {
    public int getAndroid_id() {
        return android_id;
    }

    public void setAndroid_id(int android_id) {
        this.android_id = android_id;
    }

  /*  @NonNull
    @PrimaryKey(autoGenerate = true)*/
    private int android_id;

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "line_no")
    public String line_no;
    @NonNull
    @ColumnInfo(name = "dispatch_no")
    public String dispatch_no;
    @ColumnInfo(name = "farmer_code")
    public String farmer_code;

    public String getLine_no() {
        return line_no;
    }

    public void setLine_no(String line_no) {
        this.line_no = line_no;
    }

    public String getDispatch_no() {
        return dispatch_no;
    }

    public void setDispatch_no(String dispatch_no) {
        this.dispatch_no = dispatch_no;
    }

    public String getFarmer_code() {
        return farmer_code;
    }

    public void setFarmer_code(String farmer_code) {
        this.farmer_code = farmer_code;
    }

    public String getFarmer_name() {
        return farmer_name;
    }

    public void setFarmer_name(String farmer_name) {
        this.farmer_name = farmer_name;
    }

    public String getFarmer_name2() {
        return farmer_name2;
    }

    public void setFarmer_name2(String farmer_name2) {
        this.farmer_name2 = farmer_name2;
    }

    public String getVillage_name() {
        return village_name;
    }

    public void setVillage_name(String village_name) {
        this.village_name = village_name;
    }

    public String getTaluka() {
        return taluka;
    }

    public void setTaluka(String taluka) {
        this.taluka = taluka;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLot_number() {
        return lot_number;
    }

    public void setLot_number(String lot_number) {
        this.lot_number = lot_number;
    }

    public String getHybrid() {
        return hybrid;
    }

    public void setHybrid(String hybrid) {
        this.hybrid = hybrid;
    }

    public String getNumber_of_bags() {
        return number_of_bags;
    }

    public void setNumber_of_bags(String number_of_bags) {
        this.number_of_bags = number_of_bags;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    @ColumnInfo(name = "farmer_name")
    public String farmer_name;
    @ColumnInfo(name = "farmer_name2")
    public String farmer_name2;
    @ColumnInfo(name = "village_name")
    public String village_name;
    @ColumnInfo(name = "taluka")
    public String taluka;
    @ColumnInfo(name = "state")
    public String state;
    @ColumnInfo(name = "city")
    public String city;
    @ColumnInfo(name = "lot_number")
    public String lot_number;
    @ColumnInfo(name = "hybrid")
    public String hybrid;
    @ColumnInfo(name = "number_of_bags")
    public String number_of_bags;
    @ColumnInfo(name = "quantity")
    public String quantity ;
    @ColumnInfo(name = "created_on")
    public String created_on;

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @ColumnInfo(name = "remarks")
    public String remarks;


    public String getMoisture_prcnt() {
        return moisture_prcnt;
    }

    public void setMoisture_prcnt(String moisture_prcnt) {
        this.moisture_prcnt = moisture_prcnt;
    }

    public String getHarvested_acreage() {
        return harvested_acreage;
    }

    public void setHarvested_acreage(String harvested_acreage) {
        this.harvested_acreage = harvested_acreage;
    }

    @ColumnInfo(name = "moisture_prcnt")
    public String moisture_prcnt;

    @ColumnInfo(name = "harvested_acreage")
    public String harvested_acreage;

    public String getHybrid_name() {
        return hybrid_name;
    }

    public void setHybrid_name(String hybrid_name) {
        this.hybrid_name = hybrid_name;
    }

    @ColumnInfo(name = "hybrid_name")
    public String hybrid_name;


    public static SeedDispatchNoteLineTable getLineDataFormate(SeedDispatchHeaderModel.SeedDispatchLineModel passmodel) {
        SeedDispatchNoteLineTable seedDispatchNoteLineTable = new SeedDispatchNoteLineTable();

      seedDispatchNoteLineTable.setLine_no(passmodel.line_no);

      seedDispatchNoteLineTable.setDispatch_no(passmodel.dispatch_no);
      seedDispatchNoteLineTable.setFarmer_code(passmodel.farmer_code);
      seedDispatchNoteLineTable.setFarmer_name(passmodel.farmer_name);
      seedDispatchNoteLineTable.setFarmer_name2(passmodel.farmer_name2);
      seedDispatchNoteLineTable.setVillage_name(passmodel.village_name);
      seedDispatchNoteLineTable.setTaluka(passmodel.taluka);
      seedDispatchNoteLineTable.setState(passmodel.state);
      seedDispatchNoteLineTable.setCity(passmodel.city);
      seedDispatchNoteLineTable.setLot_number(passmodel.lot_number);
      seedDispatchNoteLineTable.setHybrid(passmodel.hybrid);
      seedDispatchNoteLineTable.setNumber_of_bags( passmodel.number_of_bags);
      seedDispatchNoteLineTable.setQuantity(passmodel.quantity);
      seedDispatchNoteLineTable.setCreated_on(passmodel.created_on);
      seedDispatchNoteLineTable.setRemarks(passmodel.remarks);
      seedDispatchNoteLineTable.setMoisture_prcnt(passmodel.moisture_prcnt);
      seedDispatchNoteLineTable.setHarvested_acreage(passmodel.harvested_acreage);
        seedDispatchNoteLineTable.setHybrid_name(passmodel.hybrid_name);

        return seedDispatchNoteLineTable;
    }

    public static SeedDispatchHeaderModel.SeedDispatchLineModel getServerTypeFormat(SeedDispatchHeaderModel seedDispatchHeaderModel, SeedDispatchNoteLineTable passData) {
        SeedDispatchHeaderModel.SeedDispatchLineModel seedDispatchLineModel = seedDispatchHeaderModel.new SeedDispatchLineModel();
      seedDispatchLineModel.line_no=passData.getLine_no();
      seedDispatchLineModel.dispatch_no=passData.getDispatch_no();
      seedDispatchLineModel.farmer_code=passData.getFarmer_code();
      seedDispatchLineModel.farmer_name=passData.getFarmer_name();
      seedDispatchLineModel.farmer_name2=passData.getFarmer_name2();
      seedDispatchLineModel.village_name=passData.getVillage_name();
      seedDispatchLineModel.taluka=passData.getTaluka();
      seedDispatchLineModel.state=passData.getState();
      seedDispatchLineModel.city=passData.getCity();
      seedDispatchLineModel.lot_number=passData.getLot_number();
      seedDispatchLineModel.hybrid=passData.getHybrid() ;
      seedDispatchLineModel.number_of_bags=passData.getNumber_of_bags() ;
      seedDispatchLineModel.quantity=passData.getQuantity();
      seedDispatchLineModel.created_on=passData.getCreated_on();
      seedDispatchHeaderModel.remarks=passData.getRemarks();
      seedDispatchLineModel.moisture_prcnt=passData.moisture_prcnt;
      seedDispatchLineModel.harvested_acreage=passData.harvested_acreage;
        seedDispatchLineModel.hybrid_name=passData.hybrid_name;


        return seedDispatchLineModel;
    }



}
