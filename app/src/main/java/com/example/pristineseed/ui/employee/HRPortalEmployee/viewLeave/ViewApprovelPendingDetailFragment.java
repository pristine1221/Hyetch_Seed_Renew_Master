package com.example.pristineseed.ui.employee.HRPortalEmployee.viewLeave;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.pristineseed.model.LeaveApplicationModel.LevaeTypeModel;
import com.example.pristineseed.model.travel.travel_view_header.TravelHeaderModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.adapter.LeaveApplicationAdapter.LeaveTypeAdapter;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewApprovelPendingDetailFragment extends Fragment {
    private LeaveTypeAdapter leaveTypeAdapter;
    private RecyclerView leave_type_list;
    private SessionManagement sessionManagement;
    private List<LevaeTypeModel> list = new ArrayList<>();
    private int id;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_leave_type_fragment, container, false);
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
        leave_type_list = view.findViewById(R.id.leave_type_list);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume()
    {
        onLeaveTypeListApi();
        super.onResume();
    }

    private void generateDataList(List<LevaeTypeModel> photoList) {
        leaveTypeAdapter = new LeaveTypeAdapter(getContext(), photoList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        leave_type_list.setLayoutManager(layoutManager);
        leave_type_list.setAdapter(leaveTypeAdapter);
    }


    /**
     * View Type
     * List View For Leave Type
     **/
    private void onLeaveTypeListApi() {
        if(NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
            LoadingDialog progressDialogLoading = new LoadingDialog();
            progressDialogLoading.showLoadingDialog(getActivity());
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();

            Call<List<LevaeTypeModel>> call = mAPIService.getLeaveType();
            call.enqueue(new Callback<List<LevaeTypeModel>>() {
                @Override
                public void onResponse(Call<List<LevaeTypeModel>> call, Response<List<LevaeTypeModel>> response) {
                    try {
                        if (response.isSuccessful()) {
                            progressDialogLoading.hideDialog();
                            List<LevaeTypeModel> respoinseList = response.body();
                            for (int i = 0; i < respoinseList.size(); i++) {
                                if (respoinseList!=null && respoinseList.size() > 0 && respoinseList.get(i).condition) {
                                    generateDataList(respoinseList);
                                } else {
                                    Toast.makeText(getActivity(), list.size() > 0 ? "Result not found" : respoinseList.get(0).message, Toast.LENGTH_LONG).show();

                                }
                            }

                        } else {
                        Toast.makeText(getActivity(),response.message(),Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        progressDialogLoading.hideDialog();
                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "view_leave_list", getActivity());
                    } finally {
                        progressDialogLoading.hideDialog();
                    }
                }

                @Override
                public void onFailure(Call<List<LevaeTypeModel>> call, Throwable t) {
                    progressDialogLoading.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "view_leave_list", getActivity());
                }
            });

        }else {
            Toast.makeText(getActivity(),"Please wait for internet connection",Toast.LENGTH_SHORT).show();
        }

    }

}
