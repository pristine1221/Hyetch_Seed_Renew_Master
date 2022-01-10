package com.example.pristineseed.ui.adapter.reportsAdapter.sownAcreAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pristineseed.R;
import com.example.pristineseed.model.reportModel.PLDandSownAcreViewModel;

import java.util.List;

public class SownVillageListAdapter extends RecyclerView.Adapter<SownVillageListAdapter.ViewHolder> {

    Context context;
    List<PLDandSownAcreViewModel> acreViewModelList;

    public SownVillageListAdapter.OnVillageClickListner onItemClickListner;

    public interface OnVillageClickListner{
        void onVillageItemClick(int pos);
    }

    public SownVillageListAdapter(Context context, List<PLDandSownAcreViewModel> acreViewModelList){
        this.context = context;
        this.acreViewModelList = acreViewModelList;
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
        PLDandSownAcreViewModel data = acreViewModelList.get(position);
        holder.tv_name.setText(data.village_name);

        holder.tv_name.setOnClickListener(v -> {
            onItemClickListner.onVillageItemClick(position);
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

    public void setOnVillageClickLisner(SownVillageListAdapter.OnVillageClickListner onItemClickListner){
        this.onItemClickListner = onItemClickListner;
    }
}
