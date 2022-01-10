package com.example.pristineseed.model.seed_dispatch_note;

import java.util.ArrayList;
import java.util.List;

public class SeedDispatchHeaderModel {
         public boolean condition;
         public String message;
         public String dispatch_no;
         public String date;
         public String from_location;
         public String to_location;
         public String supervisor;
         public String transporter;
         public String organizer_name;
         public String organizer_code;
         public String truck_number;
         public String season_code;
         public String camp_at;
         public String remarks;
         public String created_on;
         public String created_by;
         public int status;
         public String nav_sync;
         public String nav_message;
         public int send_to_server_header;
         public int delete_line_header=0;
         public String document_type;
         public String refrence_no;

    public List<SeedDispatchLineModel>dispatch_line=new ArrayList<>();

         public class SeedDispatchLineModel{
                    public boolean condition;
                    public String message;
                    public String line_no;
                    public String dispatch_no;
                    public String farmer_code;
                    public String farmer_name;
                    public String farmer_name2;
                    public String village_name;
                    public String taluka;
                    public String state;
                    public String city;
                    public String lot_number;
                    public String hybrid;
                    public String number_of_bags;
                    public String quantity ;
                    public String created_on ;
                    public String remarks;
                    public int insert_line_into_local;
                    public String moisture_prcnt;
                    public String harvested_acreage;
                    public String got;
                    public String hybrid_name;

         }
}
