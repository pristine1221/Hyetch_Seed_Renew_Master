package com.example.pristineseed.ui.eventManagement.addExpanse;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pristineseed.DataBaseRepository.EventManagementTable.EventManagemantTable;
import com.example.pristineseed.DataBaseRepository.EventManagementTable.EventManagementExpenseLineTable;
import com.example.pristineseed.DataBaseRepository.EventManagementTable.EventManagmentDao;
import com.example.pristineseed.DataBaseRepository.EventManagementTable.EventManagmentExpanceLineDao;
import com.example.pristineseed.DataBaseRepository.EventManagementTable.EventTypeMasterDao;
import com.example.pristineseed.DataBaseRepository.EventManagementTable.EventTypeMasterTable;
import com.example.pristineseed.DataBaseRepository.EventManagementTable.ImageMasterDao;
import com.example.pristineseed.DataBaseRepository.EventManagementTable.ImageMasterTable;
import com.example.pristineseed.GlobalNotification.NetworkUtil;
import com.example.pristineseed.R;
import com.example.pristineseed.RoomDataBase.PristineDatabase;
import com.example.pristineseed.global.ApiRequestFailure;
import com.example.pristineseed.global.FilePath;

import com.example.pristineseed.global.LoadingDialog;
import com.example.pristineseed.global.StaticMethods;

import com.example.pristineseed.model.event_managment_model.EventManagemantModel;
import com.example.pristineseed.model.event_managment_model.EventTypeMasterModel;
import com.example.pristineseed.model.event_managment_model.HitResponseModel;
import com.example.pristineseed.model.event_managment_model.SyncEventDetailModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;

import com.example.pristineseed.ui.adapter.event_adapterr.EventExpenseListAdapter;
import com.example.pristineseed.ui.adapter.event_adapterr.EventImageHorizontalAdapter;
import com.example.pristineseed.ui.adapter.event_adapterr.EventTypeAdapter;
import com.example.pristineseed.ui.adapter.event_adapterr.EventTypeChieldAdapter;
import com.example.pristineseed.ui.eventManagement.viewEvent.ViewEventDetailFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class AddEventExpenceFragment extends Fragment {
    private int PICK_IMAGE_MULTIPLE = 1;
    private AutoCompleteTextView dropdown_event_type;
    private TextInputEditText et_quantity, et_unitCost;
    private Button addExpenseLine, submitPage, selectImages;
    private ListView event_List;
    private TextView totalAmount;
    private LinearLayout expenses_add_layout, eventImageSection;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_event_expanse_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private SyncEventDetailModel submiteEventData = null;
    private List<EventTypeMasterTable> expenseTypeList = new ArrayList<>();
    private EventTypeMasterTable selectedExpenseType = null;
    private List<SyncEventDetailModel.ExpanceLineModel> userInsertedExpenseList = new ArrayList<>();
    private int line_no = 1;
    private EventExpenseListAdapter event_expense_Adapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dropdown_event_type = view.findViewById(R.id.dropdown_event_type);
        et_quantity = view.findViewById(R.id.et_quantity);
        et_unitCost = view.findViewById(R.id.et_unitCost);
        addExpenseLine = view.findViewById(R.id.addExpenseLine);
        submitPage = view.findViewById(R.id.submitPage);
        event_List = view.findViewById(R.id.event_List);
        totalAmount = view.findViewById(R.id.totalAmount);
        selectImages = view.findViewById(R.id.selectImages);
        expenses_add_layout = view.findViewById(R.id.expenses_add_layout);
        eventImageSection = view.findViewById(R.id.eventImageSection);
        try {
            submiteEventData = new Gson().fromJson(getArguments().getString("dataPass", ""), SyncEventDetailModel.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (submiteEventData.status.equalsIgnoreCase("CREATE APPROVED")) {
            expenses_add_layout.setVisibility(View.VISIBLE);
            eventImageSection.setVisibility(View.GONE);
            PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getActivity());
            try {
                EventTypeMasterDao eventTypeMasterDao = pristineDatabase.eventTypeMasterDao();
                expenseTypeList = eventTypeMasterDao.fetchChield(eventTypeMasterDao.getParentId(submiteEventData.event_type));

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                pristineDatabase.close();
                pristineDatabase.destroyInstance();
            }

            EventTypeChieldAdapter fruitAdapter = new EventTypeChieldAdapter(getActivity(), R.layout.drop_down_textview, expenseTypeList);
            dropdown_event_type.setAdapter(fruitAdapter);
            dropdown_event_type.setOnItemClickListener((adapterView, view1, i, l) -> {
                selectedExpenseType = expenseTypeList.get(i);
            });
            et_quantity.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    float userInputQty = 0;
                    try {
                        userInputQty = et_quantity.getText().toString().equalsIgnoreCase("") ? 0 : Float.parseFloat(et_quantity.getText().toString());
                    } catch (Exception e) {
                        et_quantity.setText("0");
                        userInputQty = 0;
                    } finally {
                        totalAmount.setText(String.valueOf(userInputQty * (et_unitCost.getText().toString().equalsIgnoreCase("") ? 0 : Float.parseFloat(et_unitCost.getText().toString()))));
                    }
                }
            });
            et_unitCost.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    float userInputUnitCost = 0;
                    try {
                        userInputUnitCost = et_unitCost.getText().toString().equalsIgnoreCase("") ? 0 : Float.parseFloat(et_unitCost.getText().toString());
                        if (userInputUnitCost > Float.parseFloat(selectedExpenseType.getRate())) {
                            et_unitCost.setText("0");
                            userInputUnitCost = 0;
                        }
                    } catch (Exception e) {
                        et_unitCost.setText("0");
                        userInputUnitCost = 0;
                    } finally {
                        totalAmount.setText(String.valueOf(userInputUnitCost * (et_quantity.getText().toString().equalsIgnoreCase("") ? 0 : Float.parseFloat(et_quantity.getText().toString()))));
                    }
                }
            });
            addExpenseLine.setOnClickListener(view1 -> {
                if (dropdown_event_type.getText().toString().equalsIgnoreCase("")) {
                    dropdown_event_type.setError("Please Select Expense Type.");
                    return;
                } else if (et_quantity.getText().toString().equalsIgnoreCase("")) {
                    et_quantity.setError("Please Enter Quantity.");
                    return;
                } else if (et_unitCost.getText().toString().equalsIgnoreCase("")) {
                    et_unitCost.setError("Please Enter Rate.");
                    return;
                }
                dropdown_event_type.setError(null);
                SyncEventDetailModel.ExpanceLineModel event_expense_line_model = new SyncEventDetailModel().new ExpanceLineModel();
                event_expense_line_model.event_code = submiteEventData.event_code;
                event_expense_line_model.line_no = String.valueOf(line_no);
                event_expense_line_model.expense_type = selectedExpenseType.getEvent_type();
                event_expense_line_model.quantity = et_quantity.getText().toString();
                event_expense_line_model.rate_unit_cost = et_unitCost.getText().toString();
                event_expense_line_model.amount = totalAmount.getText().toString();
                userInsertedExpenseList.add(event_expense_line_model);
                line_no++;
                event_expense_Adapter = new EventExpenseListAdapter(getActivity(), userInsertedExpenseList);
                event_List.setAdapter(event_expense_Adapter);
                dropdown_event_type.setText("");
                et_quantity.setText("");
                et_unitCost.setText("");
                totalAmount.setText("0");
            });
            submitPage.setOnClickListener(view1 -> {
                try {
                    if (imagesEncodedList == null || imagesEncodedList.isEmpty()) {
                        Snackbar.make(submitPage, "Please Select Images.", Snackbar.LENGTH_LONG).show();
                        return;
                    } else if (userInsertedExpenseList == null || userInsertedExpenseList.isEmpty() || userInsertedExpenseList.size() == 0) {
                        Snackbar.make(submitPage, "Please Add Minimun Single Expense Line.", Snackbar.LENGTH_LONG).show();
                        return;
                    }
                    boolean networkUtil = NetworkUtil.getConnectivityStatusBoolean(getContext());
                    LoadingDialog progressDialogLoading = new LoadingDialog();
                    try {
                        if (networkUtil) {
                            JSONArray array = new JSONArray();
                            for (SyncEventDetailModel.ExpanceLineModel data : userInsertedExpenseList) {
                                JSONObject obj = new JSONObject();
                                obj.put("event_code", data.event_code);
                                obj.put("line_no", String.valueOf(data.line_no));
                                obj.put("expense_type", data.expense_type);
                                obj.put("quantity", data.quantity);
                                obj.put("rate_unit_cost", data.rate_unit_cost);
                                obj.put("amount", data.amount);
                                array.put(obj);
                            }
                            //todo call submit event expence api............
                            postImageLineToServer(array, progressDialogLoading);
                        }
                    } catch (Exception e) {
                        progressDialogLoading.hideDialog();
                        Log.e("insert_exception", e.getMessage());
                    }

                } catch (Exception e) {
                    Log.e("insert_exception", e.getMessage());
                }
            });
            selectImages.setOnClickListener(view1 -> {
                if (selectImages.getText().toString().equalsIgnoreCase("Images")) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE);
                } else {
                    selectImages.setText("Images");
                    if (imagesEncodedList != null && !imagesEncodedList.isEmpty()) {
                        imagesEncodedList.clear();
                    }
                }
            });
        } else {
            selectImages.setEnabled(false);
            submitPage.setEnabled(false);
            List<SyncEventDetailModel.ExpanceLineModel> event_expence_line = submiteEventData.expense_line;
            if (event_expence_line != null && event_expence_line.size() > 0 && event_expence_line.get(0).event_code != null) {
                event_expense_Adapter = new EventExpenseListAdapter(getActivity(), event_expence_line);
                event_List.setAdapter(event_expense_Adapter);
            }
            // todo bindImage section
            eventImageSection.setVisibility(View.VISIBLE);
            try {
                if (event_expence_line != null && event_expence_line.size() > 0) {
                    List<SyncEventDetailModel.ImageListModel> imageListModelList = event_expence_line.get(0).image;
                    RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
                    EventImageHorizontalAdapter mAdapter = new EventImageHorizontalAdapter(getActivity(), imageListModelList);
                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                    mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(mAdapter);
                }
            } catch (Exception e) {
                Log.e("local_img_exc", e.getMessage());
            }
        }
    }

    //todo prepareImageArray
    private MultipartBody.Part prepareImageFilePart(File file) {
        RequestBody requestFile = null;
        if (file != null) {
            requestFile = RequestBody.create(MediaType.parse("*/*"), file);
        }
        return MultipartBody.Part.createFormData("files", file.getName(), requestFile);
    }


    private void postImageLineToServer(JSONArray jsonArray, LoadingDialog progressDialogLoading) {
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        ArrayList<MultipartBody.Part> fileListToUpload = new ArrayList<>();
        for (String url : imagesEncodedList) {
            fileListToUpload.add(prepareImageFilePart(new File(url)));
        }
        RequestBody postedArray = RequestBody.create(MediaType.parse("multipart/form-data"), jsonArray.toString());
        RequestBody acutal_farmers = RequestBody.create(MediaType.parse("multipart/form-data"), submiteEventData.actual_farmers != null ? String.valueOf(Integer.parseInt(submiteEventData.actual_farmers)) : "0");
        RequestBody actualDealers = RequestBody.create(MediaType.parse("multipart/form-data"), submiteEventData.actual_dealers != null ? String.valueOf(Integer.parseInt(submiteEventData.actual_dealers)) : "0");
        RequestBody actualDistributer = RequestBody.create(MediaType.parse("multipart/form-data"), submiteEventData.actual_distributers != null ? String.valueOf(Integer.parseInt(submiteEventData.actual_distributers)) : "0");

        progressDialogLoading.showLoadingDialog(getActivity());
        Log.e("from_json data", actualDealers.toString() + actualDistributer.toString() + acutal_farmers.toString() + postedArray.toString() + fileListToUpload);
        Call<List<HitResponseModel>> call = mAPIService.insertEventExpense(postedArray, fileListToUpload, acutal_farmers, actualDistributer, actualDealers);
        call.enqueue(new Callback<List<HitResponseModel>>() {
            @Override
            public void onResponse(Call<List<HitResponseModel>> call, Response<List<HitResponseModel>> response) {
                try {
                    if (response.isSuccessful()) {
                        progressDialogLoading.hideDialog();
                        List<HitResponseModel> serverResponseList = response.body();
                      if (serverResponseList!=null && serverResponseList.size() > 0 && serverResponseList.get(0).condition) {
                          setAllDetailAfterSubmission(serverResponseList);
                            Toast.makeText(getActivity(), serverResponseList.get(0).message, Toast.LENGTH_SHORT).show();
                        } else {
                            progressDialogLoading.hideDialog();
                            Toast.makeText(getActivity(), serverResponseList.get(0).message, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        progressDialogLoading.hideDialog();
                        Toast.makeText(getActivity(), "Error code !", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    progressDialogLoading.hideDialog();
                    e.printStackTrace();
                    Log.e("Error", e.getMessage());
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "event_insert_line_fragment", getActivity());
                }
            }

            @Override
            public void onFailure(Call<List<HitResponseModel>> call, Throwable t) {
                progressDialogLoading.hideDialog();
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "event_insert_line_fragment", getActivity());
            }
        });
    }

    private void setAllDetailAfterSubmission(List<HitResponseModel> serverResponseList) {
        try {
            int display_qty = 0;
            int total_qty = 0;
            float total_rate_unit_cost = 0;
            float rate_unit_cost_cal = 0;
            if (userInsertedExpenseList != null && userInsertedExpenseList.size() > 0) {
                for (int i = 0; i < userInsertedExpenseList.size(); i++) {
                    rate_unit_cost_cal = Float.parseFloat(userInsertedExpenseList.get(i).rate_unit_cost);
                    display_qty = Integer.parseInt(userInsertedExpenseList.get(i).quantity);
                    total_qty = total_qty + display_qty;

                    total_rate_unit_cost = total_rate_unit_cost + rate_unit_cost_cal;
                }
                totalAmount.setText(String.valueOf(total_rate_unit_cost * (total_qty)));

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private List<String> imagesEncodedList = new ArrayList<>();

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK && null != data) {
                imagesEncodedList = new ArrayList<String>();
                if (data.getData() != null) {
                    Uri mImageUri = data.getData();
                    String selectedFilePath = FilePath.getPath(getActivity(), mImageUri);
                    imagesEncodedList.add(selectedFilePath);
                } else {
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {
                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            String selectedFilePath = FilePath.getPath(getActivity(), uri);
                            imagesEncodedList.add(selectedFilePath);
                        }
                        Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
                    }
                }
            } else {
                Toast.makeText(getActivity(), "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        } finally {
            selectImages.setText(imagesEncodedList.size() + " Files");
        }
    }


    private void bindDataEventTypeMaster() {
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        LoadingDialog progressDialogLoading = new LoadingDialog();
        progressDialogLoading.showLoadingDialog(getActivity());
        Call<List<EventTypeMasterModel>> call = mAPIService.getEventTypeData("GetEventType");
        call.enqueue(new Callback<List<EventTypeMasterModel>>() {
            @Override
            public void onResponse(Call<List<EventTypeMasterModel>> call, Response<List<EventTypeMasterModel>> response) {
                try {
                    if (response.isSuccessful()) {
                        progressDialogLoading.hideDialog();
                        List<EventTypeMasterModel> tempdata = response.body();
                        if (tempdata!=null && tempdata.size() > 0 && tempdata.get(0).condition) {
                            saveEventTypeDataLocal(tempdata);
                        } else {
                            progressDialogLoading.hideDialog();
                            Toast.makeText(getActivity(), tempdata.size() > 0 ? "Result Not found !" : "Api not responding.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    progressDialogLoading.hideDialog();
                    //   Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "Event_type_master", getActivity());
                }
            }

            @Override
            public void onFailure(Call<List<EventTypeMasterModel>> call, Throwable t) {
                progressDialogLoading.hideDialog();
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "Event_type_master", getActivity());
            }
        });
    }

    private List<EventTypeMasterTable> eventTypeList = new ArrayList<>();

    private void saveEventTypeDataLocal(List<EventTypeMasterModel> tempdata) {
        PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getContext());
        try {
            EventTypeMasterDao eventTypeMasterDao = pristineDatabase.eventTypeMasterDao();
            for (int i = 0; i < tempdata.size(); i++) {
                EventTypeMasterTable eventTypeMasterTable = EventTypeMasterTable.getEventTypeData(tempdata.get(i));

                if (eventTypeMasterDao.isDataExist(eventTypeMasterTable.getId())) {
                    eventTypeMasterDao.update(eventTypeMasterTable);
                } else {
                    eventTypeMasterDao.insert(eventTypeMasterTable);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            pristineDatabase.close();
            pristineDatabase.destroyInstance();
            bindEventType();
        }
    }

    private void bindEventType() {
        try {
            PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getContext());
            EventTypeMasterDao eventTypeMasterDao = pristineDatabase.eventTypeMasterDao();
            eventTypeList = eventTypeMasterDao.fetchParent();
            pristineDatabase.close();
            pristineDatabase.destroyInstance();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            EventTypeAdapter fruitAdapter = new EventTypeAdapter(getActivity(), R.layout.drop_down_textview, eventTypeList);
            dropdown_event_type.setAdapter(fruitAdapter);
        }
    }
}
