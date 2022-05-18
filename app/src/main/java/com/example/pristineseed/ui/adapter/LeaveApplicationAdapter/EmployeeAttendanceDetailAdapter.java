package com.example.pristineseed.ui.adapter.LeaveApplicationAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pristineseed.R;
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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
