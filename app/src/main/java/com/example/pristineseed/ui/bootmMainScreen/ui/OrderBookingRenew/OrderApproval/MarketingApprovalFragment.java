package com.example.pristineseed.ui.bootmMainScreen.ui.OrderBookingRenew.OrderApproval;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
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
import com.example.pristineseed.global.StaticMethods;
import com.example.pristineseed.model.BookingOrder.BookingResponseModel;
import com.example.pristineseed.model.BookingOrder.MarketingIndentApprovalModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.adapter.order_book.GettingOrderApproveAdapter;
import com.example.pristineseed.ui.adapter.order_book.MarketingIndentLinesDetailsAdapter;
import com.example.pristineseed.ui.bootmMainScreen.ui.OrderBookingRenew.marketing_indent.ViewMktIndentHeaderLinesFragment;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MarketingApprovalFragment extends Fragment implements GettingOrderApproveAdapter.OnStatusItemClick{

    private ListView approval_listview;
    private TextView no_record_found;
    private SessionManagement sessionManagement;
    private List<MarketingIndentApprovalModel> approvalList_ = null;
    private GettingOrderApproveAdapter gettingOrderApproveAdapter = null;
    private List<MarketingIndentApprovalModel.Marketing_Approvalindent_line> marketing_approvalindent_lines_gl = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.booking_order_approval_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initView(view);

        ArrayList<MarketingIndentApprovalModel.Marketing_Approvalindent_line> marketingApprovalindentLines = new ArrayList<>(marketing_approvalindent_lines_gl.size());
        marketingApprovalindentLines.addAll(marketing_approvalindent_lines_gl);

        approval_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (marketing_approvalindent_lines_gl != null && marketing_approvalindent_lines_gl.size() > 0){
                   /* Toast.makeText(getActivity(), "Item Clicked!", Toast.LENGTH_SHORT).show();
                    Bundle bundle  =new Bundle();
                    bundle.putString("indent_no", approvalList_.get(position).marketing_indent_no);
                    bundle.putSerializable("lines", (ArrayList<MarketingIndentApprovalModel.Marketing_Approvalindent_line>)marketing_approvalindent_lines_gl);
                    ViewMktIndentHeaderLinesFragment mktIndentHeaderLines = new ViewMktIndentHeaderLinesFragment();
                    mktIndentHeaderLines.setArguments(bundle);
                    StaticMethods.loadFragments(getActivity(),mktIndentHeaderLines,"View Posted order");*/

                    bindBottomIndentLines(marketing_approvalindent_lines_gl.get(position));
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        getApprovalList();
    }

    private void initView(View view) {
        approval_listview = view.findViewById(R.id.approval_list);
        no_record_found = view.findViewById(R.id.no_data_found);
        sessionManagement = new SessionManagement(getActivity());

       /* approval_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "clicked", Toast.LENGTH_SHORT).show();
            }
        });*/

    }

    private void getApprovalList() {
        boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getActivity());
        if (isNetwork) {
            LoadingDialog progressDialog = new LoadingDialog();
            progressDialog.showLoadingDialog(getActivity());
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            Call<List<MarketingIndentApprovalModel>> call = mAPIService.getMarketingIndentApprovalList(sessionManagement.getUserEmail());
            call.enqueue(new Callback<List<MarketingIndentApprovalModel>>() {
                @Override
                public void onResponse(Call<List<MarketingIndentApprovalModel>> call, Response<List<MarketingIndentApprovalModel>> response) {
                    try {
                        if (response.isSuccessful()) {
                            progressDialog.hideDialog();
                            List<MarketingIndentApprovalModel> orderbooking_list = response.body();
                            if (orderbooking_list != null && orderbooking_list.size() > 0 && orderbooking_list.get(0).condition) {
                                MarketingIndentApprovalModel marketingIndentApprovalModel = orderbooking_list.get(0);
                                marketing_approvalindent_lines_gl = marketingIndentApprovalModel.marketing_indent_line;
                                approvalList_ = orderbooking_list;
                                binddataWithAadapter(approvalList_);
                            } else {
                                no_record_found.setVisibility(View.VISIBLE);
                            }
                        } else {
                            progressDialog.hideDialog();
                            Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        progressDialog.hideDialog();
                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "getMarketingIndentApprovalList", getActivity());
                    }
                }

                @Override
                public void onFailure(Call<List<MarketingIndentApprovalModel>> call, Throwable t) {
                    progressDialog.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "getMarketingIndentApprovalList", getActivity());
                }
            });
        } else {
            Toast.makeText(getActivity(), "Please wait for internet connection.", Toast.LENGTH_SHORT).show();
        }

    }

    private void binddataWithAadapter(List<MarketingIndentApprovalModel> approval_list) {
        gettingOrderApproveAdapter = new GettingOrderApproveAdapter(getActivity(), approval_list);
        approval_listview.setAdapter(gettingOrderApproveAdapter);
        gettingOrderApproveAdapter.setOnStautsItemClick(this);
    }

    @Override
    public void onStatusItemClick(int pos) {
        MarketingIndentApprovalModel marketingIndentApprovalModel = approvalList_.get(pos);
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
        cancel_btn.setOnClickListener(v -> {
            dialog.dismiss();
        });
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        String s1 = "Do you really want to Approve/Reject this\n";
        String s2 = " (" + marketingIndentApprovalModel.marketing_indent_no + ") ";
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
        TextInputLayout reason_layout = PopupView.findViewById(R.id.reason_layout);
        TextInputEditText reason = PopupView.findViewById(R.id.ed_reason);

        approve_btn.setOnClickListener(v -> {
            reason_layout.setVisibility(View.GONE);
            if (reason.getText().toString().trim().equalsIgnoreCase("")) {
                updateApproveRejectStatus(marketingIndentApprovalModel.marketing_indent_no, "Approve", dialog, pos, "");
            }
        });

        reject_btn.setOnClickListener(v -> {
            reason_layout.setVisibility(View.VISIBLE);
            approve_btn.setEnabled(false);
            if (!reason.getText().toString().trim().equalsIgnoreCase("")) {
                updateApproveRejectStatus(marketingIndentApprovalModel.marketing_indent_no, "Reject", dialog, pos, reason.getText().toString().trim());
            }
        });
    }

    private void updateApproveRejectStatus(String code, String flag, Dialog dialog, int pos, String reson) {
        if (NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
            LoadingDialog progressDialog = new LoadingDialog();
            progressDialog.showLoadingDialog(getActivity());
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("marketing_indent_no", code);
            jsonObject.addProperty("status", flag);
            jsonObject.addProperty("reason", reson);

            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            Call<List<BookingResponseModel>> call = mAPIService.updateMarketingIndentBookingStatus(jsonObject);
            call.enqueue(new Callback<List<BookingResponseModel>>() {
                @Override
                public void onResponse(Call<List<BookingResponseModel>> call, Response<List<BookingResponseModel>> response) {
                    try {
                        if (response.isSuccessful()) {
                            progressDialog.hideDialog();
                            List<BookingResponseModel> insert_response_list = response.body();
                            if (insert_response_list != null && insert_response_list.size() > 0 && insert_response_list.get(0).condition) {
                                approvalList_.remove(pos);
                                gettingOrderApproveAdapter.notifyDataSetChanged();
                                Toast.makeText(getActivity(), insert_response_list.get(0).message, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            } else {
                                Toast.makeText(getActivity(), insert_response_list.size() > 0 ? insert_response_list.get(0).message : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            progressDialog.hideDialog();
                            Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        progressDialog.hideDialog();
                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "update_marketing_status", getActivity());
                    }
                }

                @Override
                public void onFailure(Call<List<BookingResponseModel>> call, Throwable t) {
                    progressDialog.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "update_marketing_status", getActivity());
                }
            });
        } else {
            Toast.makeText(getActivity(), "Please wait for internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void bindBottomIndentLines(MarketingIndentApprovalModel.Marketing_Approvalindent_line marketingLines) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
        bottomSheetDialog.setContentView(R.layout.view_mkt_indent_header_lines);
        bottomSheetDialog.show();

        RecyclerView rv_view_mkt_lines = bottomSheetDialog.findViewById(R.id.rv_view_mkt_lines);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_view_mkt_lines.setLayoutManager(layoutManager);

        MarketingIndentLinesDetailsAdapter linesDetailsAdapter = new MarketingIndentLinesDetailsAdapter(getActivity(), marketing_approvalindent_lines_gl);
        rv_view_mkt_lines.setAdapter(linesDetailsAdapter);


    }


}
