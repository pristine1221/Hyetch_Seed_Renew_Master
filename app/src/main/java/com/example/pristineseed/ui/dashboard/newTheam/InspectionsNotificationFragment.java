package com.example.pristineseed.ui.dashboard.newTheam;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pristineseed.R;
import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.firebase_notification_service.InspectionNotificationModel;
import com.example.pristineseed.global.ApiRequestFailure;
import com.example.pristineseed.global.CustomDatePicker;
import com.example.pristineseed.global.DateTimeUtilsCustome;
import com.example.pristineseed.global.LoadingDialog;
import com.example.pristineseed.model.login.LoginModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.adapter.NotificationAdapter;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InspectionsNotificationFragment extends Fragment {

    private ImageView back_press_layout;
    private ImageView filter_date;
    private SessionManagement sessionManagement;
    private RecyclerView notification_list;
private TextView data_nt_found;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.notification_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        sessionManagement=new SessionManagement(getActivity());

             back_press_layout.setOnTouchListener((v, event) -> {
                getActivity().getSupportFragmentManager().popBackStack();
                 return true;
                });

        filter_date.setOnClickListener(v -> {

            LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
            final View popupView = layoutInflater.inflate(R.layout.pop_up_menu_layout, null);

             PopupWindow popupWindow = new PopupWindow(
                    popupView,
                  600,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            popupWindow.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.dark_gray_backgraund));
            popupWindow.setElevation(43);
            popupWindow.setOutsideTouchable(true);

           TextInputEditText ed_select_date= popupView.findViewById(R.id.ed_select_date);
           Button filter_btn=popupView.findViewById(R.id.filter_btn);
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    popupWindow.dismiss();
                }
            });
            popupWindow.showAsDropDown(v);
            ed_select_date.setOnTouchListener((v1, event) -> {
                try {
                    new CustomDatePicker(getActivity()).showDatePickerDialog(ed_select_date);
                } catch (Exception e) {
                    Log.e("exc", e.getMessage());
                }
                return true;
            });

            filter_btn.setOnClickListener(v1 -> {
                if(ed_select_date.getText().toString().trim().equalsIgnoreCase("")){
                    Toast.makeText(getActivity(), "Please select date first .", Toast.LENGTH_SHORT).show();
                }
                else {
                    getNotification(DateTimeUtilsCustome.splitDateInYYYMMDD(ed_select_date.getText().toString().trim()));
                }
            });
        });
        getNotification("");

    }

    private void initView(View view) {
        back_press_layout= view.findViewById(R.id.back_press_layout);
        filter_date=view.findViewById(R.id.filter_date);
        notification_list=view.findViewById(R.id.notification_list);
        data_nt_found=view.findViewById(R.id.data_nt_found);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        notification_list.setLayoutManager(linearLayoutManager);
    }



    private void getNotification(String date){
        LoadingDialog loadingDialog=new LoadingDialog();
        loadingDialog.showLoadingDialog(getActivity());
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<List<InspectionNotificationModel>> call = mAPIService.getLocationNo(date,sessionManagement.getUserEmail());
        call.enqueue(new Callback<List<InspectionNotificationModel>>() {
            @Override
            public void onResponse(Call<List<InspectionNotificationModel>> call, Response<List<InspectionNotificationModel>> response) {
                    if (response.isSuccessful()) {
                        loadingDialog.hideDialog();
                        List<InspectionNotificationModel> getResponse = response.body();
                        if (getResponse!=null && getResponse.size()>0 && getResponse.get(0).condition) {
                            NotificationAdapter notificationAdapter=new NotificationAdapter(getActivity(),getResponse);
                            notification_list.setAdapter(notificationAdapter);
                        }
                    }
                    else {
                        loadingDialog.hideDialog();
                        Toast.makeText(getActivity(), "No data found !", Toast.LENGTH_SHORT).show();
                    }
            }
            @Override
            public void onFailure(Call<List<InspectionNotificationModel>> call,Throwable t) {
                loadingDialog.hideDialog();
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "Notification",  getActivity());
            }
        });

    }



}
