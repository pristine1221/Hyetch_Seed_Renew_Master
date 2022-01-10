package com.example.pristineseed.ui.employee.HRPortalEmployee.appliedLeave;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pristineseed.GlobalNotification.NetworkUtil;
import com.example.pristineseed.R;
import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.global.ApiRequestFailure;
import com.example.pristineseed.global.LoadingDialog;
import com.example.pristineseed.model.LeaveApplicationModel.LeaveDaysModel;
import com.example.pristineseed.model.LeaveApplicationModel.LevaeTypeModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.adapter.LeaveApplicationAdapter.LeaveDaysAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeaveAppliedFragment extends Fragment {
    private LeaveDaysAdapter leaveTypeAdapter;
    private RecyclerView leave_days_list;
    private SessionManagement sessionManagement;
    private int id;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.leave_applied_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        sessionManagement = new SessionManagement(getActivity());
        leave_days_list = view.findViewById(R.id.leave_days_list);
        onLeaveDaysListApi();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void generateDataList(List<LeaveDaysModel> photoList) {
        leaveTypeAdapter = new LeaveDaysAdapter(getContext(), photoList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        leave_days_list.setLayoutManager(layoutManager);
        leave_days_list.setAdapter(leaveTypeAdapter);
    }

    private void onLeaveDaysListApi() {
        if(NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
            LoadingDialog progressDialogLoading = new LoadingDialog();
            progressDialogLoading.showLoadingDialog(getActivity());
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            Call<List<LeaveDaysModel>> call = mAPIService.getLeaveApprovalDays();
            call.enqueue(new Callback<List<LeaveDaysModel>>() {
                @Override
                public void onResponse(Call<List<LeaveDaysModel>> call, Response<List<LeaveDaysModel>> response) {
                    try {
                        if (response.isSuccessful()) {
                            progressDialogLoading.hideDialog();
                            List<LeaveDaysModel> respoinseList = response.body();
                            if (respoinseList.size() > 0 && respoinseList.get(0).condition) {
                                generateDataList(respoinseList);
                            } else {
                                Toast.makeText(getActivity(), respoinseList.size() > 0 ? "Result not found" : "", Toast.LENGTH_LONG).show();
                                progressDialogLoading.hideDialog();
                            }
                        } else {
                            Toast.makeText(getActivity(), response.message(), Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        progressDialogLoading.hideDialog();
                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "list", getActivity());
                    } finally {
                        progressDialogLoading.hideDialog();
                    }
                }

                @Override
                public void onFailure(Call<List<LeaveDaysModel>> call, Throwable t) {
                    progressDialogLoading.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "leave approval_list", getActivity());
                }
            });
        }
        else {
            Toast.makeText(getActivity(),"Please wait for internet connection",Toast.LENGTH_SHORT).show();
        }
    }
}
