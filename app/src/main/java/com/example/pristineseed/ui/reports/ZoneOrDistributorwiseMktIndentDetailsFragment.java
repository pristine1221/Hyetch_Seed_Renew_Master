package com.example.pristineseed.ui.reports;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import com.example.pristineseed.ui.adapter.reportsAdapter.zoneOrDistributorWiseDetails.DistributorWiseAdapter;
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

public class ZoneOrDistributorwiseMktIndentDetailsFragment extends Fragment implements DistributorWiseAdapter.OnItemClickListner, ZoneWiseAdapter.OnZoneItemClick, RoleMasterAdapter.OnItemClickListner {

    private SessionManagement sessionManagement;
    private TextInputLayout distributor_name_text_input_layout, zone_text_input_layout, mkt_start_date_input_layout, mkt_end_date_input_layout;
    private TextInputEditText et_mkt_distributor_name, et_mkt_zone, et_start_date, et_end_date;
    private MaterialButton submit_mkt_list, clear_mkt_list;
    private MaterialCardView frame_mkt_distributor_list_layout, frame_mkt_zone_list_layout;
    private RecyclerView rv_mkt_distributor_list, rv_mkt_zone_list, mkt_recycler_view_list;
    private LinearLayoutManager mkt_distributor_layout_manager, mkt_zone_layout_manager, mkt_indent_list_layout_manager;
    private FloatingActionButton filter_download_floating_btn;
    private View view_weight;
    private LinearLayout parent_layout;
    private ProgressBar loading_item;
    private RelativeLayout search_reports_item_layout;
    private ImageView iv_refresh_details;
    private List<ZoneOrDistributorWiseDetailsModel> zoneOrDistributorWiseModelList_gl = new ArrayList<>();
    private ZoneOrDistributorWiseDetailsModel zoneOrDistributorWiseModel = null;
    private DistributorWiseAdapter distributorWiseAdapter;
    private ZoneWiseAdapter zoneWiseAdapter;
    private ProgressBar mkt_progressBar;
    private TextView mkt_progress_percentage_count, mkt_text_percent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_zone_or_distributorwise_mkt_indent_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManagement = new SessionManagement(getActivity());
        initView(view);

        /*et_mkt_distributor_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus && et_mkt_distributor_name.isCursorVisible()) {
                    frame_mkt_distributor_list_layout.setVisibility(View.VISIBLE);
                    frame_mkt_zone_list_layout.setVisibility(View.GONE);
                    distributor_name_text_input_layout.setStartIconDrawable(null);
                    getDistributor("");
//                    mktIndentDetailsApi("","","","","distributor_name");
                    view_weight.setVisibility(View.VISIBLE);
                }else {
                    if (!et_mkt_distributor_name.getText().toString().trim().equalsIgnoreCase("")) {
                        distributor_name_text_input_layout.setStartIconDrawable(null);
                    } else {
                        distributor_name_text_input_layout.setStartIconDrawable(R.drawable.ic_baseline_search_24);
                        frame_mkt_distributor_list_layout.setVisibility(View.GONE);
                    }
                }
            }
        });
*/
        et_mkt_distributor_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                et_mkt_distributor_name.setSelection(s.toString().length());
                if (!s.toString().equalsIgnoreCase("") && et_mkt_distributor_name.isCursorVisible()) {
                    frame_mkt_distributor_list_layout.setVisibility(View.VISIBLE);
                    getDistributor(s.toString());
//                    mktIndentDetailsApi(s.toString(), "", "", "","distributor_name");
                    view_weight.setVisibility(View.VISIBLE);
                } else {
                    frame_mkt_distributor_list_layout.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

      /*  et_mkt_zone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus && et_mkt_zone.isCursorVisible()) {
                    frame_mkt_zone_list_layout.setVisibility(View.VISIBLE);
                    frame_mkt_distributor_list_layout.setVisibility(View.GONE);
                    zone_text_input_layout.setStartIconDrawable(null);
                    mktIndentDetailsApi("","","","","zone_name");
                    view_weight.setVisibility(View.VISIBLE);
                }else {
                    if (!et_mkt_zone.getText().toString().trim().equalsIgnoreCase("")) {
                        zone_text_input_layout.setStartIconDrawable(null);
                    } else {
                        zone_text_input_layout.setStartIconDrawable(R.drawable.ic_baseline_search_24);
                        frame_mkt_zone_list_layout.setVisibility(View.GONE);
                    }
                }
            }
        });*/

        et_mkt_zone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                et_mkt_zone.setSelection(s.toString().length());
                if (!s.toString().equalsIgnoreCase("") && et_mkt_zone.isCursorVisible()) {
                    frame_mkt_zone_list_layout.setVisibility(View.VISIBLE);
                    mktIndentDetailsApi("",s.toString(), "", "", "zone_name");
                    view_weight.setVisibility(View.VISIBLE);
                } else {
                    frame_mkt_zone_list_layout.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

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


        et_end_date.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (!et_start_date.getText().toString().equalsIgnoreCase("")) {
//                    et_end_date.setEnabled(true);
                        MaterialDatePicker materialDatePicker = new MaterialDatePicker(getActivity());
                        materialDatePicker.disableDatesAccToStartDate(et_end_date, et_start_date.getText().toString());
                    } else {
                        MDToast.makeText(getActivity(), "Select Start Date First", Toast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();
//                    et_end_date.setEnabled(false);
                    }
                }
                return false;
            }
        });

        parent_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    frame_mkt_distributor_list_layout.setVisibility(View.GONE);
                    frame_mkt_zone_list_layout.setVisibility(View.GONE);
                    parent_layout.requestFocus();
                }
                return true;
            }
        });

        iv_refresh_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_mkt_distributor_name.setText("");
                et_mkt_distributor_name.clearFocus();
                frame_mkt_distributor_list_layout.setVisibility(View.GONE);
                distributor_name_text_input_layout.setStartIconDrawable(R.drawable.ic_baseline_search_24);
                et_mkt_zone.setText("");
                et_mkt_zone.clearFocus();
                frame_mkt_zone_list_layout.setVisibility(View.GONE);
                zone_text_input_layout.setStartIconDrawable(R.drawable.ic_baseline_search_24);
                et_start_date.setText("");
                et_start_date.clearFocus();
                et_end_date.setText("");
                et_end_date.clearFocus();
            }
        });

        submit_mkt_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSubmitMKTDetails( et_mkt_distributor_name.getText().toString(),et_mkt_zone.getText().toString(),et_start_date.getText().toString(), et_end_date.getText().toString());
            }
        });


    }

    //todo initialization...
    private void initView(View view) {
        distributor_name_text_input_layout = view.findViewById(R.id.distributor_name_text_input_layout);
        zone_text_input_layout = view.findViewById(R.id.zone_text_input_layout);
        mkt_start_date_input_layout = view.findViewById(R.id.mkt_start_date_input_layout);
        mkt_end_date_input_layout = view.findViewById(R.id.mkt_end_date_input_layout);
        et_mkt_distributor_name = view.findViewById(R.id.et_mkt_distributor_name);
        et_mkt_zone = view.findViewById(R.id.et_mkt_zone);
        et_start_date = view.findViewById(R.id.et_start_date);
        et_end_date = view.findViewById(R.id.et_end_date);
        submit_mkt_list = view.findViewById(R.id.submit_mkt_list);
        clear_mkt_list = view.findViewById(R.id.clear_mkt_list);
        frame_mkt_distributor_list_layout = view.findViewById(R.id.frame_mkt_distributor_list_layout);
        frame_mkt_zone_list_layout = view.findViewById(R.id.frame_mkt_zone_list_layout);
        rv_mkt_distributor_list = view.findViewById(R.id.rv_mkt_distributor_list);
        rv_mkt_zone_list = view.findViewById(R.id.rv_mkt_zone_list);
        mkt_recycler_view_list = view.findViewById(R.id.mkt_recycler_view_list);
        filter_download_floating_btn = view.findViewById(R.id.filter_download_floating_btn);
        loading_item = view.findViewById(R.id.loading_item);
        view_weight = view.findViewById(R.id.view_weight);
        parent_layout = view.findViewById(R.id.parent_relative_layout);
        iv_refresh_details = view.findViewById(R.id.iv_refresh_details);
        search_reports_item_layout = view.findViewById(R.id.search_reports_item_layout);
        //todo..
        mkt_progressBar = view.findViewById(R.id.mkt_progressBar);
        mkt_progress_percentage_count = view.findViewById(R.id.mkt_progress_percentage_count);
        mkt_text_percent = view.findViewById(R.id.mkt_text_percent);

        //todo set organizer layout manager...
        mkt_distributor_layout_manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_mkt_distributor_list.setLayoutManager(mkt_distributor_layout_manager);

        //todo set hybrid layout manager...
        mkt_zone_layout_manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_mkt_zone_list.setLayoutManager(mkt_zone_layout_manager);

        //todo set dispatch layout manager...
        mkt_indent_list_layout_manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mkt_recycler_view_list.setLayoutManager(mkt_indent_list_layout_manager);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    //todo submit details..
    private void setSubmitMKTDetails(String distributor_name,String zone_name,String start_date, String end_date) {
        if (NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
            if(et_start_date.getText().toString().equalsIgnoreCase(""))
                MDToast.makeText(getActivity(), "Start Date Required!", Toast.LENGTH_SHORT, MDToast.TYPE_ERROR);
            else if (et_end_date.getText().toString().equalsIgnoreCase(""))
                MDToast.makeText(getActivity(), "End Date Required!", Toast.LENGTH_SHORT, MDToast.TYPE_ERROR);
            else
                mktIndentDetailsApi("","",start_date, end_date, "mkt_indent_list");

        }else {
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
                            iv_refresh_details.setVisibility(View.VISIBLE);
                            loading_item.setVisibility(View.GONE);
                            frame_mkt_distributor_list_layout.setVisibility(View.GONE);
                            filter_download_floating_btn.setVisibility(View.GONE);
                            submit_mkt_list.setVisibility(View.VISIBLE);
                            clear_mkt_list.setVisibility(View.GONE);
                            et_mkt_distributor_name.setEnabled(true);
                            et_mkt_zone.setEnabled(true);
                            et_start_date.setEnabled(true);
                            et_end_date.setEnabled(true);
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
        rv_mkt_distributor_list.setAdapter(roleMasterAdapter);
        roleMasterAdapter.setOnClick(this);
    }

    //todo mkt indent api hitting,...
    private void mktIndentDetailsApi( String distributor_name, String zone_name, String start_date, String end_date, String flag) {
        if (NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
            loading_item.setVisibility(View.VISIBLE);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("zone", zone_name != "" ? zone_name : "");
            jsonObject.addProperty("distributor", distributor_name != "" ? distributor_name : "");
            jsonObject.addProperty("status", "");
            jsonObject.addProperty("start_date", DateTimeUtilsCustome.getDateRetrunIntoYYMMDD(start_date));
            jsonObject.addProperty("end_date", DateTimeUtilsCustome.getDateRetrunIntoYYMMDD(end_date));
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            Call<List<ZoneOrDistributorWiseDetailsModel>> call = mAPIService.zoneDistributorWiseMktIndentList(jsonObject);
            call.enqueue(new Callback<List<ZoneOrDistributorWiseDetailsModel>>() {
                @Override
                public void onResponse(Call<List<ZoneOrDistributorWiseDetailsModel>> call, Response<List<ZoneOrDistributorWiseDetailsModel>> response) {
                    try {
                        if (response.isSuccessful()) {
                            loading_item.setVisibility(View.GONE);
                            List<ZoneOrDistributorWiseDetailsModel> responseList = response.body();
                            if (responseList != null && responseList.size() > 0 && responseList.get(0).condition) {
                                zoneOrDistributorWiseModelList_gl.clear();
                                zoneOrDistributorWiseModelList_gl.addAll(responseList);

                                Collections.sort(zoneOrDistributorWiseModelList_gl, new Comparator<ZoneOrDistributorWiseDetailsModel>() {
                                    @Override
                                    public int compare(ZoneOrDistributorWiseDetailsModel o1, ZoneOrDistributorWiseDetailsModel o2) {
                                        return o1.zone_name.compareTo(o2.zone_name);
                                    }
                                });

                                for (int i = 0; i < zoneOrDistributorWiseModelList_gl.size(); i++) {
                                    for (int j = i + 1; j < zoneOrDistributorWiseModelList_gl.size(); j++) {
                                        if (zoneOrDistributorWiseModelList_gl.get(i).zone_name.equals(zoneOrDistributorWiseModelList_gl.get(j).zone_name)) {
                                            zoneOrDistributorWiseModelList_gl.remove(j);
                                            j--;
                                        }
                                    }
                                }

                                if (flag.equals("distributor_name")) {
//                                    bindMktDistributorName();
                                } else if (flag.equals("zone_name")) {
                                    bindMktZoneName();
                                } else if (flag.equals("mkt_indent_list")) {
                                    bindMktIndentList(responseList);
                                    MDToast.makeText(getActivity(), "Data Fetch Successful !", Toast.LENGTH_SHORT, MDToast.TYPE_SUCCESS).show();
                                } else {
                                    MDToast.makeText(getActivity(), responseList.get(0).message, Toast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();
                                }

                            } else {
                                iv_refresh_details.setVisibility(View.VISIBLE);
                                loading_item.setVisibility(View.GONE);
                                frame_mkt_distributor_list_layout.setVisibility(View.GONE);
                                filter_download_floating_btn.setVisibility(View.GONE);
//                                submit_sdn_list.setVisibility(View.VISIBLE);
//                                clear_sdn_list.setVisibility(View.GONE);
                                et_mkt_distributor_name.setEnabled(true);
                                et_mkt_zone.setEnabled(true);
                                et_start_date.setEnabled(true);
                                et_end_date.setEnabled(true);
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

    //todo mkt distributor name adapter bind...
    private void bindMktDistributorName() {
        DistributorWiseAdapter distributorWiseAdapter = new DistributorWiseAdapter(getActivity(), zoneOrDistributorWiseModelList_gl);
        rv_mkt_distributor_list.setAdapter(distributorWiseAdapter);
        distributorWiseAdapter.setClickListner(this);

    }

    //todo mkt zone name adapter bind...
    private void bindMktZoneName() {
        ZoneWiseAdapter zoneWiseAdapter = new ZoneWiseAdapter(getActivity(), zoneOrDistributorWiseModelList_gl);
        rv_mkt_zone_list.setAdapter(zoneWiseAdapter);
        zoneWiseAdapter.setClickListner(this);
    }

    //todo mkt indent details list adapter bind...
    private void bindMktIndentList(List<ZoneOrDistributorWiseDetailsModel> zoneOrDistributorList) {
        ZoneOrDistributorDetailsListAdapter detailsListAdapter = new ZoneOrDistributorDetailsListAdapter(getActivity(), zoneOrDistributorList);
        mkt_recycler_view_list.setAdapter(detailsListAdapter);
        search_reports_item_layout.setVisibility(View.VISIBLE);
        mkt_recycler_view_list.setVisibility(View.VISIBLE);
    }


    @Override
    public void onDistributorItemClick(int pos) {
        frame_mkt_distributor_list_layout.setVisibility(View.GONE);
        zoneOrDistributorWiseModel = zoneOrDistributorWiseModelList_gl.get(pos);
        if (zoneOrDistributorWiseModel != null) {
            et_mkt_distributor_name.setText(zoneOrDistributorWiseModel.ditributor);
            frame_mkt_distributor_list_layout.setVisibility(View.GONE);
        } else {
            frame_mkt_distributor_list_layout.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onZoneItemClick(int pos) {
        frame_mkt_zone_list_layout.setVisibility(View.GONE);
        zoneOrDistributorWiseModel = zoneOrDistributorWiseModelList_gl.get(pos);
        if (zoneOrDistributorWiseModel != null) {
            et_mkt_zone.setText(zoneOrDistributorWiseModel.zone_name);
            frame_mkt_zone_list_layout.setVisibility(View.GONE);
        } else {
            frame_mkt_zone_list_layout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemClick(int pos) {
        frame_mkt_distributor_list_layout.setVisibility(View.GONE);
        if (roleMasterTableList_gl != null) {
            et_mkt_distributor_name.setText(roleMasterTableList_gl.get(0).name);
            frame_mkt_distributor_list_layout.setVisibility(View.GONE);
        } else {
            frame_mkt_distributor_list_layout.setVisibility(View.VISIBLE);
        }
    }
}