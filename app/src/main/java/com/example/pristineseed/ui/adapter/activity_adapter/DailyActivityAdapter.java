package com.example.pristineseed.ui.adapter.activity_adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pristineseed.R;
import com.example.pristineseed.model.DailyActivity_Model.DailyActivityModel;

import java.util.List;

public class DailyActivityAdapter  extends RecyclerView.Adapter<DailyActivityAdapter.ViewHolder> {

    List<DailyActivityModel.DailyActivityLines> listdata;
    Context context;

    public DailyActivityAdapter(Context context, List<DailyActivityModel.DailyActivityLines> listdata) {
        super();
        this.listdata = listdata;
        this.context = context;
    }

        @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_activity_listview, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


      holder.tv_farmer_name.setText(listdata.get(position).farmer_name);
      holder.tv_district.setText(listdata.get(position).district);
      holder.tv_village.setText(listdata.get(position).village);

      holder.tv_ajeet_crop_and_verity.setText(listdata.get(position).ajeet_crop_and_verity);
      holder.tv_ajeet_crop_age.setText(listdata.get(position).ajeet_crop_age);
      holder.tv_ajeet_fruits_per.setText(listdata.get(position).ajeet_fruits_per);
      holder.tv_ajeet_pest.setText(listdata.get(position).ajeet_pest);
      holder.tv_ajeet_disease.setText(listdata.get(position).ajeet_disease);

      holder.tv_check_crop_and_verity.setText(listdata.get(position).check_crop_and_variety);
      holder.tv_check_crop_age.setText(listdata.get(position).check_crop_age);
      holder.tv_check_fruits_per.setText(listdata.get(position).check_fruits_per);
      holder.tv_check_pest.setText(listdata.get(position).check_pest);
      holder.tv_check_disease.setText(listdata.get(position).check_disease);

        //todo manage arrows of list

        holder.ajeetArrow.setOnClickListener(view -> {
            if (holder.ajeetTableRowSection1.getVisibility() == View.GONE) {
                holder.ajeetTableRowSection1.setVisibility(View.VISIBLE);
               holder.ajeetTableRowSection2.setVisibility(View.VISIBLE);
               holder.ajeetArrow.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
            } else {
              holder. ajeetTableRowSection1.setVisibility(View.GONE);
              holder. ajeetTableRowSection2.setVisibility(View.GONE);
                holder. ajeetArrow.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
            }
        });


        holder.checkArrow.setOnClickListener(view -> {
            if (holder.checkTableRowSection1.getVisibility() == View.GONE){
                holder.checkTableRowSection1.setVisibility(View.VISIBLE);
                holder.checkTableRowSection2.setVisibility(View.VISIBLE);
                 holder. checkArrow.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
            } else {
              holder.checkTableRowSection1.setVisibility(View.GONE);
               holder.checkTableRowSection2.setVisibility(View.GONE);
              holder. checkArrow.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
            }
        });
    }

    @Override
    public int getItemCount() {
        return  listdata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_farmer_name ;
        private TextView tv_district;
        private TextView tv_village;

        private TextView tv_ajeet_crop_and_verity ;
        private TextView tv_ajeet_crop_age ;
        private TextView tv_ajeet_fruits_per ;
        private TextView tv_ajeet_pest;
        private TextView tv_ajeet_disease ;

        private TextView tv_check_crop_and_verity ;
        private TextView tv_check_crop_age ;
        private TextView tv_check_fruits_per ;
        private TextView tv_check_pest;
        private TextView tv_check_disease ;
       private ImageView ajeetArrow;
        TableRow ajeetTableRowSection1;
        TableRow ajeetTableRowSection1_2;
        TableRow ajeetTableRowSection2;
        TableRow checkTableRowSection1;
        TableRow checkTableRowSection2;
        ImageView checkArrow;
        public ViewHolder( View itemView) {
            super(itemView);
           tv_farmer_name = itemView.findViewById(R.id.tv_farmer_name);
           tv_district = itemView.findViewById(R.id.tv_district);
           tv_village = itemView.findViewById(R.id.tv_village);

           tv_ajeet_crop_and_verity = itemView.findViewById(R.id.tv_ajeet_crop_and_verity);
           tv_ajeet_crop_age = itemView.findViewById(R.id.tv_ajeet_crop_age);
           tv_ajeet_fruits_per = itemView.findViewById(R.id.tv_ajeet_fruits_per);
           tv_ajeet_pest = itemView.findViewById(R.id.tv_ajeet_pest);
           tv_ajeet_disease = itemView.findViewById(R.id.tv_ajeet_disease);

           tv_check_crop_and_verity = itemView.findViewById(R.id.tv_check_crop_and_verity);
           tv_check_crop_age = itemView.findViewById(R.id.tv_check_crop_age);
           tv_check_fruits_per = itemView.findViewById(R.id.tv_check_fruits_per);
           tv_check_pest = itemView.findViewById(R.id.tv_check_pest);
           tv_check_disease = itemView.findViewById(R.id.tv_check_disease);
           ajeetArrow = itemView.findViewById(R.id.ajeetArrow);
         ajeetTableRowSection1 = itemView.findViewById(R.id.ajeetTableRowSection1);
         ajeetTableRowSection2 = itemView.findViewById(R.id.ajeetTableRowSection2);


                 checkArrow = itemView.findViewById(R.id.check_arraw);
             checkTableRowSection1 = itemView.findViewById(R.id.checkTableRowSection1);
            checkTableRowSection2 = itemView.findViewById(R.id.checkTableRowSection2);


        }
    }
}
