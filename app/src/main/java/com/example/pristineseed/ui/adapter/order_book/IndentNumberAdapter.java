package com.example.pristineseed.ui.adapter.order_book;

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
import com.example.pristineseed.model.BookingOrder.MarketingIndentModel;
import java.util.ArrayList;
import java.util.List;

public class IndentNumberAdapter extends ArrayAdapter {
    private Context context;
    private int resourceId;
    private List<MarketingIndentModel.MarketingIndentLine> items, tempItems, suggestions;
    public IndentNumberAdapter(@NonNull Context context, int resourceId, List<MarketingIndentModel.MarketingIndentLine> items) {
        super(context, resourceId,items);
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
            String indent_no = getItem(position).No;
            String variety_name = getItem(position).variety_name;
            TextView name = (TextView) view.findViewById(R.id.text_view);
            name.setText(indent_no + " ( " + variety_name + " )");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    @Nullable
    @Override
    public MarketingIndentModel.MarketingIndentLine getItem(int position) {
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
            MarketingIndentModel.MarketingIndentLine data = (MarketingIndentModel.MarketingIndentLine) resultValue;
            return data.No;
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults filterResults = new FilterResults();
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            // notifyDataSetChanged();
        }
    };
}
