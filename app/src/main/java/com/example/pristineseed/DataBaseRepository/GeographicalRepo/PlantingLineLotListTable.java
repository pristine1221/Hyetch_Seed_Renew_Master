package com.example.pristineseed.DataBaseRepository.GeographicalRepo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.pristineseed.model.item.PlantingProdcutionLotModel;

@Entity(tableName = "planting_line_lot_table")
public class PlantingLineLotListTable {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int android_id;

    public int getAndroid_id() {
        return android_id;
    }

    public void setAndroid_id(int android_id) {
        this.android_id = android_id;
    }

    @ColumnInfo(name = "production_Lot_No")
    private String production_Lot_No;

    @ColumnInfo(name = "fsiO_No")
    private String fsiO_No;

    @ColumnInfo(name = "organizer_Code")
    private String organizer_Code;

    @ColumnInfo(name = "organizer_Name")
    private String organizer_Name;

    @ColumnInfo(name = "parent_Male")
    private String parent_Male;

    @ColumnInfo(name = "parent_Female")
    private String parent_Female;

    @ColumnInfo(name = "parent_Other")
    private String parent_Other;

    @ColumnInfo(name = "parent_Male_Lot_No")
    private String parent_Male_Lot_No;

    @ColumnInfo(name = "parent_Female_Lot_No")
    private String parent_Female_Lot_No;

    @ColumnInfo(name = "parent_Other_Lot_No")
    private String parent_Other_Lot_No;

    @ColumnInfo(name = "parent_Male_Quantity")
    private String parent_Male_Quantity;

    @ColumnInfo(name = "parent_Female_Quantity")
    private String parent_Female_Quantity;

    @ColumnInfo(name = "parent_Other_Quantity")
    private String parent_Other_Quantity;

    @ColumnInfo(name = "parent_Male_Receipt_No")
    private String parent_Male_Receipt_No;
    @ColumnInfo(name = "parent_Female_Receipt_No")
    private String parent_Female_Receipt_No;
    @ColumnInfo(name = "parent_Other_Receipt_No")
    private String parent_Other_Receipt_No;

    @ColumnInfo(name = "grower_Owner")
    private String grower_Owner;

    @ColumnInfo(name = "grower_Land_Owner_Name")
    private String grower_Land_Owner_Name;

    @ColumnInfo(name = "supervisor_Name")
    private String supervisor_Name;

    @ColumnInfo(name = "crop_Code")
    private String crop_Code;

    @ColumnInfo(name = "variety_Code")
    private String variety_Code;

    @ColumnInfo(name = "item_Product_Group_Code")
    private String item_Product_Group_Code;

    @ColumnInfo(name = "item_Class_of_Seeds")
    private String item_Class_of_Seeds;

    @ColumnInfo(name = "item_Crop_Type")
    private String item_Crop_Type;

    @ColumnInfo(name = "item_Name")
    private String item_Name;

    @ColumnInfo(name = "alias_Name")
    private String alias_Name;

    @ColumnInfo(name = "revised_Yield_RAW")
    private String revised_Yield_RAW;

    @ColumnInfo(name = "land_Lease")
    private String land_Lease;

    @ColumnInfo(name = "unit_of_Measure_Code")
    private String unit_of_Measure_Code;

    @ColumnInfo(name = "sowing_Date_Male")
    private String sowing_Date_Male;

    @ColumnInfo(name = "sowing_Date_Female")
    private String sowing_Date_Female;

    @ColumnInfo(name = "sowing_Date_Other")
    private String sowing_Date_Other;

    @ColumnInfo(name = "sowing_Area_In_Acres")
    private String sowing_Area_In_Acres;

    @ColumnInfo(name = "standing_acres")
    private String standing_acres;

    @ColumnInfo(name = "inspection_I")
    private String inspection_I;

    @ColumnInfo(name = "inspection_II")
    private String inspection_II;

    @ColumnInfo(name = "inspection_III")
    private String inspection_III;

    @ColumnInfo(name = "inspection_IV")
    private String inspection_IV;

    @ColumnInfo(name = "inspection_V")
    private String inspection_V;

    @ColumnInfo(name = "inspection_VI")
    private String inspection_VI;

    @ColumnInfo(name = "inspection_VII")
    private String inspection_VII;

    @ColumnInfo(name = "inspection_VIII")
    private String inspection_VIII;

    @ColumnInfo(name = "inspection_QC")
    private String inspection_QC;

    @ColumnInfo(name = "inspection_I_Status")
    private String inspection_I_Status;

    @ColumnInfo(name = "inspection_II_Status")
    private String inspection_II_Status;

    @ColumnInfo(name = "inspection_III_Status")
    private String inspection_III_Status;

    @ColumnInfo(name = "inspection_IV_Status")
    private String inspection_IV_Status;

    @ColumnInfo(name = "inspection_V_Status")
    private String inspection_V_Status;

    @ColumnInfo(name = "inspection_VI_Status")
    private String inspection_VI_Status;

    @ColumnInfo(name = "inspection_VII_Status")
    private String inspection_VII_Status;

    @ColumnInfo(name = "inspection_VIII_Status")
    private String inspection_VIII_Status;

    @ColumnInfo(name = "temp_FSIO_No")
    private String temp_FSIO_No;

    @ColumnInfo(name = "plD_Error")
    private String plD_Error;

    @ColumnInfo(name = "pld_mark")
    private String pld_mark;

    @ColumnInfo(name = "posted")
    private String posted;

    @ColumnInfo(name = "document_SubType")
    private String document_SubType;

    public String getProduction_Lot_No() {
        return production_Lot_No;
    }

    public void setProduction_Lot_No(String production_Lot_No) {
        this.production_Lot_No = production_Lot_No;
    }

    public String getFsiO_No() {
        return fsiO_No;
    }

    public void setFsiO_No(String fsiO_No) {
        this.fsiO_No = fsiO_No;
    }

    public String getOrganizer_Code() {
        return organizer_Code;
    }

    public void setOrganizer_Code(String organizer_Code) {
        this.organizer_Code = organizer_Code;
    }

    public String getOrganizer_Name() {
        return organizer_Name;
    }

    public void setOrganizer_Name(String organizer_Name) {
        this.organizer_Name = organizer_Name;
    }

    public String getParent_Male() {
        return parent_Male;
    }

    public void setParent_Male(String parent_Male) {
        this.parent_Male = parent_Male;
    }

    public String getParent_Female() {
        return parent_Female;
    }

    public void setParent_Female(String parent_Female) {
        this.parent_Female = parent_Female;
    }

    public String getParent_Other() {
        return parent_Other;
    }

    public void setParent_Other(String parent_Other) {
        this.parent_Other = parent_Other;
    }

    public String getParent_Male_Lot_No() {
        return parent_Male_Lot_No;
    }

    public void setParent_Male_Lot_No(String parent_Male_Lot_No) {
        this.parent_Male_Lot_No = parent_Male_Lot_No;
    }

    public String getParent_Female_Lot_No() {
        return parent_Female_Lot_No;
    }

    public void setParent_Female_Lot_No(String parent_Female_Lot_No) {
        this.parent_Female_Lot_No = parent_Female_Lot_No;
    }

    public String getParent_Other_Lot_No() {
        return parent_Other_Lot_No;
    }

    public void setParent_Other_Lot_No(String parent_Other_Lot_No) {
        this.parent_Other_Lot_No = parent_Other_Lot_No;
    }

    public String getParent_Male_Quantity() {
        return parent_Male_Quantity;
    }

    public void setParent_Male_Quantity(String parent_Male_Quantity) {
        this.parent_Male_Quantity = parent_Male_Quantity;
    }

    public String getParent_Female_Quantity() {
        return parent_Female_Quantity;
    }

    public void setParent_Female_Quantity(String parent_Female_Quantity) {
        this.parent_Female_Quantity = parent_Female_Quantity;
    }

    public String getParent_Other_Quantity() {
        return parent_Other_Quantity;
    }

    public void setParent_Other_Quantity(String parent_Other_Quantity) {
        this.parent_Other_Quantity = parent_Other_Quantity;
    }

    public String getParent_Male_Receipt_No() {
        return parent_Male_Receipt_No;
    }

    public void setParent_Male_Receipt_No(String parent_Male_Receipt_No) {
        this.parent_Male_Receipt_No = parent_Male_Receipt_No;
    }

    public String getParent_Female_Receipt_No() {
        return parent_Female_Receipt_No;
    }

    public void setParent_Female_Receipt_No(String parent_Female_Receipt_No) {
        this.parent_Female_Receipt_No = parent_Female_Receipt_No;
    }

    public String getParent_Other_Receipt_No() {
        return parent_Other_Receipt_No;
    }

    public void setParent_Other_Receipt_No(String parent_Other_Receipt_No) {
        this.parent_Other_Receipt_No = parent_Other_Receipt_No;
    }

    public String getGrower_Owner() {
        return grower_Owner;
    }

    public void setGrower_Owner(String grower_Owner) {
        this.grower_Owner = grower_Owner;
    }

    public String getGrower_Land_Owner_Name() {
        return grower_Land_Owner_Name;
    }

    public void setGrower_Land_Owner_Name(String grower_Land_Owner_Name) {
        this.grower_Land_Owner_Name = grower_Land_Owner_Name;
    }

    public String getSupervisor_Name() {
        return supervisor_Name;
    }

    public void setSupervisor_Name(String supervisor_Name) {
        this.supervisor_Name = supervisor_Name;
    }

    public String getCrop_Code() {
        return crop_Code;
    }

    public void setCrop_Code(String crop_Code) {
        this.crop_Code = crop_Code;
    }

    public String getVariety_Code() {
        return variety_Code;
    }

    public void setVariety_Code(String variety_Code) {
        this.variety_Code = variety_Code;
    }

    public String getItem_Product_Group_Code() {
        return item_Product_Group_Code;
    }

    public void setItem_Product_Group_Code(String item_Product_Group_Code) {
        this.item_Product_Group_Code = item_Product_Group_Code;
    }

    public String getItem_Class_of_Seeds() {
        return item_Class_of_Seeds;
    }

    public void setItem_Class_of_Seeds(String item_Class_of_Seeds) {
        this.item_Class_of_Seeds = item_Class_of_Seeds;
    }

    public String getItem_Crop_Type() {
        return item_Crop_Type;
    }

    public void setItem_Crop_Type(String item_Crop_Type) {
        this.item_Crop_Type = item_Crop_Type;
    }

    public String getItem_Name() {
        return item_Name;
    }

    public void setItem_Name(String item_Name) {
        this.item_Name = item_Name;
    }

    public String getAlias_Name() {
        return alias_Name;
    }

    public void setAlias_Name(String alias_Name) {
        this.alias_Name = alias_Name;
    }

    public String getRevised_Yield_RAW() {
        return revised_Yield_RAW;
    }

    public void setRevised_Yield_RAW(String revised_Yield_RAW) {
        this.revised_Yield_RAW = revised_Yield_RAW;
    }

    public String getLand_Lease() {
        return land_Lease;
    }

    public void setLand_Lease(String land_Lease) {
        this.land_Lease = land_Lease;
    }

    public String getUnit_of_Measure_Code() {
        return unit_of_Measure_Code;
    }

    public void setUnit_of_Measure_Code(String unit_of_Measure_Code) {
        this.unit_of_Measure_Code = unit_of_Measure_Code;
    }

    public String getSowing_Date_Male() {
        return sowing_Date_Male;
    }

    public void setSowing_Date_Male(String sowing_Date_Male) {
        this.sowing_Date_Male = sowing_Date_Male;
    }

    public String getSowing_Date_Female() {
        return sowing_Date_Female;
    }

    public void setSowing_Date_Female(String sowing_Date_Female) {
        this.sowing_Date_Female = sowing_Date_Female;
    }

    public String getSowing_Date_Other() {
        return sowing_Date_Other;
    }

    public void setSowing_Date_Other(String sowing_Date_Other) {
        this.sowing_Date_Other = sowing_Date_Other;
    }

    public String getSowing_Area_In_Acres() {
        return sowing_Area_In_Acres;
    }

    public void setSowing_Area_In_Acres(String sowing_Area_In_Acres) {
        this.sowing_Area_In_Acres = sowing_Area_In_Acres;
    }

    public String getStanding_acres(){
        return standing_acres;
    }
    public void setStanding_acres(String standing_acres){
        this.standing_acres=standing_acres;
    }

    public String getInspection_I() {
        return inspection_I;
    }

    public void setInspection_I(String inspection_I) {
        this.inspection_I = inspection_I;
    }

    public String getInspection_II() {
        return inspection_II;
    }

    public void setInspection_II(String inspection_II) {
        this.inspection_II = inspection_II;
    }

    public String getInspection_III() {
        return inspection_III;
    }

    public void setInspection_III(String inspection_III) {
        this.inspection_III = inspection_III;
    }

    public String getInspection_IV() {
        return inspection_IV;
    }

    public void setInspection_IV(String inspection_IV) {
        this.inspection_IV = inspection_IV;
    }

    public String getInspection_V() {
        return inspection_V;
    }

    public void setInspection_V(String inspection_V) {
        this.inspection_V = inspection_V;
    }

    public String getInspection_VI() {
        return inspection_VI;
    }

    public void setInspection_VI(String inspection_VI) {
        this.inspection_VI = inspection_VI;
    }

    public String getInspection_VII() {
        return inspection_VII;
    }

    public void setInspection_VII(String inspection_VII) {
        this.inspection_VII = inspection_VII;
    }

    public String getInspection_VIII() {
        return inspection_VIII;
    }

    public void setInspection_VIII(String inspection_VIII) {
        this.inspection_VIII = inspection_VIII;
    }

    public String getInspection_QC() {
        return inspection_QC;
    }

    public void setInspection_QC(String inspection_QC) {
        this.inspection_QC = inspection_QC;
    }

    public String getInspection_I_Status() {
        return inspection_I_Status;
    }

    public void setInspection_I_Status(String inspection_I_Status) {
        this.inspection_I_Status = inspection_I_Status;
    }

    public String getInspection_II_Status() {
        return inspection_II_Status;
    }

    public void setInspection_II_Status(String inspection_II_Status) {
        this.inspection_II_Status = inspection_II_Status;
    }

    public String getInspection_III_Status() {
        return inspection_III_Status;
    }

    public void setInspection_III_Status(String inspection_III_Status) {
        this.inspection_III_Status = inspection_III_Status;
    }

    public String getInspection_IV_Status() {
        return inspection_IV_Status;
    }

    public void setInspection_IV_Status(String inspection_IV_Status) {
        this.inspection_IV_Status = inspection_IV_Status;
    }

    public String getInspection_V_Status() {
        return inspection_V_Status;
    }

    public void setInspection_V_Status(String inspection_V_Status) {
        this.inspection_V_Status = inspection_V_Status;
    }

    public String getInspection_VI_Status() {
        return inspection_VI_Status;
    }

    public void setInspection_VI_Status(String inspection_VI_Status) {
        this.inspection_VI_Status = inspection_VI_Status;
    }

    public String getInspection_VII_Status() {
        return inspection_VII_Status;
    }

    public void setInspection_VII_Status(String inspection_VII_Status) {
        this.inspection_VII_Status = inspection_VII_Status;
    }

    public String getInspection_VIII_Status() {
        return inspection_VIII_Status;
    }

    public void setInspection_VIII_Status(String inspection_VIII_Status) {
        this.inspection_VIII_Status = inspection_VIII_Status;
    }

    public String getTemp_FSIO_No() {
        return temp_FSIO_No;
    }

    public void setTemp_FSIO_No(String temp_FSIO_No) {
        this.temp_FSIO_No = temp_FSIO_No;
    }

    public String getPlD_Error() {
        return plD_Error;
    }

    public void setPlD_Error(String plD_Error) {
        this.plD_Error = plD_Error;
    }

    public String getPld_mark() {
        return pld_mark;
    }

    public void setPld_mark(String pld_mark) {
        this.pld_mark = pld_mark;
    }

    public String getPosted() {
        return posted;
    }

    public void setPosted(String posted) {
        this.posted = posted;
    }

    public String getDocument_SubType() {
        return document_SubType;
    }

    public void setDocument_SubType(String document_SubType) {
        this.document_SubType = document_SubType;
    }



    public static PlantingLineLotListTable bindPLantingLotDetail(PlantingProdcutionLotModel plantingLotParentTypeModel) {
        PlantingLineLotListTable planting_lot_master_table = new PlantingLineLotListTable();
        planting_lot_master_table.production_Lot_No = plantingLotParentTypeModel.production_Lot_No;
        planting_lot_master_table.fsiO_No=plantingLotParentTypeModel.fsiO_No;
        planting_lot_master_table.organizer_Code = plantingLotParentTypeModel.organizer_Code;
        planting_lot_master_table.organizer_Name=plantingLotParentTypeModel.organizer_Name;
        planting_lot_master_table.parent_Male=plantingLotParentTypeModel.parent_Male;
        planting_lot_master_table.parent_Female=plantingLotParentTypeModel.parent_Female;
        planting_lot_master_table.parent_Other=plantingLotParentTypeModel.parent_Other;
        planting_lot_master_table.parent_Male_Lot_No=plantingLotParentTypeModel.parent_Male_Lot_No;
        planting_lot_master_table.parent_Female_Lot_No=plantingLotParentTypeModel.parent_Female_Lot_No;
        planting_lot_master_table.parent_Other_Lot_No=plantingLotParentTypeModel.parent_Other_Lot_No;
        planting_lot_master_table.parent_Male_Receipt_No = plantingLotParentTypeModel.parent_Male_Receipt_No;
        planting_lot_master_table.parent_Female_Receipt_No = plantingLotParentTypeModel.parent_Female_Receipt_No;
        planting_lot_master_table.parent_Other_Receipt_No = plantingLotParentTypeModel.parent_Other_Receipt_No;
        planting_lot_master_table.parent_Male_Quantity=plantingLotParentTypeModel.parent_Male_Quantity;
        planting_lot_master_table.parent_Female_Quantity=plantingLotParentTypeModel.parent_Female_Quantity;
        planting_lot_master_table.parent_Other_Quantity=plantingLotParentTypeModel.parent_Other_Quantity;
        planting_lot_master_table.grower_Owner=plantingLotParentTypeModel.grower_Owner;
        planting_lot_master_table.grower_Land_Owner_Name=plantingLotParentTypeModel.grower_Land_Owner_Name;
        planting_lot_master_table.supervisor_Name=plantingLotParentTypeModel.supervisor_Name;
        planting_lot_master_table.crop_Code=plantingLotParentTypeModel.crop_Code;
        planting_lot_master_table.variety_Code = plantingLotParentTypeModel.variety_Code;
        planting_lot_master_table.item_Product_Group_Code=plantingLotParentTypeModel.item_Product_Group_Code;
        planting_lot_master_table.item_Class_of_Seeds=plantingLotParentTypeModel.item_Class_of_Seeds;
        planting_lot_master_table.item_Crop_Type=plantingLotParentTypeModel.item_Crop_Type;
        planting_lot_master_table.item_Name=plantingLotParentTypeModel.item_Name;
        planting_lot_master_table.alias_Name=plantingLotParentTypeModel.alias_Name;
        planting_lot_master_table.revised_Yield_RAW=plantingLotParentTypeModel.revised_Yield_RAW;
        planting_lot_master_table.land_Lease=plantingLotParentTypeModel.land_Lease;
        planting_lot_master_table.unit_of_Measure_Code=plantingLotParentTypeModel.unit_of_Measure_Code;
        planting_lot_master_table.sowing_Date_Male=plantingLotParentTypeModel.sowing_Date_Male;
        planting_lot_master_table.sowing_Date_Female=plantingLotParentTypeModel.sowing_Date_Female;
        planting_lot_master_table.sowing_Date_Other=plantingLotParentTypeModel.sowing_Date_Other;
        planting_lot_master_table.sowing_Area_In_Acres=plantingLotParentTypeModel.sowing_Area_In_Acres;
        planting_lot_master_table.standing_acres=plantingLotParentTypeModel.standing_acres;
        planting_lot_master_table.inspection_I=plantingLotParentTypeModel.inspection_I;
        planting_lot_master_table.inspection_II=plantingLotParentTypeModel.inspection_II;
        planting_lot_master_table.inspection_III=plantingLotParentTypeModel.inspection_III;
        planting_lot_master_table.inspection_IV=plantingLotParentTypeModel.inspection_IV;
        planting_lot_master_table.inspection_V=plantingLotParentTypeModel.inspection_V;
        planting_lot_master_table.inspection_VI=plantingLotParentTypeModel.inspection_VI;
        planting_lot_master_table.inspection_VII=plantingLotParentTypeModel.inspection_VII;
        planting_lot_master_table.inspection_VIII=plantingLotParentTypeModel.inspection_VIII;
        planting_lot_master_table.inspection_QC=plantingLotParentTypeModel.inspection_QC;
        planting_lot_master_table.inspection_I_Status=plantingLotParentTypeModel.inspection_I_Status;
        planting_lot_master_table.inspection_II_Status=plantingLotParentTypeModel.inspection_II_Status;
        planting_lot_master_table.inspection_III_Status=plantingLotParentTypeModel.inspection_III_Status;
        planting_lot_master_table.inspection_IV_Status=plantingLotParentTypeModel.inspection_IV_Status;
        planting_lot_master_table.inspection_V_Status=plantingLotParentTypeModel.inspection_V_Status;
        planting_lot_master_table.inspection_VI_Status=plantingLotParentTypeModel.inspection_VI_Status;
        planting_lot_master_table.inspection_VII_Status=plantingLotParentTypeModel.inspection_VII_Status;
        planting_lot_master_table.inspection_VIII_Status=plantingLotParentTypeModel.inspection_VIII_Status;
        planting_lot_master_table.temp_FSIO_No=plantingLotParentTypeModel.temp_FSIO_No;
        planting_lot_master_table.plD_Error=plantingLotParentTypeModel.plD_Error;
        planting_lot_master_table.posted=plantingLotParentTypeModel.posted;
        planting_lot_master_table.document_SubType=plantingLotParentTypeModel.document_SubType;
        planting_lot_master_table.pld_mark=plantingLotParentTypeModel.pld_mark;
        return planting_lot_master_table;
    }
}
