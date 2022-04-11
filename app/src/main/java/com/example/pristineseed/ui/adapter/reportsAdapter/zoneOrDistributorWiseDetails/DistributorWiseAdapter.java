package com.example.pristineseed.ui.adapter.reportsAdapter.zoneOrDistributorWiseDetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pristineseed.R;
import com.example.pristineseed.model.reportModel.ZoneOrDistributorWiseDetailsModel;
import java.util.List;

public class DistributorWiseAdapter extends RecyclerView.Adapter<DistributorWiseAdapter.ViewHolder> {
    Context context;
    List<ZoneOrDistributorWiseDetailsModel> itemsList;
    public DistributorWiseAdapter.OnItemClickListner onItemClickListner;
    public interface OnItemClickListner {
        void onDistributorItemClick(int pos );
    }

    public DistributorWiseAdapter(@NonNull Context context, List<ZoneOrDistributorWiseDetailsModel> items) {
        super();
        this.itemsList = items;
        this.context = context;
    }
    @NonNull
    @Override
    public DistributorWiseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_view, parent, false);
        DistributorWiseAdapter.ViewHolder viewHolder = new DistributorWiseAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DistributorWiseAdapter.ViewHolder holder, int position) {
        ZoneOrDistributorWiseDetailsModel data = itemsList.get(position);
        holder.tv_name.setText(data.ditributor);
        holder.tv_name.setOnClickListener(v -> {
            onItemClickListner.onDistributorItemClick(position);
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

    public void setClickListner(DistributorWiseAdapter.OnItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }
}
