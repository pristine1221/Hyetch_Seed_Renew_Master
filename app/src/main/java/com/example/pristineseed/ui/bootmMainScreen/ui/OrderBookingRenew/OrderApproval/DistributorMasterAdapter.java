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

import com.example.pristineseed.DataBaseRepository.GeographicalRepo.BankMasterTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.DistricMasterTable;
import com.example.pristineseed.R;
import com.example.pristineseed.model.item.RoleMasterModel;

import java.util.ArrayList;
import java.util.List;

public class DistributorMasterAdapter extends ArrayAdapter<RoleMasterModel.Data> {

    private List<RoleMasterModel.Data> distributorListData;
    private Context context;
    int resourceId;

    public DistributorMasterAdapter(@NonNull Context context, int resourceId, List<RoleMasterModel.Data> distributorListData) {
        super(context, resourceId, distributorListData);
        this.context = context;
        this.distributorListData = distributorListData;
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
        TextView name = (TextView) view.findViewById(R.id.text_view);
        name.setText(data.name);
        return view;
    }

    @Nullable
    @Override
    public RoleMasterModel.Data getItem(int position) {
        return distributorListData.get(position);
    }

    @Override
    public int getCount() {
        return distributorListData.size();
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
            return data.name;
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
//            notifyDataSetChanged();
        }
    };

}
