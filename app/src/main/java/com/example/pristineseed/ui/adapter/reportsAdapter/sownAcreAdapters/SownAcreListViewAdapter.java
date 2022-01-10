package com.example.pristineseed.ui.adapter.reportsAdapter.sownAcreAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pristineseed.R;
import com.example.pristineseed.model.reportModel.PLDandSownAcreViewModel;

import java.util.List;

public class SownAcreListViewAdapter extends RecyclerView.Adapter<SownAcreListViewAdapter.ViewHolder> {

    public Context context;
    public List<PLDandSownAcreViewModel> modelList;
    String flag = "";

    public SownAcreListViewAdapter(Context context, List<PLDandSownAcreViewModel> modelList, String flag){
        this.context= context;
        this.modelList = modelList;
        this.flag = flag;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sown_acre_list_details_adapter_layout, parent, false);
        return new SownAcreListViewAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PLDandSownAcreViewModel sownAcreViewModel = modelList.get(position);

        if(flag.equals("sown_acre_view")){
            holder.organiser_name.setVisibility(View.VISIBLE);
            holder.village_name.setVisibility(View.VISIBLE);
            holder.tv_organiser_name.setVisibility(View.VISIBLE);
            holder.tv_village_name.setVisibility(View.VISIBLE);
            holder.tv_organiser_name.setText(sownAcreViewModel.organizer_name);
            holder.tv_village_name.setText(sownAcreViewModel.village_name);
            holder.tv_item_name.setText(sownAcreViewModel.item_name);
            holder.tv_crop_code.setText(sownAcreViewModel.crop_code);
            holder.tv_sowing_area_In_acres.setText(sownAcreViewModel.sowing_area_In_acres);
            holder.tv_zone_name.setText(sownAcreViewModel.zone_name);
            holder.tv_season_name.setText(sownAcreViewModel.season_name);
            holder.circuler_text_section.setVisibility(View.VISIBLE);

            //todo getting first letter of name to display in list....
            if(modelList.get(position).organizer_name != null &&
                    !modelList.get(position).organizer_name.equalsIgnoreCase("")){
                String firstLetter = String.valueOf(sownAcreViewModel.organizer_name.charAt(0));
                holder.tv_character_ofImageView4.setText(firstLetter);
            }
        }
       else if(flag.equals("pld_sown_acre_view")){
            holder.organiser_name.setVisibility(View.GONE);
            holder.village_name.setVisibility(View.GONE);
            holder.tv_organiser_name.setVisibility(View.GONE);
            holder.tv_village_name.setVisibility(View.GONE);
            holder.tv_item_name.setText(sownAcreViewModel.item);
            holder.tv_crop_code.setText(sownAcreViewModel.crop);
            holder.tv_season_name.setText(sownAcreViewModel.season_name);
            holder.tv_sowing_area_In_acres.setText(sownAcreViewModel.sowing_area_In_acres);
            holder.tv_zone_name.setText(sownAcreViewModel.zone);
            holder.tv_character_ofImageView4.setVisibility(View.VISIBLE);
            holder.tv_character_ofImageView4.setText("Pld");
        }

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_item_name, tv_organiser_name, tv_season_name, tv_crop_code, tv_village_name, tv_sowing_area_In_acres,
                tv_zone_name, tv_character_ofImageView4, organiser_name, village_name;
        FrameLayout circuler_text_section;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_item_name = itemView.findViewById(R.id.tv_item_name);
            tv_organiser_name = itemView.findViewById(R.id.tv_organiser_name);
            tv_village_name = itemView.findViewById(R.id.tv_village_name);
            tv_season_name = itemView.findViewById(R.id.tv_season_name);
            tv_crop_code = itemView.findViewById(R.id.tv_crop_code);
            tv_sowing_area_In_acres = itemView.findViewById(R.id.tv_sowing_area_In_acres);
            tv_zone_name = itemView.findViewById(R.id.tv_zone_name);
            tv_character_ofImageView4 = itemView.findViewById(R.id.tv_character_ofImageView4);
            organiser_name = itemView.findViewById(R.id.organiser_name);
            village_name = itemView.findViewById(R.id.village_name);
            circuler_text_section = itemView.findViewById(R.id.circuler_text_section);

        }
    }
}
