package com.example.pristineseed.ui.dashboard.newTheam;

public class WeatherResponse {

    public Current.Location location;
    public Current current;

    public class Current {

        public String last_updated_epoch;
        public String last_updated;

        public String temp_f;

        public String temp_c;

        public String is_day;

        public String wind_mph;

        public String wind_kph;

        public String wind_degree;

        public String wind_dir;

        public String pressure_mb;

        public String pressure_in;

        public String precip_mm;

        public String precip_in;

        public String humidity;

        public String cloud;

        public String feelslike_c;

        public String feelslike_f;

        public String vis_km;

        public String vis_miles;

        public String uv;

        public String gust_mph;

        public String gust_kph;

        public Condition condition;


        public class Condition {
                  public String  text;
                  public  String  icon;
                  public  String  code;

        }

        public class Location {
            public String name;
            public String region;
            public String country;
            public String lat;
            public String lon;
            public String tz_id;
            public String localtime_epoch;
            public String localtime;
        }
    }

}
