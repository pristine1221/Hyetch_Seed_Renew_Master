package com.example.pristineseed.ui.reports;

import static android.app.Activity.RESULT_OK;
import static android.app.Notification.DEFAULT_VIBRATE;
import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pristineseed.GlobalNotification.NetworkUtil;
import com.example.pristineseed.R;
import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.model.reportModel.PLDandSownAcreViewModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.adapter.reportsAdapter.sownAcreAdapters.PLDandSownItemNameAdapter;
import com.example.pristineseed.ui.adapter.reportsAdapter.sownAcreAdapters.SownAcreListViewAdapter;
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
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PLDandSownAcreViewFragment extends Fragment implements PLDandSownItemNameAdapter.OnItemClick{

    private TextInputLayout pld_sown_item_name_layout;
    private TextInputEditText et_item_name;
    private ImageView iv_refresh_details;
    private View item_view;
    private MaterialButton submit_items, clear_items,  image_upload_btn;
    private RecyclerView rv_pld_item_name, pld_sown_list_rv;
    private LinearLayoutManager pld_sown_list_manager, pld_sown_item_manager;
    private MaterialCardView frame_pld_item_name_mcv;
    private RelativeLayout parent_relative_layout;
    private FloatingActionButton filter_download_floating_btn;
    private ProgressBar loading_item6,  progressBar;
    private TextView progress_percentage_count, text_percent;
    private SessionManagement sessionManagement;
    private List<PLDandSownAcreViewModel> plDandSownAcreViewModelList_gl = new ArrayList<>();
    private PLDandSownAcreViewModel dueInspectionModel;
    private SownAcreListViewAdapter listViewAdapter;

    private ImageView image_view;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_p_l_dand_sown_acre_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        sessionManagement = new SessionManagement(getActivity());

        et_item_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus && et_item_name.isCursorVisible()){
                    frame_pld_item_name_mcv.setVisibility(View.VISIBLE);
                    pld_sown_item_name_layout.setStartIconDrawable(null);
                    et_item_name.setEnabled(true);
                    getPLDSownAcreList("","item_name");
                    item_view.setVisibility(View.VISIBLE);
                }else {
                    if (!et_item_name.getText().toString().trim().equalsIgnoreCase("")) {
                        pld_sown_item_name_layout.setStartIconDrawable(null);
                    } else {
                        pld_sown_item_name_layout.setStartIconDrawable(R.drawable.ic_baseline_search_24);
                        frame_pld_item_name_mcv.setVisibility(View.GONE);
                    }
                }
            }
        });

        et_item_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                et_item_name.setSelection(s.toString().length());
                if (!s.toString().equalsIgnoreCase("") && et_item_name.isCursorVisible()) {
                    frame_pld_item_name_mcv.setVisibility(View.VISIBLE);
                    et_item_name.setEnabled(true);
                    getPLDSownAcreList(s.toString(),"item_name");
                    item_view.setVisibility(View.VISIBLE);
                } else {
                    frame_pld_item_name_mcv.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        iv_refresh_details.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                et_item_name.setText("");
                et_item_name.clearFocus();
                frame_pld_item_name_mcv.setVisibility(View.GONE);
                pld_sown_item_name_layout.setStartIconDrawable(R.drawable.ic_baseline_search_24);
                return true;
            }
        });

        parent_relative_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    frame_pld_item_name_mcv.setVisibility(View.GONE);
                    et_item_name.clearFocus();
                    parent_relative_layout.requestFocus();
                }
                return true;
            }
        });

        clear_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear_items.setVisibility(View.GONE);
                submit_items.setVisibility(View.VISIBLE);
                et_item_name.setText("");
                et_item_name.clearFocus();
                et_item_name.setEnabled(true);
                pld_sown_item_name_layout.setStartIconDrawable(R.drawable.ic_baseline_search_24);
                filter_download_floating_btn.setVisibility(View.GONE);
                pld_sown_list_rv.setVisibility(View.GONE);
                frame_pld_item_name_mcv.setVisibility(View.GONE);
            }
        });

        submit_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitItemName();
            }
        });

        filter_download_floating_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                filter_download_floating_btn.setVisibility(View.GONE);
                HitDownloadFileApi(ApiUtils.BASE_URL, et_item_name.getText().toString());
            }
        });

        image_upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onGallery();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        frame_pld_item_name_mcv.setFocusable(false);
        frame_pld_item_name_mcv.setVisibility(View.GONE);
    }

    void initView(View view){
        pld_sown_item_name_layout = view.findViewById(R.id.pld_sown_item_name_layout);
        et_item_name = view.findViewById(R.id.et_item_name);
        iv_refresh_details = view.findViewById(R.id.iv_refresh_details);
        item_view = view.findViewById(R.id.item_view);
        submit_items = view.findViewById(R.id.submit_items);
        clear_items = view.findViewById(R.id.clear_items);
        rv_pld_item_name = view.findViewById(R.id.rv_pld_item_name);
        pld_sown_list_rv = view.findViewById(R.id.pld_sown_list_rv);
        frame_pld_item_name_mcv = view.findViewById(R.id.frame_pld_item_name_mcv);
        parent_relative_layout = view.findViewById(R.id.parent_relative_layout);
        filter_download_floating_btn = view.findViewById(R.id.filter_download_floating_btn);
        loading_item6 = view.findViewById(R.id.loading_item6);
        progressBar = view.findViewById(R.id.progressBar);
        progress_percentage_count = view.findViewById(R.id.progress_percentage_count);
        text_percent = view.findViewById(R.id.text_percent);

        image_upload_btn = view.findViewById(R.id.image_upload_btn);
        image_view = view.findViewById(R.id.image_view);

        //todo list manager for list Adapter...
        pld_sown_list_manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        pld_sown_list_rv.setLayoutManager(pld_sown_list_manager);

        //todo list manager for item name list adapter...
        pld_sown_item_manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        rv_pld_item_name.setLayoutManager(pld_sown_item_manager);

    }

    void onGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            image_view.setImageURI(imageUri);
        }
    }

    private void submitItemName() {
        if (NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
            if (et_item_name.getText().toString().equalsIgnoreCase("")) {
                getPLDSownAcreList("","pld_sown_list");
                submit_items.setVisibility(View.GONE);
                clear_items.setVisibility(View.VISIBLE);
                filter_download_floating_btn.setVisibility(View.VISIBLE);
                item_view.setVisibility(View.GONE);
                et_item_name.setEnabled(false);
                frame_pld_item_name_mcv.setVisibility(View.GONE);
            }
            else if(!et_item_name.getText().toString().equalsIgnoreCase("")){
                submit_items.setVisibility(View.GONE);
                clear_items.setVisibility(View.VISIBLE);
                getPLDSownAcreList(et_item_name.getText().toString(),"pld_sown_list");
                filter_download_floating_btn.setVisibility(View.VISIBLE);
                et_item_name.setEnabled(false);
                frame_pld_item_name_mcv.setVisibility(View.GONE);
            }

        }else {
            Snackbar.make(submit_items,"No Network Connection. ", Snackbar.LENGTH_INDEFINITE).setAction("Cancel", view -> {
            }).show();
        }
    }

    public void getPLDSownAcreList(String item_name, String flag){
        if (NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
            loading_item6.setVisibility(View.VISIBLE);
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            Call<List<PLDandSownAcreViewModel>> call = mAPIService.pldAndSownAcreList(item_name, sessionManagement.getUserEmail());
            call.enqueue(new Callback<List<PLDandSownAcreViewModel>>() {
                @Override
                public void onResponse(Call<List<PLDandSownAcreViewModel>> call, Response<List<PLDandSownAcreViewModel>> response) {
                    try{
                        if(response.isSuccessful()){
                            loading_item6.setVisibility(View.GONE);
                            List<PLDandSownAcreViewModel> responseListModel = response.body();
                            if(responseListModel != null && responseListModel.size()>0 && responseListModel.get(0).condition){
                                plDandSownAcreViewModelList_gl.clear();
                                plDandSownAcreViewModelList_gl.addAll(responseListModel);
                                if(flag.equals("item_name")){
                                    bindItemNameListAdapter();
                                }
                                else if(flag.equals("pld_sown_list")){
                                    bindPLDSownAdapter(responseListModel);
                                }
                                else {
                                    Toast.makeText(getActivity(), responseListModel.get(0).message, Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                loading_item6.setVisibility(View.GONE);
                                submit_items.setVisibility(View.VISIBLE);
                                clear_items.setVisibility(View.GONE);
                                et_item_name.setEnabled(true);
                                Toast.makeText(getActivity(), "No Record Found : ", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            loading_item6.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<List<PLDandSownAcreViewModel>> call, Throwable t) {

                }
            });

        }else {
            Toast.makeText(getActivity(), "Please wait for online connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void bindPLDSownAdapter(List<PLDandSownAcreViewModel> viewModelList) {
        listViewAdapter = new SownAcreListViewAdapter(getActivity(), viewModelList,"pld_sown_acre_view");
        pld_sown_list_rv.setAdapter(listViewAdapter);
        pld_sown_list_rv.setVisibility(View.VISIBLE);
    }

    private void bindItemNameListAdapter() {
        PLDandSownItemNameAdapter itemListAdapter = new PLDandSownItemNameAdapter(getActivity(), plDandSownAcreViewModelList_gl,"pld_sown_item_view");
        rv_pld_item_name.setAdapter(itemListAdapter);
        itemListAdapter.setItemClickLisner(this);
    }

    //todo item name click listner....
    @Override
    public void onItemClick(int pos) {
        frame_pld_item_name_mcv.setVisibility(View.GONE);
        dueInspectionModel = plDandSownAcreViewModelList_gl.get(pos);
        if(dueInspectionModel != null){
            et_item_name.setText(plDandSownAcreViewModelList_gl.get(pos).item);
            frame_pld_item_name_mcv.setVisibility(View.GONE);
        }else {
            frame_pld_item_name_mcv.setVisibility(View.VISIBLE);
        }
    }

    private void HitDownloadFileApi(String baseUrl, String item_name) {
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://hytechdev.pristinefulfil.com");
        Retrofit retrofit = builder.build();
        NetworkInterface downloadInterface = retrofit.create(NetworkInterface.class);
        Call<ResponseBody> call = downloadInterface.downloadPLDandSownAcre(item_name, sessionManagement.getUserEmail());
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

}