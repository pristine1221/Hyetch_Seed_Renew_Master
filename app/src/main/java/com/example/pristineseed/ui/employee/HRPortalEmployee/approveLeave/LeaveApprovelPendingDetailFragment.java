package com.example.pristineseed.ui.employee.HRPortalEmployee.approveLeave;

import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pristineseed.GlobalNotification.NetworkUtil;
import com.example.pristineseed.R;
import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.global.ApiRequestFailure;
import com.example.pristineseed.global.LoadingDialog;
import com.example.pristineseed.model.LeaveApplicationModel.LeavePendingForApprovalModel;
import com.example.pristineseed.model.travel.travel_view_header.TravelHeaderModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.adapter.LeaveApplicationAdapter.LeavePendingListAdapter;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeaveApprovelPendingDetailFragment extends Fragment {
   private SessionManagement sessionManagement;
   private RecyclerView approval_list_recycler;
    private LeavePendingListAdapter adapter=null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.leave_apporvals_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        sessionManagement = new SessionManagement(getActivity());
        approval_list_recycler = view.findViewById(R.id.approval_list_recycler);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        onLeavePendingForApproval();
        super.onResume();
    }

    private void generateDataList(List<LeavePendingForApprovalModel> photoList) {
        adapter = new LeavePendingListAdapter(getContext(), photoList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        approval_list_recycler.setLayoutManager(layoutManager);
        approval_list_recycler.setAdapter(adapter);
    }
    private void onLeavePendingForApproval() {
        if(NetworkUtil.getConnectivityStatusBoolean(getActivity())){
            LoadingDialog progressDialogLoading = new LoadingDialog();
            progressDialogLoading.showLoadingDialog(getActivity());
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            Call<List<LeavePendingForApprovalModel>> call = mAPIService.getLeavePendingForApproval(sessionManagement.getUserName()); //changed by Abhit Chauhan
            call.enqueue(new Callback<List<LeavePendingForApprovalModel>>() {
                @Override
                public void onResponse(Call<List<LeavePendingForApprovalModel>> call, Response<List<LeavePendingForApprovalModel>> response) {
                        if (response.isSuccessful()) {
                            progressDialogLoading.hideDialog();
                            List<LeavePendingForApprovalModel> respoinseList = response.body();
                                if (respoinseList!=null && respoinseList.size() > 0 && respoinseList.get(0).condition) {
                                    generateDataList(respoinseList);
                                } else {
                                    Toast.makeText(getActivity(), respoinseList.size() > 0 ? "Result not found" : "", Toast.LENGTH_LONG).show();
                                    progressDialogLoading.hideDialog();
                                }
                        } else {
                            progressDialogLoading.hideDialog();
                            Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                }
                @Override
                public void onFailure(Call<List<LeavePendingForApprovalModel>> call, Throwable t) {
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "list", getActivity());
                }
            });
        }else {
            Toast.makeText(getActivity(),"Please wait for internet connection",Toast.LENGTH_SHORT).show();
        }
    }

}
