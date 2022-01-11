package com.example.pristineseed.ui.eventManagement.viewEvent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pristineseed.DataBaseRepository.EventManagementTable.EventManagemantTable;
import com.example.pristineseed.DataBaseRepository.EventManagementTable.EventManagementExpenseLineTable;
import com.example.pristineseed.DataBaseRepository.EventManagementTable.EventManagmentDao;
import com.example.pristineseed.DataBaseRepository.EventManagementTable.EventManagmentExpanceLineDao;
import com.example.pristineseed.GlobalNotification.NetworkUtil;
import com.example.pristineseed.R;
import com.example.pristineseed.RoomDataBase.PristineDatabase;
import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.global.ApiRequestFailure;

import com.example.pristineseed.global.LoadingDialog;
import com.example.pristineseed.global.StaticMethods;

import com.example.pristineseed.model.event_managment_model.SyncEventDetailModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.adapter.event_adapterr.EventViewListAdapter;
import com.example.pristineseed.ui.eventManagement.createEvent.CreateEventFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class ViewEventDetailFragment extends Fragment  implements EventViewListAdapter.EventItemClicListner {

    public static ViewEventDetailFragment newInstance() {
        return new ViewEventDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.event_view_list_fragment, container, false);
    }

    private SessionManagement sessionManagement;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private RecyclerView event_List;
    private TextView no_record_found;
    private EventViewListAdapter event_Adapter;
    private List<SyncEventDetailModel> eventListData = new ArrayList<>();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        sessionManagement = new SessionManagement(getActivity());
        initView(view);

        super.onViewCreated(view, savedInstanceState);
    }

    private void initView(View view) {
        event_List = view.findViewById(R.id.event_reycleList);
        no_record_found = view.findViewById(R.id.no_data_found);
        event_List.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        event_List.setItemAnimator(new DefaultItemAnimator());
        event_List.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    private void getEventViewDetailData() {
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        LoadingDialog progressDialogLoading = new LoadingDialog();
        progressDialogLoading.showLoadingDialog(getActivity());
        Call<List<SyncEventDetailModel>> call = mAPIService.getEventApproveDetail("getEventSync", sessionManagement.getUserEmail());
        call.enqueue(new Callback<List<SyncEventDetailModel>>() {
            @Override
            public void onResponse(Call<List<SyncEventDetailModel>> call, Response<List<SyncEventDetailModel>> response) {
                try {
                    if (response.isSuccessful()) {
                        progressDialogLoading.hideDialog();
                        List<SyncEventDetailModel> tempdata = response.body();
                        if (tempdata != null && tempdata.size() > 0 && tempdata.get(0).condition) {
                            eventListData=tempdata;
                            bindDataWithList();
                        } else {
                            progressDialogLoading.hideDialog();
                            no_record_found.setVisibility(View.VISIBLE);
                            event_List.setVisibility(View.GONE);
//                            Toast.makeText(getActivity(), tempdata.size() > 0 ? "Result Not found VE!" : "Api not responding.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    progressDialogLoading.hideDialog();
                    Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "event_view_detail_fragment", getActivity());
                }
            }
            @Override
            public void onFailure(Call<List<SyncEventDetailModel>> call, Throwable t) {
                progressDialogLoading.hideDialog();
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "event_view_detail_fragment", getActivity());
            }
        });
    }


    private void bindDataWithList(){
        if(eventListData!=null && eventListData.size()>0)
        event_Adapter = new EventViewListAdapter(getActivity(), eventListData);
        event_List.setAdapter(event_Adapter);
        event_Adapter.setOnItemListner(this);
    }

        @Override
        public void onDestroy () {
            super.onDestroy();
        }

        @Override
        public void onPause () {
            super.onPause();
        }

        @Override
        public void onResume ()
        {
            boolean networkUtil = NetworkUtil.getConnectivityStatusBoolean(getContext());
            if (networkUtil) {
                getEventViewDetailData();
            } else {
              Toast.makeText(getActivity(),"Please wait for internet connection",Toast.LENGTH_SHORT).show();
            }
            super.onResume();
        }

        @Override
        public void onItemClick ( int pos){
            Bundle bundle = new Bundle();
            bundle.putBoolean("flag", true);
            bundle.putString("passdata", new Gson().toJson(eventListData.get(pos)));
            CreateEventFragment createEventFragment = new CreateEventFragment();
            createEventFragment.setArguments(bundle);
            StaticMethods.loadFragmentsWithBackStack(getActivity(), createEventFragment, "create_event_fragment");
        }
}