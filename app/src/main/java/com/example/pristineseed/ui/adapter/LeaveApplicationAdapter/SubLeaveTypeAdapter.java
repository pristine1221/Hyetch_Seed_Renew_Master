package com.example.pristineseed.ui.adapter.LeaveApplicationAdapter;

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

import com.example.pristineseed.DataBaseRepository.GeographicalRepo.ShipToAddressDataTable;
import com.example.pristineseed.R;
import com.example.pristineseed.model.EmployeeModel.EmployeeLeaveMasterModel;
import com.example.pristineseed.model.LeaveApplicationModel.LevaeTypeModel;

import java.util.ArrayList;
import java.util.List;

public class SubLeaveTypeAdapter  extends ArrayAdapter<EmployeeLeaveMasterModel.Data> {
    private Context context;
    private int resourceId;
    private List<EmployeeLeaveMasterModel.Data> items, tempItems, suggestions;


    public SubLeaveTypeAdapter(Context context, int resourceId, List<EmployeeLeaveMasterModel.Data> items) {
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

            String cust_no = getItem(position).Leave_Type;
            name.setText(cust_no);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    @Nullable
    @Override
    public EmployeeLeaveMasterModel.Data getItem(int position) {
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
            EmployeeLeaveMasterModel.Data levaeTypeModel = (EmployeeLeaveMasterModel.Data) resultValue;
            return levaeTypeModel.Leave_Type;
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if (charSequence != null) {

//                    suggestions.clear();
//                    for (String district : tempItems) {
//                        if (district.toLowerCase().startsWith(charSequence.toString().toLowerCase())) {
//                            suggestions.add(district);
//                        }
//                    }
//                    FilterResults filterResults = new FilterResults();
//                    filterResults.values = suggestions;
//                    filterResults.count = suggestions.size();

                FilterResults filterResults = new FilterResults();
                filterResults.values = tempItems;
                filterResults.count = tempItems.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            ArrayList<EmployeeLeaveMasterModel.Data> tempValues = (ArrayList<EmployeeLeaveMasterModel.Data>) filterResults.values;
//                if (filterResults != null && filterResults.count > 0) {
//                    clear();
//                    for (String districtObj : tempValues) {
//                        add(districtObj);
//                    }
//                    notifyDataSetChanged();
//                } else {
//                    clear();
//                    notifyDataSetChanged();
//                }

            // notifyDataSetChanged();
        }
    };

}