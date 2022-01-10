package com.example.pristineseed.ui.adapter.item;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pristineseed.DataBaseRepository.GeographicalRepo.PlantingLineLotListTable;
import com.example.pristineseed.R;
import com.example.pristineseed.ui.adapter.reportsAdapter.lotsDueInspectionAdapters.ReportOrganiserNameAdapter;

import java.util.List;

public class PlantingProductionLotNumberAdapter extends RecyclerView.Adapter<PlantingProductionLotNumberAdapter.ViewHolder> {

    Context context;
    List<PlantingLineLotListTable> items, tempItems, suggestions;
    public PlantingProductionLotNumberAdapter.OnItemClickListner onItemClickListner;

    public interface OnItemClickListner {
        void onItemClick(int pos );
    }

    public PlantingProductionLotNumberAdapter(Context context, List<PlantingLineLotListTable> items){
        this.context = context;
        this.items = items;
    }
    @NonNull
    @Override
    public PlantingProductionLotNumberAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.android_item_view, parent, false);
        PlantingProductionLotNumberAdapter.ViewHolder viewHolder = new PlantingProductionLotNumberAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        PlantingLineLotListTable plantingLineLotListTable = items.get(position);
        holder.tv_name.setText(plantingLineLotListTable.getProduction_Lot_No());

        holder.tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListner.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.text_view);
        }
    }

    public void setClickListner(PlantingProductionLotNumberAdapter.OnItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }
}
