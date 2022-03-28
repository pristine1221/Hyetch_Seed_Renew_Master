package com.example.pristineseed.ui.reports;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.pristineseed.R;
import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.model.reportModel.ZoneOrDistributorWiseDetailsModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class ZoneOrDistributorwiseOrderDetailsFragment extends Fragment {
    private SessionManagement sessionManagement;
    private TextInputLayout distributor_name_text_input_layout, zone_text_input_layout, order_start_date_input_layout, order_end_date_input_layout;
    private TextInputEditText et_order_distributor_name, et_order_zone, et_start_date, et_end_date;
    private MaterialButton submit_order_list, clear_order_list;
    private MaterialCardView frame_order_distributor_list_layout, frame_order_zone_list_layout;
    private RecyclerView rv_order_distributor_list, rv_order_zone_list, order_recycler_view_list;
    private LinearLayoutManager order_distributor_layout_manager, order_zone_layout_manager, order_details_list_layout_manager;
    private FloatingActionButton filter_download_floating_btn;
    private View view_weight;
    private ScrollView parent_layout;
    private ProgressBar loading_item;
    private RelativeLayout search_reports_item_layout;
    private ImageView iv_refresh_details;
    private List<ZoneOrDistributorWiseDetailsModel> zoneOrDistributorWiseModelList_gl = new ArrayList<>();
    private ZoneOrDistributorWiseDetailsModel zoneOrDistributorWiseModel = null;
    private ProgressBar order_progressBar;
    private TextView order_progress_percentage_count, order_text_percent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_zone_or_distributorwise_order_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManagement = new SessionManagement(getActivity());
    }

    //todo initialization...
    private void initView(View view) {
        distributor_name_text_input_layout = view.findViewById(R.id.distributor_name_text_input_layout);
        zone_text_input_layout = view.findViewById(R.id.zone_text_input_layout);
        order_start_date_input_layout = view.findViewById(R.id.order_start_date_input_layout);
        order_end_date_input_layout = view.findViewById(R.id.order_end_date_input_layout);
        et_order_distributor_name = view.findViewById(R.id.et_order_distributor_name);
        et_order_zone = view.findViewById(R.id.et_order_zone);
        et_start_date = view.findViewById(R.id.et_start_date);
        et_end_date = view.findViewById(R.id.et_end_date);
        submit_order_list = view.findViewById(R.id.submit_order_list);
        clear_order_list = view.findViewById(R.id.clear_order_list);
        frame_order_distributor_list_layout = view.findViewById(R.id.frame_order_distributor_list_layout);
        frame_order_zone_list_layout = view.findViewById(R.id.frame_order_zone_list_layout);
        rv_order_distributor_list = view.findViewById(R.id.rv_order_distributor_list);
        rv_order_zone_list = view.findViewById(R.id.rv_order_zone_list);
        order_recycler_view_list = view.findViewById(R.id.order_recycler_view_list);
        filter_download_floating_btn = view.findViewById(R.id.filter_download_floating_btn);
        loading_item = view.findViewById(R.id.loading_item);
        view_weight = view.findViewById(R.id.view_weight);
        parent_layout = view.findViewById(R.id.parent_relative_layout);
        iv_refresh_details = view.findViewById(R.id.iv_refresh_details);
        search_reports_item_layout = view.findViewById(R.id.search_reports_item_layout);
        //todo.. download
        order_progressBar = view.findViewById(R.id.order_progressBar);
        order_progress_percentage_count = view.findViewById(R.id.order_progress_percentage_count);
        order_text_percent = view.findViewById(R.id.order_text_percent);

        //todo set organizer layout manager...
        order_distributor_layout_manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_order_distributor_list.setLayoutManager(order_distributor_layout_manager);

        //todo set hybrid layout manager...
        order_zone_layout_manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_order_zone_list.setLayoutManager(order_zone_layout_manager);

        //todo set dispatch layout manager...
        order_details_list_layout_manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        order_recycler_view_list.setLayoutManager(order_details_list_layout_manager);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}