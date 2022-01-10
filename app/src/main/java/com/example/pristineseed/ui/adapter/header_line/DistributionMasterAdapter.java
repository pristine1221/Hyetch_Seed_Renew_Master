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

import com.example.pristineseed.DataBaseRepository.distribution_master_table.Distribution_master_table;
import com.example.pristineseed.R;
import com.example.pristineseed.global.DateTimeUtilsCustome;
import com.example.pristineseed.global.StaticMethods;
import com.example.pristineseed.model.distributor_master.DistributorListModel;

import java.util.List;

public class DistributionMasterAdapter extends BaseAdapter {
    List<DistributorListModel> listdata;
    Activity activity;

    public DistributionMasterAdapter(Activity activity, List<DistributorListModel> listdata) {
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
        convertView = inflater.inflate(R.layout.distribution_list_layout, null);
        TextView tv_character_ofImageView = convertView.findViewById(R.id.tv_character_ofImageView);
        FrameLayout circuler_text_section = convertView.findViewById(R.id.circuler_text_section);
        TextView disbutor_code = convertView.findViewById(R.id.disbutor_code);
        TextView tv_distbutor_name = convertView.findViewById(R.id.distibutor_name);
        TextView disbutor_remark = convertView.findViewById(R.id.distbutor_remark);
        TextView distributor_date = convertView.findViewById(R.id.dist_date_time);

        if(listdata.get(position).distributor_code!=null) {
            disbutor_code.setText(listdata.get(position).distributor_code);
        }
        tv_distbutor_name.setText("Dist. name : "+listdata.get(position).district_name);
        disbutor_remark.setText(listdata.get(position).remarks);
        if(listdata.get(position).created_on!=null) {
            distributor_date.setText(DateTimeUtilsCustome.getDateYYYYMMDD(listdata.get(position).created_on));
        }

        if(listdata.get(position).district_name!=null && !listdata.get(position).district_name.equalsIgnoreCase("")) {
            tv_character_ofImageView.setText(String.valueOf(listdata.get(position).district_name.charAt(0)).toUpperCase());
        }
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(activity, R.drawable.circle_yellow_icon);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, StaticMethods.getRandomColor());
        circuler_text_section.setBackground(unwrappedDrawable);
        return convertView;
    }
}
