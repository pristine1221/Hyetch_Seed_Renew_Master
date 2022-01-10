package com.example.pristineseed.ui.image;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.pristineseed.R;
import com.example.pristineseed.global.FilePath;
import com.example.pristineseed.retrofitApi.Http_Hanler.MultipartUtility;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class ImageCaptureFragment extends Fragment {
    Button capture_image, select_captured_image,select_uploadImages;
    LinearLayout image_Layouts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_capture, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        capture_image = view.findViewById(R.id.capture_image);
        select_captured_image = view.findViewById(R.id.select_captured_image);
        image_Layouts = view.findViewById(R.id.image_Layouts);
        capture_image.setOnClickListener(view1 -> {
            try {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    File photoFile = createImageFile();
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(getActivity(),
                                "com.example.pristineseed.provider",
                                photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, 0);
                    } else {
                        Snackbar.make(getView(), "File Not Created", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(getView(), "Intent do not have resolve activity", Snackbar.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Snackbar.make(getView(), e.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });

//        todo browse the image
        select_captured_image.setOnClickListener(view1 -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
        });
        select_uploadImages=view.findViewById(R.id.select_uploadImages);
        select_uploadImages.setOnClickListener(view1 -> {
            new UploadFileAsyTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0: {
                try {
                    if (resultCode == RESULT_OK) {
                        setPic(currentPhotoPath);
                    } else if (resultCode == RESULT_CANCELED) {
                        Snackbar.make(getView(), "Cancel Capture Image", Snackbar.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Snackbar.make(getView(), e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
                break;
            }
            case 1: {
                try {
                    if (resultCode == RESULT_OK && null != data) {
                        if (data.getData() != null) {
                            setPic(FilePath.getPath(getActivity(), data.getData()));
                        } else {
                            if (data.getClipData() != null) {
                                ClipData mClipData = data.getClipData();
                                for (int i = 0; i < mClipData.getItemCount(); i++) {
                                    ClipData.Item item = mClipData.getItemAt(i);
                                    Uri uri = item.getUri();
                                    setPic(FilePath.getPath(getActivity(), uri));
                                }
                            }
                        }
                    } else if (resultCode == RESULT_CANCELED) {
                        Snackbar.make(getView(), "You haven't picked Image",
                                Snackbar.LENGTH_LONG).show();
                    } else {
                        Snackbar.make(getView(), "Image data Is Null.",
                                Snackbar.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Snackbar.make(getView(), e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
                break;
            }
        }
        BindImageViews();
    }

    ArrayList<ImageCaptureModel> captureImageList = new ArrayList<>();
    String currentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void setPic(String currentPhotoPath) {
        // Get the dimensions of the View
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int targetW = displayMetrics.widthPixels / 2;
        int targetH = displayMetrics.heightPixels / 4;
        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(currentPhotoPath, bmOptions);

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.max(1, Math.min(photoW / targetW, photoH / targetH));

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        ImageCaptureModel model = new ImageCaptureModel();
        model.setCurrentPhotoPath(currentPhotoPath);
        model.setCurrentPhotoBitmap(bitmap);
        captureImageList.add(model);
    }

    void BindImageViews() {
        image_Layouts.removeAllViews();
        for (int i = 0; i < this.captureImageList.size(); i++) {
            ImageView imageView = new ImageView(getActivity());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            imageView.setLayoutParams(layoutParams);
            imageView.setImageBitmap(this.captureImageList.get(i).getCurrentPhotoBitmap());
            imageView.setAdjustViewBounds(true);
            imageView.setPadding(5, 5, 5, 5);
            imageView.setElevation(5f);
            image_Layouts.addView(imageView);
        }
    }

    class ImageCaptureModel {
        private Bitmap currentPhotoBitmap;
        private String currentPhotoPath;

        public Bitmap getCurrentPhotoBitmap() {
            return currentPhotoBitmap;
        }

        public void setCurrentPhotoBitmap(Bitmap currentPhotoBitmap) {
            this.currentPhotoBitmap = currentPhotoBitmap;
        }

        public String getCurrentPhotoPath() {
            return currentPhotoPath;
        }

        public void setCurrentPhotoPath(String currentPhotoPath) {
            this.currentPhotoPath = currentPhotoPath;
        }
    }

    public class UploadFileAsyTask extends AsyncTask<String, String, String> {
        ProgressDialog progress;
        public UploadFileAsyTask() {
        }

        @Override
        protected void onPreExecute() {
            progress = new ProgressDialog(getActivity());
            progress.setMessage("Please Wait..");
            progress.setCanceledOnTouchOutside(false);
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.show();
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            progress.setMessage("Image Procesing " + values[0]);
        }

        @Override
        protected String doInBackground(String... customerAddSendModels) {
            try {

                MultipartUtility multipartUtility = new MultipartUtility("https://techteam2.pristinefulfil.com/api/Customer/CreateVendor");
                multipartUtility.addFormField("type","International");
                multipartUtility.addFormField("name","name");
                multipartUtility.addFormField("country", "2");
                multipartUtility.addFormField("state", "2");
                multipartUtility.addFormField("city", "2");
                multipartUtility.addFormField("pincode","201001");
                multipartUtility.addFormField("email"," dfsgfg@dfg.com");
                multipartUtility.addFormField("mobile_no","8506988092");
                multipartUtility.addFormField("phone_no", "45624564");
                multipartUtility.addFormField("address", "2");
                multipartUtility.addFormField("contact_person_name", "2");
                multipartUtility.addFormField("oprtn_prsn_name","201001");

                multipartUtility.addFormField("accnt_prsn_name","International");
                multipartUtility.addFormField("warehouse_prsn_name","name");
                multipartUtility.addFormField("prchase_order_prsn_name", "2");
                multipartUtility.addFormField("cntct_prn_cntct_no", "2");
                multipartUtility.addFormField("oprtn_prsn_cntct_no", "2");
                multipartUtility.addFormField("accnt_prsn_cntct_no","201001");
                multipartUtility.addFormField("warehouse_prsn_cntct_no","3");
                multipartUtility.addFormField("prchase_order_cntct_no","8506988092");
                multipartUtility.addFormField("gst_type", "Unregistered");
                multipartUtility.addFormField("gst_no", "");
                multipartUtility.addFormField("pan_no", " AAAAA1234A");
                multipartUtility.addFormField("currency"," INR");
                multipartUtility.addFormField("created_by","  xox");
                for (int i = 0; i < captureImageList.size(); i++) {
                    multipartUtility.addFilePart("file", new File(captureImageList.get(i).getCurrentPhotoPath()));
                }

                String imageResponse = multipartUtility.finish().getJsonResponse();
                Log.e("Response",imageResponse);
                return imageResponse;
            } catch (Exception e) {
                Toast.makeText(getActivity(),e.getMessage(), Toast.LENGTH_LONG).show();
                return "";
            }
        }

        @Override
        protected void onPostExecute(String response) {
            progress.dismiss();
        }
    }
}

