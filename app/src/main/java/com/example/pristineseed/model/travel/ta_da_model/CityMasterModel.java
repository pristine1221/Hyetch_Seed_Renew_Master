package com.example.pristineseed.model.travel.ta_da_model;

import java.util.ArrayList;
import java.util.List;

public class CityMasterModel {
    public boolean condition;
    public String code;
    public String name;
    public String country_region_code;
    public String class_of_city;
    public int active;

    public List<CityMaster> data = new ArrayList<>();

    public class CityMaster{
        //todo for new city master model variables....
        public String City;
        public String Code;
        public String Country_Region_Code;
        public String County;
        public String TimeZone;
        public String Class_of_City;
        public String City_Category;
    }
}




