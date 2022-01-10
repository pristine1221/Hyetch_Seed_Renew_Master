package com.example.pristineseed.ui.travel.addExpanse;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pristineseed.DataBaseRepository.travel.CityMasterTable;
import com.example.pristineseed.DataBaseRepository.travel.City_master_Dao;
import com.example.pristineseed.DataBaseRepository.travel.TravelLineExpenseTable;
import com.example.pristineseed.R;
import com.example.pristineseed.RoomDataBase.PristineDatabase;
import com.example.pristineseed.model.travel.ta_da_model.SyncTravelDetailModel;

import java.util.List;

public class TravelExpenseListAdapter extends RecyclerView.Adapter<TravelExpenseListAdapter.MyViewHolder> {
    List<SyncTravelDetailModel.Travel_line_Expense> listdata;
    Activity activity;

    public AddLineDeleteClickListner addLineDeleteClickListner;

    public interface AddLineDeleteClickListner {
        void onClick(int pos,SyncTravelDetailModel.Travel_line_Expense line_expense);
    }

    public TravelExpenseListAdapter(Activity activity, List<SyncTravelDetailModel.Travel_line_Expense> listdata) {
        this.listdata = listdata;
        this.activity = activity;
        this.addLineDeleteClickListner = addLineDeleteClickListner;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_travel_listview, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_date.setText(listdata.get(position).date);
        holder.tv_from_loc.setText(listdata.get(position).from_loc);
        holder.tv_to_loc.setText(listdata.get(position).to_loc);

        holder.tv_Departure.setText(listdata.get(position).departure);
        holder.tv_Arrival.setText(listdata.get(position).arrival);
        holder.tv_Fare.setText(listdata.get(position).fare);
        holder.tv_mode_of_travel.setText(listdata.get(position).mode_of_travel);
        holder.tv_Loading.setText(listdata.get(position).loading_in_any);

        holder.tv_fuelVehicleExpans.setText(listdata.get(position).fuel_vehicle_expance);
//        holder.tv_Daily_Express.setText(listdata.get(position).daily_express);
        holder.tv_VehicleRepairing.setText(listdata.get(position).vehicle_repairing);
        holder.tv_local_convance.setText(listdata.get(position).local_convance);
        holder.tv_Other_Expenses.setText(listdata.get(position).other_expenses);
        holder.tv_Total_Expanse.setText(listdata.get(position).total_amount_calulated);
        holder.tv_total_km.setText(listdata.get(position).total_km);
        holder.tv_total_km_amt.setText(listdata.get(position).total_km_amt);
        holder.tv_food_exp.setText(listdata.get(position).food_expenses);

        holder.tv_toll_tax_expense.setText(listdata.get(position).toll_tax_expense);
        holder.tv_courier_or_parcel.setText(listdata.get(position).courier);

        holder.item_container.setOnClickListener(v -> {
            addLineDeleteClickListner.onClick(position,listdata.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_date, tv_from_loc, tv_to_loc, tv_Departure, tv_Arrival, tv_Fare, tv_mode_of_travel, tv_Loading, tv_fuelVehicleExpans, tv_Daily_Express, tv_VehicleRepairing,
                tv_local_convance,
                tv_Other_Expenses,
                tv_Total_Expanse, tv_total_km, tv_total_km_amt, tv_food_exp, tv_toll_tax_expense, tv_courier_or_parcel;
        LinearLayout item_container;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_date = itemView.findViewById(R.id.tv_date);
            tv_from_loc = itemView.findViewById(R.id.tv_from_loc);
            tv_to_loc = itemView.findViewById(R.id.tv_to_loc);
            tv_Departure = itemView.findViewById(R.id.tv_Departure);
            tv_Arrival = itemView.findViewById(R.id.tv_Arrival);
            tv_Fare = itemView.findViewById(R.id.tv_Fare);
            tv_mode_of_travel = itemView.findViewById(R.id.tv_mode_of_travel);
            tv_Loading = itemView.findViewById(R.id.tv_Loading);
            tv_fuelVehicleExpans = itemView.findViewById(R.id.tv_fuelVehicleExpanse);
//            tv_Daily_Express = itemView.findViewById(R.id.tv_Daily_Express);
            tv_VehicleRepairing = itemView.findViewById(R.id.tv_VehicleRepairing);
            tv_local_convance = itemView.findViewById(R.id.tv_local_convance);
            tv_Other_Expenses = itemView.findViewById(R.id.tv_Other_Expenses);
            tv_Total_Expanse = itemView.findViewById(R.id.tv_Total_Expanse);
            item_container = itemView.findViewById(R.id.item_container);
            tv_total_km = itemView.findViewById(R.id.tv_total_km);
            tv_total_km_amt = itemView.findViewById(R.id.tv_total_km_amt);
            tv_food_exp = itemView.findViewById(R.id.tv_food_exp);
            tv_toll_tax_expense = itemView.findViewById(R.id.tv_toll_tax_expense);
            tv_courier_or_parcel = itemView.findViewById(R.id.tv_courier_or_parcel);
        }

    }

    public void setAddLineDeleteClickListner(AddLineDeleteClickListner addLineDeleteClickListner){
        this.addLineDeleteClickListner = addLineDeleteClickListner;
    }

}
