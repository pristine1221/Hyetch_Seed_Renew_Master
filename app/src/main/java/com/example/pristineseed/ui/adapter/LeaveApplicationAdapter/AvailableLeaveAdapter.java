package com.example.pristineseed.ui.adapter.LeaveApplicationAdapter;

import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pristineseed.R;
import com.example.pristineseed.global.DateTimeUtilsCustome;
import com.example.pristineseed.model.LeaveApplicationModel.AvailableLeaveListModel;

import java.util.List;

public class AvailableLeaveAdapter extends RecyclerView.Adapter<AvailableLeaveAdapter.CustomViewHolder> {

    private List<AvailableLeaveListModel> dataList;
    private Context context;

    public AvailableLeaveAdapter(Context context, List<AvailableLeaveListModel> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    public void updateList(List<AvailableLeaveListModel> list) {
        dataList = list;
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        TextView tvLeaveCode, tvLeaveType, tvLeaveStartDate, tvLeaveEndDate, tvLeaveStatus, tvLeaveHalfDay, tvLeaveReason,tvLeaveCodeNo,leave_id;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            tvLeaveCode = mView.findViewById(R.id.leave_approval_name);
            tvLeaveType = mView.findViewById(R.id.leave_type);
            tvLeaveStartDate = mView.findViewById(R.id.leave_start_date);
            tvLeaveEndDate = mView.findViewById(R.id.leave_end_date);
            tvLeaveStatus = mView.findViewById(R.id.leave_status);
            tvLeaveHalfDay = mView.findViewById(R.id.leave_half_day);
            tvLeaveReason = mView.findViewById(R.id.leave_reason);
            tvLeaveCodeNo=mView.findViewById(R.id.leave_code);
            leave_id=mView.findViewById(R.id.leave_id);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.layout_available_leave_list, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        AvailableLeaveListModel availableLeaveListModel = dataList.get(position);
        String s1 = Boolean.toString(availableLeaveListModel.half_day);
        holder.tvLeaveCode.setText(availableLeaveListModel.approval_id);
        if(availableLeaveListModel.start_datetime!=null) {
            holder.tvLeaveStartDate.setText(DateTimeUtilsCustome.getDate_Time2(availableLeaveListModel.start_datetime));
        }
        holder.tvLeaveHalfDay.setText(s1);
        holder.tvLeaveReason.setText(availableLeaveListModel.reason);
        holder.tvLeaveType.setText(availableLeaveListModel.type);
        holder.tvLeaveCodeNo.setText(availableLeaveListModel.emp_id);
        holder.leave_id.setText(String.valueOf(availableLeaveListModel.id));
        if(availableLeaveListModel.end_datetime!=null){
            holder.tvLeaveEndDate.setText(DateTimeUtilsCustome.getDate_Time2(availableLeaveListModel.end_datetime));
        }
        if(availableLeaveListModel.status.equalsIgnoreCase("Pending")){
            holder.tvLeaveStatus.setText(availableLeaveListModel.status);
            holder.tvLeaveStatus.setTextColor(Color.parseColor("#FF0000"));
        }else{
            holder.tvLeaveStatus.setText(availableLeaveListModel.status);
            holder.tvLeaveStatus.setTextColor(context.getResources().getColor(R.color.colorPrimary));

        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

}
