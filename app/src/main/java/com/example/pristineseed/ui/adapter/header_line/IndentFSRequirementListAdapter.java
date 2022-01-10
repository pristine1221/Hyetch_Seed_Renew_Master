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
import com.example.pristineseed.model.fs_indent_requirement.FsIndentRequirementsModel;
import com.example.pristineseed.model.home.CollectionList;

import java.util.List;

public class IndentFSRequirementListAdapter extends BaseAdapter {
    private List<FsIndentRequirementsModel> listdata;
   private Activity activity;

    private OnListItemClickListener onListItemClickListener;

    public interface OnListItemClickListener{
        void OnItemCLickListn(int pos);
    }

    public IndentFSRequirementListAdapter(Activity activity, List<FsIndentRequirementsModel> listdata) {
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
        convertView = inflater.inflate(R.layout.fs_indent_requirement_list_layout, null);
        TextView tv_character_ofImageView = convertView.findViewById(R.id.tv_character_ofImageView);
        FrameLayout circuler_text_section=convertView.findViewById(R.id.circuler_text_section);
        TextView fs_code = convertView.findViewById(R.id.fs_code);
        TextView crop_ = convertView.findViewById(R.id.crop_);
        TextView tv_hybrid=convertView.findViewById(R.id.tv_hybrid);
        TextView tv_remarks=convertView.findViewById(R.id.tv_remarks);
        LinearLayout parent_layout=convertView.findViewById(R.id.parent_layout);
        TextView status_fs_return=convertView.findViewById(R.id.status_fs_return);

        TextView created_on=convertView.findViewById(R.id.created_on);
        fs_code.setText(listdata.get(position).location+"("+listdata.get(position).fs_req_code+")");


        if(listdata.get(position).crop!=null){
        crop_.setText(listdata.get(position).crop);
        }
        tv_hybrid.setText(listdata.get(position).hybrid);
        tv_remarks.setText(listdata.get(position).remarks);

        if(listdata.get(position).created_on!=null && !listdata.get(position).created_on.equals("")) {
            created_on.setText(DateTimeUtilsCustome.getDateOnlyChange(listdata.get(position).created_on));
        }
//        created_on.setText(listdata.get(position).created_on);

        if(listdata.get(position).created_by!=null && !listdata.get(position).created_by.equalsIgnoreCase("")){
            tv_character_ofImageView.setText("FS" );
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
