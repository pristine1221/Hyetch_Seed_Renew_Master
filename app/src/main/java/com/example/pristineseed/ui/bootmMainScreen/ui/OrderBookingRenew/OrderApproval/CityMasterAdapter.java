package com.example.pristineseed.ui.bootmMainScreen.ui.OrderBookingRenew.OrderApproval;

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

import com.example.pristineseed.R;
import com.example.pristineseed.model.item.CropMassterModel;
import com.example.pristineseed.model.item.RoleMasterModel;
import com.example.pristineseed.model.travel.ta_da_model.CityMasterModel;

import java.util.List;

public class CityMasterAdapter extends ArrayAdapter<RoleMasterModel.Data> {
    private List<RoleMasterModel.Data> listData;
    private Context context;
    int resourceId;

    public CityMasterAdapter(@NonNull Context context, int resourceId, List<RoleMasterModel.Data> listData) {
        super(context, resourceId, listData);
        this.context = context;
        this.listData = listData;
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
        RoleMasterModel.Data data = getItem(position);
        TextView city = (TextView) view.findViewById(R.id.text_view);
        city.setText(data.city);
        return view;
    }

    @Nullable
    @Override
    public RoleMasterModel.Data getItem(int position) {
        return listData.get(position);
    }

    @Override
    public int getCount() {
        return listData.size();
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
            RoleMasterModel.Data data = (RoleMasterModel.Data) resultValue;
            return data.city;
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
