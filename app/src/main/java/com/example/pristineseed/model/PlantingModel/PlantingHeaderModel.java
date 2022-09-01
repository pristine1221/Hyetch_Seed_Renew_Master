package com.example.pristineseed.model.PlantingModel;

import java.util.ArrayList;
import java.util.List;

public class PlantingHeaderModel {
    public boolean condition;
    public String created_on;
    public int total_rows;
    public String message;
    public String code;
    public String organizer_code;
    public String organizer_name;
    public String production_centre_loc;
    public String production_centre_name;
    public String date;
    public String date_of_harvest;
    public String season_code;
    public String season_name;
    public int status;
    public String nav_sync;
    public String nav_message;
    public String type;
    public String stage_code;
    public String total_sowing_area_in_acres;
    public String total_land_in_acres;
    public String parent_type;
    public String Document_SubType;
    public String created_by;
    public String completed_on;
    public String hybrid;
    public int delete_line_header = 0;
    public int add_online_offlie_header;
    public List<PlantingLine> pl = new ArrayList<>();

    public class PlantingLine {
        public boolean condition;
        public String message;
        public String code;
        public String line_no;
        public String doc_no;
        public String organizer_code;
        public String parent_male;
        public String parent_male_lot;
        public String parent_female;
        public String parent_female_lot;
        public String reciept_no_male;
        public String reciept_no_female;
        public String grower_owner_code;
        public String grower_land_owner_name;
        public String supervisor_name;
        public String crop_code;
        public String crop_name;
        public String variety_code;
        public String item_product_group_code;
        public String item_class_of_seeds;
        public String item_crop_type;
        public String alias_code;
        public String item_name;
        public String expected_yield;
        public String revised_yield_raw;
        public String land_lease;
        public String unit_of_measure_code;
        public String sowing_date_male;
        public String sowing_date_female;
        public String sowing_date_other;
        public String sowing_area_In_acres;
        public String village_name;
        public String taluka;
        public String sub_zone;
        public String state;
        public String city;
        public String zone;
        public String address;
        public String InspectionI;
        public String InspectionII;
        public String InspectionIII;
        public String InspectionIV;
        public String InspectionV;
        public String InspectionVI;
        public String InspectionVII;
        public String InspectionVIII;
        public String InspectionIX;
        public String InspectionQC;
        public String created_by;
        public String created_on;
        public String production_lot_no;
        public String nav_planting_no;
        public String nav_line_no;
        public int send_to_server_line;
        public String quantity_male;
        public String quantity_female;
        public String quantity_other;
        public String parent_other;
        public String parent_other_lot;
        public String reciept_no_other;

        public int delete_planting_line = 0;

        //todo new fields......................
        public String standing_acres;
        public String pld_acre;
        public String net_acre;
        public String pld_reason;

    }
}
