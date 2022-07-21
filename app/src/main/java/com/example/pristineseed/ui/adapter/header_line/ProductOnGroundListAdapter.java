package com.example.pristineseed.ui.adapter.header_line;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;

import com.example.pristineseed.R;
import com.example.pristineseed.global.StaticMethods;
import com.example.pristineseed.model.POG.POGModel;
import com.example.pristineseed.model.home.OrderList;

import java.util.List;

public class ProductOnGroundListAdapter extends BaseAdapter {
    List<POGModel> listdata;
    Activity activity;

    public ProductOnGroundListAdapter(Activity activity, List<POGModel> listdata) {
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
        convertView = inflater.inflate(R.layout.pog_item_listview, null);
        TextView tv_character_ofImageView = convertView.findViewById(R.id.tv_character_ofImageView);
        FrameLayout circuler_text_section=convertView.findViewById(R.id.circuler_text_section);
        TextView tv_zone = convertView.findViewById(R.id.tv_zone);
        TextView tv_emp_name = convertView.findViewById(R.id.tv_emp_name);
        TextView tv_crop = convertView.findViewById(R.id.tv_crop);

        TextView  tv_season=convertView.findViewById(R.id.tv_season);
        TextView tv_remarks=convertView.findViewById(R.id.tv_remarks);
        TextView tv_date_time=convertView.findViewById(R.id.tv_date_time);
        TextView tv_status=convertView.findViewById(R.id.tv_status);


        tv_zone.setText("Zone : "+listdata.get(position).zone+" (Pog : "+listdata.get(position).pog_code+")");
        tv_emp_name.setText("Emp. name : "+listdata.get(position).emp_name);
        tv_crop.setText("Crop : "+listdata.get(position).crop);

        tv_season.setText("Season : "+listdata.get(position).season);
        tv_remarks.setText("Remarks : "+listdata.get(position).remarks);
        tv_date_time.setText(listdata.get(position).date);

        String currentString = listdata.get(position).pog_code;
        String[] separated = currentString.split("-");
        tv_character_ofImageView.setText(separated[1]);

        tv_character_ofImageView.setTextSize(17);
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(activity, R.drawable.circle_yellow_icon);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, StaticMethods.getRandomColor());
        circuler_text_section.setBackground(unwrappedDrawable);

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
