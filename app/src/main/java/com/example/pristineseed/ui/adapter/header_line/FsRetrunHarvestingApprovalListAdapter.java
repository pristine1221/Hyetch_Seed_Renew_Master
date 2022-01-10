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

import com.example.pristineseed.R;
import com.example.pristineseed.global.DateTimeUtilsCustome;
import com.example.pristineseed.global.StaticMethods;
import com.example.pristineseed.model.fs_return_harvesting.FSReturnHarvestingModel;
import com.example.pristineseed.model.fs_return_harvesting.FsRetrunAapprovalModel;

import java.util.List;

public class FsRetrunHarvestingApprovalListAdapter  extends BaseAdapter {
    private List<FsRetrunAapprovalModel> listdata;
    private Activity activity;
    private OnListApproveRejectClickListner onListApproveRejectClickListner;

    public interface OnListApproveRejectClickListner {
        void onBtnCLick(String flag, int pos);
    }

    public FsRetrunHarvestingApprovalListAdapter(Activity activity, List<FsRetrunAapprovalModel> listdata) {
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
        convertView = inflater.inflate(R.layout.fs_retrun_approval_listview, null);
        TextView tv_character_ofImageView = convertView.findViewById(R.id.tv_character_ofImageView);
        FrameLayout circuler_text_section = convertView.findViewById(R.id.circuler_text_section);
        TextView loc_name = convertView.findViewById(R.id.loc_name);
        TextView tv_session = convertView.findViewById(R.id.tv_session);
        TextView crop_ = convertView.findViewById(R.id.crop_);
        TextView tv_hybrid = convertView.findViewById(R.id.tv_hybrid);
        TextView tv_remarks = convertView.findViewById(R.id.tv_remarks);
        LinearLayout parent_layout = convertView.findViewById(R.id.parent_layout);
        TextView status_fs_return = convertView.findViewById(R.id.status_fs_return);

        TextView created_on = convertView.findViewById(R.id.created_on);
        loc_name.setText(listdata.get(position).location + "(" + listdata.get(position).fs_return_code + ")");
        tv_session.setText(listdata.get(position).season);
        crop_.setText(listdata.get(position).crop);
        tv_hybrid.setText(listdata.get(position).hybrid);
        tv_remarks.setText(listdata.get(position).remarks);
        if(listdata.get(position).created_on!=null && !listdata.get(position).created_on.equals("")) {
            created_on.setText(DateTimeUtilsCustome.getDateOnlyChange(listdata.get(position).created_on));
        }
//        created_on.setText(listdata.get(position).created_on);

        if (listdata.get(position).status.equalsIgnoreCase("Approve")) {
            status_fs_return.setText(listdata.get(position).status);
            status_fs_return.setTextColor(activity.getResources().getColor(R.color.colorPrimaryDark));
        } else if (listdata.get(position).status.equalsIgnoreCase("Reject")) {
            status_fs_return.setText(listdata.get(position).status);
            status_fs_return.setTextColor(activity.getResources().getColor(R.color.my_app_error_color));
        } else {
            status_fs_return.setText(listdata.get(position).status);
            status_fs_return.setTextColor(activity.getResources().getColor(R.color.pending_color));
        }

        status_fs_return.setOnClickListener(v -> {
            onListApproveRejectClickListner.onBtnCLick("ApproveRejected", position);
        });

        Drawable unwrappedDrawable = AppCompatResources.getDrawable(activity, R.drawable.circle_yellow_icon);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, StaticMethods.getRandomColor());
        circuler_text_section.setBackground(unwrappedDrawable);

        if (listdata.get(position).created_by != null && !listdata.get(position).created_by.equalsIgnoreCase("")) {
            tv_character_ofImageView.setText(String.valueOf(listdata.get(position).created_by.charAt(0)));
        }
        return convertView;
    }

    public void setOnApprvRejctClickListnerr(OnListApproveRejectClickListner onListApproveRejectClickListner) {

        this.onListApproveRejectClickListner = onListApproveRejectClickListner;
    }

}