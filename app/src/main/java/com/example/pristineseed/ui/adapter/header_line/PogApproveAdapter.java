package com.example.pristineseed.ui.adapter.header_line;

import android.app.Activity;
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
import com.example.pristineseed.model.fs_indent_requirement.FsIndentRequirementsModel;
import com.example.pristineseed.ui.adapter.event_adapterr.EventViewListAdapter;

import java.util.List;

public class PogApproveAdapter extends RecyclerView.Adapter<PogApproveAdapter.MyViewHolder> {

    private List<POGModel> listdata;
    private Context activity;

    public OnListApproveRejectClickListner onListApproveRejectClickListner;
     public interface OnListApproveRejectClickListner {
         void onBtnCLick(String flag, int pos);
    }

    public PogApproveAdapter(Context activity, List<POGModel> listdata) {
        super();
        this.listdata = listdata;
        this.activity = activity;
    }
    @NonNull
    @Override
    public PogApproveAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pog_approve_list_layout, parent, false);
        MyViewHolder vh = new MyViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull PogApproveAdapter.MyViewHolder holder, int position) {

      holder.loc_name.setText("Emp.name : " +listdata.get(position).emp_name+"("+ listdata.get(position).pog_code + ")");
      holder.tv_session.setText(listdata.get(position).season);
      holder.crop_.setText(listdata.get(position).crop);
      holder.tv_hybrid.setText(listdata.get(position).hybrid);
      holder.tv_remarks.setText(listdata.get(position).remarks);
      holder.created_on.setText(listdata.get(position).created_on);
      holder.tv_pog_qty.setText(listdata.get(position).pog_qty);

        if (listdata.get(position).status.equalsIgnoreCase("Approve")) {
            holder.status_fs_return.setText(listdata.get(position).status);
            holder.status_fs_return.setTextColor(activity.getResources().getColor(R.color.colorPrimaryDark));
        } else if (listdata.get(position).status.equalsIgnoreCase("Reject")) {
           holder.status_fs_return.setText(listdata.get(position).status);
           holder.status_fs_return.setTextColor(activity.getResources().getColor(R.color.my_app_error_color));
        } else {
           holder.status_fs_return.setText(listdata.get(position).status);
           holder.status_fs_return.setTextColor(activity.getResources().getColor(R.color.pending_color));
        }

       holder.status_fs_return.setOnClickListener(v -> {
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
      private TextView loc_name;
      private TextView tv_session ;
      private TextView crop_;
      private TextView tv_hybrid;
      private TextView tv_remarks;
      private LinearLayout parent_layout;
      private TextView status_fs_return;

        private TextView created_on;
        private TextView tv_pog_qty;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

             tv_character_ofImageView = itemView.findViewById(R.id.tv_character_ofImageView);
             circuler_text_section = itemView.findViewById(R.id.circuler_text_section);
             loc_name = itemView.findViewById(R.id.loc_name);
             tv_session = itemView.findViewById(R.id.tv_session);
             crop_ = itemView.findViewById(R.id.crop_);
             tv_hybrid = itemView.findViewById(R.id.tv_hybrid);
             tv_remarks = itemView.findViewById(R.id.tv_remarks);
             parent_layout = itemView.findViewById(R.id.parent_layout);
             status_fs_return = itemView.findViewById(R.id.status_fs_return);
             created_on = itemView.findViewById(R.id.created_on);
             tv_pog_qty=itemView.findViewById(R.id.pog_qty);
        }
    }

    public void  setOnListClick( OnListApproveRejectClickListner onListApproveRejectClickListner){
         this.onListApproveRejectClickListner=onListApproveRejectClickListner;


    }
}
