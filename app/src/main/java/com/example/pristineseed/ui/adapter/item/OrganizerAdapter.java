package com.example.pristineseed.ui.adapter.item;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pristineseed.DataBaseRepository.GeographicalRepo.LocationMasterTable;
import com.example.pristineseed.DataBaseRepository.seed_dispatch_note.Organizer_master_Table;
import com.example.pristineseed.R;
import com.example.pristineseed.model.item.OrganizerModel;

import java.util.ArrayList;
import java.util.List;

public class OrganizerAdapter extends RecyclerView.Adapter<OrganizerAdapter.ViewHolder> {
    Context context;
    int resourceId;
    List<OrganizerModel.Data> items, tempItems, suggestions;

    public OnItemClickListner onItemClickListner;

    public interface OnItemClickListner {
        void onItemClick(int pos);
    }

    public OrganizerAdapter(@NonNull Context context, List<OrganizerModel.Data> items) {
        super();
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public OrganizerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrganizerAdapter.ViewHolder holder, int position) {
        OrganizerModel.Data data = items.get(position);
        holder.tv_name.setText(data.No + "(" + data.Name + ")");

        holder.tv_name.setOnClickListener(v -> {
            onItemClickListner.onItemClick(position);
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

    public void setClickListner(OnItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }

}