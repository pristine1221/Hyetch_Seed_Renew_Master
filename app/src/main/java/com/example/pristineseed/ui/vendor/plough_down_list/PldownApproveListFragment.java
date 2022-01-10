package com.example.pristineseed.ui.vendor.plough_down_list;

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
import com.example.pristineseed.model.Plough_down_List.PloghDownListModel;
import com.example.pristineseed.model.ResponseModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.adapter.header_line.PldownApproveAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PldownApproveListFragment extends Fragment implements PldownApproveAdapter.OnListApproveRejectClickListner {
    private RecyclerView approve_list_view;
    private SessionManagement sessionManagement;
    private List<PloghDownListModel>ploghDownList;
    private  PldownApproveAdapter pldownApproveAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.pld_approve_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        approve_list_view = view.findViewById(R.id.pld_approve_list);
        sessionManagement=new SessionManagement(getContext());
        approve_list_view.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        approve_list_view.setItemAnimator(new DefaultItemAnimator());
        approve_list_view.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onResume() {
        super.onResume();
        getPldApprovalList();
    }

    private void getPldApprovalList() {
        if (NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
            LoadingDialog progressDialog = new LoadingDialog();
            progressDialog.showLoadingDialog(getActivity());
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            Call<List<PloghDownListModel>> call = mAPIService.getApprovalList(sessionManagement.getUserEmail());
            call.enqueue(new Callback<List<PloghDownListModel>>() {
                @Override
                public void onResponse(Call<List<PloghDownListModel>> call, Response<List<PloghDownListModel>> response) {
                    try {
                        if (response.isSuccessful()) {
                            progressDialog.hideDialog();
                            List<PloghDownListModel> update_approval_list = response.body();
                            if (update_approval_list != null && update_approval_list.size() > 0 && update_approval_list.get(0).condition) {
                                ploghDownList=update_approval_list;
                                bindDatawithAdapter(update_approval_list);
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
                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "pld_approve_list", getActivity());
                    }
                }

                @Override
                public void onFailure(Call<List<PloghDownListModel>> call, Throwable t) {
                    progressDialog.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "pld_approve_list", getActivity());
                }
            });
        }
        else {
            Toast.makeText(getActivity(),"Please wait for online connection",Toast.LENGTH_SHORT).show();
        }
    }

    private void bindDatawithAdapter(List<PloghDownListModel> pldApproveList) {
         pldownApproveAdapter=new PldownApproveAdapter(getActivity(),pldApproveList);
         approve_list_view.setAdapter(pldownApproveAdapter);
         pldownApproveAdapter.setOnListClick(this);
    }

    @Override
    public void onBtnCLick(String flag, int pos) {
        if(ploghDownList!=null && ploghDownList.size()>0) {
            PloghDownListModel ploghDownListModel = ploghDownList.get(pos);
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
            String s2 = " (" + ploghDownListModel.pld_code + ") ";
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
                updateApproveStatus(ploghDownListModel.pld_code, "Approve", dialog, pos);

            });

            reject_btn.setOnClickListener(v -> {
                reason_layout.setVisibility(View.VISIBLE);
                approve_btn.setEnabled(false);
                if(reason.getText().toString().trim().equalsIgnoreCase("")){
                    Toast.makeText(getActivity(),"please enter reason.",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    updateRejectStatus(ploghDownListModel.pld_code, "Reject", dialog, pos,reason.getText().toString().trim());
                }
            });

        }else {
            return;
        }
    }

    private void updateApproveStatus(String pld_code, String flag, Dialog dialog,int pos) {
        if (NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
            LoadingDialog progressDialog = new LoadingDialog();
            progressDialog.showLoadingDialog(getActivity());
            JsonObject jsonObject=new JsonObject();
            jsonObject.addProperty("pld_code",pld_code);
            jsonObject.addProperty("status",flag);
            jsonObject.addProperty("reject_reason","");

            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            Call<List<ResponseModel>> call = mAPIService.updatePldStatus(jsonObject);
            call.enqueue(new Callback<List<ResponseModel>>() {
                @Override
                public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                    try {
                        if (response.isSuccessful()){
                            progressDialog.hideDialog();
                            List<ResponseModel> insert_response_list = response.body();
                            if (insert_response_list != null && insert_response_list.size() > 0 && insert_response_list.get(0).condition) {
                                ploghDownList.remove(pos);
                                pldownApproveAdapter.notifyDataSetChanged();
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
                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "update_pld_status", getActivity());
                    }
                }

                @Override
                public void onFailure(Call<List<ResponseModel>> call, Throwable t) {
                    progressDialog.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "update_pld_status", getActivity());
                }
            });
        }
        else {
            Toast.makeText(getActivity(),"Please wait for online connection",Toast.LENGTH_SHORT).show();
        }
    }


    private void updateRejectStatus(String pld_code, String flag, Dialog dialog,int pos,String reject_reason) {
        if (NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
            LoadingDialog progressDialog = new LoadingDialog();
            progressDialog.showLoadingDialog(getActivity());
            JsonObject jsonObject=new JsonObject();
            jsonObject.addProperty("pld_code",pld_code);
            jsonObject.addProperty("status",flag);
            jsonObject.addProperty("reject_reason",reject_reason);

            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            Call<List<ResponseModel>> call = mAPIService.updatePldStatus(jsonObject);
            call.enqueue(new Callback<List<ResponseModel>>() {
                @Override
                public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                    try {
                        if (response.isSuccessful()){
                            progressDialog.hideDialog();
                            List<ResponseModel> insert_response_list = response.body();
                            if (insert_response_list != null && insert_response_list.size() > 0 && insert_response_list.get(0).condition) {
                                ploghDownList.remove(pos);
                                pldownApproveAdapter.notifyDataSetChanged();
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
                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "update_pld_status", getActivity());
                    }
                }

                @Override
                public void onFailure(Call<List<ResponseModel>> call, Throwable t) {
                    progressDialog.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "update_pld_status", getActivity());
                }
            });
        }
        else {
            Toast.makeText(getActivity(),"Please wait for online connection",Toast.LENGTH_SHORT).show();
        }
    }

}
