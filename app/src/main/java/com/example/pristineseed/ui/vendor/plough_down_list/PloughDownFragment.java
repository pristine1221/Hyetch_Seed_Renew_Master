package com.example.pristineseed.ui.vendor.plough_down_list;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.UnicodeSetSpanner;
import android.net.Uri;
import android.os.Bundle;
import android.print.PrinterId;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.PlantingLineLotListDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.PlantingLineLotListTable;
import com.example.pristineseed.GlobalNotification.NetworkUtil;
import com.example.pristineseed.R;
import com.example.pristineseed.RoomDataBase.PristineDatabase;
import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.common_data.CommonData;
import com.example.pristineseed.global.ApiRequestFailure;
import com.example.pristineseed.global.CustomDatePicker;
import com.example.pristineseed.global.DateTimeUtilsCustome;
import com.example.pristineseed.global.FilePath;
import com.example.pristineseed.global.LoadingDialog;
import com.example.pristineseed.global.StaticMethods;
import com.example.pristineseed.model.Plough_down_List.PloghDownListModel;
import com.example.pristineseed.model.ResponseModel;
import com.example.pristineseed.model.item.PlantingProdcutionLotModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.adapter.ItemAdapter;
import com.example.pristineseed.ui.adapter.header_line.PloughDownListAdapter;
import com.example.pristineseed.ui.adapter.item.PlantingProductionLotLineListAdapter;
import com.example.pristineseed.ui.adapter.item.PlantingProductionLotNumberAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import org.apache.commons.io.output.ByteArrayOutputStream;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PloughDownFragment extends Fragment implements PlantingProductionLotNumberAdapter.OnItemClickListner{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private Chip add_newItem;
    private SessionManagement sessionManagement;
    private ListView listview;
    private List<PlantingLineLotListTable> production_lot_list = new ArrayList<>();
    private String sowing_area_in_acre = "";
    private List<PloghDownListModel> pld_response_list;
    private PlantingLineLotListTable plantingLineLotListTable = null;
    private static int SELECT_FILE = 1;
    private MaterialButton clear_image_btn;
    TextInputEditText ed_sown_acre;
    TextInputEditText ac_lot_no;
    RecyclerView rv_search_lot_num_list;
    MaterialCardView frame_layout_lot_num_card_view;

    private ImageView vp_image;
    private CardView img_card;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_plough_down, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listview = view.findViewById(R.id.listview);
        sessionManagement = new SessionManagement(getContext());
        add_newItem = view.findViewById(R.id.add_newItem);
        add_newItem.setOnClickListener(view1 -> {
            AddNewItemPopup("Insert", null);
        });

        listview.setOnItemClickListener((parent, view1, position, id) -> {
            if (pld_response_list != null) {
                PloghDownListModel ploghDownListModel = pld_response_list.get(position);
                if (!ploghDownListModel.status.equalsIgnoreCase("Approve")) {
                    AddNewItemPopup("Update", ploghDownListModel);
                } else {
                    Toast.makeText(getActivity(), "You can't update item as status is approved", Toast.LENGTH_SHORT).show();
                }

            }

        });

    }

    @Override
    public void onResume() {
        super.onResume();
        getPloughDownList();
    }

    private void getPloughDownList() {
        boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getActivity());
        if (isNetwork) {
            LoadingDialog progressDialog = new LoadingDialog();
            progressDialog.showLoadingDialog(getActivity());
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            Call<List<PloghDownListModel>> call = mAPIService.getPloughDownList(sessionManagement.getUserEmail());
            call.enqueue(new Callback<List<PloghDownListModel>>() {
                @Override
                public void onResponse(Call<List<PloghDownListModel>> call, Response<List<PloghDownListModel>> response) {
                    try {
                        if (response.isSuccessful()) {
                            progressDialog.hideDialog();
                            List<PloghDownListModel> pog_response_list = response.body();
                            if (pog_response_list != null && pog_response_list.size() > 0 && pog_response_list.get(0).condition) {
                                pld_response_list = pog_response_list;
                                binddataWithAadapter(pld_response_list);
                            } else {
                                Toast.makeText(getActivity(), pog_response_list != null && pog_response_list.size() > 0 ? "No data found" : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            progressDialog.hideDialog();
                            Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        progressDialog.hideDialog();
                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "get_plough_list", getActivity());
                    }
                }

                @Override
                public void onFailure(Call<List<PloghDownListModel>> call, Throwable t) {
                    progressDialog.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "get_plough_list", getActivity());
                }
            });

        } else {
            Toast.makeText(getActivity(), "Please wait for online connection.", Toast.LENGTH_SHORT).show();
        }
    }

    private void binddataWithAadapter(List<PloghDownListModel> pldList) {
        PloughDownListAdapter approvalAdapter = new PloughDownListAdapter(getActivity(), pldList);
        listview.setAdapter(approvalAdapter);
    }

    public void AddNewItemPopup(String flag, PloghDownListModel ploghDownListModel) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View popupView = inflater.inflate(R.layout.add_plough_down_popup, null);
        Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_DialogWhenLarge_NoActionBar);
        dialog.setContentView(popupView);
        dialog.getWindow().getAttributes().windowAnimations = R.style.AppTheme_FullScreenDialog;
        dialog.show();
        ImageView close_dilog_bt = popupView.findViewById(R.id.close_dilog_bt);
        ac_lot_no = popupView.findViewById(R.id.ac_lot_no);

        rv_search_lot_num_list = popupView.findViewById(R.id.rv_search_lot_num_list);
        frame_layout_lot_num_card_view = popupView.findViewById(R.id.frame_layout_lot_num_card_view);
        TextInputLayout ac_lot_number_layout = popupView.findViewById(R.id.ac_lot_number_layout);
        AutoCompleteTextView ac_lot_status = popupView.findViewById(R.id.ac_lot_status);
        TextInputEditText ed_reason_ = popupView.findViewById(R.id.ed_reason_);
        TextInputEditText ed_pld_date = popupView.findViewById(R.id.ed_pld_date);
        TextInputEditText ed_pld_acre = popupView.findViewById(R.id.ed_pld_acre);
        ed_sown_acre = popupView.findViewById(R.id.ed_sown_acre);
        Button update_detail_btn = popupView.findViewById(R.id.update_detail);
        TextView tv_pld_update_title = popupView.findViewById(R.id.tv_pld_update_title);
        TextView add_pld_title = popupView.findViewById(R.id.add_pld_title);
        RelativeLayout rl_custom_layout = popupView.findViewById(R.id.rl_custom_layout);
        RelativeLayout top_layout = popupView.findViewById(R.id.top_layout);
        LinearLayout bottom_button = popupView.findViewById(R.id.bottom_button);
        LinearLayoutManager search_lot_num_manager;
        vp_image = popupView.findViewById(R.id.vp_image);
        MaterialButton _btn_selct_img = popupView.findViewById(R.id._btn_selct_img);
        clear_image_btn = popupView.findViewById(R.id.clear_image_btn);
        img_card = popupView.findViewById(R.id.img_card);

        search_lot_num_manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_search_lot_num_list.setLayoutManager(search_lot_num_manager);

        getPlantingLineListLot(rv_search_lot_num_list, "");

        top_layout.requestFocus();

        top_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    top_layout.requestFocus();
                    ac_lot_no.clearFocus();
                    ed_reason_.requestFocus();
                    frame_layout_lot_num_card_view.setVisibility(View.GONE);
                }
                return true;
            }
        });

        rl_custom_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    rl_custom_layout.requestFocus();
                }
                return true;
            }
        });

        ItemAdapter itemAdapter = new ItemAdapter(getActivity(), R.layout.android_item_view, Arrays.asList(CommonData.pld_lot_stauts));
        ac_lot_status.setAdapter(itemAdapter);

        ac_lot_no.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && ac_lot_no.isCursorVisible()) {
                    frame_layout_lot_num_card_view.setVisibility(View.VISIBLE);
                    ac_lot_number_layout.setStartIconDrawable(null);
                    getPlantingLineListLot(rv_search_lot_num_list,"");
                } else {
                    if (!ac_lot_no.getText().toString().trim().equalsIgnoreCase("")) {
                        ac_lot_number_layout.setStartIconDrawable(null);
                    } else {
                        ac_lot_number_layout.setStartIconDrawable(R.drawable.ic_baseline_search_24);
                        frame_layout_lot_num_card_view.setVisibility(View.GONE);
                    }
                }
            }
        });

        ac_lot_no.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ac_lot_no.setSelection(s.toString().length());
                if (!s.toString().equalsIgnoreCase("") && ac_lot_no.isCursorVisible()){
                    frame_layout_lot_num_card_view.setVisibility(View.VISIBLE);
                    getPlantingLineListLot(rv_search_lot_num_list,s.toString());
                }else {
                    frame_layout_lot_num_card_view.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        /*ac_lot_no.setOnItemClickListener((parent, view, position, id) -> {

            if (production_lot_list != null && production_lot_list.size() > 0) {
                PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getActivity());
                try {
                    PlantingLineLotListDao plantingLineLotListDao = pristineDatabase.plantingLineLotListDao();
                    plantingLineLotListTable = plantingLineLotListDao.getSowingAcre(production_lot_list.get(0).getProduction_Lot_No());

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (plantingLineLotListTable != null) {
                        ed_sown_acre.setText(plantingLineLotListTable.getSowing_Area_In_Acres());
                    }
                }
            }
        });*/

        //todo to view gallery....
        _btn_selct_img.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_FILE);
        });

        clear_image_btn.setOnClickListener(v -> {
            new MaterialAlertDialogBuilder(getActivity())
                    .setTitle("Confirm...")
                    .setMessage("Do you really want to delete this Image?")
                    .setIcon(R.drawable.ic_baseline_delete_forever_24)
                    .setPositiveButton("Confirm", (dialogInterface, i1) -> {
                        vp_image.setImageBitmap(null);
                        imageEncodList.clear();
                        img_card.setVisibility(View.GONE);
                        clear_image_btn.setVisibility(View.GONE);
                        ploghDownListModel.image = "";
                    })
                    .setNegativeButton("Cancel", (dialogInterface, i1) -> {
                    })
                    .show();
        });

        ed_pld_date.setOnTouchListener((v, event) -> {
            new CustomDatePicker(getActivity()).showDatePickerDialog(ed_pld_date);
            return true;
        });

        close_dilog_bt.setOnClickListener(view -> {
            dialog.dismiss();
        });

        Button filter_apply_bt = popupView.findViewById(R.id.filter_apply_bt);

        if (flag.equalsIgnoreCase("Insert")) {
            filter_apply_bt.setOnClickListener(view -> {

                if (!ed_pld_acre.getText().toString().trim().equalsIgnoreCase("")) {
                    float pld_acre = Float.parseFloat(ed_pld_acre.getText().toString());
                    float sown_acre = Float.parseFloat(ed_sown_acre.getText().toString());
                    if (pld_acre > sown_acre) {
                        Toast.makeText(getActivity(), "PLD Acre should be less than Sown Acre.", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        ed_pld_acre.setText(String.valueOf(pld_acre));
                        ed_pld_acre.setSelection(ed_pld_acre.getText().length());
                    }
                } else if (ed_reason_.getText().toString().trim().equals("")) {
                    Toast.makeText(getActivity(), "Please enter reason.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ac_lot_status.getText().toString().trim().equals("")) {
                    Toast.makeText(getActivity(), "Please enter lot. no", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ac_lot_status.getText().toString().trim().equals("")) {
                    Toast.makeText(getActivity(), "Please enter lot. status.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ed_pld_acre.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter pld acre", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ed_pld_acre.getText().toString().trim().equalsIgnoreCase(".")) {
                    Toast.makeText(getActivity(), "Please enter valid pld acre", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ed_pld_acre.getText().toString().trim().equalsIgnoreCase(".0") || ed_pld_acre.getText().toString().trim().equalsIgnoreCase("0.")) {
                    Toast.makeText(getActivity(), "Please enter valid pld acre", Toast.LENGTH_SHORT).show();
                    return;
                }

//                else {
                boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getActivity());
                if (isNetwork) {
                    LoadingDialog progressDialog = new LoadingDialog();
                    progressDialog.showLoadingDialog(getActivity());
                    NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("lot_no", ac_lot_no.getText().toString().trim());
                    jsonObject.addProperty("lot_status", ac_lot_status.getText().toString().trim());
                    jsonObject.addProperty("reason", ed_reason_.getText().toString().trim());
                    jsonObject.addProperty("date", DateTimeUtilsCustome.splitDateInYYYMMDD(ed_pld_date.getText().toString().trim()));
                    jsonObject.addProperty("created_by", sessionManagement.getUserEmail());
                    jsonObject.addProperty("approver_id", sessionManagement.getApprover_id());
                    jsonObject.addProperty("pld_acre", ed_pld_acre.getText().toString().trim());
                    jsonObject.addProperty("sown_acre", ed_sown_acre.getText().toString().trim());
                    try {
                        String base64 = covertBase64File(imageEncodList.get(0));
                        if (base64 != null) {
                            jsonObject.addProperty("image", base64);
                        } else {
                            jsonObject.addProperty("image", "");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        jsonObject.addProperty("image", "");
                    }

                    Call<List<ResponseModel>> call = mAPIService.insertPldwnList(jsonObject);
                    call.enqueue(new Callback<List<ResponseModel>>() {
                        @Override
                        public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                            try {
                                if (response.isSuccessful()) {
                                    progressDialog.hideDialog();
                                    List<ResponseModel> pldResponseList = response.body();
                                    if (pldResponseList != null && pldResponseList.size() > 0 && pldResponseList.get(0).condition) {
                                        Toast.makeText(getActivity(), pldResponseList.get(0).message, Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                        getPloughDownList();
                                        imageEncodList.clear();
                                    } else {
                                        Toast.makeText(getActivity(), pldResponseList != null && pldResponseList.size() > 0 ? "No data found" : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    progressDialog.hideDialog();
                                    Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                progressDialog.hideDialog();
                                ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "insert_pld_list", getActivity());
                            }
                        }

                        @Override
                        public void onFailure(Call<List<ResponseModel>> call, Throwable t) {
                            progressDialog.hideDialog();
                            ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "insert_pld_list", getActivity());
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "Please wait for internet connection.", Toast.LENGTH_SHORT).show();
                }
//                }


            });
        } else if (flag.equalsIgnoreCase("Update")) {
            filter_apply_bt.setVisibility(View.GONE);
            update_detail_btn.setVisibility(View.VISIBLE);
            tv_pld_update_title.setVisibility(View.VISIBLE);
            tv_pld_update_title.setText("Update PLD" + "(" + ploghDownListModel.pld_code + ")");
            add_pld_title.setVisibility(View.GONE);
            rl_custom_layout.requestFocus();

            rl_custom_layout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        rl_custom_layout.requestFocus();
                    }
                    return true;
                }
            });

            if (ploghDownListModel.date != null && !ploghDownListModel.date.equalsIgnoreCase("")) {
                ed_pld_date.setText(DateTimeUtilsCustome.splitDateInYYYMMDDslsh(ploghDownListModel.date));
            }

            if (ploghDownListModel.lot_no != null) {
                ac_lot_no.setText(ploghDownListModel.lot_no);
                ac_lot_no.setSelection(ac_lot_no.getText().length());
                ac_lot_no.clearFocus();
                frame_layout_lot_num_card_view.setVisibility(View.GONE);
            }

            ac_lot_status.setText(ploghDownListModel.lot_status);
            ed_reason_.setText(ploghDownListModel.reason);
            if (ploghDownListModel.pld_acre != null) {
                ed_pld_acre.setText(ploghDownListModel.pld_acre);
            }
            if (ploghDownListModel.sown_acre != null) {
                ed_sown_acre.setText(ploghDownListModel.sown_acre);
            }
            if (ploghDownListModel.image != null ) {
                img_card.setVisibility(View.VISIBLE);
                clear_image_btn.setVisibility(View.VISIBLE);
                byte[] decodedString = Base64.decode(ploghDownListModel.image, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                Glide.with(getActivity())
                        .asBitmap()
                        .load(decodedByte)
                        .placeholder(R.drawable.default_img)
                        .into(vp_image);
            } else {
                img_card.setVisibility(View.GONE);
                clear_image_btn.setVisibility(View.GONE);
            }


            update_detail_btn.setOnClickListener(v -> {
                if (!ed_pld_acre.getText().toString().trim().equalsIgnoreCase("")) {
                    float pld_acre = Float.parseFloat(ed_pld_acre.getText().toString());
                    float sown_acre = Float.parseFloat(ed_sown_acre.getText().toString());
                    if (pld_acre > sown_acre) {
                        Toast.makeText(getActivity(), "PLD Acre should be less than Sown Acre.", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        ed_pld_acre.setText(String.valueOf(pld_acre));
                        ed_pld_acre.setSelection(ed_pld_acre.getText().length());
                    }
                } else if (ed_reason_.getText().toString().trim().equals("")) {
                    Toast.makeText(getActivity(), "Please enter reason.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ac_lot_status.getText().toString().trim().equals("")) {
                    Toast.makeText(getActivity(), "Please enter lot. no", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ac_lot_status.getText().toString().trim().equals("")) {
                    Toast.makeText(getActivity(), "Please enter lot. status.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ed_pld_acre.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter pld acre", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ed_pld_acre.getText().toString().trim().equalsIgnoreCase(".")) {
                    Toast.makeText(getActivity(), "Please enter valid pld acre", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ed_pld_acre.getText().toString().trim().equalsIgnoreCase(".0") || ed_pld_acre.getText().toString().trim().equalsIgnoreCase("0.")) {
                    Toast.makeText(getActivity(), "Please enter valid pld acre", Toast.LENGTH_SHORT).show();
                    return;
                }

//            else {
                boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getActivity());
                if (isNetwork) {
                    LoadingDialog progressDialog = new LoadingDialog();
                    progressDialog.showLoadingDialog(getActivity());
                    NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("lot_no", ac_lot_no.getText().toString().trim());
                    jsonObject.addProperty("lot_status", ac_lot_status.getText().toString().trim());
                    jsonObject.addProperty("reason", ed_reason_.getText().toString().trim());
                    jsonObject.addProperty("date", DateTimeUtilsCustome.splitDateInYYYMMDD(ed_pld_date.getText().toString().trim()));
                    jsonObject.addProperty("pld_acre", ed_pld_acre.getText().toString().trim());
                    jsonObject.addProperty("sown_acre", ed_sown_acre.getText().toString().trim());
                    jsonObject.addProperty("pld_code", ploghDownListModel.pld_code);
                    try {
//                        String base64=  covertBase64File(imageEncodList.get(0));
                        String base64 = StaticMethods.convertBase64(imageEncodList.get(0));
                        if (base64 != null) {
                            jsonObject.addProperty("image", base64);
                        } else {
                            jsonObject.addProperty("image", "");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        jsonObject.addProperty("image", ploghDownListModel.image);
                    }

                    Call<List<ResponseModel>> call = mAPIService.updatePloughDownList(jsonObject);
                    call.enqueue(new Callback<List<ResponseModel>>() {
                        @Override
                        public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                            try {
                                if (response.isSuccessful()) {
                                    progressDialog.hideDialog();
                                    List<ResponseModel> pldResponseList = response.body();
                                    if (pldResponseList != null && pldResponseList.size() > 0 && pldResponseList.get(0).condition) {
                                        Toast.makeText(getActivity(), pldResponseList.get(0).message, Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                        getPloughDownList();
                                        imageEncodList.clear();
                                    } else {
                                        Toast.makeText(getActivity(), pldResponseList != null && pldResponseList.size() > 0 ? "No data found" : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    progressDialog.hideDialog();
                                    Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                progressDialog.hideDialog();
                                ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "update_pld_list", getActivity());
                            }
                        }

                        @Override
                        public void onFailure(Call<List<ResponseModel>> call, Throwable t) {
                            progressDialog.hideDialog();
                            ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "update_pld_list", getActivity());
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "Please wait for internet connection.", Toast.LENGTH_SHORT).show();
                }
//            }
            });
        }
    }

    private List<String> imageEncodList = new ArrayList<>();

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == SELECT_FILE && resultCode == RESULT_OK && data != null) {
                if (data.getData() != null) {
                    Uri mImageUri = data.getData();
                    String selectedFilePath = FilePath.getPath(getActivity(), mImageUri);
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    Bitmap bm = BitmapFactory.decodeFile(selectedFilePath, options);
                    setImageBitmap(bm, selectedFilePath);
                }
            } else {
                Toast.makeText(getActivity(), "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setImageBitmap(Bitmap bitmap, String selectedFilePath) {
        if (bitmap != null) {
            File file = new File(selectedFilePath);
            long file_size = file.length();
            long kb = file_size / 1024;
            long mb = kb / 1024;
            if (mb > 2) {
                img_card.setVisibility(View.GONE);
                clear_image_btn.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "File size must be less than 2MB.", Toast.LENGTH_SHORT).show();
            } else if (imageEncodList.size() != 2) {
                img_card.setVisibility(View.VISIBLE);
                imageEncodList.add(selectedFilePath);
                vp_image.setImageBitmap(bitmap);
                clear_image_btn.setVisibility(View.VISIBLE);
            }
         /*   else {
                img_card.setVisibility(View.VISIBLE);
                imageEncodList.add("");
                vp_image.setImageBitmap(bitmap);
                clear_image_btn.setVisibility(View.VISIBLE);
            }*/
        }
    }

    private void getPlantingLineListLot(RecyclerView rv_search_lot_num_list, String filter) {
        PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getActivity());
        try {
            PlantingLineLotListDao plantingLineLotListDao = pristineDatabase.plantingLineLotListDao();
            production_lot_list = plantingLineLotListDao.fetchFilter10AllData("%"+filter+"%");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bindProductionLotDataWithAdapter(rv_search_lot_num_list);
        }
    }

    private void bindProductionLotDataWithAdapter(RecyclerView rv_search_lot_num_list) {
        if (production_lot_list != null && production_lot_list.size() > 0) {
           /* PlantingProductionLotLineListAdapter plantingLineProductionLotAdapter = new PlantingProductionLotLineListAdapter(getActivity(), R.layout.android_item_view, production_lot_list);
            rv_search_lot_num_list.setAdapter(plantingLineProductionLotAdapter);*/
            PlantingProductionLotNumberAdapter productionLotNumberAdapter = new PlantingProductionLotNumberAdapter(getActivity(), production_lot_list);
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
        if (production_lot_list != null && production_lot_list.size() > 0) {
            PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getActivity());
            try {
                PlantingLineLotListDao plantingLineLotListDao = pristineDatabase.plantingLineLotListDao();
                plantingLineLotListTable = plantingLineLotListDao.getSowingAcre(production_lot_list.get(0).getProduction_Lot_No());
                ac_lot_no.setText(production_lot_list.get(pos).getProduction_Lot_No());
                frame_layout_lot_num_card_view.setVisibility(View.GONE);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (plantingLineLotListTable != null) {
                    ed_sown_acre.setText(plantingLineLotListTable.getSowing_Area_In_Acres());
                }
            }
        }else {
            frame_layout_lot_num_card_view.setVisibility(View.VISIBLE);
        }
    }

    public static String covertBase64File(String img_file) throws Exception {
        File imgFile = new File(img_file);
        if (imgFile.exists() && imgFile.length() > 0) {
//            Bitmap bm = BitmapFactory.decodeFile(img_file);
            Bitmap bm = getResizedBitmap(BitmapFactory.decodeFile(img_file), 400);
            if (bm != null) {
                ByteArrayOutputStream bOut = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 100, bOut);
                return Base64.encodeToString(bOut.toByteArray(), Base64.DEFAULT);
            }
        }
        return null;
    }

    public static Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }


}