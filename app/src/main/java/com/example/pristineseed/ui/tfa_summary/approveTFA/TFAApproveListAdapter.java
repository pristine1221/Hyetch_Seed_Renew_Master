package com.example.pristineseed.ui.tfa_summary.approveTFA;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pristineseed.R;
import com.example.pristineseed.model.tfa.TFAHeaderModel;
import com.google.android.material.chip.Chip;

import java.util.List;

public class TFAApproveListAdapter extends BaseAdapter {
    List<TFAHeaderModel> listdata;
    Activity activity;

    public interface OnItemClickListener {
        void onItemClick(TFAHeaderModel selectedeventData, int position, String flag);
    }

    private final OnItemClickListener listener;

    public TFAApproveListAdapter(Activity activity, List<TFAHeaderModel> listdata, OnItemClickListener listener) {
        super();
        this.listdata = listdata;
        this.activity = activity;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listdata.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return listdata.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        convertView = inflater.inflate(R.layout.view_tfa_listview, null);
        TextView event_code = convertView.findViewById(R.id.event_code);
        TextView tv_name = convertView.findViewById(R.id.tv_name);
        TextView tv_doj = convertView.findViewById(R.id.tv_doj);
        TextView tv_dod = convertView.findViewById(R.id.tv_dod);
        TextView tv_salary = convertView.findViewById(R.id.tv_salary);
        TextView tv_contact_no = convertView.findViewById(R.id.tv_contact_no);
        TextView state_name = convertView.findViewById(R.id.state_name);
        TextView district_name = convertView.findViewById(R.id.district_name);
        TextView tv_place = convertView.findViewById(R.id.tv_place);
        TextView event_status = convertView.findViewById(R.id.event_status);


        Chip approve_order = convertView.findViewById(R.id.approve_order);
        Chip reject_order = convertView.findViewById(R.id.reject_order);
        approve_order.setVisibility(View.VISIBLE);
        reject_order.setVisibility(View.VISIBLE);

        event_code.setText(listdata.get(position).tfa_code);
        tv_name.setText(listdata.get(position).name);
        tv_doj.setText(listdata.get(position).date_of_joining);
        tv_dod.setText(listdata.get(position).date_of_discontinue);
        tv_salary.setText(listdata.get(position).month_salary);
        tv_contact_no.setText(listdata.get(position).mobile_no);
        state_name.setText(listdata.get(position).state);
        district_name.setText(listdata.get(position).district);
        tv_place.setText(listdata.get(position).place);

        if(listdata.get(position).status.equalsIgnoreCase("Pending")){
            event_status.setText(listdata.get(position).status);
            event_status.setTextColor(activity.getResources().getColor(R.color.pending_color));
        }
        else if(listdata.get(position).status.equalsIgnoreCase("First Approve")){
            event_status.setText(listdata.get(position).status);
            event_status.setTextColor(activity.getResources().getColor(R.color.colorPrimaryDark));

        }
        else if(listdata.get(position).status.equalsIgnoreCase("Second Approve")){
            event_status.setText(listdata.get(position).status);
            event_status.setTextColor(activity.getResources().getColor(R.color.colorPrimaryDark));
        }

        else if(listdata.get(position).status.equalsIgnoreCase("Reject")){
            event_status.setText(listdata.get(position).status);
            event_status.setTextColor(activity.getResources().getColor(R.color.my_app_error_color));
        }


        TFAHeaderModel selected = listdata.get(position);
        approve_order.setOnClickListener(view -> {
        listener.onItemClick(selected, position, "approve");
        });

        reject_order.setOnClickListener(view -> {
            listener.onItemClick(selected, position, "reject");

        });
        convertView.setOnClickListener(view -> {
            listener.onItemClick(listdata.get(position), position, "view");
        });

        return convertView;
    }

}
