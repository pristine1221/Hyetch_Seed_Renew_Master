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

import com.example.pristineseed.DataBaseRepository.GeographicalRepo.AreaMasterTable;
import com.example.pristineseed.R;
import com.example.pristineseed.model.BookingOrder.ShipToAddressModel;
import com.example.pristineseed.model.item.BookingMasterModel;
import com.example.pristineseed.model.item.RoleMasterModel;

import java.util.List;

public class BookingMasterAdapter extends RecyclerView.Adapter<BookingMasterAdapter.ViewHolder> {

    Context context;
    List<BookingMasterModel.Data> items;
    public OnItemClickListner onItemClickListner;

    public interface OnItemClickListner{
        void bookingonItemClick(int pos);
    }


    public BookingMasterAdapter(@NonNull Context context, List<BookingMasterModel.Data> items) {
        super();
        this.items = items;
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
        BookingMasterModel.Data booking_model = items.get(position);
        holder.name.setText(booking_model.App_Booking_No);

        holder.name.setOnClickListener(v -> {
            onItemClickListner.bookingonItemClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.text_view);

        }
    }
    public void setOnClick(OnItemClickListner clickListner){
        this.onItemClickListner=clickListner;

    }


}