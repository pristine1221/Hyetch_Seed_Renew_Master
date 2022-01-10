package com.example.pristineseed.ui.reports;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.gson.Gson;

import okhttp3.ResponseBody;

public class WorkManagerService extends Worker {
    private Context context;

    public WorkManagerService(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context=context;
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.e("work","sucess");
        Data data=getInputData();
        ResponseBody responseBody=new Gson().fromJson(data.getString("file_data"),ResponseBody.class);
        String file_name=data.getString("FileName");
        Log.e("file_name",file_name);


        return Result.success();
    }
}