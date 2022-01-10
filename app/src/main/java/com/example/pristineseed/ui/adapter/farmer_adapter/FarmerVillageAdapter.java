package com.example.pristineseed.ui.adapter.farmer_adapter;

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

import com.example.pristineseed.R;
import com.example.pristineseed.model.GeoSetupModel.DispatchFarmerModel;

import java.util.ArrayList;
import java.util.List;

public class FarmerVillageAdapter extends ArrayAdapter<DispatchFarmerModel.Data> {
    Context context;
    int resourceId;
    List<DispatchFarmerModel.Data> items, tempItems, suggestions;

    public FarmerVillageAdapter(@NonNull Context context, int resourceId, List<DispatchFarmerModel.Data> items) {
        super(context, resourceId, items);
        this.items = items;
        this.context = context;
        this.resourceId = resourceId;
        tempItems = new ArrayList<DispatchFarmerModel.Data>(items);
        suggestions = new ArrayList<DispatchFarmerModel.Data>();

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(resourceId, parent, false);
        }
        DispatchFarmerModel.Data data = getItem(position);
        TextView name = (TextView) view.findViewById(R.id.text_view);
        name.setText(data.Village + "(" + data.Name + ")");
        return view;
    }

    @Nullable
    @Override
    public DispatchFarmerModel.Data getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
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
    };
}