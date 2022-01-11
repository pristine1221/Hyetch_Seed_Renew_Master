package com.example.pristineseed.ui.vendor;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.pristineseed.DataBaseRepository.GeographicalRepo.HybridItemMasterDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.Hybrid_Item_Table;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.PlantingLineLotListDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.PlantingLineLotListTable;
import com.example.pristineseed.GlobalNotification.NetworkUtil;
import com.example.pristineseed.R;
import com.example.pristineseed.RoomDataBase.PristineDatabase;
import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.global.ApiRequestFailure;
import com.example.pristineseed.global.CustomCurrentDatePicker;
import com.example.pristineseed.global.CustomDatePicker;
import com.example.pristineseed.global.DateTimeUtilsCustome;
import com.example.pristineseed.global.LoadingDialog;
import com.example.pristineseed.model.LeaveApplicationModel.LeavePendingForApprovalModel;
import com.example.pristineseed.model.ResponseModel;
import com.example.pristineseed.model.YeildEstimateModel;
import com.example.pristineseed.model.home.OrderList;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.adapter.header_line.YieldEstimatsListAdapter;
import com.example.pristineseed.ui.adapter.item.PlantingProductionLotLineListAdapter;
import com.example.pristineseed.ui.adapter.item.PlantingProductionLotNumberAdapter;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YieldEstimatesFragment extends Fragment implements PlantingProductionLotNumberAdapter.OnItemClickListner {
    private List<PlantingLineLotListTable> plantingLineLotListTableList = null;
    private PlantingLineLotListTable plantingLineLotLstTable = null;
    private SessionManagement sessionManagement;
    private ListView listview;
    private List<YeildEstimateModel> yield_estimate_list = null;
    private String datValid = "";
    RecyclerView rv_search_lot_num_list;
    MaterialCardView frame_layout_lot_num_card_view;
    TextInputEditText ac_lot_no;
    TextView farmer_details ;
    LinearLayout lotdetail_section ;
    TextView fs_female ;
    TextView tv_fs_male ;
    TextView tv_sowing_m;
    TextView tv_sowing_f ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private Chip add_newItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_yield_estimate, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listview = view.findViewById(R.id.listview);
        sessionManagement = new SessionManagement(getActivity());
        add_newItem = view.findViewById(R.id.add_newItem);
        add_newItem.setOnClickListener(view1 -> {
            AddNewItemPopup("Insert", null);
        });
        getYieldEstimateList();

        listview.setOnItemClickListener((parent, view1, position, id) -> {
            if (yield_estimate_list != null && yield_estimate_list.size() > 0) {
                AddNewItemPopup("Update", yield_estimate_list.get(position));
            } else {
                return;
            }
        });
    }

    private void getYieldEstimateList() {
        if (NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
            LoadingDialog loadingDialog = new LoadingDialog();
            loadingDialog.showLoadingDialog(getActivity());
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            Call<List<YeildEstimateModel>> call = mAPIService.getYieldEstimateList(sessionManagement.getUserEmail());
            call.enqueue(new Callback<List<YeildEstimateModel>>() {
                @Override
                public void onResponse(Call<List<YeildEstimateModel>> call, Response<List<YeildEstimateModel>> response) {
                    try {
                        if (response.isSuccessful()) {
                            loadingDialog.hideDialog();
                            List<YeildEstimateModel> respoinseList = response.body();
                            if (respoinseList != null && respoinseList.size() > 0 && respoinseList.get(0).condition) {
                                yield_estimate_list = respoinseList;
                                YieldEstimatsListAdapter approvalAdapter = new YieldEstimatsListAdapter(getActivity(), respoinseList);
                                listview.setAdapter(approvalAdapter);
                                Toast.makeText(getActivity(), "Data fetch successful.", Toast.LENGTH_SHORT).show();
                            } else {
                                yield_estimate_list = respoinseList;
                                Toast.makeText(getActivity(), respoinseList.size() > 0 ? "result not found" : "result not found", Toast.LENGTH_LONG).show();
                                loadingDialog.hideDialog();
                            }
                        } else {
                            loadingDialog.hideDialog();
                            Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        loadingDialog.hideDialog();
                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "list", getActivity());
                    } finally {
                        loadingDialog.hideDialog();
                    }
                }

                @Override
                public void onFailure(Call<List<YeildEstimateModel>> call, Throwable t) {
                    loadingDialog.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "list", getActivity());
                }
            });

        } else {
            Toast.makeText(getActivity(), "Please wait for internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private PlantingLineLotListTable plantingLineLotListTable1 = null;

    public void AddNewItemPopup(String flag, YeildEstimateModel yield_estimate_model) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View popupView = inflater.inflate(R.layout.add_yield_estimates_popup, null);
        Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_DialogWhenLarge_NoActionBar);
        dialog.setContentView(popupView);
        dialog.getWindow().getAttributes().windowAnimations = R.style.AppTheme_FullScreenDialog;
        dialog.show();

        ac_lot_no = popupView.findViewById(R.id.ac_lot_no);
        rv_search_lot_num_list = popupView.findViewById(R.id.rv_search_lot_num_list);
        frame_layout_lot_num_card_view = popupView.findViewById(R.id.frame_layout_lot_num_card_view);
        TextInputLayout ac_lot_number_layout = popupView.findViewById(R.id.ac_lot_number_layout);
        LinearLayoutManager search_lot_num_manager;

        TextInputEditText ed_yield_estimate_date = popupView.findViewById(R.id.ed_yield_estimate_date);
        TextInputEditText ed_yield_estimate = popupView.findViewById(R.id.ed_yield_estimate);
        TextInputEditText ed_remarks = popupView.findViewById(R.id.ed_remarks);
        ImageView close_dilog_bt = popupView.findViewById(R.id.close_dilog_bt);
        Button filter_apply_bt = popupView.findViewById(R.id.filter_apply_bt);
        farmer_details = popupView.findViewById(R.id.farmer_details);
        lotdetail_section = popupView.findViewById(R.id.lotdetail_section);
        fs_female = popupView.findViewById(R.id.fs_female);
        tv_fs_male = popupView.findViewById(R.id.tv_fs_male);
        tv_sowing_m = popupView.findViewById(R.id.tv_sowing_m);
        tv_sowing_f = popupView.findViewById(R.id.tv_sowing_f);
        TextView yield_estimate_title = popupView.findViewById(R.id.yield_estimate_title);
        LinearLayout top_layout = popupView.findViewById(R.id.top_layout);
        RelativeLayout rl_custom_layout = popupView.findViewById(R.id.rl_custom_layout);

        search_lot_num_manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_search_lot_num_list.setLayoutManager(search_lot_num_manager);


        getPlantingLineListLot(rv_search_lot_num_list,"");

        top_layout.requestFocus();
        ed_remarks.requestFocus();

        top_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    top_layout.requestFocus();
                    ac_lot_no.clearFocus();
                    ed_remarks.requestFocus();
                    ed_remarks.clearFocus();
                    frame_layout_lot_num_card_view.setVisibility(View.GONE);
                }
                return true;
            }
        });

        rl_custom_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    rl_custom_layout.requestFocus();
                }
                return true;
            }
        });

        ed_yield_estimate_date.setOnTouchListener((v, event) -> {
            new CustomDatePicker(getActivity()).showDatePickerDialog(ed_yield_estimate_date);
            return true;
        });


      /*  ac_lot_no.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus && ac_lot_no.isCursorVisible()){
                    frame_layout_lot_num_card_view.setVisibility(View.VISIBLE);
                    ac_lot_number_layout.setStartIconDrawable(null);
                    getPlantingLineListLot(rv_search_lot_num_list,"");
                }else {
                    if(!ac_lot_no.getText().toString().equalsIgnoreCase("")){
                        ac_lot_number_layout.setStartIconDrawable(null);
                    }else {
                        ac_lot_number_layout.setStartIconDrawable(R.drawable.ic_baseline_search_24);
                        frame_layout_lot_num_card_view.setVisibility(View.GONE);
                    }
                }
            }
        });*/

            ac_lot_no.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    ac_lot_no.setSelection(s.toString().length());
                    if (!s.toString().equalsIgnoreCase("") && ac_lot_no.isCursorVisible()) {
                        frame_layout_lot_num_card_view.setVisibility(View.VISIBLE);
                        ac_lot_number_layout.setStartIconDrawable(null);
                        getPlantingLineListLot(rv_search_lot_num_list, s.toString());
                    } else {
                        ac_lot_number_layout.setStartIconDrawable(R.drawable.ic_baseline_search_24);
                        frame_layout_lot_num_card_view.setVisibility(View.GONE);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });

        PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getActivity());
        try {
            PlantingLineLotListDao plantingLineLotListDao = pristineDatabase.plantingLineLotListDao();
//            plantingLineLotListTableList = plantingLineLotListDao.fetchAllData();
            if (yield_estimate_model.lot_no != null) {
                plantingLineLotListTable1 = plantingLineLotListDao.getAllDetail(yield_estimate_model.lot_no);
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            pristineDatabase.close();
            pristineDatabase.destroyInstance();
            /*if (plantingLineLotListTableList != null && plantingLineLotListTableList.size() > 0) {
                PlantingProductionLotLineListAdapter plantingProductionLotLineListAdapter=new PlantingProductionLotLineListAdapter(getActivity(),R.layout.android_item_view,plantingLineLotListTableList);
                ac_lot_no.setAdapter(plantingProductionLotLineListAdapter);
                PlantingProductionLotNumberAdapter productionLotNumberAdapter = new PlantingProductionLotNumberAdapter(getActivity(),plantingLineLotListTableList);
                rv_search_lot_num_list.setAdapter(productionLotNumberAdapter);
            }*/
        }


       /* ac_lot_no.setOnItemClickListener((parent, view, position, id) -> {

            PlantingLineLotListTable plantingLineLotListTable = plantingLineLotListTableList.get(position);

            if (plantingLineLotListTable != null) {
                lotdetail_section.setVisibility(View.VISIBLE);
                getAliasNameOfParentMaleFemale(plantingLineLotListTable.getParent_Female(), plantingLineLotListTable.getParent_Male(), fs_female, tv_fs_male);
                if (plantingLineLotListTable.getGrower_Land_Owner_Name() != null && !plantingLineLotListTable.getGrower_Land_Owner_Name().equalsIgnoreCase("")) {
                    farmer_details.setText(plantingLineLotListTable.getGrower_Land_Owner_Name());

                } else {
                    farmer_details.setText("");
                }
                if (plantingLineLotListTable.getSowing_Date_Female() != null && !plantingLineLotListTable.getSowing_Date_Female().equalsIgnoreCase("")) {
                    tv_sowing_f.setText(DateTimeUtilsCustome.getDate_Time2(plantingLineLotListTable.getSowing_Date_Female()));
                } else {
                    tv_sowing_f.setText("");
                }

                if (plantingLineLotListTable.getSowing_Date_Male() != null && !plantingLineLotListTable.getSowing_Date_Male().equalsIgnoreCase("")) {
                    tv_sowing_m.setText(DateTimeUtilsCustome.getDate_Time2(plantingLineLotListTable.getSowing_Date_Male()));
                } else {
                    tv_sowing_m.setText("");
                }
            } else {
                farmer_details.setText("");
                fs_female.setText("");
                tv_fs_male.setText("");
            }

        });*/

        if (flag.equalsIgnoreCase("Insert")) {
            yield_estimate_title.setText("Add Yield Esitmate");
            insertYieldEstimate(dialog, ac_lot_no, ed_yield_estimate_date, ed_yield_estimate, ed_remarks, filter_apply_bt);
        }
        else if (flag.equalsIgnoreCase("Update")) {
            filter_apply_bt.setText("View Deatils");
            filter_apply_bt.setBackgroundColor(getResources().getColor(R.color.transparent_color));
            filter_apply_bt.setTextColor(getResources().getColor(R.color.black));
            yield_estimate_title.setText("View Yield Estimate Details");
            lotdetail_section.setVisibility(View.VISIBLE);
            ac_lot_no.setText(yield_estimate_model.lot_no);
            ac_lot_no.setEnabled(false);
            ac_lot_no.setSelected(false);
            frame_layout_lot_num_card_view.setVisibility(View.GONE);
            ed_yield_estimate_date.setText(yield_estimate_model.yield_estimate_date);
            ed_yield_estimate_date.setEnabled(false);
            ed_yield_estimate.setText(yield_estimate_model.estimated_yield);
            ed_yield_estimate.setEnabled(false);
            ed_remarks.setText(yield_estimate_model.remarks);
            ed_remarks.setEnabled(false);
            if (plantingLineLotListTable1 != null) {

                getAliasNameOfParentMaleFemale(plantingLineLotListTable1.getParent_Female(), plantingLineLotListTable1.getParent_Male(), fs_female, tv_fs_male);
                if (plantingLineLotListTable1.getGrower_Land_Owner_Name() != null && !plantingLineLotListTable1.getGrower_Land_Owner_Name().equalsIgnoreCase("")) {
                    farmer_details.setText(plantingLineLotListTable1.getGrower_Land_Owner_Name());

                } else {
                    farmer_details.setText("");
                }
                if (plantingLineLotListTable1.getSowing_Date_Female() != null && !plantingLineLotListTable1.getSowing_Date_Female().equalsIgnoreCase("")) {
                    tv_sowing_f.setText(DateTimeUtilsCustome.getDate_Time2(plantingLineLotListTable1.getSowing_Date_Female()));
                } else {
                    tv_sowing_f.setText("");
                }

                if (plantingLineLotListTable1.getSowing_Date_Male() != null && !plantingLineLotListTable1.getSowing_Date_Male().equalsIgnoreCase("")) {
                    tv_sowing_m.setText(DateTimeUtilsCustome.getDate_Time2(plantingLineLotListTable1.getSowing_Date_Male()));
                } else {
                    tv_sowing_m.setText("");
                }
            } else {
                farmer_details.setText("");
                fs_female.setText("");
                tv_fs_male.setText("");
            }

        }
        close_dilog_bt.setOnClickListener(view -> {
            dialog.dismiss();
        });
    }


    private void insertYieldEstimate(Dialog dialog, TextInputEditText ac_lot_no, TextInputEditText ed_yield_estimate_date, TextInputEditText ed_yield_estimate, TextInputEditText ed_remarks, Button filter_apply_bt) {
        filter_apply_bt.setOnClickListener(view -> {
            if (ac_lot_no.getText().toString().trim().equalsIgnoreCase("")) {
                Toast.makeText(getActivity(), "Please select lot no.", Toast.LENGTH_SHORT).show();
            } else if (ed_yield_estimate_date.getText().toString().trim().equalsIgnoreCase("")) {
                Toast.makeText(getActivity(), "Please select yield estimate date", Toast.LENGTH_SHORT).show();
            } else if (ed_yield_estimate.getText().toString().trim().equalsIgnoreCase("")) {
                Toast.makeText(getActivity(), "Please enter yield estimate", Toast.LENGTH_SHORT).show();
            } else {
                if (!yield_estimate_list.get(0).condition && yield_estimate_list.get(0).lot_no == null) {
                    insertYieldEstimate(dialog, ac_lot_no, ed_yield_estimate_date, ed_yield_estimate, ed_remarks);
                } else {
                    if (yield_estimate_list != null && yield_estimate_list.size() > 0 && yield_estimate_list.get(0).lot_no != null) {
                        for (int i = 0; i < yield_estimate_list.size(); i++) {
                            String pass_date = ed_yield_estimate_date.getText().toString().trim();
                            String split_date = DateTimeUtilsCustome.splitDateInYYYMMDD(pass_date);
                            if (!dateValid(split_date, yield_estimate_list.get(i).yield_estimate_date)) {
                                datValid = "0";
                                Toast.makeText(getActivity(), "Yield Estimate Date already exists.Please select different Yield date.", Toast.LENGTH_SHORT).show();
                                break;
                            } else {
                                datValid = "1";
                                continue;

                            }
                        }
                    }
                }
                if (datValid.equalsIgnoreCase("1")) {
                    insertYieldEstimate(dialog, ac_lot_no, ed_yield_estimate_date, ed_yield_estimate, ed_remarks);
                }
            }
        });
    }

    private void insertYieldEstimate(Dialog dialog, TextInputEditText ac_lot_no, TextInputEditText ed_yield_estimate_date, TextInputEditText ed_yield_estimate, TextInputEditText ed_remarks) {
        if (NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
            LoadingDialog loadingDialog = new LoadingDialog();
            loadingDialog.showLoadingDialog(getActivity());
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("lot_no", ac_lot_no.getText().toString().trim());
            jsonObject.addProperty("yield_estimate_date", DateTimeUtilsCustome.splitDateInYYYMMDD(ed_yield_estimate_date.getText().toString().trim()));
            jsonObject.addProperty("estimated_yield", ed_yield_estimate.getText().toString().trim());
            jsonObject.addProperty("remarks", ed_remarks.getText().toString().trim());
            jsonObject.addProperty("created_by", sessionManagement.getUserEmail());

            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            Call<List<ResponseModel>> call = mAPIService.insertYieldEsitimate(jsonObject);
            call.enqueue(new Callback<List<ResponseModel>>() {
                @Override
                public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                    try {
                        if (response.isSuccessful()) {
                            loadingDialog.hideDialog();
                            List<ResponseModel> respoinseList = response.body();
                            if (respoinseList != null && respoinseList.size() > 0 && respoinseList.get(0).condition) {
                                getYieldEstimateList();
                                Toast.makeText(getActivity(), respoinseList.get(0).message, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            } else {
                                Toast.makeText(getActivity(), respoinseList.size() > 0 ? respoinseList.get(0).message : "result not found", Toast.LENGTH_LONG).show();
                                loadingDialog.hideDialog();
                            }
                        } else {
                            loadingDialog.hideDialog();
                            Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        loadingDialog.hideDialog();
                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "list", getActivity());
                    } finally {
                        loadingDialog.hideDialog();
                    }
                }

                @Override
                public void onFailure(Call<List<ResponseModel>> call, Throwable t) {
                    loadingDialog.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "list", getActivity());
                }
            });

        } else {
            Toast.makeText(getActivity(), "Please wait for internet connection", Toast.LENGTH_SHORT).show();
        }
    }


    private boolean dateValid(String pastdate, String current_date) {
        try {
            if (current_date != null) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date datec = formatter.parse(pastdate);
                Date date2 = formatter.parse(current_date);

                if (datec.compareTo(date2) == 0) {
                    return false;
                } else {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private void getAliasNameOfParentMaleFemale(String parent_female_code, String parent_male_code, TextView tv_parent_female, TextView tv_parent_male) {
        Hybrid_Item_Table plantingLineLotListTable = null;
        Hybrid_Item_Table plantingLineLotListTable1 = null;
        PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getActivity());
        try {
            HybridItemMasterDao hybridItemMasterDao = pristineDatabase.hybridItemMasterDao();
            plantingLineLotListTable = hybridItemMasterDao.getAliasNameParentMale(parent_male_code);
            plantingLineLotListTable1 = hybridItemMasterDao.getAliasNameParentFemale(parent_female_code);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pristineDatabase.close();
            pristineDatabase.destroyInstance();
            if (plantingLineLotListTable1 != null) {
                tv_parent_female.setText(plantingLineLotListTable1.getNo());
            } else {
                tv_parent_female.setText("");
            }
            if (plantingLineLotListTable != null) {
                tv_parent_male.setText(plantingLineLotListTable.getNo());
            } else {
                tv_parent_male.setText("");
            }
        }

    }

    private void getPlantingLineListLot(RecyclerView rv_search_lot_num_list, String filter) {
        PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getActivity());
        try {
            PlantingLineLotListDao plantingLineLotListDao = pristineDatabase.plantingLineLotListDao();
            plantingLineLotListTableList = plantingLineLotListDao.fetchFilter10AllData("%"+filter+"%");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bindProductionLotDataWithAdapter(rv_search_lot_num_list);
        }
    }

    private void bindProductionLotDataWithAdapter(RecyclerView rv_search_lot_num_list) {
        if (plantingLineLotListTableList != null && plantingLineLotListTableList.size() > 0) {
            PlantingProductionLotNumberAdapter productionLotNumberAdapter = new PlantingProductionLotNumberAdapter(getActivity(), plantingLineLotListTableList);
            rv_search_lot_num_list.setAdapter(productionLotNumberAdapter);
            productionLotNumberAdapter.setClickListner(this);
        }else{
            PlantingProductionLotNumberAdapter productionLotNumberAdapter = new PlantingProductionLotNumberAdapter(getActivity(), new ArrayList<>());
            rv_search_lot_num_list.setAdapter(productionLotNumberAdapter);
            productionLotNumberAdapter.setClickListner(this);
            frame_layout_lot_num_card_view.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClick(int pos) {
        PlantingLineLotListTable plantingLineLotListTable = plantingLineLotListTableList.get(pos);

        PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getActivity());

        if (plantingLineLotListTable != null && plantingLineLotListTableList.size()>0) {

            PlantingLineLotListDao plantingLineLotListDao = pristineDatabase.plantingLineLotListDao();
            plantingLineLotLstTable = plantingLineLotListDao.getSowingAcre(plantingLineLotListTableList.get(0).getProduction_Lot_No());
            ac_lot_no.setText(plantingLineLotListTableList.get(pos).getProduction_Lot_No());
            frame_layout_lot_num_card_view.setVisibility(View.GONE);

            lotdetail_section.setVisibility(View.VISIBLE);

            getAliasNameOfParentMaleFemale(plantingLineLotListTable.getParent_Female(), plantingLineLotListTable.getParent_Male(), fs_female, tv_fs_male);
            if (plantingLineLotListTable.getGrower_Land_Owner_Name() != null && !plantingLineLotListTable.getGrower_Land_Owner_Name().equalsIgnoreCase("")) {
                farmer_details.setText(plantingLineLotListTable.getGrower_Land_Owner_Name());

            } else {
                farmer_details.setText("");
            }
            if (plantingLineLotListTable.getSowing_Date_Female() != null && !plantingLineLotListTable.getSowing_Date_Female().equalsIgnoreCase("")) {
                tv_sowing_f.setText(DateTimeUtilsCustome.getDate_Time2(plantingLineLotListTable.getSowing_Date_Female()));
            } else {
                tv_sowing_f.setText("");
            }

            if (plantingLineLotListTable.getSowing_Date_Male() != null && !plantingLineLotListTable.getSowing_Date_Male().equalsIgnoreCase("")) {
                tv_sowing_m.setText(DateTimeUtilsCustome.getDate_Time2(plantingLineLotListTable.getSowing_Date_Male()));
            } else {
                tv_sowing_m.setText("");
            }
        } else {
            farmer_details.setText("");
            fs_female.setText("");
            tv_fs_male.setText("");
            frame_layout_lot_num_card_view.setVisibility(View.GONE);
        }
    }
}