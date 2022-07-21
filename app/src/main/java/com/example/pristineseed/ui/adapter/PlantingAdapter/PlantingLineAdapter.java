package com.example.pristineseed.ui.adapter.PlantingAdapter;

import android.annotation.SuppressLint;
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

import com.example.pristineseed.DataBaseRepository.Planting.PlantingLineTable;
import com.example.pristineseed.R;
import com.example.pristineseed.global.StaticMethods;
import com.example.pristineseed.model.PlantingModel.PlantingHeaderModel;

import java.util.List;

public class PlantingLineAdapter extends BaseAdapter {

    public Activity activity;
    public List<PlantingHeaderModel.PlantingLine> listDataLand;

    public PlantingLineAdapter(Activity activity, List<PlantingHeaderModel.PlantingLine> listDataLand) {
        this.activity = activity;
        this.listDataLand = listDataLand;
    }

    @Override
    public int getCount() {
        return listDataLand.size();
    }

    @Override
    public Object getItem(int position) {
        return listDataLand.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = activity.getLayoutInflater();
        convertView = inflater.inflate(R.layout.planting_header_line_detail_layout, null);
        FrameLayout circuler_text_land_selection = convertView.findViewById(R.id.circuler_text_section);
        TextView sowing_area = convertView.findViewById(R.id.sowing_area);
        TextView tv_sowing_date_male = convertView.findViewById(R.id.tv_sowing_date_male);
        TextView tv_sowing_date_female = convertView.findViewById(R.id.tv_sowing_date_female);
        TextView tv_planting_no = convertView.findViewById(R.id.tv_planting_no);
        TextView tv_character_ofImageView=convertView.findViewById(R.id.tv_character_ofImageView);
        //todo set the data into list
        tv_planting_no.setText("Line no. : " + listDataLand.get(position).line_no+"("+listDataLand.get(position).grower_land_owner_name+")");
        sowing_area.setText("Sowing Area(acres) : "+listDataLand.get(position).sowing_area_In_acres);
        tv_sowing_date_male.setText("Sowing Date male : " + listDataLand.get(position).sowing_date_male);
        tv_sowing_date_female.setText("Sowing Date female :" +listDataLand.get(position).sowing_date_female);
        if (listDataLand.get(position).grower_land_owner_name!= null && !listDataLand.get(position).grower_land_owner_name.equalsIgnoreCase("")) {
            tv_character_ofImageView.setText(String.valueOf(listDataLand.get(position).grower_land_owner_name.charAt(0)).toUpperCase());
        }
        else {
            tv_character_ofImageView.setText("P");
        }
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(activity, R.drawable.circle_yellow_icon);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, StaticMethods.getRandomColor());
        circuler_text_land_selection.setBackground(unwrappedDrawable);
        return convertView;
    }

}
