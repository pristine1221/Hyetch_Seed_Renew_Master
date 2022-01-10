package com.example.pristineseed.ui.adapter.side_panel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pristineseed.R;

import java.util.List;
import java.util.Map;

public class
CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expandableListTitle;
    private Map<String, List<String>> expandableListDetail;

    public CustomExpandableListAdapter(Context context, List<String> expandableListTitle,
                                       Map<String, List<String>> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item, null);
        }
        TextView expandedListTextView = (TextView) convertView
                .findViewById(R.id.expandedListItem);
        expandedListTextView.setText(expandedListText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.listTitle);
//        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        bindGroupImage(listTitle, (ImageView) convertView.findViewById(R.id.gropIcon));
        ImageView arrow_up_down = (ImageView) convertView.findViewById(R.id.arrow_up_down);
        //todo group arrow set
        if (isExpanded) {
            arrow_up_down.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_keyboard_arrow_up_black_24dp));
        } else {
            arrow_up_down.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_keyboard_arrow_down_black_24dp));
        }
        if (getChildrenCount(listPosition) <= 0) {
            arrow_up_down.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_exit_to_app_24));
        }
        return convertView;
    }

    /* access modifiers changed from: 0000 */
    public void bindGroupImage(String listTitle, ImageView gropIcon) {
        if (listTitle.contentEquals("Form")) {
            gropIcon.setImageDrawable(this.context.getResources().getDrawable(R.drawable.ic_menu_camera));
        } else if (listTitle.contentEquals("Gallery")) {
            gropIcon.setImageDrawable(this.context.getResources().getDrawable(R.drawable.ic_menu_gallery));
        } else if (listTitle.contentEquals("Without Gallery")) {
            gropIcon.setImageDrawable(this.context.getResources().getDrawable(R.drawable.ic_menu_slideshow));
        }
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