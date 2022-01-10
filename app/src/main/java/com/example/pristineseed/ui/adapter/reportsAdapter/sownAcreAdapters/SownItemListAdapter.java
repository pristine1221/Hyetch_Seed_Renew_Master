package com.example.pristineseed.ui.adapter.reportsAdapter.sownAcreAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pristineseed.R;
import com.example.pristineseed.model.reportModel.SownAcreViewModel;

import java.util.List;

public class SownItemListAdapter extends RecyclerView.Adapter<SownItemListAdapter.ViewHolder> {

    Context context;
    List<SownAcreViewModel> acreViewModelList;

    public SownItemListAdapter.OnItemClick onItemClickListner;

    public interface OnItemClick{
        void onItemClick(int pos);
    }

    public SownItemListAdapter(Context context, List<SownAcreViewModel> acreViewModelList){
        this.context = context;
        this.acreViewModelList = acreViewModelList;
    }
    @NonNull
    @Override
    public SownItemListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_view, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull SownItemListAdapter.ViewHolder holder, int position) {
        SownAcreViewModel data = acreViewModelList.get(position);
        holder.tv_name.setText(data.item_name);

        holder.tv_name.setOnClickListener(v -> {
            onItemClickListner.onItemClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return acreViewModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.text_view);
        }
    }

    public void setItemClickLisner(SownItemListAdapter.OnItemClick onItemClick){
        this.onItemClickListner = onItemClick;
    }
}
