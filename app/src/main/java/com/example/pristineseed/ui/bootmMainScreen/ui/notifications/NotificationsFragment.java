package com.example.pristineseed.ui.bootmMainScreen.ui.notifications;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.pristineseed.R;
import com.example.pristineseed.global.LoadingDialog;
import com.example.pristineseed.retrofitApi.Http_Hanler.HttpHandlerModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }
    Button downloadButton,downloadButton_WithProgress;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        downloadButton=view.findViewById(R.id.downloadButton);
        downloadButton_WithProgress=view.findViewById(R.id.downloadButton_WithProgress);
        downloadButton.setOnClickListener(view1 -> {
            try {
                    new DownloadNewVersion().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR,"https://zivame-dev.pristinefulfil.com/app/pfse_4.0.apk");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        downloadButton_WithProgress.setOnClickListener(view1 -> {
            try {
                new DownloadNewVersion1().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR,"https://zivame-dev.pristinefulfil.com/app/pfse_4.0.apk");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    LoadingDialog loadingDialog;

    class DownloadNewVersion extends AsyncTask<String, Integer, HttpHandlerModel> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            String msg = "";
            if (progress[0] > 99) {
                msg = "Finishing... ";

            } else {

                msg = "Downloading... " + progress[0] + "%";
            }
            downloadButton.setText(msg);
        }

        @Override
        protected HttpHandlerModel doInBackground(String... urls) {
            HttpHandlerModel passdata = new HttpHandlerModel();
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection c = (HttpURLConnection) url.openConnection();
                c.setRequestMethod("GET");
                //  c.setDoOutput(true);
                c.connect();
                String PATH = Environment.getExternalStorageDirectory() + "/Download/";
                File file = new File(PATH);
                file.mkdirs();
                File outputFile = new File(file, "Pristine.apk");
                if (outputFile.exists()) {
                    outputFile.delete();
                }

                FileOutputStream fos = new FileOutputStream(outputFile);
                InputStream is = c.getInputStream();

                int total_size = c.getContentLength();//1431692;//size of apk

                byte[] buffer = new byte[1024];
                int len1 = 0;
                int per = 0;
                int downloaded = 0;
                while ((len1 = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len1);
                    downloaded += len1;
                    per = downloaded * 100 / total_size;
                    publishProgress(per);
                }
                fos.close();
                is.close();
                passdata.setConnectStatus(true);
                passdata.setJsonResponse("Success Download");
                return passdata;
            } catch (Exception e) {
                passdata.setConnectStatus(false);
                passdata.setJsonResponse(e.getMessage());
                return passdata;
            }
        }

        @Override
        protected void onPostExecute(HttpHandlerModel result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            if (result.isConnectStatus()) {
                OpenNewVersion();
                Toast.makeText(getActivity(), "Update Done",
                        Toast.LENGTH_SHORT).show();
                downloadButton.setText("Download");

            } else {
//                errorCode.setText(result.getJsonResponse() + " Api Error please check Apk path on web.");
                downloadButton.setText("Download");
            }
            downloadButton.setEnabled(true);
        }


    }
    class DownloadNewVersion1 extends AsyncTask<String, Integer, HttpHandlerModel> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingDialog=new LoadingDialog();
          //  loadingDialog.showLoadingDialog(getActivity());
        }

        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            String msg = "";
            if (progress[0] > 99) {
                msg = "Finishing... ";

            } else {

                msg = "Downloading... " + progress[0] + "%";
            }
            downloadButton_WithProgress.setText(msg);
        }

        @Override
        protected HttpHandlerModel doInBackground(String... urls) {
            HttpHandlerModel passdata = new HttpHandlerModel();
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection c = (HttpURLConnection) url.openConnection();
                c.setRequestMethod("GET");
                //  c.setDoOutput(true);
                c.connect();
                String PATH = Environment.getExternalStorageDirectory() + "/Download/";
                File file = new File(PATH);
                file.mkdirs();
                File outputFile = new File(file, "Pristine.apk");
                if (outputFile.exists()) {
                    outputFile.delete();
                }

                FileOutputStream fos = new FileOutputStream(outputFile);
                InputStream is = c.getInputStream();

                int total_size = c.getContentLength();//1431692;//size of apk

                byte[] buffer = new byte[1024];
                int len1 = 0;
                int per = 0;
                int downloaded = 0;
                while ((len1 = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len1);
                    downloaded += len1;
                    per = downloaded * 100 / total_size;
                    publishProgress(per);
                }
                fos.close();
                is.close();
                passdata.setConnectStatus(true);
                passdata.setJsonResponse("Success Download");
                return passdata;
            } catch (Exception e) {
                passdata.setConnectStatus(false);
                passdata.setJsonResponse(e.getMessage());
                return passdata;
            }
        }

        @Override
        protected void onPostExecute(HttpHandlerModel result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            if (result.isConnectStatus()) {
                OpenNewVersion();
                Toast.makeText(getActivity(), "Update Done",
                        Toast.LENGTH_SHORT).show();
                downloadButton_WithProgress.setText("Download");

            } else {
//                errorCode.setText(result.getJsonResponse() + " Api Error please check Apk path on web.");
                downloadButton_WithProgress.setText("Download");
            }
           // loadingDialog.dismissLoading();
        }


    }
    void OpenNewVersion() {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/Download/" + "Pristine.apk")),
                    "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (Exception e) {
            File file = new File(Environment.getExternalStorageDirectory() + "/Download/" + "Pristine.apk");
            Intent install = new Intent(Intent.ACTION_VIEW);
            install.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
// Old Approach
            install.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
// End Old approach
// New Approach
            Uri apkURI = FileProvider.getUriForFile(
                    getActivity(),
                    getActivity().getApplicationContext()
                            .getPackageName() + ".provider", file);
            install.setDataAndType(apkURI, "application/vnd.android.package-archive");
            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
// End New Approach
            startActivity(install);
        }
    }
}