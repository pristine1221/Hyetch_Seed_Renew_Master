package com.example.pristineseed.ui.inspection.planting.issue_order;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pristineseed.R;
import com.example.pristineseed.common_data.CommonData;
import com.example.pristineseed.model.PlantingModel.PlantingFsio_bsio_model;
import com.example.pristineseed.model.PlantingModel.PlantingLineDocNoDetails;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.adapter.ItemAdapter;
import com.example.pristineseed.ui.adapter.PlantingAdapter.IssueOredrFsioBsioAdapter;
import com.example.pristineseed.ui.adapter.PlantingAdapter.PlantingFsioBsioAdapter;
import com.example.pristineseed.ui.adapter.PlantingAdapter.PlantingLineDocNoDetailLinesAdapter;
import com.example.pristineseed.ui.adapter.item.FarmerListAdapter;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IssueOrderFsioBsioFragment extends Fragment implements IssueOredrFsioBsioAdapter.OnItemClickListener{

    private AutoCompleteTextView ac_doc_type;
    private TextInputEditText et_search;
    private TextInputLayout et_search_item_layout;
    MaterialProgressBar content_loading;
    RecyclerView recycler;
    List<PlantingFsio_bsio_model> plantingFsioBsioModelList;
    IssueOredrFsioBsioAdapter plantingFsioBsioAdapter;

    //todo declare for plantingLine doc details......................................
    TextView tv_doc_no,tv_order_no,tv_customer_posting_grp,sell_cutomer_no,sell_customer_name,sell_customer_address,sell_customer_city,
            doc_sub_type,tv_seed_type,tv_seed_name,tv_seed_no,tv_crop_code,tv_business_type,tv_location,tv_location_code,
            tv_state_name,tv_territory,tv_reagion,tv_district,tv_zone,tv_land_arc,tv_order_date,tv_posting_date,tv_user_id,tv_message;
    private ListView listview;
    private List<PlantingLineDocNoDetails.Line> plantingDocDetailLineList ;
    public IssueOrderFsioBsioFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_issue_order_fsio_bsio, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        et_search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    et_search_item_layout.setStartIconDrawable(null);
                } else {
                    if (!ac_doc_type.getText().toString().trim().equalsIgnoreCase("")) {
                        et_search_item_layout.setStartIconDrawable(null);
                    } else {
                        et_search_item_layout.setStartIconDrawable(R.drawable.ic_baseline_search_24);
                    }
                }
            }
        });
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equalsIgnoreCase("") && !ac_doc_type.getText().toString().equalsIgnoreCase("")) {
                    String doc_sub_type=ac_doc_type.getText().toString();
                    getIssueOrderList(doc_sub_type,s.toString());
                } else {
                    //et_search.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initView(View view) {
        plantingFsioBsioModelList=new ArrayList<>();
        plantingDocDetailLineList = new ArrayList<>();
        ac_doc_type=view.findViewById(R.id.ac_doc_type);
        et_search=view.findViewById(R.id.et_search);
        recycler=view.findViewById(R.id.recycler);
        content_loading=view.findViewById(R.id.content_loading);
        et_search_item_layout=view.findViewById(R.id.et_search_item_layout);
        List<String> doc_sub_type = Arrays.asList(CommonData.doc_sub_type);
        ItemAdapter pest_adapter = new ItemAdapter(getActivity(), R.layout.android_item_view, doc_sub_type);
        ac_doc_type.setAdapter(pest_adapter);

        ac_doc_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!et_search.getText().toString().equalsIgnoreCase("")){
                    et_search.setText("");
                    plantingFsioBsioModelList.clear();
                    recycler.setAdapter(null);
                }
            }
        });
    }

    private void getIssueOrderList(String doc_type,String filter_doc_no) {
        content_loading.setVisibility(View.VISIBLE);
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<List<PlantingFsio_bsio_model>> call = mAPIService.getIssueOrderFsioBsio(doc_type,filter_doc_no);
        call.enqueue(new Callback<List<PlantingFsio_bsio_model>>() {
            @Override
            public void onResponse(Call<List<PlantingFsio_bsio_model>> call, Response<List<PlantingFsio_bsio_model>> response) {
                try {
                    if(response.isSuccessful()){
                        content_loading.setVisibility(View.GONE);
                        List<PlantingFsio_bsio_model> modelList=response.body();
                        if(modelList.size()>0 && modelList.get(0).condition){
                            plantingFsioBsioModelList=modelList;
                            setIssueOrderFsioBsioAdapter(plantingFsioBsioModelList);
                        }
                    }
                }
                catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<List<PlantingFsio_bsio_model>> call, Throwable t) {

            }

        });
    }

    private void setIssueOrderFsioBsioAdapter(List<PlantingFsio_bsio_model> plantingFsioBsioModelList) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(layoutManager);
        plantingFsioBsioAdapter = new IssueOredrFsioBsioAdapter(getActivity(), plantingFsioBsioModelList,this);
        plantingFsioBsioAdapter.setOnItemClickListener(this);
        recycler.setAdapter(plantingFsioBsioAdapter);

    }

    //todo for fsio/bsio show details........................
    private void showFsioBsioCodeDetails(PlantingLineDocNoDetails plantingLineDocNoDetails) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View popup_view = inflater.inflate(R.layout.fsio_bsio_doc_code_details_popup, null);
        Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_DialogWhenLarge_NoActionBar);
        dialog.setContentView(popup_view);
        dialog.getWindow().getAttributes().windowAnimations = R.style.AppTheme_FullScreenDialog;
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.show();
        ImageView close_dilog_bt = popup_view.findViewById(R.id.close_dilog_bt);

        close_dilog_bt.setOnClickListener(v->{
            dialog.dismiss();
        });

        //todo initialization.......................
        listview=popup_view.findViewById(R.id.doc_detalis_listView);

        tv_doc_no=popup_view.findViewById(R.id.tv_doc_no);
        tv_order_no=popup_view.findViewById(R.id.tv_order_no);
        tv_customer_posting_grp=popup_view.findViewById(R.id.tv_customer_posting_grp);
        sell_cutomer_no=popup_view.findViewById(R.id.sell_cutomer_no);
        sell_customer_name=popup_view.findViewById(R.id.sell_customer_name);
        sell_customer_address=popup_view.findViewById(R.id.sell_customer_address);
        sell_customer_city=popup_view.findViewById(R.id.sell_customer_city);
        doc_sub_type=popup_view.findViewById(R.id.doc_sub_type);
        tv_seed_type=popup_view.findViewById(R.id.tv_seed_type);
        tv_seed_name=popup_view.findViewById(R.id.tv_seed_name);
        tv_seed_no=popup_view.findViewById(R.id.tv_seed_no);
        tv_crop_code=popup_view.findViewById(R.id.tv_crop_code);
        tv_business_type=popup_view.findViewById(R.id.tv_business_type);
        tv_location=popup_view.findViewById(R.id.tv_location);
        tv_location_code=popup_view.findViewById(R.id.tv_location_code);
        tv_state_name=popup_view.findViewById(R.id.tv_state_name);
        tv_territory=popup_view.findViewById(R.id.tv_territory);
        tv_reagion=popup_view.findViewById(R.id.tv_reagion);
        tv_district=popup_view.findViewById(R.id.tv_district);
        tv_zone=popup_view.findViewById(R.id.tv_zone);
        tv_land_arc=popup_view.findViewById(R.id.tv_land_arc);
        tv_order_date=popup_view.findViewById(R.id.tv_order_date);
        tv_posting_date=popup_view.findViewById(R.id.tv_posting_date);
        tv_user_id=popup_view.findViewById(R.id.tv_user_id);
        tv_message=popup_view.findViewById(R.id.tv_message);

        bindPlantingDocHeaderDetilas(plantingLineDocNoDetails);
        setDocLinesadapter(plantingLineDocNoDetails);
    }

    private void bindPlantingDocHeaderDetilas(PlantingLineDocNoDetails plantingLineDocNoDetails){
        //todo bind details.................................
        tv_doc_no.setText(plantingLineDocNoDetails.getNo());
        tv_order_no.setText(plantingLineDocNoDetails.getOrderNo());
        tv_customer_posting_grp.setText(plantingLineDocNoDetails.getCustomerPostingGroup());
        sell_cutomer_no.setText(plantingLineDocNoDetails.getSellToCustomerNo());
        sell_customer_name.setText(plantingLineDocNoDetails.getSellToCustomerName());
        sell_customer_address.setText(plantingLineDocNoDetails.getSellToAddress());
        sell_customer_city.setText(plantingLineDocNoDetails.getSellToCity());
        doc_sub_type.setText(plantingLineDocNoDetails.getDocumentSubType());
        tv_seed_type.setText(plantingLineDocNoDetails.getChildSeedType());
        tv_seed_name.setText(plantingLineDocNoDetails.getChildSeedName());
        tv_seed_no.setText(plantingLineDocNoDetails.getChildSeed());
        tv_crop_code.setText(plantingLineDocNoDetails.getCropCode());
        tv_business_type.setText(plantingLineDocNoDetails.getBussinessType());
        tv_location.setText(plantingLineDocNoDetails.getLocationName());
        tv_location_code.setText(plantingLineDocNoDetails.getLocationCode());
        tv_state_name.setText(plantingLineDocNoDetails.getStateName());
        tv_territory.setText(plantingLineDocNoDetails.getTerritoryName());
        tv_reagion.setText(plantingLineDocNoDetails.getRegionName());
        tv_district.setText(plantingLineDocNoDetails.getDistrictName());
        tv_zone.setText(plantingLineDocNoDetails.getZoneCode());
        tv_land_arc.setText(plantingLineDocNoDetails.getLandInAcres());
        tv_order_date.setText(plantingLineDocNoDetails.getOrderDate());
        tv_posting_date.setText(plantingLineDocNoDetails.getPostingDate());
        tv_user_id.setText(plantingLineDocNoDetails.getUserID());
        tv_message.setText(plantingLineDocNoDetails.getMessage());
    }

    private void setDocLinesadapter(PlantingLineDocNoDetails plantingLineDocNoDetails) {
        plantingDocDetailLineList=plantingLineDocNoDetails.getLines();
        PlantingLineDocNoDetailLinesAdapter pladapter=new PlantingLineDocNoDetailLinesAdapter(getActivity(),R.layout.planting_line_doc_no_details_lines_layout,plantingDocDetailLineList);
        listview.setAdapter(pladapter);
    }


    @Override
    public void onItemClick(int position) {
        et_search.setText(plantingFsioBsioModelList.get(position).No);

        if(et_search.getText().toString()!=null) {

            NetworkInterface networkInterface=ApiUtils.getPristineAPIService();
            Call<PlantingLineDocNoDetails> call=networkInterface.getPlantingLineDocDetails(et_search.getText().toString());
            call.enqueue(new Callback<PlantingLineDocNoDetails>() {
                @Override
                public void onResponse(Call<PlantingLineDocNoDetails> call, Response<PlantingLineDocNoDetails> response) {
                    if(response.isSuccessful()){
                        PlantingLineDocNoDetails plantingLineDocNoDetails=response.body();
                        Log.d("ggg",response.body().toString());
                        showFsioBsioCodeDetails(plantingLineDocNoDetails);
                    }
                    else {
                        MDToast.makeText(getActivity(),response.message(),MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();
                    }
                }

                @Override
                public void onFailure(Call<PlantingLineDocNoDetails> call, Throwable t) {
                    MDToast.makeText(getActivity(),t.getMessage(),MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();
                }
            });

        }
    }
}