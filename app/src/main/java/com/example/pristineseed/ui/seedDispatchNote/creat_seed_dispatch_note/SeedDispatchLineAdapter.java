package com.example.pristineseed.ui.seedDispatchNote.creat_seed_dispatch_note;

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

import com.example.pristineseed.DataBaseRepository.seed_dispatch_note.SeedDispatchNoteLineTable;
import com.example.pristineseed.R;
import com.example.pristineseed.global.DateTimeUtilsCustome;
import com.example.pristineseed.global.StaticMethods;
import com.example.pristineseed.model.seed_dispatch_note.SeedDispatchHeaderModel;

import java.util.List;

public class SeedDispatchLineAdapter extends BaseAdapter {
    public Activity activity;
    public List<SeedDispatchHeaderModel.SeedDispatchLineModel> listDataFarmer;

    public SeedDispatchLineAdapter(Activity activity, List<SeedDispatchHeaderModel.SeedDispatchLineModel> listDataFarmer) {
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

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = activity.getLayoutInflater();
        convertView = inflater.inflate(R.layout.seed_dispatch_line_list_layout, null);
        FrameLayout circuler_text_section_farmer = convertView.findViewById(R.id.circuler_text_section);
        TextView tv_farmer_name = convertView.findViewById(R.id.tv_farmer_name);
        TextView tv_hybrid = convertView.findViewById(R.id.tv_hybrid);
        TextView tv_village = convertView.findViewById(R.id.tv_village);
        TextView remarks = convertView.findViewById(R.id.remarks);
        TextView date_time = convertView.findViewById(R.id.date_time);
        TextView tv_character_ofImageView=convertView.findViewById(R.id.tv_character_ofImageView);

        //todo set the data into list

        tv_farmer_name.setText("Farmer : "+ listDataFarmer.get(position).farmer_name);
        tv_hybrid.setText("Hybrid : " + listDataFarmer.get(position).hybrid_name);
        tv_village.setText("Village : "+listDataFarmer.get(position).village_name);
        remarks.setText("Remarks : "+ listDataFarmer.get(position).remarks);
        date_time.setText(DateTimeUtilsCustome.getDateWithSplitTime(listDataFarmer.get(position).created_on));

        if (listDataFarmer.get(position).farmer_name != null && !listDataFarmer.get(position).farmer_name.equalsIgnoreCase("")) {
            tv_character_ofImageView.setText(String.valueOf(listDataFarmer.get(position).farmer_name.charAt(0)).toUpperCase());
        }

        Drawable unwrappedDrawable = AppCompatResources.getDrawable(activity, R.drawable.circle_yellow_icon);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, StaticMethods.getRandomColor());
        circuler_text_section_farmer.setBackground(unwrappedDrawable);
        return convertView;
    }

}