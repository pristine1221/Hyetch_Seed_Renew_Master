package com.example.pristineseed.ui.adapter.farmer_adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pristineseed.R;
import com.example.pristineseed.model.GeoSetupModel.DispatchFarmerModel;
import com.example.pristineseed.model.item.OrganizerModel;
import com.example.pristineseed.ui.adapter.item.OrganizerAdapter;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;

public class FarmerVillageAdapter extends RecyclerView.Adapter<FarmerVillageAdapter.ViewHolder> {
    Context context;
    List<DispatchFarmerModel.Data> items;
    private static ClickListener mclickListener;
    public interface ClickListener {
        void onItemClick(int position);
    }

    public FarmerVillageAdapter(@NonNull Context context, List<DispatchFarmerModel.Data> items) {
        super();
        this.items = items;
        this.context = context;
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
    public FarmerVillageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FarmerVillageAdapter.ViewHolder holder, int position) {
        DispatchFarmerModel.Data data= items.get(position);
        holder.tv_name.setText(data.Village + "(" + data.Name + ")");
        holder.tv_name.setOnClickListener(v->{
            mclickListener.onItemClick(position);
        });
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.text_view);
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        FarmerVillageAdapter.mclickListener = clickListener;
    }

}