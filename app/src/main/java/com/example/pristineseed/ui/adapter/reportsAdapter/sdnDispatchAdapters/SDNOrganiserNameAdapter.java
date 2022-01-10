package com.example.pristineseed.ui.adapter.reportsAdapter.sdnDispatchAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pristineseed.R;
import com.example.pristineseed.model.reportModel.ReportSeedDispatchViewModel;

import java.util.List;

public class SDNOrganiserNameAdapter extends RecyclerView.Adapter<SDNOrganiserNameAdapter.ViewHolder> {
    Context context;
    List<ReportSeedDispatchViewModel> itemsList;

    public SDNOrganiserNameAdapter.OnItemClickListner onItemClickListner;

    public interface OnItemClickListner {
        void onItemClick(int pos );
    }

    public SDNOrganiserNameAdapter(@NonNull Context context, List<ReportSeedDispatchViewModel> items) {
        super();
        this.itemsList = items;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_view, parent, false);
        SDNOrganiserNameAdapter.ViewHolder viewHolder = new SDNOrganiserNameAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReportSeedDispatchViewModel data = itemsList.get(position);
        holder.tv_name.setText(data.organizer_name);

        holder.tv_name.setOnClickListener(v -> {
            onItemClickListner.onItemClick(position);
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

    public void setClickListner(SDNOrganiserNameAdapter.OnItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }
}
