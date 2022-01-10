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
import com.example.pristineseed.R;
import com.example.pristineseed.model.item.CropMassterModel;

import java.util.List;

public class CropHytechAdapter extends ArrayAdapter<CropMassterModel.Data> {
    Context context;
    int resourceId;
    List<CropMassterModel.Data> items;

    public CropHytechAdapter(@NonNull Context context, int resourceId, List<CropMassterModel.Data> items) {
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
        CropMassterModel.Data event_type = getItem(position);
        TextView name = view.findViewById(R.id.text_view);
        name.setText(event_type.Code + "(" + event_type.Description + ")");
        return view;
    }

    @Nullable
    @Override
    public CropMassterModel.Data getItem(int position) {
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
            CropMassterModel.Data data = (CropMassterModel.Data) resultValue;
            return data.Code;
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults filterResults = new FilterResults();
            // filterResults.values = items;
            // filterResults.count = items.size();
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            // notifyDataSetChanged();
        }
    };

}
