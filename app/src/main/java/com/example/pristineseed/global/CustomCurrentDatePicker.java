package com.example.pristineseed.global;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.widget.DatePicker;

import com.example.pristineseed.R;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;

public class CustomCurrentDatePicker {

    Activity activity;
    private Long min_date=System.currentTimeMillis();

    public static DatePickerDialog datePickerDialog;

    public CustomCurrentDatePicker(Activity activity) {
        this.activity = activity;
    }

    public void showDatePickerDialog(TextInputEditText editTextDate) {
        //todo date formate shoud be 5-12-2013
        if (datePickerDialog == null ||!datePickerDialog.isShowing()) {
            String passdate = editTextDate.getText().toString();
            int day = 0, month = 0, year = 0;
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

            DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year_this, int monthOfYear,
                                      int dayOfMonth) {

                    // TODO Auto-generated method stub
                    editTextDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year_this);
                }
            };

            datePickerDialog = new DatePickerDialog(activity, dateSetListener, year, month, day);
            datePickerDialog.setCancelable(false);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePickerDialog.show();
        }
    }

/*
    public void showWeekDaysDialog(TextInputEditText editTextDate){
        if (datePickerDialog == null ||!datePickerDialog.isShowing()) {
            String passdate = editTextDate.getText().toString();
            int day = 0, month = 0, year = 0;
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

            DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year_this, int monthOfYear,
                                      int dayOfMonth) {

                    // TODO Auto-generated method stub
                    editTextDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year_this);
                }
            };

             ArrayList<Calendar> weekends = new ArrayList<Calendar>();
            Calendar days = Calendar.getInstance();
            for (int i = 0; i < MAX_SELECTABLE_DATE_IN_FUTURE; i++) {
                if (days.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || days.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                    Calendar d = (Calendar) days.clone();
                    weekends.add(d);
                }
                days.add(Calendar.DATE, 1);
            }
            Calendar[] weekendDays = weekends.toArray(new Calendar[weekends.size()]);

            datePickerDialog = new DatePickerDialog(activity, dateSetListener, year, month, day);
            datePickerDialog.setCancelable(false);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePickerDialog.show();

        }
    }
*/



    public void showDatePickerDialog_min(TextInputEditText editTextDate,String date)
    {
        //todo date formate shoud be 5-12-2013
        if (datePickerDialog == null || !datePickerDialog.isShowing()) {
            String passdate = editTextDate.getText().toString();
            int day = 0, month = 0, year = 0;

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

            if (editTextDate.getId() == R.id.et_leave_to_date) {
                Calendar startrangeCalander = Calendar.getInstance();
                try {
                    String[] split = date.split("-");
                    int daystart = Integer.valueOf(split[0]);
                    int monthstart = Integer.valueOf(split[1]) - 1;
                    int yearstart = Integer.valueOf(split[2]);
                    startrangeCalander.set(Calendar.YEAR, yearstart);
                    startrangeCalander.set(Calendar.MONTH, monthstart);
                    startrangeCalander.set(Calendar.DAY_OF_MONTH, daystart);
                    min_date = startrangeCalander.getTimeInMillis();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year_this, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub

                    String monthDay = String.valueOf(dayOfMonth);
                    String monthYear = String.valueOf(monthOfYear + 1);
                    if (monthDay.length() == 1) {
                        monthDay = "0" + monthDay;
                    }
                    if (monthYear.length() == 1) {
                        monthYear = "0" + monthYear;
                    }
                    editTextDate.setText(monthDay + "-" + monthYear + "-" + year_this);
//                    editTextDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year_this);
                }
            };

            datePickerDialog = new DatePickerDialog(activity, dateSetListener, year, month, day);
            datePickerDialog.setCancelable(false);
            datePickerDialog.getDatePicker().setMinDate(min_date - 1000);
            datePickerDialog.show();
        }
    }

}
