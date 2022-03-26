package com.example.pristineseed.ui.adapter.reportsAdapter.zoneOrDistributorWiseDetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pristineseed.R;
import com.example.pristineseed.model.reportModel.ZoneOrDistributorWiseDetailsModel;
import java.util.List;

public class ZoneOrDistributorDetailsListAdapter extends RecyclerView.Adapter<ZoneOrDistributorDetailsListAdapter.ViewHolder> {
    public Context context;
    public List<ZoneOrDistributorWiseDetailsModel> modelList;

    public ZoneOrDistributorDetailsListAdapter(Context context, List<ZoneOrDistributorWiseDetailsModel> modelList){
        this.context= context;
        this.modelList = modelList;
    }
    @NonNull
    @Override
    public ZoneOrDistributorDetailsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.zone_or_distributor_details_adapter_layout, parent, false);
        return new ZoneOrDistributorDetailsListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ZoneOrDistributorDetailsListAdapter.ViewHolder holder, int position) {
        ZoneOrDistributorWiseDetailsModel detailsModel = modelList.get(position);
        holder.tv_season.setText(detailsModel.season);
        holder.tv_distributor_name.setText(detailsModel.ditributor);
        holder.tv_zone.setText(detailsModel.zone_name);
        holder.tv_crop_name.setText(detailsModel.crop_name);
        holder.tv_booing_indent_no.setText(detailsModel.booking_indent_no);
        holder.tv_booking_qty.setText(detailsModel.booking_qty);
        holder.tv_marketing_indent_no.setText(detailsModel.marketing_indent_no);
        holder.tv_status.setText(detailsModel.status);

        //todo getting first letter of name to display in list....
        if(modelList.get(position).ditributor != null &&
                !modelList.get(position).ditributor.equalsIgnoreCase("")){
            String firstLetter = String.valueOf(detailsModel.ditributor.charAt(0));
            holder.tv_character_ofImageView4.setText(firstLetter);
        }
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_season, tv_distributor_name,tv_zone, tv_crop_name, tv_booing_indent_no, tv_booking_qty,tv_marketing_indent_no,tv_status, tv_character_ofImageView4;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_season = itemView.findViewById(R.id.tv_season);
            tv_distributor_name = itemView.findViewById(R.id.tv_distributor_name);
            tv_zone = itemView.findViewById(R.id.tv_zone);
            tv_crop_name = itemView.findViewById(R.id.tv_crop_name);
            tv_booing_indent_no = itemView.findViewById(R.id.tv_booing_indent_no);
            tv_booking_qty = itemView.findViewById(R.id.tv_booking_qty);
            tv_marketing_indent_no = itemView.findViewById(R.id.tv_marketing_indent_no);
            tv_status = itemView.findViewById(R.id.tv_status);
            tv_character_ofImageView4 = itemView.findViewById(R.id.tv_character_ofImageView4);
        }
    }
}

