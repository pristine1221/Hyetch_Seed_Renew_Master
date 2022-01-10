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

import com.example.pristineseed.DataBaseRepository.GeographicalRepo.Hybrid_Item_Table;
import com.example.pristineseed.R;
import com.example.pristineseed.model.item.RoleMasterModel;

import java.util.ArrayList;
import java.util.List;

public class RoleMasterC_DAdapter extends ArrayAdapter<RoleMasterModel.Data> {

    Context context;
    int resourceId;
    List<RoleMasterModel.Data> items, tempItems, suggestions;

    public RoleMasterC_DAdapter(@NonNull Context context, int resource, List<RoleMasterModel.Data> items) {
        super(context, resource);
        this.items = items;
        this.context = context;
        this.resourceId = resource;
        tempItems = new ArrayList<RoleMasterModel.Data>(items);
        suggestions = new ArrayList<RoleMasterModel.Data>();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(resourceId, parent, false);
        }
        RoleMasterModel.Data data = getItem(position);
        TextView name = view.findViewById(R.id.text_view);
        name.setText(data.Search_Name+"["+ data.no+"]");
        return view;
    }

    @Nullable
    @Override
    public RoleMasterModel.Data getItem(int position) {
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

//    @NonNull
//    @Override
//    public Filter getFilter() {
//        return customerFilter;
//    }

    private final Filter customerFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            RoleMasterModel.Data data = (RoleMasterModel.Data ) resultValue;
            return data.Search_Name;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            suggestions.clear();
            suggestions.addAll(tempItems);
            FilterResults filterResults = new FilterResults();
            filterResults.values = suggestions;
            filterResults.count = suggestions.size();
            return filterResults;

//            if (constraint != null) {
//                suggestions.clear();
//                for (RoleMasterModel.Data item : tempItems) {
//                    if (item.Search_Name.toLowerCase().contains(constraint.toString().toLowerCase())) {
//                        suggestions.add(item);
//                    }
//                }
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = suggestions;
//                filterResults.count = suggestions.size();
//                return filterResults;
//            } else {
//                suggestions.clear();
//                suggestions.addAll(tempItems);
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = suggestions;
//                filterResults.count = suggestions.size();
//                return filterResults;
//            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<RoleMasterModel.Data> filterList = (ArrayList<RoleMasterModel.Data>) results.values;
            if (results != null && results.count > 0) {
                try {
                    clear();
                    for (RoleMasterModel.Data people : filterList) {
                        if (people != null) {
                            addAll(people);
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
