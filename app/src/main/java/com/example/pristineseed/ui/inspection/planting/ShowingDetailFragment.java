package com.example.pristineseed.ui.inspection.planting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pristineseed.R;
import com.example.pristineseed.global.StaticMethods;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;

public class ShowingDetailFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_showing_detail, container, false);
    }

    Chip chip_add_planting;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        expandableListView = view.findViewById(R.id.schedule_inspection_expnd_list);
        chip_add_planting = view.findViewById(R.id.chip_add_planting);
        expandiblelistbind();
        chip_add_planting.setOnClickListener(v -> {
            //StaticMethods.loadFragmentsWithBackStack(getActivity(), new CreatePlantingFragment(), "Create Planting");
        });

    }

    public int selectedchildPosition = 0;


    ExpandableListView expandableListView;

    List<PlantingResponseModel> scheduleInspectionModelList = new ArrayList<>();

    public void expandiblelistbind() {
        scheduleInspectionModelList.clear();
       int k = 1;
        for (int i = 0; i < 20; i++) {
            PlantingResponseModel parent = new PlantingResponseModel();
            parent.planting_number = "Plant/00" + i;
            parent.Season_code = "SES" + i;
            parent.Season_name = "Season-Name " + i;
            parent.Date = "12-12-2020";
            parent.FSIO_Details = "FSIO-" + i;
            parent.Organizer = "Organizer Test (ORG-" + i + "), 8506988092";
            parent.Hybrid = "Hybrid name" + i;
            parent.il = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                PlantingResponseModel.PlantingLineModel linesobject = parent.new PlantingLineModel();
                linesobject.planting_number = parent.planting_number;
                linesobject.farmername = "Test" + j;
                linesobject.farmerContactNo = "8506988092";
                linesobject.farmeVillageName = "village name" + j;
                linesobject.villageaddresss = "taluka , zone , city , state , pincode";
                linesobject.expected_Yield = "20";
                linesobject.production_lot_no = "LOT-" + k;
                linesobject.Area_as_measured = "2" + j;
                parent.il.add(linesobject);
                k++;
            }
            scheduleInspectionModelList.add(parent);
        }
        PlantingExpandableListAdapter expandableListAdapter = new PlantingExpandableListAdapter(this.getActivity(), scheduleInspectionModelList);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(groupPosition -> {
            for (int g = 0; g < expandableListAdapter.getGroupCount(); g++) {
                if (g != groupPosition) {
                    expandableListView.collapseGroup(g);
                }
            }
        });
        expandableListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            // Toast.makeText(getApplicationContext(),expandableListTitle.get(groupPosition) + " -> "+ expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();
            int index = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition, childPosition));
            parent.setItemChecked(index, true);
            // groupName = scheduleInspectionModelList.get(groupPosition).arrival_plan_no;
            selectedchildPosition = index;
          //  CreatePlantingFragment fragment = new CreatePlantingFragment();
            Bundle bundle = new Bundle();
            bundle.putString("planting_number", scheduleInspectionModelList.get(groupPosition).planting_number);
           // fragment.setArguments(bundle);
            //StaticMethods.loadFragmentsWithBackStack(getActivity(), fragment, "Create Planting");
            return false;
        });
    }
}