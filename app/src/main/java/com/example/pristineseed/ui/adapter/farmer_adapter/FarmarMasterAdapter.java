package com.example.pristineseed.ui.adapter.farmer_adapter;

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
import com.example.pristineseed.global.DateTimeUtilsCustome;
import com.example.pristineseed.global.StaticMethods;
import com.example.pristineseed.model.distributor_master.DistributorListModel;

import java.util.List;

public class FarmarMasterAdapter extends BaseAdapter {
    public Activity activity;
    public List<DistributorListModel> listDataFarmer;

    public FarmarMasterAdapter(Activity activity,List<DistributorListModel> listDataFarmer){
        this.activity = activity;
        this.listDataFarmer = listDataFarmer;
    }

    @Override
    public int getCount() {
        return listDataFarmer.size();
    }

    @Override
    public Object getItem(int position) {
        return listDataFarmer.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint({"ViewHolder","InflateParams"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = activity.getLayoutInflater();
        convertView = inflater.inflate(R.layout.farmer_list_layout,null);
        FrameLayout circuler_text_section_farmer = convertView.findViewById(R.id.circuler_text_section_farmer);
        TextView tv_character_ofImageView_farmer_line = convertView.findViewById(R.id.tv_character_ofImageView_farmer_line);
        TextView disbutor_code_farmer_line = convertView.findViewById(R.id.disbutor_code_farmer_line);
        TextView distibutor_name_farmer_line = convertView.findViewById(R.id.distibutor_name_farmer_line);
        TextView distbutor_remark_farmer_line = convertView.findViewById(R.id.distbutor_remark_farmer_line);
        TextView dist_date_time_farmer_line = convertView.findViewById(R.id.dist_date_time_farmer_line);

        //todo set the data into list
        disbutor_code_farmer_line.setText(listDataFarmer.get(position).distributor_code);
        if(listDataFarmer.get(position).distributor_code!=null) {
            distibutor_name_farmer_line.setText("Cust. name : " + listDataFarmer.get(position).distributor_name);
        }
        distbutor_remark_farmer_line.setText(listDataFarmer.get(position).remarks);
        if(listDataFarmer.get(position).created_on!=null && !listDataFarmer.get(position).created_on.equalsIgnoreCase("")) {
            dist_date_time_farmer_line.setText(DateTimeUtilsCustome.getDateDDMMYYY(listDataFarmer.get(position).created_on));
        }

      if(listDataFarmer.get(position).distributor_name!=null){
          tv_character_ofImageView_farmer_line.setText(String.valueOf(listDataFarmer.get(position).distributor_name.charAt(0)).toUpperCase());
      }
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(activity, R.drawable.circle_yellow_icon);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, StaticMethods.getRandomColor());
        circuler_text_section_farmer.setBackground(unwrappedDrawable);

        return convertView;
    }


}
