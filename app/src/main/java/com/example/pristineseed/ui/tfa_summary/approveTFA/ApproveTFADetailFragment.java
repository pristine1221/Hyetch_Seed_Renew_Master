package com.example.pristineseed.ui.tfa_summary.approveTFA;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pristineseed.GlobalNotification.NetworkUtil;
import com.example.pristineseed.R;
import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.global.ApiRequestFailure;
import com.example.pristineseed.global.LoadingDialog;
import com.example.pristineseed.model.tfa.TFAHeaderModel;
import com.example.pristineseed.model.tfa.TFAResponseModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApproveTFADetailFragment extends Fragment {

    private ListView tfa_list_;
    private SessionManagement sessionManagement;
    private TFAApproveListAdapter event_Adapter;
    private List<TFAHeaderModel> updateTfaList = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_tfa_approve_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManagement = new SessionManagement(getActivity());
        tfa_list_ = view.findViewById(R.id.tfa_list_);

    }

    private void getApproveList() throws Exception {
        LoadingDialog loadingDialog = new LoadingDialog();
        loadingDialog.showLoadingDialog(getActivity());
        NetworkInterface mApiService = ApiUtils.getPristineAPIService();
        Call<List<TFAHeaderModel>> approve = mApiService.getTfaApproveList(sessionManagement.getUserEmail());
        approve.enqueue(new Callback<List<TFAHeaderModel>>() {
            @Override
            public void onResponse(Call<List<TFAHeaderModel>> call, Response<List<TFAHeaderModel>> response) {
                try {
                    loadingDialog.hideDialog();
                    if (response.isSuccessful()) {
                        List<TFAHeaderModel> TFAHeaderModelList = response.body();
                        if (TFAHeaderModelList.size() > 0 && TFAHeaderModelList.get(0).condition) {
                            updateTfaList = TFAHeaderModelList;
                            bindUiListData();
                            Toast.makeText(getActivity(), "Updated Successfully!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), TFAHeaderModelList.size() > 0 ? TFAHeaderModelList.get(0).message : "Record not found.", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    loadingDialog.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "insert_TFA_Approve", getActivity());
                } finally {
                    loadingDialog.hideDialog();
                }
            }

            @Override
            public void onFailure(Call<List<TFAHeaderModel>> call, Throwable t) {
                loadingDialog.hideDialog();
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "insert_TFA_Approve", getActivity());
            }
        });
    }


    private void bindUiListData() {
        event_Adapter = new TFAApproveListAdapter(getActivity(), updateTfaList, (TFAHeaderModel selectedeventData, int position, String flag) -> {
            approveRejectOrder(selectedeventData, position, flag);
        });
        tfa_list_.setAdapter(event_Adapter);
    }

    private void approveRejectOrder(TFAHeaderModel selectedeventData, int position, String flag) {
        if (flag.equalsIgnoreCase("approve")) {
            new MaterialAlertDialogBuilder(getActivity())
                    .setTitle("Confirm " + selectedeventData.tfa_code)
                    .setMessage("Do you really want to approve?")
                    .setIcon(R.drawable.approve_order_icon)
                    .setPositiveButton("Confirm", (dialogInterface, i) -> {
                        try {
                            JsonObject jsonObject = new JsonObject();
                            jsonObject.addProperty("tfa_code", selectedeventData.tfa_code);
                            jsonObject.addProperty("email_id", sessionManagement.getUserEmail());
                            jsonObject.addProperty("approver_id", sessionManagement.getApprover_id());
                            jsonObject.addProperty("approver_status", selectedeventData.status.equalsIgnoreCase("Pending") ? "Approve" : "Approve");
                            call_approve_hit(jsonObject, position);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    })
                    .setNegativeButton("Cancel", (dialogInterface, i) -> {
                    })
                    .show();
        }
        else if (flag.equalsIgnoreCase("reject")) {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
            builder.setTitle("Confirm " + selectedeventData.tfa_code);
            builder.setIcon(R.drawable.approve_order_icon);
            LinearLayout parentVertical = new LinearLayout(getActivity());
            LinearLayout.LayoutParams parentVerticalParams = new LinearLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
            parentVertical.setLayoutParams(parentVerticalParams);
            parentVertical.setOrientation(LinearLayout.VERTICAL);
            TextView messageSession = new TextView(getActivity());
            messageSession.setText("Do you really want to reject?");
            messageSession.setTextColor(Color.BLACK);
            TextView enterReson_tv = new TextView(getActivity());
            enterReson_tv.setText("Enter Reason");
            enterReson_tv.setTextColor(getResources().getColor(R.color.colorAccent));
            enterReson_tv.setPadding(0, 10, 0, 0);
            EditText reason = new EditText(getActivity());
            parentVertical.addView(messageSession);
            parentVertical.addView(enterReson_tv);
            parentVertical.addView(reason);
            parentVertical.setPadding(40, 20, 40, 10);
            builder.setView(parentVertical);
            builder.setPositiveButton("Confirm", (dialogInterface, i) -> {
                try {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("tfa_code", selectedeventData.tfa_code);
                    jsonObject.addProperty("email_id", sessionManagement.getUserEmail());
                    jsonObject.addProperty("approver_id", sessionManagement.getApprover_id());
                    jsonObject.addProperty("approver_status", selectedeventData.status.equalsIgnoreCase("Pending") ? "Reject" : "Reject");
                    jsonObject.addProperty("reason", reason.getText().toString());
                    call_reject_hit(jsonObject, position);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            builder.setNegativeButton("Cancel", null);
            builder.show();
        }
    }

    private void call_reject_hit(JsonObject jsonObject, int position) {
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();

        Call<List<TFAResponseModel>> call = mAPIService.TfaApproveRejectDetails(jsonObject);
        call.enqueue(new Callback<List<TFAResponseModel>>() {
            @Override
            public void onResponse(Call<List<TFAResponseModel>> call, Response<List<TFAResponseModel>> response) {
                try {
                    if (response.isSuccessful()) {
                        List<TFAResponseModel> approveRejectModelList = response.body();
                        if (approveRejectModelList != null && approveRejectModelList.size() > 0 && approveRejectModelList.get(0).condition) {
                            updateTfaList.remove(position);
                            event_Adapter.notifyDataSetChanged();
                            Toast.makeText(getActivity(), approveRejectModelList.get(0).message, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), approveRejectModelList.size() > 0 ? approveRejectModelList.get(0).message : "Something went wrong !.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "approve_reject_tfa", getActivity());
                }
            }

            @Override
            public void onFailure(Call<List<TFAResponseModel>> call, Throwable t) {
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "approve_reject_tfa", getActivity());
            }
        });
    }


    private void call_approve_hit(JsonObject jsonObject, int position) {
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<List<TFAResponseModel>> call = mAPIService.TfaApproveRejectDetails(jsonObject);
        call.enqueue(new Callback<List<TFAResponseModel>>() {
            @Override
            public void onResponse(Call<List<TFAResponseModel>> call, Response<List<TFAResponseModel>> response) {
                try {
                    if (response.isSuccessful()) {
                        List<TFAResponseModel> approveRejectModelList = response.body();
                        if (approveRejectModelList.size() > 0 && approveRejectModelList.get(0).condition) {
                            getApproveList();
                            Toast.makeText(getActivity(), approveRejectModelList.get(0).message, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), approveRejectModelList.size() > 0 ? approveRejectModelList.get(0).message : "Something went wrong !.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "approve_reject_tfa", getActivity());
                }
            }

            @Override
            public void onFailure(Call<List<TFAResponseModel>> call, Throwable t) {
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "approve_reject_tfa", getActivity());
            }
        });
    }

    @Override
    public void onResume() {
        if (NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
            try {
                getApproveList();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("fragment_resume", "fragment_resume");
            }
        } else {
            return;
        }
        super.onResume();
    }
}
