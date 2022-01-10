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

import com.example.pristineseed.DataBaseRepository.GeographicalRepo.TalukaMasterTable;
import com.example.pristineseed.R;
import com.example.pristineseed.model.item.BankMaserModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TalukaAdapter extends ArrayAdapter<TalukaMasterTable> {

    private Context context;
   private int resourceId;
    private List<TalukaMasterTable> items,tempItems, suggestions;

    public TalukaAdapter(@NonNull Context context, int resourceId, List<TalukaMasterTable> items) {
        super(context, resourceId, items);
        this.items = items;
        this.context = context;
        this.resourceId = resourceId;
        tempItems = new ArrayList<TalukaMasterTable>(items);
        suggestions = new ArrayList<TalukaMasterTable>();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(resourceId, parent, false);
        }
        TalukaMasterTable event_type = getItem(position);
        TextView name = view.findViewById(R.id.text_view);
        if(event_type.getDescription()!=null) {
            name.setText(event_type.getCode()+"("+event_type.getDescription()+")");
        }
        return view;
    }

    @Nullable
    @Override
    public TalukaMasterTable getItem(int position) {
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
            TalukaMasterTable data = (TalukaMasterTable) resultValue;
            return data.getDescription();
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if (charSequence != null) {
                suggestions.clear();
                for (TalukaMasterTable item : tempItems) {
                    if (item.getDescription().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        suggestions.add(item);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            }else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            List<TalukaMasterTable> filterList = (ArrayList<TalukaMasterTable>) filterResults.values;
            if (filterList != null && filterList.size() > 0) {
                clear();

                for (TalukaMasterTable people : filterList) {
                        if(people!=null) {
                            add(people);
                            notifyDataSetChanged();
                        }
                    }
            //todo for concurrent modification exception error...
              /* for(Iterator<TalukaMasterTable> iter = filterList.iterator();
                iter.hasNext(); ) {
                TalukaMasterTable data = iter.next();
                if(data != null){
                    add(data);
                    notifyDataSetChanged();
                    iter.remove();
                }
            }*/
            }
        }
    };

}
