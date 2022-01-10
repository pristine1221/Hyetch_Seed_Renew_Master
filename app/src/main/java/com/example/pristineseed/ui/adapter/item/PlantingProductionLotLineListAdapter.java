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

import com.example.pristineseed.DataBaseRepository.GeographicalRepo.CropHytechMasterTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.PlantingLineLotListTable;
import com.example.pristineseed.R;
import com.example.pristineseed.model.item.OrganizerModel;

import java.util.ArrayList;
import java.util.List;

public class PlantingProductionLotLineListAdapter extends ArrayAdapter<PlantingLineLotListTable> {

    Context context;
    int resourceId;
    List<PlantingLineLotListTable> items, tempItems, suggestions;

    public PlantingProductionLotLineListAdapter(@NonNull Context context, int resourceId, List<PlantingLineLotListTable> items) {
        super(context, resourceId, items);
        this.items = items;
        this.context = context;
        this.resourceId = resourceId;
        tempItems = new ArrayList<PlantingLineLotListTable>(items);
        suggestions = new ArrayList<PlantingLineLotListTable>();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(resourceId, parent, false);
        }
        PlantingLineLotListTable event_type = getItem(position);
        TextView name = view.findViewById(R.id.text_view);
        name.setText(event_type.getProduction_Lot_No());
        return view;
    }

    @Nullable
    @Override
    public PlantingLineLotListTable getItem(int position) {
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
            PlantingLineLotListTable data = (PlantingLineLotListTable) resultValue;
            return data.getProduction_Lot_No();
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults filterResults = new FilterResults();
            return  filterResults;
           /* FilterResults filterResults = new FilterResults();
            if (charSequence != null) {
                suggestions.clear();
                for (PlantingLineLotListTable item : tempItems) {
                    if (item.getProduction_Lot_No().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        suggestions.add(item);
                    }
                }
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }*/
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
           /* List<PlantingLineLotListTable> filterList = (ArrayList<PlantingLineLotListTable>) filterResults.values;
            if (filterResults != null && filterResults.count > 0) {
                clear();
                for (PlantingLineLotListTable people : filterList) {
                    add(people);
                    notifyDataSetChanged();
                }
            }*/
        }
    };
}