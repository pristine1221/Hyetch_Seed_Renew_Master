package com.example.pristineseed.ui.employee;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pristineseed.R;

public class EmployeeAttendanceFragmentDummy extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    RadioButton rb_head_quarter, rb_ex_station, rb_out_station, rb_in_transit, rb_other;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_employee_attendance, container, false);
    }

    LinearLayout ll_ex_station, ll_out_station, ll_in_transit, ll_other, init_layout_Section, stopwatch_timer;
    Button bt_submitattandance;
    RadioGroup rg_work_type;
    TextView tv_timere_textview;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rg_work_type = view.findViewById(R.id.rg_work_type);
        rb_head_quarter = view.findViewById(R.id.rb_head_quarter);
        rb_ex_station = view.findViewById(R.id.rb_ex_station);
        rb_out_station = view.findViewById(R.id.rb_out_station);
        rb_in_transit = view.findViewById(R.id.rb_in_transit);
        rb_other = view.findViewById(R.id.rb_other);

        ll_ex_station = view.findViewById(R.id.ll_ex_station);
        ll_out_station = view.findViewById(R.id.ll_out_station);
        ll_in_transit = view.findViewById(R.id.ll_in_transit);
        ll_other = view.findViewById(R.id.ll_other);
        bt_submitattandance = view.findViewById(R.id.bt_submitattandance);
        init_layout_Section = view.findViewById(R.id.init_layout_Section);
        stopwatch_timer = view.findViewById(R.id.stopwatch_timer);
        tv_timere_textview = view.findViewById(R.id.tv_timere_textview);
        // This overrides the radiogroup onCheckListener
        rg_work_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (rb_ex_station.isChecked()) {
                    ll_ex_station.setVisibility(View.VISIBLE);
                } else {
                    ll_ex_station.setVisibility(View.GONE);
                }

                if (rb_out_station.isChecked()) {
                    ll_out_station.setVisibility(View.VISIBLE);
                } else {
                    ll_out_station.setVisibility(View.GONE);
                }

                if (rb_in_transit.isChecked()) {
                    ll_in_transit.setVisibility(View.VISIBLE);
                } else {
                    ll_in_transit.setVisibility(View.GONE);
                }

                if (rb_other.isChecked()) {
                    ll_other.setVisibility(View.VISIBLE);
                } else {
                    ll_other.setVisibility(View.GONE);
                }
            }
        });
        bt_submitattandance.setOnClickListener(v -> {
            if (bt_submitattandance.getText().toString().equalsIgnoreCase("Start Work")) {
                init_layout_Section.setVisibility(View.GONE);
                bt_submitattandance.setText("Stop Work");
                bt_submitattandance.setBackgroundColor(Color.RED);
                stopwatch_timer.setVisibility(View.VISIBLE);
                handler.postDelayed(timerThread, 1000);
            } else {
                init_layout_Section.setVisibility(View.VISIBLE);
                bt_submitattandance.setText("Start Work");
                bt_submitattandance.setBackgroundColor(Color.parseColor("#009D48"));
                stopwatch_timer.setVisibility(View.GONE);
                handler.removeCallbacks(timerThread);
                houres = 0; minute = 0; sec = 0;
                tv_timere_textview.setText(intToString(houres,2) + ":" + intToString(minute,2) + ":" + intToString(sec,2));
            }
        });
    }

    int houres = 0, minute = 0, sec = 0;
    Handler handler = new Handler(Looper.myLooper());
    Runnable timerThread = new Runnable() {
        @Override
        public void run() {
            sec++;
            if (sec > 60) {
                sec = 0;
                minute++;
            }
            if (minute > 60) {
                minute = 0;
                houres++;
            }
            tv_timere_textview.setText(intToString(houres,2) + ":" + intToString(minute,2) + ":" + intToString(sec,2));
            handler.postDelayed(this, 1000);
        }
    };
    public static String intToString(int num, int digits) {
        String output = Integer.toString(num);
        while (output.length() < digits) output = "0" + output;
        return output;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(timerThread);
    }
}