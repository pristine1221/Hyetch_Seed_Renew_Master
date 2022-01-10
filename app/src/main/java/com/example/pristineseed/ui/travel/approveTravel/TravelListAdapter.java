package com.example.pristineseed.ui.travel.approveTravel;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.DistricMasterDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.DistricMasterTable;
import com.example.pristineseed.R;
import com.example.pristineseed.RoomDataBase.PristineDatabase;
import com.example.pristineseed.global.DateTimeUtilsCustome;

import com.example.pristineseed.model.travel.ta_da_model.SyncTravelDetailModel;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;

public class TravelListAdapter extends   RecyclerView.Adapter<TravelListAdapter.ViewHolder>  {
    List<SyncTravelDetailModel> listdata;
    List<SyncTravelDetailModel.Travel_Line_Attachments> travelLineAttachmentsList;
    Context activity;
    private final OnItemClickListener listener;


    public interface OnItemClickListener {
        void onItemClick(SyncTravelDetailModel selectedeventData, int position, String flag);
    }

    public TravelListAdapter(Activity activity, List<SyncTravelDetailModel> listdata, OnItemClickListener listener) {
        super();
        this.listdata = listdata;
        this.activity = activity;
        this.listener=listener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_travel_listview, parent, false);
        return  new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(listdata.get(position), activity,position);
    }



    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView travel_code;
        TextView tv_from_loc;
        TextView tv_to_loc;
        TextView tv_start_date;
        TextView tv_end_date;
        TextView tv_travel_reson;
        TextView tv_expense_budget;
        TextView tv_travel_status;
        TextView tv_Created ;
        Chip approve_order;
        Chip reject_order;
        ImageView imgView;
        TextView tv_create_on;
        View view_ty;
        TextView tv_app_budget_title;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            travel_code = itemView.findViewById(R.id.travel_code);
            tv_from_loc = itemView.findViewById(R.id.tv_from_loc);
            tv_to_loc = itemView.findViewById(R.id.tv_to_loc);
            tv_start_date = itemView.findViewById(R.id.tv_start_date);
            tv_end_date = itemView.findViewById(R.id.tv_end_date);
            tv_travel_reson = itemView.findViewById(R.id.tv_travel_reson);
            tv_expense_budget = itemView.findViewById(R.id.tv_expense_budget);
            tv_travel_status = itemView.findViewById(R.id.tv_travel_status);
            tv_Created = itemView.findViewById(R.id.tv_Created);
            approve_order=itemView.findViewById(R.id.approve_order);
            reject_order=itemView.findViewById(R.id.reject_order);

            imgView = itemView.findViewById(R.id.imgView);
            tv_create_on=itemView.findViewById(R.id.tv_create_on);
            tv_app_budget_title=itemView.findViewById(R.id.tv_app_budget_title);
            view_ty=itemView.findViewById(R.id.view_ty);
        }

        public void bindData(SyncTravelDetailModel dataModel, Context context, int position) {
            approve_order.setVisibility(View.VISIBLE);
            reject_order.setVisibility(View.VISIBLE);

            travel_code.setText(dataModel.travelcode);

            tv_from_loc.setText(dataModel.from_loc);
            tv_to_loc.setText(dataModel.to_loc);

            tv_start_date.setText(dataModel.start_date);
            tv_end_date.setText(dataModel.end_date);
            tv_Created.setText(dataModel.created_by);
            tv_travel_reson.setText(dataModel.travel_reson);
            tv_expense_budget.setText(dataModel.expense_budget);
            String status=dataModel.STATUS;

            if(dataModel.created_on!=null){
                tv_create_on.setText(dataModel.created_on != null ? (DateTimeUtilsCustome.getDate_Time(dataModel.created_on)):"");

            }

            if(dataModel.STATUS!=null){
                switch (dataModel.STATUS){
                    case "PENDING":
                        view_ty.setVisibility(View.GONE);
                        tv_travel_status.setTextColor(context.getResources().getColor(R.color.pending_color));
                        tv_travel_status.setText(dataModel.STATUS);
                        break;
                    case "INSERT EXPENSE":
                        view_ty.setVisibility(View.GONE);
                        tv_travel_status.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
                        tv_travel_status.setText(dataModel.STATUS);
                        break;
                    default:
                        view_ty.setVisibility(View.GONE);
                        tv_travel_status.setText(dataModel.STATUS);

                }

            }

            approve_order.setOnClickListener(view -> {
                listener.onItemClick(dataModel, position, "approve");
            });
            reject_order.setOnClickListener(view -> {
                listener.onItemClick(dataModel, position, "reject");
            });

            if (status.equalsIgnoreCase("INSERT EXPENSE")) {
                List<SyncTravelDetailModel.Travel_line_Expense> travelline_expence = dataModel.travel_line_expense;
                if (travelline_expence.size()>0 && travelline_expence.get(0).travel_line_attachments.get(0).attachment != null) {
                    String file_attachment = travelline_expence.get(0).travel_line_attachments.get(0).attachment;
                        byte[] decodedString = Base64.decode(file_attachment, Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                        Glide.with(activity)
                                .asBitmap()
                                .load(decodedByte)
                                .placeholder(R.drawable.add_image)
                                .transform(new CircleCrop())
                                .into(imgView);
                    imgView.setVisibility(View.VISIBLE);

                }else{
                    imgView.setVisibility(View.GONE);
                }
            }

        }
    }
}
