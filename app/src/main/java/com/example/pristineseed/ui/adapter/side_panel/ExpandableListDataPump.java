package com.example.pristineseed.ui.adapter.side_panel;

import android.content.Context;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExpandableListDataPump {
    public static Map<String, List<String>> getData(Context context) {
        Map<String, List<String>> expandableListDetail = new LinkedHashMap<String, List<String>>();
        try {
            List<String> menulist = new ArrayList<String>();
            menulist.add("Header Line");
            menulist.add("List");
            menulist.add("Menu Change");
            menulist.add("Image Capture");
            expandableListDetail.put("Form", menulist);
            return expandableListDetail;
        } catch (Exception e) {
            return expandableListDetail;
        }
    }
}