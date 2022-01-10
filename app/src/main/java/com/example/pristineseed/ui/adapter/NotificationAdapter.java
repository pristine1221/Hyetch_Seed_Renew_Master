package com.example.pristineseed.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.pristineseed.R;
import com.example.pristineseed.firebase_notification_service.InspectionNotificationModel;
import com.example.pristineseed.model.event_managment_model.SyncEventDetailModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.ui.adapter.event_adapterr.EventImageHorizontalAdapter;
import java.util.List;

public class NotificationAdapter  extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {
    private List<InspectionNotificationModel> notification_list;
    Context activity;

    class MyViewHolder extends RecyclerView.ViewHolder {
       private TextView  insp_type,lot_no,tv_created_on,tv_loc;
        MyViewHolder(View view) {
            super(view);
            lot_no = view.findViewById(R.id.lot_no);
            insp_type=view.findViewById(R.id.insp_type);
            tv_created_on=view.findViewById(R.id.created_on);
            tv_loc=view.findViewById(R.id.loc);
        }
    }

    public NotificationAdapter(Activity activity, List<InspectionNotificationModel> notification_list) {
        this.notification_list = notification_list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public NotificationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_layout, parent, false);
        return new NotificationAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NotificationAdapter.MyViewHolder holder, int position) {

       InspectionNotificationModel inspectionNotificationModel=notification_list.get(position);
       holder.tv_created_on.setText(inspectionNotificationModel.created_on);

        List<InspectionNotificationModel.BodyArrayModel> bodyArrayModel1=inspectionNotificationModel.bodyArray;

        for(int i=0;i<bodyArrayModel1.size();i++){
            holder.lot_no.setText(bodyArrayModel1.get(i).production_lot_no);
            holder.insp_type.setText(bodyArrayModel1.get(i).inspection_type);
            holder.tv_loc.setText(bodyArrayModel1.get(i).production_centre_name);
        }

    }

    @Override
    public int getItemCount() {
        if (notification_list != null && notification_list.size() > 0) {
            return notification_list.size();
        } else {
            return 0;
        }
    }

}