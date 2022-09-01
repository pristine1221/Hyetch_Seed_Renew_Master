package com.example.pristineseed.common_data;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.AccessibleObject;

public final class CommonData {

   public static String[] stateArray={"Mizoram(001)","Andhra Pradesh(002)","Arunachal Pradesh(003)","Assam(004)","Bihar(005)","Chhattisgarh(006)","Goa(007)","Gujarat(010)","Haryana(011)","Himachal Pradesh(012)",
            "Jharkhand(013)","Karnataka(014)","Kerala(015)","Madhya Pradesh(016)","Maharashtra(017)","Manipur(018)","Meghalaya(019)","Mizoram(020)","Nagaland(021)","Odisha(022)","Punjab(023)","Rajasthan(023)",
            "Sikkim(024)","Tamil Nadu(025)","Telangana(026)","Tripura(027)","Uttar Pradesh(027)","Uttarakhand(028)","West Bengal(029)"};

   public static  String[] forward_by={"TSE","TSM","DSM","ASM"};

   public static  String[] gst_type={"Registered","Unregistered","Export","Deemed Export","Exempted","SEZ Development","SEZ Unit"};

   public static  String[] paymentType ={"Outstanding","TAB/ABS","Security Deposit"};

   public static String[] germination_per={"95-99","85-94","75-84","<75"};

   public static  String[] vigor={"Good","Average","Weak"};

   public static String[] pest={"Stem Borer","Fall Army Worm","Leaf Hopper","Leaf Folder","Pink Stem Borer","Cut Worm","Cob Borer","Thrips","Aphids","Shoot Fly","Nematode","Termites"};
   public static String[] desease={"Bacterial Leaf Blight (BLB)","Leaf Blast","Brown Spot","Bacterial Stalk Rot","Rust","Wilt","mosiac Virus","Roor Rot","Downy Mildew"};
   public static String[] pld={"Poor Male Germination","Poor Female Germination","Low Plant Population","Irrigation Stress","Water Logging Condition","Heavy Weeds"," Un-inform field","Poor Crop Estabilishment","Pest / Disease Damage","Male - Female Mixing","Animal Damage","Poor Grower Managament"};

   public static String[] pest_infalation={"0-3","4-6","7-9"};
   public static String[] desease_infalation={"0-3","4-6","7-9"};
   public static  String[] crop_condition={"Good","Average","Poor"};
   public static  String[] male_female={"Male","Female"};

   public static String[] over_all_agronomy={"A","B","C"};

   public static String[] isolation_={"-","Distance","Grain","Time"};

   public static String[] flag_status={"GF","YF","RF"};

   public static String[] crop_={"Maize","Sorghum","Pearl Millet","Mustard"};

   public static String[] parent_type={"Single","Cross","NA"};

   public static String[] first_top_dressing_bags={"1","2","3","4"};

   public static  String[] first_top_dressing={"2020013","2828","Urea","191919","102626","143514"};
   public static String[] recommend_dose_of_fertilizer={"2020013","2828","Urea","191919","102626","143514"};

   public static String[] type={"Certified","Non-Certified","NA"};

   public static String[] expense_type={"Own Vehicle", "Company Vehicle"};

   public static String[] doc_sub_type={"FSIO", "BSIO"};

   public static String[] month={"January", "February","March","April","May","June","July","August","September","October","November","December"};

   public static String[] status={"Open","Close"};

   public static String[] pld_lot_stauts={"PLD","Bank"};

   public static  String[] stage_code={"raw","cobs","condition"};

   public static String[] option_history={"< 3", "<6", "<9"," >12 months"};

   public static String[] history_on_prvs_crop={"Same Crop","Different Crop"};

   public static String[] sales_type={"Demo Seed","Normal Seed","Export Seed","Compensation Seed"};

   public static String[] tsp_type={"CORN","SORGHUM","BAJRA"};

   public static String[]  soil_type=  {"Light","Heavy","Medium"};
   public static  String[] uniform_of_silking={"Uniform","Non-Uniform"};
   public static String[] sensitive_tempr={"Yes","No"};

   public static String[] doc_type={"Foundation","Hybrid"};

   public static String[] leaveTypeList = {"Single Leave", "Multi Leave"};
   public  static String[] leaveSubList = {"Casual Leave", "Comp Off", "Optional Leave", "Priviledge Leaves", "Sick Leaves"};

   public static  String[] wthr_recmmnd={"Yes","No"};

   public static String[] status_male_female={"Advancing","Same","Delay"};

   public static String[] crop_stage={"Germination","Vegetative",
           "Flag Leaf","Flowering","Grain Formation","Maturity","Tassel","Silk"};


   public static String[] off_type_male_female={"0.1","0.1-0.5",">0.5","0.05","0.05-0.08",">0.08"};

   public static String[] pollen_shd={"*5/acre","*5-15acre",">15/acre","0.05%","0.05-0.08%",">0.08"};

   public static String[] iso_dis={"NA","<50","<100","100-200","<300","300-400","400-500"};

   public static String[] nick_per={"NA","5%","5 to 25%",">25%"};

   public static String[] border_rows={"NA","Present with synchrony","Present with partial synchrony","Absent/Present without synchrony"};

//    ,NA,Towards contaminant,Towards our crop
   public static String[] wind_dir={"NA","Towards contaminant","Towards our crop"};

   public static String[] male_stand={"NA",">75%","50 to 75%","<50"};

   public static String[] lot_recommnd={"Flag-Green","Flag-Yellow","Flag-Red"};

   public static String[] abiotic_stress={"Heat","water logging"};

   public static String weather_key = "e96dcbfd604c4420b4a65954211108";

   public static String WEATHER_URL = "https://api.weatherapi.com";

   public static  String[] seed_setting={"Good","Average","Poor"};

   public static String[] got={"Yes","No"};

   public static void hideSoftKeyBoard(Activity activity) {
      try {
         if(activity!=null) {
            // hides the soft keyboard when the drawer opens
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);

            inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }



}
