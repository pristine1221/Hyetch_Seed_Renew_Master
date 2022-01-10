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

public class ProdAndQAItemNameAdapter extends RecyclerView.Adapter<ProdAndQAItemNameAdapter.ViewHolder> {
    Context context;
    List<ProdAndQAInspectionStatusModel> itemsList;

    public ProdAndQAItemNameAdapter.OnItemClickListner onItemClickListner;

    public interface OnItemClickListner {
        void onItemClick(int pos );
    }

    public ProdAndQAItemNameAdapter(@NonNull Context context, List<ProdAndQAInspectionStatusModel> items) {
        super();
        this.itemsList = items;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_view, parent, false);
        return new ProdAndQAItemNameAdapter.ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProdAndQAInspectionStatusModel data = itemsList.get(position);
        holder.tv_name.setText(data.item_name);

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

    public void setOnItemClickListner(ProdAndQAItemNameAdapter.OnItemClickListner onItemClickListner){
        this.onItemClickListner = onItemClickListner;
    }
}
