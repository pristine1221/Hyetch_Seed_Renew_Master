package com.example.pristineseed.ui.adapter.reportsAdapter.lotsDueInspectionAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pristineseed.R;
import com.example.pristineseed.model.reportModel.LotsDueInspectionModel;

import java.util.ArrayList;
import java.util.List;

public class LotsDueInspectionAdapter extends RecyclerView.Adapter<LotsDueInspectionAdapter.ViewHolder> {

    public Context context;
    public List<LotsDueInspectionModel> lotsDueInspectionModelList;

    LayoutInflater inflater;
    private List<LotsDueInspectionModel> getItemsSearchList = new ArrayList<>();

    public LotsDueInspectionAdapter(Context context, List<LotsDueInspectionModel> lotsDueInspectionModelList){
        this.context= context;
        this.lotsDueInspectionModelList = lotsDueInspectionModelList;

        inflater = LayoutInflater.from(context);
        this.getItemsSearchList.addAll(lotsDueInspectionModelList);

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lots_due_inspection_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LotsDueInspectionModel inspectionModel =lotsDueInspectionModelList.get(position);
        holder.tv_item_no.setText(inspectionModel.item_name);
        holder.tv_organiser.setText(inspectionModel.organizer_name);
        holder.tv_season.setText(inspectionModel.season_name);
        holder.tv_village.setText(inspectionModel.organizer_code);
        holder.tv_inspection.setText(inspectionModel.Inspection);
        holder.tv_prod_lot_no.setText(inspectionModel.production_lot_no);

        //todo getting first letter of name to display in list....
        if(lotsDueInspectionModelList.get(position).organizer_name != null &&
                !lotsDueInspectionModelList.get(position).organizer_name.equalsIgnoreCase("")){
            String firstLetter = String.valueOf(inspectionModel.organizer_name.charAt(0));
            holder.tv_character_ofImageView2.setText(firstLetter);
        }
    }

    @Override
    public int getItemCount() {
        return lotsDueInspectionModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_item_no, tv_organiser, tv_season, tv_village,tv_inspection,tv_character_ofImageView2, tv_prod_lot_no;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_item_no = itemView.findViewById(R.id.tv_item_no);
            tv_organiser = itemView.findViewById(R.id.tv_organiser);
            tv_season = itemView.findViewById(R.id.tv_season);
            tv_village = itemView.findViewById(R.id.tv_village);
            tv_inspection = itemView.findViewById(R.id.tv_inspection);
            tv_character_ofImageView2 = itemView.findViewById(R.id.tv_character_ofImageView2);
            tv_prod_lot_no = itemView.findViewById(R.id.tv_prod_lot_no);
        }
    }

  /*  public void filter(String newText) {
        newText = newText.toLowerCase(Locale.getDefault());
        lotsDueInspectionModelList.clear();
        if (newText == null || newText.length() == 0) {
            lotsDueInspectionModelList.addAll(getItemsSearchList);
        } else {

            for (LotsDueInspectionModel itemListModel : getItemsSearchList) {
                if (itemListModel.organizer_name.toLowerCase(Locale.getDefault()).contains(newText) ||
                        itemListModel.item_name.toLowerCase(Locale.getDefault()).contains(newText)) {
                    lotsDueInspectionModelList.add(itemListModel);
                }
            }
        }
        notifyDataSetChanged();
    }*/
}
