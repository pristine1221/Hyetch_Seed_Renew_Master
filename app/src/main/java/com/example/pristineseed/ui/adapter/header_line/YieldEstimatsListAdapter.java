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
import com.example.pristineseed.model.YeildEstimateModel;
import com.example.pristineseed.model.home.OrderList;

import java.util.List;

public class YieldEstimatsListAdapter extends BaseAdapter {
    List<YeildEstimateModel> listdata;
    Activity activity;

    public YieldEstimatsListAdapter(Activity activity, List<YeildEstimateModel> listdata) {
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
        convertView = inflater.inflate(R.layout.yield_estimate_list_layout, null);
        TextView tv_character_ofImageView = convertView.findViewById(R.id.tv_character_ofImageView);
        FrameLayout circuler_text_section=convertView.findViewById(R.id.circuler_text_section);
        TextView title = convertView.findViewById(R.id.title);
        TextView subtitle = convertView.findViewById(R.id.subtitle);
        subtitle.setText("Yield Estimate Date : "+listdata.get(position).yield_estimate_date);
        TextView remarks=convertView.findViewById(R.id.remarks);
        TextView date_time = convertView.findViewById(R.id.tv_date_time);
        title.setText("Lot : "+listdata.get(position).lot_no);
        remarks.setText("Remark : "+listdata.get(position).remarks);

        date_time.setText(listdata.get(position).created_on);
        tv_character_ofImageView.setText("YE");
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(activity, R.drawable.circle_yellow_icon);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, StaticMethods.getRandomColor());
        circuler_text_section.setBackground(unwrappedDrawable);
        return convertView;
    }

}
