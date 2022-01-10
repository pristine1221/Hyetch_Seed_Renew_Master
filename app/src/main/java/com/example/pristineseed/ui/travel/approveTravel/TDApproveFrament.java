package com.example.pristineseed.ui.travel.approveTravel;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
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
import com.example.pristineseed.model.travel.ta_da_model.SyncTravelDetailModel;
import com.example.pristineseed.model.travel.travel_view_header.EventCreateResponseModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TDApproveFrament extends Fragment {

    public static TDApproveFrament newInstance() {
        return new TDApproveFrament();
    }

    private RecyclerView travel_List;
    private TravelListAdapter event_Adapter;
    private SessionManagement sessionManagement;
    private List<EventCreateResponseModel> list = new ArrayList<>();
    private List<SyncTravelDetailModel> updateOrderDetailList = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_travel_detail_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManagement = new SessionManagement(getActivity());
        travel_List = view.findViewById(R.id.travel_List);
        travel_List.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        travel_List.setItemAnimator(new DefaultItemAnimator());
        travel_List.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    private void getApproveStatus() {
        LoadingDialog progressDialogLoading = new LoadingDialog();
        progressDialogLoading.showLoadingDialog(getActivity());
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<List<SyncTravelDetailModel>> call = mAPIService.getTravelUpdateApproveStatus("getTravelApproveExpense", sessionManagement.getUserEmail());
        call.enqueue(new Callback<List<SyncTravelDetailModel>>() {
            @Override
            public void onResponse(Call<List<SyncTravelDetailModel>> call, Response<List<SyncTravelDetailModel>> response) {
                try {
                    if (response.isSuccessful()) {
                        List<SyncTravelDetailModel> responseList = response.body();
                        if (responseList != null && responseList.size() > 0 && responseList.get(0).condition) {
                            updateOrderDetailList = responseList;
                            progressDialogLoading.hideDialog();
                            Toast.makeText(getActivity(), "Updated Successfully!", Toast.LENGTH_SHORT).show();
                            bindUiListData();
                        } else {
                            Toast.makeText(getContext(), responseList.size() > 0 ? responseList.get(0).message : "Record Not inserted.", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    progressDialogLoading.hideDialog();
                    //   Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "insert_travel_header", getActivity());
                }
            }

            @Override
            public void onFailure(Call<List<SyncTravelDetailModel>> call, Throwable t) {
                progressDialogLoading.hideDialog();
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "insert_travel_header", getActivity());
            }
        });

    }

    private void bindUiListData() {
        event_Adapter = new TravelListAdapter(getActivity(), updateOrderDetailList, (selectedeventData, position, flag) -> {
            approveRejectTravel(selectedeventData, position, flag);
        });
        travel_List.setAdapter(event_Adapter);
    }

    void approveRejectTravel(SyncTravelDetailModel selectedeventData, int position, String flag) {
        if (flag.equalsIgnoreCase("approve")) {
            if (selectedeventData.STATUS.equalsIgnoreCase("PENDING")) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
                builder.setTitle("Confirm " + selectedeventData.travelcode);
                builder.setIcon(R.drawable.approve_order_icon);
                LinearLayout parentVertical = new LinearLayout(getActivity());
                LinearLayout.LayoutParams parentVerticalParams = new LinearLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
                parentVertical.setLayoutParams(parentVerticalParams);
                parentVertical.setOrientation(LinearLayout.VERTICAL);
                TextView messageSession = new TextView(getActivity());
                Typeface typeface1 = ResourcesCompat.getFont(getActivity(), R.font.product_sans_regular);
                messageSession.setText("Do you really want to approve?");
                messageSession.setTextColor(Color.BLACK);
                TextView enterReson_tv = new TextView(getActivity());
                enterReson_tv.setText("Enter Approve Budget");
                enterReson_tv.setTextColor(getResources().getColor(R.color.colorPrimary));
                enterReson_tv.setPadding(0, 10, 0, 0);
                enterReson_tv.setTypeface(typeface1);
                EditText approvebudget = new EditText(getActivity());
                approvebudget.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL); //for decimal numbers
                approvebudget.setTypeface(typeface1);
                parentVertical.addView(messageSession);
                parentVertical.addView(enterReson_tv);
                parentVertical.addView(approvebudget);

                TextView tv_advanced_budgt = new TextView(getActivity());
                tv_advanced_budgt.setText("Enter Advance Budget.");
                tv_advanced_budgt.setTextColor(getResources().getColor(R.color.colorPrimary));
                tv_advanced_budgt.setPadding(0, 10, 0, 0);
                EditText advancedBdgt = new EditText(getActivity());

                advancedBdgt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                tv_advanced_budgt.setTypeface(typeface1);
                advancedBdgt.setTypeface(typeface1);
                parentVertical.addView(tv_advanced_budgt);
                parentVertical.addView(advancedBdgt);

                parentVertical.setPadding(40, 20, 40, 10);
                builder.setView(parentVertical);
                builder.setPositiveButton("Confirm", (dialogInterface, i) -> {

                    try {
                        float budget_needed = Float.parseFloat(selectedeventData.expense_budget);
                        float approvebudget_by_approver = approvebudget.getText().toString().equalsIgnoreCase("") ? 0 : Float.parseFloat(approvebudget.getText().toString());
                        float Advancebudget_by_approver = advancedBdgt.getText().toString().equalsIgnoreCase("") ? 0 : Float.parseFloat(advancedBdgt.getText().toString());

                        if (approvebudget_by_approver > budget_needed) {
                            Toast.makeText(getContext(), "Please Enter Approve Budget <" + selectedeventData.expense_budget, Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (Advancebudget_by_approver > approvebudget_by_approver) {
                            Toast.makeText(getContext(), "Please Enter Advance Budget <" + approvebudget.getText().toString(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        JsonObject postedjson = new JsonObject();
                        postedjson.addProperty("email", sessionManagement.getUserEmail());
                        postedjson.addProperty("travel_code", selectedeventData.travelcode);
                        postedjson.addProperty("approve_budget", approvebudget.getText().toString());
                        postedjson.addProperty("advance_budget", advancedBdgt.getText().toString());
                        postedjson.addProperty("travel_status", selectedeventData.STATUS.equalsIgnoreCase("PENDING") ? "CREATE APPROVED" : "APPROVED");
                        postedjson.addProperty("reson", selectedeventData.STATUS.equalsIgnoreCase("PENDING") ? "CREATE APPROVED" : "APPROVED");
                        //call Approve reject______api here
                        callApproveRejectHit(postedjson, position);


                    } catch (Exception e) {
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.show();
            } else {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
                builder.setTitle("Confirm " + selectedeventData.travelcode);
                builder.setMessage("Do you really want to approve?");
                builder.setIcon(R.drawable.approve_order_icon);

                builder.setPositiveButton("Confirm", (dialogInterface, i) -> {
                    try {
                        JsonObject postedjson = new JsonObject();
                        postedjson.addProperty("email", sessionManagement.getUserEmail());
                        postedjson.addProperty("travel_code", selectedeventData.travelcode);
                        postedjson.addProperty("approve_budget", "0");
                        postedjson.addProperty("advance_budget", "0");
                        postedjson.addProperty("travel_status", selectedeventData.STATUS.equalsIgnoreCase("PENDING") ? "CREATE APPROVED" : "APPROVED");
                        postedjson.addProperty("reson", selectedeventData.STATUS.equalsIgnoreCase("PENDING") ? "CREATE APPROVED" : "APPROVED");

                        callApproveRejectHit(postedjson, position);
                    } catch (Exception e) {
                    }
                });
                builder.setNegativeButton("Cancel", (dialogInterface, i) -> {

                });
                builder.show();

            }
        } else if (flag.equalsIgnoreCase("reject")) {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
            builder.setTitle("Confirm " + selectedeventData.travelcode);
            builder.setIcon(R.drawable.approve_order_reject_icon);
            LinearLayout parentVertical = new LinearLayout(getActivity());
            LinearLayout.LayoutParams parentVerticalParams = new LinearLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
            parentVertical.setLayoutParams(parentVerticalParams);
            parentVertical.setOrientation(LinearLayout.VERTICAL);
            TextView messageSession = new TextView(getActivity());
            messageSession.setText("Do you really want to reject?");
            messageSession.setTextColor(Color.BLACK);
            TextView enterReson_tv = new TextView(getActivity());
            enterReson_tv.setText("Enter Reason");
            enterReson_tv.setTextColor(getResources().getColor(R.color.colorPrimary));
            enterReson_tv.setPadding(0, 10, 0, 0);
            EditText reason = new EditText(getActivity());
            parentVertical.addView(messageSession);
            parentVertical.addView(enterReson_tv);
            parentVertical.addView(reason);
            parentVertical.setPadding(40, 20, 40, 10);
            builder.setView(parentVertical);
            builder.setPositiveButton("Confirm", (dialogInterface, i) -> {

                try {
                    JsonObject postedjson = new JsonObject();
                    postedjson.addProperty("email", sessionManagement.getUserEmail());
                    postedjson.addProperty("travel_code", selectedeventData.travelcode);
                    postedjson.addProperty("approve_budget", "0");
                    postedjson.addProperty("advance_budget", "0");
                    postedjson.addProperty("travel_status", selectedeventData.STATUS.equalsIgnoreCase("PENDING") ? "CREATE REJECTED" : "REJECTED");
                    postedjson.addProperty("reson", reason.getText().toString());
                    callApproveRejectHit(postedjson, position);
                } catch (Exception e) {
                }
            });
            builder.setNegativeButton("Cancel", null);
            builder.show();
        }
    }


    private void callApproveRejectHit(JsonObject postedjson, int position) {
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<List<EventCreateResponseModel>> call = mAPIService.approve_reject_order(postedjson);
        call.enqueue(new Callback<List<EventCreateResponseModel>>() {
            @Override
            public void onResponse(Call<List<EventCreateResponseModel>> call, Response<List<EventCreateResponseModel>> response) {
                try {
                    if (response.isSuccessful()) {
                        List<EventCreateResponseModel> responseList = response.body();
                        if (responseList.size() > 0 && responseList.get(0).condition) {
                            updateOrderDetailList.remove(position);
                            event_Adapter.notifyDataSetChanged();
                            Toast.makeText(getActivity(), responseList.get(0).message, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), responseList.size() > 0 ? responseList.get(0).message : "Something went wrong !.", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    //   Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "approve_reject_order", getActivity());
                }
            }

            @Override
            public void onFailure(Call<List<EventCreateResponseModel>> call, Throwable t) {
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "approve_reject_order", getActivity());
            }
        });

    }

    @Override
    public void onResume() {
        if (NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
            getApproveStatus();
            Log.e("fragment_resume", "fragment_resume");
        } else {
            Toast.makeText(getActivity(), "Please wait for internet connection", Toast.LENGTH_SHORT).show();
        }
        super.onResume();
    }

}
