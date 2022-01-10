package com.example.pristineseed.model.fs_return_harvesting;

import java.util.List;

public class FSReturnHarvestingModel {
        public boolean condition ;
         public String fs_return_code;
         public String location;
         public String season;
         public String remarks;
         public String created_by;
         public String created_on;
         public String updated_by;
         public String updated_on;
         public String nav_syn;
         public String nav_message;
         public String status ;
         public String approver_id;
         public String doc_type;
         public String location_name;
         public String season_name;
         public String fsioSalesOrderNo;

         public List<FSReturnHarvestingLineModel>fs_return_line;

                 public class FSReturnHarvestingLineModel{
                 public boolean condition;
                 public String message;
                 public String fs_return_code;
                 public String line_no;
                 public String crop;
                 public String hybrid;
                 public String hybrid_name;
                 public String crop_name;
                 public String female_lot_no;
                 public String female_no_of_bags;
                 public String female_pack_size;
                 public String female_quantity;
                 public String male_lot_no;
                 public String male_no_of_bags;
                 public String male_pack_size;
                 public String male_quantity;
                 public String male_bulk_lot_no;
                 public String dispatch_date;
                 public String physical_condition;
                 public String remarks;
                 public String created_by;
                 public String created_on;
                 public String updated_by;
                 public String updated_on;
                 public String nav_syn;
                 public String nav_message;
                 public String status ;
                 public String approver_id;
                 public String creator;

                }
}
