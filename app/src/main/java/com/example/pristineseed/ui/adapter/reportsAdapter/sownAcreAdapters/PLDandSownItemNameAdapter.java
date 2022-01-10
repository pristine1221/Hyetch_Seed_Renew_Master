package com.example.pristineseed.ui.adapter.reportsAdapter.sownAcreAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pristineseed.R;
import com.example.pristineseed.model.reportModel.PLDandSownAcreViewModel;

import java.util.List;

public class PLDandSownItemNameAdapter extends RecyclerView.Adapter<PLDandSownItemNameAdapter.ViewHolder> {

    Context context;
    List<PLDandSownAcreViewModel> acreViewModelList;
    String flag = "";

    public PLDandSownItemNameAdapter.OnItemClick onItemClickListner;

    public interface OnItemClick{
        void onItemClick(int pos);
    }

    public PLDandSownItemNameAdapter(Context context, List<PLDandSownAcreViewModel> acreViewModelList, String flag){
        this.context = context;
        this.acreViewModelList = acreViewModelList;
        this.flag = flag;
    }
    @NonNull
    @Override
    public PLDandSownItemNameAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_view, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull PLDandSownItemNameAdapter.ViewHolder holder, int position) {
        PLDandSownAcreViewModel data = acreViewModelList.get(position);
        if(flag.equals("sown_item_view")) {
            holder.tv_name.setText(data.item_name);
        }
        else if(flag.equals("pld_sown_item_view")){
            holder.tv_name.setText(data.item);
        }

        holder.tv_name.setOnClickListener(v -> {
            onItemClickListner.onItemClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return acreViewModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.text_view);
        }
    }

    public void setItemClickLisner(PLDandSownItemNameAdapter.OnItemClick onItemClick){
        this.onItemClickListner = onItemClick;
    }
}
