package com.example.pristineseed.ui.adapter.order_book;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pristineseed.R;
import com.example.pristineseed.model.BookingOrder.MarketingIndentModel;
import com.example.pristineseed.model.PlantingModel.PlantingHeaderModel;

import java.util.List;

public class MarketingIndentAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<MarketingIndentModel> expandableListDetail;

    public MarketingIndentAdapter(Context context,
                                             List<MarketingIndentModel> expandableListDetail) {
        this.context = context;
        this.expandableListDetail = expandableListDetail;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        MarketingIndentModel.MarketingIndentLine data = this.expandableListDetail.get(listPosition).marketing_indent_line.get(expandedListPosition);
        return data; }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final MarketingIndentModel.MarketingIndentLine expandedListText = (MarketingIndentModel.MarketingIndentLine) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.marketing_indent_child_layout, null);
        }
        TextView tv_book_no = convertView.findViewById(R.id.tv_book_no);
        TextView tv_crop = convertView.findViewById(R.id.tv_crop);
        TextView tv_booking_qty = convertView.findViewById(R.id.tv_booking_qty);

        TextView tv_variety=convertView.findViewById(R.id.tv_variety);
        // underlinetextview.setPaintFlags(underlinetextview.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        // TextView grname_varity_txt = convertView.findViewById(R.id.grname_varity);
        tv_book_no.setText(expandedListText.booking_indent_no);
        tv_crop.setText(expandedListText.crop_code);
        tv_booking_qty.setText(expandedListText.booking_qty);
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
       /* if(this.expandableListDetail.get(listPosition).il.size()==0){
         return 0;
        }*/
        return this.expandableListDetail.get(listPosition).marketing_indent_line
                .size();
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
        MarketingIndentModel listTitle = (MarketingIndentModel) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.marketing_indent_header_layout, null);
        }

        TextView tv_mkt_no = convertView.findViewById(R.id.tv_mkt_no);
        TextView tv_date = convertView.findViewById(R.id.tv_date);
        TextView tv_season = convertView.findViewById(R.id.tv_season);
        TextView tv_name = convertView.findViewById(R.id.tv_name);
        TextView tv_sale_type = convertView.findViewById(R.id.tv_sale_type);
        TextView tv_season_code = convertView.findViewById(R.id.tv_season_code);
        TextView tv_status = convertView.findViewById(R.id.tv_status);

        tv_mkt_no.setText(listTitle.marketing_indent_no);
        tv_season.setText(listTitle.season_name);
        tv_date.setText(listTitle.date);
        tv_name.setText(listTitle.name);
        tv_sale_type.setText(listTitle.sales_type);

        switch (listTitle.status){
            case "Pending":
                tv_status.setTextColor(context.getResources().getColor(R.color.pending_color));
                tv_status.setText(listTitle.status);
                break;
            case "Sent For Approval":
                tv_status.setTextColor(context.getResources().getColor(R.color.colorAccent));
                tv_status.setText(listTitle.status);
                break;
            case "Approve":
                tv_status.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
                tv_status.setText(listTitle.status);
                break;
            case"Reject":
                tv_status.setTextColor(context.getResources().getColor(R.color.my_app_error_color));
                tv_status.setText(listTitle.status);
                break;
        }


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