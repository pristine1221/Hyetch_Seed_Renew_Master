package com.example.pristineseed.ui.adapter.reportsAdapter.prodInspectionStatusAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pristineseed.R;
import com.example.pristineseed.model.reportModel.ProdAndQAInspectionStatusModel;

import java.util.List;

public class ProdAndQAInspectionStatusListAdpater extends RecyclerView.Adapter<ProdAndQAInspectionStatusListAdpater.ViewHolder> {

    public Context context;
    public List<ProdAndQAInspectionStatusModel> inspectionStatusModelList;
    String flag = "";

    public ProdAndQAInspectionStatusListAdpater(Context context, List<ProdAndQAInspectionStatusModel> inspectionStatusModelList, String flag){
        this.context= context;
        this.inspectionStatusModelList = inspectionStatusModelList;
        this.flag = flag;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.prod_inspection_status_layout_adapter, parent, false);
        return new ProdAndQAInspectionStatusListAdpater.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProdAndQAInspectionStatusModel inspectionStatusModel = inspectionStatusModelList.get(position);
        holder.tv_item_name.setText(inspectionStatusModel.item_name);
        holder.tv_crop.setText(inspectionStatusModel.crop);
        holder.tv_organiser.setText(inspectionStatusModel.organizer_name);
        holder.tv_season.setText(inspectionStatusModel.season_name);
        holder.tv_sowing_area_In_acres.setText(inspectionStatusModel.sowing_area_In_acres);
        holder.tv_production_lot_no.setText(inspectionStatusModel.production_lot_no);
        holder.tv_zone.setText(inspectionStatusModel.zone);

        //todo getting first letter of name to display in list....
        if(inspectionStatusModelList.get(position).organizer_name != null &&
                !inspectionStatusModelList.get(position).organizer_name.equalsIgnoreCase("")){
            String firstLetter = String.valueOf(inspectionStatusModel.organizer_name.charAt(0));
            holder.tv_character_ofImageView5.setText(firstLetter);
        }

        if(flag.equals("prod_status_view")){
            holder.prod_status.setVisibility(View.VISIBLE);
            holder.qa_status.setVisibility(View.GONE);
            holder.tv_flag_status.setVisibility(View.GONE);
            holder.text_flag_status.setVisibility(View.GONE);
            switch (inspectionStatusModel.QC_inspection){
                case "DONE":
                    holder.prod_status.setText(inspectionStatusModel.QC_inspection);
                    holder.prod_status.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
                    break;

                case "PENDING":
                    holder.prod_status.setText(inspectionStatusModel.QC_inspection);
                    holder.prod_status.setTextColor(context.getResources().getColor(R.color.pending_color));
                    break;
            }
        }
        else if(flag.equals("qa_status_view")){
            holder.prod_status.setVisibility(View.GONE);
            holder.qa_status.setVisibility(View.VISIBLE);
            holder.tv_flag_status.setVisibility(View.GONE);
            holder.text_flag_status.setVisibility(View.GONE);
            switch (inspectionStatusModel.is_qa_done){
                case "Done":
                    holder.qa_status.setText(inspectionStatusModel.is_qa_done);
                    holder.qa_status.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
                    break;

                case"Pending":
                    holder.qa_status.setText(inspectionStatusModel.is_qa_done);
                    holder.qa_status.setTextColor(context.getResources().getColor(R.color.pending_color));
                    break;
            }
        }
        else if(flag.equals("flag_status")){
            holder.tv_flag_status.setVisibility(View.VISIBLE);
            holder.text_flag_status.setVisibility(View.VISIBLE);
            holder.prod_status.setVisibility(View.GONE);
            holder.qa_status.setVisibility(View.GONE);
            holder.tv_flag_status.setText(inspectionStatusModel.flag_status);
        }

    }

    @Override
    public int getItemCount() {
        return inspectionStatusModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_item_name, tv_crop, tv_organiser, tv_season, tv_sowing_area_In_acres, tv_production_lot_no, tv_zone, tv_flag_status,text_flag_status , tv_character_ofImageView5,prod_status, qa_status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_item_name = itemView.findViewById(R.id.tv_item_name);
            tv_crop = itemView.findViewById(R.id.tv_crop);
            tv_organiser = itemView.findViewById(R.id.tv_organiser);
            tv_season = itemView.findViewById(R.id.tv_season);
            tv_sowing_area_In_acres = itemView.findViewById(R.id.tv_sowing_area_In_acres);
            tv_production_lot_no = itemView.findViewById(R.id.tv_production_lot_no);
            tv_zone = itemView.findViewById(R.id.tv_zone);
            tv_character_ofImageView5 = itemView.findViewById(R.id.tv_character_ofImageView5);
            prod_status = itemView.findViewById(R.id.prod_status);
            qa_status = itemView.findViewById(R.id.qa_status);
            tv_flag_status = itemView.findViewById(R.id.tv_flag_status);
            text_flag_status = itemView.findViewById(R.id.text_flag_status);
        }
    }
}
