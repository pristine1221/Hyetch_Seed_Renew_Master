package com.example.pristineseed.ui.adapter.header_line;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.example.pristineseed.R;
import com.example.pristineseed.global.StaticMethods;
import com.example.pristineseed.model.Plough_down_List.PloghDownListModel;
import com.example.pristineseed.model.home.OrderList;
import com.google.android.gms.maps.model.Circle;

import java.util.List;

public class PloughDownListAdapter extends BaseAdapter {
    List<PloghDownListModel> listdata;
    Activity activity;

    public PloughDownListAdapter(Activity activity, List<PloghDownListModel> listdata) {
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        convertView = inflater.inflate(R.layout.pld_list_layout, null);
        TextView tv_character_ofImageView = convertView.findViewById(R.id.tv_character_ofImageView);
        FrameLayout circuler_text_section = convertView.findViewById(R.id.circuler_text_section);
        ImageView image_display = convertView.findViewById(R.id.image_display);


        TextView tv_lot_no = convertView.findViewById(R.id.tv_lot_no);
        TextView tv_status = convertView.findViewById(R.id.tv_status);
        TextView tv_reason = convertView.findViewById(R.id.tv_reason);
        TextView tv_date_time = convertView.findViewById(R.id.tv_date_time);

        tv_lot_no.setText(listdata.get(position).lot_no + "," + listdata.get(position).pld_code);
        if (listdata.get(position).reason != null && !listdata.get(position).reason.equals("")) {
            tv_reason.setText(listdata.get(position).reason);
        }
        tv_date_time.setText(listdata.get(position).date);

        if (listdata.get(position).image != null && !listdata.get(position).image.equalsIgnoreCase("")){
            tv_character_ofImageView.setVisibility(View.GONE);
            image_display.setVisibility(View.VISIBLE);
            byte[] decodedString = Base64.decode(listdata.get(position).image, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            Glide.with(activity)
                    .asBitmap()
                    .load(decodedByte)
                    .placeholder(R.drawable.default_img)
                    .transform(new CircleCrop())
                    .into(image_display);//
         }else {
            try {
                tv_character_ofImageView.setVisibility(View.VISIBLE);
                image_display.setVisibility(View.GONE);
                tv_character_ofImageView.setText("PLD");
                Drawable unwrappedDrawable = AppCompatResources.getDrawable(activity, R.drawable.circle_yellow_icon);
                Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
                DrawableCompat.setTint(wrappedDrawable, StaticMethods.getRandomColor());
                circuler_text_section.setBackground(unwrappedDrawable);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        switch (listdata.get(position).status){
            case "Approve":
                tv_status.setText("Approved");
                tv_status.setTextColor(activity.getResources().getColor(R.color.colorPrimaryDark));
                break;

            case "Reject":
                tv_status.setText("Rejected");
                tv_status.setTextColor(activity.getResources().getColor(R.color.my_app_error_color));
                break;
            case "Pending":
                tv_status.setText(listdata.get(position).status);
                tv_status.setTextColor(activity.getResources().getColor(R.color.pending_color));
                break;

            default:
                tv_status.setText(listdata.get(position).status);
                tv_status.setTextColor(activity.getResources().getColor(R.color.dark_gray));
        }
        return convertView;
    }

}
