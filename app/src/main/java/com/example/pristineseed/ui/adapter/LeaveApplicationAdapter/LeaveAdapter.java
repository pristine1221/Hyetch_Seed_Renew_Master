package com.example.pristineseed.ui.adapter.LeaveApplicationAdapter;

import android.app.TimePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pristineseed.R;
import com.example.pristineseed.model.LeaveApplicationModel.LeaveListModel;

import java.util.List;

public class LeaveAdapter extends RecyclerView.Adapter<LeaveAdapter.CustomViewHolder> {

    private List<LeaveListModel> dataList;
    private Context context;
    TimePickerDialog timePickerDialog;

    public LeaveAdapter(Context context, List<LeaveListModel> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        TextView tvLeaveCode, tvLeaveDesc, tvLeaveCarryForward, tvLeaveCarryForwardLimit, tvBalancedLeave;
        ImageView attendance_delete;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            tvLeaveCode = mView.findViewById(R.id.leave_code);
            tvLeaveDesc = mView.findViewById(R.id.leave_desc);
            tvLeaveCarryForward = mView.findViewById(R.id.leave_forward);
            tvLeaveCarryForwardLimit = mView.findViewById(R.id.leave_forward_limit);
            tvBalancedLeave = mView.findViewById(R.id.leave_balanced_list);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.layout_leave_list, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        LeaveListModel leaveListModel = dataList.get(position);

        holder.tvLeaveCode.setText(leaveListModel.leave_Code);
        holder.tvLeaveCarryForward.setText(leaveListModel.carry_Forward);
        holder.tvLeaveCarryForwardLimit.setText(leaveListModel.carry_Forward_Limit);
        holder.tvLeaveDesc.setText(leaveListModel.description);
        holder.tvBalancedLeave.setText(leaveListModel.balance_Leave_s);


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


}
