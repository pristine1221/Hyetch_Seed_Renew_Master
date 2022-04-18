package com.example.pristineseed.ui.bootmMainScreen.ui.OrderBookingRenew.marketing_indent;

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
import android.widget.Toast;

import com.example.pristineseed.R;
import com.example.pristineseed.model.BookingOrder.MarketingIndentApprovalModel;
import com.example.pristineseed.ui.adapter.order_book.MarketingIndentLinesDetailsAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewMktIndentHeaderLinesFragment extends Fragment {
    private String marketing_indent_no = "";
    private RecyclerView rv_view_mkt_lines;
    private LinearLayoutManager layoutManager;
    public List<MarketingIndentApprovalModel.Marketing_Approvalindent_line> marketing_approvalindent_lines_gl = new ArrayList<>();
    private ImageView iv_close_ui;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // todo Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_mkt_indent_header_lines, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

        Bundle bundle = getArguments();
        if (bundle != null){
            marketing_indent_no = bundle.getString("indent_no");
            marketing_approvalindent_lines_gl = (List<MarketingIndentApprovalModel.Marketing_Approvalindent_line>)getArguments().getSerializable("lines");

            //todo bind lines detail adapter..
            MarketingIndentLinesDetailsAdapter linesDetailsAdapter = new MarketingIndentLinesDetailsAdapter(getActivity(), marketing_approvalindent_lines_gl);
            rv_view_mkt_lines.setAdapter(linesDetailsAdapter);
        }

        //todo close fragment....
        iv_close_ui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStackImmediate();
            }
        });
    }

    private void initView(View view) {
        rv_view_mkt_lines = view.findViewById(R.id.rv_view_mkt_lines);
        iv_close_ui = view.findViewById(R.id.iv_close_ui);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_view_mkt_lines.setLayoutManager(layoutManager);

    }
}