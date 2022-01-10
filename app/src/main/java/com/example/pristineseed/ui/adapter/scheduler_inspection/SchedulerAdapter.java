package com.example.pristineseed.ui.adapter.scheduler_inspection;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pristineseed.R;
import com.example.pristineseed.global.DateTimeUtilsCustome;
import com.example.pristineseed.model.scheduler_inspection.SchedulerModel;

import java.util.List;

public class SchedulerAdapter extends BaseExpandableListAdapter {
    private final Context context;
    private final List<SchedulerModel> expandableListDetail;

    public SchedulerAdapter(Context context, List<SchedulerModel> expandableListDetail) {
        this.context = context;
        this.expandableListDetail = expandableListDetail;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        SchedulerModel.SchedulerLines data = this.expandableListDetail.get(listPosition).scheduler_lines.get(expandedListPosition);
        return data;
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final SchedulerModel.SchedulerLines schedulerLinesList = (SchedulerModel.SchedulerLines) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.scheduler_line_list_layout, null);
        }
        TextView tv_lot_no = convertView.findViewById(R.id.tv_prod_lot);
        //TextView tv_scheduler_no = convertView.findViewById(R.id.schedule_no);
        TextView tv_arrival_no = convertView.findViewById(R.id.tv_arrival_no);
        TextView tv_organizer_name = convertView.findViewById(R.id.organizer_name);
        TextView tv_organizer_code = convertView.findViewById(R.id.org_code);
        TextView tv_grower_owner = convertView.findViewById(R.id.grwer_owner);
        TextView tv_status = convertView.findViewById(R.id.tv_status);

        tv_lot_no.setText(schedulerLinesList.production_lot_no);
        //tv_scheduler_no.setText(schedulerLinesList.schedule_no);
        tv_arrival_no.setText(schedulerLinesList.arrival_plan_no);
        tv_organizer_name.setText(schedulerLinesList.organizer_name);
        tv_organizer_code.setText(schedulerLinesList.organizer_code);
        tv_grower_owner.setText(schedulerLinesList.grower_owner);

        try {
            if (schedulerLinesList.inspection_1 > 0) {
                tv_status.setText("GER. Done");
                tv_status.setTextColor(Color.parseColor("#007E35"));

                if (schedulerLinesList.inspection_2 > 0) {
                    tv_status.setText("SED. Done");
                    tv_status.setTextColor(Color.parseColor("#007E35"));
                }
                if (schedulerLinesList.inspection_3 > 0) {
                    tv_status.setText("VEG. Done");
                    tv_status.setTextColor(Color.parseColor("#007E35"));
                }
                if (schedulerLinesList.inspection_4 > 0) {
                    tv_status.setText("NIK. Done");
                    tv_status.setTextColor(Color.parseColor("#007E35"));
                }
                if (schedulerLinesList.inspection_5 > 0) {
                    tv_status.setText("NIK2. Done");
                    tv_status.setTextColor(Color.parseColor("#007E35"));
                }
                if (schedulerLinesList.inspection_6 > 0) {
                    tv_status.setText("FLWR. Done");
                    tv_status.setTextColor(Color.parseColor("#007E35"));
                }
                if (schedulerLinesList.inspection_7 > 0) {
                    tv_status.setText("PFLWR. Done");
                    tv_status.setTextColor(Color.parseColor("#007E35"));
                }
                if (schedulerLinesList.inspection_8 > 0) {
                    tv_status.setText("MRTY. Done");
                    tv_status.setTextColor(Color.parseColor("#007E35"));
                }

                if (schedulerLinesList.inspection_9 > 0) {
                    tv_status.setText("Harvst. Done");
                    tv_status.setTextColor(Color.parseColor("#007E35"));
                }
                if (schedulerLinesList.inspection_qc > 0) {
                    tv_status.setText("QC. Done");
                    tv_status.setTextColor(Color.parseColor("#007E35"));
                }
            }else {
                tv_status.setText("Pending");
                tv_status.setTextColor(Color.parseColor("#F44336"));
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
       /* if(this.expandableListDetail.get(listPosition).il.size()==0){
         return 0;
        }*/
        return this.expandableListDetail.get(listPosition).scheduler_lines
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListDetail.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListDetail.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        SchedulerModel listTitle = (SchedulerModel) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.scheduler_list_layout, null);
        }

        TextView scheduler_no = convertView.findViewById(R.id.scheduler_no);
        TextView tv_status = convertView.findViewById(R.id.status);
        TextView tv_season = convertView.findViewById(R.id.tv_season);
        TextView tv_season_name = convertView.findViewById(R.id.season_name);
        TextView tv_tv_prod_no = convertView.findViewById(R.id.tv_prod_no);
        TextView tv_date_ = convertView.findViewById(R.id.date_);

        scheduler_no.setText(listTitle.schedule_no);
        tv_status.setText(listTitle.status);
        tv_season.setText(listTitle.season);
        tv_season_name.setText(listTitle.season_name);
        tv_tv_prod_no.setText(listTitle.production_centre);
        tv_date_.setText(DateTimeUtilsCustome.getDate_Time(listTitle.date));
        ImageView arrow_up_down = convertView.findViewById(R.id.arrow_up_down);

        if (isExpanded) {
            arrow_up_down.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_keyboard_arrow_up_black_24dp));
        } else {
            arrow_up_down.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_keyboard_arrow_down_black_24dp));
        }
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }

}
