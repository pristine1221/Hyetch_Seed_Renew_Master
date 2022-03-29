package com.example.pristineseed.ui.reports;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pristineseed.GlobalNotification.NetworkUtil;
import com.example.pristineseed.R;
import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.global.ApiRequestFailure;
import com.example.pristineseed.global.DateTimeUtilsCustome;
import com.example.pristineseed.global.MaterialDatePicker;
import com.example.pristineseed.model.item.RoleMasterModel;
import com.example.pristineseed.model.reportModel.ZoneOrDistributorWiseDetailsModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.adapter.item.RoleMasterAdapter;
import com.example.pristineseed.ui.adapter.reportsAdapter.zoneOrDistributorWiseDetails.ZoneOrDistributorDetailsListAdapter;
import com.example.pristineseed.ui.adapter.reportsAdapter.zoneOrDistributorWiseDetails.ZoneWiseAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ZoneOrDistributorwiseOrderDetailsFragment extends Fragment implements RoleMasterAdapter.OnItemClickListner{
    private SessionManagement sessionManagement;
    private TextInputLayout distributor_name_text_input_layout, order_start_date_input_layout, order_end_date_input_layout;
    private TextInputEditText et_order_distributor_name, et_start_date, et_end_date;
    private MaterialButton submit_order_list, clear_order_list;
    private MaterialCardView frame_order_distributor_list_layout;
    private RecyclerView rv_order_distributor_list, order_recycler_view_list;
    private LinearLayoutManager order_distributor_layout_manager, order_details_list_layout_manager;
    private FloatingActionButton filter_download_floating_btn;
    private View view_weight;
    private ScrollView parent_layout;
    private ProgressBar loading_item;
    private RelativeLayout search_reports_item_layout;
    private ImageView iv_refresh_details;
    private List<ZoneOrDistributorWiseDetailsModel> zoneOrDistributorWiseModelList_gl = new ArrayList<>();
    private ZoneOrDistributorWiseDetailsModel zoneOrDistributorWiseModel = null;
    private ProgressBar order_progressBar;
    private TextView order_progress_percentage_count, order_text_percent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // todo Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_zone_or_distributorwise_order_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManagement = new SessionManagement(getActivity());
        initView(view);

        //todo distributor filter..
        et_order_distributor_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                et_order_distributor_name.setSelection(s.toString().length());
                if (!s.toString().equalsIgnoreCase("") && et_order_distributor_name.isCursorVisible()) {
                    frame_order_distributor_list_layout.setVisibility(View.VISIBLE);
                    getDistributor(s.toString());
                    view_weight.setVisibility(View.VISIBLE);
                } else {
                    frame_order_distributor_list_layout.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        //todo order start date filter...
        et_start_date.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    MaterialDatePicker materialDatePicker = new MaterialDatePicker(getActivity());
                    materialDatePicker.disableDateAfterToday(et_start_date);
                }
                return false;
            }
        });

        //todo end date filter...
        et_end_date.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (!et_start_date.getText().toString().equalsIgnoreCase("")) {
                        MaterialDatePicker materialDatePicker = new MaterialDatePicker(getActivity());
                        materialDatePicker.disableDatesAccToStartDate(et_end_date, et_start_date.getText().toString());
                    } else {
                        MDToast.makeText(getActivity(), "Select Start Date First", Toast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();
                    }
                }
                return false;
            }
        });

        //todo focus removed from view on click parent layout...
        parent_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    frame_order_distributor_list_layout.setVisibility(View.GONE);
                    et_order_distributor_name.clearFocus();
                    et_start_date.clearFocus();
                    et_end_date.clearFocus();
//                    parent_layout.requestFocus();
                }
                return true;
            }
        });

        //todo clear input texts from refresh listener...
        iv_refresh_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_order_distributor_name.setText("");
                et_order_distributor_name.clearFocus();
                frame_order_distributor_list_layout.setVisibility(View.GONE);
                distributor_name_text_input_layout.setStartIconDrawable(R.drawable.ic_baseline_search_24);
                et_start_date.setText("");
                et_start_date.clearFocus();
                et_end_date.setText("");
                et_end_date.clearFocus();
            }
        });

        //todo submit order list details...
        submit_order_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSubmitOrderDetails(et_order_distributor_name.getText().toString(), et_start_date.getText().toString(), et_end_date.getText().toString());
            }
        });

        //todo clear list and details...
        clear_order_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearUIRefresh();
            }
        });
    }

    //todo initialization...
    private void initView(View view) {
        distributor_name_text_input_layout = view.findViewById(R.id.distributor_name_text_input_layout);
        order_start_date_input_layout = view.findViewById(R.id.order_start_date_input_layout);
        order_end_date_input_layout = view.findViewById(R.id.order_end_date_input_layout);
        et_order_distributor_name = view.findViewById(R.id.et_order_distributor_name);
        et_start_date = view.findViewById(R.id.et_start_date);
        et_end_date = view.findViewById(R.id.et_end_date);
        submit_order_list = view.findViewById(R.id.submit_order_list);
        clear_order_list = view.findViewById(R.id.clear_order_list);
        frame_order_distributor_list_layout = view.findViewById(R.id.frame_order_distributor_list_layout);
        rv_order_distributor_list = view.findViewById(R.id.rv_order_distributor_list);
        order_recycler_view_list = view.findViewById(R.id.order_recycler_view_list);
        filter_download_floating_btn = view.findViewById(R.id.filter_download_floating_btn);
        loading_item = view.findViewById(R.id.loading_item);
        view_weight = view.findViewById(R.id.view_weight);
        parent_layout = view.findViewById(R.id.parent_relative_layout);
        iv_refresh_details = view.findViewById(R.id.iv_refresh_details);
        search_reports_item_layout = view.findViewById(R.id.search_reports_item_layout);
        //todo.. download
        order_progressBar = view.findViewById(R.id.order_progressBar);
        order_progress_percentage_count = view.findViewById(R.id.order_progress_percentage_count);
        order_text_percent = view.findViewById(R.id.order_text_percent);

        //todo set organizer layout manager...
        order_distributor_layout_manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_order_distributor_list.setLayoutManager(order_distributor_layout_manager);

        //todo set dispatch layout manager...
        order_details_list_layout_manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        order_recycler_view_list.setLayoutManager(order_details_list_layout_manager);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    //todo submit details..
    private void setSubmitOrderDetails(String distributor_name, String start_date, String end_date) {
        if (NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
            if (et_start_date.getText().toString().equalsIgnoreCase(""))
                MDToast.makeText(getActivity(), "Start Date Required!", Toast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();
            else if (et_end_date.getText().toString().equalsIgnoreCase(""))
                MDToast.makeText(getActivity(), "End Date Required!", Toast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();
            else if (!et_start_date.getText().toString().equalsIgnoreCase("") || !et_end_date.getText().toString().equalsIgnoreCase(""))
                orderDetailsApi("" , start_date, end_date, "order_detail_list");
            else if (!et_order_distributor_name.getText().toString().equalsIgnoreCase("") || !et_start_date.getText().toString().equalsIgnoreCase("")
                    || !et_end_date.getText().toString().equalsIgnoreCase(""))
                orderDetailsApi(distributor_name , start_date, end_date, "order_detail_list");

        } else {
            Toast.makeText(getActivity(), "Please wait for online connection", Toast.LENGTH_SHORT).show();
        }
    }

    //todo getting distributor filter...
    private List<RoleMasterModel.Data> roleMasterTableList_gl = new ArrayList<>();

    private void getDistributor(String filter_key) {
        loading_item.setVisibility(View.VISIBLE);
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<RoleMasterModel> call = mAPIService.getDistributor("Distributor", filter_key, sessionManagement.getSalePersonCode());
        call.enqueue(new Callback<RoleMasterModel>() {
            @Override
            public void onResponse(Call<RoleMasterModel> call, Response<RoleMasterModel> response) {
                try {
                    if (response.isSuccessful()) {
                        loading_item.setVisibility(View.GONE);
                        RoleMasterModel roleMasterModelList = response.body();
                        if (roleMasterModelList != null && roleMasterModelList.condition) {
                            List<RoleMasterModel.Data> rolemasterList = roleMasterModelList.data;
                            if (rolemasterList != null && rolemasterList.size() > 0) {
                                roleMasterTableList_gl.clear();
                                roleMasterTableList_gl.addAll(rolemasterList);

                                Collections.sort(roleMasterTableList_gl, new Comparator<RoleMasterModel.Data>() {
                                    @Override
                                    public int compare(RoleMasterModel.Data o1, RoleMasterModel.Data o2) {
                                        return o1.name.compareTo(o2.name);
                                    }
                                });

                                for (int i = 0; i < roleMasterTableList_gl.size(); i++) {
                                    for (int j = i + 1; j < roleMasterTableList_gl.size(); j++) {
                                        if (roleMasterTableList_gl.get(i).name.equals(roleMasterTableList_gl.get(j).name)) {
                                            roleMasterTableList_gl.remove(j);
                                            j--;
                                        }
                                    }
                                }
                                setAdapter();
                            }
                        } else {
                            listEmptyResponseUI();
                            MDToast.makeText(getActivity(), roleMasterModelList.message, Toast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<RoleMasterModel> call, Throwable t) {

            }
        });
    }

    //todo adapter for distributor drop down...
    private void setAdapter() {
        RoleMasterAdapter roleMasterAdapter = new RoleMasterAdapter(getActivity(), roleMasterTableList_gl);
        order_recycler_view_list.setAdapter(roleMasterAdapter);
        roleMasterAdapter.setOnClick(this);
    }

    //todo order details api hitting,...
    private void orderDetailsApi(String distributor_name, String start_date, String end_date, String flag) {
        if (NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
            loading_item.setVisibility(View.VISIBLE);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("zone", "");
            jsonObject.addProperty("distributor", distributor_name != "" ? distributor_name : "");
            jsonObject.addProperty("status", "");
            jsonObject.addProperty("start_date", DateTimeUtilsCustome.getDateRetrunIntoYYMMDD(start_date));
            jsonObject.addProperty("end_date", DateTimeUtilsCustome.getDateRetrunIntoYYMMDD(end_date));
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            Call<List<ZoneOrDistributorWiseDetailsModel>> call = mAPIService.zoneDistributorWiseOrderList(jsonObject);
            call.enqueue(new Callback<List<ZoneOrDistributorWiseDetailsModel>>() {
                @Override
                public void onResponse(Call<List<ZoneOrDistributorWiseDetailsModel>> call, Response<List<ZoneOrDistributorWiseDetailsModel>> response) {
                    try {
                        if (response.isSuccessful()) {
                            loading_item.setVisibility(View.GONE);
                            List<ZoneOrDistributorWiseDetailsModel> responseList = response.body();
                            if (responseList != null && responseList.size() > 0) {
                                zoneOrDistributorWiseModelList_gl.clear();
                                zoneOrDistributorWiseModelList_gl.addAll(responseList);
                                if (flag.equals("order_detail_list")) {
                                    bindOrderDetailList(responseList);
                                    MDToast.makeText(getActivity(), "Data Fetch Successful !", Toast.LENGTH_SHORT, MDToast.TYPE_SUCCESS).show();
                                } else {
                                    MDToast.makeText(getActivity(), responseList.get(0).message, Toast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();
                                }

                            } else {
                                listEmptyResponseUI();
                                Toast.makeText(getActivity(), responseList.size() > 0 ? "No Record found" : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(getActivity(), response.message() + "wrong code:" + response.code(), Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        loading_item.setVisibility(View.GONE);
                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "report_list", getActivity());
                    }
                }

                @Override
                public void onFailure(Call<List<ZoneOrDistributorWiseDetailsModel>> call, Throwable t) {
                    loading_item.setVisibility(View.GONE);
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "report_list", getActivity());
                }
            });
        } else {
            Toast.makeText(getActivity(), "Please wait for online connection", Toast.LENGTH_SHORT).show();
        }

    }

    //todo order details list adapter bind...
    private void bindOrderDetailList(List<ZoneOrDistributorWiseDetailsModel> zoneOrDistributorList) {
        ZoneOrDistributorDetailsListAdapter detailsListAdapter = new ZoneOrDistributorDetailsListAdapter(getActivity(), zoneOrDistributorList, "order_details_view");
        order_recycler_view_list.setAdapter(detailsListAdapter);
        order_recycler_view_list.setVisibility(View.VISIBLE);
        submitUIRefresh();
    }

    //todo set show UI on submit details...
    public void submitUIRefresh() {
        search_reports_item_layout.setVisibility(View.VISIBLE);
        submit_order_list.setVisibility(View.GONE);
        clear_order_list.setVisibility(View.VISIBLE);
        et_order_distributor_name.setEnabled(false);
        et_start_date.setEnabled(false);
        et_end_date.setEnabled(false);
        filter_download_floating_btn.setVisibility(View.VISIBLE);
        iv_refresh_details.setVisibility(View.GONE);
    }

    //todo clear button UI refreshing..
    public void clearUIRefresh() {
        search_reports_item_layout.setVisibility(View.VISIBLE);
        order_recycler_view_list.setVisibility(View.GONE);
        submit_order_list.setVisibility(View.VISIBLE);
        clear_order_list.setVisibility(View.GONE);
        filter_download_floating_btn.setVisibility(View.GONE);
        et_order_distributor_name.setText("");
        et_order_distributor_name.clearFocus();
        et_order_distributor_name.setEnabled(true);
        frame_order_distributor_list_layout.setVisibility(View.GONE);
        distributor_name_text_input_layout.setStartIconDrawable(R.drawable.ic_baseline_search_24);
        et_start_date.setText("");
        et_start_date.clearFocus();
        et_start_date.setEnabled(true);
        et_end_date.setText("");
        et_end_date.clearFocus();
        et_end_date.setEnabled(true);
        iv_refresh_details.setVisibility(View.VISIBLE);
    }

    //todo set UI on else case on list null...
    public void listEmptyResponseUI() {
        iv_refresh_details.setVisibility(View.VISIBLE);
        loading_item.setVisibility(View.GONE);
        frame_order_distributor_list_layout.setVisibility(View.GONE);
        filter_download_floating_btn.setVisibility(View.GONE);
        submit_order_list.setVisibility(View.VISIBLE);
        clear_order_list.setVisibility(View.GONE);
        et_order_distributor_name.setEnabled(true);
        et_start_date.setEnabled(true);
        et_end_date.setEnabled(true);
    }

    //todo order detail distributor item click listener...
    @Override
    public void onItemClick(int pos) {
        frame_order_distributor_list_layout.setVisibility(View.GONE);
        if (roleMasterTableList_gl != null) {
            et_order_distributor_name.setText(roleMasterTableList_gl.get(0).name);
            frame_order_distributor_list_layout.setVisibility(View.GONE);
        } else {
            frame_order_distributor_list_layout.setVisibility(View.VISIBLE);
        }
    }

}