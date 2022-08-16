package com.example.pristineseed.model.scheduler_inspection;

import java.util.ArrayList;
import java.util.List;

public class SchedulerModel {
           public boolean condition;
           public String schedule_no;
           public String user_id;
           public String season;
           public String season_name;
           public String production_centre;
           public String production_centre_name;
           public String date;
           public String status;
           public String user_type;

          public List<SchedulerLines> scheduler_lines=new ArrayList<>();

    public class SchedulerLines{
        public String schedule_no;
        public String arrival_plan_no;
        public String production_lot_no;
        public String line_no;
        public String fsio_no;
        public String organizer_code;
        public String organizer_name;
        public String grower_owner;
        public String grower_land_owner_name;
        public String grower_village;
        public String grower_taluka_mandal;
        public String grower_district;
        public String grower_city;
        public String supervisor_name;
        public String crop_code;
        public String variety_code;
        public String item_product_group_code;
        public String item_class_of_seeds;
        public String item_crop_type;
        public String item_name;
        public String revised_yield_raw;
        public String land_lease;
        public String unit_of_measure_code;
        public String sowing_date_male;
        public String sowing_date_female;
        public String sowing_date_other;

        public String pld_marked;

        public int inspection_1;
        public String ins1_completed_on;
        public int ins1_nav_sync;
        public int inspection_2;
        public String ins2_completed_on;
        public int ins2_nav_sync;
        public int inspection_3;
        public String ins3_completed_on;
        public int ins3_nav_sync;
        public int inspection_4;
        public String ins4_completed_on;
        public int ins4_nav_sync;
        public int inspection_5;
        public String ins5_completed_on;
        public int ins5_nav_sync;
        public int inspection_6;
        public String ins6_completed_on;
        public int ins6_nav_sync;
        public int inspection_7;
        public String ins7_completed_on;
        public int ins7_nav_sync;
        public int inspection_8;
        public String ins8_completed_on;
        public int ins8_nav_sync;
        public int inspection_qc;
        public int inspection_9;
        public String ins9_completed_on;
        public int ins9_nav_sync;
        public String ins_qc_completed_on;
        public int ins_qc_nav_sync;

       public int ins1_sync_with_server;
       public int ins2_sync_with_server;
        public int ins3_sync_with_server;
        public int ins4_sync_with_server;
        public int ins5_sync_with_server;
        public int ins6_sync_with_server;
        public int ins7_sync_with_server;
        public int ins8_sync_with_server;
        public int ins9_sync_with_server;

        public List<Germination_InspectionLineModel>germination_inspection1;
        public List<SeedLing_InspectionLineModel>seedling_inspection2;
        public List<Vegitative_InspectionModel>vegetative_inspection3;
        public List<Nicking_InspectionLineModel>nicking_inspection4;
        public List<Nicking2InspectionModel>nicking2_inspection5;
        public List<FloweringInspectionModel>flowering_inspection6;
        public List<PostFloweringInspectionModel>post_flowering_inspection7;
        public List<MaturityInspectionModel>maturity_inspection8;
        public List<HarvestingInspectionModel>harvesting_inspection9;
        public List<Qc_Inspection_Model>inspectionQC;

    }
}
