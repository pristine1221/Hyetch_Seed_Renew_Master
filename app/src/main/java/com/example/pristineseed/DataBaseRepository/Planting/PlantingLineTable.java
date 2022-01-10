package com.example.pristineseed.DataBaseRepository.Planting;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.pristineseed.DataBaseRepository.Scheduler.SchedulerInspectionLineTable;
import com.example.pristineseed.global.DateTimeUtilsCustome;
import com.example.pristineseed.model.PlantingModel.PlantingHeaderModel;
import com.example.pristineseed.model.scheduler_inspection.SchedulerModel;

@Entity(tableName = "planting_line_table", primaryKeys = {"code","line_no"})
public class PlantingLineTable {
    public int getAndroid_id() {
        return android_id;
    }
    public void setAndroid_id(int android_id) {
        this.android_id = android_id;
    }

   /* @NonNull
    @PrimaryKey(autoGenerate = true)*/
    @ColumnInfo(name = "android_id")
    private int android_id;

    @NonNull
    @ColumnInfo(name = "code")
    public String code;

    @NonNull
    @ColumnInfo(name = "line_no")
    public String line_no;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLine_no() {
        return line_no;
    }

    public void setLine_no(String line_no) {
        this.line_no = line_no;
    }

    public String getGrower_owner_code() {
        return grower_owner_code;
    }

    public void setGrower_owner_code(String grower_owner_code) {
        this.grower_owner_code = grower_owner_code;
    }

    public String getGrower_land_owner_name() {
        return grower_land_owner_name;
    }

    public void setGrower_land_owner_name(String grower_land_owner_name) {
        this.grower_land_owner_name = grower_land_owner_name;
    }

    public String getSupervisor_name() {
        return supervisor_name;
    }

    public void setSupervisor_name(String supervisor_name) {
        this.supervisor_name = supervisor_name;
    }

    public String getCrop_code() {
        return crop_code;
    }

    public void setCrop_code(String crop_code) {
        this.crop_code = crop_code;
    }

    public String getVariety_code() {
        return variety_code;
    }

    public void setVariety_code(String variety_code) {
        this.variety_code = variety_code;
    }

    public String getItem_product_group_code() {
        return item_product_group_code;
    }

    public void setItem_product_group_code(String item_product_group_code) {
        this.item_product_group_code = item_product_group_code;
    }

    public String getItem_class_of_seeds() {
        return item_class_of_seeds;
    }

    public void setItem_class_of_seeds(String item_class_of_seeds) {
        this.item_class_of_seeds = item_class_of_seeds;
    }

    public String getItem_crop_type() {
        return item_crop_type;
    }

    public void setItem_crop_type(String item_crop_type) {
        this.item_crop_type = item_crop_type;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getExpected_yield() {
        return expected_yield;
    }

    public void setExpected_yield(String expected_yield) {
        this.expected_yield = expected_yield;
    }

    public String getRevised_yield_raw() {
        return revised_yield_raw;
    }

    public void setRevised_yield_raw(String revised_yield_raw) {
        this.revised_yield_raw = revised_yield_raw;
    }

    public String getLand_lease() {
        return land_lease;
    }

    public void setLand_lease(String land_lease) {
        this.land_lease = land_lease;
    }

    public String getUnit_of_measure_code() {
        return unit_of_measure_code;
    }

    public void setUnit_of_measure_code(String unit_of_measure_code) {
        this.unit_of_measure_code = unit_of_measure_code;
    }

    public String getSowing_date_male() {
        return sowing_date_male;
    }

    public void setSowing_date_male(String sowing_date_male) {
        this.sowing_date_male = sowing_date_male;
    }

    public String getSowing_date_female() {
        return sowing_date_female;
    }

    public void setSowing_date_female(String sowing_date_female) {
        this.sowing_date_female = sowing_date_female;
    }

    public String getSowing_date_other() {
        return sowing_date_other;
    }

    public void setSowing_date_other(String sowing_date_other) {
        this.sowing_date_other = sowing_date_other;
    }

    public String getSowing_area_In_acres() {
        return sowing_area_In_acres;
    }

    public void setSowing_area_In_acres(String sowing_area_In_acres) {
        this.sowing_area_In_acres = sowing_area_In_acres;
    }

    public String getInspectionI() {
        return InspectionI;
    }

    public void setInspectionI(String inspectionI) {
        InspectionI = inspectionI;
    }

    public String getInspectionII() {
        return InspectionII;
    }

    public void setInspectionII(String inspectionII) {
        InspectionII = inspectionII;
    }

    public String getInspectionIII() {
        return InspectionIII;
    }

    public void setInspectionIII(String inspectionIII) {
        InspectionIII = inspectionIII;
    }

    public String getInspectionIV() {
        return InspectionIV;
    }

    public void setInspectionIV(String inspectionIV) {
        InspectionIV = inspectionIV;
    }

    public String getInspectionV() {
        return InspectionV;
    }

    public void setInspectionV(String inspectionV) {
        InspectionV = inspectionV;
    }

    public String getInspectionVI() {
        return InspectionVI;
    }

    public void setInspectionVI(String inspectionVI) {
        InspectionVI = inspectionVI;
    }

    public String getInspectionVII() {
        return InspectionVII;
    }

    public void setInspectionVII(String inspectionVII) {
        InspectionVII = inspectionVII;
    }

    public String getInspectionVIII() {
        return InspectionVIII;
    }

    public void setInspectionVIII(String inspectionVIII) {
        InspectionVIII = inspectionVIII;
    }

    public String getInspectionQC() {
        return InspectionQC;
    }

    public void setInspectionQC(String inspectionQC) {
        InspectionQC = inspectionQC;
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

    public String getPoduction_lot_no() {
        return poduction_lot_no;
    }

    public void setPoduction_lot_no(String poduction_lot_no) {
        this.poduction_lot_no = poduction_lot_no;
    }

    public String getNav_planting_no() {
        return nav_planting_no;
    }

    public void setNav_planting_no(String nav_planting_no) {
        this.nav_planting_no = nav_planting_no;
    }

    public String getNav_line_no() {
        return nav_line_no;
    }

    public void setNav_line_no(String nav_line_no) {
        this.nav_line_no = nav_line_no;
    }

    @ColumnInfo(name = "grower_owner_code")
    public String grower_owner_code;
    @ColumnInfo(name = "grower_land_owner_name")
    public String grower_land_owner_name;
    @ColumnInfo(name = "supervisor_name")
    public String supervisor_name;
    @ColumnInfo(name = "crop_code")
    public String crop_code;
    @ColumnInfo(name = "variety_code")
    public String variety_code;
    @ColumnInfo(name = "item_product_group_code")
    public String item_product_group_code;
    @ColumnInfo(name = "item_class_of_seeds")
    public String item_class_of_seeds;
    @ColumnInfo(name = "item_crop_type")
    public String item_crop_type;
    @ColumnInfo(name = "item_name")
    public String item_name;
    @ColumnInfo(name = "expected_yield")
    public String expected_yield;
    @ColumnInfo(name = "revised_yield_raw")
    public String revised_yield_raw;
    @ColumnInfo(name = "land_lease")
    public String land_lease;
    @ColumnInfo(name = "unit_of_measure_code")
    public String unit_of_measure_code;
    @ColumnInfo(name = "sowing_date_male")
    public String sowing_date_male;
    @ColumnInfo(name = "sowing_date_female")
    public String sowing_date_female;
    @ColumnInfo(name = "sowing_date_other")
    public String sowing_date_other;
    @ColumnInfo(name = "sowing_area_In_acres")
    public String sowing_area_In_acres;
    @ColumnInfo(name = "InspectionI")
    public String InspectionI;
    @ColumnInfo(name = "InspectionII")
    public String InspectionII;
    @ColumnInfo(name = "InspectionIII")
    public String InspectionIII;
    @ColumnInfo(name = "InspectionIV")
    public String InspectionIV;
    @ColumnInfo(name = "InspectionV")
    public String InspectionV;
    @ColumnInfo(name = "InspectionVI")
    public String InspectionVI;
    @ColumnInfo(name = "InspectionVII")
    public String InspectionVII;
    @ColumnInfo(name = "InspectionVIII")
    public String InspectionVIII;

    public String getInspectionIX() {
        return InspectionIX;
    }

    public void setInspectionIX(String inspectionIX) {
        InspectionIX = inspectionIX;
    }

    @ColumnInfo(name = "InspectionIX")
    public String InspectionIX;
    @ColumnInfo(name = "InspectionQC")
    public String InspectionQC;
    @ColumnInfo(name = "created_by")
    public String created_by;
    @ColumnInfo(name = "created_on")
    public String created_on;
    @ColumnInfo(name = "poduction_lot_no")
    public String poduction_lot_no;
    @ColumnInfo(name = "nav_planting_no")
    public String nav_planting_no;
    @ColumnInfo(name = "nav_line_no")
    public String nav_line_no;

    @ColumnInfo(name = "village_name")
   private String village_name;
    @ColumnInfo(name = "taluka")
   private String taluka;
    @ColumnInfo(name = "state")
   private String  state;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @ColumnInfo(name = "city")
   private String  city;
    @ColumnInfo(name = "address")
   private String  address;

    @ColumnInfo(name = "doc_no")
    public String doc_no;
    @ColumnInfo(name = "organizer_code")
    public String organizer_code;
    @ColumnInfo(name = "parent_male")
    public String parent_male;
    @ColumnInfo(name = "parent_male_lot")
    public String parent_male_lot;
    @ColumnInfo(name = "parent_female")
    public String parent_female;
    @ColumnInfo(name = "parent_female_lot")
    public String parent_female_lot;

    public String getDoc_no() {
        return doc_no;
    }

    public void setDoc_no(String doc_no) {
        this.doc_no = doc_no;
    }

    public String getOrganizer_code() {
        return organizer_code;
    }

    public void setOrganizer_code(String organizer_code) {
        this.organizer_code = organizer_code;
    }

    public String getParent_male() {
        return parent_male;
    }

    public void setParent_male(String parent_male) {
        this.parent_male = parent_male;
    }

    public String getParent_male_lot() {
        return parent_male_lot;
    }

    public void setParent_male_lot(String parent_male_lot) {
        this.parent_male_lot = parent_male_lot;
    }

    public String getParent_female() {
        return parent_female;
    }

    public void setParent_female(String parent_female) {
        this.parent_female = parent_female;
    }

    public String getParent_female_lot() {
        return parent_female_lot;
    }

    public void setParent_female_lot(String parent_female_lot) {
        this.parent_female_lot = parent_female_lot;
    }


    public String getReciept_no_male() {
        return reciept_no_male;
    }

    public void setReciept_no_male(String reciept_no_male) {
        this.reciept_no_male = reciept_no_male;
    }

    public String getReciept_no_female() {
        return reciept_no_female;
    }

    public void setReciept_no_female(String reciept_no_female) {
        this.reciept_no_female = reciept_no_female;
    }


    @ColumnInfo(name = "reciept_no_male")
    public String reciept_no_male;
    @ColumnInfo(name = "reciept_no_female")
    public String reciept_no_female;

    @ColumnInfo(name = "send_to_server")
    public int send_to_server;

    @ColumnInfo(name = "quantity_male")
    public String quantity_male;

    @ColumnInfo(name = "quantity_female")
    public  String quantity_female;

    public String getQuantity_male() {
        return quantity_male;
    }

    public void setQuantity_male(String quantity_male) {
        this.quantity_male = quantity_male;
    }

    public String getQuantity_female() {
        return quantity_female;
    }

    public void setQuantity_female(String quantity_female) {
        this.quantity_female = quantity_female;
    }

    public String getQuantity_other() {
        return quantity_other;
    }

    public void setQuantity_other(String quantity_other) {
        this.quantity_other = quantity_other;
    }

    public String getParent_other() {
        return parent_other;
    }

    public void setParent_other(String parent_other) {
        this.parent_other = parent_other;
    }

    public String getParent_other_lot() {
        return parent_other_lot;
    }

    public void setParent_other_lot(String parent_other_lot) {
        this.parent_other_lot = parent_other_lot;
    }

    public String getReciept_no_other() {
        return reciept_no_other;
    }

    public void setReciept_no_other(String reciept_no_other) {
        this.reciept_no_other = reciept_no_other;
    }

    @ColumnInfo(name = "quantity_other")
    public  String  quantity_other;

    @ColumnInfo(name = "parent_other")
    public  String  parent_other;
    @ColumnInfo(name = "parent_other_lot")
    public  String   parent_other_lot ;
    @ColumnInfo(name = "reciept_no_other")
    public  String  reciept_no_other ;

    public int getDelete_planting_line() {
        return delete_planting_line;
    }

    public void setDelete_planting_line(int delete_planting_line) {
        this.delete_planting_line = delete_planting_line;
    }

    @ColumnInfo(name = "delete_planting_line")
    private int delete_planting_line;

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    @ColumnInfo(name = "zone")
    private String zone;

    public static PlantingLineTable getLineDataFormate(PlantingHeaderModel.PlantingLine passmodel) {
        PlantingLineTable plantingLineTable = new PlantingLineTable();

        plantingLineTable.setCode(passmodel.code);
        plantingLineTable.setLine_no(passmodel.line_no);

       plantingLineTable.setDoc_no(passmodel.doc_no);
       plantingLineTable.setOrganizer_code(passmodel.organizer_code);
       plantingLineTable.setParent_male(passmodel.parent_male);
       plantingLineTable.setParent_female(passmodel.parent_female);
       plantingLineTable.setParent_female_lot(passmodel.parent_female_lot);
       plantingLineTable.setParent_male_lot(passmodel.parent_male_lot);
       plantingLineTable.setReciept_no_female(passmodel.reciept_no_female);
       plantingLineTable.setReciept_no_male(passmodel.reciept_no_male);

       plantingLineTable.setGrower_owner_code(passmodel.grower_owner_code);
       plantingLineTable.setGrower_land_owner_name(passmodel.grower_land_owner_name);
       plantingLineTable.setSupervisor_name(passmodel.supervisor_name);
       plantingLineTable.setCrop_code(passmodel.crop_code);
       plantingLineTable.setVariety_code(passmodel.variety_code);
       plantingLineTable.setItem_product_group_code(passmodel.item_product_group_code);
       plantingLineTable.setGrower_land_owner_name(passmodel.grower_land_owner_name);
       plantingLineTable.setItem_class_of_seeds(passmodel.item_class_of_seeds);
       plantingLineTable.setItem_crop_type(passmodel.item_crop_type);
       plantingLineTable.setItem_name(passmodel.item_name);
       plantingLineTable.setExpected_yield(passmodel.expected_yield);
       plantingLineTable.setSupervisor_name(passmodel.supervisor_name);
       plantingLineTable.setCrop_code(passmodel.crop_code);
       plantingLineTable.setRevised_yield_raw(passmodel.revised_yield_raw);
       plantingLineTable.setLand_lease(passmodel.land_lease);

        plantingLineTable.setUnit_of_measure_code(passmodel.unit_of_measure_code);
        plantingLineTable.setSowing_date_male(passmodel.sowing_date_male);
        plantingLineTable.setSowing_date_female(passmodel.sowing_date_female);
        plantingLineTable.setSowing_date_other(passmodel.sowing_date_other);
        plantingLineTable.setSowing_area_In_acres(passmodel.sowing_area_In_acres);
        plantingLineTable.setInspectionI(passmodel.InspectionI);
        plantingLineTable.setInspectionII(passmodel.InspectionII);
        plantingLineTable.setInspectionIII(passmodel.InspectionIII);
        plantingLineTable.setInspectionIV(passmodel.InspectionIV);
        plantingLineTable.setInspectionV(passmodel.InspectionV);
        plantingLineTable.setInspectionVI(passmodel.InspectionVI);
        plantingLineTable.setInspectionVII(passmodel.InspectionVII);
        plantingLineTable.setInspectionVIII(passmodel.InspectionVIII);
        plantingLineTable.setInspectionIX(passmodel.InspectionIX);
        plantingLineTable.setInspectionQC(passmodel.InspectionQC);
        plantingLineTable.setCreated_by(passmodel.created_by);
        plantingLineTable.setCreated_on(passmodel.created_on);
        plantingLineTable.setPoduction_lot_no(passmodel.production_lot_no);
        plantingLineTable.setNav_planting_no(passmodel.nav_planting_no);
        plantingLineTable.setNav_line_no(passmodel.nav_line_no);
        plantingLineTable.setVillage_name(passmodel.village_name);
        plantingLineTable.setTaluka(passmodel.taluka);
        plantingLineTable.setState(passmodel.state);
        plantingLineTable.setCity(passmodel.city);
        plantingLineTable.setAddress(passmodel.address);
        plantingLineTable.setReciept_no_other(passmodel.reciept_no_other);
        plantingLineTable.setQuantity_female(passmodel.quantity_female);
        plantingLineTable.setQuantity_male(passmodel.quantity_male);
        plantingLineTable.setQuantity_other(passmodel.quantity_other);
        plantingLineTable.setParent_other(passmodel.parent_other);
        plantingLineTable.setParent_other_lot(passmodel.parent_other_lot);



        return plantingLineTable;
    }

    public static PlantingHeaderModel.PlantingLine getServerTypeFormat(PlantingHeaderModel plantingHeaderModel, PlantingLineTable passData) {
        PlantingHeaderModel.PlantingLine plantingLine = plantingHeaderModel.new PlantingLine();
        plantingLine.code=passData.getCode();
        plantingLine.line_no=passData.getLine_no() ;
        plantingLine.doc_no=passData.getDoc_no();
        plantingLine.organizer_code=passData.getOrganizer_code();
        plantingLine.parent_male=passData.parent_male;
        plantingLine.parent_female=passData.parent_female;
        plantingLine.parent_female_lot=passData.parent_female_lot;
        plantingLine.parent_male_lot=passData.parent_male_lot;

        plantingLine.reciept_no_female=passData.reciept_no_female;
        plantingLine.reciept_no_male=passData.reciept_no_female;
        plantingLine.grower_owner_code=passData.getGrower_owner_code() ;
        plantingLine.grower_land_owner_name=passData.getGrower_land_owner_name() ;
        plantingLine.supervisor_name=passData.getSupervisor_name();
        plantingLine.crop_code=passData.getCrop_code();
        plantingLine.variety_code=passData.getVariety_code();
        plantingLine.item_product_group_code=passData.getItem_product_group_code();
        plantingLine.item_class_of_seeds=passData.getItem_class_of_seeds();
        plantingLine.item_crop_type=passData.getItem_crop_type();
        plantingLine.item_name=passData.getItem_name();
        plantingLine.expected_yield=passData.getExpected_yield();
        plantingLine.revised_yield_raw=passData.getRevised_yield_raw();
        plantingLine.land_lease=passData.getLand_lease();
        plantingLine.unit_of_measure_code=passData.getUnit_of_measure_code();
        plantingLine.sowing_date_male=passData.getSowing_date_male();
        plantingLine.sowing_date_female=passData.getSowing_date_female();
        plantingLine.sowing_date_other=passData.getItem_crop_type();
        plantingLine.sowing_area_In_acres=passData.getItem_name();
        plantingLine.InspectionI=passData.getInspectionI();
        plantingLine.InspectionII=passData.getInspectionII();
        plantingLine.InspectionIII=passData.getInspectionIII();
        plantingLine.InspectionIV=passData.getInspectionIV();
        plantingLine.InspectionV=passData.getInspectionV();
        plantingLine.InspectionVI=passData.getInspectionVI();
        plantingLine.InspectionVII=passData.getInspectionVII();
        plantingLine.InspectionVIII=passData.getInspectionVIII();
        plantingLine.InspectionIX=passData.getInspectionIX();
        plantingLine.InspectionQC=passData.getInspectionQC();
        plantingLine.created_by=passData.getCreated_by();
        plantingLine.created_on=passData.getCreated_on();
        plantingLine.production_lot_no=passData.getPoduction_lot_no();
        plantingLine.nav_planting_no=passData.getNav_planting_no();
        plantingLine.nav_line_no=passData.getNav_line_no();

        plantingLine.reciept_no_other=passData.getReciept_no_other();
        plantingLine.quantity_female=passData.getQuantity_female();
        plantingLine.quantity_male=passData.getQuantity_male();
        plantingLine.quantity_other=passData.getQuantity_other();
        plantingLine.parent_other=passData.getParent_other();
        plantingLine.parent_other_lot=passData.getParent_other_lot();
        plantingLine.zone=passData.getZone();
        return plantingLine;
    }




}
