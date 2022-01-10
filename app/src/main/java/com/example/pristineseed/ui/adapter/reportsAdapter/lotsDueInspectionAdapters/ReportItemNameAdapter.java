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

import java.util.List;

public class ReportItemNameAdapter extends RecyclerView.Adapter<ReportItemNameAdapter.ViewHolder> {

    Context context;
    int resourceId;
    List<LotsDueInspectionModel> itemsList, tempItems, suggestions;

    public ReportItemNameAdapter.OnItemClickListner onItemClickListner;

    public interface OnItemClickListner {
        void onItemClickList(int pos );
    }

    public ReportItemNameAdapter(@NonNull Context context, List<LotsDueInspectionModel> items) {
        super();
        this.itemsList = items;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_view, parent, false);
        ReportItemNameAdapter.ViewHolder viewHolder = new ReportItemNameAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LotsDueInspectionModel data = itemsList.get(position);
        holder.tv_name.setText(data.item_name);

        holder.tv_name.setOnClickListener(v -> {
            onItemClickListner.onItemClickList(position);
        });
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.text_view);
        }
    }

    public void setClickListner(ReportItemNameAdapter.OnItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }
}
