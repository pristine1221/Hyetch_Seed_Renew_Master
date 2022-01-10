package com.example.pristineseed.firebase_notification_service;

import java.util.List;

public class InspectionNotificationModel {

   public boolean  condition;
    public String  id;
     public String  hybrid;
      public String  name;
      public String created_on;

      public List<BodyArrayModel> bodyArray;



    public class BodyArrayModel{

                   public String production_centre_name;
                   public String production_lot_no;
                   public String inspection_type;
    }
}
