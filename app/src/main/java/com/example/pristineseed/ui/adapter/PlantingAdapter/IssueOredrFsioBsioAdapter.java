package com.example.pristineseed.ui.adapter.PlantingAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pristineseed.R;
import com.example.pristineseed.model.PlantingModel.PlantingFsio_bsio_model;
import com.example.pristineseed.ui.adapter.item.OrganizerAdapter;

import java.util.List;
public class IssueOredrFsioBsioAdapter extends RecyclerView.Adapter<IssueOredrFsioBsioAdapter.ViewHolder> {
    Context context;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    List<PlantingFsio_bsio_model> items;
    private  OnItemClickListener listener;

    public IssueOredrFsioBsioAdapter(@NonNull Context context, List<PlantingFsio_bsio_model> items,OnItemClickListener listener) {
        super();
        this.items = items;
        this.context = context;
        this.listener=listener;

    }
   /* @NonNull
    @Override
    public Filter getFilter() {
        return customerFilter;
    }

    private Filter customerFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            DispatchFarmerModel.Data data = (DispatchFarmerModel.Data) resultValue;
            return data.No;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (DispatchFarmerModel.Data item : tempItems) {
                    if (item.Village.toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(item);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<DispatchFarmerModel.Data> filterList = (ArrayList<DispatchFarmerModel.Data>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (DispatchFarmerModel.Data people : filterList) {
                    if (people != null) {
                        add(people);
                        notifyDataSetChanged();
                    }
                }
            }
        }
    };*/

    @NonNull
    @Override
    public IssueOredrFsioBsioAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IssueOredrFsioBsioAdapter.ViewHolder holder, int position) {
        PlantingFsio_bsio_model data= items.get(position);

        holder.tv_name.setText(data.No);
        holder.tv_name.setOnClickListener(v->{
            listener.onItemClick(position);
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
            tv_name = itemView.findViewById(R.id.text_view);
        }
    }

    public void setOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.listener = mItemClickListener;
    }
}
