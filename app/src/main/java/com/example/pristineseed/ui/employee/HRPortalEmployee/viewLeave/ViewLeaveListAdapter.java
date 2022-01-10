package com.example.pristineseed.ui.employee.HRPortalEmployee.viewLeave;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pristineseed.R;
import com.example.pristineseed.model.travel.travel_view_header.TravelHeaderModel;

import java.util.List;

public class ViewLeaveListAdapter extends BaseAdapter {
    List<TravelHeaderModel> listdata;
    Activity activity;
    public interface OnItemClickListener {
        void onItemClick(TravelHeaderModel selectedeventData, int position, String flag);
    }
    private final OnItemClickListener listener;
    public ViewLeaveListAdapter(Activity activity, List<TravelHeaderModel> listdata, OnItemClickListener listener) {
        super();
        this.listdata = listdata;
        this.activity = activity;
        this.listener=listener;
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
        convertView = inflater.inflate(R.layout.aa_view_leave_listview, null);
        TextView travel_code = convertView.findViewById(R.id.travel_code);
        TextView tv_start_date = convertView.findViewById(R.id.tv_start_date);
        TextView tv_end_date = convertView.findViewById(R.id.tv_end_date);
        TextView tv_travel_reson = convertView.findViewById(R.id.tv_travel_reson);
        TextView tv_expense_budget = convertView.findViewById(R.id.tv_expense_budget);
        TextView tv_travel_status = convertView.findViewById(R.id.tv_travel_status);

        ImageView approve_order = convertView.findViewById(R.id.approve_order);
        ImageView reject_order = convertView.findViewById(R.id.reject_order);
        approve_order.setVisibility(View.VISIBLE);
        reject_order.setVisibility(View.VISIBLE);

        travel_code.setText(listdata.get(position).travelcode);
        tv_start_date.setText(listdata.get(position).start_date);
        tv_end_date.setText(listdata.get(position).end_date);
        tv_travel_reson.setText(listdata.get(position).travel_reson);
        tv_expense_budget.setText(listdata.get(position).expense_budget);
        tv_travel_status.setText(listdata.get(position).status);
        tv_travel_status.setVisibility(View.VISIBLE);
        approve_order.setOnClickListener(view -> {
            listener.onItemClick(listdata.get(position), position, "approve");
        });
        reject_order.setVisibility(View.GONE);
        reject_order.setOnClickListener(view -> {
            listener.onItemClick(listdata.get(position), position, "reject");
        });
        return convertView;

    }

}
