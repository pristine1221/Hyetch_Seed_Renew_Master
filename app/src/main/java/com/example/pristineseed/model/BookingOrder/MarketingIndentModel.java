package com.example.pristineseed.model.BookingOrder;

import java.util.ArrayList;
import java.util.List;

public class MarketingIndentModel {
    public boolean condition;
    public String marketing_indent_no;
    public String date;
    public String posted;
    public String season;
    public String season_name;
    public String no_series;
    public String customer_no;
    public String name;
    public String name_2;
    public String address;
    public String address_2;
    public String zone;
    public String state;
    public String region;
    public String area;
    public String territory;
    public String location_code;
    public String zone_name;
    public String state_name;
    public String territory_name;
    public String region_name;
    public String area_name;
    public String customer_type;
    public String ship_to_code;
    public String ship_to_name;
    public String ship_to_name_2;
    public String ship_to_address;
    public String ship_to_address_2;
    public String ship_to_postcode;
    public String ship_to_city;
    public String ship_to_contact;
    public String region_code;
    public String ship_to_county;
    public String shipment_date;
    public String GST_ship_to_state_code;
    public String ship_to_gst_reg_no;
    public String shipment_method_code;
    public String shipping_agent_code;
    public String shipping_agent_service_code;
    public String dispatch_location_code;
    public String status;
    public String sales_type;
    public String dispatch_by_date;
    public String created_by;
    public String created_on;
    public String approver_id;
    public String customer_name;


    public List<MarketingIndentLine> marketing_indent_line = new ArrayList<>();

    public class MarketingIndentLine {
        public boolean condition;
        public String message;
        public String marketing_indent_no;
        public String line_no;
        public String crop_code;
        public String variety_group;
        public String booking_qty;
        public String alotted_qty;
        public String do_created;
        public String posted;
        public String alotted_percent;
        public String unit_of_measure_code;
        public String variety_code;
        public String variety_name;
        public String variety_package_size;
        public String variety_class_of_seeds;
        public String variety_product_group_code;
        public String no_of_bags;
        public String VarietyGroup;
        public String booking_rate;
        public String booking_indent_no;
        public String nav_booking_indent_no;
        public String indent_qty;
        public String balance_qty;
        public String supplies_qty;
        public String unit_price;
        public String line_discount_percent;
        public String line_amount;
        public String created_by;
        public String created_on;

        //todo bind marketing indent line fields....
        public String No;
        public String Season_Code;
        public String Distributor_Code;
        public String Distributor_Name;
        public String District_Code;
        public String District_Name;
        public String Crop_Code;
        public String Variety_Class_of_Seeds;
        public String Variety_Code;
        public String Variety_Name;
        public String Variety_Product_Group_Code;
        public String Target;
        public String Booking_Qty;
        public String Alloted_PERCENT;
        public String Alloted_Qty;
        public String No_Series;
        public String Posted;

    }
}
