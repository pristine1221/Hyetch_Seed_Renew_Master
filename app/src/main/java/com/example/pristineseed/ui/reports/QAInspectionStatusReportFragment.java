package com.example.pristineseed.ui.reports;

import static android.app.Notification.DEFAULT_VIBRATE;
import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
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
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pristineseed.GlobalNotification.NetworkUtil;
import com.example.pristineseed.R;
import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.model.reportModel.ProdAndQAInspectionStatusModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.adapter.reportsAdapter.prodInspectionStatusAdapter.ProdAndQAInspectionStatusListAdpater;
import com.example.pristineseed.ui.adapter.reportsAdapter.prodInspectionStatusAdapter.ProdAndQAItemNameAdapter;
import com.example.pristineseed.ui.adapter.reportsAdapter.prodInspectionStatusAdapter.ProdAndQAOrgNameAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
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


public class QAInspectionStatusReportFragment extends Fragment implements ProdAndQAOrgNameAdapter.OnOrgClickListner, ProdAndQAItemNameAdapter.OnItemClickListner {
    private MaterialCardView frame_layout_organizer_qa_list, frame_layout_qa_item_list;
    private RecyclerView rv_organiser_list_items, rv_item_list_items, qa_recycler_view_list;
    private LinearLayoutManager rv_qa_linear_layout_manager, rv_qa_organizer_name_manager, rv_qa_item_name_manager;
    private TextInputLayout qa_organser_name_input_layout, qa_item_name_input_layout;
    private TextInputEditText et_qa_organizer_name, et_qa_item_name;
    private ProgressBar loading_item, progressBar;
    private LinearLayout parent_layout;
    private MaterialButton submit_report_name, clear_report_name;
    private RelativeLayout search_qa_item_layout;
    private FloatingActionButton filter_download_floating_btn;
    private View view_weight;
    private ImageView iv_refresh_details;
    private TextView progress_percentage_count, text_percent;
    private SessionManagement sessionManagement;
    private ProdAndQAInspectionStatusListAdpater qaInspectionListAdpater;
    private ProdAndQAInspectionStatusModel qaInspectionStatusModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_q_a_inspection_status_report, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        sessionManagement = new SessionManagement(getActivity());

        et_qa_organizer_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && et_qa_organizer_name.isCursorVisible()){
                    frame_layout_organizer_qa_list.setVisibility(View.VISIBLE);
                    frame_layout_qa_item_list.setVisibility(View.GONE);
                    qa_organser_name_input_layout.setStartIconDrawable(null);
                    et_qa_organizer_name.setEnabled(true);
                    view_weight.setVisibility(View.VISIBLE);
                    qaInspectionListView("","","qa_organiser_name");
                }
                else {
                    if(!et_qa_organizer_name.getText().toString().equalsIgnoreCase("")){
                        qa_organser_name_input_layout.setStartIconDrawable(null);
                    }else {
                        qa_organser_name_input_layout.setStartIconDrawable(R.drawable.ic_baseline_search_24);
                        frame_layout_organizer_qa_list.setVisibility(View.GONE);
                    }
                }
            }
        });
        et_qa_organizer_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                et_qa_organizer_name.setSelection(s.toString().length());
                if(!s.toString().equalsIgnoreCase("") && et_qa_organizer_name.isCursorVisible()){
                    qaInspectionListView(s.toString(),"","qa_organiser_name");
                    frame_layout_organizer_qa_list.setVisibility(View.VISIBLE);
                    view_weight.setVisibility(View.VISIBLE);
                    et_qa_organizer_name.setEnabled(true);
                }
                else {
                    frame_layout_organizer_qa_list.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        et_qa_item_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus && et_qa_item_name.isCursorVisible()){
                    frame_layout_organizer_qa_list.setVisibility(View.GONE);
                    frame_layout_qa_item_list.setVisibility(View.VISIBLE);
                    qa_item_name_input_layout.setStartIconDrawable(null);
                    et_qa_item_name.setEnabled(true);
                    view_weight.setVisibility(View.VISIBLE);
                    qaInspectionListView("","","qa_item_name");
                }
                else {
                    if(!et_qa_item_name.getText().toString().equalsIgnoreCase("")){
                        qa_item_name_input_layout.setStartIconDrawable(null);
                    }else {
                        qa_item_name_input_layout.setStartIconDrawable(R.drawable.ic_baseline_search_24);
                        frame_layout_qa_item_list.setVisibility(View.GONE);
                    }
                }
            }
        });
        et_qa_item_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                et_qa_item_name.setSelection(s.toString().length());
                if(!s.toString().equalsIgnoreCase("") && et_qa_item_name.isCursorVisible()){
                    qaInspectionListView("",s.toString(),"qa_item_name");
                    frame_layout_qa_item_list.setVisibility(View.VISIBLE);
                    et_qa_item_name.setEnabled(true);
                    view_weight.setVisibility(View.VISIBLE);
                }
                else {
                    frame_layout_qa_item_list.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        iv_refresh_details.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                et_qa_organizer_name.setText("");
                et_qa_organizer_name.clearFocus();
                frame_layout_organizer_qa_list.setVisibility(View.GONE);
                qa_organser_name_input_layout.setStartIconDrawable(R.drawable.ic_baseline_search_24);
                et_qa_item_name.setText("");
                et_qa_item_name.clearFocus();
                frame_layout_qa_item_list.setVisibility(View.GONE);
                qa_item_name_input_layout.setStartIconDrawable(R.drawable.ic_baseline_search_24);
                return true;
            }
        });

        submit_report_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitQAStatus();
            }
        });

        parent_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    frame_layout_organizer_qa_list.setVisibility(View.GONE);
                    frame_layout_qa_item_list.setVisibility(View.GONE);
                    parent_layout.requestFocus();
                }
                return true;
            }
        });

        clear_report_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_qa_item_layout.setVisibility(View.VISIBLE);
                qa_recycler_view_list.setVisibility(View.GONE);
                et_qa_organizer_name.setText("");
                et_qa_organizer_name.clearFocus();
                et_qa_organizer_name.setEnabled(true);
                qa_organser_name_input_layout.setStartIconDrawable(R.drawable.ic_baseline_search_24);
                et_qa_item_name.setText("");
                et_qa_item_name.clearFocus();
                et_qa_item_name.setEnabled(true);
                qa_item_name_input_layout.setStartIconDrawable(R.drawable.ic_baseline_search_24);
                submit_report_name.setVisibility(View.VISIBLE);
                clear_report_name.setVisibility(View.GONE);
                filter_download_floating_btn.setVisibility(View.GONE);
                frame_layout_organizer_qa_list.setVisibility(View.GONE);
                frame_layout_qa_item_list.setVisibility(View.GONE);
            }
        });

        filter_download_floating_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                filter_download_floating_btn.setVisibility(View.GONE);
                HitDownloadFileApi(ApiUtils.BASE_URL, et_qa_organizer_name.getText().toString(), et_qa_item_name.getText().toString());
            }
        });

    }

    private void submitQAStatus() {
        if(NetworkUtil.getConnectivityStatusBoolean(getActivity())){
            if(et_qa_organizer_name.getText().toString().equalsIgnoreCase("") && et_qa_item_name.getText().toString().equalsIgnoreCase("")){
                Toast.makeText(getActivity(), "Data Fetch Successfully !", Toast.LENGTH_SHORT).show();
                qaInspectionListView("","","qa_adapter_list");
                submit_report_name.setVisibility(View.GONE);
                clear_report_name.setVisibility(View.VISIBLE);
                et_qa_organizer_name.setEnabled(false);
                et_qa_item_name.setEnabled(false);
                filter_download_floating_btn.setVisibility(View.VISIBLE);
                view_weight.setVisibility(View.GONE);
            }else if(!et_qa_organizer_name.getText().toString().equalsIgnoreCase("") || !et_qa_item_name.getText().toString().equalsIgnoreCase("")){
                qaInspectionListView(et_qa_organizer_name.getText().toString(), et_qa_item_name.getText().toString(),"qa_adapter_list");
                submit_report_name.setVisibility(View.GONE);
                filter_download_floating_btn.setVisibility(View.VISIBLE);
                clear_report_name.setVisibility(View.VISIBLE);
                et_qa_organizer_name.setEnabled(false);
                et_qa_item_name.setEnabled(false);
            }
            else {
                Snackbar.make(submit_report_name,"No Network Connection. ", Snackbar.LENGTH_INDEFINITE).setAction("Cancel", view -> {
                }).show();
            }
        }
    }

    void initView(View view){
        frame_layout_organizer_qa_list = view.findViewById(R.id.frame_layout_organizer_qa_list);
        frame_layout_qa_item_list = view.findViewById(R.id.frame_layout_qa_item_list);
        rv_organiser_list_items = view.findViewById(R.id.rv_organiser_list_items);
        rv_item_list_items = view.findViewById(R.id.rv_item_list_items);
        qa_recycler_view_list = view.findViewById(R.id.qa_recycler_view_list);
        qa_organser_name_input_layout = view.findViewById(R.id.qa_organser_name_input_layout);
        qa_item_name_input_layout = view.findViewById(R.id.qa_item_name_input_layout);
        et_qa_organizer_name = view.findViewById(R.id.et_qa_organizer_name);
        et_qa_item_name = view.findViewById(R.id.et_qa_item_name);
        loading_item = view.findViewById(R.id.loading_item);
        progressBar = view.findViewById(R.id.progressBar);
        parent_layout = view.findViewById(R.id.parent_layout);
        submit_report_name = view.findViewById(R.id.submit_report_name);
        clear_report_name = view.findViewById(R.id.clear_report_name);
        search_qa_item_layout = view.findViewById(R.id.search_qa_item_layout);
        filter_download_floating_btn = view.findViewById(R.id.filter_download_floating_btn);
        view_weight = view.findViewById(R.id.view_weight);
        iv_refresh_details = view.findViewById(R.id.iv_refresh_details);
        progress_percentage_count = view.findViewById(R.id.progress_percentage_count);
        text_percent = view.findViewById(R.id.text_percent);

        //todo organiser name layout manager set....
        rv_qa_organizer_name_manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_organiser_list_items.setLayoutManager(rv_qa_organizer_name_manager);

        //todo item name layout manager set....
        rv_qa_item_name_manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_item_list_items.setLayoutManager(rv_qa_item_name_manager);

        //todo qa inspection status list layout set....
        rv_qa_linear_layout_manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        qa_recycler_view_list.setLayoutManager(rv_qa_linear_layout_manager);
    }

    @Override
    public void onResume() {
        super.onResume();
        frame_layout_organizer_qa_list.setFocusable(false);
        frame_layout_qa_item_list.setFocusable(false);
    }

    List<ProdAndQAInspectionStatusModel> qaInspectionStatusModelList_gl = new ArrayList<>();

    public void qaInspectionListView(String organiser_name, String item_name, String flag){
        if(NetworkUtil.getConnectivityStatusBoolean(getActivity())){
            loading_item.setVisibility(View.VISIBLE);
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            retrofit2.Call<List<ProdAndQAInspectionStatusModel>> call = mAPIService.qcInspectionStatusView(organiser_name, item_name, sessionManagement.getUserEmail());
            call.enqueue(new Callback<List<ProdAndQAInspectionStatusModel>>() {
               @Override
               public void onResponse(Call<List<ProdAndQAInspectionStatusModel>> call, Response<List<ProdAndQAInspectionStatusModel>> response) {
                   try{
                       if(response.isSuccessful()){
                           loading_item.setVisibility(View.GONE);
                           List<ProdAndQAInspectionStatusModel> responseList = response.body();
                           if(responseList != null && responseList.size() > 0 && responseList.get(0).condition){
                               qaInspectionStatusModelList_gl.clear();
                               qaInspectionStatusModelList_gl.addAll(responseList);

                               if(flag.equals("qa_organiser_name")){
                                   Collections.sort(qaInspectionStatusModelList_gl, new Comparator<ProdAndQAInspectionStatusModel>() {
                                       @Override
                                       public int compare(ProdAndQAInspectionStatusModel o1, ProdAndQAInspectionStatusModel o2) {
                                           return o1.organizer_name.compareTo(o2.organizer_name);
                                       }
                                   });

                                   //todo remove duplicate element from list...
                                   for (int i = 0; i< qaInspectionStatusModelList_gl.size(); i++){
                                       for (int j= i + 1; j < qaInspectionStatusModelList_gl.size(); j++){
                                           if(qaInspectionStatusModelList_gl.get(i).organizer_name.equals(qaInspectionStatusModelList_gl.get(j).organizer_name)){
                                               qaInspectionStatusModelList_gl.remove(j);
                                               j--;
                                           }
                                       }
                                   }
                                   bindQAOrgDropDown();
                               }
                               else if(flag.equals("qa_item_name")){
                                   Collections.sort(qaInspectionStatusModelList_gl, new Comparator<ProdAndQAInspectionStatusModel>() {
                                       @Override
                                       public int compare(ProdAndQAInspectionStatusModel o1, ProdAndQAInspectionStatusModel o2) {
                                           return o1.item_name.compareTo(o2.item_name);
                                       }
                                   });

                                   //todo remove duplicate element....
                                   for (int i = 0; i< qaInspectionStatusModelList_gl.size(); i++){
                                       for (int j= i + 1; j < qaInspectionStatusModelList_gl.size(); j++){
                                           if(qaInspectionStatusModelList_gl.get(i).item_name.equals(qaInspectionStatusModelList_gl.get(j).item_name)){
                                               qaInspectionStatusModelList_gl.remove(j);
                                               j--;
                                           }
                                       }
                                   }
                                   bindQAItemDropDown();
                               }
                               else if(flag.equals("qa_adapter_list")){
                                   clear_report_name.setVisibility(View.VISIBLE);
                                   bindQAAdpaterList(responseList);
                               }
                               else {
                                   Toast.makeText(getActivity(), responseList.get(0).message, Toast.LENGTH_SHORT).show();
                               }
                           }else {
                               Toast.makeText(getActivity(), "No Record Found On Item_name: " + et_qa_item_name.getText().toString(), Toast.LENGTH_SHORT).show();
                           }
                       }else {
                           loading_item.setVisibility(View.GONE);
                           Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                       }
                   }catch (Exception e){
                       e.printStackTrace();
                   }
               }

               @Override
               public void onFailure(Call<List<ProdAndQAInspectionStatusModel>> call, Throwable t) {

               }
           });
        }
        else {
            Toast.makeText(getActivity(), "Please wait for online connection", Toast.LENGTH_SHORT).show();
        }
    }

    //todo qa inspection status adapter list bind....
    private void bindQAAdpaterList(List<ProdAndQAInspectionStatusModel> inspectionStatusModels) {
        qa_recycler_view_list.setVisibility(View.VISIBLE);
        search_qa_item_layout.setVisibility(View.VISIBLE);
        qaInspectionListAdpater = new ProdAndQAInspectionStatusListAdpater(getActivity(), inspectionStatusModels, "qa_status_view");
        qa_recycler_view_list.setAdapter(qaInspectionListAdpater);
    }

    //todo organiser name list binding....
    private void bindQAOrgDropDown() {
        ProdAndQAOrgNameAdapter qaOrgNameAdapter = new ProdAndQAOrgNameAdapter(getActivity(), qaInspectionStatusModelList_gl);
        rv_organiser_list_items.setAdapter(qaOrgNameAdapter);
        qaOrgNameAdapter.setOnOrgClickListner(this);
    }

    //todo item name list binding...
    private void bindQAItemDropDown() {
        ProdAndQAItemNameAdapter qaItemNameAdapter = new ProdAndQAItemNameAdapter(getActivity(), qaInspectionStatusModelList_gl);
        rv_item_list_items.setAdapter(qaItemNameAdapter);
        qaItemNameAdapter.setOnItemClickListner(this);
    }

    @Override
    public void onOrgClick(int pos) {
        frame_layout_organizer_qa_list.setVisibility(View.GONE);
        qaInspectionStatusModel = qaInspectionStatusModelList_gl.get(pos);
        if(qaInspectionStatusModel != null){
            et_qa_organizer_name.setText(qaInspectionStatusModelList_gl.get(pos).organizer_name);
            frame_layout_organizer_qa_list.setVisibility(View.GONE);
        }else {
            frame_layout_organizer_qa_list.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemClick(int pos) {
        frame_layout_qa_item_list.setVisibility(View.GONE);
        qaInspectionStatusModel = qaInspectionStatusModelList_gl.get(pos);
        if(qaInspectionStatusModel != null){
            et_qa_item_name.setText(qaInspectionStatusModelList_gl.get(pos).item_name);
            frame_layout_qa_item_list.setVisibility(View.GONE);
        }else {
            frame_layout_qa_item_list.setVisibility(View.VISIBLE);
        }
    }

    //todo codes for download files...
    private void HitDownloadFileApi(String baseUrl, String organiser_name, String item_name) {
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://hytechdev.pristinefulfil.com");
        Retrofit retrofit = builder.build();
        NetworkInterface downloadInterface = retrofit.create(NetworkInterface.class);
        Call<ResponseBody> call = downloadInterface.downloadQCInspectionFile(organiser_name, item_name, sessionManagement.getUserEmail());
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
                Snackbar.make(filter_download_floating_btn, "No :(", Snackbar.LENGTH_INDEFINITE).setAction("Cancel", v -> {
                }).show();
            }
        });
    }

    private void writeResponseBodyToDisk(ResponseBody responseBody, String file_name) {
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

                        long fileSize = responseBody.contentLength();
                        long fileSizeDownloaded = 0;

                        inputStream = responseBody.byteStream();
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
    }

    private void showForegroundNotification(FragmentActivity activity, String desc) {
        Intent showTaskIntent = new Intent(getActivity(), ProdInspectionStatusFragment.class);
        showTaskIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_SINGLE_TOP);
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
        manager.notify(0, notification.build());
    }


   /* private void showForegroundNotification(Context context, String desc) {
        // pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        String temp_file_name;
        temp_file_name = headers.get("filename");
        Intent showTaskIntent = new Intent(getActivity(), QAInspectionStatusReportFragment.class);
        showTaskIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_SINGLE_TOP);
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), temp_file_name);
        Log.e("filename", String.valueOf(file));
        PendingIntent p;
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        String ext = file.getName().substring(file.getName().indexOf(".") + 1);
        String type = mime.getMimeTypeFromExtension(ext);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.fromFile(file));
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentURI = FileProvider.getUriForFile(requireActivity(),
                        BuildConfig.APPLICATION_ID + ".provider", file);
                intent.setDataAndType(contentURI, type);
            } else {
                intent.setDataAndType(Uri.fromFile(file), type);
            }
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getContext(), "No activity found to open this attachment.", Toast.LENGTH_LONG).show();
        }
        p = PendingIntent.getActivity(getActivity(), 0, intent, 0);

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
                    .setContentIntent(p)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setWhen(System.currentTimeMillis());
        } else {
            notification = new Notification.Builder(getActivity())
                    .setContentTitle("Report")
                    .setContentText(desc)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setPriority(Notification.PRIORITY_MAX)
                    .setContentIntent(p)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setWhen(System.currentTimeMillis())
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setVibrate(new long[]{DEFAULT_VIBRATE})
                    .setWhen(System.currentTimeMillis());
        }

        manager.notify(0, notification.build());
    }*/

}