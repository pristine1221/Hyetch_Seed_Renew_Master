package com.example.pristineseed.ui.adapter.LeaveApplicationAdapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.TextClassifierEvent;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pristineseed.GlobalNotification.NetworkUtil;
import com.example.pristineseed.R;
import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.global.ApiRequestFailure;
import com.example.pristineseed.global.DateTimeUtilsCustome;
import com.example.pristineseed.global.LoadingDialog;
import com.example.pristineseed.model.LeaveApplicationModel.LeavePendingApprovedModel;
import com.example.pristineseed.model.LeaveApplicationModel.LeavePendingForApprovalModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;
import com.google.gson.internal.$Gson$Preconditions;

import java.sql.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeavePendingListAdapter extends RecyclerView.Adapter<LeavePendingListAdapter.CustomViewHolder> {
    private List<LeavePendingForApprovalModel> dataList;
    private Context context;
    AutoCompleteTextView select_oder_status;
    String[] leaveTypeList = {"Approved", "Rejected"};
    TextInputEditText et_desc;
    Button btn_ok;
    String id;
    private SessionManagement sessionManagement;

    public LeavePendingListAdapter(Context context, List<LeavePendingForApprovalModel> dataList) {
        this.context = context;
        this.dataList = dataList;
        sessionManagement = new SessionManagement(context);
    }

    public void updateList(List<LeavePendingForApprovalModel> list) {
        dataList = list;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        TextView empId, tvDesc, tvStartDate, tvEndDate, tvReason, tvType,leave_id;
        ImageView approvalEdit;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            empId = mView.findViewById(R.id.tv_emp_id);
            tvStartDate = mView.findViewById(R.id.tv_start_date);
            tvEndDate = mView.findViewById(R.id.tv_end_date);
            tvReason = mView.findViewById(R.id.tv_reason);
            tvType = mView.findViewById(R.id.tv_type);
            approvalEdit = mView.findViewById(R.id.approve_edit);
            leave_id=mView.findViewById(R.id.tv_leave_id);

        }
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.view_leave_pending_layout, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        LeavePendingForApprovalModel leavePendingForApprovalModel = dataList.get(position);

        holder.empId.setText(leavePendingForApprovalModel.emp_id);
       try {
           holder.leave_id.setText(String.valueOf(leavePendingForApprovalModel.id));
       }catch (Exception e){
           e.printStackTrace();
       }
        holder.tvStartDate.setText(DateTimeUtilsCustome.get_yyyy_mm_dd_to_dd_mm_yyyy_ToDateFormate(leavePendingForApprovalModel.start_datetime));
        holder.tvEndDate.setText(DateTimeUtilsCustome.get_yyyy_mm_dd_to_dd_mm_yyyy_ToDateFormate(leavePendingForApprovalModel.end_datetime));
        holder.tvReason.setText(leavePendingForApprovalModel.reason);
        holder.tvType.setText(leavePendingForApprovalModel.type);
        id = String.valueOf(dataList.get(position).id);

        holder.approvalEdit.setOnClickListener(v -> {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View popupView = inflater.inflate(R.layout.add_leave_pending_popup, null);
            Dialog dialog = new Dialog(context, android.R.style.Theme_DeviceDefault_DialogWhenLarge_NoActionBar);
            dialog.setContentView(popupView);
            dialog.getWindow().getAttributes().windowAnimations = R.style.AppTheme_FullScreenDialog;
            dialog.show();
            btn_ok = popupView.findViewById(R.id.btn_ok);
            select_oder_status = popupView.findViewById(R.id.select_oder_status);
            et_desc = popupView.findViewById(R.id.et_desc);
            ImageView close_dilog_bt = popupView.findViewById(R.id.close_dilog_bt);
            close_dilog_bt.setOnClickListener(view -> {
                dialog.dismiss();
            });

            btn_ok.setOnClickListener(v1 -> {
                if(select_oder_status.getText().toString().trim().equalsIgnoreCase("")){
                    Toast.makeText(context,"Please select approval status.",Toast.LENGTH_SHORT).show();
                }
                if(select_oder_status.getText().toString().trim().equalsIgnoreCase("Rejected")) {
                    if (et_desc.getText().toString().trim().equalsIgnoreCase("")) {
                        Toast.makeText(context, "Please select enter reject reason", Toast.LENGTH_SHORT).show();
                    } else {
                        onLeavePendingApproved(position, String.valueOf(dataList.get(position).id));
                        dialog.dismiss();
                    }
                }
                else {
                    onLeavePendingApproved(position, String.valueOf(dataList.get(position).id));
                    dialog.dismiss();

                }
            });

            select_oder_status.setOnItemClickListener((parent, view, position1, id) -> {
                if (position1 == 0) {
                    et_desc.setText("");
                    et_desc.setEnabled(false);
                } else if (position1 == 1) {
                    et_desc.setEnabled(true);

                }
            });
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, leaveTypeList);
            select_oder_status.setAdapter(adapter);
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void onLeavePendingApproved(int pos,String id) {
        if(NetworkUtil.getConnectivityStatusBoolean(context)) {
            LoadingDialog progressDialogLoading = new LoadingDialog();
            progressDialogLoading.showLoadingDialog((Activity) context);
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            JsonObject postedJson = new JsonObject();
            postedJson.addProperty("Id", id);
            postedJson.addProperty("EmailId", sessionManagement.getUserEmail());
            postedJson.addProperty("Status", select_oder_status.getText().toString());
            postedJson.addProperty("Reason", et_desc.getText().toString());

            Call<List<LeavePendingApprovedModel>> call = mAPIService.getLeaveApproved(postedJson);

            call.enqueue(new Callback<List<LeavePendingApprovedModel>>() {
                @Override
                public void onResponse(Call<List<LeavePendingApprovedModel>> call, Response<List<LeavePendingApprovedModel>> response) {
                    try {
                        if (response.isSuccessful()) {
                            progressDialogLoading.hideDialog();
                            List<LeavePendingApprovedModel> list = response.body();
                                if (list!=null && list.size() > 0 && list.get(0).condition) {
                                    dataList.remove(pos);
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, list.get(0).message, Toast.LENGTH_LONG).show();
                                    progressDialogLoading.hideDialog();
                                }
                        }
                    } catch (Exception e) {
                        progressDialogLoading.hideDialog();
                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "submit", context);
                    }
                }

                @Override
                public void onFailure(Call<List<LeavePendingApprovedModel>> call, Throwable t) {
                    progressDialogLoading.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "submit_leave_approved", context);
                }
            });
        }else {
            Toast.makeText(context,"Please wait for internet connection",Toast.LENGTH_SHORT).show();
        }
    }
}