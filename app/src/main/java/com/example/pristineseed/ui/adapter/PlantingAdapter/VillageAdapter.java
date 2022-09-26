package com.example.pristineseed.ui.adapter.PlantingAdapter;

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

public class VillageAdapter extends ArrayAdapter<DispatchFarmerModel.Data> {
    private Context context;
    private int resourceId;
    private List<DispatchFarmerModel.Data> items, tempItems, suggestions;

    public VillageAdapter(@NonNull Context context, int resourceId, List<DispatchFarmerModel.Data> items) {
        super(context,resourceId,items);
        this.items = items;
        this.context = context;
        this.resourceId = resourceId;
        tempItems = new ArrayList<>(items);
        suggestions = new ArrayList<>();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        try {
            if (convertView == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                view = inflater.inflate(resourceId, parent, false);
            }

            TextView name = (TextView) view.findViewById(R.id.text_view);
            DispatchFarmerModel.Data data= items.get(position);
            name.setText(data.Village + "(" + data.Name + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Nullable
    @Override
    public DispatchFarmerModel.Data getItem(int position) {
        return items.get(position);
    }

  /*  @NonNull
    @Override
    public Filter getFilter() {
        return districtFilter;
    }

    private Filter districtFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String district = (String) resultValue;
            return district;
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if (charSequence != null) {
//                    suggestions.clear();
//                    for (String district : tempItems) {
//                        if (district.toLowerCase().startsWith(charSequence.toString().toLowerCase())) {
//                            suggestions.add(district);
//                        }
//                    }
//                    FilterResults filterResults = new FilterResults();
//                    filterResults.values = suggestions;
//                    filterResults.count = suggestions.size();
                FilterResults filterResults = new FilterResults();
                filterResults.values = tempItems;
                filterResults.count = tempItems.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            ArrayList<String> tempValues = (ArrayList<String>) filterResults.values;
//                if (filterResults != null && filterResults.count > 0) {
//                    clear();
//                    for (String districtObj : tempValues) {
//                        add(districtObj);
//                    }
//                    notifyDataSetChanged();
//                } else {
//                    clear();
//                    notifyDataSetChanged();
//                }

            notifyDataSetChanged();
        }
    };*/
}
