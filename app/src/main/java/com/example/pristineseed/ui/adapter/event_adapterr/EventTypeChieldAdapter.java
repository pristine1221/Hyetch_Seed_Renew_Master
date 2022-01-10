package com.example.pristineseed.ui.adapter.event_adapterr;

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

import com.example.pristineseed.DataBaseRepository.EventManagementTable.EventTypeMasterTable;
import com.example.pristineseed.R;

import java.util.List;

public class EventTypeChieldAdapter extends ArrayAdapter<EventTypeMasterTable> {
    Context context;
    int resourceId;
    List<EventTypeMasterTable> items;

    public EventTypeChieldAdapter(@NonNull Context context, int resourceId, List<EventTypeMasterTable> items) {
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
        EventTypeMasterTable event_type = getItem(position);
        TextView name = (TextView) view.findViewById(R.id.text_view);
        name.setText(event_type.getEvent_type());
        return view;
    }

    @Nullable
    @Override
    public EventTypeMasterTable getItem(int position) {
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
            EventTypeMasterTable eventdata = (EventTypeMasterTable) resultValue;
            return eventdata.getEvent_type();
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults filterResults = new FilterResults();
            filterResults.values = items;
            filterResults.count = items.size();
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            notifyDataSetChanged();
        }
    };
}