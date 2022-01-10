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
import androidx.recyclerview.widget.RecyclerView;

import com.example.pristineseed.DataBaseRepository.GeographicalRepo.RoleMasterTable;
import com.example.pristineseed.R;
import com.example.pristineseed.model.DailyActivity_Model.DailyActivityModel;
import com.example.pristineseed.model.item.RoleMasterModel;
import com.example.pristineseed.ui.adapter.activity_adapter.DailyActivityAdapter;

import java.util.List;

public class RoleMasterAdapter extends  RecyclerView.Adapter<RoleMasterAdapter.ViewHolder>{

    private List<RoleMasterModel.Data> listdata;
    private Context context;

    public OnItemClickListner clickListner;

    public interface OnItemClickListner{
        void onItemClick(int pos);
    }

    public RoleMasterAdapter(Context context, List<RoleMasterModel.Data> listdata) {
        super();
        this.listdata = listdata;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RoleMasterModel.Data roledata_model=listdata.get(position);
        holder.textview.setText(roledata_model.name);

        holder.textview.setOnClickListener(v -> {
            clickListner.onItemClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textview;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textview=itemView.findViewById(R.id.text_view);
        }
    }

    public void setOnClick(OnItemClickListner clickListner){
        this.clickListner=clickListner;

    }
}