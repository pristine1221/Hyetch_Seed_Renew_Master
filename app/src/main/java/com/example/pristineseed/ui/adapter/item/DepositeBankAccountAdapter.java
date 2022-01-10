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

import com.example.pristineseed.DataBaseRepository.seed_dispatch_note.Seed_Farmer_master_Table;
import com.example.pristineseed.R;
import com.example.pristineseed.model.item.BankAccountModel;

import java.util.ArrayList;
import java.util.List;

public class DepositeBankAccountAdapter extends ArrayAdapter<BankAccountModel.Data> {

    Context context;
    int resourceId;
    List<BankAccountModel.Data> items, tempItems, suggestions;

    public DepositeBankAccountAdapter(@NonNull Context context, int resourceId, List<BankAccountModel.Data> items) {
        super(context, resourceId, items);
        this.items = items;
        this.context = context;
        this.resourceId = resourceId;
        tempItems = new ArrayList<BankAccountModel.Data>(items);
        suggestions = new ArrayList<BankAccountModel.Data>();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(resourceId, parent, false);
        }
        BankAccountModel.Data data = getItem(position);
        TextView name = (TextView) view.findViewById(R.id.text_view);
        name.setText(data.Name + " (" + data.Bank_Account_No + ")");
        return view;
    }

    @Nullable
    @Override
    public BankAccountModel.Data getItem(int position) {
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
            BankAccountModel.Data data = (BankAccountModel.Data) resultValue;
            return data.Name;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
          //  if (constraint != null) {
          //      suggestions.clear();
         //       for (Seed_Farmer_master_Table item : tempItems) {
           //         if (item.getNo().toLowerCase().contains(constraint.toString().toLowerCase())) {
           //             suggestions.add(item);
            //        }
           //     }
                FilterResults filterResults = new FilterResults();
           //     filterResults.values = suggestions;
            //    filterResults.count = suggestions.size();
            //    return filterResults;
         //   } else {
                return new FilterResults();
          //  }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
           // List<Seed_Farmer_master_Table> filterList = (ArrayList<Seed_Farmer_master_Table>) results.values;
         //   if (results != null && results.count > 0) {
          //      clear();
           //     for (Seed_Farmer_master_Table people : filterList) {
             //       add(people);
          //          notifyDataSetChanged();
         //       }
       //     }
        }
    };
}
