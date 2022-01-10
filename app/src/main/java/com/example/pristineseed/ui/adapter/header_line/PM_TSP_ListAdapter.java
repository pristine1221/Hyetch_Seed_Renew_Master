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
import com.example.pristineseed.model.TspModel.TrialSeedModel;
import com.example.pristineseed.model.home.OrderList;

import java.util.List;

public class PM_TSP_ListAdapter extends BaseAdapter {
    List<TrialSeedModel> listdata;
    Activity activity;

    public  PM_TSP_ListAdapter(Activity activity, List<TrialSeedModel> listdata) {
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
        convertView = inflater.inflate(R.layout.trial_seed_list_layout, null);
        TextView tv_character_ofImageView = convertView.findViewById(R.id.tv_character_ofImageView);
        FrameLayout circuler_text_section=convertView.findViewById(R.id.circuler_text_section);
        TextView tv_tsp_detail = convertView.findViewById(R.id.tv_tsp_detail);
        TextView producblty_of_hybrid=convertView.findViewById(R.id.producblty_of_hybrid);
        TextView date_of_sowing = convertView.findViewById(R.id.date_of_sowing);
        TextView date_time = convertView.findViewById(R.id.tv_date_time);

        tv_tsp_detail.setText("TSP : "+listdata.get(position).tsp_type+"("+listdata.get(position).tsp_code+")");
        producblty_of_hybrid.setText("Producublity Hybrid : "+listdata.get(position).produciblity_of_hybrid);
        date_of_sowing.setText("Sowing date : "+listdata.get(position).date_of_sowing);
        date_time.setText(listdata.get(position).created_on);
        tv_character_ofImageView.setText("PM");


        Drawable unwrappedDrawable = AppCompatResources.getDrawable(activity, R.drawable.circle_yellow_icon);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, StaticMethods.getRandomColor());
        circuler_text_section.setBackground(unwrappedDrawable);
        return convertView;
    }

}
