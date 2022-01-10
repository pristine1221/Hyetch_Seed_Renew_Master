
package com.example.pristineseed.ui.bootmMainScreen.ui.profile;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.media.Image;
import android.media.ImageReader;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.example.pristineseed.DataBaseRepository.Scheduler.OfflineStatusUpdateModel;
import com.example.pristineseed.DataBaseRepository.Scheduler.ScheduleInspectionLineDao;
import com.example.pristineseed.LoginActivity;
import com.example.pristineseed.R;
import com.example.pristineseed.RoomDataBase.PristineDatabase;
import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.global.ApiRequestFailure;
import com.example.pristineseed.global.LoadingDialog;
import com.example.pristineseed.global.StaticMethods;
import com.example.pristineseed.model.ResponseModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.ManualSyncingMaster.GeographicalSyncing;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.JsonObject;
import com.valdesekamdem.library.mdtoast.MDToast;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment implements EZCamCallback, View.OnLongClickListener {
    private SessionManagement sessionManagement;
    private LinearLayout logout_profile;
    private LinearLayout sycnData;
    private TextView tv_main_user_name,tv_username,tv_user_mobile_no,tv_user_email;
    private ImageView profile_pic;
    public static int REQUEST_CAMERA=5;
    EZCam cam;
    TextureView   textureView;
    SimpleDateFormat dateFormat;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManagement=new SessionManagement(getActivity());
        tv_main_user_name=view.findViewById(R.id.tv_main_user_name);
        tv_main_user_name.setText(sessionManagement.getUserName());
        tv_username=view.findViewById(R.id.tv_username);
        tv_username.setText(sessionManagement.getUserName());
        tv_user_mobile_no=view.findViewById(R.id.tv_user_mobile_no);
        if(sessionManagement.getUserPhone()!=null) {
            tv_user_mobile_no.setText(sessionManagement.getUserPhone());
        }
        tv_user_email=view.findViewById(R.id.tv_user_email);
        logout_profile=view.findViewById(R.id.logout_profile);
        profile_pic=view.findViewById(R.id.profile_pic);
        tv_user_email.setText(sessionManagement.getUserEmail());
        sycnData=view.findViewById(R.id.sycn_data);
        logout_profile.setOnClickListener(v -> {
                new MaterialAlertDialogBuilder(getActivity())
                        .setTitle("Are you sure to logout ? ")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                callLogoutApi();
                            }
                        })
                        .setNegativeButton("CANCEL", null)
                        .show();
            });
        sycnData.setOnClickListener(v -> {
            StaticMethods.loadFragmentsWithBackStack(getActivity(),new GeographicalSyncing(),"Geo_sync_data");
        });

        //   textureView = (TextureView)view.findViewById(R.id.textureView);
        profile_pic.setOnClickListener(v -> {
          /*  Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_CAMERA);*/

        });
/*
        cam = new EZCam(getActivity());
        cam.setCameraCallback(this);

        String id = cam.getCamerasList().get(CameraCharacteristics.LENS_FACING_BACK);
        cam.selectCamera(id);
        dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault());*/
        //takePicture();
    }


 /*   private void takePicture(){

        Dexter.withActivity(getActivity()).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                cam.open(CameraDevice.TEMPLATE_PREVIEW, textureView);
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).check();

    }
*/

    void callLogoutApi(){
        LoadingDialog loadingDialog=new LoadingDialog();
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        loadingDialog.showLoadingDialog(getActivity());
        JsonObject postedJson = new JsonObject();
        postedJson.addProperty("Email",sessionManagement.getUserEmail());
        Call<List<ResponseModel>>call = mAPIService.logout(postedJson);
        call.enqueue(new Callback<List<ResponseModel>>() {
            @Override
            public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                try {
                    if (response.isSuccessful()) {
                        loadingDialog.hideDialog();
                        List<ResponseModel> logOutModelResponse= response.body();
                        if (logOutModelResponse!=null && logOutModelResponse.size()>0 && logOutModelResponse.get(0).condition) {
                            sessionManagement.ClearSession();
                            deleteDataBase();
                            Intent mainIntent = new Intent(getActivity(), LoginActivity.class);
                            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// clear back stack
                            startActivity(mainIntent);

                            getActivity().finish();
                            Toast.makeText(getActivity(),logOutModelResponse.get(0).action,Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getActivity(), logOutModelResponse.get(0).action, Toast.LENGTH_SHORT).show();
                            }
                              }
                    else{
                        loadingDialog.hideDialog();
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    loadingDialog.hideDialog();
                    Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "profile_fragment_logout", getActivity());
                }finally {
                    loadingDialog.hideDialog();
                }
            }
            @Override
            public void onFailure(Call<List<ResponseModel>> call, Throwable t) {
                loadingDialog.hideDialog();
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "profile_fragment_logout", getActivity());
            }
        });
    }


    @Override
    public boolean onLongClick(View v) {
       // cam.takePicture();
        return false;
    }

    @Override
    public void onCameraReady() {
       /* cam.setCaptureSetting(CaptureRequest.COLOR_CORRECTION_ABERRATION_MODE, CameraMetadata.COLOR_CORRECTION_ABERRATION_MODE_HIGH_QUALITY);
        cam.startPreview();*/

       // textureView.setOnLongClickListener(this);
    }

    @Override
    public void onPicture(Image image) {
       /* cam.stopPreview();
        try {
            String filename = "image_"+dateFormat.format(new Date())+".jpg";
            File file = new File(getActivity().getFilesDir(), filename);
            EZCam.saveImage(image, file);
            String absoulyte_path =file.getAbsolutePath();
            File file1 = new File(absoulyte_path);
            Log.e("file_path", String.valueOf(file1));
           // Glide.with(this).load(file1).into(imageView);
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }*/
    }

    @Override
    public void onError(String message) {
        Log.e("onerr", message);
    }

    @Override
    public void onCameraDisconnected() {
        Log.e("cam_discnnt", "Camera disconnected");
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
    }
    private void deleteDataBase(){
        PristineDatabase pristineDatabase =PristineDatabase.getAppDatabase(getActivity());
        try{
            ScheduleInspectionLineDao scheduleInspectionLineDao=pristineDatabase.scheduleInspectionLineDao();
            List<OfflineStatusUpdateModel>  offlineStatusUpdateModelList= scheduleInspectionLineDao.getTableUpdate();
            if(offlineStatusUpdateModelList.size()>0){
                if(offlineStatusUpdateModelList.get(0).ins1_offline>0 || offlineStatusUpdateModelList.get(0).ins2_offline>0  ||
                        offlineStatusUpdateModelList.get(0).ins3_offline>0  ||
                        offlineStatusUpdateModelList.get(0).ins4_offline>0  ||
                        offlineStatusUpdateModelList.get(0).ins5_offline>0  ||
                        offlineStatusUpdateModelList.get(0).ins6_offline>0  ||

                        offlineStatusUpdateModelList.get(0).ins7_offline>0  ||
                        offlineStatusUpdateModelList.get(0).ins8_offline>0  ||

                        offlineStatusUpdateModelList.get(0).ins9_offline>0  ||
                        offlineStatusUpdateModelList.get(0).insqc_offline>0)
                    {
                      StaticMethods.showMDToast(getActivity(),"You can't update detials until inspections is syncing with server.",MDToast.TYPE_ERROR);
                    }
                 else {
                    getActivity().deleteDatabase("pristine_hytech_database");
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            pristineDatabase.close();
            pristineDatabase.destroyInstance();
        }


    }
    /*for test*/
}