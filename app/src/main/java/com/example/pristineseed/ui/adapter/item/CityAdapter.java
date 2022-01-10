package com.example.pristineseed.ui.adapter.item;

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

import com.example.pristineseed.DataBaseRepository.travel.CityMasterTable;
import com.example.pristineseed.R;
import com.example.pristineseed.model.GeoSetupModel.DispatchFarmerModel;
import com.example.pristineseed.model.item.StateModel;
import com.example.pristineseed.model.travel.ta_da_model.CityMasterModel;

import java.util.ArrayList;
import java.util.List;

public class CityAdapter extends ArrayAdapter<CityMasterModel.CityMaster> {
    Context context;
    int resourceId;
    List<CityMasterModel.CityMaster> items, tempItems, suggestions;

    public CityAdapter(@NonNull Context context, int resourceId, List<CityMasterModel.CityMaster> items) {
        super(context, resourceId, items);
        this.items = items;
        this.context = context;
        this.resourceId = resourceId;
        tempItems = new ArrayList<CityMasterModel.CityMaster>(items);
        suggestions = new ArrayList<CityMasterModel.CityMaster>();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(resourceId, parent, false);
        }

        CityMasterModel.CityMaster data = getItem(position);
        TextView name = (TextView) view.findViewById(R.id.text_view);
        name.setText(data.City +" (" +data.Code + ")");
        return view;
    }

    @Nullable
    @Override
    public CityMasterModel.CityMaster getItem(int position) {
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
            CityMasterModel.CityMaster data = (CityMasterModel.CityMaster) resultValue;
            return data.Code;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            if (constraint != null) {
                suggestions.clear();
                for (CityMasterModel.CityMaster item : tempItems) {
                    if (item.Code.toLowerCase().contains(constraint.toString().toLowerCase()) ||
                            item.City.toLowerCase().contains(constraint.toString().toLowerCase())) {
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

        /*    FilterResults filterResults = new FilterResults();
            return filterResults;*/
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            List<CityMasterModel.CityMaster> filterList = (ArrayList<CityMasterModel.CityMaster>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (CityMasterModel.CityMaster people : filterList) {
                    if(people!=null) {
                        add(people);
                        notifyDataSetChanged();
                    }
                }

            }


        }
    };
}