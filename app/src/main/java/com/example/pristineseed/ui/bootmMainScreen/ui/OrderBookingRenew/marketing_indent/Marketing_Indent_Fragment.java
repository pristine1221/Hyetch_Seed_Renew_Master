package com.example.pristineseed.ui.bootmMainScreen.ui.OrderBookingRenew.marketing_indent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
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
import com.example.pristineseed.model.BookingOrder.MarketingIndentModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.adapter.order_book.MarketingIndentAdapter;
import com.google.android.material.chip.Chip;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Marketing_Indent_Fragment extends Fragment {
    private ExpandableListView marketing_indent_list;
    private Chip add_header;
    private SessionManagement sessionManagement;
    private List<MarketingIndentModel> approval_list;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.marketing_indent_fragment, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        sessionManagement=new SessionManagement(getActivity());
        initView(view);

        add_header.setOnClickListener(v -> {
            CreateMarketingIndentFragment createMarketingIndentFragment=new CreateMarketingIndentFragment();
            StaticMethods.loadFragmentsWithBackStack(getActivity(),createMarketingIndentFragment,"create_marketing_indent_fragment");
        });

        marketing_indent_list.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            MarketingIndentModel marketingIndentModel=approval_list.get(groupPosition);
            Bundle bundle=new Bundle();
            bundle.putString("data",new Gson().toJson(marketingIndentModel));
            CreateMarketingIndentFragment createMarketingIndentFragment=new CreateMarketingIndentFragment();
            createMarketingIndentFragment.setArguments(bundle);
            StaticMethods.loadFragmentsWithBackStack(getActivity(),createMarketingIndentFragment,"create_marketing_indent_fragment");
            return true;
        });
    }

    @Override
    public void onResume() {
        getMarketingIndentData();
        super.onResume();
    }

    private void initView(View view) {
        marketing_indent_list=view.findViewById(R.id.marketing_indent_list);
        add_header=view.findViewById(R.id.add_header);
    }

   private void  getMarketingIndentData(){
       boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getActivity());
       if (isNetwork) {
           LoadingDialog progressDialog = new LoadingDialog();
           progressDialog.showLoadingDialog(getActivity());
           NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
           Call<List<MarketingIndentModel>> call = mAPIService.getMarketingIndent(sessionManagement.getUserEmail());
           call.enqueue(new Callback<List<MarketingIndentModel>>() {
               @Override
               public void onResponse(Call<List<MarketingIndentModel>> call, Response<List<MarketingIndentModel>> response) {
                   try {
                       if (response.isSuccessful()) {
                           progressDialog.hideDialog();
                           List<MarketingIndentModel> orderbooking_list = response.body();
                           if (orderbooking_list != null && orderbooking_list.size() > 0 && orderbooking_list.get(0).condition) {
                               approval_list=orderbooking_list;
                               binddataWithAadapter(approval_list);
                           } else {
                               Toast.makeText(getActivity(), orderbooking_list!=null && orderbooking_list.size() > 0  ? "No data found" : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                           }
                       } else {
                           progressDialog.hideDialog();
                           Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                       }

                   } catch (Exception e) {
                       progressDialog.hideDialog();
                       ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "getMarketing_indent_data", getActivity());
                   }
               }
               @Override
               public void onFailure(Call<List<MarketingIndentModel>> call, Throwable t) {
                   progressDialog.hideDialog();
                   ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "getMarketing_indent_data", getActivity());
               }
           });
       } else {
           Toast.makeText(getActivity(), "Please wait for internet connection.", Toast.LENGTH_SHORT).show();
       }
   }

    private void binddataWithAadapter(List<MarketingIndentModel> marketingIndentModelList) {
        MarketingIndentAdapter marketingIndentAdapter=new MarketingIndentAdapter(getActivity(),marketingIndentModelList);
        marketing_indent_list.setAdapter(marketingIndentAdapter);
    }

}
