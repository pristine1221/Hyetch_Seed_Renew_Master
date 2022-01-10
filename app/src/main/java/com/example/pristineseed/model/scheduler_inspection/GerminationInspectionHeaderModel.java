package com.example.pristineseed.model.scheduler_inspection;

import java.util.ArrayList;
import java.util.List;

public class GerminationInspectionHeaderModel {
    public boolean  condition;
    public String schedule_no;
    public String production_lot_no;

   public  int inspection_1;
   public  int inspection_2;
   public  int inspection_3;
   public  int inspection_4;
   public  int inspection_5;
   public  int inspection_6;
   public  int inspection_7;
   public  int inspection_8;
   public  int inspection_9;
  public int inspection_qc;


   public String ins1_completed_on;
   public int ins1_nav_sync;

   public String ins2_completed_on;
   public int ins2_nav_sync;

   public String ins3_completed_on;
   public int ins3_nav_sync;

   public String ins4_completed_on;
   public int ins4_nav_sync;

   public String ins5_completed_on;
   public int ins5_nav_sync;

   public String ins6_completed_on;
   public int ins6_nav_sync;

   public int ins7_nav_sync;
   public String ins7_completed_on;

   public int ins8_nav_sync;
   public String ins8_completed_on;

   public int ins9_nav_sync;
   public String ins9_completed_on;

   public int ins_qc_nav_sync;
   public String ins_qc_completed_on;

    public List<Germination_InspectionLineModel> germination_ins1=new ArrayList<>();

    public List<SeedLing_InspectionLineModel> seedling_ins2= new ArrayList<>();

    public List<Vegitative_InspectionModel> vegitative_ins3=new ArrayList<>();

    public List<Nicking_InspectionLineModel> nicking_ins4=new ArrayList<>();

    public List<Nicking2InspectionModel> nicking2_ins5=new ArrayList<>();
    public List<FloweringInspectionModel>  flowering_ins6=new ArrayList<>();

    public List<PostFloweringInspectionModel> post_flowering_ins7=new ArrayList<>();

    public List<MaturityInspectionModel> maturity_ins8=new ArrayList<>();
    public List<HarvestingInspectionModel>  harvesting_inspection9  =new ArrayList<>();

    public List<Qc_Inspection_Model> inspection_qc1=new ArrayList<>();

}
