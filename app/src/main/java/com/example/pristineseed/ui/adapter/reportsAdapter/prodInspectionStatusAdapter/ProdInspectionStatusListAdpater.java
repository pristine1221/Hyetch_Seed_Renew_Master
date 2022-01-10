package com.example.pristineseed.ui.adapter.reportsAdapter.prodInspectionStatusAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pristineseed.R;
import com.example.pristineseed.model.reportModel.LotsDueInspectionModel;
import com.example.pristineseed.model.reportModel.ProdInspectionStatusModel;
import com.example.pristineseed.ui.adapter.reportsAdapter.lotsDueInspectionAdapters.LotsDueInspectionAdapter;

import java.util.ArrayList;
import java.util.List;

public class ProdInspectionStatusListAdpater extends RecyclerView.Adapter<ProdInspectionStatusListAdpater.ViewHolder> {

    public Context context;
    public List<ProdInspectionStatusModel> inspectionStatusModelList;

    public ProdInspectionStatusListAdpater(Context context, List<ProdInspectionStatusModel> inspectionStatusModelList){
        this.context= context;
        this.inspectionStatusModelList = inspectionStatusModelList;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.prod_inspection_status_layout_adapter, parent, false);
        return new ProdInspectionStatusListAdpater.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProdInspectionStatusModel inspectionStatusModel = inspectionStatusModelList.get(position);
        holder.tv_item_name.setText(inspectionStatusModel.item_name);
        holder.tv_crop.setText(inspectionStatusModel.crop);
        holder.tv_organiser.setText(inspectionStatusModel.organizer_name);
        holder.tv_season.setText(inspectionStatusModel.season_name);
        holder.tv_sowing_area_In_acres.setText(inspectionStatusModel.sowing_area_In_acres);
        holder.tv_production_lot_no.setText(inspectionStatusModel.production_lot_no);
        holder.tv_zone.setText(inspectionStatusModel.zone);
    }

    @Override
    public int getItemCount() {
        return inspectionStatusModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_item_name, tv_crop, tv_organiser, tv_season, tv_sowing_area_In_acres, tv_production_lot_no, tv_zone;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_item_name = itemView.findViewById(R.id.tv_item_name);
            tv_crop = itemView.findViewById(R.id.tv_crop);
            tv_organiser = itemView.findViewById(R.id.tv_organiser);
            tv_season = itemView.findViewById(R.id.tv_season);
            tv_sowing_area_In_acres = itemView.findViewById(R.id.tv_sowing_area_In_acres);
            tv_production_lot_no = itemView.findViewById(R.id.tv_production_lot_no);
            tv_zone = itemView.findViewById(R.id.tv_zone);
        }
    }
}
