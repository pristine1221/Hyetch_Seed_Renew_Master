package com.example.pristineseed.ui.inspection.planting;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pristineseed.R;

import java.util.List;

public class PlantingExpandableListAdapter extends BaseExpandableListAdapter {

    private  Context context;
    private  List<PlantingResponseModel> expandableListDetail;

    public PlantingExpandableListAdapter(Context context,
                                         List<PlantingResponseModel> expandableListDetail) {
        this.context = context;
        this.expandableListDetail = expandableListDetail;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        PlantingResponseModel.PlantingLineModel data = this.expandableListDetail.get(listPosition).il.get(expandedListPosition);
        return data;
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final PlantingResponseModel.PlantingLineModel expandedListText = (PlantingResponseModel.PlantingLineModel) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.expanded_planting_child_list, null);
        }
        TextView tv_lot_no = convertView.findViewById(R.id.tv_lot_no);
        TextView tv_status=convertView.findViewById(R.id.tv_status);
        TextView underlinetextview=convertView.findViewById(R.id.underlinetextview);
        underlinetextview.setPaintFlags(underlinetextview.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        TextView grname_varity_txt = convertView.findViewById(R.id.grname_varity);
        grname_varity_txt.setText(expandedListText.farmername+"  , "+expandedListText.farmeVillageName+" , "+expandedListText.villageaddresss+" , "+expandedListText.farmerContactNo);
        tv_lot_no.setText(expandedListText.production_lot_no );
        tv_status.setText("Pending");
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
       /* if(this.expandableListDetail.get(listPosition).il.size()==0){
         return 0;
        }*/
        return this.expandableListDetail.get(listPosition).il.size();
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
        PlantingResponseModel listTitle = (PlantingResponseModel) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.expandable_list_group_layout, null);
        }

        TextView tv_org_name = convertView.findViewById(R.id.tv_org_name);
        TextView tv_org_code = convertView.findViewById(R.id.tv_org_code_);
        TextView tv_planting_no = convertView.findViewById(R.id.tv_palnting_no);
        TextView tv_date = convertView.findViewById(R.id.tv_date);
        TextView tv_season_name = convertView.findViewById(R.id.tv_season_name);
        TextView tv_season_code = convertView.findViewById(R.id.tv_season_code);

     /*   tv_org_name.setText(listTitle.organizer_name);
        tv_org_code.setText(listTitle.organizer_code);
        tv_planting_no.setText(listTitle.code);
        tv_date.setText(listTitle.date);
        tv_season_name.setText(listTitle.season_name);
        tv_season_code.setText(listTitle.season_code);*/

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


}
