package com.example.pristineseed.ui.adapter.LeaveApplicationAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pristineseed.R;
import com.example.pristineseed.global.ApiRequestFailure;
import com.example.pristineseed.model.LeaveApplicationModel.LeaveCreateViewModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeaveAppliedCreateAdapter extends RecyclerView.Adapter<LeaveAppliedCreateAdapter.CustomViewHolder> {
    private List<LeaveCreateViewModel> dataList;
    private Context context;

    public LeaveAppliedCreateAdapter(Context context, List<LeaveCreateViewModel> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.layout_leave_applied_create, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        LeaveCreateViewModel leaveCreateViewModel = dataList.get(position);
        holder.tvFDate.setText(leaveCreateViewModel.from_date);
        holder.tvtDate.setText(leaveCreateViewModel.to_date);
        holder.tvDays.setText(leaveCreateViewModel.days_diff);
        holder.tvType.setText(leaveCreateViewModel.type);
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLeaveAppliedDeleteApi(leaveCreateViewModel.id, leaveCreateViewModel.line_no, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void onLeaveAppliedDeleteApi(String leave_id, String line_no, int position) {
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();

        Call<List<LeaveAppliedDeleteModel>> call = mAPIService.getLeaveAppliedDelete(leave_id, line_no);
        call.enqueue(new Callback<List<LeaveAppliedDeleteModel>>() {
            @Override
            public void onResponse(Call<List<LeaveAppliedDeleteModel>> call, Response<List<LeaveAppliedDeleteModel>> response) {
                try {
                    if (response.isSuccessful()) {
                        List<LeaveAppliedDeleteModel> deleteAppliedList = response.body();
                        if (deleteAppliedList.size() > 0 && deleteAppliedList.get(0).condition) {
                            dataList.remove(position);
                            notifyDataSetChanged();
                        }
                    }
                } catch (Exception e) {
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "Applied Create list", context);
                } finally {
                }
            }

            @Override
            public void onFailure(Call<List<LeaveAppliedDeleteModel>> call, Throwable t) {
                Toast.makeText(context, "Error", Toast.LENGTH_LONG
                ).show();
            }
        });
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        TextView tvFDate, tvtDate, tvDays, tvType;
        ImageView btnDelete;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            tvFDate = mView.findViewById(R.id.tv_from_date);
            tvtDate = mView.findViewById(R.id.tv_to_end_date);
            tvDays = mView.findViewById(R.id.tv_days);
            tvType = mView.findViewById(R.id.tv_emp_type);
            btnDelete = mView.findViewById(R.id.approve_delete);
        }
    }
}
