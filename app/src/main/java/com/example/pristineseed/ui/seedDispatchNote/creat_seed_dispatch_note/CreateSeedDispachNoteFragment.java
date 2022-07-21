package com.example.pristineseed.ui.seedDispatchNote.creat_seed_dispatch_note;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pristineseed.BackgroundTask;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.PlantingLineLotListDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.PlantingLineLotListTable;
import com.example.pristineseed.DataBaseRepository.seed_dispatch_note.SeedDispatchHeaderDao;
import com.example.pristineseed.GlobalNotification.NetworkUtil;
import com.example.pristineseed.R;
import com.example.pristineseed.RoomDataBase.PristineDatabase;
import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.common_data.CommonData;
import com.example.pristineseed.global.ApiRequestFailure;
import com.example.pristineseed.global.CustomDatePicker;
import com.example.pristineseed.global.DateTimeUtilsCustome;
import com.example.pristineseed.global.LoadingDialog;
import com.example.pristineseed.model.GeoSetupModel.DispatchFarmerModel;
import com.example.pristineseed.model.GeoSetupModel.UserLocationModel;
import com.example.pristineseed.model.PlantingModel.SeasonMasterModel;
import com.example.pristineseed.model.item.OrganizerModel;
import com.example.pristineseed.model.item.PlantingProdcutionLotModel;
import com.example.pristineseed.model.seed_dispatch_note.DispatchResponseModel;
import com.example.pristineseed.model.seed_dispatch_note.SeedDispatchHeaderModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.adapter.ItemAdapter;
import com.example.pristineseed.ui.adapter.PlantingAdapter.SeasonMasterAdapter;
import com.example.pristineseed.ui.adapter.item.FarmerListAdapter;
import com.example.pristineseed.ui.adapter.item.LocationMasterAdapter;
import com.example.pristineseed.ui.adapter.item.OrganizerAdapter;
import com.example.pristineseed.ui.adapter.item.PlantingProductionLotLineListAdapter;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateSeedDispachNoteFragment extends Fragment implements OrganizerAdapter.OnItemClickListner {

    private String seed_dipatch_no = "";
    private ImageView back_button_go_topreviousPage;
    private TextInputEditText edit_date, edit_Supervisor, edit_Transporter, edit_Truck_Number, ed_camp_at, ed_remarks;
    private Chip chip_create_planting_header, complete_header, chip_add_line_production_lot_number, delete_header_chip;
    private LinearLayout dispacth_detail_header_section;
    private RelativeLayout header_create_section;
    private CardView line_cardview;
    private TextView tv_header_dispatch_no, tv_superviser, tv_date, tv_From_Location, tv_To_Location, tv_Transporter, tv_Truck_Number, tv_org_name, tv_org_code, tv_doc_type;
    private ListView listview;
    private SessionManagement sessionManagement;
    private AutoCompleteTextView ac_fromLocation, ac_tolocation, ac_season_code, ac_doc_doc_type;
    private UserLocationModel fromLocation = null;
    private UserLocationModel toLocation = null;
    private List<SeasonMasterModel> seasonMasterTableList = null;

    private List<UserLocationModel> fromLcationmasterList = null;
    private List<UserLocationModel> tolocationmasterList = null;
    private List<OrganizerModel.Data> organizerMasterTableList = null;
    private SeasonMasterModel seasonMasterTable = null;
    private OrganizerModel.Data organizer_master_table = null;
    private String view_flag = "";
    //  private List<Hybrid_Item_Table> hybrid_item_tableList=null;
    // private List<Hybrid_Item_Table> foundation_item_list=null;
    private List<SeedDispatchHeaderModel.SeedDispatchLineModel> dispatchNoteLineTableList = new ArrayList<>();
    private SeedDispatchHeaderModel seedDispatchHeader_list = null;

    // private List<SeedDispatchNoteHeaderTable> seedDispatchNoteHeaderTableList = new ArrayList<>();
    private SeedDispatchLineAdapter seed_dispatch_line_adapter;
    private List<PlantingLineLotListTable> fetchProductionLineLotList = null;
    // private List<PlantingLineLotListTable> fetchProductionLineLotListFoundation=null;
    private TextInputEditText ed_reference_no,ac_orgnizer_code;
    private MaterialProgressBar content_loading;
    private String alias_code = "";
    private MaterialCardView frame_layout_org_list;
    private ProgressBar search_loading_item;
    private RecyclerView lv_cust_dist_list;
    private TextInputLayout search_input_layout;

    //  private  Hybrid_Item_Table hybrid_item_table=null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments() != null) {
            seed_dipatch_no = getArguments().getString("SDN_Number", "");
            view_flag = getArguments().getString("flag", "");
            seedDispatchHeader_list = new Gson().fromJson(getArguments().getString("Seed_Dispatch_note_details"), SeedDispatchHeaderModel.class);

        }
        return inflater.inflate(R.layout.fragment_create_seed_dispachnote, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

        ItemAdapter doc_adapter = new ItemAdapter(getActivity(), R.layout.android_item_view, Arrays.asList(CommonData.doc_type));
        ac_doc_doc_type.setAdapter(doc_adapter);
        chip_add_line_production_lot_number.setOnClickListener(v -> {
            AddLineNewItemPopup();//seedDispatchHeader_list.document_type, seedDispatchHeader_list.organizer_code
        });
        back_button_go_topreviousPage.setOnClickListener(v -> {
            getParentFragmentManager().popBackStack();
        });

        edit_date.setOnTouchListener((v, event) -> {
            new CustomDatePicker(getActivity()).showDatePickerDialog(edit_date);
            return true;
        });

        delete_header_chip.setOnClickListener(v -> {
            if (dispatchNoteLineTableList != null && dispatchNoteLineTableList.size() > 0) {
                Toast.makeText(getActivity(), "You can't delete header as lines exist ! ", Toast.LENGTH_SHORT).show();
                return;
            } else if (seedDispatchHeader_list != null &&
                    seedDispatchHeader_list.dispatch_no != null && !seedDispatchHeader_list.dispatch_no.equalsIgnoreCase("")) {
                new MaterialAlertDialogBuilder(getActivity())
                        .setTitle("Header" + "(" + seedDispatchHeader_list.dispatch_no + ")")
                        .setMessage("Do you want to delete this header ?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteHeader(seedDispatchHeader_list.dispatch_no);
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("CANCEL", null)
                        .show();
            } else {
            }
        });


        listview.setOnItemLongClickListener((parent, view1, position, id) -> {
            if (dispatchNoteLineTableList != null && dispatchNoteLineTableList.get(position).dispatch_no != null
                    && !dispatchNoteLineTableList.get(position).dispatch_no.equalsIgnoreCase("")
                    && seedDispatchHeader_list.status > 0) {

                Toast.makeText(getActivity(), "You can't delete the line after completing Dispatch!", Toast.LENGTH_SHORT).show();
                return false;
            } else if (dispatchNoteLineTableList != null && dispatchNoteLineTableList.get(position).dispatch_no != null
                    && !dispatchNoteLineTableList.get(position).dispatch_no.equalsIgnoreCase("")) {
                new MaterialAlertDialogBuilder(getActivity())
                        .setTitle("Dispatch Line" + "(" + dispatchNoteLineTableList.get(0).dispatch_no + ")")
                        .setMessage("Do you want to delete this line ?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteDispatchLine(dispatchNoteLineTableList.get(position).dispatch_no, position);
                            }
                        })
                        .setNegativeButton("CANCEL", null)
                        .show();
            }
            return true;
        });
        complete_header.setOnClickListener(v -> {
            if (dispatchNoteLineTableList != null && dispatchNoteLineTableList.size() > 0 && dispatchNoteLineTableList.get(0).line_no != null) {
                new MaterialAlertDialogBuilder(getActivity())
                        .setTitle("Dispatch no." + "(" + seedDispatchHeader_list.dispatch_no + ")")
                        .setMessage("Do you really want to complete this dispatch note ?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                completeDispatchNote();
                            }
                        })
                        .setNegativeButton("CANCEL", null)
                        .show();
            } else {
                Toast.makeText(getActivity(), "You can't complete Seed dispatch note as line doesn't exist", Toast.LENGTH_SHORT).show();
            }
        });
        chip_create_planting_header.setOnClickListener(v -> {
            if (ac_doc_doc_type.getText().toString().trim().equalsIgnoreCase("")) {
                Toast.makeText(getActivity(), "Please select doc type", Toast.LENGTH_SHORT).show();
            } else if (organizer_master_table.No == null || organizer_master_table.No.equalsIgnoreCase("")) {
                Toast.makeText(getActivity(), "Please select Organiser.", Toast.LENGTH_SHORT).show();
            } else if (edit_date.getText().toString().trim().equalsIgnoreCase("")) {
                Toast.makeText(getActivity(), "Please select date", Toast.LENGTH_SHORT).show();
            } else {
                SeedDispatchHeaderModel seedDispatchHeaderModel = new SeedDispatchHeaderModel();
                seedDispatchHeaderModel.date = DateTimeUtilsCustome.splitDateInYYYMMDD(edit_date.getText().toString().trim());
                seedDispatchHeaderModel.from_location = fromLocation.location_code;
                seedDispatchHeaderModel.to_location = toLocation.location_code;
                seedDispatchHeaderModel.supervisor = edit_Supervisor.getText().toString().trim();
                seedDispatchHeaderModel.transporter = edit_Transporter.getText().toString().trim();
                seedDispatchHeaderModel.organizer_code = organizer_master_table.No;
                seedDispatchHeaderModel.organizer_name = organizer_master_table.Name != null && !organizer_master_table.Name.equalsIgnoreCase("") ? organizer_master_table.Name : "";
                seedDispatchHeaderModel.truck_number = edit_Truck_Number.getText().toString().trim();
                seedDispatchHeaderModel.season_code = seasonMasterTable.season_Code != null && !seasonMasterTable.season_Code.equalsIgnoreCase("") ? seasonMasterTable.season_Code : "";
                seedDispatchHeaderModel.camp_at = ed_camp_at.getText().toString().trim();
                seedDispatchHeaderModel.remarks = ed_remarks.getText().toString().trim();
                seedDispatchHeaderModel.created_by = sessionManagement.getUserEmail();
                seedDispatchHeaderModel.document_type = ac_doc_doc_type.getText().toString().trim();
                seedDispatchHeaderModel.refrence_no = ed_reference_no.getText().toString().trim();
                //boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getActivity());
               //if (isNetwork) {
                    String jsonString = new Gson().toJson(seedDispatchHeaderModel);
                    JsonObject asJsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
                    NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                    Call<List<SeedDispatchHeaderModel>> call = mAPIService.insertDispatchheader(asJsonObject);
                    LoadingDialog progressDialogLoading = new LoadingDialog();
                    progressDialogLoading.showLoadingDialog(getActivity());
                    //  ArrayList<SeedDispatchHeaderModel> dispatch_header_list = new ArrayList<>();
                    call.enqueue(new Callback<List<SeedDispatchHeaderModel>>() {
                        @Override
                        public void onResponse(Call<List<SeedDispatchHeaderModel>> call, Response<List<SeedDispatchHeaderModel>> response) {
                            try {
                                if (response.isSuccessful()) {
                                    progressDialogLoading.hideDialog();
                                    List<SeedDispatchHeaderModel> insert_header_list = response.body();
                                    if (insert_header_list != null && insert_header_list.size() > 0 && insert_header_list.get(0).condition) {
                                        seedDispatchHeaderModel.send_to_server_header = 0;
                                        seedDispatchHeaderModel.dispatch_no = insert_header_list.get(0).dispatch_no;
                                        seedDispatchHeaderModel.created_on = insert_header_list.get(0).created_on;
                                        //dispatch_header_list.add(seedDispatchHeaderModel);
                                        seedDispatchHeader_list = insert_header_list.get(0);
                                        bindDispatchHeaderData();
                                        Toast.makeText(getActivity(), insert_header_list.get(0).message, Toast.LENGTH_SHORT).show();
                                    } else {
                                        progressDialogLoading.hideDialog();
                                        Toast.makeText(getActivity(), insert_header_list.size() > 0 ? insert_header_list.get(0).message : "Api not respoding.", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    progressDialogLoading.hideDialog();
                                    Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                progressDialogLoading.hideDialog();
                                Log.e("exception database", e.getMessage() + "cause");
                                ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "insert_dispatch_header", getActivity());
                            } finally {
                                progressDialogLoading.hideDialog();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<SeedDispatchHeaderModel>> call, Throwable t) {
                            progressDialogLoading.hideDialog();
                            ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "insert_dispatch_header", getActivity());
                        }
                    });
                //} else {
                /*    ArrayList<SeedDispatchHeaderModel> dispatch_header_list = new ArrayList<>();
                    seedDispatchHeaderModel.dispatch_no = "0";
                    seedDispatchHeaderModel.send_to_server_header = 1;
                    dispatch_header_list.add(seedDispatchHeaderModel);
                    bindDispatchHeaderData(new ArrayList<>());*/

                    Toast.makeText(getActivity(), "Please wait for internet connection. ", Toast.LENGTH_SHORT).show();
                //}
            }
        });
    }

    private void getDispatchHeaderDataFromLocal() {
        if (seedDispatchHeader_list != null) {
            try {
                if (view_flag.equalsIgnoreCase("view_flag")) {
                    if (seedDispatchHeader_list != null) {
                        if (seedDispatchHeader_list.status > 0) {
                            header_create_section.setVisibility(View.GONE);
                            dispacth_detail_header_section.setVisibility(View.VISIBLE);
                            line_cardview.setVisibility(View.VISIBLE);
                            chip_add_line_production_lot_number.setVisibility(View.GONE);
                            delete_header_chip.setVisibility(View.GONE);
                            complete_header.setVisibility(View.GONE);
                        } else {
                            header_create_section.setVisibility(View.GONE);
                            line_cardview.setVisibility(View.VISIBLE);
                            chip_add_line_production_lot_number.setVisibility(View.VISIBLE);
                            delete_header_chip.setVisibility(View.VISIBLE);
                            dispacth_detail_header_section.setVisibility(View.VISIBLE);
                            complete_header.setVisibility(View.VISIBLE);
                        }
                        setHeaderDetailData();
                        dispatchNoteLineTableList = seedDispatchHeader_list.dispatch_line;
                        if (dispatchNoteLineTableList != null && dispatchNoteLineTableList.size() > 0 && dispatchNoteLineTableList.get(0).line_no != null) {
                            seed_dispatch_line_adapter = new SeedDispatchLineAdapter(getActivity(), seedDispatchHeader_list.dispatch_line);
                            listview.setAdapter(seed_dispatch_line_adapter);
                        } else {
                            listview.setAdapter(null);
                        }
                    } else {
                        header_create_section.setVisibility(View.GONE);
                        dispacth_detail_header_section.setVisibility(View.VISIBLE);
                        delete_header_chip.setVisibility(View.VISIBLE);
                        line_cardview.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (seedDispatchHeader_list != null) {
                        if (seedDispatchHeader_list.status > 0) {
                            header_create_section.setVisibility(View.GONE);
                            dispacth_detail_header_section.setVisibility(View.VISIBLE);
                            line_cardview.setVisibility(View.VISIBLE);
                            chip_add_line_production_lot_number.setVisibility(View.GONE);
                            delete_header_chip.setVisibility(View.GONE);
                            complete_header.setVisibility(View.GONE);
                        } else {
                            header_create_section.setVisibility(View.GONE);
                            line_cardview.setVisibility(View.VISIBLE);
                            chip_add_line_production_lot_number.setVisibility(View.VISIBLE);
                            delete_header_chip.setVisibility(View.VISIBLE);
                            dispacth_detail_header_section.setVisibility(View.VISIBLE);
                            complete_header.setVisibility(View.VISIBLE);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void bindDispatchHeaderData() {
      /*  PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getActivity());
        SeedDispatchHeaderDao dispatchHeaderDao = pristineDatabase.seedDispatchHeaderDao();
        try {
            if(seedDispatchHeaderModelList.size()>0) {
                tv_header_dispatch_no.setText("(" + seedDispatchHeaderModelList.get(0).dispatch_no + ")");
                SeedDispatchNoteHeaderTable seedDispatchNoteHeaderTable = new SeedDispatchNoteHeaderTable();
                seedDispatchNoteHeaderTable.setDispatch_no(seedDispatchHeaderModelList.get(0).dispatch_no);
                seedDispatchNoteHeaderTable.setDate(seedDispatchHeaderModelList.get(0).date);
                seedDispatchNoteHeaderTable.setFrom_location(seedDispatchHeaderModelList.get(0).from_location);
                seedDispatchNoteHeaderTable.setTo_location(seedDispatchHeaderModelList.get(0).to_location);
                seedDispatchNoteHeaderTable.setSupervisor(seedDispatchHeaderModelList.get(0).supervisor);
                seedDispatchNoteHeaderTable.setSeason_code(seedDispatchHeaderModelList.get(0).season_code);
                seedDispatchNoteHeaderTable.setTransporter(seedDispatchHeaderModelList.get(0).transporter);
                seedDispatchNoteHeaderTable.setOrganizer_code(seedDispatchHeaderModelList.get(0).organizer_code);
                seedDispatchNoteHeaderTable.setOrganizer_name(seedDispatchHeaderModelList.get(0).organizer_name);
                seedDispatchNoteHeaderTable.setTruck_number(seedDispatchHeaderModelList.get(0).truck_number);
                seedDispatchNoteHeaderTable.setCamp_at(seedDispatchHeaderModelList.get(0).camp_at);
                seedDispatchNoteHeaderTable.setRemarks(seedDispatchHeaderModelList.get(0).remarks);
                seedDispatchNoteHeaderTable.setCreated_by(seedDispatchHeaderModelList.get(0).created_by);
                seedDispatchNoteHeaderTable.setDocument_type(seedDispatchHeaderModelList.get(0).document_type);
                seedDispatchNoteHeaderTable.setRefrence_no(seedDispatchHeaderModelList.get(0).refrence_no);


                if (dispatchHeaderDao.isDataExist(seedDispatchHeaderModelList.get(0).dispatch_no)) {
                    dispatchHeaderDao.update(seedDispatchNoteHeaderTable);
                } else {
                    dispatchHeaderDao.insert(seedDispatchNoteHeaderTable);
                }
                seedDispatchNoteHeaderTableList = dispatchHeaderDao.getHeaderDispactchNo(seedDispatchNoteHeaderTable.getDispatch_no());
            }
            Log.e("dispatch_header_list", new Gson().toJson(seedDispatchNoteHeaderTableList));*/
        try {
            if (seedDispatchHeader_list != null) {
                header_create_section.setVisibility(View.GONE);
                dispacth_detail_header_section.setVisibility(View.VISIBLE);
                delete_header_chip.setVisibility(View.VISIBLE);
                line_cardview.setVisibility(View.VISIBLE);
                complete_header.setVisibility(View.VISIBLE);
                setHeaderDetailData();
            } else {
                header_create_section.setVisibility(View.VISIBLE);
                complete_header.setVisibility(View.GONE);
                line_cardview.setVisibility(View.GONE);
                dispacth_detail_header_section.setVisibility(View.GONE);
                delete_header_chip.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setHeaderDetailData() {
        try {
            tv_header_dispatch_no.setText("(" + seedDispatchHeader_list.dispatch_no + ")");
            tv_superviser.setText(seedDispatchHeader_list.supervisor);
            tv_date.setText(seedDispatchHeader_list.date);
            tv_From_Location.setText(seedDispatchHeader_list.from_location);
            tv_To_Location.setText(seedDispatchHeader_list.to_location);
            tv_Transporter.setText(seedDispatchHeader_list.transporter);
            tv_Truck_Number.setText(seedDispatchHeader_list.truck_number);
            tv_doc_type.setText(seedDispatchHeader_list.document_type);
            tv_org_code.setText(seedDispatchHeader_list.organizer_code);
            tv_org_name.setText(seedDispatchHeader_list.organizer_name);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initView(View view) {
        sessionManagement = new SessionManagement(getActivity());
        back_button_go_topreviousPage = view.findViewById(R.id.back_button_go_topreviousPage);
        edit_date = view.findViewById(R.id.edit_date);
        chip_create_planting_header = view.findViewById(R.id.chip_create_planting_header);
        header_create_section = view.findViewById(R.id.header_create_section);
        dispacth_detail_header_section = view.findViewById(R.id.dispatch_detail_header_section);
        line_cardview = view.findViewById(R.id.line_cardview);
        tv_header_dispatch_no = view.findViewById(R.id.tv_header_dispatch_no);
        complete_header = view.findViewById(R.id.complete_header);
        chip_add_line_production_lot_number = view.findViewById(R.id.chip_add_line_production_lot_number);
        ac_fromLocation = view.findViewById(R.id.ac_from_location);
        ac_tolocation = view.findViewById(R.id.ac_to_location);
        ac_orgnizer_code = view.findViewById(R.id.ac_organise);
        ac_season_code = view.findViewById(R.id.ac_season);
        listview = view.findViewById(R.id.listview);

        edit_Supervisor = view.findViewById(R.id.edit_Supervisor);
        edit_Transporter = view.findViewById(R.id.edit_Transporter);
        edit_Truck_Number = view.findViewById(R.id.edit_Truck_Number);
        ed_camp_at = view.findViewById(R.id.ed_camp_at);
        ed_remarks = view.findViewById(R.id.ed_remarks);
        ed_reference_no=view.findViewById(R.id.ed_reference_no);
        ac_doc_doc_type = view.findViewById(R.id.ac_doc_doc_type);
        content_loading = view.findViewById(R.id.content_loading);
        //tv_header show fields------

        tv_superviser = view.findViewById(R.id.tv_superviser);
        tv_date = view.findViewById(R.id.tv_date);
        tv_From_Location = view.findViewById(R.id.tv_From_Location);
        tv_To_Location = view.findViewById(R.id.tv_To_Location);
        tv_Transporter = view.findViewById(R.id.tv_Transporter);
        tv_Truck_Number = view.findViewById(R.id.tv_Truck_Number);
        delete_header_chip = view.findViewById(R.id.delete_header_chip);
        tv_org_name = view.findViewById(R.id.tv_org_name);
        tv_org_code = view.findViewById(R.id.tv_org_code);
        tv_doc_type = view.findViewById(R.id.tv_doc_type);
        frame_layout_org_list=view.findViewById(R.id.frame_layout_org_list);
        search_loading_item=view.findViewById(R.id.loading_item);
        lv_cust_dist_list=view.findViewById(R.id.lv_cust_dist_list);
        search_input_layout = view.findViewById(R.id.search_input_layout);
        LinearLayoutManager layoutManager= new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        lv_cust_dist_list.setLayoutManager(layoutManager);
        getDispatchHeaderDataFromLocal();
        getUserLocation();
        getSeasonMaster();


        ac_orgnizer_code.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus){
                search_input_layout.setEndIconDrawable(null);
            }else {
                if(!ac_orgnizer_code.getText().toString().trim().equals("")){
                    search_input_layout.setEndIconDrawable(null);
                }
                else {
                    search_input_layout.setEndIconDrawable(R.drawable.ic_baseline_search_24);
                }
            }
        });


        ac_orgnizer_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ac_orgnizer_code.setSelection(s.toString().length());
                if(!ac_orgnizer_code.getText().toString().equalsIgnoreCase("")) {
                    frame_layout_org_list.setVisibility(View.VISIBLE);
                    getOrganizerList(s.toString());
                }
                else {
                    frame_layout_org_list.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        ac_fromLocation.setOnItemClickListener((parent, view1, position, id) -> {
            if (fromLcationmasterList != null && fromLcationmasterList.size() > 0) {
                fromLocation = fromLcationmasterList.get(position);
                ac_fromLocation.setText(fromLocation.location_name);
            }
        });

        ac_tolocation.setOnItemClickListener((parent, view1, position, id) -> {
            if (tolocationmasterList != null && tolocationmasterList.size() > 0) {
                toLocation = tolocationmasterList.get(position);
                ac_tolocation.setText(toLocation.location_name);
            }
        });

        ac_season_code.setOnItemClickListener((parent, view1, position, id) -> {
            seasonMasterTable = seasonMasterTableList.get(position);
            if (seasonMasterTable != null) {
                ac_season_code.setText(seasonMasterTable.description);
            } else {
                ac_season_code.setText("");
            }
        });
    }

    private void getUserLocation() {
        content_loading.setVisibility(View.VISIBLE);
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<List<UserLocationModel>> call = mAPIService.getUserLocation(sessionManagement.getUserEmail());
        call.enqueue(new Callback<List<UserLocationModel>>() {
            @Override
            public void onResponse(Call<List<UserLocationModel>> call, Response<List<UserLocationModel>> response) {
                try {
                    if (response.isSuccessful()) {
                        List<UserLocationModel> user_locastion_masterlist = response.body();
                        if (user_locastion_masterlist != null && user_locastion_masterlist.size() > 0) {
                            content_loading.setVisibility(View.GONE);
                            fromLcationmasterList = user_locastion_masterlist;
                            tolocationmasterList = user_locastion_masterlist;
                            LocationMasterAdapter locationMasterAdapter = new LocationMasterAdapter(getActivity(), R.layout.android_item_view, fromLcationmasterList);
                            ac_fromLocation.setAdapter(locationMasterAdapter);
                            LocationMasterAdapter toLocationAdapter = new LocationMasterAdapter(getActivity(), R.layout.android_item_view, tolocationmasterList);
                            ac_tolocation.setAdapter(toLocationAdapter);
                        } else {
                            ac_tolocation.setAdapter(null);
                        }
                    } else {
                        ac_tolocation.setAdapter(null);
                        content_loading.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    content_loading.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "user_location_master", getActivity());
                }
            }

            @Override
            public void onFailure(Call<List<UserLocationModel>> call, Throwable t) {
                content_loading.setVisibility(View.GONE);
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "user_location_master", getActivity());
            }
        });
    }


    private void getSeasonMaster() {
        content_loading.setVisibility(View.VISIBLE);
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<List<SeasonMasterModel>> call = mAPIService.getSeasonMaster();
        call.enqueue(new Callback<List<SeasonMasterModel>>() {
            @Override
            public void onResponse(Call<List<SeasonMasterModel>> call, Response<List<SeasonMasterModel>> response) {
                try {
                    if (response.isSuccessful()) {
                        content_loading.setVisibility(View.GONE);
                        List<SeasonMasterModel> templantingList_season = response.body();
                        if (templantingList_season != null && templantingList_season.size() > 0) {
                            seasonMasterTableList = templantingList_season;
                            SeasonMasterAdapter locationMasterAdapter = new SeasonMasterAdapter(getActivity(), R.layout.android_item_view, seasonMasterTableList);
                            ac_season_code.setAdapter(locationMasterAdapter);
                        } else {
                            content_loading.setVisibility(View.GONE);
                            ac_season_code.setText("");
                            ac_season_code.setAdapter(null);
                            Toast.makeText(getActivity(), templantingList_season != null && templantingList_season.size() > 0 ? "Season Record not found !" : "Api not respoding.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        ac_season_code.setAdapter(null);
                        content_loading.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    content_loading.setVisibility(View.GONE);
                    Log.e("exception database", e.getMessage() + "cause");
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "get_season", getActivity());
                } finally {
                    content_loading.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<SeasonMasterModel>> call, Throwable t) {
                content_loading.setVisibility(View.GONE);
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "get_season", getActivity());
            }
        });
    }

    private void getOrganizerList(String filter_key) {
        search_loading_item.setVisibility(View.VISIBLE);
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<OrganizerModel> call = mAPIService.getOrgnizerData("O_D",filter_key);
        call.enqueue(new Callback<OrganizerModel>() {
            @Override
            public void onResponse(Call<OrganizerModel> call, Response<OrganizerModel> response) {
                try {
                    if (response.isSuccessful()) {
                        search_loading_item.setVisibility(View.GONE);
                        OrganizerModel organizerModel = response.body();
                        if (organizerModel != null && organizerModel.condition) {
                            List<OrganizerModel.Data> organizerList = organizerModel.data;
                            if (organizerList != null && organizerList.size() > 0) {
                                organizerMasterTableList = organizerList;
                                 setOrganiserAdapter();
                            }
                            else {
                                lv_cust_dist_list.setAdapter(null);
                                frame_layout_org_list.setVisibility(View.GONE);
                            }
                        } else {
                            frame_layout_org_list.setVisibility(View.GONE);
                            search_loading_item.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "Organizer Record not found !", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        frame_layout_org_list.setVisibility(View.GONE);
                        search_loading_item.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    search_loading_item.setVisibility(View.GONE);
                    Log.e("exception database", e.getMessage() + "cause");
                    //   Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "organizer_list", getActivity());
                }
            }

            @Override
            public void onFailure(Call<OrganizerModel> call, Throwable t) {
                search_loading_item.setVisibility(View.GONE);
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "organizer_list", getActivity());
            }
        });
    }

    private void setOrganiserAdapter() {
        OrganizerAdapter organizerAdapter = new OrganizerAdapter(getActivity(), organizerMasterTableList);
        lv_cust_dist_list.setAdapter(organizerAdapter);
        organizerAdapter.setClickListner(this);
    }

    private String farmer_code = "", got = "";
    private List<DispatchFarmerModel.Data> seed_farmer_master_tableList = null;
    private  AutoCompleteTextView ac_lot_no;

    public void AddLineNewItemPopup() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View popupView = inflater.inflate(R.layout.add_seed_dispach_note_line_popup, null);
        Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_DialogWhenLarge_NoActionBar);
        dialog.setContentView(popupView);
        dialog.getWindow().getAttributes().windowAnimations = R.style.AppTheme_FullScreenDialog;
        dialog.show();
        TextInputEditText ac_farmer_code = popupView.findViewById(R.id.ac_farmer_code);
        TextInputEditText ed_city = popupView.findViewById(R.id.ed_city);
        ImageView close_dilog_bt = popupView.findViewById(R.id.close_dilog_bt);
        Button filter_apply_bt = popupView.findViewById(R.id.filter_apply_bt);
        TextInputEditText ed_state = popupView.findViewById(R.id.dropdown_state);
        TextInputEditText ed_taluka = popupView.findViewById(R.id.ac_taluka);
             ac_lot_no = popupView.findViewById(R.id.ac_lot_no);
        AutoCompleteTextView ac_hybrid = popupView.findViewById(R.id.ac_hybrid);
        TextInputEditText ed_no_of_bags = popupView.findViewById(R.id.ed_no_of_bags);
        TextInputEditText ed_remarks = popupView.findViewById(R.id.ed_remarks);
        TextInputEditText ed_quantity = popupView.findViewById(R.id.ed_quantity);
        TextInputEditText ed_village = popupView.findViewById(R.id.ed_village);
        TextInputEditText ed_first_name = popupView.findViewById(R.id.ed_first_name);
        TextInputEditText ed_last_name = popupView.findViewById(R.id.ed_last_name);
        TextInputEditText ed_moister_per = popupView.findViewById(R.id.ed_moister_per);
        TextInputEditText ed_harvested_acreage = popupView.findViewById(R.id.ed_harvest_acre_age);
        MaterialProgressBar content_loading = popupView.findViewById(R.id.content_loading);
        TableRow flag_status_table_row = popupView.findViewById(R.id.flag_status_table_row);
        TextView tv_flag_status = popupView.findViewById(R.id.tv_flag_status);
        AutoCompleteTextView ac_got = popupView.findViewById(R.id.ac_got);

        ItemAdapter got_adapter = new ItemAdapter(getActivity(), R.layout.android_item_view, Arrays.asList(CommonData.got));
        ac_got.setAdapter(got_adapter);
        ac_got.setOnItemClickListener((parent, view, position, id) -> {
            if (position == 0) {
                got = "1";
            } else if (position == 1) {
                got = "0";
            }
        });
        //getHybrid(ac_hybrid,doc_type);
      // getPlantingLineListLot(ac_lot_no, doc_type, org_code);

        new BackgraundThread(getActivity()).execute();

        //getFarmerMasterData(content_loading,ac_farmer_code);
       /* ac_hybrid.setOnItemClickListener((parent, view, position, id) -> {
            if(hybrid_item_tableList!=null && hybrid_item_tableList.size()>0) {
                 hybrid_item_table = hybrid_item_tableList.get(position);
                if(hybrid_item_table!=null) {
                    ac_hybrid.setText(hybrid_item_table.getAlias_Name());
                  //hybrid_item_table.getNo()
                }
            }
           else if(foundation_item_list!=null && foundation_item_list.size()>0) {
                Hybrid_Item_Table hybrid_item_table = foundation_item_list.get(position);
                if(hybrid_item_table!=null) {
                    getPlantingLineListLot(ac_lot_no, doc_type, org_code);//hybrid_item_table.getNo()
                }
            }
        });*/

        ac_lot_no.setOnItemClickListener((parent, view, position, id) -> {
            PlantingLineLotListTable plantingLineLotListTable = fetchProductionLineLotList.get(position);
            if (plantingLineLotListTable != null) {
                content_loading.setVisibility(View.VISIBLE);
                NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                Call<PlantingProdcutionLotModel.PlantingLineLotwise> call = mAPIService.getPlantingLineLotWiseListData(plantingLineLotListTable.getProduction_Lot_No());
                call.enqueue(new Callback<PlantingProdcutionLotModel.PlantingLineLotwise>() {
                    @Override
                    public void onResponse(Call<PlantingProdcutionLotModel.PlantingLineLotwise> call, Response<PlantingProdcutionLotModel.PlantingLineLotwise> response) {
                        if (response.isSuccessful()) {
                            content_loading.setVisibility(View.GONE);
                            PlantingProdcutionLotModel.PlantingLineLotwise plantingProdcutionLotModel = response.body();
                            if (plantingProdcutionLotModel != null && plantingProdcutionLotModel.condition) {
                                List<PlantingProdcutionLotModel.PlantingProductionLotNo> planting_lot_details = plantingProdcutionLotModel.data;
                                if (planting_lot_details != null && planting_lot_details.size() > 0) {
                                    PlantingProdcutionLotModel.PlantingProductionLotNo prodcutionLotModel = planting_lot_details.get(0);
                                    if (prodcutionLotModel.Farmer_Name != null && !prodcutionLotModel.Farmer_Name.equalsIgnoreCase("")) {
                                        ed_first_name.setText(prodcutionLotModel.Farmer_Name);
                                        farmer_code = prodcutionLotModel.Grower_Owner != null ? prodcutionLotModel.Grower_Owner : "";
                                        ac_farmer_code.setText(prodcutionLotModel.Farmer_Name);
                                    }
                                    if (prodcutionLotModel.Farmer_Name_2 != null && prodcutionLotModel.Farmer_Name_2.equalsIgnoreCase("")) {
                                        ed_last_name.setText(prodcutionLotModel.Farmer_Name_2);
                                    }

                                    if (prodcutionLotModel.Alias_Name != null && !prodcutionLotModel.Alias_Name.equalsIgnoreCase("")) {
                                        ac_hybrid.setText(prodcutionLotModel.Alias_Name);
                                        alias_code = prodcutionLotModel.Variety_Code;
                                    }
                                    if (prodcutionLotModel.Village_Name != null && !prodcutionLotModel.Village_Name.equalsIgnoreCase("")) {
                                        ed_village.setText(prodcutionLotModel.Village_Name);
                                    }
                                    if (prodcutionLotModel.Farmer_City != null && !prodcutionLotModel.Farmer_City.equalsIgnoreCase("")) {
                                        ed_city.setText(prodcutionLotModel.Farmer_City);
                                    }

                                    if (prodcutionLotModel.State_Name != null && !prodcutionLotModel.State_Name.equalsIgnoreCase("")) {
                                        ed_state.setText(prodcutionLotModel.State_Name);
                                    }
                                    if (prodcutionLotModel.Taluka != null && !prodcutionLotModel.Taluka.equalsIgnoreCase("")) {
                                        ed_taluka.setText(prodcutionLotModel.Taluka);
                                    }
                                    if (prodcutionLotModel.Flag_Status != null) {
                                        flag_status_table_row.setVisibility(View.VISIBLE);
                                        tv_flag_status.setText(prodcutionLotModel.Flag_Status);
                                    }
                                }

                            } else {
                                content_loading.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), "Farmer Record not found !", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<PlantingProdcutionLotModel.PlantingLineLotwise> call, Throwable t) {
                        content_loading.setVisibility(View.GONE);
                        ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "farmer_list", getActivity());
                    }
                });
            }
        });

        /*ac_farmer_code.setOnItemClickListener((parent, view, position, id) -> {
            DispatchFarmerModel.Data seed_farmer_master_table=  seed_farmer_master_tableList.get(position);
          if(seed_farmer_master_table!=null){
              content_loading.setVisibility(View.VISIBLE);
              NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
              Call<DispatchFarmerModel> call = mAPIService.getDispatchFarmerList(seed_farmer_master_table.No);
              call.enqueue(new Callback<DispatchFarmerModel>() {
                  @Override
                  public void onResponse(Call<DispatchFarmerModel> call, Response<DispatchFarmerModel> response) {
                      try {
                          if (response.isSuccessful()) {
                              DispatchFarmerModel farmerMasterModel = response.body();
                              if (farmerMasterModel!=null && farmerMasterModel.condition) {
                                  List<DispatchFarmerModel.Data> farmermaster_list= farmerMasterModel.data;
                                  if(farmermaster_list!=null && farmermaster_list.size()>0) {
                                      DispatchFarmerModel.Data farmer_detail_model=farmermaster_list.get(0);
                                      if(seed_farmer_master_table.Name!=null && !seed_farmer_master_table.Name.equalsIgnoreCase("")){
                                          ed_first_name.setText(seed_farmer_master_table.Name);
                                      }
                                      if(seed_farmer_master_table.Name_2!=null && !seed_farmer_master_table.Name_2.equalsIgnoreCase("")){
                                          ed_last_name.setText(seed_farmer_master_table.Name_2);
                                      }
                                      if(seed_farmer_master_table.Address!=null && !seed_farmer_master_table.Address.equalsIgnoreCase("")){
                                          ed_village.setText(seed_farmer_master_table.Address_2);
                                      }
                                      if(seed_farmer_master_table.City!=null && !seed_farmer_master_table.City.equalsIgnoreCase("")){
                                          ed_city.setText(seed_farmer_master_table.City);
                                      }

                                      if(seed_farmer_master_table.State_Name!=null && !seed_farmer_master_table.State_Name.equalsIgnoreCase("")){
                                          ed_state.setText(seed_farmer_master_table.State_Name);
                                      }
                                      if(seed_farmer_master_table.Territory_Name!=null && !seed_farmer_master_table.Territory_Name.equalsIgnoreCase("")){
                                          ed_taluka.setText(seed_farmer_master_table.Territory_Name);
                                      }
                                  }
                                } else {
                                  content_loading.setVisibility(View.GONE);
                                  Toast.makeText(getActivity(),  "Farmer Record not found !", Toast.LENGTH_SHORT).show();
                              }
                          } else {
                              content_loading.setVisibility(View.GONE);
                              Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                          }
                      } catch (Exception e) {
                          content_loading.setVisibility(View.GONE);
                          Log.e("exception database", e.getMessage() + "cause");
                          ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "farmer_list", getActivity());
                      } finally {
                          content_loading.setVisibility(View.GONE);
                      }
                  }
                  @Override
                  public void onFailure(Call<DispatchFarmerModel>call, Throwable t) {
                      content_loading.setVisibility(View.GONE);
                      ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "farmer_list", getActivity());
                  }
              });

          }

        });*/

        filter_apply_bt.setOnClickListener(view -> {
            if (ed_no_of_bags.getText().toString().trim().equalsIgnoreCase("")) {
                Toast.makeText(getActivity(), "Please enter no. of bags", Toast.LENGTH_SHORT).show();
            } else if (ed_no_of_bags.getText().toString().equalsIgnoreCase(".")
                    || ed_no_of_bags.getText().toString().trim().equalsIgnoreCase(".0") || ed_no_of_bags.getText().toString().trim().equalsIgnoreCase("0.")) {
                Toast.makeText(getActivity(), "Please select valid bags Qty.", Toast.LENGTH_SHORT).show();
            } else if (ed_quantity.getText().toString().trim().equalsIgnoreCase("")) {
                Toast.makeText(getActivity(), "Please enter qty.", Toast.LENGTH_SHORT).show();
            } else if (ed_quantity.getText().toString().equalsIgnoreCase(".")
                    || ed_quantity.getText().toString().trim().equalsIgnoreCase(".0") || ed_quantity.getText().toString().trim().equalsIgnoreCase("0.")) {
                Toast.makeText(getActivity(), "Please select valid Qty.", Toast.LENGTH_SHORT).show();
            } else if (ed_moister_per.getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), "Please enter moisture %.", Toast.LENGTH_SHORT).show();
            } else if (ed_harvested_acreage.getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), "Please enter harvested acreage.", Toast.LENGTH_SHORT).show();
            } else if (ed_harvested_acreage.getText().toString().equalsIgnoreCase(".") || ed_harvested_acreage.getText().toString().equalsIgnoreCase("0")) {
                Toast.makeText(getActivity(), "Please enter valid harvested acreage.", Toast.LENGTH_SHORT).show();
            } else {
                SeedDispatchHeaderModel.SeedDispatchLineModel seedDispatchLineModel = new SeedDispatchHeaderModel().new SeedDispatchLineModel();
                try {
                    seedDispatchLineModel.line_no = dispatchNoteLineTableList != null ? String.valueOf(dispatchNoteLineTableList.size() + 1) : "1";
                    seedDispatchLineModel.dispatch_no = seedDispatchHeader_list.dispatch_no;
                    seedDispatchLineModel.farmer_code = farmer_code;
                    seedDispatchLineModel.farmer_name = ed_first_name.getText().toString().trim();
                    seedDispatchLineModel.farmer_name2 = ed_last_name.getText().toString().trim();
                    seedDispatchLineModel.village_name = ed_village.getText().toString().trim();
                    seedDispatchLineModel.taluka = ed_taluka.getText().toString().trim();
                    seedDispatchLineModel.state = ed_state.getText().toString().trim();
                    seedDispatchLineModel.city = ed_city.getText().toString().trim();
                    seedDispatchLineModel.lot_number = ac_lot_no.getText().toString().trim();
                    seedDispatchLineModel.moisture_prcnt = ed_moister_per.getText().toString().trim();
                    seedDispatchLineModel.harvested_acreage = ed_harvested_acreage.getText().toString().trim();
                    seedDispatchLineModel.hybrid = alias_code != null ? alias_code : "";
                    seedDispatchLineModel.hybrid_name = ac_hybrid.getText().toString().trim();
                    seedDispatchLineModel.number_of_bags = ed_no_of_bags.getText().toString().trim();
                    seedDispatchLineModel.quantity = ed_quantity.getText().toString().trim();
                    seedDispatchLineModel.created_on = DateTimeUtilsCustome.getCurrentTime();
                    seedDispatchLineModel.remarks = ed_remarks.getText().toString().trim();
                    seedDispatchLineModel.got = got;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getActivity());
                if (isNetwork) {
                    String jsonString = new Gson().toJson(seedDispatchLineModel);
                    JsonObject asJsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
                    NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                    Call<List<SeedDispatchHeaderModel.SeedDispatchLineModel>> call = mAPIService.insertDispatchLine(asJsonObject);
                    LoadingDialog progressDialogLoading = new LoadingDialog();
                    //List<SeedDispatchHeaderModel.SeedDispatchLineModel> dispatch_line_list = new ArrayList<>();
                    progressDialogLoading.showLoadingDialog(getActivity());
                    call.enqueue(new Callback<List<SeedDispatchHeaderModel.SeedDispatchLineModel>>() {
                        @Override
                        public void onResponse(Call<List<SeedDispatchHeaderModel.SeedDispatchLineModel>> call, Response<List<SeedDispatchHeaderModel.SeedDispatchLineModel>> response) {
                            try {
                                if (response.isSuccessful()) {
                                    progressDialogLoading.hideDialog();
                                    List<SeedDispatchHeaderModel.SeedDispatchLineModel> insert_dispatch_linelist = response.body();
                                    if (insert_dispatch_linelist != null && insert_dispatch_linelist.size() > 0 && insert_dispatch_linelist.get(0).condition) {
                                        seedDispatchLineModel.dispatch_no = insert_dispatch_linelist.get(0).dispatch_no;
                                        seedDispatchLineModel.created_on = insert_dispatch_linelist.get(0).created_on;
                                        seedDispatchLineModel.line_no = insert_dispatch_linelist.get(0).line_no;
                                        seedDispatchLineModel.insert_line_into_local = 0;
                                        dispatchNoteLineTableList = insert_dispatch_linelist;

                                       /* dispatch_line_list.add(seedDispatchLineModel);
                                        insertDispatchLineIntoLocal(dispatch_line_list, dialog);*/
                                        setAdapter();
                                        dialog.dismiss();
                                        Toast.makeText(getActivity(), insert_dispatch_linelist.get(0).message, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getActivity(), insert_dispatch_linelist.get(0).message, Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    progressDialogLoading.hideDialog();
                                    Toast.makeText(getActivity(), "Api not respoding.", Toast.LENGTH_SHORT).show();
                                }

                            } catch (Exception e) {
                                progressDialogLoading.hideDialog();
                                Log.e("exception database", e.getMessage() + "cause");
                                ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "insert Dispatch line", getActivity());
                            } finally {
                                progressDialogLoading.hideDialog();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<SeedDispatchHeaderModel.SeedDispatchLineModel>> call, Throwable t) {
                            progressDialogLoading.hideDialog();
                            ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "insert Dispatch line", getActivity());
                        }
                    });
                } else {
                  /*  List<SeedDispatchHeaderModel.SeedDispatchLineModel> dispatch_line_list = new ArrayList<>();
                    seedDispatchLineModel.dispatch_no = "0";
                    seedDispatchLineModel.insert_line_into_local = 1;
                    dispatch_line_list.add(seedDispatchLineModel);*/
                    Toast.makeText(getActivity(), "Please wait for internet connection.", Toast.LENGTH_SHORT).show();
                    // insertDispatchLineIntoLocal(dispatch_line_list, dialog);
                }
            }
        });

        close_dilog_bt.setOnClickListener(view -> {
            dialog.dismiss();
        });
    }

   /* private void insertDispatchLineIntoLocal(List<SeedDispatchHeaderModel.SeedDispatchLineModel> insert_dispatch_linelist, Dialog dialog) {
        PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getActivity());
        try {
            SeedDispatchNoteLineTable seedDispatchNoteLineTable = new SeedDispatchNoteLineTable();
            SeedDispatchNoteLineDao seedDispatchNoteLineDao = pristineDatabase.seedDispatchNoteLineDao();
            for (int i = 0; i < insert_dispatch_linelist.size(); i++) {
                seedDispatchNoteLineTable.setLine_no(insert_dispatch_linelist.get(i).line_no);
                seedDispatchNoteLineTable.setDispatch_no(insert_dispatch_linelist.get(i).dispatch_no);
                seedDispatchNoteLineTable.setFarmer_code(insert_dispatch_linelist.get(i).farmer_code);
                seedDispatchNoteLineTable.setFarmer_name(insert_dispatch_linelist.get(i).farmer_name);
                seedDispatchNoteLineTable.setFarmer_name2(insert_dispatch_linelist.get(i).farmer_name2);
                seedDispatchNoteLineTable.setVillage_name(insert_dispatch_linelist.get(i).village_name);
                seedDispatchNoteLineTable.setTaluka(insert_dispatch_linelist.get(i).taluka);
                seedDispatchNoteLineTable.setState(insert_dispatch_linelist.get(i).state);
                seedDispatchNoteLineTable.setCity(insert_dispatch_linelist.get(i).city);
                seedDispatchNoteLineTable.setLot_number(insert_dispatch_linelist.get(i).lot_number);
                seedDispatchNoteLineTable.setHybrid(insert_dispatch_linelist.get(i).hybrid);
                seedDispatchNoteLineTable.setNumber_of_bags(insert_dispatch_linelist.get(i).number_of_bags);
                seedDispatchNoteLineTable.setQuantity(insert_dispatch_linelist.get(i).quantity);
                seedDispatchNoteLineTable.setCreated_on(insert_dispatch_linelist.get(i).created_on);
                seedDispatchNoteLineTable.setRemarks(insert_dispatch_linelist.get(i).remarks);
                seedDispatchNoteLineTable.setHybrid_name(insert_dispatch_linelist.get(i).hybrid_name);

              *//*  if(seedDispatchNoteLineDao.isDataExist(dispatchNoteLineTableList.get(i).dispatch_no)){
                    seedDispatchNoteLineDao.update(seedDispatchNoteLineTable);
                   }
                 else {*//*
                seedDispatchNoteLineDao.insert(seedDispatchNoteLineTable);
                // }
            }
            dispatchNoteLineTableList = seedDispatchNoteLineDao.getAllDatabyDispatchCode(seedDispatchNoteLineTable.getDispatch_no());
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            pristineDatabase.close();
            pristineDatabase.destroyInstance();
            dialog.dismiss();
            bindDispatchHeaderData(new ArrayList<>());
        }
    }*/

    private void setAdapter() {
        seed_dispatch_line_adapter = new SeedDispatchLineAdapter(getActivity(), dispatchNoteLineTableList);
        listview.setAdapter(seed_dispatch_line_adapter);
    }

    private void deleteHeader(String dispatch_no) {
        boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getActivity());
        //  PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getActivity());
        //  try {
        //  SeedDispatchHeaderDao seedDispatchHeaderDao = pristineDatabase.seedDispatchHeaderDao();
        if (isNetwork) {
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("dispatch_no", dispatch_no);
            Call<List<DispatchResponseModel>> call = mAPIService.deleteDispatchHeader(jsonObject);
            LoadingDialog progressDialogLoading = new LoadingDialog();
            progressDialogLoading.showLoadingDialog(getActivity());
            call.enqueue(new Callback<List<DispatchResponseModel>>() {
                @Override
                public void onResponse(Call<List<DispatchResponseModel>> call, Response<List<DispatchResponseModel>> response) {
                    try {
                        if (response.isSuccessful()) {
                            progressDialogLoading.hideDialog();
                            List<DispatchResponseModel> deleteResponseList = response.body();
                            if (deleteResponseList != null && deleteResponseList.size() > 0 && deleteResponseList.get(0).condition) {
                                // int remove = seedDispatchHeaderDao.deleteRecordHedaer(seedDispatchHeader_list.get(0).dispatch_no);
                                //  if (remove > 0) {
                                seedDispatchHeader_list = null;
                                clearHeaderCreateSection();
                                bindDispatchHeaderData();
                                Toast.makeText(getActivity(), "delete header successful!", Toast.LENGTH_SHORT).show();
                                //  }
                            } else {
                                progressDialogLoading.hideDialog();
                                Toast.makeText(getActivity(), deleteResponseList.size() > 0 ? "No data found" : "Api not respoding.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            progressDialogLoading.hideDialog();
                            Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        progressDialogLoading.hideDialog();
                        Log.e("exception database", e.getMessage() + "cause");
                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "delete Dispatch header", getActivity());
                    } finally {
                        progressDialogLoading.hideDialog();
                    }
                }

                @Override
                public void onFailure(Call<List<DispatchResponseModel>> call, Throwable t) {
                    progressDialogLoading.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "delete Dispatch header", getActivity());
                }
            });
        } else {
            Toast.makeText(getActivity(), "Please wait for internet connection. ", Toast.LENGTH_SHORT).show();
              /*  SeedDispatchHeaderModel dispatchHeaderModel = new SeedDispatchHeaderModel();
                ArrayList<SeedDispatchHeaderModel> dispatchHeaderModelArrayList = new ArrayList<>();
                int remove = seedDispatchHeaderDao.deleteRecordHedaerOffline(seedDispatchNoteHeaderTableList.get(0).getAndroid_id());
                if (remove > 0) {
                    seedDispatchNoteHeaderTableList.remove(0);
                    seedDispatchHeaderDao.updateAfterDelete(1);
                    dispatchHeaderModel.delete_line_header = 1;
                    dispatchHeaderModelArrayList.add(dispatchHeaderModel);
                    Toast.makeText(getActivity(), "delete data successful!", Toast.LENGTH_SHORT).show();
                    bindDispatchHeaderData(dispatchHeaderModelArrayList);
                }*/
        }

       /* } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pristineDatabase.close();
            pristineDatabase.destroyInstance();
        }*/
    }


    private void deleteDispatchLine(String dispatch_code, int pos) {
        boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getActivity());
       // PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getActivity());
      //  try {
            // SeedDispatchNoteLineDao dispatchNoteLineDao = pristineDatabase.seedDispatchNoteLineDao();
            if (isNetwork) {
                NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("dispatch_no", dispatch_code);
                jsonObject.addProperty("line_no", dispatchNoteLineTableList.get(pos).line_no);
                Call<List<DispatchResponseModel>> call = mAPIService.deleteDispatchLine(jsonObject);
                LoadingDialog progressDialogLoading = new LoadingDialog();
                progressDialogLoading.showLoadingDialog(getActivity());
                call.enqueue(new Callback<List<DispatchResponseModel>>() {
                    @Override
                    public void onResponse(Call<List<DispatchResponseModel>> call, Response<List<DispatchResponseModel>> response) {
                        if (response.isSuccessful()) {
                            progressDialogLoading.hideDialog();
                            List<DispatchResponseModel> deleteResponseList = response.body();
                            if (deleteResponseList != null && deleteResponseList.size() > 0 && deleteResponseList.get(0).condition) {
                                //  int remove = dispatchNoteLineDao.deleteSeedDispatchNoteLine(dispatch_code,dispatchNoteLineTableList.get(pos).getLine_no());
                                //  if (remove > 0) {
                                dispatchNoteLineTableList.remove(pos);
                                seed_dispatch_line_adapter.notifyDataSetChanged();
                                //  insertDispatchLineIntoLocal(new ArrayList<>(), null);
                                Toast.makeText(getActivity(), "delete line successful!", Toast.LENGTH_SHORT).show();
                                //   }
                            } else {
                                progressDialogLoading.hideDialog();
                                Toast.makeText(getActivity(), deleteResponseList.size() > 0 ? "No data found" : "Api not responding.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            progressDialogLoading.hideDialog();
                            Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<DispatchResponseModel>> call, Throwable t) {
                        progressDialogLoading.hideDialog();
                        ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "delete_dispatch_line", getActivity());
                    }
                });
            } else {
                //  int remove = dispatchNoteLineDao.deleteLocalSeedDispatchNoteLine(dispatchNoteLineTableList.get(pos).getAndroid_id(), dispatchNoteLineTableList.get(pos).getLine_no());
                //   if (remove > 0) {
                  /*  dispatchNoteLineTableList.remove(pos);
                    seed_dispatch_line_adapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "delete line successful!", Toast.LENGTH_SHORT).show();
                    insertDispatchLineIntoLocal(new ArrayList<>(), null);*/
                //   }

                Toast.makeText(getActivity(), "Please wait for internet connection", Toast.LENGTH_SHORT).show();
            }
      /*  } catch (Exception e) {
            e.printStackTrace();
        }*/ /*finally {
          *//*  pristineDatabase.close();
            pristineDatabase.destroyInstance();*//*
        }*/
    }

    private void completeDispatchNote() {
        try {
            boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getActivity());
            if (isNetwork) {
                NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                Call<List<DispatchResponseModel>> call = mAPIService.completeDispatchNote(seedDispatchHeader_list.dispatch_no);
                LoadingDialog progressDialogLoading = new LoadingDialog();
                progressDialogLoading.showLoadingDialog(getActivity());
                call.enqueue(new Callback<List<DispatchResponseModel>>() {
                    @Override
                    public void onResponse(Call<List<DispatchResponseModel>> call, Response<List<DispatchResponseModel>> response) {
                        try {
                            if (response.isSuccessful()) {
                                progressDialogLoading.hideDialog();
                                List<DispatchResponseModel> completeResponseList = response.body();
                                if (completeResponseList != null && completeResponseList.size() > 0 && completeResponseList.get(0).condition) {
                                    seedDispatchHeader_list.status = 1;
                                    getDispatchHeaderDataFromLocal();
                                    //  dispatchCompleteOnlineOffline("Online");
                                } else {
                                    progressDialogLoading.hideDialog();
                                    Toast.makeText(getActivity(), completeResponseList.size() > 0 ? completeResponseList.get(0).message : "Api not responding.", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                progressDialogLoading.hideDialog();
                                Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            progressDialogLoading.hideDialog();
                            Log.e("exception_database", e.getMessage() + "cause");
                            ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "complete_dispatch_note", getActivity());
                        } finally {
                            progressDialogLoading.hideDialog();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<DispatchResponseModel>> call, Throwable t) {
                        progressDialogLoading.hideDialog();
                        ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "dispatchHeaderDao", getActivity());
                    }
                });

            } else {
                Toast.makeText(getActivity(), "Please wait for internet connection.", Toast.LENGTH_SHORT).show();
                // dispatchCompleteOnlineOffline("Offline");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void dispatchCompleteOnlineOffline(String onlineOffline) {
        PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getActivity());
        try {
            SeedDispatchHeaderDao dispatchHeaderDao = pristineDatabase.seedDispatchHeaderDao();
            if (onlineOffline.equalsIgnoreCase("Online")) {
                dispatchHeaderDao.updateCompleteDispatchHeaderStatus(1, 0, seedDispatchHeader_list.dispatch_no);
            } else {
                dispatchHeaderDao.updateCompleteDispatchHeaderStatus(0, 1, seedDispatchHeader_list.dispatch_no);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pristineDatabase.close();
            pristineDatabase.destroyInstance();
            getDispatchHeaderDataFromLocal();
        }
    }

    /*  private void getHybrid(AutoCompleteTextView ac_hybrid_name,String class_of_seed_type){
          PristineDatabase pristineDatabase=PristineDatabase.getAppDatabase(getActivity());
          try{
              HybridItemMasterDao hybridItemMasterDao=pristineDatabase.hybridItemMasterDao();
              if(class_of_seed_type.equalsIgnoreCase("Hybrid")){
                  hybrid_item_tableList=hybridItemMasterDao.getHybridAliasItem();
              }else if(class_of_seed_type.equalsIgnoreCase("Foundation")){
                  foundation_item_list=hybridItemMasterDao.getFoundationItem(sessionManagement.getUserEmail().toLowerCase());
              }
          }catch (Exception e){
              e.printStackTrace();
          }finally {
              pristineDatabase.close();
              pristineDatabase.destroyInstance();
              if(hybrid_item_tableList!=null &&  hybrid_item_tableList.size()>0){
                  HybridItemAliasNameAdapter hybridItemAliasNameAdapter=new  HybridItemAliasNameAdapter(getActivity(),R.layout.android_item_view,hybrid_item_tableList);
                  ac_hybrid_name.setAdapter(hybridItemAliasNameAdapter);
              }
              if(foundation_item_list!=null && foundation_item_list.size()>0){
                  HybridItemAdapter hybridItemAdapter=new HybridItemAdapter(getActivity(),R.layout.android_item_view,foundation_item_list);
                  ac_hybrid_name.setAdapter(hybridItemAdapter);
              }
          }
      }
  */
    private void getPlantingLineListLot(AutoCompleteTextView ac_prod_lot, String doc_type, String org_code) {
        PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getActivity());
        try {
            PlantingLineLotListDao plantingLineLotListDao = pristineDatabase.plantingLineLotListDao();
            if (doc_type.equalsIgnoreCase("Hybrid")) {
                fetchProductionLineLotList = plantingLineLotListDao.fetchLineLotData("FSIO", org_code);//varity_code,org_code
            } else if (doc_type.equalsIgnoreCase("Foundation")) {
                fetchProductionLineLotList = plantingLineLotListDao.fetchLineLotData("BSIO", org_code);//,varity_code,org_code
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pristineDatabase.close();
            pristineDatabase.destroyInstance();
            if (fetchProductionLineLotList != null && fetchProductionLineLotList.size() > 0) {
                PlantingProductionLotLineListAdapter plantingLineProductionLotAdapter = new PlantingProductionLotLineListAdapter(getContext(), R.layout.android_item_view, fetchProductionLineLotList);
                ac_prod_lot.setAdapter(plantingLineProductionLotAdapter);
            }
          /*  else if(fetchProductionLineLotListFoundation!=null && fetchProductionLineLotListFoundation.size()>0){
                PlantingProductionLotLineListAdapter plantingLineProductionLotAdapter = new PlantingProductionLotLineListAdapter(getContext(), R.layout.android_item_view, fetchProductionLineLotListFoundation);
                ac_prod_lot.setAdapter(plantingLineProductionLotAdapter);
            }*/
        }
    }

    private void clearHeaderCreateSection() {
        tv_header_dispatch_no.setText("");
        edit_date.setText("");
        ac_fromLocation.setText("");
        ac_tolocation.setText("");
        edit_Supervisor.setText("");
        edit_Transporter.setText("");
        ac_orgnizer_code.setText("");
        edit_Truck_Number.setText("");
        ac_season_code.setText("");
        ed_camp_at.setText("");
        ac_doc_doc_type.setText("");
        tv_header_dispatch_no.setText("");
        ed_remarks.setText("");
    }

    private void getFarmerMasterData(ProgressBar content_loading, AutoCompleteTextView ac_grwer_name) {
        content_loading.setVisibility(View.VISIBLE);
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<DispatchFarmerModel> call = mAPIService.getDispatchFarmerList("");
        call.enqueue(new Callback<DispatchFarmerModel>() {
            @Override
            public void onResponse(Call<DispatchFarmerModel> call, Response<DispatchFarmerModel> response) {
                try {
                    if (response.isSuccessful()) {
                        DispatchFarmerModel farmerMasterModel = response.body();
                        if (farmerMasterModel != null && farmerMasterModel.condition) {
                            List<DispatchFarmerModel.Data> farmermaster_list = farmerMasterModel.data;
                            if (farmermaster_list != null && farmermaster_list.size() > 0) {
                                seed_farmer_master_tableList = farmermaster_list;
                                FarmerListAdapter farmerListAdapter = new FarmerListAdapter(getActivity(), R.layout.android_item_view, seed_farmer_master_tableList);
                                ac_grwer_name.setAdapter(farmerListAdapter);
                            }
                        } else {
                            content_loading.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "Farmer Record not found !", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        content_loading.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    content_loading.setVisibility(View.GONE);
                    Log.e("exception database", e.getMessage() + "cause");
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "farmer_list", getActivity());
                } finally {
                    content_loading.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<DispatchFarmerModel> call, Throwable t) {
                content_loading.setVisibility(View.GONE);
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "farmer_list", getActivity());
            }
        });

    }

    @Override
    public void onItemClick(int pos) {
        organizer_master_table = organizerMasterTableList.get(pos);
        if (organizer_master_table != null) {
            ac_orgnizer_code.setText(organizer_master_table.Name);
            search_input_layout.setEndIconDrawable(null);
            frame_layout_org_list.setVisibility(View.GONE);
        } else {
            frame_layout_org_list.setVisibility(View.GONE);
            search_input_layout.setEndIconDrawable(null);
            ac_orgnizer_code.setText("");
        }
    }

    public class BackgraundThread extends BackgroundTask {
        public Activity activity;
        PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getActivity());
        public BackgraundThread(Activity activity) {
            super(activity);
            this.activity = activity;
        }
       /* public BackgraundThread(Activity activity, AutoCompleteTextView ac_prod_lot, String doc_type, String org_code) {
            super();
            this.activity = activity;
            this.ac_prod_lot = ac_prod_lot;
            this.doc_type = doc_type;
            this.org_code = org_code;

        }
*/
        @Override
        public void doInBackground() {
            try {
                PlantingLineLotListDao plantingLineLotListDao = pristineDatabase.plantingLineLotListDao();
                if(seedDispatchHeader_list.organizer_code!=null) {
                    if (seedDispatchHeader_list.document_type.equalsIgnoreCase("Hybrid")) {
                        fetchProductionLineLotList = plantingLineLotListDao.fetchLineLotData("FSIO",seedDispatchHeader_list.organizer_code);//varity_code,org_code
                    } else if (seedDispatchHeader_list.document_type.equalsIgnoreCase("Foundation")) {
                        fetchProductionLineLotList = plantingLineLotListDao.fetchLineLotData("BSIO",seedDispatchHeader_list.organizer_code);//,varity_code,org_code
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                pristineDatabase.close();
                pristineDatabase.destroyInstance();
            }
        }
        @Override
        public void onPostExecute() {
            if (fetchProductionLineLotList != null && fetchProductionLineLotList.size() > 0) {
                PlantingProductionLotLineListAdapter plantingLineProductionLotAdapter = new PlantingProductionLotLineListAdapter(getContext(), R.layout.android_item_view, fetchProductionLineLotList);
                ac_lot_no.setAdapter(plantingLineProductionLotAdapter);
            }
        }
    }
}