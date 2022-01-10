package com.example.pristineseed.ui.tfa_summary.viewTFA;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.pristineseed.GlobalNotification.NetworkUtil;
import com.example.pristineseed.R;
import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.global.ApiRequestFailure;
import com.example.pristineseed.global.LoadingDialog;
import com.example.pristineseed.global.StaticMethods;
import com.example.pristineseed.model.tfa.TFAHeaderModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.tfa_summary.createTFA.CreateTFASummaryFragment;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewTFADetailFragment extends Fragment {
    private SessionManagement sessionManagement;
    private ListView tfa_view_list;
    private List<TFAHeaderModel> tfaLineModelList=new ArrayList<>();
    public static ViewTFADetailFragment newInstance() {
        return new ViewTFADetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.view_event_detail_fragment, container, false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tfa_view_list=view.findViewById(R.id.event_list);
        sessionManagement=new SessionManagement(getActivity());

        tfa_view_list.setOnItemClickListener((parent, view1, position, id) -> {
            if(tfaLineModelList!=null && tfaLineModelList.size()>0 && tfaLineModelList.get(position).status.equalsIgnoreCase("Reject")){
                Bundle bundle = new Bundle();
                bundle.putString("flag", "Reject");
                bundle.putString("passdata", new Gson().toJson(tfaLineModelList.get(position)));
                CreateTFASummaryFragment createTFASummaryFragment = new CreateTFASummaryFragment();
                createTFASummaryFragment.setArguments(bundle);
                StaticMethods.loadFragmentsWithBackStack(getActivity(), createTFASummaryFragment, "create_tfa_fragment");
            }
           else if(tfaLineModelList!=null && tfaLineModelList.size()>0) {
                Bundle bundle = new Bundle();
                bundle.putString("flag", "Pending");
                bundle.putString("passdata", new Gson().toJson(tfaLineModelList.get(position)));
                CreateTFASummaryFragment createTFASummaryFragment = new CreateTFASummaryFragment();
                createTFASummaryFragment.setArguments(bundle);
                StaticMethods.loadFragmentsWithBackStack(getActivity(), createTFASummaryFragment, "create_tfa_fragment");
            }
        });
    }


    private void callUpdateListRefrshStatus(){
        LoadingDialog progressDialogLoading = new LoadingDialog();
        try {
            progressDialogLoading.showLoadingDialog(getActivity());
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            Call<List<TFAHeaderModel>> call = mAPIService.getTFAHeaderLine(sessionManagement.getUserEmail());
            call.enqueue(new Callback<List<TFAHeaderModel>>() {
                @Override
                public void onResponse(Call<List<TFAHeaderModel>> call, Response<List<TFAHeaderModel>> response) {
                    try {
                        if (response.isSuccessful()) {
                            List<TFAHeaderModel> responseList = response.body();
                            progressDialogLoading.hideDialog();
                            if (responseList!=null && responseList.size() > 0 && responseList.get(0).condition) {
                                tfaLineModelList=responseList;
                                bindResponse();
                                Toast.makeText(getActivity(), "Data fetch Successfully!", Toast.LENGTH_SHORT).show();
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
                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "view_tfa_detail", getActivity());
                    }
                }

                @Override
                public void onFailure(Call<List<TFAHeaderModel>> call, Throwable t) {
                    progressDialogLoading.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "view_tfa_detail", getActivity());
                }
            });

        } catch (Exception e) {
            Log.e("exception",e.getMessage());
            Log.e("cause",e.getCause().toString());

        }
    }

   private void bindResponse() {
       TFAListAdapter tfaListAdapter = new TFAListAdapter(getActivity(), tfaLineModelList);
       tfa_view_list.setAdapter(tfaListAdapter);

    }
    @Override
    public void onResume() {
        if(NetworkUtil.getConnectivityStatusBoolean(getActivity())){
            callUpdateListRefrshStatus();
        }else {
            return;
        }
        super.onResume();
    }
}
