package com.example.pristineseed.ui.adapter.event_adapterr;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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
import com.example.pristineseed.R;
import com.example.pristineseed.RoomDataBase.PristineDatabase;
import com.example.pristineseed.global.DateTimeUtilsCustome;
import com.example.pristineseed.model.event_managment_model.SyncEventDetailModel;
import com.google.android.material.chip.Chip;
import java.util.List;

public class EventApproveListAdapter extends BaseAdapter {
    List<SyncEventDetailModel> listdata;
    Activity activity;

    public interface OnItemClickListener {
        void onItemClick(SyncEventDetailModel selectedeventData, int position, String flag);
    }

    private final OnItemClickListener listener;

    public EventApproveListAdapter(Activity activity, List<SyncEventDetailModel> listdata, OnItemClickListener listener) {
        super();
        this.listdata = listdata;
        this.activity = activity;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listdata.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return listdata.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        convertView = inflater.inflate(R.layout.view_event_listview, null);
        TextView event_code = convertView.findViewById(R.id.event_code);
        TextView event_date = convertView.findViewById(R.id.event_date);
        TextView event_desc = convertView.findViewById(R.id.event_desc);
        TextView event_status = convertView.findViewById(R.id.event_status);
        TextView event_type = convertView.findViewById(R.id.event_type);
        TextView event_budget = convertView.findViewById(R.id.event_budget);
        TextView crop_name = convertView.findViewById(R.id.crop_name);
        TextView variety_name = convertView.findViewById(R.id.variety_name);
        TextView state_name = convertView.findViewById(R.id.state_name);
        TextView district_name = convertView.findViewById(R.id.district_name);
        TextView taluka_name = convertView.findViewById(R.id.taluka_name);
        TextView village = convertView.findViewById(R.id.village);

        Chip approve_order = convertView.findViewById(R.id.approve_order);
        Chip reject_order = convertView.findViewById(R.id.reject_order);
        approve_order.setVisibility(View.VISIBLE);
        reject_order.setVisibility(View.VISIBLE);
        event_code.setText(listdata.get(position).event_code);
        event_desc.setText(listdata.get(position).event_desc);
        event_type.setText(listdata.get(position).event_type);
        event_budget.setText(listdata.get(position).event_budget);
        crop_name.setText(listdata.get(position).crop_name);
        event_date.setText(DateTimeUtilsCustome.getDateMMMDDYYYY(listdata.get(position).event_date));
        event_status.setText(DateTimeUtilsCustome.getDate_Time(listdata.get(position).created_on) + " " + listdata.get(position).status);
        PristineDatabase pristineDatabase =PristineDatabase.getAppDatabase(activity);
        try {
            CropHytechMasterDao cropMaster_dao=pristineDatabase.cropHytechMasterDao();
            List<CropHytechMasterTable> cropMasterTableList=cropMaster_dao.getCropName(listdata.get(position).crop);
            listdata.get(position).crop_name =cropMasterTableList.get(position).getDescription();

            HybridItemMasterDao crop_item_master_dao=pristineDatabase.hybridItemMasterDao();
            List<Hybrid_Item_Table> cropItemMasterTableList=crop_item_master_dao.getHybridItemForEvent(listdata.get(position).variety);
            listdata.get(position).crop_hybrid = cropItemMasterTableList.get(position).getNo();

            StateMasterDao stateMasterDao=pristineDatabase.stateMasterDao();
           List<StateMasterTable> stateMasterTableList = stateMasterDao.getStateName(listdata.get(position).state);

            listdata.get(position).state_name=stateMasterTableList.get(position).getName();

            DistricMasterDao districMasterDao=pristineDatabase.districMasterDao();

            List<DistricMasterTable> districMasterTableList = districMasterDao.fetchDistrictName(listdata.get(position).district);
            listdata.get(position).district_name=districMasterTableList.get(position).getName();

            TalukaMasterDao talukaMasterDao=pristineDatabase.talukaMasterDao();

           List<TalukaMasterTable> talukaMasterTablesList= talukaMasterDao.getTalukaName(listdata.get(position).taluka);

            listdata.get(position).taluka_name =talukaMasterTablesList.get(position).getDescription();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pristineDatabase.close();
            pristineDatabase.destroyInstance();

            crop_name.setText(listdata.get(position).crop_name);
            variety_name.setText(listdata.get(position).crop_hybrid);
            state_name.setText(listdata.get(position).state_name);
            district_name.setText(listdata.get(position).district_name);
            taluka_name.setText(listdata.get(position).taluka_name);
        }
        approve_order.setOnClickListener(view -> {
            listener.onItemClick(listdata.get(position), position, "approve");
        });
        reject_order.setOnClickListener(view -> {
            listener.onItemClick(listdata.get(position), position, "reject");
        });
        convertView.setOnClickListener(view -> {
            listener.onItemClick(listdata.get(position), position, "view");
        });
        village.setText(listdata.get(position).village);
        return convertView;

    }
}