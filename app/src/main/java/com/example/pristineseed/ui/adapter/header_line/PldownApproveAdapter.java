package com.example.pristineseed.ui.adapter.header_line;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pristineseed.R;
import com.example.pristineseed.global.StaticMethods;
import com.example.pristineseed.model.POG.POGModel;
import com.example.pristineseed.model.Plough_down_List.PloghDownListModel;

import java.util.List;

public class PldownApproveAdapter extends RecyclerView.Adapter<PldownApproveAdapter.MyViewHolder> {
    private List<PloghDownListModel> listdata;
    private Context activity;
    public OnListApproveRejectClickListner onListApproveRejectClickListner;

    public interface OnListApproveRejectClickListner {
        void onBtnCLick(String flag, int pos);
    }

    public PldownApproveAdapter(Context activity, List<PloghDownListModel> listdata) {
        super();
        this.listdata = listdata;
        this.activity = activity;
    }

    @NonNull
    @Override
    public PldownApproveAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pldown_approve_list_layout, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull PldownApproveAdapter.MyViewHolder holder, int position) {
        holder.tv_lot_no.setText(listdata.get(position).lot_no+", "+listdata.get(position).pld_code);

        holder.tv_reason.setText(listdata.get(position).reason);
        holder.tv_date_time.setText(listdata.get(position).date);

        if (listdata.get(position).status.equalsIgnoreCase("Approve")) {
            holder.tv_status.setText(listdata.get(position).status);
            holder.tv_status.setTextColor(activity.getResources().getColor(R.color.colorPrimaryDark));
        } else if (listdata.get(position).status.equalsIgnoreCase("Reject")) {
            holder.tv_status.setText(listdata.get(position).status);
            holder.tv_status.setTextColor(activity.getResources().getColor(R.color.my_app_error_color));
        } else {
            holder.tv_status.setText(listdata.get(position).status);
            holder.tv_status.setTextColor(activity.getResources().getColor(R.color.pending_color));
        }

        holder.tv_status.setOnClickListener(v -> {
            onListApproveRejectClickListner.onBtnCLick("ApproveRejected", position);
        });

        Drawable unwrappedDrawable = AppCompatResources.getDrawable(activity, R.drawable.circle_yellow_icon);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, StaticMethods.getRandomColor());
        holder.circuler_text_section.setBackground(unwrappedDrawable);

        if (listdata.get(position).created_by != null && !listdata.get(position).created_by.equalsIgnoreCase("")) {
            holder.tv_character_ofImageView.setText(String.valueOf(listdata.get(position).created_by.charAt(0)));
        }
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
     private TextView tv_character_ofImageView;
     private FrameLayout circuler_text_section;
     private TextView tv_lot_no;
     private TextView tv_status;
     private TextView tv_reason;
     private TextView tv_date_time;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
             tv_character_ofImageView = itemView.findViewById(R.id.tv_character_ofImageView);
             circuler_text_section=itemView.findViewById(R.id.circuler_text_section);
             tv_lot_no = itemView.findViewById(R.id.tv_lot_no);
             tv_status = itemView.findViewById(R.id.tv_status);
             tv_reason = itemView.findViewById(R.id.tv_reason);
             tv_date_time=itemView.findViewById(R.id.tv_date_time);
        }
    }

    public void setOnListClick(OnListApproveRejectClickListner onListApproveRejectClickListner) {
        this.onListApproveRejectClickListner = onListApproveRejectClickListner;

    }

}