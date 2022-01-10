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

public class ProdAndQAOrgNameAdapter extends RecyclerView.Adapter<ProdAndQAOrgNameAdapter.ViewHolder> {
    Context context;
    List<ProdAndQAInspectionStatusModel> itemsList;

    public ProdAndQAOrgNameAdapter.OnOrgClickListner onOrgClickListner;

    public interface OnOrgClickListner {
        void onOrgClick(int pos );
    }

    public ProdAndQAOrgNameAdapter(@NonNull Context context, List<ProdAndQAInspectionStatusModel> items) {
        super();
        this.itemsList = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_view, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProdAndQAInspectionStatusModel data = itemsList.get(position);
        holder.tv_name.setText(data.organizer_name);

        holder.tv_name.setOnClickListener(v -> {
            onOrgClickListner.onOrgClick(position);
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

    public void setOnOrgClickListner(ProdAndQAOrgNameAdapter.OnOrgClickListner onOrgClickListner){
        this.onOrgClickListner = onOrgClickListner;
    }

}
