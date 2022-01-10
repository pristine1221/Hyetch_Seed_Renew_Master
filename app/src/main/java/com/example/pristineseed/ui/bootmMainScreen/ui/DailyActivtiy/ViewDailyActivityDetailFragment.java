package com.example.pristineseed.ui.bootmMainScreen.ui.DailyActivtiy;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pristineseed.GlobalNotification.NetworkUtil;
import com.example.pristineseed.R;
import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.global.ApiRequestFailure;

import com.example.pristineseed.global.LoadingDialog;
import com.example.pristineseed.global.StaticMethods;
import com.example.pristineseed.model.DailyActivity_Model.DailyActivityModel;
import com.example.pristineseed.model.DailyActivity_Model.DailyActivityResponseModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.adapter.activity_adapter.DailyActivityListAdapter;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewDailyActivityDetailFragment extends Fragment {
    private RecyclerView dailyActivityList;
    private DailyActivityListAdapter DailyActivityAdapter;
    private SearchView searchView;
    private TextInputEditText et_attendance_date;
    private ImageView cross;
    private SessionManagement sessionManagement;
    public static ViewDailyActivityDetailFragment newInstance() {
        return new ViewDailyActivityDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.daily_activity_detail_fragment, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        sessionManagement = new SessionManagement(getActivity());
        dailyActivityList = view.findViewById(R.id.dailyActivityList);
       // searchView = view.findViewById(R.id.searchView);
       // cross = view.findViewById(R.id.cross);
        et_attendance_date = view.findViewById(R.id.et_attendance_date);
        dailyActivityList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        dailyActivityList.setItemAnimator(new DefaultItemAnimator());
        dailyActivityList.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        if (NetworkUtil.getConnectivityStatusBoolean(getContext())) {
            Sync_DailyActivitDatawithServer();
        } else {
            return;
        }
    }
    private void bindDataWithAdapter(List<DailyActivityResponseModel> serverResponseList) {
        DailyActivityAdapter = new DailyActivityListAdapter(getActivity(), serverResponseList, pos -> {
            OrderDetailItem(serverResponseList.get(pos).d_line,serverResponseList.get(pos));
        });

        dailyActivityList.setAdapter(DailyActivityAdapter);
    }

    void OrderDetailItem(List<DailyActivityResponseModel.DailyLineModel> selectedOrderLine, DailyActivityResponseModel header_model) {
        DailyActivityModel passData = new DailyActivityModel();
        passData.contact = header_model.contact_no;
        passData.contact1 = header_model.contact_no1;
        passData.payment_collected = header_model.payment_collected;
        passData.order_collected = header_model.order_collected;
        passData.addlines = new ArrayList<>();
        for (int i = 0; i < selectedOrderLine.size(); i++) {
            DailyActivityModel.DailyActivityLines lines = passData.new DailyActivityLines();
            lines.farmer_name = selectedOrderLine.get(i).farmer_name;
            lines.district = selectedOrderLine.get(i).district;
            lines.village = selectedOrderLine.get(i).village;

            lines.ajeet_crop_and_verity = selectedOrderLine.get(i).ajeet_crop_and_verity;
            lines.ajeet_crop_age = selectedOrderLine.get(i).ajeet_crop_age;
            lines.ajeet_fruits_per = selectedOrderLine.get(i).ajeet_fruits_per;
            lines.ajeet_pest = selectedOrderLine.get(i).ajeet_pest;
            lines.ajeet_disease = selectedOrderLine.get(i).ajeet_disease;

            lines.check_crop_and_variety = selectedOrderLine.get(i).check_crop_and_verity;
            lines.check_crop_age = selectedOrderLine.get(i).check_crop_age;
            lines.check_fruits_per = selectedOrderLine.get(i).check_fruits_per;
            lines.check_pest = selectedOrderLine.get(i).check_pest;
            lines.check_disease = selectedOrderLine.get(i).check_disease;
            passData.addlines.add(lines);
        }
        Bundle bundle = new Bundle();
        bundle.putString("dataPass", new Gson().toJson(passData));
        bundle.putBoolean("hideAllActions", true);
        DailyActivityFragment dailyActivityFragment = new DailyActivityFragment();
        dailyActivityFragment.setArguments(bundle);
        StaticMethods.loadFragmentsWithBackStack(getActivity(), dailyActivityFragment, "daily_activity_fragment");

    }

    public int getImage(String imageName) {
        int drawableResourceId = this.getResources().getIdentifier(imageName, "drawable", getActivity().getPackageName());
        return drawableResourceId;
    }

    void Sync_DailyActivitDatawithServer() {
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        LoadingDialog progressDialogLoading = new LoadingDialog();
        progressDialogLoading.showLoadingDialog(getActivity());
        Call<List<DailyActivityResponseModel>> call = mAPIService.getDailyActivityDetail(sessionManagement.getUserEmail());
        call.enqueue(new Callback<List<DailyActivityResponseModel>>() {
            @Override
            public void onResponse(Call<List<DailyActivityResponseModel>> call, Response<List<DailyActivityResponseModel>> response) {
                try {
                    if (response.isSuccessful()) {
                        progressDialogLoading.hideDialog();
                        List<DailyActivityResponseModel> serverResponseList = response.body();
                        if (serverResponseList!=null && serverResponseList.size() > 0 && serverResponseList.get(0).condition) {
                            Toast.makeText(getActivity(), "fetch data sucessfully!", Toast.LENGTH_SHORT).show();
                            bindDataWithAdapter(serverResponseList);
                        }
                    }
                    else {
                        progressDialogLoading.hideDialog();
                        Toast.makeText(getActivity(), "Error code !", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    progressDialogLoading.hideDialog();
                    e.printStackTrace();
                    Log.e("daily_activity Error", e.getMessage());
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "daily_activity_detail", getActivity());
                }
              }

            @Override
            public void onFailure(Call<List<DailyActivityResponseModel>> call, Throwable t) {
                progressDialogLoading.hideDialog();
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "daily_activity_detail", getActivity());
            }
        });
    }
}
