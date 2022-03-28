package com.example.pristineseed.ui.adapter.reportsAdapter.zoneOrDistributorWiseDetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pristineseed.R;
import com.example.pristineseed.model.reportModel.ZoneOrDistributorWiseDetailsModel;
import java.util.List;

public class ZoneOrDistributorDetailsListAdapter extends RecyclerView.Adapter<ZoneOrDistributorDetailsListAdapter.ViewHolder> {
    public Context context;
    public List<ZoneOrDistributorWiseDetailsModel> modelList;
    private String flag = "";

    public ZoneOrDistributorDetailsListAdapter(Context context, List<ZoneOrDistributorWiseDetailsModel> modelList, String flag){
        this.context= context;
        this.modelList = modelList;
        this.flag = flag;
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
        holder.tv_zone.setText(detailsModel.zone_name);
        holder.tv_crop_name.setText(detailsModel.crop_name);
        holder.tv_booking_qty.setText(detailsModel.booking_qty);
        holder.status_title.setVisibility(View.VISIBLE);
        holder.tv_status.setVisibility(View.VISIBLE);
        holder.tv_status.setText(detailsModel.status);

        if (flag.equals("mkt_indent_view")){
            holder.tv_distributor_name.setText(detailsModel.ditributor);
            holder.bookind_ind_title.setVisibility(View.VISIBLE);
            holder.tv_booking_indent_no.setVisibility(View.VISIBLE);
            holder.tv_booking_indent_no.setText(detailsModel.booking_indent_no);
            holder.marketing_ind_title.setVisibility(View.VISIBLE);
            holder.tv_marketing_indent_no.setVisibility(View.VISIBLE);
            holder.tv_marketing_indent_no.setText(detailsModel.marketing_indent_no);

            //todo hide supply details some field views..
            holder.supply_allotted_qty_title.setVisibility(View.GONE);
            holder.tv_supply_allotted_qty.setVisibility(View.GONE);
            holder.supply_supplies_qty_title.setVisibility(View.GONE);
            holder.tv_supply_supplies_qty.setVisibility(View.GONE);
        }else if(flag.equals("supply_details_view")){
            holder.tv_distributor_name.setText(detailsModel.ditributor);
            holder.supply_allotted_qty_title.setVisibility(View.VISIBLE);
            holder.tv_supply_allotted_qty.setVisibility(View.VISIBLE);
            holder.tv_supply_allotted_qty.setText(detailsModel.alotted_qty);
            holder.supply_supplies_qty_title.setVisibility(View.VISIBLE);
            holder.tv_supply_supplies_qty.setVisibility(View.VISIBLE);
            holder.tv_supply_supplies_qty.setText(detailsModel.supplies_qty);

            //todo hide mkt indent some field views...
            holder.bookind_ind_title.setVisibility(View.GONE);
            holder.tv_booking_indent_no.setVisibility(View.GONE);
            holder.marketing_ind_title.setVisibility(View.GONE);
            holder.tv_marketing_indent_no.setVisibility(View.GONE);
            holder.status_title.setVisibility(View.GONE);
            holder.tv_status.setVisibility(View.GONE);

        }else if (flag.equals("order_details_view")){
            holder.season_title.setVisibility(View.GONE);
            holder.tv_season.setVisibility(View.GONE);
            holder.booking_qty_title.setVisibility(View.GONE);
            holder.tv_booking_qty.setVisibility(View.GONE);
            holder.bookind_ind_title.setVisibility(View.GONE);
            holder.tv_booking_indent_no.setVisibility(View.GONE);
            holder.mkt_ind_nd_crop_layout.setVisibility(View.GONE);

            holder.order_description_title.setVisibility(View.VISIBLE);
            holder.tv_order_description.setVisibility(View.VISIBLE);
            holder.tv_order_description.setText(detailsModel.Description);
            holder.tv_distributor_name.setText(detailsModel.distributor_name);
            holder.order_booking_no_title.setVisibility(View.VISIBLE);
            holder.tv_order_booking_no.setVisibility(View.VISIBLE);
            holder.tv_order_booking_no.setText(detailsModel.booking_no);
            holder.order_field_created_layout.setVisibility(View.VISIBLE);
            holder.tv_order_created_on.setText(detailsModel.created_on);
            holder.order_crop_name_title.setVisibility(View.VISIBLE);
            holder.tv_order_crop_name.setVisibility(View.VISIBLE);
            holder.tv_order_crop_name.setText(detailsModel.crop_name);
        }

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
        TextView tv_season, tv_distributor_name,tv_zone, tv_crop_name, tv_booking_indent_no, tv_booking_qty,tv_marketing_indent_no,tv_status,
                tv_character_ofImageView4, bookind_ind_title,marketing_ind_title, supply_allotted_qty_title,tv_supply_allotted_qty, supply_supplies_qty_title,
                status_title,season_title, booking_qty_title, tv_supply_supplies_qty, order_description_title, tv_order_description,
                order_booking_no_title, tv_order_booking_no, order_created_on_title,tv_order_created_on,order_crop_name_title,tv_order_crop_name;
        LinearLayout mkt_ind_nd_crop_layout, order_field_created_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_season = itemView.findViewById(R.id.tv_season);
            tv_distributor_name = itemView.findViewById(R.id.tv_distributor_name);
            tv_zone = itemView.findViewById(R.id.tv_zone);
            tv_crop_name = itemView.findViewById(R.id.tv_crop_name);
            tv_booking_indent_no = itemView.findViewById(R.id.tv_booking_indent_no);
            booking_qty_title = itemView.findViewById(R.id.booking_qty_title);
            tv_booking_qty = itemView.findViewById(R.id.tv_booking_qty);
            tv_marketing_indent_no = itemView.findViewById(R.id.tv_marketing_indent_no);
            tv_status = itemView.findViewById(R.id.tv_status);
            tv_character_ofImageView4 = itemView.findViewById(R.id.tv_character_ofImageView4);
            bookind_ind_title = itemView.findViewById(R.id.bookind_ind_title);
            marketing_ind_title = itemView.findViewById(R.id.marketing_ind_title);
            supply_allotted_qty_title = itemView.findViewById(R.id.supply_allotted_qty_title);
            tv_supply_allotted_qty = itemView.findViewById(R.id.tv_supply_allotted_qty);
            supply_supplies_qty_title = itemView.findViewById(R.id.supply_supplies_qty_title);
            tv_supply_supplies_qty = itemView.findViewById(R.id.tv_supply_supplies_qty);
            status_title = itemView.findViewById(R.id.status_title);
            season_title = itemView.findViewById(R.id.season_title);
            order_description_title = itemView.findViewById(R.id.order_description_title);
            tv_order_description = itemView.findViewById(R.id.tv_order_description);
            order_booking_no_title = itemView.findViewById(R.id.order_booking_no_title);
            tv_order_booking_no = itemView.findViewById(R.id.tv_order_booking_no);
            mkt_ind_nd_crop_layout = itemView.findViewById(R.id.mkt_ind_nd_crop_layout);
            order_field_created_layout = itemView.findViewById(R.id.order_field_created_layout);
            order_created_on_title = itemView.findViewById(R.id.order_created_on_title);
            tv_order_created_on = itemView.findViewById(R.id.tv_order_created_on);
            order_crop_name_title = itemView.findViewById(R.id.order_crop_name_title);
            tv_order_crop_name = itemView.findViewById(R.id.tv_order_crop_name);
        }
    }
}

