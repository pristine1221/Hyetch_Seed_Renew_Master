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

public class ReportOrganiserNameAdapter extends RecyclerView.Adapter<ReportOrganiserNameAdapter.ViewHolder>{
    Context context;
    List<LotsDueInspectionModel> itemsList;

    public ReportOrganiserNameAdapter.OnItemClickListner onItemClickListner;

    public interface OnItemClickListner {
        void onOrgClick(int pos );
    }

    public ReportOrganiserNameAdapter(@NonNull Context context, List<LotsDueInspectionModel> items) {
        super();
        this.itemsList = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ReportOrganiserNameAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_view, parent, false);
        ReportOrganiserNameAdapter.ViewHolder viewHolder = new ReportOrganiserNameAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LotsDueInspectionModel data = itemsList.get(position);
        holder.tv_name.setText(data.organizer_name);

        holder.tv_name.setOnClickListener(v -> {
            onItemClickListner.onOrgClick(position);
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

    public void setClickListner(ReportOrganiserNameAdapter.OnItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }

}
