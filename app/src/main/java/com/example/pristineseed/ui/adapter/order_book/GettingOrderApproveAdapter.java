package com.example.pristineseed.ui.adapter.order_book;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;

import com.example.pristineseed.R;
import com.example.pristineseed.global.StaticMethods;
import com.example.pristineseed.model.BookingOrder.MarketingIndentApprovalModel;
import com.example.pristineseed.model.BookingOrder.MarketingIndentModel;
import com.google.android.material.chip.Chip;

import java.util.List;

public class GettingOrderApproveAdapter extends BaseAdapter {

    public Activity activity;
    public List<MarketingIndentApprovalModel> bookinglineList;

    public OnStatusItemClick onStatusItemClick;
    public interface  OnStatusItemClick{
        void onStatusItemClick(int pos);
    }

    public GettingOrderApproveAdapter(Activity activity, List<MarketingIndentApprovalModel> bookinglineList) {
        this.activity = activity;
        this.bookinglineList = bookinglineList;
    }

    @Override
    public int getCount() {
        return bookinglineList.size();
    }

    @Override
    public Object getItem(int position) {
        return bookinglineList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = activity.getLayoutInflater();
        convertView = inflater.inflate(R.layout.getting_order_approval_list_layout, null);
        TextView mkt_no = convertView.findViewById(R.id.mkt_no);
//        TextView tv_cust_no = convertView.findViewById(R.id.tv_cust_no);
//        TextView tv_address = convertView.findViewById(R.id.tv_address);
        TextView tv_cust_type = convertView.findViewById(R.id.tv_cust_type);
        TextView tv_distributor_name = convertView.findViewById(R.id.tv_distributor_name);
        TextView tv_ship_to = convertView.findViewById(R.id.tv_ship_to);
        TextView tv_created_date = convertView.findViewById(R.id.tv_date);
        Chip tv_status = convertView.findViewById(R.id.tv_status);

        //todo set the data into list
        mkt_no.setText(bookinglineList.get(position).marketing_indent_no);

        //todo remove fields 14-04-22
//        tv_cust_no.setText(bookinglineList.get(position).customer_no);
//        tv_address.setText(bookinglineList.get(position).address);
        //  tv_character_ofImageView_farmer_line.setText(String.valueOf("O"));

        tv_cust_type.setText(bookinglineList.get(position).customer_type);

        tv_distributor_name.setText(bookinglineList.get(position).name);

        tv_ship_to.setText(bookinglineList.get(position).ship_to_code);

        tv_created_date.setText(bookinglineList.get(position).date);

        tv_status.setText(bookinglineList.get(position).status);

        tv_status.setOnClickListener(v -> {
            onStatusItemClick.onStatusItemClick(position);
        });

       /* switch (bookinglineList.get(position).status){
            case "Pending":
                tv_status.setTextColor(activity.getResources().getColor(R.color.pending_color));
                tv_status.setText(bookinglineList.get(position).status);
                break;
            case "Reject":
                tv_status.setTextColor(activity.getResources().getColor(R.color.my_app_error_color));
                tv_status.setText(bookinglineList.get(position).status);
                break;
            case "Approve":
                tv_status.setTextColor(activity.getResources().getColor(R.color.colorPrimaryDark));
                tv_status.setText(bookinglineList.get(position).status);
                break;
        }*/

       return convertView;
    }

    public void setOnStautsItemClick(OnStatusItemClick onStatusItemClick ){
        this.onStatusItemClick=onStatusItemClick;
    }

}
