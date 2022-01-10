package com.example.pristineseed.ui.adapter.event_adapterr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pristineseed.DataBaseRepository.EventManagementTable.EventManagemantTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.CropHytechMasterDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.CropHytechMasterTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.DistricMasterDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.DistricMasterTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.HybridItemMasterDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.Hybrid_Item_Table;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.StateMasterDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.StateMasterTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.TalukaMasterDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.TalukaMasterTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.ZoneMasterTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.ZoneMaterDao;
import com.example.pristineseed.R;
import com.example.pristineseed.RoomDataBase.PristineDatabase;
import com.example.pristineseed.global.DateTimeUtilsCustome;

import com.example.pristineseed.model.event_managment_model.SyncEventDetailModel;
import com.google.android.material.chip.Chip;

import java.util.List;

import retrofit2.Callback;

public class EventViewListAdapter extends RecyclerView.Adapter<EventViewListAdapter.MyViewModel> {
    List<SyncEventDetailModel> listdata;
    Context context;
    public  EventItemClicListner eventItemClicListner;

    public interface  EventItemClicListner{
        void onItemClick(int pos);

    }

    public EventViewListAdapter(Context context, List<SyncEventDetailModel> listdata) {
        super();
        this.listdata = listdata;
        this.context = context;

    }
    @NonNull
    @Override
    public MyViewModel onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_event_listview, parent, false);
        MyViewModel vh = new  MyViewModel(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewModel holder, int position) {
      holder.approve_order.setVisibility(View.GONE);
      holder.reject_order.setVisibility(View.GONE);
      holder.event_code.setText(listdata.get(position).event_code);
      holder.event_desc.setText(listdata.get(position).event_desc);
      holder.event_type.setText(listdata.get(position).event_type);
      holder.event_budget.setText(listdata.get(position).event_budget);
      holder.event_date.setText(DateTimeUtilsCustome.getDateOnly(listdata.get(position).event_date));
        holder.event_status.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);

      if(listdata.get(position).created_on!=null){
          holder.event_date_detail.setText(DateTimeUtilsCustome.getDate_Time(listdata.get(position).created_on));
          holder. event_status.setText(listdata.get(position).status);
      }else {
          holder. event_status.setText(listdata.get(position).status);
      }

        PristineDatabase pristineDatabase=PristineDatabase.getAppDatabase(context);
        try {
            CropHytechMasterDao cropMaster_dao=pristineDatabase.cropHytechMasterDao();
            StateMasterDao stateMasterDao=pristineDatabase.stateMasterDao();
            HybridItemMasterDao crop_item_master_dao=pristineDatabase.hybridItemMasterDao();
            DistricMasterDao districMasterDao=pristineDatabase.districMasterDao();
            TalukaMasterDao talukaMasterDao=pristineDatabase.talukaMasterDao();
            ZoneMaterDao zoneMaterDao=pristineDatabase.zoneMaterDao();

            List<CropHytechMasterTable> cropMasterTableList=cropMaster_dao.getCropName(listdata.get(position).crop);
            if(cropMasterTableList!=null && cropMasterTableList.size()>0) {
                listdata.get(position).crop = cropMasterTableList.get(position).getDescription();
            }

            List<Hybrid_Item_Table> cropItemMasterTableList=crop_item_master_dao.getHybridItemForEvent(listdata.get(position).crop);

            if(cropItemMasterTableList!=null && cropItemMasterTableList.size()>0) {
                listdata.get(position).crop_hybrid = cropItemMasterTableList.get(position).getNo();
            }

            List<StateMasterTable> stateMasterTableList = stateMasterDao.getStateName(listdata.get(position).state);

            if(stateMasterTableList!=null && stateMasterTableList.size()>0) {
                listdata.get(position).state = stateMasterTableList.get(position).getName();
            }

            List<DistricMasterTable> districMasterTableList = districMasterDao.fetchDistrictName(listdata.get(position).district);

            if(districMasterTableList!=null && districMasterTableList.size()>0){
            listdata.get(position).district=districMasterTableList.get(position).getName();
            }
            List<TalukaMasterTable> talukaMasterTablesList= talukaMasterDao.getTalukaName(listdata.get(position).taluka);
            if(talukaMasterTablesList!=null && talukaMasterTablesList.size()>0) {
                listdata.get(position).taluka = talukaMasterTablesList.get(position).getDescription();
            }
            ZoneMasterTable zoneMasterTable=zoneMaterDao.getZoneNameBycode(listdata.get(position).zone);
            if(zoneMasterTable!=null){
                listdata.get(position).zone=zoneMasterTable.getDescription();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pristineDatabase.close();
            pristineDatabase.destroyInstance();

           holder.crop_name.setText(listdata.get(position).crop);
           holder.variety_name.setText(listdata.get(position).crop_hybrid);
           holder.state_name.setText(listdata.get(position).state);
           holder.district_name.setText(listdata.get(position).district);
           holder.taluka_name.setText(listdata.get(position).taluka);
        }

       holder. village.setText(listdata.get(position).village);
        holder.view_detail_event_list.setOnClickListener(v -> {
            eventItemClicListner.onItemClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return   listdata.size();
    }

    public class MyViewModel extends RecyclerView.ViewHolder {

      private TextView event_code ;
      private TextView event_date ;
      private TextView event_desc ;
      private TextView event_status;
      private TextView event_type;
      private TextView event_budget;
      private TextView crop_name ;
      private TextView variety_name;
      private TextView state_name;
      private TextView district_name;
      private TextView taluka_name;
      private TextView village,event_date_detail;

      private Chip approve_order;
      private Chip reject_order;
      private LinearLayout view_detail_event_list;
        public MyViewModel(View itemView) {
            super(itemView);

            event_code = itemView.findViewById(R.id.event_code);
            event_date = itemView.findViewById(R.id.event_date);
            event_desc = itemView.findViewById(R.id.event_desc);
            event_status = itemView.findViewById(R.id.event_status);
            event_type = itemView.findViewById(R.id.event_type);
            event_budget = itemView.findViewById(R.id.event_budget);
            crop_name = itemView.findViewById(R.id.crop_name);
            variety_name = itemView.findViewById(R.id.variety_name);
            state_name = itemView.findViewById(R.id.state_name);
            district_name = itemView.findViewById(R.id.district_name);
            taluka_name = itemView.findViewById(R.id.taluka_name);
            village = itemView.findViewById(R.id.village);
             approve_order = itemView.findViewById(R.id.approve_order);
             reject_order = itemView.findViewById(R.id.reject_order);
            view_detail_event_list=itemView.findViewById(R.id.view_detail_event_list_layout);
            event_date_detail=itemView.findViewById(R.id.event_date_detail);
        }
    }

    public void setOnItemListner(EventItemClicListner eventItemClicListner){
        this.eventItemClicListner=eventItemClicListner;
    }

}
