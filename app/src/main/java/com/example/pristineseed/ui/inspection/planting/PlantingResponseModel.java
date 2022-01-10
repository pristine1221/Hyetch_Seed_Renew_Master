package com.example.pristineseed.ui.inspection.planting;

import java.util.ArrayList;
import java.util.List;

public class PlantingResponseModel {
    public String planting_number;
    public String Season_code;
    public String Season_name;
    public String Date;
    public String Organizer;
    public String FSIO_Details;
    public String Hybrid;
    public List<PlantingLineModel> il = new ArrayList<>();


    public class PlantingLineModel {
        public String planting_number;
        public String production_lot_no; //todo its auto genrate in planting
        //todo farmaer detail
        public String farmername;
        public String farmerContactNo;

        //todo vilage detail
        public String farmeVillageName;
        public String villageaddresss;

        public String expected_Yield;
        public String Area_as_measured;
    }
}
