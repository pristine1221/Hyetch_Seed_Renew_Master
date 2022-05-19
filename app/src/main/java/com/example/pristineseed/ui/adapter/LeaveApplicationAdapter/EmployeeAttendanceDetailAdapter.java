package com.example.pristineseed.ui.adapter.LeaveApplicationAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pristineseed.R;
import com.example.pristineseed.global.DateTimeUtilsCustome;
import com.example.pristineseed.model.EmployeeModel.EmployeeAttendanceDetailModel;

import java.util.List;

public class EmployeeAttendanceDetailAdapter extends RecyclerView.Adapter<EmployeeAttendanceDetailAdapter.ViewHolder> {
    private Context context;
    private List<EmployeeAttendanceDetailModel> list;

    public EmployeeAttendanceDetailAdapter(Context context, List<EmployeeAttendanceDetailModel> list){
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public EmployeeAttendanceDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_attendance_list_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeAttendanceDetailAdapter.ViewHolder holder, int position) {
        EmployeeAttendanceDetailModel model = list.get(position);
        holder.tv_emp_id.setText(model.emp_id);
        holder.tv_work_type.setText(model.work_type);
        holder.tv_work_loc.setText(model.work_location);
        holder.tv_start_date.setText(DateTimeUtilsCustome.getDateOnly(model.start_datetime));
        holder.tv_end_date.setText(DateTimeUtilsCustome.getDateOnly(model.end_datetime));
        holder.tv_start_loc.setText(model.start_location);
        holder.tv_end_loc.setText(model.end_location);
        holder.tv_work_hour.setText(model.work_hours);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_emp_id, tv_work_type, tv_work_loc, tv_start_date, tv_end_date, tv_start_loc, tv_end_loc, tv_work_hour;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_emp_id = itemView.findViewById(R.id.tv_emp_id);
            tv_work_type = itemView.findViewById(R.id.tv_work_type);
            tv_work_loc = itemView.findViewById(R.id.tv_work_loc);
            tv_start_date = itemView.findViewById(R.id.tv_start_date);
            tv_end_date = itemView.findViewById(R.id.tv_end_date);
            tv_start_loc = itemView.findViewById(R.id.tv_start_loc);
            tv_end_loc = itemView.findViewById(R.id.tv_end_loc);
            tv_work_hour = itemView.findViewById(R.id.tv_work_hour);
        }
    }
}
