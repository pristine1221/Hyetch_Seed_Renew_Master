package com.example.pristineseed.ui.adapter.LeaveApplicationAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.example.pristineseed.R;
import com.example.pristineseed.model.LeaveApplicationModel.LevaeTypeModel;
import java.util.List;

public class LeaveTypeAdapter extends RecyclerView.Adapter<LeaveTypeAdapter.CustomViewHolder> {

    private List<LevaeTypeModel> dataList;
    private Context context;

    public LeaveTypeAdapter(Context context, List<LevaeTypeModel> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        TextView tvLeaveCode, tvLeaveDes;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            tvLeaveCode = mView.findViewById(R.id.leave_code);
            tvLeaveDes = mView.findViewById(R.id.leave_desc);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.layout_leave_type, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        LevaeTypeModel leaveListModel = dataList.get(position);

        holder.tvLeaveCode.setText(leaveListModel.leave_code);
        holder.tvLeaveDes.setText(leaveListModel.description);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
