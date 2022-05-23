package com.example.pristineseed.global;

import android.app.Activity;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.valdesekamdem.library.mdtoast.MDToast;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
                    "14-05-2021", "21-07-2021", "10-09-2021", "15-10-2021", "04-11-2021", "01-01-2022", "14-01-2022", "26-01-2022",
                    "02-03-2022", "18-03-2022", "15-04-2022", "02-05-2022", "11-08-2022", "15-08-2022", "19-08-2022", "31-08-2022",
                    "05-10-2022", "24-10-2022"};

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

            //todo set to_date on single leave..
            if (flag.equals("to_date_set_text")) {
                DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        fromDateTextDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        toDateTextDate.setText(fromDateTextDate.getText());
                    }
                };
                datePickerDialog.setOnDateSetListener(onDateSetListener);
                datePickerDialog.show(activity.getFragmentManager(), "DatePickerDialog");
                datePickerDialog.setCancelable(true);

            }

            //todo not set to_date on multi leave...
            else if (flag.equals("not_set_text")) {

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
                        calendar.set(Calendar.DAY_OF_MONTH, d + 1);

                        datePickerDialog.setMinDate(calendar);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("date1", from_date);
                }

                datePickerDialog.setOnDateSetListener(onDateSetListener);
                datePickerDialog.show(activity.getFragmentManager(), "DatePickerDialog");
                datePickerDialog.setCancelable(true);
            }

            //todo its create new instance od date picker...

        }
    }

    //todo disable all dates after today date for report statement....
    public void disableDateAfterToday(TextInputEditText startDateEditText) {
        datePickerDialog = new DatePickerDialog();
        if (datePickerDialog == null || !datePickerDialog.isVisible()) {
            Calendar today = Calendar.getInstance();

            //todo disable all dates after today date....
            Calendar afterTodayDate = (Calendar) today.clone();
            afterTodayDate.add(Calendar.DATE, 0);
            datePickerDialog.setMaxDate(afterTodayDate);

            //todo set date into edit input text....
            DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                    int month = monthOfYear + 1;
                    String f_month = "" + month;
                    String f_day = "" + dayOfMonth;
                    if (month < 10) {
                        f_month = "0" + month;
                    }
                    if (dayOfMonth < 10) {
                        f_day = "0" + dayOfMonth;
                    }
                    String date = "" + f_day + "-" + f_month + "-" + year;

                    startDateEditText.setText(date);
                    startDateEditText.setSelection(startDateEditText.getText().length());
//                    startDateEditText.setText(  monthOfYear+1  +"-" +dayOfMonth+"-" + year);
                }
            };

            //todo these lines show date calender...
            datePickerDialog.setOnDateSetListener(onDateSetListener);
            datePickerDialog.show(activity.getFragmentManager(), "DatePickerDialog");
            datePickerDialog.setCancelable(true);
        }
    }


    //todo disable calender date according to start date selected....
    public void disableDatesAccToStartDate(TextInputEditText endDateTextEdit, String start_date) {
        //todo convert mm-dd-yy to dd-mm-yyyy for next calender date...
//        String startDate = DateTimeUtilsCustome.get_mm_dd_yy_to_dd_mm_yyyy_ToDateFormate(start_date);
        datePickerDialog = new DatePickerDialog();
        if (datePickerDialog == null || !datePickerDialog.isVisible()) {
            Calendar today = Calendar.getInstance();

            //todo disable all dates after today date....
            Calendar afterTodayDate = (Calendar) today.clone();
            afterTodayDate.add(Calendar.DATE, 0);
            datePickerDialog.setMaxDate(afterTodayDate);

            //todo set date into edit input text....
            DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                    int month = monthOfYear + 1;
                    String f_month = "" + month;
                    String f_day = "" + dayOfMonth;
                    if (month < 10) {
                        f_month = "0" + month;
                    }
                    if (dayOfMonth < 10) {
                        f_day = "0" + dayOfMonth;
                    }
                    String date = "" + f_day + "-" + f_month + "-" + year;
                    endDateTextEdit.setText(date);
                    endDateTextEdit.setSelection(endDateTextEdit.getText().length());
//                    endDateTextEdit.setText(dayOfMonth +"-" +monthOfYear+1  +"-" + year);
                }
            };

            //todo disable all dates before selected specific date....
            try {
                if (!start_date.equals("")) {
                    Calendar c = Calendar.getInstance();
                    int d = 0, m = 0, y = 0;
                    String[] split = start_date.split("-");
                    d = Integer.valueOf(split[0]);
                    m = Integer.valueOf(split[1]) - 1;
                    y = Integer.valueOf(split[2]);

                    c.set(Calendar.YEAR, y);
                    c.set(Calendar.MONTH, m);
                    c.set(Calendar.DAY_OF_MONTH, d);
                    datePickerDialog.setMinDate(c);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            //todo these lines show date calender...
            datePickerDialog.setOnDateSetListener(onDateSetListener);
            datePickerDialog.show(activity.getFragmentManager(), "DatePickerDialog");
            datePickerDialog.setCancelable(true);
        }
    }


    //todo after selected date after and before disable all dates....
    public void disableAllDatesUnableOneMonthDates(TextInputEditText startDateEditText, TextInputEditText endDateEditText, String from_date) {
        datePickerDialog = new DatePickerDialog();
        if (datePickerDialog == null || !datePickerDialog.isVisible()) {

            Calendar c = Calendar.getInstance();
            String passdate = startDateEditText.getText().toString();
            String passeditdate = endDateEditText.getText().toString();
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

            //todo set date into edit input text....
            DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                    int month = monthOfYear + 1;
                    String f_month = "" + month;
                    String f_day = "" + dayOfMonth;
                    if (month < 10) {
                        f_month = "0" + month;
                    }
                    if (dayOfMonth < 10) {
                        f_day = "0" + dayOfMonth;
                    }
                    String date = "" + f_day + "-" + f_month + "-" + year;

                    //todo comparing selected date with current date and throwing error...
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    Date dateSource = null;
                    Calendar cal = Calendar.getInstance();
                    Date sysDate = cal.getTime(); //todo getting current date;
                    try {
                        dateSource = sdf.parse(date);
                        if (dateSource.compareTo(sysDate) > 0) {
                            MDToast.makeText(activity, "Can Not Select Future Date !", Toast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();
                            endDateEditText.setText("");
                        }else if (dateSource.compareTo(sysDate) < 0) {
                            endDateEditText.setText(date);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            };


            //todo disable all dates before today date...
            try {
                if (!from_date.equals("")) {

                    int d = 0, m = 0, y = 0;
                    String[] split = from_date.split("-");
                    d = Integer.valueOf(split[0]);
                    m = Integer.valueOf(split[1]) - 1;
                    y = Integer.valueOf(split[2]);

                    c.set(Calendar.YEAR, y);
                    c.set(Calendar.MONTH, m);
                    c.set(Calendar.DAY_OF_MONTH, d);

                    datePickerDialog.setMinDate(c);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            //todo disable all dates after one month...
            try {
                if (!from_date.equals("")) {

                    int d = 0, m = 0, y = 0;
                    String[] split = from_date.split("-");
                    d = Integer.valueOf(split[0]);
                    m = Integer.valueOf(split[1]) - 1;
                    y = Integer.valueOf(split[2]);

                    c.set(Calendar.YEAR, y);
                    c.set(Calendar.MONTH, m + 1);
                    c.set(Calendar.DAY_OF_MONTH, d - 1);

                    datePickerDialog.setMaxDate(c);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            //todo these lines show date calender...
            datePickerDialog.setOnDateSetListener(onDateSetListener);
            datePickerDialog.show(activity.getFragmentManager(), "DatePickerDialog");
            datePickerDialog.setCancelable(true);
        }
    }

}
