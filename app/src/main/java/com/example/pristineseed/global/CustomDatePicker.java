package com.example.pristineseed.global;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.widget.DatePicker;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CustomDatePicker {
    Activity activity;
    public static DatePickerDialog datePickerDialog;

    public CustomDatePicker(Activity activity) {
        this.activity = activity;
    }

    public void showDatePickerDialog(TextInputEditText editTextDate) {
        //todo date formate shoud be 5-12-2013
        if (datePickerDialog == null ||!datePickerDialog.isShowing()) {
            String passdate = editTextDate.getText().toString();
            int day = 0, month = 0, year = 0;
            try {
                if (passdate != null && !passdate.equalsIgnoreCase("")) {
                    String[] split = passdate.split("-");
                    day = Integer.valueOf(split[0]);
                    month = Integer.valueOf(split[1]) - 1;
                    year = Integer.valueOf(split[2]);
                } else {
                    Calendar c = Calendar.getInstance();
                    year = c.get(Calendar.YEAR);
                    month = c.get(Calendar.MONTH);
                    day = c.get(Calendar.DAY_OF_MONTH);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year_this, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    int month= monthOfYear+1;
                    String f_month = ""+month;
                    String f_day = ""+dayOfMonth;
                    if(month < 10){
                        f_month = "0"+month;
                    }
                    if (dayOfMonth < 10){
                        f_day = "0"+dayOfMonth;
                    }
                    String date= ""+f_day+"-"+f_month+"-"+year_this;
                    editTextDate.setText(date);
                    editTextDate.setSelection(editTextDate.getText().length());

//                    editTextDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year_this);
                }
            };

            datePickerDialog = new DatePickerDialog(activity,
                    dateSetListener, year, month, day);
            datePickerDialog.setCancelable(false);
            datePickerDialog.show();
        }

    }

    public void showDatePickerDialogDash(TextInputEditText editTextDate) {
        //todo date formate shoud be 5-12-2013
        if (datePickerDialog == null ||!datePickerDialog.isShowing()) {
            String passdate = editTextDate.getText().toString();
            int day = 0, month = 0, year = 0;
            try {
                if (passdate != null && !passdate.equalsIgnoreCase("") || month>10) {
                    String[] split = passdate.split("-");
                    year = Integer.valueOf(split[0]);
                    month = Integer.valueOf(split[1]) - 1;
                    day = Integer.valueOf(split[2]);
                } else {
                    Calendar c = Calendar.getInstance();
                    year = c.get(Calendar.YEAR);
                    month = c.get(Calendar.MONTH);
                    day = c.get(Calendar.DAY_OF_MONTH);
                }

            }
            catch (Exception e){
                e.printStackTrace();
            }

            DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year_this, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    editTextDate.setText(year_this + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                   // editTextDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year_this);
                }
            };
            datePickerDialog = new DatePickerDialog(activity,
                    dateSetListener, year, month, day);
            datePickerDialog.setCancelable(false);
            datePickerDialog.show();
        }

    }

    public void showDatePickerDialogByRange(TextInputEditText editTextDate, String mindate, String maxDate) {
        Calendar c = Calendar.getInstance();

        //todo date formate shoud be 5-12-2013
        if (datePickerDialog == null || !datePickerDialog.isShowing()) {
            String passdate = editTextDate.getText().toString();
            int day = 0, month = 0, year = 0;
            if (passdate != null && !passdate.equalsIgnoreCase("")) {
                String[] split = passdate.split("-");
                day = Integer.valueOf(split[0]);
                month = Integer.valueOf(split[1]) - 1;
                year = Integer.valueOf(split[2]);
                c.set(Calendar.YEAR,year);
                c.set(Calendar.MONTH,month);
                c.set(Calendar.DAY_OF_MONTH,day);
            } else {
                //Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);
            }

            Calendar startrangeCalander = Calendar.getInstance();
            Calendar endrangeCalander = Calendar.getInstance();
            try{
                String[] split = mindate.split("-");
                int daystart = Integer.valueOf(split[0]);
                int  monthstart = Integer.valueOf(split[1]) - 1;
                int yearstart = Integer.valueOf(split[2]);
                startrangeCalander.set(Calendar.YEAR,yearstart);
                startrangeCalander.set(Calendar.MONTH,monthstart);
                startrangeCalander.set(Calendar.DAY_OF_MONTH,daystart);

                String[] endsplit = maxDate.split("-");
                int dayend = Integer.valueOf(endsplit[0]);
                int  monthend = Integer.valueOf(endsplit[1]) - 1;
                int yearend = Integer.valueOf(endsplit[2]);
                endrangeCalander.set(Calendar.YEAR,yearend);
                endrangeCalander.set(Calendar.MONTH,monthend);
                endrangeCalander.set(Calendar.DAY_OF_MONTH,dayend);
            }catch (Exception e){
                e.printStackTrace();
            }

            DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year_this, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    editTextDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year_this);
                }
            };

            datePickerDialog = new DatePickerDialog(activity,
                    dateSetListener, year, month, day);
            datePickerDialog.getDatePicker().setMinDate(startrangeCalander.getTimeInMillis());
            datePickerDialog.getDatePicker().setMaxDate(endrangeCalander.getTimeInMillis());
            datePickerDialog.setCancelable(false);
            datePickerDialog.show();
        }
    }
}
