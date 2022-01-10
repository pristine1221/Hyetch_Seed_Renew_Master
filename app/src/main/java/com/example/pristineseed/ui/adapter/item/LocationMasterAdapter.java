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

import com.example.pristineseed.DataBaseRepository.GeographicalRepo.LocationMasterTable;
import com.example.pristineseed.R;
import com.example.pristineseed.model.GeoSetupModel.UserLocationModel;

import java.util.ArrayList;
import java.util.List;

public class LocationMasterAdapter  extends ArrayAdapter<UserLocationModel> {
    Context context;
    int resourceId;
    List<UserLocationModel> items, tempItems, suggestions;

    public LocationMasterAdapter(@NonNull Context context, int resourceId, List<UserLocationModel> items) {
        super(context, resourceId, items);
        this.items = items;
        this.context = context;
        this.resourceId = resourceId;
      /*  tempItems = new ArrayList<LocationMasterTable>(items);
        suggestions = new ArrayList<LocationMasterTable>();*/
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(resourceId, parent, false);
        }
        UserLocationModel data = getItem(position);
        TextView name = (TextView) view.findViewById(R.id.text_view);
        name.setText(data.location_code + " (" + data.location_name + ")");
        return view;
    }

    @Nullable
    @Override
    public UserLocationModel getItem(int position) {
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
            UserLocationModel data = (UserLocationModel) resultValue;
            return data.location_code;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
          //  if (constraint != null) {
           //     suggestions.clear();
         //       for (LocationMasterTable item : tempItems) {
            //        if (item.getLocation_name().toLowerCase().contains(constraint.toString().toLowerCase())) {
             //           suggestions.add(item);
              //      }
             //   }
              //  FilterResults filterResults = new FilterResults();
             //   filterResults.values = suggestions;
             //   filterResults.count = suggestions.size();
             //   return filterResults;
          //  } else {
                return new FilterResults();
          //  }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
         //   List<LocationMasterTable> filterList = (ArrayList<LocationMasterTable>) results.values;
          //  if (results != null && results.count > 0) {
           //     clear();
            //    for (LocationMasterTable people : filterList) {
              //      add(people);
               //     notifyDataSetChanged();
             //   }
          //  }
        }
    };

}