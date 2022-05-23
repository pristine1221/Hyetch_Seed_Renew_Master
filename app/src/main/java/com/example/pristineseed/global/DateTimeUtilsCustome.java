package com.example.pristineseed.global;

import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class DateTimeUtilsCustome {
    private static final String TAG = "DateUtils";
    public static final String DATE_FORMAT_1 = "hh:mm a";
    public static final String DATE_FORMAT_2 = "h:mm a";
    public static final String DATE_FORMAT_3 = "yyyy-MM-dd";
    public static final String DATE_FORMAT_4 = "dd-MMMM-yyyy";
    public static final String DATE_FORMAT_5 = "dd MMMM yyyy";
    public static final String DATE_FORMAT_6 = "dd MMMM yyyy zzzz";
    public static final String DATE_FORMAT_7 = "EEE, MMM d, ''yy";
    public static final String DATE_FORMAT_8 = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_9 = "h:mm a dd MMMM yyyy";
    public static final String DATE_FORMAT_10 = "K:mm a, z";
    public static final String DATE_FORMAT_11 = "hh 'o''clock' a, zzzz";
    public static final String DATE_FORMAT_12 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String DATE_FORMAT_13 = "E, dd MMM yyyy HH:mm:ss z";
    public static final String DATE_FORMAT_14 = "yyyy.MM.dd G 'at' HH:mm:ss z";
    public static final String DATE_FORMAT_15 = "yyyyy.MMMMM.dd GGG hh:mm aaa";
    public static final String DATE_FORMAT_16 = "EEE, d MMM yyyy HH:mm:ss Z";
    public static final String DATE_FORMAT_17 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public static final String DATE_FORMAT_18 = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String DATE_FORMAT_19 = "MMM dd, yyyy";

    public static String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_1);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date today = Calendar.getInstance().getTime();
        return dateFormat.format(today);
    }

    public static String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_18);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date today = Calendar.getInstance().getTime();
        return dateFormat.format(today);
    }

    public static String getDateFormat_18(){
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_1);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date today = Calendar.getInstance().getTime();
        return dateFormat.format(today);
    }

    public static String getDateYYMMDD(String value) {
        try {
            SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");
            Date date = formatter1.parse(value);
            SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");
            return newFormat.format(date);
        } catch (Exception e) {
            return value;
        }
    }

    /**
     * @param time        in milliseconds (Timestamp)
     * @param mDateFormat SimpleDateFormat
     */
    public static String getDateTimeFromTimeStamp(Long time, String mDateFormat) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(mDateFormat);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date dateTime = new Date(time);
        return dateFormat.format(dateTime);
    }

    /**
     * Get Timestamp from date and time
     *
     * @param mDateTime   datetime String
     * @param mDateFormat Date Format
     * @throws ParseException
     */
    public static long getTimeStampFromDateTime(String mDateTime, String mDateFormat)
            throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(mDateFormat);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = dateFormat.parse(mDateTime);
        return date.getTime();
    }

    /**
     * Return  datetime String from date object
     *
     * @param mDateFormat format of date
     * @param date        date object that you want to parse
     */
    public static String formatDateTimeFromDate(String mDateFormat, Date date) {
        if (date == null) {
            return null;
        }
        return DateFormat.format(mDateFormat, date).toString();
    }

    /**
     * Convert one date format string  to another date format string in android
     *
     * @param inputDateFormat  Input SimpleDateFormat
     * @param outputDateFormat Output SimpleDateFormat
     * @param inputDate        input Date String
     * @throws ParseException
     */
    public static String formatDateFromDateString(String inputDateFormat, String outputDateFormat,
                                                  String inputDate) throws ParseException {
        Date mParsedDate;
        String mOutputDateString;
        SimpleDateFormat mInputDateFormat =
                new SimpleDateFormat(inputDateFormat, java.util.Locale.getDefault());
        SimpleDateFormat mOutputDateFormat =
                new SimpleDateFormat(outputDateFormat, java.util.Locale.getDefault());
        mParsedDate = mInputDateFormat.parse(inputDate);
        mOutputDateString = mOutputDateFormat.format(mParsedDate);
        return mOutputDateString;
    }

    public static String getDate(String value) {
        try {
            return value.substring(0, 10).replace('-', '/');
        } catch (Exception e) {
            return value.substring(0, 10);
        }
    }

    public static String getTime(String value) {
        try {
            int time = Integer.parseInt(value.substring(11, 13));
            if (time - 12 > 0) {
                return time - 12 + ":" + value.substring(14, 16) + " :PM";
            } else {
                return time + ":" + value.substring(14, 16) + " :AM";
            }
        } catch (Exception e) {
            return value.substring(11, 16);
        }
    }

    public static String getDate_Time(String value) {
        try {
            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = formatter1.parse(value);
            SimpleDateFormat newFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm aa");
            return newFormat.format(date);
        } catch (Exception e) {
            return value.substring(0, 10);
        }
    }

    public static String[] monthName = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
            "Aug", "Sep", "Oct", "Nov",
            "Dec"};

    public static String getCurrentDateBY() {
        Calendar c = Calendar.getInstance();

        String month = monthName[c.get(Calendar.MONTH)];
        int year = c.get(Calendar.YEAR);
        int date = c.get(Calendar.DATE);
        return month + " " + date + ", " + year;
    }

    public static String getCurrentDateBY_() {
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH) + 1;
        int year = c.get(Calendar.YEAR);
        int date = c.get(Calendar.DATE);
        return date + "-" + month + "-" + year;
    }

    public static String IncreaseDayOfDate(String passdate, int dayIncrease) {
        try {
            String[] split = passdate.split("-");
            int date = Integer.valueOf(split[0]);
            int month = Integer.valueOf(split[1]) - 1;
            int year = Integer.valueOf(split[2]);
            Calendar c = Calendar.getInstance();
            c.set(year, month, date);
            c.add(Calendar.DATE, dayIncrease);
            month = c.get(Calendar.MONTH) + 1;
            year = c.get(Calendar.YEAR);
            date = c.get(Calendar.DATE);
            return date + "-" + month + "-" + year;
        } catch (Exception e) {
            return passdate;
        }
    }

    public static String getDateMMMDDYYYY(String value) {
        try {
            if (value == null || value.equalsIgnoreCase("")) {
                return "";
            }
            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = formatter1.parse(value);
            String month = monthName[date.getMonth()];
            int year = 1900 + date.getYear();
            long day = date.getDate();
            return month + " " + day + ", " + year;
        } catch (Exception e) {
            return value.substring(0, 10);
        }
    }

    public static String getDateMMMDDYYYYSlsh(String value) {
        try {
            if (value == null || value.equalsIgnoreCase("")) {
                return "";
            }
            SimpleDateFormat formatter1 = new SimpleDateFormat("MM/dd/yyyy'T'HH:mm:ss");//03/03/2021 00:00:00",
            Date date = formatter1.parse(value);
            String month = monthName[date.getMonth()];
            int year = 1900 + date.getYear();
            long day = date.getDate();
            return month + " " + day + ", " + year;
        } catch (Exception e) {
            return value.substring(0, 10);
        }
    }



    public static String getDateMMMDDYYYYSlsh1(String value) {
        try {
          if(!value.equalsIgnoreCase("")) {
              String substring_value=value.substring(0,10);
              String[] value1 = substring_value.split("/");
              String mnth = value1[0];
              String dd = value1[1];
              String year = value1[2];
              if (mnth.length() == 1) {
                  return dd + "-" + "0" + mnth + "-" + year;
              } else {
                  return dd + "-" + mnth + "-" + year;
              }
          }
          else {
              return "";
          }
        } catch (Exception e) {
            return value.substring(0, 10);
        }
    }

    public static String getDateOnly(String value) {
        try {
            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = formatter1.parse(value);
            SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yyyy");
            return newFormat.format(date);
        } catch (Exception e) {
            return value;
        }
    }

    public static String getDateOnlyInDash(String value) {
        try {
            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = formatter1.parse(value);
            SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yyyy");
            return newFormat.format(date);
        } catch (Exception e) {
            return value;
        }
    }

    public static String getDateOnlyInSlash(String value) {
        try {
            SimpleDateFormat formatter1 = new SimpleDateFormat("MM-dd-yyyy'T'HH:mm:ss");
            Date date = formatter1.parse(value);
            SimpleDateFormat newFormat = new SimpleDateFormat("dd/MM/yyyy");
            return newFormat.format(date);
        } catch (Exception e) {
            return value;
        }
    }

    public static String getDateYYYYMMDD(String value) {
        try {
            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = formatter1.parse(value);
            SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
            return newFormat.format(date);
        } catch (Exception e) {
            return value;
        }
    }

    public static String getDateDDMMYYY(String value) {
        try {
            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = formatter1.parse(value);
            SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yyyy 'T' hh:mm aa");
            return newFormat.format(date);
        } catch (Exception e) {
            return value;
        }
    }


    public static String dateDiffrence(String passdate, String passdate1) {
        try {
            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
            Date date = formatter1.parse(passdate);
            Date date1 = formatter1.parse(passdate1);
            Date secondDate, firstDate;
            if (date.compareTo(date1) > 0) {
                secondDate = date;
                firstDate = date1;
            } else {
                secondDate = date1;
                firstDate = date;
            }
            long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            return String.valueOf(diff);
        } catch (Exception e) {
            return "0";
        }
    }

    public static String timeConvert12(String str) {
        try {
            SimpleDateFormat displayFormat = new SimpleDateFormat("hh:mm a");
            SimpleDateFormat parseFormat = new SimpleDateFormat("HH:mm");
            Date date = parseFormat.parse(str);
            return displayFormat.format(date);
        } catch (Exception e) {
            return str;
        }
    }

    public static String splitDateInYYYMMDD(String date){
        if(!date.equalsIgnoreCase("")) {
            String[] value = date.split("-");
            String dt = value[0];
            String mnth = value[1];
            String year = value[2];
            if (mnth.length() == 1) {
                return year + "-" + "0" + mnth + "-" + dt;
            } else {
                return year + "-" + mnth + "-" + dt;
            }
        }
        return "";
    }

    public static String splitDateInYYYMMDDslsh(String date){
        if(!date.equalsIgnoreCase("")){
        String[] value= date.split("-");
        String year=value[0];
        String mnth=value[1];
        String dt=value[2];
        return  dt+"-"+mnth+"-"+year;
        }
        return "";
    }

    public static String splitDateInMMDDYYYYslsh(String date){
        String[] value= date.split("-");
        String dt=value[0];
        String mnth=value[1];
        String year=value[2];

        return  mnth+"/"+dt+"/"+year;

    }



    public static String splitDateDDDMMYYYY(String date){
        if(date!=null && !date.equalsIgnoreCase("")) {
            String date_sub_string=date.substring(0,11);
            String[] value = date_sub_string.split("-");
            String dt = value[0];
            String mnth = value[1];
            String year = value[2];
            return dt + "-" + mnth + "-" + year;
        }
        else {
            return "";
        }
    }



    public static String splitDateInMMDDYYYYDash(String date){
        String[] value= date.split("-");
        String dt=value[0];
        String mnth=value[1];
        String year=value[2];
        return  mnth+"-"+dt+"-"+year;

    }

    public static String getDateWithSplitTime(String value) {
        try {
            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = formatter1.parse(value);
            SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");
            return newFormat.format(date);
        } catch (Exception e) {
            return value.substring(0, 10);
        }
    }

    public static String returnDateWithSplitTime(String value) {
        try {
            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = formatter1.parse(value);
            SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");
            return newFormat.format(date);
        } catch (Exception e) {
            return value.substring(0, 10);
        }
    }


    public static String getDateDDMMMYYYY(String value) {
        try {
            if (value == null || value.equalsIgnoreCase("")) {
                return "";
            }
            SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy-'T'HH:mm:ss");
            Date date = formatter1.parse(value);
            String month = monthName[date.getMonth()];
            int year = 1900 + date.getYear();
            long day = date.getDate();
            return day + " " + month + ", " + year;//recent_chnage_order
        } catch (Exception e) {
            return value;//.substring(0, 9);
        }
    }

    //todo for employee...

    public static String getDateTimeOnly(String value) {
        try {
            SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");
            Date date = formatter1.parse(value);
            SimpleDateFormat newFormat = new SimpleDateFormat("MM/dd/yyyy");
            return newFormat.format(date);
        } catch (Exception e) {
            return value;
        }
    }

    public static String getDateOnlyChange(String value) {
        try {
            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = formatter1.parse(value);
            SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");
            return newFormat.format(date);
        } catch (Exception e) {
            return value;
        }
    }


    public static String get_yyyy_mm_dd_to_dd_mm_yyyy_ToDateFormate1(String value) {
        try {
            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
            Date date = formatter1.parse(value);
            SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yyyy");
            return newFormat.format(date);//yyyy-MM-dd'T'HH:mm:ss
        } catch (Exception e) {
            return null;
        }
    }

    public static String get_yyyy_mm_dd_to_dd_mm_yyyy_ToDateFormate2(String value) {
        try {
            SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
            Date date = formatter1.parse(value);
            SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yyyy");
            return newFormat.format(date);//yyyy-MM-dd'T'HH:mm:ss
        } catch (Exception e) {
            return null;
        }
    }


    public static String get_yyyy_mm_dd_to_dd_mm_yyyy_ToDateFormate(String value) {
        try {
            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
            Date date = formatter1.parse(value);
            SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yyyy");
            return newFormat.format(date);//yyyy-MM-dd'T'HH:mm:ss
        } catch (Exception e) {
            return null;
        }
    }


    public static String getDateRetrunIntoYYMMDD(String value) {
        try {
            SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");
            Date date = formatter1.parse(value);
            SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");
            return newFormat.format(date);
        } catch (Exception e) {
            return value;
        }
    }


    public static String getDate_Time2(String value) {
        try {
            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = formatter1.parse(value);
            SimpleDateFormat newFormat = new SimpleDateFormat("dd/MM/yyyy");
            return newFormat.format(date);
        } catch (Exception e) {
            return value.substring(0, 10);
        }
    }


    public static String getDateFormate(String date_valueformat,String date_value,String return_format) {
        try {
            SimpleDateFormat formatter1 = new SimpleDateFormat(date_valueformat);
            Date date = formatter1.parse(date_value);
            SimpleDateFormat newFormat = new SimpleDateFormat(return_format);
            String aa= newFormat.format(date);
            return aa;
        } catch (Exception e) {
            return (date_value != null && date_value.length() >= 10) ? date_value.substring(0, 10) : (date_value==null?"":date_value);
        }
    }

    public static String validateJavaDate(String strDate)
    {
        if (!strDate.trim().equals("")){
            SimpleDateFormat sdfrmt = new SimpleDateFormat("yyyy-MM-dd");
            sdfrmt.setLenient(false);
                String[] value= strDate.split("-");
                String year=value[0];
                String mnth=value[1];
                String dd=value[2];
                return  year+"-"+mnth+"-"+dd;
        }
        else {
            String[] value= strDate.split("-");
            String dt=value[0];
            String mnth=value[1];
            String year=value[2];
            return  mnth+"-"+dt+"-"+year;

        }
    }
}