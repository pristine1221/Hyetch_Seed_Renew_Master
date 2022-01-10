package com.example.pristineseed.ui.travel.addExpanse;

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
import com.example.pristineseed.model.travel.ta_da_model.ModeOfTravelModel;

import java.util.ArrayList;
import java.util.List;

public class ModeOftravelAdapter extends ArrayAdapter<ModeOfTravelModel> {
    Context context;
    int resourceId;
    List<ModeOfTravelModel> items, tempItems, suggestions;

    public ModeOftravelAdapter(@NonNull Context context, int resourceId, List<ModeOfTravelModel> items) {
        super(context, resourceId, items);
        this.items = items;
        this.context = context;
        this.resourceId = resourceId;
        tempItems = new ArrayList<ModeOfTravelModel>(items);
        suggestions = new ArrayList<ModeOfTravelModel>();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(resourceId, parent, false);
        }
        ModeOfTravelModel event_type = getItem(position);
        TextView name = (TextView) view.findViewById(R.id.text_view);
        name.setText(event_type.travel_mode);
        return view;
    }

    @Nullable
    @Override
    public ModeOfTravelModel getItem(int position) {
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
            ModeOfTravelModel model = (ModeOfTravelModel) resultValue;
            return model.travel_mode;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (ModeOfTravelModel item : tempItems) {
                    if (item.travel_mode.toLowerCase().contains(constraint.toString().toLowerCase())) {
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
            List<ModeOfTravelModel> filterList = (ArrayList<ModeOfTravelModel>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (ModeOfTravelModel people : filterList) {
                    add(people);
                    notifyDataSetChanged();
                }
            }
        }
    };
}