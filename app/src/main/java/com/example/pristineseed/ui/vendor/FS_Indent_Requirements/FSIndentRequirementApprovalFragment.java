package com.example.pristineseed.ui.vendor.FS_Indent_Requirements;

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
import android.widget.ListView;
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
import com.example.pristineseed.model.fs_indent_requirement.FsIndentRequirementsModel;
import com.example.pristineseed.model.fs_return_harvesting.FsRetrunAapprovalModel;
import com.example.pristineseed.model.fs_return_harvesting.FsReturnResponseModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.adapter.header_line.FsIndentRequirmentApprovalAadapter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FSIndentRequirementApprovalFragment extends Fragment implements FsIndentRequirmentApprovalAadapter.OnListApproveRejectClickListner {

    private ListView fs_requirement_list;
    FsIndentRequirmentApprovalAadapter fsIndentRequirmentApprovalAadapter;
    private List<FsIndentRequirementsModel> fsIndentRequirementsModelList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private SessionManagement sessionManagement;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fs_indent_approval_requrmnt, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fs_requirement_list = view.findViewById(R.id.fs_requirement_list);
        sessionManagement=new SessionManagement(getActivity());
        fsIndentRequirementsModelList=new ArrayList<>();
    }

    @Override
    public void onResume() {
        getFsIndentApprovalList();
        super.onResume();
    }

    private void getFsIndentApprovalList(){
        if (NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
            LoadingDialog progressDialog = new LoadingDialog();
            progressDialog.showLoadingDialog(getActivity());
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            Call<List<FsIndentRequirementsModel>> call = mAPIService.getFsIndentApprovalList(sessionManagement.getUserEmail());
            call.enqueue(new Callback<List<FsIndentRequirementsModel>>() {
                @Override
                public void onResponse(Call<List<FsIndentRequirementsModel>> call, Response<List<FsIndentRequirementsModel>> response) {
                    try {
                        if (response.isSuccessful()) {
                            progressDialog.hideDialog();
                            List<FsIndentRequirementsModel> update_approval_list = response.body();
                            if (update_approval_list != null && update_approval_list.size() > 0 && update_approval_list.get(0).condition) {
                                fsIndentRequirementsModelList=update_approval_list;
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
                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "fs_indent_approval_list", getActivity());
                    }
                }
                @Override
                public void onFailure(Call<List<FsIndentRequirementsModel>> call, Throwable t) {
                    progressDialog.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "fs_indent_approval_list", getActivity());
                }
            });
        }
        else {
            Toast.makeText(getActivity(),"Please wait for online connection",Toast.LENGTH_SHORT).show();
        }

    }

    private void bindDatawithAdapter(List<FsIndentRequirementsModel> update_approval_list) {
         fsIndentRequirmentApprovalAadapter=new FsIndentRequirmentApprovalAadapter(getActivity(),update_approval_list);
        fs_requirement_list.setAdapter(fsIndentRequirmentApprovalAadapter);
        fsIndentRequirmentApprovalAadapter.setOnApprvRejctClickListnerr(this);

    }

    @Override
    public void onBtnCLick(String flag, int pos) {
        FsIndentRequirementsModel fsIndentRequirementsModel=fsIndentRequirementsModelList.get(pos);
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
        TextView title_text=PopupView.findViewById(R.id.title_text);
        ImageView cancel_btn=PopupView.findViewById(R.id.close_pop_up);
        TextInputLayout reason_layout=PopupView.findViewById(R.id.reason_layout);
        TextInputEditText reason=PopupView.findViewById(R.id.ed_reason);
        cancel_btn.setOnClickListener(v -> {
            dialog.dismiss();
        });
        SpannableStringBuilder spannableStringBuilder=new SpannableStringBuilder();
        String s1="Do you really want to Approve/Reject this\n";
        String s2=" ("+fsIndentRequirementsModel.fs_req_code+") ";
        String s3= " Item ?";
        SpannableString spannableString=new SpannableString(s1);
        spannableStringBuilder.append(spannableString);

        SpannableString spannableString1=new SpannableString(s2);
        spannableString1.setSpan(new ForegroundColorSpan(Color.BLACK),0,s2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.append(spannableString1);

        SpannableString spannableString2=new SpannableString(s3);
        spannableStringBuilder.append(spannableString2);

        title_text.setText(spannableStringBuilder);

        MaterialButton approve_btn=PopupView.findViewById(R.id.approve_btn);
        MaterialButton reject_btn=PopupView.findViewById(R.id.reject_btn);

        approve_btn.setOnClickListener( v -> {
            reason_layout.setVisibility(View.GONE);
            if(reason.getText().toString().trim().equalsIgnoreCase("")) {
                updateApproveRejectStatus(fsIndentRequirementsModel.fs_req_code, "Approve", dialog, pos, "");
            }
        });

        reject_btn.setOnClickListener(v -> {
            reason_layout.setVisibility(View.VISIBLE);
            approve_btn.setEnabled(false);
            if(!reason.getText().toString().trim().equalsIgnoreCase("")) {
                updateApproveRejectStatus(fsIndentRequirementsModel.fs_req_code, "Reject",dialog, pos,reason.getText().toString().trim());
            }

        });
    }

    private void updateApproveRejectStatus(String fs_code, String flag, Dialog dialog,int pos,String reasaon) {
        if (NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
            LoadingDialog progressDialog = new LoadingDialog();
            progressDialog.showLoadingDialog(getActivity());
            JsonObject jsonObject=new JsonObject();
            jsonObject.addProperty("fs_req_code",fs_code);
            jsonObject.addProperty("status",flag);
            jsonObject.addProperty("reason",reasaon);

            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            Call<List<FsReturnResponseModel>> call = mAPIService.updateFsRequirementApprveStatus(jsonObject);
            call.enqueue(new Callback<List<FsReturnResponseModel>>() {
                @Override
                public void onResponse(Call<List<FsReturnResponseModel>> call, Response<List<FsReturnResponseModel>> response) {
                    try {
                        if (response.isSuccessful()) {
                            progressDialog.hideDialog();
                            List<FsReturnResponseModel> insert_response_list = response.body();
                            if (insert_response_list != null && insert_response_list.size() > 0 && insert_response_list.get(0).condition) {
                                fsIndentRequirementsModelList.remove(pos);
                                fsIndentRequirmentApprovalAadapter.notifyDataSetChanged();
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
                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "update_fs_retrun_status", getActivity());
                    }
                }

                @Override
                public void onFailure(Call<List<FsReturnResponseModel>> call, Throwable t) {
                    progressDialog.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "update_fs_retrun_status", getActivity());
                }
            });
        }
        else {
            Toast.makeText(getActivity(),"Please wait for online connection",Toast.LENGTH_SHORT).show();
        }
    }

}
