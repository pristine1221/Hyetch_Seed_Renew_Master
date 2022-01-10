package com.example.pristineseed.ui.adapter.order_book;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pristineseed.R;
import com.example.pristineseed.model.BookingOrder.ShipToAddressModel;

import java.util.List;

public class ShipToDetailAdapter extends RecyclerView.Adapter<ShipToDetailAdapter.ViewHolder>{

private List<ShipToAddressModel.Data> listdata;
private Context context;

public ShipOnItemClickListner clickListner;

public interface ShipOnItemClickListner{
    void shipOnItemClick(int pos);
}

    public ShipToDetailAdapter(Context context, List<ShipToAddressModel.Data> listdata) {
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
    public void onBindViewHolder(@NonNull ShipToDetailAdapter.ViewHolder holder, int position) {
        ShipToAddressModel.Data roledata_model=listdata.get(position);
        holder.textview.setText(roledata_model.Code);

        holder.itemView.setOnClickListener(v -> {
            clickListner.shipOnItemClick(position);
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

    public void setOnClick(ShipOnItemClickListner clickListner){
        this.clickListner=clickListner;

    }
}