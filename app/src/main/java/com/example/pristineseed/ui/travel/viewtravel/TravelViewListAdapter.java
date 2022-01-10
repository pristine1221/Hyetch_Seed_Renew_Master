package com.example.pristineseed.ui.travel.viewtravel;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pristineseed.DataBaseRepository.GeographicalRepo.DistricMasterDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.DistricMasterTable;
import com.example.pristineseed.DataBaseRepository.travel.TravelHeaderTable;
import com.example.pristineseed.R;
import com.example.pristineseed.RoomDataBase.PristineDatabase;
import com.example.pristineseed.global.DateTimeUtilsCustome;
import com.example.pristineseed.model.travel.ta_da_model.SyncTravelDetailModel;
import com.google.android.material.chip.Chip;

import java.util.List;

public class TravelViewListAdapter  extends   RecyclerView.Adapter<TravelViewListAdapter.ViewHolder>  {
        List<SyncTravelDetailModel> listdata;
        Activity activity;
        public interface OnItemClickListener {
        void onItemClick( int position);
}
    private final OnItemClickListener listener;
    public TravelViewListAdapter(Activity activity, List<SyncTravelDetailModel> listdata, OnItemClickListener listener) {
        super();
        this.listdata = listdata;
        this.activity = activity;
        this.listener=listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_travel_listview, parent, false);
            ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(listdata.get(position).STATUS.equalsIgnoreCase("PENDING")){
            holder.tv_app_budget_title.setVisibility(View.GONE);
            holder.view_ty.setVisibility(View.VISIBLE);
            holder.  tv_app_budget.setVisibility(View.GONE);
        }
        else{
            holder. tv_app_budget_title.setVisibility(View.VISIBLE);
            holder. tv_app_budget.setVisibility(View.VISIBLE);
            holder.  tv_app_budget.setText(listdata.get(position).approve_budget);
            holder.view_ty.setVisibility(View.GONE);
        }

        holder. approve_order.setVisibility(View.GONE);
        holder.imgView.setVisibility(View.GONE);

        holder. reject_order.setVisibility(View.GONE);

        holder. travel_code.setText(listdata.get(position).travelcode);

       holder.tv_from_loc.setText(listdata.get(position).from_loc);
       holder.tv_to_loc.setText(listdata.get(position).to_loc);
       holder.tv_start_date.setText(listdata.get(position).start_date);
       try {
           holder.tv_end_date.setText(DateTimeUtilsCustome.getDateWithSplitTime(listdata.get(position).start_date));
       }catch (Exception e){
           e.printStackTrace();
       }
       holder.tv_Created.setText(listdata.get(position).created_by);
       holder.tv_travel_reson.setText(listdata.get(position).travel_reson);
       holder.tv_expense_budget.setText(listdata.get(position).expense_budget);
       holder.tv_create_on.setText((DateTimeUtilsCustome.getDate_Time(listdata.get(position).created_on)));
       holder.tv_travel_status.setText(listdata.get(position).STATUS);
       holder.tv_travel_status.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);

       holder. item_container_layout.setOnClickListener(v -> {

           this.listener.onItemClick(position);
       });

       if(listdata.get(position).STATUS.equalsIgnoreCase("CREATE APPROVED")){
           holder.tv_travel_status.setTextColor(activity.getResources().getColor(R.color.colorPrimary));
       }else if(listdata.get(position).STATUS.equalsIgnoreCase("Create Rejected") ||listdata.get(position).STATUS.equalsIgnoreCase("Rejected")){
           holder.tv_travel_status.setTextColor(activity.getResources().getColor(R.color.my_app_error_color));
       }
       else if(listdata.get(position).STATUS.equalsIgnoreCase("Pending")){
           holder.tv_travel_status.setTextColor(activity.getResources().getColor(R.color.pending_color));
       }
       else if(listdata.get(position).STATUS.equalsIgnoreCase("Approved")){
           holder.tv_travel_status.setTextColor(activity.getResources().getColor(R.color.colorPrimaryDark));
       }
       else if(listdata.get(position).STATUS.equalsIgnoreCase("Insert Expense")){
           holder.tv_travel_status.setTextColor(activity.getResources().getColor(R.color.colorPrimaryDark));
       }
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
    TextView tv_Created;
    Chip approve_order;
    Chip reject_order;
    TextView tv_app_budget_title;
    TextView tv_app_budget;
    TextView tv_create_on;
    LinearLayout item_container_layout;
    ImageView   imgView;

    View view_ty;




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
        tv_app_budget_title=itemView.findViewById(R.id.tv_app_budget_title);
        tv_app_budget=itemView.findViewById(R.id.tv_app_budget);
        item_container_layout=itemView.findViewById(R.id.container_layout);
        approve_order=itemView.findViewById(R.id.approve_order);
        reject_order=itemView.findViewById(R.id.reject_order);
        tv_create_on = itemView.findViewById(R.id.tv_create_on);
        imgView=itemView.findViewById(R.id.imgView);
        view_ty=itemView.findViewById(R.id.view_ty);
    }

}
}
