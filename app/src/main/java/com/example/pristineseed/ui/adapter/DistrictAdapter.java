package com.example.pristineseed.ui.adapter;

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

import com.example.pristineseed.DataBaseRepository.GeographicalRepo.DistricMasterTable;
import com.example.pristineseed.R;
import com.example.pristineseed.model.GeoSetupModel.DispatchFarmerModel;

import java.util.ArrayList;
import java.util.List;

public class DistrictAdapter extends ArrayAdapter<DistricMasterTable> {

    Context context;
    int resourceId;
    List<DistricMasterTable> items, tempItems, suggestions;

    public DistrictAdapter(@NonNull Context context, int resourceId, List<DistricMasterTable> items) {
        super(context, resourceId, items);
        this.items = items;
        this.context = context;
        this.resourceId = resourceId;
        tempItems = new ArrayList<DistricMasterTable>(items);
        suggestions = new ArrayList<DistricMasterTable>();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(resourceId, parent, false);
        }
        DistricMasterTable event_type = getItem(position);
        TextView name = view.findViewById(R.id.text_view);
        name.setText(event_type.getCode() + "(" + event_type.getName() + ")");
        return view;
    }

    @Nullable
    @Override
    public DistricMasterTable getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getCount() {
        if (items.size() > 10)
            return 10;
        else
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
            DistricMasterTable data = (DistricMasterTable) resultValue;
            return data.getName();
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if (charSequence != null) {
                suggestions.clear();
                for (DistricMasterTable item : tempItems) {
                    if (item.getCode().toLowerCase().contains(charSequence.toString().toLowerCase()) ||
                            item.getName().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        suggestions.add(item);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                suggestions.clear();
                suggestions.addAll(tempItems);
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            }

        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            List<DistricMasterTable> filterList = (ArrayList<DistricMasterTable>) filterResults.values;
            if (filterResults != null && filterResults.count > 0) {
                try {
                    clear();
                    for (DistricMasterTable people : filterList) {
                        if (people != null) {
                            add(people);
                            notifyDataSetChanged();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };
}