package com.example.pristineseed.ui.eventManagement.approveEvent;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.pristineseed.R;
import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.global.ApiRequestFailure;
import com.example.pristineseed.global.LoadingDialog;
import com.example.pristineseed.global.StaticMethods;

import com.example.pristineseed.model.event_managment_model.SyncEventDetailModel;
import com.example.pristineseed.model.travel.travel_view_header.EventCreateResponseModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.adapter.event_adapterr.EventApproveListAdapter;
import com.example.pristineseed.ui.eventManagement.createEvent.CreateEventFragment;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.io.output.AppendableOutputStream;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventApprovalFragment  extends Fragment {

   private SessionManagement sessionManagement;
   private ListView event_List;
   private TextView no_record_found;
   private EventApproveListAdapter event_Adapter;

   private List<SyncEventDetailModel> list = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_event_detail_fragment, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // mViewModel = ViewModelProviders.of(this).get(ApproveEventDetailViewModel.class);
        // TODO: Use the ViewModel
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        sessionManagement = new SessionManagement(getActivity());
        event_List = view.findViewById(R.id.event_list);
        no_record_found = view.findViewById(R.id.no_data_found);
           super.onViewCreated(view, savedInstanceState);
    }




    private void callEventApproveApi() {
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        LoadingDialog progressDialogLoading = new LoadingDialog();
        progressDialogLoading.showLoadingDialog(getActivity());
        Call<List<SyncEventDetailModel>> call = mAPIService.getEventApproveDetail("getEventApproveExpense", sessionManagement.getUserEmail());
        call.enqueue(new Callback<List<SyncEventDetailModel>>() {
            @Override
            public void onResponse(Call<List<SyncEventDetailModel>> call, Response<List<SyncEventDetailModel>> response) {
                try {
                    if (response.isSuccessful()) {
                        progressDialogLoading.hideDialog();
                        List<SyncEventDetailModel> tempdata = response.body();
                        if (tempdata.size() > 0 && tempdata.get(0).condition) {
                            list = tempdata;
                            Toast.makeText(getActivity(),"fetch data successful.", Toast.LENGTH_SHORT).show();
                            bindUiListData();
                        } else {
                            progressDialogLoading.hideDialog();
                            no_record_found.setVisibility(View.VISIBLE);
                            event_List.setVisibility(View.GONE);
//                            Toast.makeText(getActivity(), tempdata.size() > 0 ? "Result Not found !  EA" : "Api not responding.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    progressDialogLoading.hideDialog();
                    //   Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "event_approve_detail_fragment", getActivity());
                }
            }

            @Override
            public void onFailure(Call<List<SyncEventDetailModel>> call, Throwable t) {
                progressDialogLoading.hideDialog();
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "event_approve_detail_fragment", getActivity());
            }
        });
    }

    private void bindUiListData() {
        event_Adapter = new EventApproveListAdapter(getActivity(), list, (selectedeventData, position, flag) -> {
            if (flag.equalsIgnoreCase("approve") || flag.equalsIgnoreCase("reject"))
                approveRejectOrder(selectedeventData, position, flag);
            else if (flag.equalsIgnoreCase("view")) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("flag", true);
                bundle.putBoolean("approveView", true);
                bundle.putString("passdata", new Gson().toJson(list.get(position)));
                CreateEventFragment createEventFragment = new CreateEventFragment();
                createEventFragment.setArguments(bundle);
                StaticMethods.loadFragmentsWithBackStack(getActivity(), createEventFragment, "CreateEvent_Fragment");
                //loadFragments(R.id.nav_event_create, "Create Event", bundle);
            }
        });
        event_List.setAdapter(event_Adapter);


    }


    void approveRejectOrder(SyncEventDetailModel selectedeventData, int position, String flag) {
        LoadingDialog progressDialogLoading=new LoadingDialog();
        progressDialogLoading.hideDialog();
        if (flag.equalsIgnoreCase("approve")) {
            new MaterialAlertDialogBuilder(getActivity())
                    .setTitle("Confirm " + selectedeventData.event_code)
                    .setMessage("Do you really want to approve?")
                    .setIcon(R.drawable.approve_order)
                    .setPositiveButton("Confirm", (dialogInterface, i) -> {
                        try {
                            JsonObject postedjson = new JsonObject();
                            postedjson.addProperty("email", "WSRV32017-IND\\PARAS");
                            postedjson.addProperty("event_code", selectedeventData.event_code);
                            postedjson.addProperty("event_status", selectedeventData.status.equalsIgnoreCase("PENDING") ? "CREATE APPROVED" : "APPROVED");
                            postedjson.addProperty("reson", selectedeventData.status.equalsIgnoreCase("PENDING") ? "CREATE APPROVED" : "APPROVED");
                            callApiApproveReject(postedjson, position,progressDialogLoading);
                        } catch (Exception e) {
                            progressDialogLoading.hideDialog();
                            Log.e("print_stack_trace", e.getMessage());
                        }
                    })
                    .setNegativeButton("Cancel", (dialogInterface, i) -> {

                    })
                    .show();
        } else if (flag.equalsIgnoreCase("reject")) {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
            builder.setTitle("Confirm " + selectedeventData.event_code);
            builder.setIcon(R.drawable.approve_order);
            LinearLayout parentVertical = new LinearLayout(getActivity());
            LinearLayout.LayoutParams parentVerticalParams = new LinearLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
            parentVertical.setLayoutParams(parentVerticalParams);
            parentVertical.setOrientation(LinearLayout.VERTICAL);
            TextView messageSession = new TextView(getActivity());
            messageSession.setText("Do you really want to reject?");
            messageSession.setTextColor(Color.BLACK);
            TextView enterReson_tv = new TextView(getActivity());
            enterReson_tv.setText("Enter Reason");
            enterReson_tv.setTextColor(getResources().getColor(R.color.colorAccent));
            enterReson_tv.setPadding(0, 10, 0, 0);
            EditText reason = new EditText(getActivity());
            parentVertical.addView(messageSession);
            parentVertical.addView(enterReson_tv);
            parentVertical.addView(reason);
            parentVertical.setPadding(40, 20, 40, 10);
            builder.setView(parentVertical);
            builder.setPositiveButton("Confirm", (dialogInterface, i) -> {
                try {
                    JsonObject postedjson = new JsonObject();
                    postedjson.addProperty("email", "WSRV32017-IND\\PARAS");
                    postedjson.addProperty("event_code", selectedeventData.event_code);
                    postedjson.addProperty("event_status", selectedeventData.status.equalsIgnoreCase("PENDING") ? "CREATE REJECTED" : "REJECTED");
                    postedjson.addProperty("reson", reason.getText().toString());
                    callApiApproveReject(postedjson, position,progressDialogLoading);

                } catch (Exception e) {
                    progressDialogLoading.hideDialog();
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("Cancel", null);
            builder.show();
        }

    }

    @Override
    public void onResume() {
        callEventApproveApi();
        super.onResume();
    }

    void callApiApproveReject(JsonObject postedJson, int position,LoadingDialog progressDialogLoading) {
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            progressDialogLoading.showLoadingDialog(getActivity());
            Call<List<EventCreateResponseModel>> call = mAPIService.updateEventStatusDeatil(postedJson);
            call.enqueue(new Callback<List<EventCreateResponseModel>>() {
                @Override
                public void onResponse(Call<List<EventCreateResponseModel>> call, Response<List<EventCreateResponseModel>> response) {
                    if (response.isSuccessful()) {
                        progressDialogLoading.hideDialog();
                        List<EventCreateResponseModel> eventCreateResponseModelList = response.body();
                        if (eventCreateResponseModelList.size() > 0 && eventCreateResponseModelList.get(0).condition) {
                            list.remove(position);
                            event_Adapter.notifyDataSetChanged();
                            Toast.makeText(getActivity(), eventCreateResponseModelList.get(0).message, Toast.LENGTH_SHORT).show();
                        } else {
                            Snackbar.make(event_List, eventCreateResponseModelList.size() > 0 ? eventCreateResponseModelList.get(0).message : "Record Not inserted.", Snackbar.LENGTH_INDEFINITE).setAction("Cancel", view -> {
                            }).show();
                        }
                    } else {
                        progressDialogLoading.hideDialog();
                        Toast.makeText(getContext(), "Errror Code:", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<List<EventCreateResponseModel>> call, Throwable t) {
                    progressDialogLoading.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "event_approve_reject_api", getActivity());
                }
            });
    }

}
