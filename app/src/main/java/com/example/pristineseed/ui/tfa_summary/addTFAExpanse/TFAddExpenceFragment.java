package com.example.pristineseed.ui.tfa_summary.addTFAExpanse;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pristineseed.DataBaseRepository.tfa.TFA_Header_Table;
import com.example.pristineseed.GlobalNotification.NetworkUtil;
import com.example.pristineseed.R;
import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.common_data.CommonData;
import com.example.pristineseed.global.ApiRequestFailure;
import com.example.pristineseed.global.DateTimeUtilsCustome;
import com.example.pristineseed.global.LoadingDialog;
import com.example.pristineseed.global.StaticMethods;
import com.example.pristineseed.model.tfa.FAInsertLineResponseModel;
import com.example.pristineseed.model.tfa.TFAHeaderModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.adapter.item.MonthArrayAdapter;
import com.example.pristineseed.ui.adapter.tfa_adapter.TFALineAdapter;
import com.example.pristineseed.ui.tfa_summary.viewTFA.ViewTFADetailFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TFAddExpenceFragment  extends Fragment {


    private TextView tv_name_tfa, tv_state, tv_district, tv_doj, tv_dod, tv_place,tv_mobile,tv_salary,tv_bank_name,tv_accont_no,tv_ifsc_code,
            tfa_code_detail;
    private Button add_line_tfa;
    private MaterialButton submit_tfa_header_line;

    private SessionManagement sessionManagement;
    private TFAHeaderModel submitTFAData;
    private List<TFA_Header_Table> tfa_header_tableList = new ArrayList<>();
    private List<TFAHeaderModel.Tfa_lineModel> tfaLineModelList=new ArrayList<>();
    private TextInputEditText edt_salary_amnt,edt_remarks;
    private AutoCompleteTextView ac_salary_month;
    private LinearLayout line_detail_layout;
    private MaterialButton update_tfa_line;
    private String line_no="";

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            Bundle bundle = getArguments();
            if (bundle != null) {
                String  passBackFragmentData = bundle.getString("flag", "");
                submitTFAData = new Gson().fromJson(bundle.getString("dataPass", ""), TFAHeaderModel.class);
                if(submitTFAData!=null) {
                    bindtextValues(passBackFragmentData);
                }
            }
        }catch (Exception e){
            Log.e("exc",e.getMessage());
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tfa_monthaly_amount_summary_fragment, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManagement = new SessionManagement(getActivity());
        setInitialValues(view);

        submit_tfa_header_line.setOnClickListener(v -> {
            if(ac_salary_month.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(getActivity(),"Please enter salary month !",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(edt_salary_amnt.getText().toString().trim().equalsIgnoreCase("")){
                    Toast.makeText(getActivity(),"Please enter salary amt. !",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getActivity());
                    if (isNetwork) {
                        LoadingDialog loadingDialog = new LoadingDialog();
                        loadingDialog.showLoadingDialog(getActivity());
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("tfa_code", submitTFAData!=null?submitTFAData.tfa_code:"");
                        jsonObject.addProperty("salary_month", ac_salary_month.getText().toString());
                        jsonObject.addProperty("salary_amount", Double.parseDouble(edt_salary_amnt.getText().toString()));
                        jsonObject.addProperty("remarks", edt_remarks.getText().toString().trim());

                        NetworkInterface mApiService = ApiUtils.getPristineAPIService();
                        Call<List<FAInsertLineResponseModel>> call = mApiService.TfaViewInsertLine(jsonObject);
                        call.enqueue(new Callback<List<FAInsertLineResponseModel>>() {
                            @Override
                            public void onResponse(Call<List<FAInsertLineResponseModel>> call, Response<List<FAInsertLineResponseModel>> response) {
                                try {
                                    if (response.isSuccessful()) {
                                        loadingDialog.hideDialog();
                                        List<FAInsertLineResponseModel> FAInsertLineResponseModelList = response.body();
                                        if (FAInsertLineResponseModelList != null && FAInsertLineResponseModelList.size() > 0 && FAInsertLineResponseModelList.get(0).condition) {
                                            ViewTFADetailFragment viewTFADetailFragment=new ViewTFADetailFragment();
                                            StaticMethods.loadFragmentsWithBackStack(getActivity(),viewTFADetailFragment,"view_detail_fragment");
                                            Toast.makeText(getActivity(), FAInsertLineResponseModelList.get(0).message, Toast.LENGTH_SHORT).show();
                                        } else {
                                            loadingDialog.hideDialog();
                                            Toast.makeText(getActivity(), FAInsertLineResponseModelList.get(0).message, Toast.LENGTH_SHORT).show();
                                        }

                                    } else {
                                        loadingDialog.hideDialog();
                                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                    }

                                } catch (Exception e) {
                                    loadingDialog.hideDialog();
                                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "insert_view_expense", getActivity());
                                }
                            }

                            @Override
                            public void onFailure(Call<List<FAInsertLineResponseModel>> call, Throwable t) {
                                loadingDialog.hideDialog();
                                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "insert_view_expense", getActivity());
                            }
                        });
                    } else {
                        Toast.makeText(getActivity(), "Please wait for online !", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        update_tfa_line.setOnClickListener(v -> {
            if(ac_salary_month.getText().toString().equalsIgnoreCase("")){
                Toast.makeText(getActivity(),"Please enter salary month !",Toast.LENGTH_SHORT).show();
                return;
            }
            else if(edt_salary_amnt.getText().toString().trim().equalsIgnoreCase("")){
                Toast.makeText(getActivity(),"Please enter salary amt. !",Toast.LENGTH_SHORT).show();
                return;
            }
            else {
                boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getActivity());
                if (isNetwork) {
                    LoadingDialog loadingDialog = new LoadingDialog();
                    loadingDialog.showLoadingDialog(getActivity());
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("tfa_code", submitTFAData!=null?submitTFAData.tfa_code:"");
                    jsonObject.addProperty("line_no",line_no);
                    jsonObject.addProperty("salary_month", ac_salary_month.getText().toString());
                    jsonObject.addProperty("salary_amount", Double.parseDouble(edt_salary_amnt.getText().toString()));
                    jsonObject.addProperty("remarks", edt_remarks.getText().toString().trim());

                    NetworkInterface mApiService = ApiUtils.getPristineAPIService();
                    Call<List<FAInsertLineResponseModel.Tfa_lineModel>> call = mApiService.updateTfaLine(jsonObject);
                    call.enqueue(new Callback<List<FAInsertLineResponseModel.Tfa_lineModel>>() {
                        @Override
                        public void onResponse(Call<List<FAInsertLineResponseModel.Tfa_lineModel>> call, Response<List<FAInsertLineResponseModel.Tfa_lineModel>> response) {
                            try {
                                if (response.isSuccessful()) {
                                    loadingDialog.hideDialog();
                                    List<FAInsertLineResponseModel.Tfa_lineModel> FAInsertLineResponseModelList = response.body();
                                    if (FAInsertLineResponseModelList != null && FAInsertLineResponseModelList.size() > 0 && FAInsertLineResponseModelList.get(0).condition) {
                                        ViewTFADetailFragment viewTFADetailFragment=new ViewTFADetailFragment();
                                        StaticMethods.loadFragmentsWithBackStack(getActivity(),viewTFADetailFragment,"view_detail_fragment");
                                        Toast.makeText(getActivity(), FAInsertLineResponseModelList.get(0).message, Toast.LENGTH_SHORT).show();
                                    } else {
                                        loadingDialog.hideDialog();
                                        Toast.makeText(getActivity(), FAInsertLineResponseModelList.get(0).message, Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    loadingDialog.hideDialog();
                                    Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                }

                            } catch (Exception e) {
                                loadingDialog.hideDialog();
                                ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "update_tfa_expense", getActivity());
                            }
                        }

                        @Override
                        public void onFailure(Call<List<FAInsertLineResponseModel.Tfa_lineModel>> call, Throwable t) {
                            loadingDialog.hideDialog();
                            ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "update_tfa_expense", getActivity());
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "Please wait for online !", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void bindtextValues(String  passBackFragmentData) {
        if(passBackFragmentData.equalsIgnoreCase("insert_line")) {
            if (submitTFAData != null) {
                update_tfa_line.setVisibility(View.GONE);
                submit_tfa_header_line.setVisibility(View.VISIBLE);
                setHeaderDetails();
            }
        }
        else if(passBackFragmentData.equalsIgnoreCase("update_line")){
            if (submitTFAData != null) {
                update_tfa_line.setVisibility(View.VISIBLE);
                submit_tfa_header_line.setVisibility(View.GONE);
                setHeaderDetails();
            }
        }
    }

    private void setHeaderDetails() {
            tfa_code_detail.setText("  TFA Detail" + "( " + submitTFAData.tfa_code + " )");
            tv_name_tfa.setText(submitTFAData.name);
            tv_state.setText(submitTFAData.state);
            tv_district.setText(submitTFAData.district);
            tv_doj.setText(submitTFAData.date_of_joining);
            tv_dod.setText(submitTFAData.date_of_discontinue);
            tv_place.setText(submitTFAData.place);
            tv_mobile.setText(submitTFAData.mobile_no);
            tv_salary.setText(submitTFAData.month_salary);
            tv_bank_name.setText(submitTFAData.bank_name);
            tv_accont_no.setText(submitTFAData.account_no);
            tv_ifsc_code.setText(submitTFAData.ifsc_code);

            if (submitTFAData.tfa_line != null && submitTFAData.tfa_line.get(0).line_no != null) {
                 line_no= submitTFAData.tfa_line.get(0).line_no;
                edt_salary_amnt.setText(submitTFAData.tfa_line.get(0).salary_amount);
                ac_salary_month.setText(submitTFAData.tfa_line.get(0).salary_month);
                edt_remarks.setText(submitTFAData.tfa_line.get(0).remarks);
            }

            if (submitTFAData.status.equalsIgnoreCase("INSERT EXPENSE")) {
                line_detail_layout.setVisibility(View.GONE);
                submit_tfa_header_line.setEnabled(false);
            } else {
                line_detail_layout.setVisibility(View.VISIBLE);
                submit_tfa_header_line.setEnabled(true);
            }

    }

    private void setInitialValues(View view) {
        tfa_code_detail=view.findViewById(R.id.tfa_code_detail);
        tv_name_tfa=view.findViewById(R.id.tv_name_tfa);
        tv_state=view.findViewById(R.id.tv_state);
        tv_district=view.findViewById(R.id.tv_district);
        tv_doj=view.findViewById(R.id.tv_doj);
        tv_dod=view.findViewById(R.id.tv_dod);
        tv_place=view.findViewById(R.id.tv_place);
        tv_mobile=view.findViewById(R.id.tv_mobile);
        tv_salary=view.findViewById(R.id.tv_salary);
        tv_bank_name=view.findViewById(R.id.tv_bank_name);
        tv_accont_no=view.findViewById(R.id.tv_accont_no);
        tv_ifsc_code=view.findViewById(R.id.tv_ifsc_code);
        update_tfa_line=view.findViewById(R.id.update_tfa_line);
        line_detail_layout=view.findViewById(R.id.line_detail_layout);
        submit_tfa_header_line=view.findViewById(R.id.submit_tfa_header_line);


        edt_salary_amnt=view.findViewById(R.id.edt_salary_amnt);
          edt_remarks=view.findViewById(R.id.edt_remarks);
         ac_salary_month=view.findViewById(R.id.edt_salary_month);

        MonthArrayAdapter month_adapter = new MonthArrayAdapter(getActivity(), R.layout.android_item_view, Arrays.asList(CommonData.month));
        ac_salary_month.setAdapter(month_adapter);

    }


}
