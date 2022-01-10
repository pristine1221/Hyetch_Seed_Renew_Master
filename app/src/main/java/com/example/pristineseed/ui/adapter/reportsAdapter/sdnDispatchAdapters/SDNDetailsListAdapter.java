package com.example.pristineseed.ui.adapter.reportsAdapter.sdnDispatchAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pristineseed.R;
import com.example.pristineseed.model.reportModel.ReportSeedDispatchViewModel;

import java.util.List;

public class SDNDetailsListAdapter extends RecyclerView.Adapter<SDNDetailsListAdapter.ViewHolder> {
    public Context context;
    public List<ReportSeedDispatchViewModel> reportSeedDispatchViewModelsList;

    public SDNDetailsListAdapter(Context context, List<ReportSeedDispatchViewModel> reportSeedDispatchViewModelsList){
        this.context= context;
        this.reportSeedDispatchViewModelsList = reportSeedDispatchViewModelsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sdn_details_list_adapter, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReportSeedDispatchViewModel reportSDNModel = reportSeedDispatchViewModelsList.get(position);
        holder.tv_dispatch_no.setText(reportSDNModel.dispatch_no);
        holder.tv_organiser_name.setText(reportSDNModel.organizer_name);
        holder.tv_hybrid_name.setText(reportSDNModel.hybrid_name);
        holder.tv_quantity.setText(reportSDNModel.quantity);
        holder.tv_description.setText(reportSDNModel.Description);


        //todo get date in yyyy-mm-dd format convert it in dd-mm-yyyy and set in text...
        String[] split = reportSDNModel.date.split("-");
        String y = split[0];
        String m = split[1];
        String d = split[2];

        String date = d+"-"+m+"-"+y;
        holder.tv_dates.setText(date);

        //todo getting first letter of name to display in list....
        if(reportSeedDispatchViewModelsList.get(position).organizer_name != null &&
                !reportSeedDispatchViewModelsList.get(position).organizer_name.equalsIgnoreCase("")){
            String firstLetter = String.valueOf(reportSDNModel.organizer_name.charAt(0));
            holder.tv_character_ofImageView3.setText(firstLetter);
        }
    }

    @Override
    public int getItemCount() {
        return reportSeedDispatchViewModelsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_dispatch_no, tv_organiser_name,tv_hybrid_name, tv_quantity, tv_description, tv_dates,tv_character_ofImageView3;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_dispatch_no = itemView.findViewById(R.id.tv_dispatch_no);
            tv_organiser_name = itemView.findViewById(R.id.tv_organiser_name);
            tv_hybrid_name = itemView.findViewById(R.id.tv_hybrid_name);
            tv_quantity = itemView.findViewById(R.id.tv_quantity);
            tv_description = itemView.findViewById(R.id.tv_description);
            tv_dates = itemView.findViewById(R.id.tv_dates);
            tv_character_ofImageView3 = itemView.findViewById(R.id.tv_character_ofImageView3);
        }
    }
}
