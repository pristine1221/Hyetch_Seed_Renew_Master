package com.example.pristineseed.ui.adapter.header_line;

import android.app.Activity;
import android.graphics.Color;
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

import com.example.pristineseed.R;
import com.example.pristineseed.global.DateTimeUtilsCustome;
import com.example.pristineseed.global.StaticMethods;
import com.example.pristineseed.model.fs_return_harvesting.FSReturnHarvestingModel;
import com.example.pristineseed.model.home.CollectionList;

import java.util.List;

public class FSReturnHarvestingListAdapter extends BaseAdapter {
   private List<FSReturnHarvestingModel> listdata;
  private   Activity activity;

  private OnListItemClickListener onListItemClickListener;

  public interface OnListItemClickListener{
      void OnItemCLickListn(int pos);
  }

    public FSReturnHarvestingListAdapter(Activity activity, List<FSReturnHarvestingModel> listdata) {
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
        convertView = inflater.inflate(R.layout.fs_rerturn_harvesting_listview, null);
        TextView tv_character_ofImageView = convertView.findViewById(R.id.tv_character_ofImageView);
        FrameLayout circuler_text_section=convertView.findViewById(R.id.circuler_text_section);
        TextView loc_name = convertView.findViewById(R.id.loc_name);
        LinearLayout parent_layout=convertView.findViewById(R.id.parent_layout);
        TextView status_fs_return=convertView.findViewById(R.id.status_fs_return);
        TextView tv_season=convertView.findViewById(R.id.tv_session);
        TextView tv_doc_no=convertView.findViewById(R.id.tv_doc_no);

        TextView created_on=convertView.findViewById(R.id.created_on);
        loc_name.setText(listdata.get(position).location+"("+listdata.get(position).fs_return_code+")");


        if(listdata.get(position).created_on!=null && !listdata.get(position).created_on.equals("")) {
            created_on.setText(DateTimeUtilsCustome.getDateOnlyChange(listdata.get(position).created_on));
        }
        tv_doc_no.setText(listdata.get(position).fsioSalesOrderNo);
        tv_season.setText(listdata.get(position).season);

        if(listdata.get(position).created_by!=null && !listdata.get(position).created_by.equalsIgnoreCase("")){
            tv_character_ofImageView.setText(String.valueOf(listdata.get(position).created_by.charAt(0)) );
        }
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(activity, R.drawable.circle_yellow_icon);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, StaticMethods.getRandomColor());
        circuler_text_section.setBackground(unwrappedDrawable);

        switch (listdata.get(position).status) {
            case "Approve":
                status_fs_return.setText("Approved");
                status_fs_return.setTextColor(activity.getResources().getColor(R.color.colorPrimaryDark));
                break;
            case "Reject":
                status_fs_return.setText("Rejected");
                status_fs_return.setTextColor(activity.getResources().getColor(R.color.my_app_error_color));
                break;
            case "Pending":
                status_fs_return.setText(listdata.get(position).status);
                status_fs_return.setTextColor(activity.getResources().getColor(R.color.pending_color));
                break;
            case "Sent For Approve":
                status_fs_return.setText(listdata.get(position).status);
                status_fs_return.setTextColor(activity.getResources().getColor(R.color.pending_color));
                break;
            default:
                status_fs_return.setText(listdata.get(position).status);
                status_fs_return.setTextColor(activity.getResources().getColor(R.color.dark_gray));
             }

        parent_layout.setOnClickListener(v -> {
            onListItemClickListener.OnItemCLickListn(position);
        });

        return convertView;
    }

    public void setOnListItemClickListener(OnListItemClickListener onListItemClickListener){
      this.onListItemClickListener=onListItemClickListener;
    }

}
