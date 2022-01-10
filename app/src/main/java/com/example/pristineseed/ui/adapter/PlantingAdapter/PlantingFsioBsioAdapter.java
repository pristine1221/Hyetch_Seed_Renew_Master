package com.example.pristineseed.ui.adapter.PlantingAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pristineseed.DataBaseRepository.Planting.PlantingFSIO_BSIO_Table;
import com.example.pristineseed.R;
import com.example.pristineseed.model.PlantingModel.PlantingFsio_bsio_model;

import java.util.ArrayList;
import java.util.List;

public class PlantingFsioBsioAdapter extends ArrayAdapter {
    private Context context;
    private int resourceId;
    private List<PlantingFsio_bsio_model> items, tempItems, suggestions;


    public PlantingFsioBsioAdapter(Context context, int resourceId, List<PlantingFsio_bsio_model> items) {
        super(context, resourceId, items);
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

            TextView name = view.findViewById(R.id.text_view);

            String doc_no = getItem(position).No;
            name.setText(doc_no);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    @Nullable
    @Override
    public PlantingFsio_bsio_model getItem(int position) {
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

        return districtFilter;
    }

    private Filter districtFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            PlantingFsio_bsio_model plantingFsio_bsio_model = (PlantingFsio_bsio_model) resultValue;
            return plantingFsio_bsio_model.No;
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if (charSequence != null) {
                suggestions.clear();
                for (PlantingFsio_bsio_model district : tempItems) {
                    if (district.No.toLowerCase().startsWith(charSequence.toString().toLowerCase())) {
                        suggestions.add(district);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();

                filterResults.values = tempItems;
                filterResults.count = tempItems.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            ArrayList<PlantingFsio_bsio_model> tempValues = (ArrayList<PlantingFsio_bsio_model>) filterResults.values;
            if (filterResults != null && filterResults.count > 0) {
                clear();
                for (PlantingFsio_bsio_model districtObj : tempValues) {
                    add(districtObj);
                }
                notifyDataSetChanged();
            } else {
                clear();
                notifyDataSetChanged();
            }

            notifyDataSetChanged();
        }
    };
}
