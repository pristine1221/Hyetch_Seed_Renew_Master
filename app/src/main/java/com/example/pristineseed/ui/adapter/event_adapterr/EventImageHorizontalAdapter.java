package com.example.pristineseed.ui.adapter.event_adapterr;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.pristineseed.R;
import com.example.pristineseed.model.event_managment_model.SyncEventDetailModel;
import com.example.pristineseed.retrofitApi.ApiUtils;

import java.util.List;

public class EventImageHorizontalAdapter extends RecyclerView.Adapter<EventImageHorizontalAdapter.MyViewHolder> {
    private List<SyncEventDetailModel.ImageListModel> imageUrlList;
    Activity activity;

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgView;

        MyViewHolder(View view) {
            super(view);
            imgView = view.findViewById(R.id.imgView);
        }
    }

    public EventImageHorizontalAdapter(Activity activity, List<SyncEventDetailModel.ImageListModel> imageUrlList) {
        this.imageUrlList = imageUrlList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_display_horizontaly_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        if(imageUrlList.get(position).image_url!=null) {
            Glide.with(activity.getApplicationContext())
                    .load(ApiUtils.BASE_URL + "/" + imageUrlList.get(position).image_url)
                    .apply(RequestOptions.circleCropTransform())
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .placeholder(R.color.gray3)
                    .into(holder.imgView);

        }
    }

    @Override
    public int getItemCount() {
        if(imageUrlList!=null && imageUrlList.size()>0){
            return imageUrlList.size();
        }
        else {
            return 0;
        }
    }
}