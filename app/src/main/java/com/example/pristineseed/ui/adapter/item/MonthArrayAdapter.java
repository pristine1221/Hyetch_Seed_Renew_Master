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
import com.example.pristineseed.R;

import java.util.ArrayList;
import java.util.List;

public class MonthArrayAdapter extends ArrayAdapter<String> {
    Context context;
    int resourceId;
    List<String> items, tempItems, suggestions;

    public MonthArrayAdapter(@NonNull Context context, int resourceId, List<String> items) {
        super(context, resourceId, items);
        this.items = items;
        this.context = context;
        this.resourceId = resourceId;
        tempItems = new ArrayList<String>(items);
        suggestions = new ArrayList<String>();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(resourceId, parent, false);
        }
        String data = getItem(position);
        TextView name = (TextView) view.findViewById(R.id.text_view);
        name.setText(data);
        return view;
    }

    @Nullable
    @Override
    public String getItem(int position) {
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
            String data = (String) resultValue;
            return data;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            //    if (constraint != null) {
            //      suggestions.clear();
            //     for (BankMasterTable item : tempItems) {
            //         if (item.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
            //             suggestions.add(item);
            //        }
            //    }
            FilterResults filterResults = new FilterResults();
            //   filterResults.values = suggestions;
            //    filterResults.count = suggestions.size();
            return filterResults;
            //  } else {
            // return new FilterResults();
            //   }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            //  List<BankMasterTable> filterList = (ArrayList<BankMasterTable>) results.values;
            //   if (results != null && results.count > 0) {
            //  clear();
            //     for (BankMasterTable people : filterList) {
            //          add(people);
            //    notifyDataSetChanged();
            //     }
            //   }
        }
    };
}