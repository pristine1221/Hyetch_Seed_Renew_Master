package com.example.pristineseed.ui.adapter.land_selection;

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

import com.example.pristineseed.R;
import com.example.pristineseed.global.StaticMethods;
import com.example.pristineseed.model.landselection.LandSelectionModel;

import java.util.List;

public class LandSelectionListAdapter extends BaseAdapter {

    public Activity activity;
    public List<LandSelectionModel> listDataLand;

    public LandSelectionListAdapter(Activity activity,List<LandSelectionModel> listDataLand){
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
        return 0;
    }

    @SuppressLint({"ViewHolder","InflateParams"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = activity.getLayoutInflater();
        convertView = inflater.inflate(R.layout.land_selction_get_list_layout,null);
        FrameLayout circuler_text_land_selection = convertView.findViewById(R.id.circuler_text_land_selection);
        TextView tv_character_ofImageView_land_selection = convertView.findViewById(R.id.tv_character_ofImageView_land_selection);
        TextView land_code_land_selection = convertView.findViewById(R.id.land_code_land_selection);
        TextView block_name_land_selection = convertView.findViewById(R.id.block_name_land_selection);
        TextView remark_land_selection = convertView.findViewById(R.id.remark_land_selection);
        TextView dist_date_time_land_selection = convertView.findViewById(R.id.dist_date_time_land_selection);
        TextView total_rows_land_selection = convertView.findViewById(R.id.total_rows_land_selection);

        //todo set the data into list
        land_code_land_selection.setText(listDataLand.get(position).land_selection_code);
        block_name_land_selection.setText("Block name: "+listDataLand.get(position).block_name);
        remark_land_selection.setText("Remark : "+listDataLand.get(position).remark);
        dist_date_time_land_selection.setText(listDataLand.get(position).created_on);
     //  total_rows_land_selection.setText(listDataLand.get(position).getTotal_rows());

        if(listDataLand.get(position).block_name!=null){
            tv_character_ofImageView_land_selection.setText(String.valueOf(listDataLand.get(position).block_name).toUpperCase());
//            tv_character_ofImageView_land_selection.setText(String.valueOf(listDataLand.get(position).block_name.CharAt(0)).toUpperCase());
        }

        Drawable unwrappedDrawable = AppCompatResources.getDrawable(activity, R.drawable.circle_yellow_icon);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, StaticMethods.getRandomColor());
        circuler_text_land_selection.setBackground(unwrappedDrawable);
        return convertView;
    }
    
}
