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
import com.example.pristineseed.model.home.CollectionList;

import java.util.List;

public class CollectionListAdapter extends BaseAdapter {
    List<CollectionList> listdata;
    Activity activity;

    public CollectionListAdapter(Activity activity, List<CollectionList> listdata) {
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
        convertView = inflater.inflate(R.layout.collection_listview, null);
        TextView tv_character_ofImageView = convertView.findViewById(R.id.tv_character_ofImageView);
        FrameLayout circuler_text_section=convertView.findViewById(R.id.circuler_text_section);
        TextView title = convertView.findViewById(R.id.title);
        TextView subtitle = convertView.findViewById(R.id.subtitle);
        TextView date_time = convertView.findViewById(R.id.date_time);
        TextView subtitle2=convertView.findViewById(R.id.subtitle2);
        TextView tv_remark_coll=convertView.findViewById(R.id.tv_remark_coll);
        title.setText("Coll.cd. : "+listdata.get(position).getCollection_code());
        subtitle.setText("Name : "+listdata.get(position).getParty_name()+"("+listdata.get(position).getChq_dd_rtgs_no()+")");
        date_time.setText(listdata.get(position).getDate());
        subtitle2.setText("Bank name : "+listdata.get(position).getDrawn_on_bank_name());
        tv_remark_coll.setText("Remark : "+listdata.get(position).getRemark());

        tv_character_ofImageView.setText(String.valueOf(listdata.get(position).getParty_name().charAt(0)).toUpperCase());
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(activity, R.drawable.circle_yellow_icon);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, StaticMethods.getRandomColor());
        circuler_text_section.setBackground(unwrappedDrawable);
        return convertView;
    }

}
