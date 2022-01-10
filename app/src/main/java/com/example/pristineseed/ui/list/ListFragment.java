package com.example.pristineseed.ui.list;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.pristineseed.R;
import com.example.pristineseed.global.CustomDatePicker;
import com.example.pristineseed.global.CustomTimePicker;
import com.example.pristineseed.model.home.OrderList;
import com.example.pristineseed.model.item.StateModel;
import com.example.pristineseed.ui.adapter.header_line.ListAdapter;
import com.example.pristineseed.ui.adapter.item.StateAdapter;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    SwipeRefreshLayout swipe_refresh_layout;
    Chip chip_filter_icon, add_newItem;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView listview = view.findViewById(R.id.listview);
        swipe_refresh_layout = view.findViewById(R.id.swipe_refresh_layout);
        chip_filter_icon = view.findViewById(R.id.chip_filter_icon);
        add_newItem = view.findViewById(R.id.add_newItem);
        add_newItem.setOnClickListener(view1 -> {
            AddNewItemPopup();
        });
        List<OrderList> listitem = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            listitem.add(new OrderList("Titile" + i, "SubTitile" + i, "12/01/2020"));
        }
        ListAdapter approvalAdapter = new ListAdapter(getActivity(), listitem);
        listview.setAdapter(approvalAdapter);

        //todo go to infinite list
        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int lastInScreen = firstVisibleItem + visibleItemCount;
                if ((lastInScreen == totalItemCount)) {
                    for (int i = totalItemCount; i < totalItemCount + 20; i++) {
                        listitem.add(new OrderList("Titile" + i, "SubTitile" + i, "12/01/2020"));
                    }
                    approvalAdapter.notifyDataSetChanged();
                }
            }
        });

        swipe_refresh_layout.setOnRefreshListener(() -> {
            // To keep animation for 4 seconds
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Stop animation (This will be after 3 seconds)
                    listitem.clear();
                    for (int i = 0; i < 20; i++) {
                        listitem.add(new OrderList("Titile" + i, "SubTitile" + i, "12/01/2020"));
                    }
                    approvalAdapter.notifyDataSetChanged();
                    swipe_refresh_layout.setRefreshing(false);
                }
            }, 3000); // Delay in millis
        });
        swipe_refresh_layout.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light)
        );

        chip_filter_icon.setOnClickListener(view1 -> {
            FilterPopup();
        });
    }

    public void FilterPopup() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View popupView = inflater.inflate(R.layout.list_filter_popup, null);
        Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_DialogWhenLarge_NoActionBar);
        dialog.setContentView(popupView);
        dialog.getWindow().getAttributes().windowAnimations = R.style.AppTheme_FullScreenDialog;
        dialog.show();
        ImageView close_dilog_bt = popupView.findViewById(R.id.close_dilog_bt);
        Button filter_apply_bt = popupView.findViewById(R.id.filter_apply_bt);
        filter_apply_bt.setOnClickListener(view -> {
            dialog.dismiss();
        });
        close_dilog_bt.setOnClickListener(view -> {
            dialog.dismiss();
        });
    }

    public void AddNewItemPopup() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View popupView = inflater.inflate(R.layout.add_item_popup, null);
        Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_DialogWhenLarge_NoActionBar);
        dialog.setContentView(popupView);
        dialog.getWindow().getAttributes().windowAnimations = R.style.AppTheme_FullScreenDialog;
        dialog.show();
        List<StateModel> state_list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            StateModel model = new StateModel();
            model.setState_code("id_" + i);
            model.setState_name("Name_" + i);
            state_list.add(model);
        }
        AutoCompleteTextView dropdown_state = popupView.findViewById(R.id.dropdown_state);
        StateAdapter stateAdapter = new StateAdapter(getActivity(), R.layout.drop_down_textview, state_list);
        dropdown_state.setAdapter(stateAdapter);
        dropdown_state.setOnItemClickListener((adapterView, view1, i, l) -> {
            //selectedExpenseType = expenseTypeList.get(i);
        });
        AutoCompleteTextView dropdown_city = popupView.findViewById(R.id.dropdown_city);
      /*  CityAdapter cityAdapter = new CityAdapter(getActivity(), R.layout.drop_down_textview, state_list);
        dropdown_city.setAdapter(cityAdapter);*/
        ImageView close_dilog_bt = popupView.findViewById(R.id.close_dilog_bt);
        Button filter_apply_bt = popupView.findViewById(R.id.filter_apply_bt);
        filter_apply_bt.setOnClickListener(view -> {
            dialog.dismiss();
        });

        TextInputEditText tv_datePicker = popupView.findViewById(R.id.tv_datePicker);
        tv_datePicker.setOnTouchListener((view, motionEvent) -> {
            //todo date formate shoud be 5-12-2013
            new CustomDatePicker(getActivity()).showDatePickerDialog(tv_datePicker);
            return true;
        });
        TextInputEditText tv_TimePicker = popupView.findViewById(R.id.tv_TimePicker);
        tv_TimePicker.setOnTouchListener((view, motionEvent) -> {
            //todo pass time formate shoud be HH:mm AM
            new CustomTimePicker(getActivity()).showDialog(tv_TimePicker);
            return true;
        });
        close_dilog_bt.setOnClickListener(view -> {
            dialog.dismiss();
        });
    }
}