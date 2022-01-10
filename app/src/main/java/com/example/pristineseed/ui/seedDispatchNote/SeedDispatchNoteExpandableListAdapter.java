package com.example.pristineseed.ui.seedDispatchNote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pristineseed.R;
import com.example.pristineseed.model.seed_dispatch_note.SeedDispatchHeaderModel;

import java.util.List;

public class SeedDispatchNoteExpandableListAdapter extends BaseExpandableListAdapter {


    private Context context;
    private List<SeedDispatchHeaderModel> expandableListDetail;

    public SeedDispatchNoteExpandableListAdapter(Context context,
                                                 List<SeedDispatchHeaderModel> expandableListDetail) {
        this.context = context;
        this.expandableListDetail = expandableListDetail;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        SeedDispatchHeaderModel.SeedDispatchLineModel data = this.expandableListDetail.get(listPosition).dispatch_line.get(expandedListPosition);
        return data;
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final SeedDispatchHeaderModel.SeedDispatchLineModel expandedListText = (SeedDispatchHeaderModel.SeedDispatchLineModel) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.sheed_dishpachexpandable_list_layout, null);
        }
        TextView tv_lot_no = convertView.findViewById(R.id.tv_lot_no);
        TextView tv_Hybrid=convertView.findViewById(R.id.tv_Hybrid);
        TextView tv_Number_of_Bags=convertView.findViewById(R.id.tv_Number_of_Bags);
        TextView tv_Farmer=convertView.findViewById(R.id.tv_Farmer);
        TextView tv_Village=convertView.findViewById(R.id.tv_Village);
        TextView tv_Remarks=convertView.findViewById(R.id.tv_Remarks);
        tv_lot_no.setText(expandedListText.lot_number );
        tv_Hybrid.setText(expandedListText.hybrid );
        tv_Number_of_Bags.setText(expandedListText.number_of_bags);
        tv_Farmer.setText(expandedListText.farmer_name);
        tv_Village.setText(expandedListText.village_name);
        //tv_Remarks.setText(expandedListText.);
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
       /* if(this.expandableListDetail.get(listPosition).il.size()==0){
         return 0;
        }*/
        return this.expandableListDetail.get(listPosition).dispatch_line
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListDetail.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        if(expandableListDetail!=null) {
            return this.expandableListDetail.size();
        }
        return 0;
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        SeedDispatchHeaderModel listTitle = (SeedDispatchHeaderModel) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.seeddispachnoteexpandable_list_group_layout, null);
        }


        TextView tv_SDN_Number = convertView.findViewById(R.id.tv_SDN_Number);
        TextView tv_date = convertView.findViewById(R.id.tv_date);
        TextView tv_From_Location = convertView.findViewById(R.id.tv_From_Location);
        TextView tv_To_Location = convertView.findViewById(R.id.tv_To_Location);
        TextView tv_Transporter = convertView.findViewById(R.id.tv_Transporter);
        TextView tv_Truck_Number = convertView.findViewById(R.id.tv_Truck_Number);

        tv_SDN_Number.setText(listTitle.dispatch_no);
        tv_date.setText(listTitle.date);
        tv_From_Location.setText(listTitle.from_location);
        tv_To_Location.setText(listTitle.to_location);
        tv_Transporter.setText(listTitle.transporter);
        tv_Truck_Number.setText(listTitle.truck_number);
        ImageView arrow_up_down = convertView.findViewById(R.id.arrow_up_down);

        if (isExpanded) {
            arrow_up_down.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_keyboard_arrow_up_black_24dp));
        } else {
            arrow_up_down.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_keyboard_arrow_down_black_24dp));
        }
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
//dfndfnjkdf

}
