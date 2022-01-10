package com.example.pristineseed.ui.travel.viewtravel;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pristineseed.DataBaseRepository.travel.TravelHeaderTable;
import com.example.pristineseed.DataBaseRepository.travel.TravelViewHeaderDao;
import com.example.pristineseed.GlobalNotification.NetworkUtil;
import com.example.pristineseed.R;
import com.example.pristineseed.RoomDataBase.PristineDatabase;
import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.global.ApiRequestFailure;
import com.example.pristineseed.global.LoadingDialog;
import com.example.pristineseed.global.StaticMethods;
import com.example.pristineseed.model.travel.ta_da_model.SyncTravelDetailModel;
import com.example.pristineseed.model.travel.travel_view_header.TravelHeaderModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.travel.CreateTravelFragment;
import com.example.pristineseed.ui.travel.addExpanse.AddTravelExpanseFragment;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewTravelDetailFragment extends Fragment implements TravelViewListAdapter.OnItemClickListener {
    public static ViewTravelDetailFragment newInstance() {
        return new ViewTravelDetailFragment();
    }
   private boolean networkUtil;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_travel_detail_list_fragment, container, false);
    }

   private SessionManagement sessionManagement;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

 private RecyclerView travel_List;
 private TravelViewListAdapter travel_Adapter;
 private List<SyncTravelDetailModel>travelListData = new ArrayList<>();

    private String getSyncflag="getTravelSync";
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sessionManagement = new SessionManagement(getActivity());
        travel_List = view.findViewById(R.id.travel_List);
        travel_List.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        travel_List.setItemAnimator(new DefaultItemAnimator());
        travel_List.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

         networkUtil = NetworkUtil.getConnectivityStatusBoolean(getContext());
        if (networkUtil) {
            callUpdateListRefrshStatus();
        } else {
            Toast.makeText(getActivity(),"Please wait for internet connection",Toast.LENGTH_SHORT).show();
        }
    }

    private void callUpdateListRefrshStatus(){
                LoadingDialog progressDialogLoading = new LoadingDialog();
                progressDialogLoading.showLoadingDialog(getActivity());
                NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                Call<List<SyncTravelDetailModel>> call = mAPIService.getTravelUpdateApproveStatus(getSyncflag,sessionManagement.getUserEmail());
                call.enqueue(new Callback<List<SyncTravelDetailModel>>() {
                    @Override
                    public void onResponse(Call<List<SyncTravelDetailModel>> call, Response<List<SyncTravelDetailModel>> response) {
                        try {
                            if (response.isSuccessful()) {
                                List<SyncTravelDetailModel> responseList = response.body();
                                progressDialogLoading.hideDialog();
                                if (responseList!=null && responseList.size() > 0 && responseList.get(0).condition) {
                                    travelListData=responseList;
                                    Toast.makeText(getActivity(), "Data fetch Successfully!", Toast.LENGTH_SHORT).show();
                                    bindResponse();
                                } else {
                                    progressDialogLoading.hideDialog();
                                    Toast.makeText(getContext(), responseList.size() > 0 ? responseList.get(0).message : "Record Not inserted.",Toast.LENGTH_SHORT ).show();
                                }
                            } else {
                                progressDialogLoading.hideDialog();
                                Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            progressDialogLoading.hideDialog();
                            ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "view_travel_detail", getActivity());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<SyncTravelDetailModel>> call, Throwable t) {
                        progressDialogLoading.hideDialog();
                        ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "view_travel_detail", getActivity());
                    }
                });
    }

    private void bindResponse() {
           travel_Adapter = new TravelViewListAdapter(getActivity(), travelListData,this);
           travel_List.setAdapter(travel_Adapter);
    }

    @Override
    public void onItemClick(int position){
        Bundle bundle=new Bundle();
        bundle.putBoolean("flag",true);
        bundle.putString("passdata",new Gson().toJson(travelListData.get(position)));
        AddTravelExpanseFragment addTravelExpanseFragment=new AddTravelExpanseFragment();
        addTravelExpanseFragment.setArguments(bundle);
        StaticMethods.loadFragmentsWithBackStack(getActivity(),addTravelExpanseFragment,"add_expence_travel");
    }
    @Override
    public void onResume() {
        callUpdateListRefrshStatus();
        Log.e("fragment_resume","fragment_resume");
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}