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
import com.example.pristineseed.global.CustomDatePicker;
import com.example.pristineseed.model.reportModel.ReportSeedDispatchViewModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.adapter.reportsAdapter.sdnDispatchAdapters.SDNDetailsListAdapter;
import com.example.pristineseed.ui.adapter.reportsAdapter.sdnDispatchAdapters.SDNHybridNameAdapter;
import com.example.pristineseed.ui.adapter.reportsAdapter.sdnDispatchAdapters.SDNOrganiserNameAdapter;
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

public class SDNDetailsFragment extends Fragment implements SDNOrganiserNameAdapter.OnItemClickListner, SDNHybridNameAdapter.OnItemClickListner {

    private SessionManagement sessionManagement;
    private TextInputLayout organser_name_text_input_layout, hybrid_name_text_input_layout, sdn_date_input_layout;
    private TextInputEditText et_sdn_organizer_name, et_sdn_hybrid_name, et_sdn_date;
    private MaterialButton submit_sdn_list, clear_sdn_list;
    private MaterialCardView frame_sdn_organiser_list_layout, frame_sdn_hybrid_list_layout;
    private RecyclerView rv_sdn_organiser_list, rv_sdn_hybrid_list, sdn_recycler_view_list;
    private LinearLayoutManager sdn_organiser_layout_manager, sdn_hybrid_layout_manager, sdn_dispatch_list_layout_manager;
    private FloatingActionButton filter_download_floating_btn;
    private View view_weight;
    private LinearLayout parent_layout;
    private ProgressBar loading_item;
    private RelativeLayout search_reports_item_layout;
    private ImageView iv_refresh_details;
    private List<ReportSeedDispatchViewModel> reportSeedDispatchViewModels_gl = new ArrayList<>();
    private ReportSeedDispatchViewModel reportSeedDispatchModel = null;
    private SDNOrganiserNameAdapter sdnOrganiserNameAdapter;
    private SDNHybridNameAdapter sdnHybridNameAdapter;
    private ProgressBar sdn_progressBar;
    private TextView sdn_progress_percentage_count, sdn_text_percent;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_s_d_n_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManagement = new SessionManagement(getActivity());
        initView(view);

        et_sdn_organizer_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && et_sdn_organizer_name.isCursorVisible()) {
                    frame_sdn_organiser_list_layout.setVisibility(View.VISIBLE);
                    frame_sdn_hybrid_list_layout.setVisibility(View.GONE);
                    organser_name_text_input_layout.setStartIconDrawable(null);
                    sdnDispatchList("", "", "", "organizer_name");
                    view_weight.setVisibility(View.VISIBLE);
                } else {
                    if (!et_sdn_organizer_name.getText().toString().trim().equalsIgnoreCase("")) {
                        organser_name_text_input_layout.setStartIconDrawable(null);
                    } else {
                        organser_name_text_input_layout.setStartIconDrawable(R.drawable.ic_baseline_search_24);
                        frame_sdn_organiser_list_layout.setVisibility(View.GONE);
                    }
                }
            }
        });

        et_sdn_organizer_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                et_sdn_organizer_name.setSelection(s.toString().length());
                if (!s.toString().equalsIgnoreCase("") && et_sdn_organizer_name.isCursorVisible()) {
                    frame_sdn_organiser_list_layout.setVisibility(View.VISIBLE);
                    sdnDispatchList(s.toString(), "", "", "organizer_name");
                    view_weight.setVisibility(View.VISIBLE);
                } else {
                    frame_sdn_organiser_list_layout.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        et_sdn_hybrid_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && et_sdn_hybrid_name.isCursorVisible()) {
                    frame_sdn_hybrid_list_layout.setVisibility(View.VISIBLE);
                    frame_sdn_organiser_list_layout.setVisibility(View.GONE);
                    hybrid_name_text_input_layout.setStartIconDrawable(null);
                    sdnDispatchList("", "", "", "hybrid_name");
                    view_weight.setVisibility(View.VISIBLE);
                } else {
                    if (!et_sdn_hybrid_name.getText().toString().trim().equalsIgnoreCase("")) {
                        hybrid_name_text_input_layout.setStartIconDrawable(null);
                    } else {
                        hybrid_name_text_input_layout.setStartIconDrawable(R.drawable.ic_baseline_search_24);
                        frame_sdn_hybrid_list_layout.setVisibility(View.GONE);
                    }
                }
            }
        });

        et_sdn_hybrid_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                et_sdn_hybrid_name.setSelection(s.toString().length());
                if (!s.toString().equalsIgnoreCase("") && et_sdn_hybrid_name.isCursorVisible()) {
                    frame_sdn_hybrid_list_layout.setVisibility(View.VISIBLE);
                    sdnDispatchList("", s.toString(), "", "hybrid_name");
                    view_weight.setVisibility(View.VISIBLE);
                } else {
                    frame_sdn_hybrid_list_layout.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_sdn_date.setOnTouchListener((v, event) -> {
            try {
                new CustomDatePicker(getActivity()).showDatePickerDialogDash(et_sdn_date);
            } catch (Exception e) {
                Log.e("exc", e.getMessage());
            }
            return true;
        });

        /*et_sdn_date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                et_sdn_date.setSelection(s.toString().length());
                if(!s.toString().equalsIgnoreCase("")) {
                    sdnDispatchList("", "", s.toString(), "sdn_list");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/

        parent_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    frame_sdn_organiser_list_layout.setVisibility(View.GONE);
                    frame_sdn_hybrid_list_layout.setVisibility(View.GONE);
                    parent_layout.requestFocus();
                }
                return true;
            }
        });

        iv_refresh_details.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    et_sdn_organizer_name.setText("");
                    et_sdn_organizer_name.clearFocus();
                    frame_sdn_organiser_list_layout.setVisibility(View.GONE);
                    organser_name_text_input_layout.setStartIconDrawable(R.drawable.ic_baseline_search_24);
                    et_sdn_hybrid_name.setText("");
                    et_sdn_hybrid_name.clearFocus();
                    frame_sdn_hybrid_list_layout.setVisibility(View.GONE);
                    hybrid_name_text_input_layout.setStartIconDrawable(R.drawable.ic_baseline_search_24);
                    et_sdn_date.setText("");
                    et_sdn_date.clearFocus();
                }
                return true;
            }
        });

        submit_sdn_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSubmitSDNDetails();
            }
        });

        clear_sdn_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_reports_item_layout.setVisibility(View.VISIBLE);
                sdn_recycler_view_list.setVisibility(View.GONE);
                submit_sdn_list.setVisibility(View.VISIBLE);
                clear_sdn_list.setVisibility(View.GONE);
                filter_download_floating_btn.setVisibility(View.GONE);
                et_sdn_organizer_name.setText("");
                et_sdn_organizer_name.clearFocus();
                et_sdn_organizer_name.setEnabled(true);
                organser_name_text_input_layout.setStartIconDrawable(R.drawable.ic_baseline_search_24);
                et_sdn_hybrid_name.setText("");
                et_sdn_hybrid_name.clearFocus();
                et_sdn_hybrid_name.setEnabled(true);
                hybrid_name_text_input_layout.setStartIconDrawable(R.drawable.ic_baseline_search_24);
                et_sdn_date.setText("");
                et_sdn_date.clearFocus();
                et_sdn_date.setEnabled(true);
                frame_sdn_organiser_list_layout.setVisibility(View.GONE);
                frame_sdn_hybrid_list_layout.setVisibility(View.GONE);
                iv_refresh_details.setVisibility(View.VISIBLE);

            }
        });

        filter_download_floating_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HitDownloadFileApi(ApiUtils.BASE_URL, et_sdn_organizer_name.getText().toString(), et_sdn_hybrid_name.getText().toString(), et_sdn_date.getText().toString());
                sdn_progressBar.setVisibility(View.VISIBLE);
                filter_download_floating_btn.setVisibility(View.GONE);
            }
        });

    }

    public void HitDownloadFileApi(String url, String organizer_name, String item_name, String date){
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://hytechdev.pristinefulfil.com");

        Retrofit retrofit = builder.build();
        NetworkInterface downloadInterface = retrofit.create(NetworkInterface.class);
        Call<ResponseBody> call = downloadInterface.downloadSdnFile(organizer_name, item_name, date, sessionManagement.getUserEmail());

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                Headers header = response.headers();
                ResponseBody responseBody = response.body();
                String file_name = header.get("filename");
                writeResponseBodyToDisk(responseBody, file_name);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(), "No :(", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void writeResponseBodyToDisk(ResponseBody body, String file_name){
        Handler handler = new Handler(Looper.getMainLooper());
        sdn_progressBar.setVisibility(View.VISIBLE);
        sdn_progressBar.setMax(100);
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    // todo change the file location/name according to your needs
                    //  String path1=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) +file_name;
                    File futurFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), file_name);
         /*   File file = new File(path1);
            if (!file.exists()) {
                file.mkdirs();
            }
            File dataFile = new File(file, file_name);
            if (dataFile.exists()) {
                dataFile.delete();
            }*/

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
                                    sdn_progress_percentage_count.setVisibility(View.VISIBLE);
                                    sdn_text_percent.setVisibility(View.VISIBLE);
                                    sdn_progress_percentage_count.setText(String.valueOf((int) perce));
                                    sdn_progressBar.setProgress((int) (finalFileSizeDownloaded * 100 / fileSize));
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
                                sdn_progressBar.setVisibility(View.GONE);
                                filter_download_floating_btn.setVisibility(View.VISIBLE);
                                sdn_progress_percentage_count.setText("");
                                sdn_progress_percentage_count.setVisibility(View.GONE);
                                sdn_progressBar.setProgress((0));
                                sdn_text_percent.setVisibility(View.GONE);
                                showForegroundNotification(getActivity(), "Download finished ! ");
                            }
                        });

                        /*File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + file_name);
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.fromFile(file), "application/pdf");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent);*/
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        executorService.shutdown();
    }

    public void initView(View view) {
        organser_name_text_input_layout = view.findViewById(R.id.organser_name_text_input_layout);
        hybrid_name_text_input_layout = view.findViewById(R.id.hybrid_name_text_input_layout);
        sdn_date_input_layout = view.findViewById(R.id.sdn_date_input_layout);
        et_sdn_organizer_name = view.findViewById(R.id.et_sdn_organizer_name);
        et_sdn_hybrid_name = view.findViewById(R.id.et_sdn_hybrid_name);
        et_sdn_date = view.findViewById(R.id.et_sdn_date);
        submit_sdn_list = view.findViewById(R.id.submit_sdn_list);
        clear_sdn_list = view.findViewById(R.id.clear_sdn_list);
        frame_sdn_organiser_list_layout = view.findViewById(R.id.frame_sdn_organiser_list_layout);
        frame_sdn_hybrid_list_layout = view.findViewById(R.id.frame_sdn_hybrid_list_layout);
        rv_sdn_organiser_list = view.findViewById(R.id.rv_sdn_organiser_list);
        rv_sdn_hybrid_list = view.findViewById(R.id.rv_sdn_hybrid_list);
        sdn_recycler_view_list = view.findViewById(R.id.sdn_recycler_view_list);
        filter_download_floating_btn = view.findViewById(R.id.filter_download_floating_btn);
        loading_item = view.findViewById(R.id.loading_item);
        view_weight = view.findViewById(R.id.view_weight);
        parent_layout = view.findViewById(R.id.parent_relative_layout);
        iv_refresh_details = view.findViewById(R.id.iv_refresh_details);
        search_reports_item_layout = view.findViewById(R.id.search_reports_item_layout);
        sdn_progressBar = view.findViewById(R.id.sdn_progressBar);
        sdn_progress_percentage_count = view.findViewById(R.id.sdn_progress_percentage_count);
        sdn_text_percent = view.findViewById(R.id.sdn_text_percent);

        //todo set organizer layout manager...
        sdn_organiser_layout_manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_sdn_organiser_list.setLayoutManager(sdn_organiser_layout_manager);

        //todo set hybrid layout manager...
        sdn_hybrid_layout_manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_sdn_hybrid_list.setLayoutManager(sdn_hybrid_layout_manager);

        //todo set dispatch layout manager...
        sdn_dispatch_list_layout_manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        sdn_recycler_view_list.setLayoutManager(sdn_dispatch_list_layout_manager);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setSubmitSDNDetails() {
        if (NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
            if (et_sdn_organizer_name.getText().toString().equalsIgnoreCase("") && et_sdn_hybrid_name.getText().toString().equalsIgnoreCase("")
                    && et_sdn_date.getText().toString().equalsIgnoreCase("")) {
                Toast.makeText(getActivity(), "Data Fetch Successfully", Toast.LENGTH_SHORT).show();
                sdnDispatchList("", "", "", "sdn_list");
                submit_sdn_list.setVisibility(View.GONE);
                clear_sdn_list.setVisibility(View.VISIBLE);
                et_sdn_organizer_name.setEnabled(false);
                et_sdn_hybrid_name.setEnabled(false);
                et_sdn_date.setEnabled(false);
                filter_download_floating_btn.setVisibility(View.VISIBLE);
                view_weight.setVisibility(View.GONE);
            }
            else if (!et_sdn_organizer_name.getText().toString().equalsIgnoreCase("") || !et_sdn_hybrid_name.getText().toString().equalsIgnoreCase("")
                    || !et_sdn_date.getText().toString().equalsIgnoreCase("")) {
                sdnDispatchList(et_sdn_organizer_name.getText().toString(), et_sdn_hybrid_name.getText().toString(), et_sdn_date.getText().toString(), "sdn_list");
                submit_sdn_list.setVisibility(View.GONE);
                filter_download_floating_btn.setVisibility(View.VISIBLE);
                et_sdn_organizer_name.setEnabled(false);
                et_sdn_hybrid_name.setEnabled(false);
                et_sdn_date.setEnabled(false);
                clear_sdn_list.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(getActivity(), "Wrong Data", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void sdnDispatchList(String organizer_name, String hybrid_name, String date, String flag) {
        if (NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
            loading_item.setVisibility(View.VISIBLE);
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            Call<ArrayList<ReportSeedDispatchViewModel>> call = mAPIService.reportSeedDispatchList(organizer_name, hybrid_name, date, sessionManagement.getUserEmail());
            call.enqueue(new Callback<ArrayList<ReportSeedDispatchViewModel>>() {
                @Override
                public void onResponse(Call<ArrayList<ReportSeedDispatchViewModel>> call, Response<ArrayList<ReportSeedDispatchViewModel>> response) {
                    try {
                        if (response.isSuccessful()) {
                            loading_item.setVisibility(View.GONE);
                            ArrayList<ReportSeedDispatchViewModel> reportSeedDispatchViewModelList = response.body();
                            if (reportSeedDispatchViewModelList != null && reportSeedDispatchViewModelList.size() > 0 && reportSeedDispatchViewModelList.get(0).condition) {
//                                Toast.makeText(getActivity(), "Data fetch successful !", Toast.LENGTH_SHORT).show();
                                reportSeedDispatchViewModels_gl.clear();
                                reportSeedDispatchViewModels_gl.addAll(reportSeedDispatchViewModelList);

                                Collections.sort(reportSeedDispatchViewModels_gl, new Comparator<ReportSeedDispatchViewModel>() {
                                    @Override
                                    public int compare(ReportSeedDispatchViewModel o1, ReportSeedDispatchViewModel o2) {
                                        return o1.organizer_name.compareTo(o2.organizer_name);
                                    }
                                });

                                for (int i = 0; i < reportSeedDispatchViewModels_gl.size(); i++) {
                                    for (int j = i + 1; j < reportSeedDispatchViewModels_gl.size(); j++) {
                                        if (reportSeedDispatchViewModels_gl.get(i).organizer_name.equals(reportSeedDispatchViewModels_gl.get(j).organizer_name)) {
//                                            Log.e("org_code", reportSeedDispatchViewModels_gl.get(i).organizer_name);
                                            reportSeedDispatchViewModels_gl.remove(j);
                                            j--;
                                        }
                                    }
                                }
                                Log.e("temp_list", String.valueOf(reportSeedDispatchViewModels_gl.size()));
                                Log.e("temp_list1", reportSeedDispatchViewModels_gl.toString());

                                if (flag.equals("organizer_name")) {
                                    bindSDNOrganiserName();
                                } else if (flag.equals("hybrid_name")) {
                                    bindSDNHybridName();
                                } else if (flag.equals("sdn_list")) {
                                    bindSDNDetailList(reportSeedDispatchViewModelList);
                                    Toast.makeText(getActivity(), "Data Fetch Successful !", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getActivity(), reportSeedDispatchViewModelList.get(0).message, Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                iv_refresh_details.setVisibility(View.VISIBLE);
                                loading_item.setVisibility(View.GONE);
                                frame_sdn_organiser_list_layout.setVisibility(View.GONE);
                                filter_download_floating_btn.setVisibility(View.GONE);
                                submit_sdn_list.setVisibility(View.VISIBLE);
                                clear_sdn_list.setVisibility(View.GONE);
                                et_sdn_organizer_name.setEnabled(true);
                                et_sdn_hybrid_name.setEnabled(true);
                                et_sdn_date.setEnabled(true);
                                Toast.makeText(getActivity(), reportSeedDispatchViewModelList.size() > 0 ? "No Record found" : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), response.message() + "wrong code:" + response.code(), Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        loading_item.setVisibility(View.GONE);
                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "report_list", getActivity());
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<ReportSeedDispatchViewModel>> call, Throwable t) {
                    loading_item.setVisibility(View.GONE);
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "report_list", getActivity());
                }
            });
        } else {
            Toast.makeText(getActivity(), "Please wait for online connection", Toast.LENGTH_SHORT).show();
        }
    }


    //todo organiser drop down list bind adapter...
    private void bindSDNOrganiserName() {
        SDNOrganiserNameAdapter sdnOrganiserNameAdapter = new SDNOrganiserNameAdapter(getActivity(), reportSeedDispatchViewModels_gl);
        rv_sdn_organiser_list.setAdapter(sdnOrganiserNameAdapter);
        sdnOrganiserNameAdapter.setClickListner(this);
    }

    //todo hybrid drop down list bind adapter...
    private void bindSDNHybridName() {
        SDNHybridNameAdapter sdnHybridNameAdapter = new SDNHybridNameAdapter(getActivity(), reportSeedDispatchViewModels_gl);
        rv_sdn_hybrid_list.setAdapter(sdnHybridNameAdapter);
        sdnHybridNameAdapter.setClickListner(this);
    }

    private void bindSDNDetailList(List<ReportSeedDispatchViewModel> reportSeedDispatchModels) {
        SDNDetailsListAdapter detailsListAdapter = new SDNDetailsListAdapter(getActivity(), reportSeedDispatchModels);
        sdn_recycler_view_list.setAdapter(detailsListAdapter);
        search_reports_item_layout.setVisibility(View.VISIBLE);
        sdn_recycler_view_list.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClick(int pos) {
        frame_sdn_organiser_list_layout.setVisibility(View.GONE);
        reportSeedDispatchModel = reportSeedDispatchViewModels_gl.get(pos);
        if (reportSeedDispatchModel != null) {
            et_sdn_organizer_name.setText(reportSeedDispatchViewModels_gl.get(pos).organizer_name);
            frame_sdn_organiser_list_layout.setVisibility(View.GONE);
        } else {
            frame_sdn_organiser_list_layout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onHybridItemClick(int pos) {
        frame_sdn_hybrid_list_layout.setVisibility(View.GONE);
        reportSeedDispatchModel = reportSeedDispatchViewModels_gl.get(pos);
        if (reportSeedDispatchModel != null) {
            et_sdn_hybrid_name.setText(reportSeedDispatchViewModels_gl.get(pos).hybrid_name);
            frame_sdn_hybrid_list_layout.setVisibility(View.GONE);
        } else {
            frame_sdn_hybrid_list_layout.setVisibility(View.VISIBLE);
        }
    }

    public void showForegroundNotification(Context context , String desc){
        Intent showTaskIntent = new Intent(getActivity(), SDNDetailsFragment.class);
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