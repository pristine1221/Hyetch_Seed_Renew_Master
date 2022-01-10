package com.example.pristineseed.ui.adapter.order_book;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import com.example.pristineseed.model.BookingOrder.BookingApprovalModel;
import com.example.pristineseed.model.BookingOrder.OrderBookingModel;
import com.example.pristineseed.ui.adapter.header_line.PldownApproveAdapter;

import java.util.List;

public class BookingApprovalAdapter extends BaseAdapter {
    public Activity activity;
    public List<BookingApprovalModel> bookingOrderList;

    public OnListApproveRejectClickListner onListApproveRejectClickListner;

    public interface OnListApproveRejectClickListner {
        void onStatus(String flag, int pos);
    }

    public BookingApprovalAdapter(Activity activity,List<BookingApprovalModel> bookingOrderList){
        this.activity = activity;
        this.bookingOrderList = bookingOrderList;
    }

    @Override
    public int getCount() {
        return bookingOrderList.size();
    }

    @Override
    public Object getItem(int position) {
        return bookingOrderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"ViewHolder","InflateParams"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = activity.getLayoutInflater();
        convertView = inflater.inflate(R.layout.order_booking_approval_list,null);
        FrameLayout circuler_text_section_farmer = convertView.findViewById(R.id.circuler_text_land_selection);
        TextView tv_character_ofImageView_farmer_line = convertView.findViewById(R.id.tv_character_ofImageView_land_selection);
        TextView tv_order_book_no = convertView.findViewById(R.id.tv_order_book_no);
        TextView tv_season = convertView.findViewById(R.id.tv_season);
        TextView tv_distbuter_name = convertView.findViewById(R.id.tv_distbuter_name);
        TextView tv_crop = convertView.findViewById(R.id.tv_crop);
        TextView tv_variety_name=convertView.findViewById(R.id.tv_variety_name);
        TextView dist_date_time_land_selection=convertView.findViewById(R.id.dist_date_time_land_selection);
        TextView tv_order_booking_status=convertView.findViewById(R.id.booking_status);
        LinearLayout status_layout=convertView.findViewById(R.id.status_layout);

        //todo set the data into list
        tv_order_book_no.setText(bookingOrderList.get(position).booking_no);

        tv_season.setText("Season: " + bookingOrderList.get(position).season_code);

        tv_distbuter_name.setText("Distributor : "+bookingOrderList.get(position).distributor_code+"("+bookingOrderList.get(position).distributor_name+")");
        tv_crop.setText("Crop : "+bookingOrderList.get(position).crop_code);

        tv_character_ofImageView_farmer_line.setText(String.valueOf("O"));

        tv_variety_name.setText("Variety : "+bookingOrderList.get(position).variety_name +", QTY : "+bookingOrderList.get(position).booking_qty);

        dist_date_time_land_selection.setText(bookingOrderList.get(position).created_on);


        switch (bookingOrderList.get(position).status){
            case "Pending":
                tv_order_booking_status.setText(bookingOrderList.get(position).status);
                status_layout.setBackgroundResource(R.drawable.gray_backgraund);
                tv_order_booking_status.setTextColor(activity.getResources().getColor(R.color.pending_color));
                break;
            case "First Approve":
                tv_order_booking_status.setText(bookingOrderList.get(position).status);
                status_layout.setBackgroundResource(R.drawable.gray_backgraund);
                tv_order_booking_status.setTextColor(activity.getResources().getColor(R.color.colorPrimaryDark));
                break;
            case "Final Approve":
                tv_order_booking_status.setText(bookingOrderList.get(position).status);
                status_layout.setBackgroundResource(R.drawable.gray_backgraund);
                tv_order_booking_status.setTextColor(activity.getResources().getColor(R.color.colorPrimaryDark));
                break;
            case "Reject":
                tv_order_booking_status.setText(bookingOrderList.get(position).status);
                status_layout.setBackgroundResource(R.drawable.gray_backgraund);
                tv_order_booking_status.setTextColor(activity.getResources().getColor(R.color.my_app_error_color));
                break;

        }

        status_layout.setOnClickListener(v -> {
            onListApproveRejectClickListner.onStatus("ApproveRejected",position);

        });

        Drawable unwrappedDrawable = AppCompatResources.getDrawable(activity, R.drawable.circle_yellow_icon);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, StaticMethods.getRandomColor());
        circuler_text_section_farmer.setBackground(unwrappedDrawable);

        return convertView;
    }

    public void setOnListClick(OnListApproveRejectClickListner onListApproveRejectClickListner) {
        this.onListApproveRejectClickListner = onListApproveRejectClickListner;

    }
}
