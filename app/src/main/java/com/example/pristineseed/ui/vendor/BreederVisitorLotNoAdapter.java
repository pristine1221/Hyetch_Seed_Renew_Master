package com.example.pristineseed.ui.vendor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pristineseed.DataBaseRepository.GeographicalRepo.PlantingLineLotListTable;
import com.example.pristineseed.R;
import com.example.pristineseed.model.PlantingModel.PlantingLotModel;
import com.example.pristineseed.ui.adapter.item.PlantingProductionLotNumberAdapter;

import java.util.List;

public class BreederVisitorLotNoAdapter extends RecyclerView.Adapter<BreederVisitorLotNoAdapter.ViewHolder> {

    Context context;
    List<PlantingLotModel.Data> items, tempItems, suggestions;
    public BreederVisitorLotNoAdapter.OnItemClickListner onItemClickListner;

    public interface OnItemClickListner {
        void onItemClick(int pos );
    }

    public BreederVisitorLotNoAdapter(Context context, List<PlantingLotModel.Data> items){
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.android_item_view, parent, false);
        BreederVisitorLotNoAdapter.ViewHolder viewHolder = new BreederVisitorLotNoAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BreederVisitorLotNoAdapter.ViewHolder holder, int position) {
        PlantingLotModel.Data data = items.get(position);
        holder.tv_name.setText(data.Lot_No);

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
    public void setClickListner(BreederVisitorLotNoAdapter.OnItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }

}
