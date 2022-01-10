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
import com.example.pristineseed.model.reportModel.LotsDueInspectionModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.adapter.reportsAdapter.lotsDueInspectionAdapters.LotsDueInspectionAdapter;
import com.example.pristineseed.ui.adapter.reportsAdapter.lotsDueInspectionAdapters.ReportItemNameAdapter;
import com.example.pristineseed.ui.adapter.reportsAdapter.lotsDueInspectionAdapters.ReportOrganiserNameAdapter;
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

public class LotsDueforInspectionFragment extends Fragment implements ReportOrganiserNameAdapter.OnItemClickListner, ReportItemNameAdapter.OnItemClickListner {

    private static final int PERMISSION_STORAGE_CODE = 1000;
    private MaterialCardView frame_layout_organizer_report_list, frame_layout_report_item_list;
    private RecyclerView rv_search_organiser_list_items, rv_search_item_list_items, report_recycler_view_list;
    private LinearLayoutManager rv_report_linear_layout_manager, rv_report_organizer_name_manager, rv_report_item_name_manager;
    private TextInputLayout organser_name_input_layout, item_name_input_layout;
    private TextInputEditText et_report_organizer_name, et_report_item_name;
    private ProgressBar loading_item;
    private LinearLayout parent_layout;
    private MaterialButton submit_report_name, clear_report_name;
    private RelativeLayout search_reports_item_layout;
    private FloatingActionButton filter_download_floating_btn;
    private View view_weight;
    private ImageView iv_refresh_details;
    private List<LotsDueInspectionModel> lotsDueInspectionModelList_gl = new ArrayList<>();
    private LotsDueInspectionModel dueInspectionModel;
    private LotsDueInspectionAdapter lotsDueInspectionAdapter;
    private ReportOrganiserNameAdapter reportSearchAdapter;
    private ProgressBar progressBar;
    private TextView progress_percentage_count, text_percent;
    private SessionManagement sessionManagement;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lots_due_for_inspection, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        sessionManagement = new SessionManagement(getActivity());

        et_report_organizer_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && et_report_organizer_name.isCursorVisible()) {
                    frame_layout_organizer_report_list.setVisibility(View.VISIBLE);
                    frame_layout_report_item_list.setVisibility(View.GONE);
                    organser_name_input_layout.setStartIconDrawable(null);
                    et_report_organizer_name.setEnabled(true);
                    lotsInspectionList("", "", "organizer_name");
                    view_weight.setVisibility(View.VISIBLE);
                } else {
                    if (!et_report_organizer_name.getText().toString().trim().equalsIgnoreCase("")) {
                        organser_name_input_layout.setStartIconDrawable(null);
                    } else {
                        organser_name_input_layout.setStartIconDrawable(R.drawable.ic_baseline_search_24);
                        frame_layout_organizer_report_list.setVisibility(View.GONE);
                    }
                }
            }
        });

        et_report_organizer_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                et_report_organizer_name.setSelection(s.toString().length());
                if (!s.toString().equalsIgnoreCase("") && et_report_organizer_name.isCursorVisible()) {
                    frame_layout_organizer_report_list.setVisibility(View.VISIBLE);
                    view_weight.setVisibility(View.VISIBLE);
                    et_report_organizer_name.setEnabled(true);
                    lotsInspectionList(s.toString(), "", "organizer_name");
                } else {
                    frame_layout_organizer_report_list.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        et_report_item_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && et_report_item_name.isCursorVisible()) {
                    frame_layout_report_item_list.setVisibility(View.VISIBLE);
                    frame_layout_organizer_report_list.setVisibility(View.GONE);
                    et_report_item_name.setEnabled(true);
                    item_name_input_layout.setStartIconDrawable(null);
                    lotsInspectionList("", "", "item_name");
                    view_weight.setVisibility(View.VISIBLE);
                } else {
                    if (!et_report_item_name.getText().toString().trim().equalsIgnoreCase("")) {
                        item_name_input_layout.setStartIconDrawable(null);
                    } else {
                        item_name_input_layout.setStartIconDrawable(R.drawable.ic_baseline_search_24);
                        frame_layout_report_item_list.setVisibility(View.GONE);
                    }
                }
            }
        });

        et_report_item_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                et_report_item_name.setSelection(s.toString().length());
                if (!s.toString().equalsIgnoreCase("") && et_report_item_name.isCursorVisible()) {
                    frame_layout_report_item_list.setVisibility(View.VISIBLE);
                    lotsInspectionList("", s.toString(), "item_name");
                    et_report_item_name.setEnabled(true);
                    view_weight.setVisibility(View.VISIBLE);
                } else {
                    frame_layout_report_item_list.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        parent_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    frame_layout_organizer_report_list.setVisibility(View.GONE);
                    frame_layout_report_item_list.setVisibility(View.GONE);
                    parent_layout.requestFocus();
                }
                return true;
            }
        });

        submit_report_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSubmit_report_name();
            }
        });

        clear_report_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_reports_item_layout.setVisibility(View.VISIBLE);
                report_recycler_view_list.setVisibility(View.GONE);
                et_report_organizer_name.setText("");
                et_report_organizer_name.clearFocus();
                et_report_organizer_name.setEnabled(true);
                organser_name_input_layout.setStartIconDrawable(R.drawable.ic_baseline_search_24);
                et_report_item_name.setText("");
                et_report_item_name.clearFocus();
                et_report_item_name.setEnabled(true);
                item_name_input_layout.setStartIconDrawable(R.drawable.ic_baseline_search_24);
                clear_report_name.setVisibility(View.GONE);
                submit_report_name.setVisibility(View.VISIBLE);
                filter_download_floating_btn.setVisibility(View.GONE);
                frame_layout_organizer_report_list.setVisibility(View.GONE);
                frame_layout_report_item_list.setVisibility(View.GONE);
                view_weight.setVisibility(View.VISIBLE);
            }
        });


        filter_download_floating_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HitDownloadFileApi(ApiUtils.BASE_URL, et_report_organizer_name.getText().toString(), et_report_item_name.getText().toString());
                progressBar.setVisibility(View.VISIBLE);
                filter_download_floating_btn.setVisibility(View.GONE);
            }
        });


        iv_refresh_details.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                et_report_organizer_name.setText("");
                et_report_organizer_name.clearFocus();
                frame_layout_organizer_report_list.setVisibility(View.GONE);
                organser_name_input_layout.setStartIconDrawable(R.drawable.ic_baseline_search_24);
                et_report_item_name.setText("");
                et_report_item_name.clearFocus();
                frame_layout_report_item_list.setVisibility(View.GONE);
                item_name_input_layout.setStartIconDrawable(R.drawable.ic_baseline_search_24);
                return true;
            }
        });

    }

    public void initView(View view) {
        rv_search_organiser_list_items = view.findViewById(R.id.rv_search_organiser_list_items);
        rv_search_item_list_items = view.findViewById(R.id.rv_search_item_list_items);
        frame_layout_organizer_report_list = view.findViewById(R.id.frame_layout_organizer_report_list);
        frame_layout_report_item_list = view.findViewById(R.id.frame_layout_report_item_list);
        report_recycler_view_list = view.findViewById(R.id.report_recycler_view_list);
        organser_name_input_layout = view.findViewById(R.id.organser_name_input_layout);
        item_name_input_layout = view.findViewById(R.id.item_name_input_layout);
        et_report_organizer_name = view.findViewById(R.id.et_report_organizer_name);
        et_report_item_name = view.findViewById(R.id.et_report_item_name);
        loading_item = view.findViewById(R.id.loading_item);
        submit_report_name = view.findViewById(R.id.submit_report_name);
        search_reports_item_layout = view.findViewById(R.id.search_reports_item_layout);
        clear_report_name = view.findViewById(R.id.clear_report_name);
        filter_download_floating_btn = view.findViewById(R.id.filter_download_floating_btn);
        parent_layout = view.findViewById(R.id.parent_layout);
        view_weight = view.findViewById(R.id.view_weight);
        iv_refresh_details = view.findViewById(R.id.iv_refresh_details);
        progressBar = view.findViewById(R.id.progressBar);
        progress_percentage_count = view.findViewById(R.id.progress_percentage_count);
        text_percent = view.findViewById(R.id.text_percent);

        //todo organiser name layout manager set...
        rv_report_organizer_name_manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_search_organiser_list_items.setLayoutManager(rv_report_organizer_name_manager);

        //todo item name layout manager set...
        rv_report_item_name_manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_search_item_list_items.setLayoutManager(rv_report_item_name_manager);

        //todo lots due inspection manager list....
        rv_report_linear_layout_manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        report_recycler_view_list.setLayoutManager(rv_report_linear_layout_manager);
    }

    @Override
    public void onResume() {
        super.onResume();
        frame_layout_organizer_report_list.setFocusable(false);
        frame_layout_report_item_list.setFocusable(false);
    }

    public void setSubmit_report_name() {
        if (NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
            if (et_report_organizer_name.getText().toString().equalsIgnoreCase("") && et_report_item_name.getText().toString().equalsIgnoreCase("")) {
                Toast.makeText(getActivity(), "Data Fetch Successfully", Toast.LENGTH_SHORT).show();
                lotsInspectionList("", "", "report_list");
                submit_report_name.setVisibility(View.GONE);
                clear_report_name.setVisibility(View.VISIBLE);
                et_report_organizer_name.setEnabled(false);
                et_report_item_name.setEnabled(false);
                filter_download_floating_btn.setVisibility(View.VISIBLE);
                view_weight.setVisibility(View.GONE);
            } else if (!et_report_organizer_name.getText().toString().equalsIgnoreCase("") || !et_report_item_name.getText().toString().equalsIgnoreCase("")) {
                lotsInspectionList(et_report_organizer_name.getText().toString(), et_report_item_name.getText().toString(), "report_list");
                submit_report_name.setVisibility(View.GONE);
                filter_download_floating_btn.setVisibility(View.VISIBLE);
                et_report_organizer_name.setEnabled(false);
                et_report_item_name.setEnabled(false);
                clear_report_name.setVisibility(View.VISIBLE);
            }
        }
        else {
            Toast.makeText(getActivity(), "No Network Connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void lotsInspectionList(String organizer_name, String item_name, String flag) {
        if (NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
            loading_item.setVisibility(View.VISIBLE);
            /*LoadingDialog progressDialog = new LoadingDialog();
            progressDialog.showLoadingDialog(getActivity());*/
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            Call<ArrayList<LotsDueInspectionModel>> call = mAPIService.lotsDueInspectionList(organizer_name, item_name, sessionManagement.getUserEmail());
            call.enqueue(new Callback<ArrayList<LotsDueInspectionModel>>() {
                @Override
                public void onResponse(Call<ArrayList<LotsDueInspectionModel>> call, Response<ArrayList<LotsDueInspectionModel>> response) {
                    try {
                        if (response.isSuccessful()) {
                            loading_item.setVisibility(View.GONE);
                            ArrayList<LotsDueInspectionModel> responseLotsInspectionModelList = response.body();
                            if (responseLotsInspectionModelList != null && responseLotsInspectionModelList.size() > 0 && responseLotsInspectionModelList.get(0).condition) {
//                                Toast.makeText(getActivity(), "Data fetch successful !", Toast.LENGTH_SHORT).show();
                                lotsDueInspectionModelList_gl.clear();
                                lotsDueInspectionModelList_gl.addAll(responseLotsInspectionModelList);

                                Collections.sort(lotsDueInspectionModelList_gl, new Comparator<LotsDueInspectionModel>() {
                                    @Override
                                    public int compare(LotsDueInspectionModel o1, LotsDueInspectionModel o2) {
                                        return o1.organizer_name.compareTo(o2.organizer_name);
                                    }
                                });

                                Collections.sort(lotsDueInspectionModelList_gl, new Comparator<LotsDueInspectionModel>() {
                                    @Override
                                    public int compare(LotsDueInspectionModel o1, LotsDueInspectionModel o2) {
                                        return o1.item_name.compareTo(o2.item_name);
                                    }
                                });

                                for (int i = 0; i < lotsDueInspectionModelList_gl.size(); i++) {
                                    for (int j = i + 1; j < lotsDueInspectionModelList_gl.size(); j++) {
                                        if (lotsDueInspectionModelList_gl.get(i).organizer_name.equals(lotsDueInspectionModelList_gl.get(j).organizer_name)
                                                || lotsDueInspectionModelList_gl.get(i).item_name.equals(lotsDueInspectionModelList_gl.get(j).item_name)) {
                                            Log.e("org_code", lotsDueInspectionModelList_gl.get(i).organizer_name);
                                            lotsDueInspectionModelList_gl.remove(j);
                                            j--;
                                        }
                                    }
                                }
                                Log.e("temp_list", String.valueOf(lotsDueInspectionModelList_gl.size()));
                                Log.e("temp_list1", lotsDueInspectionModelList_gl.toString());

                                if (flag.equals("organizer_name")) {
                                    bindOrganizerListAdapter();
                                } else if (flag.equals("item_name")) {
                                    bindItemNameListAdapter();
                                } else if (flag.equals("report_list")) {
                                    Toast.makeText(getActivity(), "Data Fetch Successfully", Toast.LENGTH_SHORT).show();
                                    clear_report_name.setVisibility(View.VISIBLE);
                                    bindInspectionListAdapter(responseLotsInspectionModelList);
                                } else {
                                    Toast.makeText(getActivity(), responseLotsInspectionModelList.get(0).message, Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                loading_item.setVisibility(View.GONE);
                                frame_layout_organizer_report_list.setVisibility(View.GONE);
                                submit_report_name.setVisibility(View.VISIBLE);
                                filter_download_floating_btn.setVisibility(View.GONE);
                                clear_report_name.setVisibility(View.GONE);
                                et_report_organizer_name.setEnabled(true);
                                et_report_item_name.setEnabled(true);
                                Toast.makeText(getActivity(), "No Record Found On Item_name: " + et_report_item_name.getText().toString(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            loading_item.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        loading_item.setVisibility(View.GONE);
                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "report_list", getActivity());
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<LotsDueInspectionModel>> call, Throwable t) {
                    loading_item.setVisibility(View.GONE);
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "report_list", getActivity());
                }
            });
        } else {
            Toast.makeText(getActivity(), "Please wait for online connection", Toast.LENGTH_SHORT).show();
        }

    }

    //todo organiser list bind adapter...
    public void bindOrganizerListAdapter() {
        ReportOrganiserNameAdapter reportSearchAdapter = new ReportOrganiserNameAdapter(getActivity(), lotsDueInspectionModelList_gl);
        rv_search_organiser_list_items.setAdapter(reportSearchAdapter);
        reportSearchAdapter.setClickListner(this);
    }

    //todo item name list bind adapter...
    public void bindItemNameListAdapter() {
        ReportItemNameAdapter reportItemNameAdapter = new ReportItemNameAdapter(getActivity(), lotsDueInspectionModelList_gl);
        rv_search_item_list_items.setAdapter(reportItemNameAdapter);
        reportItemNameAdapter.setClickListner(this);
    }

    @Override
    public void onOrgClick(int pos) {
        frame_layout_organizer_report_list.setVisibility(View.GONE);
        dueInspectionModel = lotsDueInspectionModelList_gl.get(pos);
        if (dueInspectionModel != null) {
            et_report_organizer_name.setText(lotsDueInspectionModelList_gl.get(pos).organizer_name);
//            report_item_search_view.setText(lotsDueInspectionModelList_gl.get(pos).organizer_name);
            frame_layout_organizer_report_list.setVisibility(View.GONE);
        } else {
            frame_layout_organizer_report_list.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemClickList(int pos) {
        frame_layout_report_item_list.setVisibility(View.GONE);
        dueInspectionModel = lotsDueInspectionModelList_gl.get(pos);
        if (dueInspectionModel != null) {
            et_report_item_name.setText(lotsDueInspectionModelList_gl.get(pos).item_name);
//            report_item_search_view.setText(lotsDueInspectionModelList_gl.get(pos).organizer_name);
            frame_layout_report_item_list.setVisibility(View.GONE);
        } else {
            frame_layout_report_item_list.setVisibility(View.VISIBLE);
        }
    }


    //todo full details list bind adapter...
    public void bindInspectionListAdapter(List<LotsDueInspectionModel> inspectionModelList) {
        lotsDueInspectionAdapter = new LotsDueInspectionAdapter(getActivity(), inspectionModelList);
        report_recycler_view_list.setAdapter(lotsDueInspectionAdapter);
        report_recycler_view_list.setVisibility(View.VISIBLE);
        search_reports_item_layout.setVisibility(View.VISIBLE);
    }

    public void HitDownloadFileApi(String url, String organizer_name, String item_name) {
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://hytechdev.pristinefulfil.com");

        Retrofit retrofit = builder.build();
        NetworkInterface downloadInterface = retrofit.create(NetworkInterface.class);
        Call<ResponseBody> call = downloadInterface.downloadFileStreaming(organizer_name, item_name, sessionManagement.getUserEmail());

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

    private void writeResponseBodyToDisk(ResponseBody body, String file_name) {
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

    private void showForegroundNotification(Context context, String desc) {

        Intent showTaskIntent = new Intent(getActivity(), LotsDueforInspectionFragment.class);
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