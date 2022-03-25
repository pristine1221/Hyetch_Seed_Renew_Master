package com.example.pristineseed.ui.reports;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.pristineseed.R;
import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.model.reportModel.ReportSeedDispatchViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class ZoneOrDistributorwiseMktIndentDetailsFragment extends Fragment {

    private SessionManagement sessionManagement;
    private TextInputLayout organser_name_text_input_layout, hybrid_name_text_input_layout, sdn_date_input_layout;
    private TextInputEditText et_sdn_organizer_name, et_sdn_hybrid_name, et_sdn_date;
    private MaterialButton submit_sdn_list, clear_sdn_list;
    private MaterialCardView frame_sdn_organiser_list_layout, frame_sdn_hybrid_list_layout;
    private RecyclerView rv_sdn_organiser_list, rv_sdn_hybrid_list, sdn_recycler_view_list;
    private LinearLayoutManager sdn_organiser_layout_manager, sdn_hybrid_layout_manager, sdn_dispatch_list_layout_manager;
    private FloatingActionButton filter_download_floating_btn;
    private View view_weight;
    private LinearLayout parent_layout;
    private ProgressBar loading_item;
    private RelativeLayout search_reports_item_layout;
    private ImageView iv_refresh_details;
    private List<ReportSeedDispatchViewModel> reportSeedDispatchViewModels_gl = new ArrayList<>();
    private ReportSeedDispatchViewModel reportSeedDispatchModel = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_zone_or_distributorwise_mkt_indent_details, container, false);
    }
}