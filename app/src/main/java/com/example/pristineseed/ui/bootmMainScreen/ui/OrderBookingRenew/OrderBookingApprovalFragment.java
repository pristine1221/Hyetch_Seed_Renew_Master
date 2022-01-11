package com.example.pristineseed.ui.bootmMainScreen.ui.OrderBookingRenew;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
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
import com.example.pristineseed.model.BookingOrder.BookingApprovalModel;
import com.example.pristineseed.model.BookingOrder.BookingResponseModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.adapter.order_book.BookingApprovalAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderBookingApprovalFragment extends Fragment implements BookingApprovalAdapter.OnListApproveRejectClickListner {


    private ListView orderApprovalList;
    private TextView no_record_found;
    private SessionManagement sessionManagement;

    private List<BookingApprovalModel> approval_list;
    private BookingApprovalAdapter bookingApprovalAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.order_approval_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initView(view);

    }

    private void initView(View view) {
        orderApprovalList = view.findViewById(R.id.orderApprovalList);
        no_record_found = view.findViewById(R.id.no_data_found);
        sessionManagement = new SessionManagement(getContext());
    }


    @Override
    public void onResume() {
        getOrderApprovalList();
        super.onResume();
    }

    private void getOrderApprovalList() {
        boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getActivity());
        if (isNetwork) {
            LoadingDialog progressDialog = new LoadingDialog();
            progressDialog.showLoadingDialog(getActivity());
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            Call<List<BookingApprovalModel>> call = mAPIService.getBookingApprovalList(sessionManagement.getUserEmail());
            call.enqueue(new Callback<List<BookingApprovalModel>>() {
                @Override
                public void onResponse(Call<List<BookingApprovalModel>> call, Response<List<BookingApprovalModel>> response) {
                    try {
                        if (response.isSuccessful()) {
                            progressDialog.hideDialog();
                            List<BookingApprovalModel> orderbooking_list = response.body();
                            if (orderbooking_list != null && orderbooking_list.size() > 0 && orderbooking_list.get(0).condition) {
                                approval_list = orderbooking_list;
                                binddataWithAadapter(approval_list);
                            } else {
                                orderApprovalList.setVisibility(View.GONE);
                                no_record_found.setVisibility(View.VISIBLE);
//                                Toast.makeText(getActivity(), orderbooking_list != null && orderbooking_list.size() > 0 ? "No data found OBA" : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            progressDialog.hideDialog();
                            Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        progressDialog.hideDialog();
                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "getOrderApprovalBookList", getActivity());
                    }
                }

                @Override
                public void onFailure(Call<List<BookingApprovalModel>> call, Throwable t) {
                    progressDialog.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "getOrderApprovalBookList", getActivity());
                }
            });
        } else {
            Toast.makeText(getActivity(), "Please wait for internet connection.", Toast.LENGTH_SHORT).show();
        }

    }

    private void binddataWithAadapter(List<BookingApprovalModel> approval_list) {
        bookingApprovalAdapter = new BookingApprovalAdapter(getActivity(), approval_list);
        orderApprovalList.setAdapter(bookingApprovalAdapter);
        bookingApprovalAdapter.setOnListClick(this);
    }

    @Override
    public void onStatus(String flag, int pos) {
        if (approval_list != null && approval_list.size() > 0) {
            //todo getting particular item model from list...
            BookingApprovalModel bookingApprovalModel = approval_list.get(pos);
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
            TextInputLayout reason_layout = PopupView.findViewById(R.id.reason_layout);
            TextInputEditText reason = PopupView.findViewById(R.id.ed_reason);
            TextInputEditText booking_allloted_qty = PopupView.findViewById(R.id.ed_booking_alloted_qty);
            TextInputEditText ed_allotd_per = PopupView.findViewById(R.id.ed_allotd_per);
            TextInputLayout booking_allloted_qty_input_layout = PopupView.findViewById(R.id.booking_allloted_qty_input_layout);
            TextInputLayout alloted_per_input_layout = PopupView.findViewById(R.id.alloted_per_input_layout);

            cancel_btn.setOnClickListener(v -> {
                dialog.dismiss();
            });
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            String s1 = "Do you really want to Approve/Reject this\n";
            String s2 = " (" + bookingApprovalModel.booking_no + ") ";
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

            ed_allotd_per.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!approval_list.get(pos).booking_qty.equalsIgnoreCase("") &&
                            !ed_allotd_per.getText().toString().trim().equalsIgnoreCase("")) {
                        float booking_qty = Float.parseFloat(approval_list.get(pos).booking_qty);

                        float alloted_per = Float.parseFloat(ed_allotd_per.getText().toString().trim());

                        float alloted_boooking_qty = booking_qty * alloted_per / 100;
                        if (!String.valueOf(alloted_boooking_qty).equalsIgnoreCase("")) {
                            if (alloted_per <= 100) {
                                booking_allloted_qty.setText(String.valueOf(alloted_boooking_qty));
                            } else {
                                ed_allotd_per.setText("100");
                                float booking_qty1 = Float.parseFloat(approval_list.get(pos).booking_qty);

                                float alloted_per1 = Float.parseFloat(ed_allotd_per.getText().toString().trim());

                                float alloted_boooking_qty1 = booking_qty1 * alloted_per1 / 100;

                                booking_allloted_qty.setText(String.valueOf(alloted_boooking_qty1));
                            }

                        }
                    } else {
                        booking_allloted_qty.setText("0");
                    }
                }
            });


            approve_btn.setOnClickListener(v -> {
                if(bookingApprovalModel.status.equalsIgnoreCase("Pending")) {
                    booking_allloted_qty_input_layout.setVisibility(View.GONE);
                    alloted_per_input_layout.setVisibility(View.GONE);
                    reason_layout.setVisibility(View.GONE);
                    updateApproveRejectStatus(bookingApprovalModel.booking_no, bookingApprovalModel.status.equalsIgnoreCase("Pending") ? "Approve" : "Approve", dialog, pos, "", "0");
                }
                else if(bookingApprovalModel.status.equalsIgnoreCase("First Approve")){
                    booking_allloted_qty_input_layout.setVisibility(View.VISIBLE);
                    alloted_per_input_layout.setVisibility(View.VISIBLE);
                    reason_layout.setVisibility(View.GONE);
                    if (ed_allotd_per.getText().toString().trim().equals("")) {
                        Toast.makeText(getActivity(), "Please enter booking qty.", Toast.LENGTH_SHORT).show();
                    } else if (ed_allotd_per.getText().toString().trim().equals(".")) {
                        Toast.makeText(getActivity(), "Please enter valid booking qty.", Toast.LENGTH_SHORT).show();
                    } else if (ed_allotd_per.getText().toString().trim().equals(".0")) {
                        Toast.makeText(getActivity(), "Please enter valid booking qty.", Toast.LENGTH_SHORT).show();
                    } else if (ed_allotd_per.getText().toString().trim().equals("0.1")) {
                        Toast.makeText(getActivity(), "Please enter valid booking qty.", Toast.LENGTH_SHORT).show();
                    } else {
                        float alloted_per = Float.parseFloat(ed_allotd_per.getText().toString().trim());
                        if (alloted_per > 100) {
                            Toast.makeText(getActivity(), "Please enter valid alloted %", Toast.LENGTH_SHORT).show();
                        } else {
                            updateApproveRejectStatus(bookingApprovalModel.booking_no, bookingApprovalModel.status.equalsIgnoreCase("First Approve") ? "Approve" : "Approve", dialog, pos, "", ed_allotd_per.getText().toString().trim());
                        }
                    }
                }


            });

            reject_btn.setOnClickListener(v -> {
                booking_allloted_qty_input_layout.setVisibility(View.GONE);
                alloted_per_input_layout.setVisibility(View.GONE);
                reason_layout.setVisibility(View.VISIBLE);
                approve_btn.setEnabled(false);
                if (reason.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "please enter reason.", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    updateApproveRejectStatus(bookingApprovalModel.booking_no, "Reject", dialog, pos, reason.getText().toString().trim(), "0");
                }
            });
        } else {
            return;
        }

    }

    private void updateApproveRejectStatus(String booking_no, String status, Dialog dialog, int pos, String reason, String alloted_per) {
        if (NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
            LoadingDialog progressDialog = new LoadingDialog();
            progressDialog.showLoadingDialog(getActivity());
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("booking_no", booking_no);
            jsonObject.addProperty("status", status); //selectedeventData.status.equalsIgnoreCase("Pending") ? "Approve" : "Approve"
            jsonObject.addProperty("reason", reason);
            jsonObject.addProperty("alotted_percent", alloted_per);
            jsonObject.addProperty("approver_id", sessionManagement.getApprover_id());

            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            Call<List<BookingResponseModel>> call = mAPIService.updateBookingStatus(jsonObject);
            call.enqueue(new Callback<List<BookingResponseModel>>() {
                @Override
                public void onResponse(Call<List<BookingResponseModel>> call, Response<List<BookingResponseModel>> response) {
                    try {
                        if (response.isSuccessful()) {
                            progressDialog.hideDialog();
                            List<BookingResponseModel> insert_response_list = response.body();
                            if (insert_response_list != null && insert_response_list.size() > 0 && insert_response_list.get(0).condition) {
                                approval_list.remove(pos);
                                bookingApprovalAdapter.notifyDataSetChanged();
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
                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "update_pld_status", getActivity());
                    }
                }

                @Override
                public void onFailure(Call<List<BookingResponseModel>> call, Throwable t) {
                    progressDialog.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "update_pld_status", getActivity());
                }
            });
        } else {
            Toast.makeText(getActivity(), "Please wait for online connection", Toast.LENGTH_SHORT).show();
        }


    }
}
