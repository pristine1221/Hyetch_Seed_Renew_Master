package com.example.pristineseed.ui.adapter.header_line;

import android.app.Activity;
import android.content.Context;
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
import com.example.pristineseed.model.Dealer.DealerMasterModel;
import com.example.pristineseed.model.distributor_master.DistributorListModel;

import java.util.List;

public class DealerListAdapter  extends BaseAdapter {
    private List<DealerMasterModel> dealer_list;
    private Activity context;
    public  DealerListAdapter(Activity context, List<DealerMasterModel> dealer_list){
        this.context=context;
        this.dealer_list=dealer_list;
    }

    @Override
    public int getCount() {
        return dealer_list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(R.layout.dealer_list_layout, null);
        TextView tv_character_ofImageView = convertView.findViewById(R.id.tv_character_ofImageView);
        FrameLayout circuler_text_section = convertView.findViewById(R.id.circuler_text_section);
        TextView disbutor_code = convertView.findViewById(R.id.dealer_code);
        TextView tv_distbutor_name = convertView.findViewById(R.id.dealer_name);
        TextView disbutor_remark = convertView.findViewById(R.id.dealer_remark);
        TextView distributor_date = convertView.findViewById(R.id.dealer_date_time);

        if(dealer_list.get(position).distributor_code!=null) {
            disbutor_code.setText(dealer_list.get(position).distributor_code);
        }
        tv_distbutor_name.setText("Dealer name : "+dealer_list.get(position).distributor_name);
        disbutor_remark.setText("Remark : "+dealer_list.get(position).remarks);
        distributor_date.setText(DateTimeUtilsCustome.getDateYYYYMMDD(dealer_list.get(position).created_on));

        if(dealer_list.get(position).distributor_name!=null) {
            tv_character_ofImageView.setText(String.valueOf(dealer_list.get(position).distributor_name.charAt(0)).toUpperCase());
        }
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(context, R.drawable.circle_yellow_icon);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, StaticMethods.getRandomColor());
        circuler_text_section.setBackground(unwrappedDrawable);
        return convertView;
    }
}
