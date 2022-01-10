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

import com.example.pristineseed.DataBaseRepository.GeographicalRepo.BankMasterTable;
import com.example.pristineseed.DataBaseRepository.travel.CityMasterTable;
import com.example.pristineseed.R;
import com.example.pristineseed.model.item.BankMaserModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BankMasterAdapter extends ArrayAdapter<BankMaserModel.Data>

    {
        Context context;
        int resourceId;
        List<BankMaserModel.Data> items, tempItems, suggestions;

    public BankMasterAdapter(@NonNull Context context, int resourceId, List<BankMaserModel.Data> items) {
        super(context, resourceId, items);
        this.items = items;
        this.context = context;
        this.resourceId = resourceId;
        tempItems = new ArrayList<BankMaserModel.Data>(items);
        suggestions = new ArrayList<BankMaserModel.Data>();
    }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(resourceId, parent, false);
        }
            BankMaserModel.Data data = getItem(position);
        TextView name = (TextView) view.findViewById(R.id.text_view);
        name.setText(data.Name + " (" + data.Code + ")");
        return view;
    }

        @Nullable
        @Override
        public BankMaserModel.Data getItem(int position) {
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
                BankMaserModel.Data data = (BankMaserModel.Data) resultValue;
                return data.Name;
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                if (constraint != null) {
                    suggestions.clear();
                    for (BankMaserModel.Data item : tempItems) {
                        if (item.Name.toLowerCase().contains(constraint.toString().toLowerCase())) {
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
                List<BankMaserModel.Data> filterList = (ArrayList<BankMaserModel.Data>) results.values;
                if (filterList != null && filterList.size() > 0) {
                    clear();
                    /*for (BankMaserModel.Data people : filterList) {
                        if(people!=null) {
                            add(people);
                            notifyDataSetChanged();
                        }
                    }*/

                    //todo for concurrent modification exception error...
                    for(Iterator<BankMaserModel.Data> iter = filterList.iterator();
                        iter.hasNext(); ) {
                        BankMaserModel.Data data = iter.next();
                        if(data != null){
                            add(data);
                            notifyDataSetChanged();
                            iter.remove();
                        }
                    }
                }

            }
        };

}
