package com.example.pristineseed.global;

import android.app.Activity;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MaterialDatePicker {

    public Activity activity;
    public static DatePickerDialog datePickerDialog;
    String flag = "";
    String var = "";

    public MaterialDatePicker(Activity activity) {
        this.activity = activity;
        this.flag = flag;
    }

    public void showWeekDaysDialog(TextInputEditText fromDateTextDate, TextInputEditText toDateTextDate, String flag, String from_date) {
        datePickerDialog = new DatePickerDialog();

        if (datePickerDialog == null || !datePickerDialog.isVisible()) {
            Calendar c = Calendar.getInstance();
            String passdate = fromDateTextDate.getText().toString();
            String passeditdate = toDateTextDate.getText().toString();
            int day = 0, month = 0, year = 0;
            if (passdate != null && !passdate.equalsIgnoreCase("")) {
                String[] split = passdate.split("-");
                day = Integer.valueOf(split[0]);
                month = Integer.valueOf(split[1]) - 1;
                year = Integer.valueOf(split[2]);
            } else if (passeditdate != null && !passeditdate.equalsIgnoreCase("")) {
                String[] split = passeditdate.split("-");
                day = Integer.valueOf(split[0]);
                month = Integer.valueOf(split[1]) - 1;
                year = Integer.valueOf(split[2]);
            } else {
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);
            }

            //todo disabled weekends dates in calender
            Calendar min_date_c = Calendar.getInstance();
            datePickerDialog.setMinDate(min_date_c);

            Calendar max_date_c = Calendar.getInstance();
            max_date_c.set(Calendar.YEAR, year + 40);
            datePickerDialog.setMaxDate(max_date_c);

            for (Calendar days = min_date_c;
                 min_date_c.before(max_date_c);
                 min_date_c.add(Calendar.DATE, 1), days = min_date_c) {
                int dayOfWeek = days.get(Calendar.DAY_OF_WEEK);
                if (dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY) {
                    Calendar[] disabledDays = new Calendar[1];
                    disabledDays[0] = days;
                    datePickerDialog.setDisabledDays(disabledDays);
                }
            }

            //todo disabled holidays dates in calender...
            SimpleDateFormat dateFormatVar = new SimpleDateFormat("dd-MM-yyyy");
            String[] holidays = {"01-01-2021", "14-01-2021", "26-01-2021", "11-03-2021", "02-04-2021", "13-04-2021", "21-04-2021",
                    "14-05-2021", "21-07-2021", "10-09-2021", "15-10-2021", "04-11-2021", "01-01-2022", "26-01-2022"};

            java.util.Date date = null;

            for (int i = 0; i < holidays.length; i++) {
                try {
                    date = dateFormatVar.parse(holidays[i]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                c.setTime(date);
                List<Calendar> dates = new ArrayList<>();
                dates.add(c);
                Calendar[] disabledDays1 = dates.toArray(new Calendar[dates.size()]);
                datePickerDialog.setDisabledDays(disabledDays1);
            }

            if(flag.equals("to_date_set_text")){
                DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        fromDateTextDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        toDateTextDate.setText(fromDateTextDate.getText());
                    }
                };
                datePickerDialog.setOnDateSetListener(onDateSetListener);

//           datePickerDialog = DatePickerDialog.newInstance(onDateSetListener, year, month, day);
                datePickerDialog.show(activity.getFragmentManager(), "DatePickerDialog");
                datePickerDialog.setCancelable(true);

            }
            else if(flag.equals("not_set_text")) {

                DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                        fromDateTextDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                };

                try {
                    if (!from_date.equals("")) {
                        Log.e("date1", from_date);

                        Calendar calendar = Calendar.getInstance();
                        int d = 0, m = 0, y = 0;
                        String[] split = from_date.split("-");
                        d = Integer.valueOf(split[0]);
                        m = Integer.valueOf(split[1]) - 1;
                        y = Integer.valueOf(split[2]);

                        calendar.set(Calendar.YEAR, y);
                        calendar.set(Calendar.MONTH, m);
                        calendar.set(Calendar.DAY_OF_MONTH, d+1);

                        datePickerDialog.setMinDate(calendar);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Log.e("date1", from_date);
                }

                datePickerDialog.setOnDateSetListener(onDateSetListener);

//           datePickerDialog = DatePickerDialog.newInstance(onDateSetListener, year, month, day);
                datePickerDialog.show(activity.getFragmentManager(), "DatePickerDialog");
                datePickerDialog.setCancelable(true);
            }

            //todo its create new instance od date picker...

        }
    }

    public static long daysBetween(Calendar startDate, Calendar endDate) {
        long end = endDate.getTimeInMillis();
        long start = startDate.getTimeInMillis();
        long get_date = TimeUnit.MILLISECONDS.toDays(Math.abs(end - start));
        return get_date;
    }

}
