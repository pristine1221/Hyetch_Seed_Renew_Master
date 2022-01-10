package com.example.pristineseed.ui.adapter.activity_adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pristineseed.R;
import com.example.pristineseed.global.DateTimeUtilsCustome;

import com.example.pristineseed.model.DailyActivity_Model.DailyActivityResponseModel;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class  DailyActivityListAdapter extends RecyclerView.Adapter<DailyActivityListAdapter.ViewHolder> implements Filterable {

    List<DailyActivityResponseModel> listdata;
     OnItemClicListner onItemClicListner;
    Context activity;
    private List<DailyActivityResponseModel> dataListFull;

    public  interface OnItemClicListner{
        void onClick(int pos);
    }

    public void updateList(List<DailyActivityResponseModel> list) {
        listdata = list;
    }

    public DailyActivityListAdapter(Context activity, List<DailyActivityResponseModel> listdata, OnItemClicListner onItemClicListner) {
        super();
        this.listdata = listdata;
        this.activity = activity;
        this.onItemClicListner=onItemClicListner;
        dataListFull = new ArrayList<>(listdata);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_activity_view_listview, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.order_collected_tv.setText("Order Collected : "+listdata.get(position).order_collected);
        holder.paymentCollected.setText("Payt. Collected : "+listdata.get(position).payment_collected);
        holder.activity_no.setText(listdata.get(position).activity_no);
        holder.contact_no_tv.setText("Contact : "+listdata.get(position).contact_no +(listdata.get(position).contact_no.equalsIgnoreCase("0")?"":" , "+listdata.get(position).contact_no) );
        try {
           holder. created_on.setText(DateTimeUtilsCustome.getDate_Time(listdata.get(position).updated_on));

        } catch (Exception e) {
        }

        holder.card_layout.setOnClickListener(v -> {
            onItemClicListner.onClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView activity_no;
        TextView contact_no_tv;
        TextView created_on;
        TextView order_collected_tv;
        TextView paymentCollected;
        MaterialCardView card_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           activity_no = itemView.findViewById(R.id.activity_no);
           contact_no_tv = itemView.findViewById(R.id.contact_no_tv);
           created_on = itemView.findViewById(R.id.created_on);
           order_collected_tv=itemView.findViewById(R.id.order_collected_tv);
           paymentCollected=itemView.findViewById(R.id.paymentCollected);
           card_layout=itemView.findViewById(R.id.card_layout);
        }
    }

    @Override
    public Filter getFilter() {
        return dataListFilter;
    }

    private Filter dataListFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

          /*  List<DailyActivityResponseModel> filterList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filterList.addAll(dataListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (DailyActivityResponseModel item : dataListFull) {
                    if (DateTimeUtilsCustome.getDateOnly(item.updated_on) != null) {
                        if (DateTimeUtilsCustome.getDateOnly(item.updated_on).toLowerCase().contains(filterPattern)) {
                            filterList.add(item);
                        }
                    }
                }
            }*/

            FilterResults results = new FilterResults();
           // results.values = filterList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listdata.clear();
            listdata.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
