package com.example.pristineseed.ui.vendor.POG;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.example.pristineseed.model.POG.POGModel;
import com.example.pristineseed.model.ResponseModel;
import com.example.pristineseed.model.fs_return_harvesting.FsRetrunAapprovalModel;
import com.example.pristineseed.model.fs_return_harvesting.FsReturnResponseModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.adapter.header_line.PogApproveAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class POGApprovalFragment extends Fragment implements PogApproveAdapter.OnListApproveRejectClickListner {

    private SessionManagement sessionManagement;
    private RecyclerView pog_approve_list;
    private List<POGModel>pogModelList;
    private  PogApproveAdapter pogApproveAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.pog_approve_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManagement=new SessionManagement(getActivity());
        pog_approve_list=view.findViewById(R.id.pog_approve_list);
        pogModelList=new ArrayList<>();
        sessionManagement=new SessionManagement(getActivity());

        pog_approve_list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        pog_approve_list.setItemAnimator(new DefaultItemAnimator());
        pog_approve_list.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }


    @Override
    public void onResume() {
        super.onResume();
        getPogAproveList();
    }

    private void getPogAproveList() {
        if (NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
            LoadingDialog progressDialog = new LoadingDialog();
            progressDialog.showLoadingDialog(getActivity());
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            Call<List<POGModel>> call = mAPIService.getPogApprovalList(sessionManagement.getUserEmail());
            call.enqueue(new Callback<List<POGModel>>() {
                @Override
                public void onResponse(Call<List<POGModel>> call, Response<List<POGModel>> response) {
                    try {
                        if (response.isSuccessful()) {
                            progressDialog.hideDialog();
                            List<POGModel> update_approval_list = response.body();
                            if (update_approval_list != null && update_approval_list.size() > 0 && update_approval_list.get(0).condition) {
                                pogModelList=update_approval_list;
                                bindDatawithAdapter(pogModelList);
                                Toast.makeText(getActivity(), "Data fetch successful !", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), update_approval_list.size() > 0 ? "No data found" : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            progressDialog.hideDialog();
                            Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        progressDialog.hideDialog();
                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "pog_approval_list", getActivity());
                    }
                }
                @Override
                public void onFailure(Call<List<POGModel>> call, Throwable t) {
                    progressDialog.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "pog_approval_list", getActivity());
                }
            });
        }
        else {
            Toast.makeText(getActivity(),"Please wait for online connection",Toast.LENGTH_SHORT).show();
        }
    }

    private void bindDatawithAdapter(List<POGModel> update_approval_list) {
         pogApproveAdapter=new PogApproveAdapter(getActivity(),update_approval_list);
        pog_approve_list.setAdapter(pogApproveAdapter);
        pogApproveAdapter.setOnListClick(this);
    }

    @Override
    public void onBtnCLick(String flag, int pos) {
        if(pogModelList!=null && pogModelList.size()>0) {
            POGModel pogModel = pogModelList.get(pos);
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View PopupView = inflater.inflate(R.layout.approve_reject_popup, null);
            Dialog dialog = new Dialog(getActivity(), R.style.CustomDialog);
            Window window = dialog.getWindow();
            window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            dialog.getWindow().getAttributes().windowAnimations = R.style.AppTheme_FullScreenDialog;
            dialog.setContentView(PopupView);
            dialog.setCancelable(false);
            dialog.show();
            TextView title_text = PopupView.findViewById(R.id.title_text);
            ImageView cancel_btn = PopupView.findViewById(R.id.close_pop_up);
            TextInputLayout reason_layout=PopupView.findViewById(R.id.reason_layout);
            TextInputEditText reason=PopupView.findViewById(R.id.ed_reason);
            cancel_btn.setOnClickListener(v -> {
                dialog.dismiss();
            });
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            String s1 = "Do you really want to Approve/Reject this\n";
            String s2 = " (" + pogModel.pog_code + ") ";
            String s3 = " Item ?";
            SpannableString spannableString = new SpannableString(s1);
            spannableStringBuilder.append(spannableString);

            SpannableString spannableString1 = new SpannableString(s2);
            spannableString1.setSpan(new ForegroundColorSpan(Color.BLACK), 0, s2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableStringBuilder.append(spannableString1);

            SpannableString spannableString2 = new SpannableString(s3);
            spannableStringBuilder.append(spannableString2);

            title_text.setText(spannableStringBuilder);

            MaterialButton approve_btn = PopupView.findViewById(R.id.approve_btn);
            MaterialButton reject_btn = PopupView.findViewById(R.id.reject_btn);

            approve_btn.setOnClickListener(v -> {
                reason_layout.setVisibility(View.GONE);
                if(reason.getText().toString().trim().equalsIgnoreCase("")) {
                    updateApproveRejectStatus(pogModel.pog_code, "Approve", dialog, pos, "");
                }
            });

            reject_btn.setOnClickListener(v -> {
                reason_layout.setVisibility(View.VISIBLE);
                approve_btn.setEnabled(false);
                if(reason.getText().toString().trim().equalsIgnoreCase("")){
                    Toast.makeText(getActivity(),"please enter reason.",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    updateApproveRejectStatus(pogModel.pog_code, "Reject", dialog, pos,reason.getText().toString().trim());
                }
            });

        }else {
            return;
        }
    }

    private void updateApproveRejectStatus(String pog_code, String flag, Dialog dialog,int pos,String reason) {
        if (NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
            LoadingDialog progressDialog = new LoadingDialog();
            progressDialog.showLoadingDialog(getActivity());
            JsonObject jsonObject=new JsonObject();
            jsonObject.addProperty("pog_code",pog_code);
            jsonObject.addProperty("status",flag);
            jsonObject.addProperty("reject_reason",reason);

            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            Call<List<ResponseModel>> call = mAPIService.updatePogAppravalStatus(jsonObject);
            call.enqueue(new Callback<List<ResponseModel>>() {
                @Override
                public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                    try {
                        if (response.isSuccessful()){
                            progressDialog.hideDialog();
                            List<ResponseModel> insert_response_list = response.body();
                            if (insert_response_list != null && insert_response_list.size() > 0 && insert_response_list.get(0).condition) {
                                pogModelList.remove(pos);
                                pogApproveAdapter.notifyDataSetChanged();
                                Toast.makeText(getActivity(), insert_response_list.get(0).message, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            } else {
                                Toast.makeText(getActivity(), insert_response_list.size() > 0 ? "No data found" : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            progressDialog.hideDialog();
                            Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        progressDialog.hideDialog();
                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "update_pog_status", getActivity());
                    }
                }

                @Override
                public void onFailure(Call<List<ResponseModel>> call, Throwable t) {
                    progressDialog.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "update_pog_retrun_status", getActivity());
                }
            });
        }
        else {
            Toast.makeText(getActivity(),"Please wait for internet connection",Toast.LENGTH_SHORT).show();
        }
    }
}
