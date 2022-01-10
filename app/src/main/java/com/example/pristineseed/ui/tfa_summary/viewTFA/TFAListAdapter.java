package com.example.pristineseed.ui.tfa_summary.viewTFA;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pristineseed.R;
import com.example.pristineseed.model.tfa.TFAHeaderModel;
import com.example.pristineseed.ui.tfa_summary.approveTFA.ApproveTFADetailFragment;

import java.util.List;

    public class TFAListAdapter extends BaseAdapter {
    List<TFAHeaderModel> listdata;
    Activity activity;

    public TFAListAdapter(Activity activity, List<TFAHeaderModel> listdata) {
        super();
        this.listdata = listdata;
        this.activity = activity;
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
        convertView = inflater.inflate(R.layout.view_tfa_listview_only_tfa, null);
        TextView tfa_code = convertView.findViewById(R.id.tfa_code);
        TextView tfa_name = convertView.findViewById(R.id.tfa_name);
        TextView tv_doj = convertView.findViewById(R.id.tv_doj);
        TextView tv_dod = convertView.findViewById(R.id.tv_dod);
        TextView tv_salary = convertView.findViewById(R.id.tv_salary);
        TextView tv_contact_no = convertView.findViewById(R.id.tv_contact_no);
        TextView district_name = convertView.findViewById(R.id.district_name);
        TextView state_name = convertView.findViewById(R.id.state_name);
        TextView tv_status=convertView.findViewById(R.id.tv_status);

        TextView tv_place = convertView.findViewById(R.id.tv_place);

        tfa_code.setText(listdata.get(position).tfa_code);
        tfa_name.setText(listdata.get(position).name);
        tv_doj.setText(listdata.get(position).date_of_joining);
        tv_dod.setText(listdata.get(position).date_of_discontinue);
        tv_salary.setText(listdata.get(position).month_salary);
        tv_contact_no.setText(listdata.get(position).mobile_no);
        state_name.setText(listdata.get(position).state);
        district_name.setText(listdata.get(position).district);
        tv_place.setText(listdata.get(position).place);

         if(listdata.get(position).status.equalsIgnoreCase("Reject")){
            tv_status.setText(listdata.get(position).status);
            tv_status.setTextColor(activity.getResources().getColor(R.color.my_app_error_color));
        }
        else if(listdata.get(position).status.equalsIgnoreCase("Pending")){
            tv_status.setText(listdata.get(position).status);
            tv_status.setTextColor(activity.getResources().getColor(R.color.pending_color));
        }
        else if(listdata.get(position).status.equalsIgnoreCase("First Approve")){
            tv_status.setText(listdata.get(position).status);
            tv_status.setTextColor(activity.getResources().getColor(R.color.colorPrimaryDark));
        }
        else if(listdata.get(position).status.equalsIgnoreCase("Insert Expense")){
            tv_status.setText(listdata.get(position).status);
            tv_status.setTextColor(activity.getResources().getColor(R.color.colorPrimaryDark));
        }
        else if(listdata.get(position).status.equalsIgnoreCase("Second Approve")){
             tv_status.setText(listdata.get(position).status);
             tv_status.setTextColor(activity.getResources().getColor(R.color.colorPrimaryDark));
         }

        return convertView;

    }

}
