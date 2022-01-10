package com.example.pristineseed.ui.reports;

import static android.app.Notification.DEFAULT_VIBRATE;
import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
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
import com.example.pristineseed.model.reportModel.PLDandSownAcreViewModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.adapter.reportsAdapter.sownAcreAdapters.SownAcreListViewAdapter;
import com.example.pristineseed.ui.adapter.reportsAdapter.sownAcreAdapters.PLDandSownItemNameAdapter;
import com.example.pristineseed.ui.adapter.reportsAdapter.sownAcreAdapters.SownOrganiserNameListAdapter;
import com.example.pristineseed.ui.adapter.reportsAdapter.sownAcreAdapters.SownVillageListAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SownAcresFragment extends Fragment implements SownOrganiserNameListAdapter.OnItemClickListner, PLDandSownItemNameAdapter.OnItemClick, SownVillageListAdapter.OnVillageClickListner {

    private TextInputLayout sa_organser_name_text_input_layout, sa_item_name_text_input_layout, sa_village_name_text_input_layout;
    private TextInputEditText et_sow_acre_organizer_name, et_sow_acre_item_name, et_sow_acre_village_name;
    private ImageView iv_refresh_details;
    private View view_weight, village_view;
    private MaterialButton submit_sown_acre_list, clear_sow_acre_list;
    private RecyclerView sown_acre_recycler_view_list, rv_sow_acre_organiser_list, rv_sow_acre_item_list, rv_sow_acre_village_list;
    private LinearLayoutManager sown_acre_list_layout_manager, sow_org_list_layout_manager, sown_item_list_layout_manger, sown_village_list_layout_manager;
    private MaterialCardView frame_sow_acre_organiser_list_layout, frame_sow_acre_item_list_layout, frame_sow_acre_village_list_layout;
    private LinearLayout parent_relative_layout;
    private RelativeLayout sow_acre_search_reports_item_layout;
    private FloatingActionButton filter_download_floating_btn;
    private ProgressBar loading_item, progressBar;
    private List<PLDandSownAcreViewModel> sownAcreViewModelList_gl = new ArrayList<>();
    private PLDandSownAcreViewModel dueInspectionModel;
    private SownAcreListViewAdapter listViewAdapter;
    private TextView progress_percentage_count, text_percent;
    private SessionManagement sessionManagement;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sown_acres, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        sessionManagement = new SessionManagement(getActivity());

        et_sow_acre_organizer_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && et_sow_acre_organizer_name.isCursorVisible()) {
                    frame_sow_acre_organiser_list_layout.setVisibility(View.VISIBLE);
                    frame_sow_acre_item_list_layout.setVisibility(View.GONE);
                    frame_sow_acre_item_list_layout.setFocusable(false);
                    frame_sow_acre_village_list_layout.setVisibility(View.GONE);
                    sa_organser_name_text_input_layout.setStartIconDrawable(null);
                    et_sow_acre_organizer_name.setEnabled(true);
                    getSownAcreList("", "", "", "organiser_name");
                    view_weight.setVisibility(View.VISIBLE);
                    village_view.setVisibility(View.GONE);
                } else {
                    if (!et_sow_acre_organizer_name.getText().toString().trim().equalsIgnoreCase("")) {
                        sa_organser_name_text_input_layout.setStartIconDrawable(null);
                    } else {
                        sa_organser_name_text_input_layout.setStartIconDrawable(R.drawable.ic_baseline_search_24);
                        frame_sow_acre_organiser_list_layout.setVisibility(View.GONE);
                    }
                }
            }
        });

        et_sow_acre_organizer_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                et_sow_acre_organizer_name.setSelection(s.toString().length());
                if (!s.toString().equalsIgnoreCase("") && et_sow_acre_organizer_name.isCursorVisible()) {
                    frame_sow_acre_organiser_list_layout.setVisibility(View.VISIBLE);
                    view_weight.setVisibility(View.VISIBLE);
                    et_sow_acre_organizer_name.setEnabled(true);
                    getSownAcreList(s.toString(), "", "", "organiser_name");
                } else {
                    frame_sow_acre_organiser_list_layout.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        et_sow_acre_item_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && et_sow_acre_item_name.isCursorVisible()) {
                    frame_sow_acre_item_list_layout.setVisibility(View.VISIBLE);
                    frame_sow_acre_organiser_list_layout.setVisibility(View.GONE);
                    frame_sow_acre_village_list_layout.setVisibility(View.GONE);
                    sa_item_name_text_input_layout.setStartIconDrawable(null);
                    et_sow_acre_item_name.setEnabled(true);
                    getSownAcreList("", "", "", "item_name");
                    view_weight.setVisibility(View.VISIBLE);
                    village_view.setVisibility(View.GONE);
                } else {
                    if (!et_sow_acre_item_name.getText().toString().trim().equalsIgnoreCase("")) {
                        sa_item_name_text_input_layout.setStartIconDrawable(null);
                    } else {
                        sa_item_name_text_input_layout.setStartIconDrawable(R.drawable.ic_baseline_search_24);
                        frame_sow_acre_item_list_layout.setVisibility(View.GONE);
                    }
                }
            }
        });

        et_sow_acre_item_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                et_sow_acre_item_name.setSelection(s.toString().length());
                if (!s.toString().equalsIgnoreCase("") && et_sow_acre_item_name.isCursorVisible()) {
                    frame_sow_acre_item_list_layout.setVisibility(View.VISIBLE);
                    et_sow_acre_item_name.setEnabled(true);
                    getSownAcreList("", s.toString(), "", "item_name");
                    view_weight.setVisibility(View.VISIBLE);
                } else {
                    frame_sow_acre_item_list_layout.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        et_sow_acre_village_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && et_sow_acre_village_name.isCursorVisible()) {
                    frame_sow_acre_village_list_layout.setVisibility(View.VISIBLE);
                    frame_sow_acre_item_list_layout.setVisibility(View.GONE);
                    frame_sow_acre_organiser_list_layout.setVisibility(View.GONE);
                    sa_village_name_text_input_layout.setStartIconDrawable(null);
                    et_sow_acre_village_name.setEnabled(true);
                    getSownAcreList("", "", "", "village");
                    view_weight.setVisibility(View.GONE);
                    village_view.setVisibility(View.VISIBLE);
                } else {
                    if (!et_sow_acre_village_name.getText().toString().trim().equalsIgnoreCase("")) {
                        sa_village_name_text_input_layout.setStartIconDrawable(null);
                    } else {
                        sa_village_name_text_input_layout.setStartIconDrawable(R.drawable.ic_baseline_search_24);
                        frame_sow_acre_village_list_layout.setVisibility(View.GONE);
                    }
                }
            }
        });

        et_sow_acre_village_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                et_sow_acre_village_name.setSelection(s.toString().length());
                if (!s.toString().equalsIgnoreCase("") && et_sow_acre_village_name.isCursorVisible()) {
                    frame_sow_acre_village_list_layout.setVisibility(View.VISIBLE);
                    et_sow_acre_village_name.setEnabled(true);
                    getSownAcreList("", "", s.toString(), "village");
                    village_view.setVisibility(View.VISIBLE);
                } else {
                    frame_sow_acre_village_list_layout.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        iv_refresh_details.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                et_sow_acre_organizer_name.setText("");
                et_sow_acre_organizer_name.clearFocus();
                frame_sow_acre_organiser_list_layout.setVisibility(View.GONE);
                sa_organser_name_text_input_layout.setStartIconDrawable(R.drawable.ic_baseline_search_24);
                et_sow_acre_item_name.setText("");
                et_sow_acre_item_name.clearFocus();
                frame_sow_acre_item_list_layout.setVisibility(View.GONE);
                sa_item_name_text_input_layout.setStartIconDrawable(R.drawable.ic_baseline_search_24);
                et_sow_acre_village_name.setText("");
                et_sow_acre_village_name.clearFocus();
                frame_sow_acre_village_list_layout.setVisibility(View.GONE);
                sa_village_name_text_input_layout.setStartIconDrawable(R.drawable.ic_baseline_search_24);
                return true;
            }
        });

        parent_relative_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    frame_sow_acre_organiser_list_layout.setVisibility(View.GONE);
                    frame_sow_acre_village_list_layout.setVisibility(View.GONE);
                    frame_sow_acre_item_list_layout.setVisibility(View.GONE);
                    parent_relative_layout.requestFocus();
                }
                return true;
            }
        });

        submit_sown_acre_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSubmitSownAcreList();
            }
        });

        clear_sow_acre_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sow_acre_search_reports_item_layout.setVisibility(View.VISIBLE);
                sown_acre_recycler_view_list.setVisibility(View.GONE);
                et_sow_acre_organizer_name.setText("");
                et_sow_acre_organizer_name.clearFocus();
                et_sow_acre_organizer_name.setEnabled(true);
                sa_organser_name_text_input_layout.setStartIconDrawable(R.drawable.ic_baseline_search_24);
                et_sow_acre_item_name.setText("");
                et_sow_acre_item_name.clearFocus();
                et_sow_acre_item_name.setEnabled(true);
                sa_item_name_text_input_layout.setStartIconDrawable(R.drawable.ic_baseline_search_24);
                et_sow_acre_village_name.setText("");
                et_sow_acre_village_name.clearFocus();
                et_sow_acre_village_name.setEnabled(true);
                sa_village_name_text_input_layout.setStartIconDrawable(R.drawable.ic_baseline_search_24);
                clear_sow_acre_list.setVisibility(View.GONE);
                submit_sown_acre_list.setVisibility(View.VISIBLE);
                filter_download_floating_btn.setVisibility(View.GONE);
            }
        });

        filter_download_floating_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HitDownloadFileApi(ApiUtils.BASE_URL, et_sow_acre_organizer_name.getText().toString(), et_sow_acre_item_name.getText().toString(), et_sow_acre_village_name.getText().toString());
                progressBar.setVisibility(View.VISIBLE);
                filter_download_floating_btn.setVisibility(View.GONE);
            }
        });
    }

    private void initView(View view) {
        sa_organser_name_text_input_layout = view.findViewById(R.id.sa_organser_name_text_input_layout);
        sa_item_name_text_input_layout = view.findViewById(R.id.sa_item_name_text_input_layout);
        sa_village_name_text_input_layout = view.findViewById(R.id.sa_village_name_text_input_layout);
        et_sow_acre_organizer_name = view.findViewById(R.id.et_sow_acre_organizer_name);
        et_sow_acre_item_name = view.findViewById(R.id.et_sow_acre_item_name);
        et_sow_acre_village_name = view.findViewById(R.id.et_sow_acre_village_name);
        iv_refresh_details = view.findViewById(R.id.iv_refresh_details);
        view_weight = view.findViewById(R.id.view_weight);
        village_view = view.findViewById(R.id.village_view);
        submit_sown_acre_list = view.findViewById(R.id.submit_sown_acre_list);
        clear_sow_acre_list = view.findViewById(R.id.clear_sow_acre_list);
        sown_acre_recycler_view_list = view.findViewById(R.id.sown_acre_recycler_view_list);
        rv_sow_acre_organiser_list = view.findViewById(R.id.rv_sow_acre_organiser_list);
        rv_sow_acre_item_list = view.findViewById(R.id.rv_sow_acre_item_list);
        rv_sow_acre_village_list = view.findViewById(R.id.rv_sow_acre_village_list);
        frame_sow_acre_organiser_list_layout = view.findViewById(R.id.frame_sow_acre_organiser_list_layout);
        frame_sow_acre_item_list_layout = view.findViewById(R.id.frame_sow_acre_item_list_layout);
        frame_sow_acre_village_list_layout = view.findViewById(R.id.frame_sow_acre_village_list_layout);
        parent_relative_layout = view.findViewById(R.id.parent_relative_layout);
        sow_acre_search_reports_item_layout = view.findViewById(R.id.sow_acre_search_reports_item_layout);
        loading_item = view.findViewById(R.id.loading_item);
        filter_download_floating_btn = view.findViewById(R.id.filter_download_floating_btn);
        progressBar = view.findViewById(R.id.progressBar);
        progress_percentage_count = view.findViewById(R.id.progress_percentage_count);
        text_percent = view.findViewById(R.id.text_percent);

        //todo set sown acre list manager...
        sown_acre_list_layout_manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        sown_acre_recycler_view_list.setLayoutManager(sown_acre_list_layout_manager);

        //todo set sown organiser list layout manger...
        sow_org_list_layout_manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_sow_acre_organiser_list.setLayoutManager(sow_org_list_layout_manager);

        //todo set sown item list layout manager...
        sown_item_list_layout_manger = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_sow_acre_item_list.setLayoutManager(sown_item_list_layout_manger);

        //todo set sown village list layout manager...
        sown_village_list_layout_manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_sow_acre_village_list.setLayoutManager(sown_village_list_layout_manager);
    }

    @Override
    public void onResume() {
        super.onResume();
        frame_sow_acre_organiser_list_layout.setFocusable(false);
        frame_sow_acre_item_list_layout.setFocusable(false);
        frame_sow_acre_village_list_layout.setFocusable(false);
    }

    public void setSubmitSownAcreList() {
        if (NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
            if (et_sow_acre_organizer_name.getText().toString().equalsIgnoreCase("")
                    && et_sow_acre_item_name.getText().toString().equalsIgnoreCase("")
                    && et_sow_acre_village_name.getText().toString().equalsIgnoreCase("")) {
                Toast.makeText(getActivity(), "Data Fetch Successfully", Toast.LENGTH_SHORT).show();
                getSownAcreList("","","","sown_acre_list");
                submit_sown_acre_list.setVisibility(View.GONE);
                clear_sow_acre_list.setVisibility(View.VISIBLE);
                filter_download_floating_btn.setVisibility(View.VISIBLE);
                view_weight.setVisibility(View.GONE);
                village_view.setVisibility(View.GONE);
                et_sow_acre_organizer_name.setEnabled(false);
                et_sow_acre_item_name.setEnabled(false);
                et_sow_acre_village_name.setEnabled(false);
            }
            else if(!et_sow_acre_organizer_name.getText().toString().equalsIgnoreCase("")
            || !et_sow_acre_item_name.getText().toString().equalsIgnoreCase("")
            || !et_sow_acre_village_name.getText().toString().equalsIgnoreCase("")){
                submit_sown_acre_list.setVisibility(View.GONE);
                clear_sow_acre_list.setVisibility(View.VISIBLE);
                getSownAcreList(et_sow_acre_organizer_name.getText().toString(),et_sow_acre_item_name.getText().toString(),et_sow_acre_village_name.getText().toString(),"sown_acre_list");
                filter_download_floating_btn.setVisibility(View.VISIBLE);
                et_sow_acre_organizer_name.setEnabled(false);
                et_sow_acre_item_name.setEnabled(false);
                et_sow_acre_village_name.setEnabled(false);
            }
        } else {
            Toast.makeText(getActivity(), "No Network Connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void getSownAcreList(String organiser_name, String item_name, String village, String flag) {
        if (NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
            loading_item.setVisibility(View.VISIBLE);
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            Call<List<PLDandSownAcreViewModel>> call = mAPIService.sownAcreViewList(organiser_name, item_name, village, sessionManagement.getUserEmail());

            call.enqueue(new Callback<List<PLDandSownAcreViewModel>>() {
                @Override
                public void onResponse(Call<List<PLDandSownAcreViewModel>> call, Response<List<PLDandSownAcreViewModel>> response) {
                    try {
                        if (response.isSuccessful()) {
                            loading_item.setVisibility(View.GONE);
                            List<PLDandSownAcreViewModel> sownAcreViewListResponse = response.body();
                            if (sownAcreViewListResponse != null && sownAcreViewListResponse.size() > 0 && sownAcreViewListResponse.get(0).condition) {
                                sownAcreViewModelList_gl.clear();
                                sownAcreViewModelList_gl.addAll(sownAcreViewListResponse);

                                if (flag.equals("organiser_name")) {

                                    Collections.sort(sownAcreViewModelList_gl, new Comparator<PLDandSownAcreViewModel>() {
                                        @Override
                                        public int compare(PLDandSownAcreViewModel o1, PLDandSownAcreViewModel o2) {
                                            return o1.organizer_name.compareTo(o2.organizer_name);
                                        }
                                    });

                                    for (int i = 0; i < sownAcreViewModelList_gl.size(); i++) {
                                        for (int j = i + 1; j < sownAcreViewModelList_gl.size(); j++) {
                                            if (sownAcreViewModelList_gl.get(i).organizer_name.equals(sownAcreViewModelList_gl.get(j).organizer_name)) {
                                                sownAcreViewModelList_gl.remove(j);
                                                j--;
                                            }
                                        }
                                    }
                                    bindSownOrganiserListAdapter();
                                } else if (flag.equals("item_name")) {

                                    Collections.sort(sownAcreViewModelList_gl, new Comparator<PLDandSownAcreViewModel>() {
                                        @Override
                                        public int compare(PLDandSownAcreViewModel o1, PLDandSownAcreViewModel o2) {
                                            return o1.item_name.compareTo(o2.item_name);
                                        }
                                    });

                                    for (int i = 0; i < sownAcreViewModelList_gl.size(); i++) {
                                        for (int j = i + 1; j < sownAcreViewModelList_gl.size(); j++) {
                                            if (sownAcreViewModelList_gl.get(i).item_name.equals(sownAcreViewModelList_gl.get(j).item_name)) {
                                                sownAcreViewModelList_gl.remove(j);
                                                j--;
                                            }
                                        }
                                    }
                                    bindSownItemListAdapter();
                                } else if (flag.equals("village")) {

                                    Collections.sort(sownAcreViewModelList_gl, new Comparator<PLDandSownAcreViewModel>() {
                                        @Override
                                        public int compare(PLDandSownAcreViewModel o1, PLDandSownAcreViewModel o2) {
                                            return o1.village_name.compareTo(o2.village_name);
                                        }
                                    });

                                    for (int i = 0; i < sownAcreViewModelList_gl.size(); i++) {
                                        for (int j = i + 1; j < sownAcreViewModelList_gl.size(); j++) {
                                            if (sownAcreViewModelList_gl.get(i).village_name.equals(sownAcreViewModelList_gl.get(j).village_name)) {
                                                sownAcreViewModelList_gl.remove(j);
                                                j--;
                                            }
                                        }
                                    }
                                    bindSownVillageListAdapter();
                                }
                                else if(flag.equals("sown_acre_list")){
                                    bindSownListAdapter(sownAcreViewListResponse);
                                }else {
                                    Toast.makeText(getActivity(), sownAcreViewListResponse.get(0).message, Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                loading_item.setVisibility(View.GONE);
                                submit_sown_acre_list.setVisibility(View.VISIBLE);
                                filter_download_floating_btn.setVisibility(View.GONE);
                                clear_sow_acre_list.setVisibility(View.GONE);
                                et_sow_acre_organizer_name.setEnabled(true);
                                et_sow_acre_item_name.setEnabled(true);
                                et_sow_acre_village_name.setEnabled(true);
                                Toast.makeText(getActivity(), "No Record Found : ", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            loading_item.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<List<PLDandSownAcreViewModel>> call, Throwable t) {
                    loading_item.setVisibility(View.GONE);
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "report_list", getActivity());
                }
            });

        } else {
            Toast.makeText(getActivity(), "Please wait for online connection", Toast.LENGTH_SHORT).show();
        }
    }

    //todo organiser name list adapter binding....
    public void bindSownOrganiserListAdapter() {
        SownOrganiserNameListAdapter organiserNameListAdapter = new SownOrganiserNameListAdapter(getActivity(), sownAcreViewModelList_gl);
        rv_sow_acre_organiser_list.setAdapter(organiserNameListAdapter);
        organiserNameListAdapter.setOnOrgClickListner(this);
    }

    //todo item name list adapter binding....
    public void bindSownItemListAdapter() {
        PLDandSownItemNameAdapter itemListAdapter = new PLDandSownItemNameAdapter(getActivity(), sownAcreViewModelList_gl, "sown_item_view");
        rv_sow_acre_item_list.setAdapter(itemListAdapter);
        itemListAdapter.setItemClickLisner(this);
    }

    //todo village name list adapter binding....
    public void bindSownVillageListAdapter() {
        SownVillageListAdapter villageListAdapter = new SownVillageListAdapter(getActivity(), sownAcreViewModelList_gl);
        rv_sow_acre_village_list.setAdapter(villageListAdapter);
        villageListAdapter.setOnVillageClickLisner(this);
    }

    //todo full sown list adapter binding....
    public void bindSownListAdapter(List<PLDandSownAcreViewModel> viewModelList){
        listViewAdapter = new SownAcreListViewAdapter(getActivity(), viewModelList,"sown_acre_view");
        sown_acre_recycler_view_list.setAdapter(listViewAdapter);
        sown_acre_recycler_view_list.setVisibility(View.VISIBLE);
        sow_acre_search_reports_item_layout.setVisibility(View.VISIBLE);
    }

    //todo organiser item click listner...
    @Override
    public void onOrgItemClick(int pos) {
        frame_sow_acre_organiser_list_layout.setVisibility(View.GONE);
        dueInspectionModel = sownAcreViewModelList_gl.get(pos);
        if (dueInspectionModel != null) {
            et_sow_acre_organizer_name.setText(sownAcreViewModelList_gl.get(pos).organizer_name);
            frame_sow_acre_organiser_list_layout.setVisibility(View.GONE);
        } else {
            frame_sow_acre_organiser_list_layout.setVisibility(View.VISIBLE);
        }
    }

    //todo item name click listner
    @Override
    public void onItemClick(int pos) {
        frame_sow_acre_item_list_layout.setVisibility(View.GONE);
        dueInspectionModel = sownAcreViewModelList_gl.get(pos);
        if (dueInspectionModel != null) {
            et_sow_acre_item_name.setText(sownAcreViewModelList_gl.get(pos).item_name);
            frame_sow_acre_item_list_layout.setVisibility(View.GONE);
        } else {
            frame_sow_acre_item_list_layout.setVisibility(View.VISIBLE);
        }
    }

    //todo village name click listner....
    @Override
    public void onVillageItemClick(int pos) {
        frame_sow_acre_village_list_layout.setVisibility(View.GONE);
        dueInspectionModel = sownAcreViewModelList_gl.get(pos);
        if (dueInspectionModel != null) {
            et_sow_acre_village_name.setText(sownAcreViewModelList_gl.get(pos).village_name);
            frame_sow_acre_village_list_layout.setVisibility(View.GONE);
        } else {
            frame_sow_acre_village_list_layout.setVisibility(View.VISIBLE);
        }
    }

    private void HitDownloadFileApi(String url, String organiser_name, String item_name, String village_name) {
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://hytechdev.pristinefulfil.com");

        Retrofit retrofit = builder.build();
        NetworkInterface downloadInterface = retrofit.create(NetworkInterface.class);
        Call<ResponseBody> call = downloadInterface.downloadSownAcreFile(organiser_name, item_name, village_name, sessionManagement.getUserEmail());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Headers headers = response.headers();
                ResponseBody responseBody = response.body();
                String file_name = headers.get("filename");
                writeResponseBodyToDisk(responseBody, file_name);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void writeResponseBodyToDisk(ResponseBody body, String file_name){
        Handler handler = new Handler(Looper.getMainLooper());
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setMax(100);
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    // todo change the file location/name according to your needs
                    //  String path1=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) +file_name;
                    File futurFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), file_name);

                    InputStream inputStream = null;
                    OutputStream outputStream = null;

                    try {
                        byte[] fileReader = new byte[4096];

                        long fileSize = body.contentLength();
                        long fileSizeDownloaded = 0;

                        inputStream = body.byteStream();
                        outputStream = new FileOutputStream(futurFile);

                        while (true) {
                            int read = inputStream.read(fileReader);
                            if (read == -1) {
                                break;
                            }

                            outputStream.write(fileReader, 0, read);
                            fileSizeDownloaded += read;

                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            long perce = fileSizeDownloaded * 100 / fileSize;
                            long finalFileSizeDownloaded = fileSizeDownloaded;
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    progress_percentage_count.setVisibility(View.VISIBLE);
                                    text_percent.setVisibility(View.VISIBLE);
                                    progress_percentage_count.setText(String.valueOf((int) perce));
                                    progressBar.setProgress((int) (finalFileSizeDownloaded * 100 / fileSize));
                                }
                            });

                            Log.d("user files", "file download: " + fileSizeDownloaded + " of " + fileSize);

                        }
                        outputStream.flush();
                        inputStream.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (inputStream != null) {
                            inputStream.close();
                        }

                        if (outputStream != null) {
                            outputStream.close();
                        }
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.GONE);
                                filter_download_floating_btn.setVisibility(View.VISIBLE);
                                progress_percentage_count.setText("");
                                progress_percentage_count.setVisibility(View.GONE);
                                progressBar.setProgress((0));
                                text_percent.setVisibility(View.GONE);
                                showForegroundNotification(getActivity(), "Download finished ! ");
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        executorService.shutdown();
    }

    private void showForegroundNotification(Context activity, String desc) {
        Intent showTaskIntent = new Intent(getActivity(), SownAcresFragment.class);
        showTaskIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_SINGLE_TOP);
        // pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        PendingIntent contentIntent = PendingIntent.getActivity(
                getActivity(),
                0,
                showTaskIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager manager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "channel01";
        String channelName = "task_name";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new
                    NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            channel.setShowBadge(true);
            channel.enableVibration(true);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            manager.createNotificationChannel(channel);
        }

        Notification.Builder notification = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notification = new Notification.Builder(getActivity(), channelId)
                    .setContentTitle("Report")
                    .setContentText(desc)
                    .setContentIntent(contentIntent)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setWhen(System.currentTimeMillis());
        } else {
            notification = new Notification.Builder(getActivity())
                    .setContentTitle("Report")
                    .setContentText(desc)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setPriority(Notification.PRIORITY_MAX)
                    .setContentIntent(contentIntent)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setWhen(System.currentTimeMillis())
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setVibrate(new long[]{DEFAULT_VIBRATE})
                    .setWhen(System.currentTimeMillis());
        }
     /*   NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channelId)
                .setContentTitle("Report")
                .setContentText(desc)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setPriority(Notification.PRIORITY_HIGH)
                .setContentIntent(contentIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis());*/

        manager.notify(0, notification.build());
    }
}