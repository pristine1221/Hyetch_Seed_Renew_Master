package com.example.pristineseed.ui.adapter.event_adapterr;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pristineseed.DataBaseRepository.EventManagementTable.EventManagementExpenseLineTable;
import com.example.pristineseed.DataBaseRepository.EventManagementTable.EventTypeMasterDao;
import com.example.pristineseed.DataBaseRepository.EventManagementTable.EventTypeMasterTable;
import com.example.pristineseed.R;
import com.example.pristineseed.RoomDataBase.PristineDatabase;
import com.example.pristineseed.model.event_managment_model.SyncEventDetailModel;


import java.util.List;

public class EventExpenseListAdapter  extends BaseAdapter {
    List<SyncEventDetailModel.ExpanceLineModel> listdata;
    Activity activity;

    public EventExpenseListAdapter(Activity activity, List<SyncEventDetailModel.ExpanceLineModel> listdata) {
        super();
        this.listdata = listdata;
        this.activity = activity;
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
        convertView = inflater.inflate(R.layout.expense_event_listview, null);
        TextView expenseType = convertView.findViewById(R.id.expenseType);
        TextView quantity = convertView.findViewById(R.id.quantity);
        TextView rate = convertView.findViewById(R.id.rate);
        TextView amount = convertView.findViewById(R.id.amount);
        if (listdata.get(position).expense_type == null || listdata.get(position).expense_type.equalsIgnoreCase("")) {
            PristineDatabase pristineDatabase=PristineDatabase.getAppDatabase(activity);
            try {
                EventTypeMasterDao eventTypeMasterDao=pristineDatabase.eventTypeMasterDao();
                List<EventTypeMasterTable> eventTypeMasterTablesList = eventTypeMasterDao.fetchNameById(listdata.get(position).expense_type);
                listdata.get(position).expense_type=eventTypeMasterTablesList.get(position).getEvent_type();

            }catch (Exception e){
                e.printStackTrace();
            }
            finally {
                pristineDatabase.close();
                pristineDatabase.destroyInstance();
            }
        }
        expenseType.setText(listdata.get(position).expense_type);
        quantity.setText(listdata.get(position).quantity);
        rate.setText(listdata.get(position).rate_unit_cost);
        amount.setText(listdata.get(position).amount);
        return convertView;

    }
}