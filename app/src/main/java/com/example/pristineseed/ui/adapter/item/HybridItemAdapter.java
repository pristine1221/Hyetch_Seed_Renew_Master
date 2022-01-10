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

import java.util.ArrayList;
import java.util.List;

public class HybridItemAdapter extends ArrayAdapter<Hybrid_Item_Table> {
    Context context;
    int resourceId;
    List<Hybrid_Item_Table> items, tempItems, suggestions;

    public HybridItemAdapter(@NonNull Context context, int resourceId, List<Hybrid_Item_Table> items) {
        super(context, resourceId, items);
        this.items = items;
        this.context = context;
        this.resourceId = resourceId;
        tempItems = new ArrayList<Hybrid_Item_Table>(items);
        suggestions = new ArrayList<Hybrid_Item_Table>();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(resourceId, parent, false);
        }
        Hybrid_Item_Table data = getItem(position);
        TextView name = view.findViewById(R.id.text_view);
        name.setText(data.getNo());
//        name.setText(data.getNo()+"("+data.getAlias_Name()+")");
        return view;
    }

    @Nullable
    @Override
    public Hybrid_Item_Table getItem(int position) {
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

    private final Filter customerFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            Hybrid_Item_Table data = (Hybrid_Item_Table) resultValue;
            return data.getNo();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
             //   suggestions.clear();
              //  for (Hybrid_Item_Table item : tempItems) {
              //      if (item.getNo().toLowerCase().contains(constraint.toString().toLowerCase())) {
              //          suggestions.add(item);
              //      }
             //   }
                FilterResults filterResults = new FilterResults();
               // filterResults.values = suggestions;
               // filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<Hybrid_Item_Table> filterList = (ArrayList<Hybrid_Item_Table>) results.values;
           // if (results != null && results.count > 0) {
           //     clear();
            //    for (Hybrid_Item_Table people : filterList) {
               //     add(people);
                //    notifyDataSetChanged();
             //   }
         //   }
        }
    };
}