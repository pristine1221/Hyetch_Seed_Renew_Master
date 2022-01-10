package com.example.pristineseed.ui.adapter.PlantingAdapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pristineseed.R;
import com.example.pristineseed.model.PlantingModel.PlantingHeaderModel;
import com.example.pristineseed.ui.inspection.planting.PlantingResponseModel;

import java.util.List;

public class PlantingDetailExpendedListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<PlantingHeaderModel> expandableListDetail;

    public PlantingDetailExpendedListAdapter(Context context,
                                             List<PlantingHeaderModel> expandableListDetail) {
        this.context = context;
        this.expandableListDetail = expandableListDetail;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        PlantingHeaderModel.PlantingLine data = this.expandableListDetail.get(listPosition).pl.get(expandedListPosition);
        return data;
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final PlantingHeaderModel.PlantingLine expandedListText = (PlantingHeaderModel.PlantingLine) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.expanded_planting_child_list, null);
        }
        TextView tv_lot_no = convertView.findViewById(R.id.tv_lot_no);
        TextView tv_status = convertView.findViewById(R.id.tv_status);
        TextView farmer_address = convertView.findViewById(R.id.farmer_address);
        // underlinetextview.setPaintFlags(underlinetextview.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        // TextView grname_varity_txt = convertView.findViewById(R.id.grname_varity);
        farmer_address.setText(expandedListText.grower_land_owner_name + ",  Addrss- " + expandedListText.state + " , " + expandedListText.city + " , " + expandedListText.taluka);
        tv_lot_no.setText(expandedListText.production_lot_no);

        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
       /* if(this.expandableListDetail.get(listPosition).il.size()==0){
         return 0;
        }*/
        if (expandableListDetail.get(listPosition).pl != null) {
            return this.expandableListDetail.get(listPosition).pl.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListDetail.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListDetail.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        PlantingHeaderModel listTitle = (PlantingHeaderModel) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.expandable_list_group_layout, null);
        }

        TextView tv_org_name = convertView.findViewById(R.id.tv_org_name);
        TextView tv_org_code = convertView.findViewById(R.id.tv_org_code_);
        TextView tv_planting_no = convertView.findViewById(R.id.tv_palnting_no);
        TextView tv_date = convertView.findViewById(R.id.tv_date);
        TextView doc_sub_type = convertView.findViewById(R.id.doc_sub_type);
        TextView tv_season_code = convertView.findViewById(R.id.tv_season_code);
        TextView swng_area_acr = convertView.findViewById(R.id.swng_area_acr);
        TextView tv_status = convertView.findViewById(R.id.tv_status);

        tv_org_code.setText(listTitle.production_centre_loc);
        tv_planting_no.setText(listTitle.code);
        tv_date.setText(listTitle.date);
        doc_sub_type.setText(listTitle.Document_SubType);
        swng_area_acr.setText(listTitle.total_sowing_area_in_acres);
        tv_season_code.setText(listTitle.season_code);


        ImageView arrow_up_down = convertView.findViewById(R.id.arrow_up_down);

        if(listTitle.status == 0) {
            tv_status.setText("Pending");
        }
        else if(listTitle.status == 1){
            tv_status.setText("Completed");
        }


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

}


