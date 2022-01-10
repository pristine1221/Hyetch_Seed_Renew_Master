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

import com.example.pristineseed.DataBaseRepository.GeographicalRepo.AreaMasterTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.ZoneMasterTable;
import com.example.pristineseed.R;

import java.util.List;

public class AreaAdapter extends ArrayAdapter<AreaMasterTable> {

    Context context;
    int resourceId;
    List<AreaMasterTable> items;

    public AreaAdapter(@NonNull Context context, int resourceId, List<AreaMasterTable> items) {
        super(context, resourceId, items);
        this.items = items;
        this.context = context;
        this.resourceId = resourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(resourceId, parent, false);
        }
        AreaMasterTable event_type = getItem(position);
        TextView name = view.findViewById(R.id.text_view);
        name.setText(event_type.getArea_code() + "(" + event_type.getArea_name() + ")");
        return view;
    }

    @Nullable
    @Override
    public AreaMasterTable getItem(int position) {
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
            AreaMasterTable data = (AreaMasterTable) resultValue;
            return data.getArea_code();
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults filterResults = new FilterResults();
            // filterResults.values = items;
            //  filterResults.count = items.size();
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            // notifyDataSetChanged();
        }
    };

}