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
import com.example.pristineseed.model.Breeder.BreederListVisitorModel;

import java.util.List;

public class BreederVisitListAdapter extends BaseAdapter {
    List<BreederListVisitorModel> listdata;
    Activity activity;

    public BreederVisitListAdapter(Activity activity, List<BreederListVisitorModel> listdata) {
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
        convertView = inflater.inflate(R.layout.breeder_listview_layout, null);
        TextView tv_character_ofImageView = convertView.findViewById(R.id.tv_character_ofImageView);
        FrameLayout circuler_text_section=convertView.findViewById(R.id.circuler_text_section);
        TextView vistor_code = convertView.findViewById(R.id.visitor_code);
        TextView subtitle = convertView.findViewById(R.id.comment);
        TextView date_time = convertView.findViewById(R.id.visit_date);
        vistor_code.setText("Lot : "+listdata.get(position).lot_no+"("+listdata.get(position).visitor_code+")"+" : "+listdata.get(position).visitor_name);
        subtitle.setText("Remark : "+listdata.get(position).comment);
        date_time.setText(listdata.get(position).date_of_visit);
        if(listdata.get(position).visitor_name!=null && !listdata.get(position).visitor_name.equals("")) {
            tv_character_ofImageView.setText(String.valueOf(listdata.get(position).visitor_name.charAt(0)));
        }
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(activity, R.drawable.circle_yellow_icon);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, StaticMethods.getRandomColor());
        circuler_text_section.setBackground(unwrappedDrawable);
        return convertView;
    }

}
