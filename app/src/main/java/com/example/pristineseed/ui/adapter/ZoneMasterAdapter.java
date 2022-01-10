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

import com.example.pristineseed.DataBaseRepository.GeographicalRepo.ZoneMasterTable;
import com.example.pristineseed.R;

import java.util.List;

public class ZoneMasterAdapter extends ArrayAdapter<ZoneMasterTable> {

    Context context;
    int resourceId;
    List<ZoneMasterTable> items;

    public ZoneMasterAdapter(@NonNull Context context, int resourceId, List<ZoneMasterTable> items) {
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
        ZoneMasterTable event_type = getItem(position);
        TextView name = view.findViewById(R.id.text_view);
        name.setText(event_type.getCode()+"("+event_type.getDescription()+")");
        return view;
    }

    @Nullable
    @Override
    public ZoneMasterTable getItem(int position) {
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
            ZoneMasterTable data = (ZoneMasterTable) resultValue;
            return data.getDescription();
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
