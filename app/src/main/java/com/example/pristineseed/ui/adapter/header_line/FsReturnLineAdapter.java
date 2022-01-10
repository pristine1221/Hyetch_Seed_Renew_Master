package com.example.pristineseed.ui.adapter.header_line;

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
import androidx.recyclerview.widget.RecyclerView;

import com.example.pristineseed.R;
import com.example.pristineseed.global.DateTimeUtilsCustome;
import com.example.pristineseed.global.StaticMethods;
import com.example.pristineseed.model.fs_return_harvesting.FSReturnHarvestingModel;

import java.util.List;

public class FsReturnLineAdapter extends BaseAdapter {
    private List<FSReturnHarvestingModel.FSReturnHarvestingLineModel> listdata;
    private   Activity activity;

    public FsReturnLineAdapter(Activity activity, List<FSReturnHarvestingModel.FSReturnHarvestingLineModel> listdata) {
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
        convertView = inflater.inflate(R.layout.fs_rerturn_harvesting_line_listview, null);
        TextView tv_character_ofImageView = convertView.findViewById(R.id.tv_character_ofImageView);
        FrameLayout circuler_text_section=convertView.findViewById(R.id.circuler_text_section);
        TextView loc_name = convertView.findViewById(R.id.fs_code);
        TextView tv_remarks=convertView.findViewById(R.id.tv_remarks);
        LinearLayout parent_layout=convertView.findViewById(R.id.parent_layout);
        TextView tv_crop_=convertView.findViewById(R.id.crop_);
        TextView tv_hybrid=convertView.findViewById(R.id.tv_hybrid);

        TextView created_on=convertView.findViewById(R.id.created_on);
        loc_name.setText("("+listdata.get(position).fs_return_code+")" + "  Line no: "+ listdata.get(position).line_no);
        tv_remarks.setText(listdata.get(position).remarks);

        if(listdata.get(position).created_on!=null && !listdata.get(position).created_on.equals("")) {
            created_on.setText(DateTimeUtilsCustome.getDateOnlyChange(listdata.get(position).created_on));
        }
        tv_crop_.setText(listdata.get(position).crop);
        tv_hybrid.setText(listdata.get(position).hybrid);

        if(listdata.get(position).created_by!=null && !listdata.get(position).created_by.equalsIgnoreCase("")){
            tv_character_ofImageView.setText(String.valueOf(listdata.get(position).created_by.charAt(0)) );
        }
        else {
            tv_character_ofImageView.setText(String.valueOf(listdata.get(position).creator.charAt(0)) );
        }

        Drawable unwrappedDrawable = AppCompatResources.getDrawable(activity, R.drawable.circle_yellow_icon);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, StaticMethods.getRandomColor());
        circuler_text_section.setBackground(unwrappedDrawable);

        return convertView;
    }

}
